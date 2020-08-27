package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 孔佳齐丶
 * @create 2020-08-27 14:32
 * @package com.atguigu.crowd.service.impl
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImp implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {

        //1.创建example对象,
        MemberPOExample example = new MemberPOExample();

        //2.创建Criteria对象
        MemberPOExample.Criteria criteria = example.createCriteria();

        //3.封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        //获取结果
        List<MemberPO> list =  memberPOMapper.selectByExample(example);

        MemberPO memberPO = list.get(0);

        return memberPO;
    }
}
