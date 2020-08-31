package util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CrowdUtil {

	/*** 专门负责上传文件到 OSS 服务器的工具方法
	 * @param endpoint OSS 参数
	 * @param accessKeyId OSS 参数
	 * @param accessKeySecret OSS 参数
	 * @param inputStream 要上传的文件的输入流
	 * @param bucketName OSS 参数
	 * @param bucketDomain OSS 参数
	 * @param originalName 要上传的文件的原始文件名
	 * @return 包含上传结果以及上传的文件在 OSS 上的访问路径 */
	public static ResultEntity<String> uploadFileToOss(
			String endpoint,
			String accessKeyId,
			String accessKeySecret,
			InputStream inputStream,
			String bucketName,
			String bucketDomain,
			String originalName) {
		// 创建 OSSClient 实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		// 生成上传文件的目录
		String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// 生成上传文件在 OSS 服务器上保存时的文件名
		// 原始文件名：beautfulgirl.jpg
		// 生成文件名：wer234234efwer235346457dfswet346235.jpg
		// 使用 UUID 生成文件主体名称
		String fileMainName = UUID.randomUUID().toString().replace("-", "");
		// 从原始文件名中获取文件扩展名
		String extensionName = originalName.substring(originalName.lastIndexOf("."));
		// 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
		String objectName = folderName + "/" + fileMainName + extensionName;
		try {
			// 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
			PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
			// 从响应结果中获取具体响应消息
			ResponseMessage responseMessage = putObjectResult.getResponse();
			// 根据响应状态码判断请求是否成功
			if (responseMessage == null) {
				// 拼接访问刚刚上传的文件的路径
				String ossFileAccessPath = bucketDomain + "/" + objectName;
				// 当前方法返回成功
				return ResultEntity.successWithData(ossFileAccessPath);
			} else {
				// 获取响应状态码
				int statusCode = responseMessage.getStatusCode();
				// 如果请求没有成功，获取错误消息
				String errorMessage = responseMessage.getErrorResponseAsString();
				// 当前方法返回失败
				return ResultEntity.failed(" 当 前 响 应 状 态 码 =" + statusCode + " 错 误 消 息 =" + errorMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 当前方法返回失败
			return ResultEntity.failed(e.getMessage());
		} finally {
			if (ossClient != null) {
				// 关闭
				ossClient.shutdown();
			}
		}
	}

	/*public static void main(String[] args) throws FileNotFoundException {

		FileInputStream inputStream = new FileInputStream("aaa.jpg");

		ResultEntity<String> resultEntity = uploadFileToOss("http://oss-cn-beijing.aliyuncs.com",
				"LTAI4GF6EhZwnVKpV1b9Rbcp",
				"QqQx25KOpmxUYTNhRVqjuqjLy83Jcj",
				inputStream,
				"kong-scwproject-2020-8-29",
				"http://kong-scwproject-2020-8-29.oss-cn-beijing.aliyuncs.com",
				"aaa.jgp");
		System.out.println(resultEntity);
	}

	@Test
	public void test() throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream("aaa.jpg");

		ResultEntity<String> resultEntity = uploadFileToOss("http://oss-cn-beijing.aliyuncs.com",
				"LTAI4GF6EhZwnVKpV1b9Rbcp",
				"QqQx25KOpmxUYTNhRVqjuqjLy83Jcj",
				inputStream,
				"kong-scwproject-2020-8-29",
				"http://kong-scwproject-2020-8-29.oss-cn-beijing.aliyuncs.com",
				"aaa.jpg");
		System.out.println(resultEntity);
	}*/

	/**
	 * 给远程第三方短信接口,验证码发送到用户的手机上
	 *
	 * @param phoneNum //接收验证码的手机号
	 * @param appCode  //用来调用第三方短信api的appCode
	 * @param sign     //签名
	 * @param skin     //模板
	 * @param host		//短信调用url地址
	 * @param path		//具体发送短信的功能地址
	 * @return 返回调用结果是否成功,
	 */
	public static ResultEntity<String> sendShortMessage(String phoneNum, String appCode, String sign, String skin,String host,String path) {
		//String host = "https://feginesms.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
		//String path = "/codeNotice";// 【2】后缀
		//登录到阿里云进入控制台,找到购买短信接口的appcode
		String appcode = appCode; // 【3】开通服务后 买家中心-查看AppCode
		//封装其他参数
		//签名编号
		//String sign = "175622"; // 【4】请求参数，详见文档描述
		//模板编号
		//String skin = "10"; // 【4】请求参数，详见文档描述
		//生成验证码
		StringBuilder builder = new StringBuilder();
		for (int i= 0;i<4;i++){
			int random = (int)(Math.random()*10);
			builder.append(random);
		}
		//发送的验证码,也就是模板中动态变化的
		String param = builder.toString(); // 【4】请求参数，详见文档描述
		//收短信的手机号
		//String phone = "15709657599"; // 【4】请求参数，详见文档描述
		String urlSend = host + path + "?param=" + param +"&phone="+phoneNum +"&sign="+sign +"&skin="+skin;  // 【5】拼接请求链接
		try {
			URL url = new URL(urlSend);
			HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
			httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);// 格式Authorization:APPCODE
			// (中间是英文空格)
			int httpCode = httpURLCon.getResponseCode();
			if (httpCode == 200) {
				//String json = read(httpURLCon.getInputStream());
				//操作成功,生成的验证码返回
				return ResultEntity.successWithData(param);
			} else {
				Map<String, List<String>> map = httpURLCon.getHeaderFields();
				String error = map.get("X-Ca-Error-Message").get(0);
				if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
					return ResultEntity.failed("AppCode错误 ");
				} else if (httpCode == 400 && error.equals("Invalid Url")) {
					return ResultEntity.failed("请求的 Method、Path 或者环境错误");
				} else if (httpCode == 400 && error.equals("Invalid Param Location")) {
					return ResultEntity.failed("参数错误");
				} else if (httpCode == 403 && error.equals("Unauthorized")) {
					return ResultEntity.failed("服务未被授权（或URL和Path不正确");
				} else if (httpCode == 403 && error.equals("Quota Exhausted")) {
					return ResultEntity.failed("套餐包次数用完");
				} else {
					return ResultEntity.failed(error);
				}
			}

		} catch (MalformedURLException e) {
			return ResultEntity.failed("URL格式错误");
		} catch (Exception e) {
			// 打开注释查看详细报错异常信息
			// e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}


	/*
	 * 读取返回结果
	 */
	private static String read(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes(), "utf-8");
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	/**
	 * 对明文字符串进行MD5加密
	 * @param source
	 * @return 加密结果
	 */
	public static String m5(String source) {
		// 1.判断source是否有效
		if (source == null || source.length() == 0) {
			//2.如果不是有效的字符串抛出异常
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}
		try {
			//3.获取MessageDigest对象
			String algorithm="md5";
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			
			//4.获取明文字符串对应的字节数组
			byte[] input = source.getBytes();
			
			//5.执行加密
			byte[] output = messageDigest.digest(input);
			
			//6.创建BigInteger对象
			int signum=1;
			BigInteger bigInteger = new BigInteger(signum,output);
			
			//7.按照16进制将bigInteger的值转换为字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix);
			
			return encoded;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断当前请求是否为Ajax请求
	 * 
	 * @param request
	 *            请求对象
	 * @return true：当前请求是Ajax请求 false：当前请求不是Ajax请求
	 */
	public static boolean judgeRequestType(HttpServletRequest request) {

		// 1.获取请求消息头
		String acceptHeader = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");

		// 2.判断
		return (acceptHeader != null && acceptHeader.contains("application/json"))
				||
				(xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
	}

}
