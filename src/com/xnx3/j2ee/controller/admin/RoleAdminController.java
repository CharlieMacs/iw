package com.xnx3.j2ee.controller.admin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.entity.RolePermission;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.entity.UserRole;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.PermissionService;
import com.xnx3.j2ee.service.RolePermissionService;
import com.xnx3.j2ee.service.RoleService;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.Lang;
import com.xnx3.j2ee.bean.PermissionTree;
import com.xnx3.j2ee.bean.RoleMark;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 权限管理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/role")
public class RoleAdminController extends BaseController {

	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private RolePermissionService rolePermissionService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserService userService;
	@Resource
	private GlobalService globalService;
	@Resource
	private LogService logService;
	/**
	 * 添加角色
	 * @return View
	 */
	@RequestMapping("addRole")
	@RequiresPermissions("adminRoleAddRole")
	public String addRole(){
		return "admin/role/role";
	}
	
	/**
	 * 编辑角色
	 * @param id 角色的id, Role.id
	 * @param model {@link Model} 
	 * @return View
	 */
	@RequestMapping("editRole")
	@RequiresPermissions("adminRoleEditRole")
	public String editRole(@RequestParam(value = "id", required = true) int id,Model model){
		if(id>0){
			Role role = roleService.findById(id);
			if(role!=null){
				model.addAttribute("role", role);
				return "admin/role/role";
			}
		}
		return "redirect:/admin/role/roleList.do";
	}
	
