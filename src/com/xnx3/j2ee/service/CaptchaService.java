package com.xnx3.j2ee.service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.media.CaptchaUtil;


/**
 * 验证码。每个验证码只能使用一次。使用后的验证码将会重新赋值
 * @author 管雷鸣
 *
 */
public interface CaptchaService {
	
	/**
	 * 显示图片验证码，直接创建出jpg格式图片输出到页面。可以在页面中直接调用<img>标签显示，调用了此方法的控制器
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 */
	public void showImage(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * 显示图片验证码，直接创建出jpg格式图片
	 * @param captchaUtil {@link CaptchaUtil} 可对其自定义验证码图片的属性、显示、格式等
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 */
	public void showImage(CaptchaUtil captchaUtil, HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 用户输入的验证码，与系统存储的进行比较，返回结果。忽略大小写
	 * @param inputCode 用户输入的验证码
	 * @param request HttpServletRequest 主要用于从其中获取Session
	 * @return {@link BaseVO}
	 */
	public BaseVO compare(String inputCode, HttpServletRequest request);
}