package spring.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 19:21
 * @package spring.atguigu.crowd
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CrowdGateWay80 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdGateWay80.class, args);
    }
}
