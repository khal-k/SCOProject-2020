package spring.atguigu.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 孔佳齐丶
 * @create 2020-08-28 11:07
 * @package spring.atguigu.crowd.config
 */
@Component
@ConfigurationProperties(prefix = "short.message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageProperties {

    private String host;
    private String path;
    private String appCode;
    private String sign;
    private String skin;

}
