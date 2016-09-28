package com.xnx3.j2ee.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 文本合法性过滤
 * @author 管雷鸣
 */
public interface TxtFilterService {
	
	/**
	 * 判断该字符串中是否有违法字符，若有，返回true，
	 * @param request HttpServletRequest
	 * @param title 检测到文本中有违法字符，会将违法字符以邮件的形势发送出去。这里便是发送出去后的邮件标题，让接收者知道是那片文章出的问题
	 * @param url 疑似违规的邮件收到后，看到邮件，应该有个链接地址，点击后可以直接进入违规的那个页面详情，或者后台管理详情，快速定位到此违规内容。（可为空）
	 * @param text 要检测的文本
	 * @return 若有违规，则返回true
	 */
	public boolean filter(HttpServletRequest request, String title, String url, String text);
	
}
