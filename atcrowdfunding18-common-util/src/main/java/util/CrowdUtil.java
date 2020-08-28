package util;

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
import java.util.List;
import java.util.Map;


public class CrowdUtil {

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
