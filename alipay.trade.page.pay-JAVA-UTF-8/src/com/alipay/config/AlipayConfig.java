package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000118641401";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaym+ayjxxaRzgZGgtvyN5cKPyqt1vDtR3XpB0K7xNkz/Ql32SzgUgGI+sI85AAOyAfhd0xNCX8jp2P7MQKyqPNGgYJVXx4FQvZgRtKe7cGxHyvVsHQTc7HX3KwfSvdePBE7l8/XqKM6QMcrxpGYxMzZrTvNDdBiCbdgJBeA4eiO4X38AYOGBBWnp2YM5knUWW0aBNQO4gmI+PELCxbD5Wwj77GvpIK2qDOLZqQsxjoTfLKMkDskn8gHVDqrF/ZiRdFMN0+pdOG5LRzQHPs8JguPs7FG2qfEGANBHdqWnGAXlsB9abG/Lwip3+OiE6piqL268cu9sVqWzinaKeAPfTAgMBAAECggEAYp47AKl8XN0VMrmFzi+K0GI9cev+bDVMlEjBS+JZdCsuax05OgDcLxi24HtnekucYjjzhMeoBkajjJcE5HY8BUTkVQ3kddfwiso6Y7plngH5K2gMyNB5+cXOURHFbMx1MJv6f3GXUtmX2P54n5VRV9VeoSwY/dhbifBje4abqEOjEZWFSHUrIFYAEXJfc9oRIxEApAKOlCFnBODS4XfZQl/WYME3Sh5OIHoJXYOLeb9IwKZbfc8lbu+OJljqbMVgp0ANnklxlJz+OC5x1VDZBTCVCdU41M1rP5HmcY5P5NXtlDmKMGD7TS0ScS1MvW7zPeuzFrNMQpdxZukN7lXP4QKBgQDUNqdzESrK59BBSBWjQA5OBMQ0uBcDaz83fP8VFi2zCwxv0EweeARCbbUaCJPqhwWqvX4dTlLYE/bNd+5ZwyYpYuqECnvQXaqrk/ilXwzW21tVVDs30d+K7iZLXc7LE0KcitblQi3a0zQIHxK3i8Yqe6zsepDOhGe2+KfgbyeOeQKBgQC6uqbBHWeKy4LWx37+6Uw3olLZ5n4CEFj+XsIWm5VLoObj/0qoldYA0LvfCsyWdxh9DS6AeHlu89/Q4yy40WELBlMsh6xIht8TLrGcEhGZlYSHyHLaVRwX9sHMUKn9sxDCmcGlyaArVCLaZ3aXS1som/q2gOw2EE7kgitKYlP1qwKBgQCnc0zkJhqvKwYQTRIN1CC1fVtZAh3eq8ShQzeAaxS6X7B7llBKBofN5wVtOnGFMO/Oz2FxPHlwWSh47vaby7oUFCJa+G7EdvSSGNutmffmngZOqX1kyW1sEgkTlTz0zwjTv1ZcZsEKF3fKJMK/JELFCPvlSb1zx9SS81h8Zxk+eQKBgBDyd8f3HzqdldZgbMo96kjyVoqoPK97RFhxyTO8EyS2Cb6PoPsD/lQ8nZB0rnTJglN3Ks1GId9MCvguZhSwDBqwDERyW9SJSR24ky4TBb3msAySyLnAF7ilEJm2iHcaFV9UDRs63WqRTEh+M2kv0aox3eNKeCW//SkIF1285tnZAoGAHI/Jb912yWh9wBoj24pCxTUuxn+eANkSbbZ1xXA301vB7HBvT7TXpJZir5pP3VSmSlVukp7LLDP7c04sUiXErpC1h5QBt3PPzs3DpSEtKkGKMtDuN39QRFY0y1SHkRGBRvNIr10XkM1FuzmJuqbYqkPvlJifQekVB9E82Q5AmTY=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUiM/nSxYOLqpsigTPtFS3rlwXE4rM0U+e84+vmev0qkLR0kx0dT9VWlLxf77MG0GCFxXFsetgcADV6DslVyYssXcKZRiC/l9qPj9joi89tDziPrPjtRo5odAevOBo6rMeTSM2ccVO+pgd0X9Un19KgDLveHExBwuGzxr0K/8Jddhifg+4iiZxvhzdkwOKe5qq/ubF+zvqlr/DYZKtDMuyCbGIG31p5njAWYXlesMaSbFi5SNdOXZ+71+1bJwH7nqWMggzgAchFXzAycIDlnDtGXt/xMy9DZe5vKeN7VJyf0HTXQXJxyvYY7fNHI41osZW1CiYS6M7V2gVe7jOh6CwIDAQAB";


	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://k3hrxb.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://k3hrxb.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";


	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

