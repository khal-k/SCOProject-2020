package spring.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySqlRemoteService;
import com.atguigu.crowd.api.RedisRemoteService;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.MemberVO;
import constant.CrowdConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.atguigu.crowd.config.ShortMessageProperties;
import util.CrowdUtil;
import util.ResultEntity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 孔佳齐丶
 * @create 2020-08-28 11:01
 * @package spring.atguigu.crowd.handler
 */
@Controller
public class MemberHandler {

    @Autowired
    private ShortMessageProperties shortMessageProperties;
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    /**
     * 会员注册
     * @param memberVO
     * @param modelMap
     * @return
     */
    @RequestMapping("/auth/do/member/register")
    public String registerMember(MemberVO memberVO, ModelMap modelMap){

        //1.获取用户手机号
        String phoneNum = memberVO.getPhoneNum();

        //2.拼接redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;

        //3.从redis读取key对应的value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);

        //4.检查查询操作是否有效
        String result = resultEntity.getResult();

        //如果查询失败
        if(ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-reg";
        }

        //得到redis数据库中的查询数据
        String redisCode = resultEntity.getData();

        //数据库中没查询到
        if(redisCode==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXIT);
            return "member-reg";
        }

        //得到输入验证码
        String fromCode = memberVO.getCode();

        //判断输入验证码与输入验证码是否一致
        if(!Objects.equals(redisCode,fromCode)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        //6.如果验证码正确,删除redis中的验证码
       redisRemoteService.removeRedisKeyRemote(key);

        //5.对比表单验证码和redis中的验证码,如果能查到
         //7.执行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //获取用户输入的密码并进行加密操作,然后存入member对象
        String userPwdBeforeEncode = memberVO.getUserPswd();
        String userPwdAfterEncode = passwordEncoder.encode(userPwdBeforeEncode);
        memberVO.setUserPswd(userPwdAfterEncode);

        //8.执行保存操作
        //①先创建一个新的MemberPo对象,使用BeanUtil将MemberVo转换
        MemberPO memberPo = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPo);

        ResultEntity<String> saveResultEntity = mySqlRemoteService.saveMember(memberPo);
        if(ResultEntity.FAILED.equals(saveResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveResultEntity.getMessage());
            return "member-reg";
        }
        //使用重定向,
        return "redirect:/auth/member/to/login/page";
    }

    /**
     * 获取手机验证码
     * @param phoneNum
     * @return
     */
    @RequestMapping("auth/member/sent/short/message.json")
    @ResponseBody
    public ResultEntity<String> sentShortMessage(@RequestParam("phoneNum") String phoneNum){

        //1.发送验证码到phoneNum手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendShortMessage(
                phoneNum,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getSign(),
                shortMessageProperties.getSkin(),
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath());
        //2.判断短信发送结果
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            //3.如果发送成功,将验证码存入Redis
            //①从上一部的操作中获取随机生成的验证码
            String code = sendMessageResultEntity.getData();

            //②拼接用于在redis中存储数据的key
            String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;

            //③调用远程接口存入Redis
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeOut(key,code,15, TimeUnit.MINUTES);

            //④判断结果
            if(ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())){
                return ResultEntity.successWithoutData();
            }else{
                return saveCodeResultEntity;
            }
        }else{
            return sendMessageResultEntity;
        }
    }
}
