package spring.atguigu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 孔佳齐丶
 * @create 2020-08-30 10:55
 * @package spring.atguigu.crowd.config
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //view-controller是在projectConsumer中定义的,所以这里不经过zuul访问
        //所以访问路径不加zuul里的路由规则,不加"/project"
        ///project/agree/protocol/page
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");
        registry.addViewController("/launch/project/page").setViewName("project-launch");
        registry.addViewController("/return/info/page").setViewName("project-return");

        //project/create/confirm/page.html
        registry.addViewController("/create/confirm/page").setViewName("project-confirm");

       //ProjectConsumerHandler
        registry.addViewController("/create/success").setViewName("project-success");

    }
}
