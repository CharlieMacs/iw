package com.xnx3.j2ee.util;

import java.io.UnsupportedEncodingException;
import com.xnx3.Lang;

/**
 * 发送短信类
 * <br/>短信平台：<a href="http://www.sioo.com.cn/">www.sioo.com.cn</a>
 * @author 管雷鸣
 *
 */
public class SendPhoneMsgUtil {
	/********* 以下需配置 ********/
	private final static String UID="80";	//用户ID
	private final static String COMPANYCODE = "sd...";	//企业代码
	private final static String PASSWOED = "sd...";	//登录密码
	/********* 以上需配置 ********/
	
	private final static String requestIp = "210.5.158.31";	//api请求的ip地址
	private static String passwordMd5;					//MD5加密后的代码＋密码
	static{
		passwordMd5 = MD5Util.MD5(COMPANYCODE+PASSWOED);
	}
	
	public static void main(String[] args) {
		System.out.println(new SendPhoneMsgUtil().send("13011658091", "testa"+PASSWOED));
	}
	
	/**
	 * 发送短信
	 * @param phone 发送到的手机号
	 * @param content 发送内容，编码为UTF－8编码，70个文字以内，超过70个字会发送多条信息
	 * @return String <li>返回空字符null，为发送成功
	 * 				  <li>返回字符串，非null，则发送失败，返回出错信息
	 */
	public static String send(String phone,String content){
		String result=null;
		
		try {
			content=java.net.URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		com.xnx3.net.HttpUtil httpUtil=new com.xnx3.net.HttpUtil();
		String url="http://"+requestIp+":9011/hy?uid="+UID+"&auth="+passwordMd5+"&mobile="+phone+"&msg="+content+"&encode=utf-8&expid=0";
		String requestResult=httpUtil.get(url).getContent();
		if(requestResult!=null&&requestResult.split(",")[0].equals("0")){
			result=null;
		}else{
			//send failure
			if(requestResult.indexOf("-")>0){
				int state=Lang.stringToInt(requestResult, 0);
				switch (state) {
					case 0:
						result=requestResult;
						break;
					case -1: 
						result="签权失败";
						break;
					case -2:
						result="未检索到被叫号码";
						break;
					case -3:
						result="被叫号码过多";
						break;
					case -4:
						result="内容未签名";
						break;
					case -5:
						result="内容过长";
						break;
					case -6:
						result="余额不足";
						break;
					case -7:
						result="暂停发送";
						break;
					case -8:
						result="保留";
						break;
					case -9:
						result="定时发送时间格式错误";
						break;
					case -10:
						result="下发内容为空";
						break;
					case -11:
						result="账户无效";
						break;
					case -12:
						result="Ip地址非法";
						break;
					case -13:
						result="操作频率快";
						break;
					case -14:
						result="'操作失败";
						break;
					case -15:
						result="拓展码无效(1-999)";
						break;
				default:
					result="未知错误";
					break;
				}
			}else{
				result=requestResult;
			}
		}
		
		httpUtil=null;
		return result;
	}
	
}