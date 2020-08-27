package com.atguigu.crowd.handler;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.ResultEntity;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 14:26
 * @package com.atguigu.crowd.handler
 */
@RestController
public class MemberProviderHandler {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct) {

        try {

            //1.调用本地service完成查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginAcct);

            //2.没有异常返回成功结果
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();

            //3.补货异常,返回失败结果
            return ResultEntity.failed(e.getMessage());
        }
    }
}
