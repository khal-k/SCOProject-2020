package spring.atguigu.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 18:11
 * @package spring.atguigu.crowd.handler
 */
@Controller
public class PortalHandler {
    @RequestMapping("/")
    public String showPortalPage(){
        //显示首页
        return "portal";
    }
}
