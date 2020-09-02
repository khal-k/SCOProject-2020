package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.AddressPO;
import com.atguigu.crowd.entity.po.AddressPOExample;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.mapper.AddressPOMapper;
import com.atguigu.crowd.service.api.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 22:22
 * @package com.atguigu.crowd.service.impl
 */
@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public List<AddressVO> getAddressVoList(Integer memberId) {
        AddressPOExample example = new AddressPOExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);
        List<AddressVO> addressVOList = new ArrayList<>();

        for(AddressPO addressPO:addressPOList){
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOList.add(addressVO);
        }

        return addressVOList;
    }

    @Override
    public void saveAddress(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO, addressPO);
        addressPOMapper.insert(addressPO);
    }
}
