package spring.atguigu.crowd.config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author 孔佳齐丶
 * @create 2020-08-29 21:58
 * @package spring.atguigu.crowd.config
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OSSProperties {
    private String endPoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketDomain;
}
