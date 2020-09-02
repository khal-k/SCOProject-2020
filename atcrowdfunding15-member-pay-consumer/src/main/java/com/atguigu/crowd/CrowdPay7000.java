package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 孔佳齐丶
 * @create 2020-09-02 14:42
 * @package com.atguigu.crowd
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.atguigu.crowd.api")
public class CrowdPay7000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdPay7000.class, args);
    }
}
