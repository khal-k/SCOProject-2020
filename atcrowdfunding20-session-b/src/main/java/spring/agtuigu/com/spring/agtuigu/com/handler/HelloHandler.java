package spring.agtuigu.com.spring.agtuigu.com.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 孔佳齐丶
 * @create 2020-08-29 11:50
 * @package spring.agtuigu.com.spring.agtuigu.com.handler
 */
@RestController
public class HelloHandler {
    @RequestMapping("/test/spring/session/get")
    public String getSession(HttpSession session){
        String king = (String)session.getAttribute("king");
        return king;
    }
}
