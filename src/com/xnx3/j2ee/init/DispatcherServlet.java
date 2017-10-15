package com.xnx3.j2ee.init;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xnx3.j2ee.func.ActionLogCache;

/**
 * 重写 springmvc 的 DispatcherServlet ， 用来拦截404状态，自定义404页面
 * @author 管雷鸣
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet   {
	/**
	 * 404错误页面所在。
	 * 此404错误页面只是针对springmvc的转发进行拦截，其他像是图片，css等静态资源不会被此拦截，也就是其他静态资源还是使用默认的404页面
	 */
	public static String error404JspPath = "/WEB-INF/view/iw/404.jsp";
	
	/**
	 * 自定义404页面
	 */
	@Override
	protected void noHandlerFound(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionLogCache.insert(request, "404", request.getRequestURI());
		request.getRequestDispatcher(error404JspPath).forward(request, response);
	}
	
}
