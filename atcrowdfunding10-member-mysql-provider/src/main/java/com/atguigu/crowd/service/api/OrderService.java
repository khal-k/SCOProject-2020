package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 19:00
 * @package com.atguigu.crowd.service.api
 */
public interface OrderService {

    /** 查询订单具体信息*/
    OrderProjectVO getOrderProjectByProjectIdAndReturnId(Integer projectId,Integer returnId);

    void saveOrderVo(OrderVO orderVO);
}
