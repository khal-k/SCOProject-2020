package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.OrderPO;
import com.atguigu.crowd.entity.po.OrderProjectPO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.mapper.AddressPOMapper;
import com.atguigu.crowd.mapper.OrderPOMapper;
import com.atguigu.crowd.mapper.OrderProjectPOMapper;
import com.atguigu.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 19:00
 * @package com.atguigu.crowd.service.impl
 */
@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;


    @Override
    public OrderProjectVO getOrderProjectByProjectIdAndReturnId(Integer projectId,Integer returnId) {

        return orderProjectPOMapper.selectOrderProjectVo(returnId);
    }

    @Override
    public void saveOrderVo(OrderVO orderVO) {
        OrderPO orderPo = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPo);
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);
        orderProjectPOMapper.insert(orderProjectPO);

        Integer id = orderPo.getId();

        orderProjectPO.setOrderId(id);

        orderProjectPOMapper.insert(orderProjectPO);

    }
}

















