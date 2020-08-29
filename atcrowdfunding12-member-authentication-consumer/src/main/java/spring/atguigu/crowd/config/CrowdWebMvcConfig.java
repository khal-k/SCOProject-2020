package spring.atguigu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 孔佳齐丶
 * @create 2020-08-28 9:33
 * @package spring.atguigu.crowd.config
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器访问地址
        String urlPath = "/auth/member/to/reg/page";

        //目标视图名称,将来拼接"prefix:classpath:/templates/"suffix:.html"后缀
        String viewName = "member-reg";

        //添加view-controller
        registry.addViewController(urlPath).setViewName(viewName);
        ///auth/member/to/login
        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        //跳转用户中心的请求
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
    }
}
