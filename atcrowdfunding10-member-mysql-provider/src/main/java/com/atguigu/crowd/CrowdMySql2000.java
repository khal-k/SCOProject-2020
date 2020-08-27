package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 孔佳齐丶
 * @create 2020-08-27 11:45
 * @package com.atguigu.crowd
 */

@SpringBootApplication
@EnableDiscoveryClient
//扫描mybatis接口所在的包? 待定
@MapperScan(value = "com.atguigu.crowd.mapper")
public class CrowdMySql2000 {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMySql2000.class, args);
    }
}
