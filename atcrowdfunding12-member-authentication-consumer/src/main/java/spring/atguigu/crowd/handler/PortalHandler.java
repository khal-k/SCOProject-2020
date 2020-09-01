package spring.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySqlRemoteService;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import util.ResultEntity;

import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 18:11
 * @package spring.atguigu.crowd.handler
 */
@Controller
public class PortalHandler {

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    @RequestMapping("/")
    public String showPortalPage(ModelMap modelMap){

        //1.调用mySqlRemoteService获取portal的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySqlRemoteService.getPortalTypeProjectData();

        //2.判断查询数据是否成功
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){

            //3.获取查询集合
            List<PortalTypeVO> portalList = resultEntity.getData();

            //4.将查询集合放入modelMap中
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA,portalList);
        }

        //显示首页,实际开发中要价在数据
        return "portal";
    }
}
