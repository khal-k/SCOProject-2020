package spring.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 孔佳齐丶
 * @create 2020-09-01 16:57
 * @package spring.atguigu.crowd
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.atguigu.crowd.api")
public class CrowdOrder6000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdOrder6000.class, args);
    }
}
