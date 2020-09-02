package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.vo.AddressVO;

import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 22:21
 * @package com.atguigu.crowd.service.api
 */
public interface AddressService {
    List<AddressVO> getAddressVoList(Integer memberId);

    void saveAddress(AddressVO addressVO);
}
