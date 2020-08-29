package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 11:45
 * @package com.atguigu.crowd
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.atguigu.crowd.api")
@MapperScan("com.atguigu.crowd.mapper")
public class CrowdMySql2000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMySql2000.class, args);
    }
}