	/**
	 * 添加角色提交页
	 * @param role {@link Role}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("saveRole")
	@RequiresPermissions("adminRoleSaveRole")
	public String saveRole(Role role,Model model){
		roleService.save(role);
		logService.insert(role.getId(), "ADMIN_SYSTEM_ROLE_SAVE", role.getName());
		return success(model, "保存成功", "admin/role/roleList.do");
	}
	

	/**
	 * 删除角色
	 * @param id 角色id，Role.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("deleteRole")
	@RequiresPermissions("adminRoleDeleteRole")
	public String deleteRole(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			Role role = roleService.findById(id);
			if(role!=null){
				roleService.delete(role);
				logService.insert(role.getId(), "ADMIN_SYSTEM_ROLE_DELETE", role.getName());
				return success(model, "删除成功", "admin/role/roleList.do");
			}
		}
		return error(model, "删除失败");
	}
	
	/**
	 * 角色列表
	 * @param role {@link Role}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("roleList")
	@RequiresPermissions("adminRoleRoleList")
	public String roleList(Role role,Model model){
		List<Role> list = roleService.findAll(); 
		
		model.addAttribute("list", list);
		return "admin/role/roleList";
	}
	

	/**
	 * 添加permission
	 * @param parentId 所添加的资源的所属上级资源。如果是顶级资源，则为0
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("addPermission")
	@RequiresPermissions("adminRoleAddPermission")
	public String addPermission(
			@RequestParam(value = "parentId", required = true) int parentId,
			Model model){
		Permission parentPermission = permissionService.findById(parentId);
		String parentPermissionDescription = "顶级";
		if(parentPermission!=null){
			parentPermissionDescription = parentPermission.getDescription();
		}
		
		Permission permission = new Permission();
		permission.setParentId(parentId);
		
		model.addAttribute("permission", permission);
		model.addAttribute("parentPermissionDescription", parentPermissionDescription);
		return "admin/role/permission";
	}
	
	/**
	 * 编辑资源
	 * @param id 资源的id，Permission.id 
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("editPermission")
	@RequiresPermissions("adminRoleEditPermission")
	public String editPermission(@RequestParam(value = "id", required = true) int id,Model model){
		if(id>0){
			Permission permission = permissionService.findById(id);
			if(permission!=null){
				String parentPermissionDescription="顶级";
				if(permission.getParentId()>0){
					Permission parentPermission = permissionService.findById(permission.getParentId());
					parentPermissionDescription = parentPermission.getDescription();
				}
				
				model.addAttribute("permission", permission);
				model.addAttribute("parentPermissionDescription", parentPermissionDescription);
				return "admin/role/permission";
			}
		}
		return "redirect:/admin/role/permissionList.do";
	}
	
	/**
	 * Permission提交保存
	 * @param permission {@link Permission}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("savePermission")
	@RequiresPermissions("adminRoleSaveRole")
	public String savePermission(Permission permission,Model model){
		permissionService.save(permission);
		logService.insert(permission.getId(), "ADMIN_SYSTEM_PERMISSION_SAVE", permission.getName());
		return success(model, "保存成功", "admin/role/permissionList.do");
	}
	
	/**
	 * 删除资源Permission
	 * @param id 资源的id，Permission.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("deletePermission")
	@RequiresPermissions("adminRoleDeletePermission")
	public String deletePermission(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			Permission permission = permissionService.findById(id);
			if(permission!=null){
				permissionService.delete(permission);
				logService.insert(permission.getId(), "ADMIN_SYSTEM_PERMISSION_DELETE", permission.getName());
				return success(model, "删除成功", "admin/role/permissionList.do");
			}
		}
		
		return error(model, "删除失败");
	}
	
	
	/**
	 * 资源Permission列表
	 * @param permission {@link Permission}
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminRolePermissionList")
	@RequestMapping("permissionList")
	public String permissionList(Permission permission,HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"description","url","name","parent_id","percode"};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("permission", where);
		Page page = new Page(count, 1000, request);
		List<Permission> list = globalService.findBySqlQuery("SELECT * FROM permission", where, page,Permission.class);
		List<PermissionTree> permissionTreeList = new ShiroFunc().PermissionToTree(new ArrayList<Permission>(), list);
		
		model.addAttribute("page", page);
		model.addAttribute("list", permissionTreeList);
		return "admin/role/permissionList";
	}
	
	/**
	 * 编辑权限－资源关系
	 * @param id 角色id，Rold.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminRoleEditRolePermission")
	@RequestMapping("editRolePermission")
	public String editRolePermission(@RequestParam(value = "roleId", required = true) int roleId, Model model){
		if(roleId>0){
			List<Permission> myList = rolePermissionService.findPermissionByRoleId(roleId);	//选中的
			List<Permission> allList = permissionService.findAll();	//所有的
			//转换为树状集合
			List<PermissionTree> list = new ShiroFunc().PermissionToTree(myList, allList);	
			
			Role role = roleService.findById(roleId);
			
			model.addAttribute("role", role);
			model.addAttribute("list", list);
			return "admin/role/rolePermission";
		}
		return null;
	}

	/**
	 * 保存角色－资源设置
	 * @param roleId 角色id，Role.id
	 * @param permission 多选框的资源列表，如 1,2,3,4
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminRoleEditRolePermission")
	@RequestMapping("saveRolePermission")
	public String saveRolePermission(
			@RequestParam(value = "roleId", required = true) int roleId,
			@RequestParam(value = "permission", required = false) String permission,
			Model model){
		
		if(roleId==0){
			return error(model, "传入的编号不正确！");
		}
		List<RolePermission> myRolePermissionList = rolePermissionService.findByRoleId(roleId);		//此角色原先的权限
		
		/***增加资源***/
		String permissionArray[] = permission.split(",");		//此角色新编辑的权限
		for (int i = 0; i < permissionArray.length; i++) {
			int pid = Lang.stringToInt(permissionArray[i], 0);
			if(pid>0){
				boolean haveRP=false;	//是否数据库中已经存在了这条资源－角色纪录
				for (int j = 0; j < myRolePermissionList.size(); j++) {
					RolePermission myrp=myRolePermissionList.get(j);
					if(myrp.getPermissionid()==pid){
						haveRP=true;
						break;
					}
				}
				
				if(!haveRP){
					RolePermission rp = new RolePermission();
					rp.setRoleid(roleId);
					rp.setPermissionid(pid);
					rolePermissionService.save(rp);
				}
			}
		}
		
