package com.xnx3.j2ee.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 用户User
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private GlobalService globalService;
	
	/**
	 * 删除用户
	 * @return
	 */
	@RequiresPermissions("adminUserDelete")
	@RequestMapping("deleteUser")
	public String deletePost(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			User u = userService.findById(id);
			if(u!=null){
				userService.delete(u);
			}
		}
		
		return "redirect:/admin/user/list.do";
	}
	
	/**
	 * 用户列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminUserList")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"username","email","nickname","phone","id=","regtime(date:yyyy-MM-dd hh:mm:ss)>"};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("user", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		List<User> list = globalService.findBySqlQuery("SELECT * FROM user", where, page,User.class);

		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/admin/user/list";
	}
	
}
