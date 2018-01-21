package com.xnx3.j2ee.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.controller.BaseController;

/**
 * 管理后台首页
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/admin/index")
public class AdminIndexController_ extends BaseController{
	
	/**
	 * 管理后台首页
	 */
	@RequiresPermissions("adminIndexIndex")
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model){
		//登录成功后，管理后台的主题页面，默认首页的url
		String url = "admin/user/list.do"; 
		
		//这里可以根据不同的管理级别，来指定显示默认是什么页面
//		Func.isAuthorityBySpecific(getUser().getAuthority(), roleId);
		
		model.addAttribute("indexUrl", url);	//首页(欢迎页)url
		return "/iw/admin/index/index";
	}
	
	
}
