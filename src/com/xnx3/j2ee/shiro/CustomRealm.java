package com.xnx3.j2ee.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import com.xnx3.j2ee.DBConfigure;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.PermissionService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.bean.PermissionTree;

/**
 * 自定义realm
 * @author 管雷鸣
 *
 */
public class CustomRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private LogService logService;
	
	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}
	
	//realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String username = (String) token.getPrincipal();
        if (username != null && !"".equals(username)) {  
        	User user = null;
    		try {
    			List<User> l = userService.findByUsername(username);
    			if(l!=null){
    				user = l.get(0);
    				user.setLasttime(DateUtil.timeForUnix10());
//    				user.setLastip(request.getRemoteAddr());
    				userService.save(user);
    				
    				Log log = new Log();
    				log.setAddtime(new Date());
    				log.setType(DBConfigure.LOG_USER_LOGIN_SUCCESS);
    				log.setUserid(user.getId());
    				logService.save(log);
    			}
    		} catch (Exception e1) {
    			e1.printStackTrace();
    		}
            if (user != null) {  
            	ActiveUser activeUser = new ActiveUser();
            	activeUser.setUser(user);
   
            	//根据用户id查询权限url
        		List<Permission> permissions = permissionService.getPermissionByUserId(user.getId());
        		activeUser.setPermissions(permissions);
        		
    			//转换为树状集合
    			List<PermissionTree> permissionTreeList = new ShiroFunc().PermissionToTree(new ArrayList<Permission>(), permissions);	
        		activeUser.setPermissionTreeList(permissionTreeList);
    			
        		//将activeUser设置simpleAuthenticationInfo
        		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
        				activeUser, user.getPassword(),ByteSource.Util.bytes(user.getSalt()), this.getName());
        		return simpleAuthenticationInfo;
            }  
        }  
  
        return null;  
	}
	
	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUser activeUser =  (ActiveUser) principals.getPrimaryPrincipal();
		
		//根据身份信息获取权限信息
		//从数据库获取到权限数据
		List<Permission> permissionList = null;
		try {
			permissionList = activeUser.getPermissions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//单独定一个集合对象 
		List<String> permissions = new ArrayList<String>();
		if(permissionList!=null){
			for(Permission permission:permissionList){
				//将数据库中的权限标签 符放入集合
				permissions.add(permission.getPercode());
			}
		}
				
		//查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}
	
	
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
