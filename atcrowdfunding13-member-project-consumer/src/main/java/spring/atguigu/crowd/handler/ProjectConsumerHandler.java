package spring.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySqlRemoteService;
import com.atguigu.crowd.entity.vo.MemberConfirmInfoVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.entity.vo.ReturnVO;
import constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import spring.atguigu.crowd.config.OSSProperties;
import util.CrowdUtil;
import util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-08-30 12:15
 * @package spring.atguigu.crowd.handler
 */
@Controller
public class ProjectConsumerHandler {

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    ///create/confirm
    @RequestMapping("/create/confirm")
    String projectConfirm(ModelMap modelMap ,HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){

        //1.从session域中获取ProjectVo对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        //2.将表单数据确认信息设置到projectVo中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        //3.获取登录用户信息session
       MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

       //4.获取登录用户信息的id
        Integer memberId = memberLoginVO.getId();

        //5.调用远程方法将数据存放入mysql数据库
        ResultEntity<String> resultEntity = mySqlRemoteService.saveProjectVo(projectVO,memberId);

        //6.判断存入结果
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            
            //7.保存成功后,将ProjectVo从session中移除
            session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            //8.存入成功,跳转到确认页面
            return "redirect:http://www.crowd.com/project/create/success";
        }else{
            //9.存入失败,返回结果信息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "project-confirm";
        }
    }


    ///project/create/save/return.json
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    ResultEntity<String> saveReturn(ReturnVO returnVo, HttpSession session){
        try {
            //1.从session域中读取到ProjectVo对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            //2.判断是否为null
            if(projectVO==null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMP_PROJECT_MISSING);
            }

            //3.从projectVo中获取信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            //4.判断returnVoList是否为空
            if(returnVOList==null||returnVOList.size()==0){

                returnVOList = new ArrayList<>();

                //为了以后能正常使用这个集合,将其设置到projectVo对象中
                projectVO.setReturnVOList(returnVOList);

            }

            //5.集合添加返回数据
            returnVOList.add(returnVo);

            //6.将数据有变化的ProjectVo重新存放在session中.以确保新数据能在redis中
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    ///project/create/upload/return/picture.json
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(
            //接收用户上传的文件
           @RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {

        //1.执行文件上传
        ResultEntity<String> upLoadHeaderPictureResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(), returnPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());

        return upLoadHeaderPictureResultEntity;
    }

    ///create/confirm/page.html

    //图片和图片详情提交到OSS阿里云存储
    @RequestMapping("create/project/information")
    public String saveProjectBasicInfo(
            // 接受除了图片的其他普通数据
            ProjectVO projectVO,
            //接受上传的头图
            MultipartFile headerPicture,
            //接受上传的详情图片
            List<MultipartFile> detailPictureList,
            //用来收集一部分数据的ProjectVo对象,存入session域
            HttpSession session,
            //用来操作失败后返回上一个表单页面的提示消息
            ModelMap modelMap) throws IOException {

        //1.完成头图上传
        boolean pictureIsEmpty = headerPicture.isEmpty();

        //如果头图为空,则返回错误消息
        if (pictureIsEmpty) {
            //2.如果上传失败返回并提示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }

        //2.不为空上传到阿里云oss,为空不执行//2.如果用户上传了有内容的文件,则上传
        ResultEntity<String> upLoadHeaderPictureResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(), headerPicture.getInputStream(),
                ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());
        String result = upLoadHeaderPictureResultEntity.getResult();

        //3.判断是否上传成功
        if (ResultEntity.SUCCESS.equals(result)) {

            //4.从返回的数据获取图片访问路径
            String headerPicturePath = upLoadHeaderPictureResultEntity.getData();

            //5.存入projectVO对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            //6.如果上传失败返回并提示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }


        //二.1.创建一个用来加载图pain路劲IG的集合
        List<String> detailPictureArrayList = new ArrayList<String>();

        //2.判断图片详细信息是否为空
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_LIST_PIC_EMPTY);
            return "project-launch";
        }

        //4.遍历detailPictureList集合
        for (MultipartFile detailPicture : detailPictureList) {

            if (detailPicture.isEmpty()) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }

            //5.判断是否为空
            //6.执行上传
            ResultEntity<String> detailUpLoadResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(), ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());

            //检查上传结果集
            String detailEntityResult = detailUpLoadResultEntity.getResult();

            if (ResultEntity.SUCCESS.equals(detailEntityResult)) {
                //7.将上传成功的图片信息放入集合
                detailPictureArrayList.add(detailEntityResult);
            }else {
                //6.如果上传失败返回并提示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }

        }

        //三.1.将存放图片路径的集合存放到projectVo集合中
        projectVO.setDetailPicturePathList(detailPictureArrayList);

        //2.将projectVo对象存入到session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        //3.已完整的访问路径前往下一个手机信息的页面
        return "redirect:http://www.crowd.com/project/return/info/page";
    }
}
