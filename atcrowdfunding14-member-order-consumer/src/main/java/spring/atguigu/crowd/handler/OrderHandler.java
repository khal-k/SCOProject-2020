package spring.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySqlRemoteService;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import constant.CrowdConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 18:28
 * @package spring.atguigu.crowd.handler
 */
@Controller
@Slf4j
public class OrderHandler {

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    private Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    ///confirm/order/

    /**
     * form表单提交,保存address
     * @param addressVO
     * @return
     */
    @RequestMapping("save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session){
        //1.调用远程接口保存地址
        ResultEntity<String> resultEntity = mySqlRemoteService.saveAddressRemote(addressVO);

        logger.debug(resultEntity.getResult());

        //2.从session域中获取订单对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        //3.获取returnCount为了再次调用加载订单页面
        Integer returnCount = orderProjectVO.getReturnCount();


        return "redirect:http://www.crowd.com/order/confirm/order/"+returnCount;
    }

    ///confirm/order/"+returnCount
    @RequestMapping("/confirm/order/{returnCount}")
    public String confirmOrderInfo(@PathVariable("returnCount") Integer returnCount,
                                            HttpSession session){

        //1.把接收到的数据存到session域 中
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        //2.将数据设置到对象中
        orderProjectVO.setReturnCount(returnCount);

        //3.获取欧当前已登录用户的id
        MemberLoginVO memberLoginVo = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId= memberLoginVo.getId();

        //4.查询目前地址
        ResultEntity<List<AddressVO>> resultEntity = mySqlRemoteService.getAddressVoRemote(memberId);

        //5.判断查询结果
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){

            //6.获取查询到的地址列表
            List<AddressVO> addressVOList = resultEntity.getData();

            //7.将地址存放到session中
            session.setAttribute("addressVOList",addressVOList);
        }

        return "confirm-order";
    }

    /**
     * 根据projectDetail传来的projectId和returnId获取到返回结果的详细信息
     * @param projectId
     * @param returnId
     * @param session
     * @return
     */
    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirm(@PathVariable("projectId") Integer projectId,
                                    @PathVariable("returnId") Integer returnId ,
                                    HttpSession session){
        ResultEntity<OrderProjectVO> resultEntity =
                mySqlRemoteService.getOrderProjectVoRemote(projectId,returnId);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO orderProjectVO = resultEntity.getData();
            session.setAttribute("orderProjectVO",orderProjectVO);
        }

        return "confirm-return";
    }

}
