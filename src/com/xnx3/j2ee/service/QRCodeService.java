package com.xnx3.j2ee.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二维码相关
 * @author 管雷鸣
 */
public interface QRCodeService {
	
	/**
	 * 在当前页面上输出二维码。使用如：
	 * <pre>
	 * public void test(HttpServletResponse response) throws IOException{
	 *		qRCodeService.showQRCodeForPage("http://www.xnx3.com", response);
	 * }
	 * </pre>
	 * @param content 二维码扫描后的内容，若网址，要写上http://
	 * @param response
	 */
	public void showQRCodeForPage(String content, HttpServletResponse response);
	
}
