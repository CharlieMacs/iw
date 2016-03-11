package com.xnx3.j2ee.controller.admin;

import java.util.Date;
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
public class SystemAdminController extends BaseController {

	@Resource
	private PostClassService postClassService;
	
	@Resource
	private SystemService systemService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@RequiresPermissions("adminSystemIndex")
	@RequestMapping("index")
	public String index(Model model){
		List<com.xnx3.j2ee.entity.System> systemList = systemService.findByListshow(com.xnx3.j2ee.entity.System.LISTSHOW_SHOW);
		model.addAttribute("systemList", systemList);
		return "/admin/system/index";
	}
	
	/**
	 * 生成所有缓存
	 * @return
	 */
	@RequiresPermissions("adminSystemGenerateAllCache")
	@RequestMapping("generateAllCache")
	public String generateAllCache(Model model){
		new Bbs().postClass(postClassService.findAll());
		new Message().state();
//		new Log().type(); 
		
		com.xnx3.j2ee.entity.Log log = new com.xnx3.j2ee.entity.Log();
		log.setAddtime(new Date());
		log.setType(com.xnx3.j2ee.entity.Log.typeMap.get("ADMIN_SYSTEM_GENERATEALLCACHE"));
		log.setUserid(getUser().getId());
		logService.save(log);
		
		return success(model, "已生成所有缓存", "admin/system/index.do");
	}
	
	/**
	 * 用户注册后自动拥有的一个权限
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
			
			com.xnx3.j2ee.entity.Log log = new com.xnx3.j2ee.entity.Log();
			log.setAddtime(new Date());
			log.setType(com.xnx3.j2ee.entity.Log.typeMap.get("ADMIN_SYSTEM_REG_ROLE"));
			log.setUserid(getUser().getId());
			logService.save(log);
			
			Global.system.put("USERREG_ROLE", value);
			return success(model, "保存成功", "admin/system/index.do");
		}else{
			//编辑页面
			List<Role> roleList = roleService.findAll();	//所有权限
			
			model.addAttribute("system", system);
			model.addAttribute("roleList", roleList);
			return "/admin/system/userRegRole";
		}
	}

	/**
	 * 修改system表的单个项目
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
			new InitServlet();	//重新初始化数据
			
			com.xnx3.j2ee.entity.Log log = new com.xnx3.j2ee.entity.Log();
			log.setAddtime(new Date());
			log.setType(com.xnx3.j2ee.entity.Log.typeMap.get("ADMIN_SYSTEM_EDITSYSTEM"));
			log.setUserid(getUser().getId());
			log.setValue(system.getName());
			logService.save(log);
			
			return success(model, "保存成功", "admin/system/index.do");
		}else{
			//编辑页面
			model.addAttribute("system", system);
			return "/admin/system/system";
		}
	}
	
	
}
