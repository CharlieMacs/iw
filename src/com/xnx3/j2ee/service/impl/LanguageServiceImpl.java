package com.xnx3.j2ee.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.LanguageService;
import com.xnx3.j2ee.util.CookieUtil;

public class LanguageServiceImpl implements LanguageService {

	@Override
	public void setCurrentLanguagePage(String languagePageName,HttpServletRequest request,HttpServletResponse response) {
		//判断 language.xml 中是否有这个语言包
		if(Global.language.get(languagePageName) != null){
			Global.language_default = languagePageName;
			CookieUtil cookie = new CookieUtil(request, response, 999999);
			cookie.addCookie("language_default", languagePageName);
			request.getSession().setAttribute("", "");
		}else{
			System.out.println("language page name not find ! please check language.xml");
		}
	}

}
