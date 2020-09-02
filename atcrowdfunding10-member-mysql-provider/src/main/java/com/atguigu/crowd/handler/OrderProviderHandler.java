package com.atguigu.crowd.handler;

import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.service.api.AddressService;
import com.atguigu.crowd.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.ResultEntity;

import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 18:59
 * @package com.atguigu.crowd.handler
 */
@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @RequestMapping("/save/order/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){

        try {
            orderService.saveOrderVo(orderVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 保存地址
     * @param addressVO
     * @return
     */
    @RequestMapping("/save/address/vo/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
        try {
            addressService.saveAddress(addressVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 远程调用执行获取地址列表
     * @param memberId
     * @return
     */
    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVoRemote(@RequestParam("memberId") Integer memberId){
        try {
            List<AddressVO> addressVOList = addressService.getAddressVoList(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 远程调用执行获取汇报内容
     * @param projectId
     * @param returnId
     * @return
     */
    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVoRemote(@RequestParam("projectId") Integer projectId,
                                                         @RequestParam("returnId") Integer returnId){
        try {
            OrderProjectVO orderProjectVO =
                    orderService.getOrderProjectByProjectIdAndReturnId(projectId,returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}

















