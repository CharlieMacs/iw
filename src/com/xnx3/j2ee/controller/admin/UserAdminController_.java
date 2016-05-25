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
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 用户管理
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController_ extends BaseController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private GlobalService globalService;
	
	/**
	 * 删除用户
	 * @param id 用户id，User.id
	 * @return View
	 */
	@RequiresPermissions("adminUserDelete")
	@RequestMapping("deleteUser")
	public String deleteUser(@RequestParam(value = "id", required = true) int id){
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
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminUserList")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
//		Sql sql = new Sql();
//		String[] column = {"username","email","nickname","phone","id=","regtime(date:yyyy-MM-dd hh:mm:ss)>"};
//		String where = sql.generateWhere(request, column, null);
//		int count = globalService.count("user", where);
//		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
//		page.setDefaultOrderBy("id_DESC");
//		List<User> list = globalService.findBySqlQuery("SELECT * FROM user", where, page,User.class);

		Sql sql = new Sql(request);
		sql.setSearchColumn(new String[]{"username","email","nickname","phone","id=","regtime(date:yyyy-MM-dd hh:mm:ss)>"});
		int count = globalService.count("user", sql.getWhere());
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		sql.setSelectFromAndPage("SELECT * FROM user", page);
		sql.setDefaultOrderBy("user.id DESC");
		List<User> list = globalService.findEntityBySql(sql, User.class);
		
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/iw/admin/user/list";
	}
	
	/**
	 * 用户详情
	 * @param id
	 * @return
	 */
	@RequiresPermissions("adminUserView")
	@RequestMapping("view")
	public String view(@RequestParam(value = "id", required = true) int id,Model model){
		User user = userService.findById(id);
		if(user == null){
			return error(model, "要查看的用户不存在");
		}
		
		if(user.getReferrerid()==null || user.getReferrerid()==0){
			model.addAttribute("referrer", "无邀请人");
		}else{
			model.addAttribute("referrer", "<a href='view.do?id="+user.getReferrerid()+"'>id:"+user.getReferrerid()+","+user.getNickname()+"</a>");
		}
		
		model.addAttribute("u", user);
		return "/iw/admin/user/view";
	}
	
	/**
	 * 冻结／解除冻结用户
	 * @param id {@link User}.id
	 * @param isfreeze 要更改的值
	 */
	@RequiresPermissions("adminUserUpdateFreeze")
	@RequestMapping("updateFreeze")
	public String updateFreeze(@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "isfreeze", required = true) int isfreeze,
			Model model){
		BaseVO baseVO = new BaseVO();
		if(isfreeze==User.ISFREEZE_FREEZE){
			baseVO = userService.freezeUser(id);
		}else if (isfreeze==User.ISFREEZE_NORMAL) {
			baseVO = userService.unfreezeUser(id);
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "未知参数！");
		}
		
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "操作成功！","admin/user/view.do?id="+id);
		}else{
			return error(model, "操作失败！");
		}
	}
}