		/***删除资源，删除原先有的，新编辑后没有的***/
		for (int i = 0; i < myRolePermissionList.size(); i++) {
			RolePermission myrp=myRolePermissionList.get(i);
			
			boolean haveRP=false;	//是否数据库中有这条纪录，但是新提交修改的并没有这条纪录。默认为没有这条纪录
			for (int j = 0; j < permissionArray.length; j++) {
				int pid = Lang.stringToInt(permissionArray[j], 0);
				if(pid>0&&myrp.getPermissionid()==pid){
					haveRP=true;
					break;
				}
			}
			
			if(!haveRP){
				rolePermissionService.delete(myrp);
			}
		}
		logService.insert(roleId, "ADMIN_SYSTEM_ROLE_PERMISSION_SAVE", roleService.findById(roleId).getName());
		return success(model, "保存成功","admin/role/roleList.do");
	}
	

	/**
	 * 编辑用户－权限关系
	 * @param userid 用户id，User.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminRoleEditUserRole")
	@RequestMapping("editUserRole")
	public String editUserRole(@RequestParam(value = "userid", required = true) int userid, Model model){
		User user = userService.findById(userid);
		if(user!=null){
			List<Role> myList = userRoleService.findRoleByUserId(userid);	//用户自己有哪些角色
			List<Role> allList = roleService.findAll();	//所用权限
			
			//标记用户当前拥有的角色
			List<RoleMark> roleMarkList = new ArrayList<RoleMark>();
			for (int i = 0; i < allList.size(); i++) {
				Role role = allList.get(i);
				RoleMark roleMark = new RoleMark();
				roleMark.setRole(role);
				
				for (int j = 0; j < myList.size(); j++) {
					Role myRole = myList.get(j);
					if(myRole.getId()==role.getId()){
						roleMark.setSelected(true);
						break;
					}
				}
				roleMarkList.add(roleMark);
			}
			
			model.addAttribute("currentUser", user);
			model.addAttribute("list", roleMarkList);
			return "admin/role/userRole";
		}
		return null;
	}


	/**
	 * 保存用户－角色设置
	 * @param userid 用户id,User.id
	 * @param role 权限多选框提交列表，如 1,2,3,4,5
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminRoleEditUserRole")
	@RequestMapping("saveUserRole")
	public String saveUserRole(
			@RequestParam(value = "userid", required = true) int userid,
			@RequestParam(value = "role", required = false) String role,
			Model model){
		
		if(userid==0){
			return error(model, "传入的编号不正确");
		}
		List<UserRole> myUserRoleList = userRoleService.findByUserId(userid);	//此用户原先的权限
		
		/***增加资源***/
		String roleArray[] = role.split(",");		//此角色新编辑的权限
		for (int i = 0; i < roleArray.length; i++) {
			int rid = Lang.stringToInt(roleArray[i], 0);
			if(rid>0){
				boolean haveUR=false;	//是否数据库中已经存在了这条资源－角色纪录
				for (int j = 0; j < myUserRoleList.size(); j++) {
					UserRole myUR=myUserRoleList.get(j);
					if(myUR.getRoleid()==rid){
						haveUR=true;
						break;
					}
				}
				
				if(!haveUR){
					UserRole ur = new UserRole();
					ur.setRoleid(rid);
					ur.setUserid(userid);
					userRoleService.save(ur);
				}
			}
		}
		
		/***删除资源，删除原先有的，新编辑后没有的***/
		for (int i = 0; i < myUserRoleList.size(); i++) {
			UserRole myUR=myUserRoleList.get(i);
			
			boolean haveUR=false;	//是否数据库中有这条纪录，但是新提交修改的并没有这条纪录。默认为没有这条纪录
			for (int j = 0; j < roleArray.length; j++) {
				int rid = Lang.stringToInt(roleArray[j], 0);
				if(rid>0&&myUR.getRoleid()==rid){
					haveUR=true;
					break;
				}
			}
			
			if(!haveUR){
				userRoleService.delete(myUR);
			}
		}
		
		logService.insert(userid, "ADMIN_SYSTEM_USER_ROLE_SAVE");
		return success(model, "保存成功", "admin/user/list.do");
	}
	
}