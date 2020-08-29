package spring.agtuigu.com.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 孔佳齐丶
 * @create 2020-08-29 11:47
 * @package spring.agtuigu.com.handler
 */
@RestController
public class HelloHandler {
    @RequestMapping("/test/spring/session")
    public String testSession(HttpSession session){
        session.setAttribute("king", "hello-king");
        return "数据存入session域";
    }
}
