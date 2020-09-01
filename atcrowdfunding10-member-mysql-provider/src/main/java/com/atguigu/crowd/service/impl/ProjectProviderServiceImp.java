package com.atguigu.crowd.service.impl;
import com.atguigu.crowd.entity.po.MemberConfirmInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ReturnPO;
import com.atguigu.crowd.entity.vo.*;
import com.atguigu.crowd.mapper.*;
import com.atguigu.crowd.service.api.ProjectProviderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author 孔佳齐丶
 * @create 2020-08-30 10:03
 * @package com.atguigu.crowd.service.impl
 */
@Service
@Transactional(readOnly = true)
public class ProjectProviderServiceImp implements ProjectProviderService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private TagPOMapper tagPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    /**
     * 遍历project对象
     * @return
     */
    @Override
    public List<PortalTypeVO> getPortalTypeVO() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {

        //1.查询得到detailProjectVO对象
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);

        //2.获取状态,
        Integer status = detailProjectVO.getStatus();
         switch (status){
             case 0:
                 detailProjectVO.setStatusText("审核中");
                 break;
             case 1:
                 detailProjectVO.setStatusText("众筹中");
                 break;
             case 2:
                 detailProjectVO.setStatusText("众筹成功");
                 break;
             case 3:
                 detailProjectVO.setStatusText("已关闭");
                 break;

             default:
                break;
         }

         //3.根据deployDate计算lastDay
        String deployDate = detailProjectVO.getDeployDate();

         //获取当前日期
        Date currentDay = new Date();

        //把众筹日期转换为Calendar类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = simpleDateFormat.parse(deployDate);

            //获取当前日期的时间戳
            long currentDayTimeStamp = currentDay.getTime();

            //获取众筹日期的时间戳
            long deployDayTime = deployDay.getTime();

            //两个时间戳相减得到已经过去的时间
            long passDays = (currentDayTimeStamp-deployDayTime)/1000/60/60/24;

            //获取总的众筹的天数
            Integer totalDays = detailProjectVO.getDay();

            //使用总的众筹时间减已经过去的天数
            Integer lastDay = (int) (totalDays-passDays);

            //剩余的天数
            detailProjectVO.setLastDay(lastDay);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailProjectVO;
    }

    @Override
    //rollbackFor 如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，数据库里面的数据也会回滚。
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    /**
     * 保存project
     */
    public void saveProject(ProjectVO projectVO, Integer memberId) {

    //一 保存ProjectPo对象
        // 1.创建空的ProjectPo对象
        ProjectPO projectPO = new ProjectPO();

        //2.把ProjectVo中的属性保存到ProjectPo中
        BeanUtils.copyProperties(projectVO, projectPO);

        //---修复,把memberId设置到ProjectPo中
        projectPO.setMemberid(memberId);

        //--修复,生成创建时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM:dd HH:mm:ss");
        Date date = new Date();
        String currentDate = simpleDateFormat.format(date);

        //--将创建时间存入
        projectPO.setCreatedate(currentDate);

        //status设置为0,为即将开始
        projectPO.setStatus(0);


        //3.将projectPo对象插入到数据库,
        //为了能获取插入数据的主键,需要在mapper.xml文件中设置属性
        //<insert id="insertSelective" useGeneratedKeys="true" keyProperty="id" ...
        projectPOMapper.insertSelective(projectPO);

        //4.获取插入数据的主键
        Integer projectId = projectPO.getId();

    //二.保存项目,分类 id 集合 ,分类关联关系集合
        //1.从projectVo中获取  private List<Integer> typeIdList;
        List<Integer> typeIdList = projectVO.getTypeIdList();

        //2.将数据集合插入到数据库
        // insert into t_project_type(projectid,typeid) values
        //    <foreach collection="typeIdList" item="typeId" separator=",">
        //      #(projectId),#(typeId)
        //    </foreach>
        projectPOMapper.insertRelationShipOfTypeIdList(projectId,typeIdList);

    //三,保存项目, 标签 id 集合 , 标签关联关系集合
        //1.从projectVo中获取,  private List<Integer> tagIdList;
        List<Integer> tagIdList = projectVO.getTagIdList();

        //2.将数据集合插入到数据库
        projectPOMapper.insertRelationShipOfTagList(projectId,tagIdList);

    //四,保存项目,详情图片的路径 ,详情图片路径集合
        //1.......private List<String> detailPicturePathList;
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();

        //2.将数据保存数据库,在ProjectItemPicPOMapper
        //  <insert id="insertPicturePathList">
        //    insert into t_project_item_pic(projectid,item_pic_path) values
        //    <foreach collection="detailPicturePathList" item="item_pic_path" separator=",">
        //      (#{projectId},#{item_pic_path})
        //    </foreach>
        //  </insert>
        projectItemPicPOMapper.insertPicturePathList(projectId,detailPicturePathList);

    //五,保存项目,发起人信息,
        //1.因为MemberLauchInfoVO是一个对象,不是数组,所以在projectVo中取出需要再次转换一下成为MemberLauchInfoPo
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

        //2.新建MemberLaunchInfoPO用来接收前端传输来的数据
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);

        //2.将memberId存入到memberLaunchInfoPO对象中
        memberLaunchInfoPO.setMemberid(memberId);

        //2.将memberLaunchInfoPO存入到数据库中
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

    //六,保存项目,回报信息集合
        //1.获取list
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();

        //2.遍历集合,将returnVOList集合中的每个ReturnVO转换为returnPO,然后添加到List<ReturnPO>集合中
       ReturnPO returnPO = new ReturnPO();
       for (ReturnVO returnVO:returnVOList){
           BeanUtils.copyProperties(returnVO, returnPO);
           returnPOList.add(returnPO);
       }

        //2.插入数据库
        returnPOMapper.insertReturnBeach(projectId,returnPOList);

     //七,保存项目,确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();

        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();

        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);

        memberConfirmInfoPO.setMemberid(memberId);

        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);

    }


}





















