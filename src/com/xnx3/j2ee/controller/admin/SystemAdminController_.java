package com.xnx3.j2ee.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.entity.System;
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.generateCache.Message;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.service.RoleService;
import com.xnx3.j2ee.service.SystemService;
import com.xnx3.j2ee.servlet.InitServlet;
import com.xnx3.j2ee.controller.BaseController;

/**
 * 系统管理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController_ extends BaseController {

	@Resource
	private PostClassService postClassService;
	
	@Resource
	private SystemService systemService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 首页
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminSystemIndex")
	@RequestMapping("index")
	public String index(Model model){
		List<com.xnx3.j2ee.entity.System> systemList = systemService.findByListshow(com.xnx3.j2ee.entity.System.LISTSHOW_SHOW);
		model.addAttribute("systemList", systemList);
		return "/iw/admin/system/index";
	}
	
	/**
	 * 生成所有缓存
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminSystemGenerateAllCache")
	@RequestMapping("generateAllCache")
	public String generateAllCache(Model model){
		new Bbs().postClass(postClassService.findAll());
		new Message().state();
		
		logService.insert("ADMIN_SYSTEM_GENERATEALLCACHE");
		return success(model, "已生成所有缓存", "admin/system/index.do");
	}
	
	/**
	 * 用户注册后自动拥有的一个权限
	 * @param value 值
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminSystemUserRegRole")
	@RequestMapping("userRegRole")
	public String userRegRole(
			@RequestParam(value = "value", required = false) String value,
			Model model){
		com.xnx3.j2ee.entity.System system = new System();
		List<com.xnx3.j2ee.entity.System> systemList = systemService.findByName("USER_REG_ROLE");
		if(systemList!=null&&systemList.size()==1){
			system=systemList.get(0);
		}else{
			system.setName("USER_REG_ROLE");
			system.setDescription("用户注册后的权限");
		}
		
		//是否时修改提交的页面
		if(value!=null&&value.length()>0){
			//保存页面
			system.setValue(value);
			systemService.save(system);
			
			logService.insert("ADMIN_SYSTEM_REG_ROLE");
			Global.system.put("USERREG_ROLE", value);
			return success(model, "保存成功", "admin/system/index.do");
		}else{
			//编辑页面
			List<Role> roleList = roleService.findAll();	//所有权限
			
			model.addAttribute("system", system);
			model.addAttribute("roleList", roleList);
			return "/iw/admin/system/userRegRole";
		}
	}

	/**
	 * 修改system表的单个项目
	 * @param name 名字
	 * @param value 值
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminSystemEditSystem")
	@RequestMapping("editSystem")
	public String editSystem(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "value", required = false) String value,
			Model model){
		com.xnx3.j2ee.entity.System system = new System();
		
		System systemWhere = new System();
		systemWhere.setListshow(com.xnx3.j2ee.entity.System.LISTSHOW_SHOW);
		systemWhere.setName(name);
		List<com.xnx3.j2ee.entity.System> systemList = systemService.findByExample(systemWhere);
		if(systemList!=null&&systemList.size()==1){
			system=systemList.get(0);
		}else{
			return error(model, "要修改的项不存在！");
		}
		
		//是否是修改提交的页面
		if(value!=null&&value.length()>0){
			//保存页面
			system.setValue(value);
			systemService.save(system);
			
			/***更新内存数据****/
			Global.system.clear();
			List<com.xnx3.j2ee.entity.System> list = systemService.findAll();
			for (int i = 0; i < list.size(); i++) {
				com.xnx3.j2ee.entity.System s = list.get(i);
				Global.system.put(s.getName(), s.getValue());
			}
			
			logService.insert("ADMIN_SYSTEM_EDITSYSTEM", system.getName());
			return success(model, "保存成功", "admin/system/index.do");
		}else{
			//编辑页面
			model.addAttribute("system", system);
			return "/iw/admin/system/system";
		}
	}
	
	
}
