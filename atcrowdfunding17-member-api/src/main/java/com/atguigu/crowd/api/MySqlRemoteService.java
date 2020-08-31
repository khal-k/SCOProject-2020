package com.atguigu.crowd.api;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.ResultEntity;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 14:17
 * @package com.atguigu.crowd.api
 */
@FeignClient(value = "kjq-crowd-mysql")
public interface MySqlRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct);

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVo(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
}
