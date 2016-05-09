package com.xnx3.j2ee.shiro;

import java.util.List;

import com.xnx3.j2ee.bean.PermissionTree;
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.User;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * @author 管雷鸣
 *
 */
public class ActiveUser {
	
	private User user;	//用户信息
	private List<Permission> permissions;// 拥有的权限
	private List<PermissionTree> permissionTreeList;	//入口列表菜单树
	
	/**
	 * 入口列表菜单树
	 * @return
	 */
	public List<PermissionTree> getPermissionTreeList() {
		return permissionTreeList;
	}
	public void setPermissionTreeList(List<PermissionTree> permissionTreeList) {
		this.permissionTreeList = permissionTreeList;
	}

	/**
	 * 拥有的权限
	 * @return
	 */
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	/**
	 * 用户信息
	 * @return
	 */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ActiveUser [user=" + user + ", permissions=" + permissions
				+ ", permissionTreeList=" + permissionTreeList + "]";
	}
	
}
