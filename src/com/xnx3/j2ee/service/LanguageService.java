package com.xnx3.j2ee.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 语言
 * @author 管雷鸣
 */
public interface LanguageService {

	/**
	 * 设置当前使用的语言包，语言包位于 src 下的 language.xml 文件，可以在其中随意增加其他语言，格式同 chinese 节点
	 * <br/>会自动将此加入Cookies，下次打开页面时，会自动将此语言包设为默认语言包
	 * @param languagePageName 语言包名字，位于 language.xml 的一级节点名字，如现有的 chinese 、 english
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	public void setCurrentLanguagePage(String languagePageName,HttpServletRequest request,HttpServletResponse response);

}
