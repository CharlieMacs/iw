package com.xnx3.j2ee.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.Friend;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.ActionLogCache;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.FriendListVO;

/**
 * 好友
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/friend")
public class FriendController_ extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	
	/**
	 * 好友功能的首页
	 * @return View
	 */
	@RequiresPermissions("friendIndex")
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		ActionLogCache.insert(request, "好友首页");
		return "iw/friend/index";
	}

	/**
	 * 传入用户名/邮箱，根据此来添加好友
	 * @param param 用户名/邮箱 
	 */
	@RequiresPermissions("friendAdd")
	@RequestMapping("/add")
	public String add(String param ,Model model, HttpServletRequest request){
		param = StringUtil.filterXss(param);
		
		//判断是用户名还是邮箱,来定义查询用户名还是邮箱
		List<User> findList = sqlService.findByProperty(User.class, param.indexOf("@")>0? "email":"username", param);
		
		User user = getUser();	//当前登录用户
		if(findList.size()==0){
			ActionLogCache.insert(request, "添加好友", "失败，"+user.getUsername()+"添加好友“"+param+"”失败，因为要加的用户不存在");
			return error(model, "要加的用户不存在！");
		}else{
			User otherUser=findList.get(0);	//要加的目标用户
			
			//继而查询是否曾添加过此人了，避免数据重复
			//List<Friend> friendList=friendDao.getSession().createQuery("SELECT * FROM friend WHERE self="+user.getId()+" AND other ="+otherUser.getId()).list();
			Friend selectFriend=new Friend();
			selectFriend.setId(user.getId());
			selectFriend.setOther(otherUser.getId());
			List<Friend> friendList=sqlService.findByExample(selectFriend);
//			List<Friend> friendList=friendService.findByExample(selectFriend);
			
			if(friendList.size()>0){
				ActionLogCache.insert(request, otherUser.getId(), "添加好友", "失败，"+user.getUsername()+"添加"+otherUser.getUsername()+"好友失败，因为已经是好友了");
				return error(model, "已经加过此人了，无需重复添加！");
			}else{
				Friend friend=new Friend();
				friend.setOther(otherUser.getId());
				friend.setSelf(user.getId());
				sqlService.save(friend);
				
				ActionLogCache.insert(request, otherUser.getId(), "添加好友", "成功，"+user.getUsername()+"添加"+otherUser.getUsername()+"为好友");
				return success(model, "添加成功！");
			}
		}
	}
	
	/**
	 * 根据好友的userid删除好友
	 * @param id 好友的userid
	 */
	@RequiresPermissions("friendDelete")
	@RequestMapping("/delete")
	public String delete(int id ,Model model, HttpServletRequest request){
		User user = getUser();
		
		Friend friendFind=new Friend();
		friendFind.setSelf(user.getId());
		friendFind.setOther(id);
		
		List<Friend> list= sqlService.findByExample(friendFind);
//		List<Friend> list= friendService.findByExample(friendFind);
		if(list.size()==0){
			ActionLogCache.insert(request, "删除好友", "失败，"+user.getUsername()+"删除userid为"+id+"的好友失败，因为要删除的用户不存在");
			return error(model, "要删除的用户不存在！");
		}else{
			Friend friend = list.get(0);
			sqlService.delete(friend);
			
			ActionLogCache.insert(request, "删除好友", "成功，"+user.getUsername()+"删除userid为"+id+"的好友");
			return success(model, "删除成功");
		}
	}
	
	/**
	 * 我的好友列表
	 * @return {@link FriendListVO}
	 */
	@RequiresPermissions("friendList")
	@RequestMapping("/list")
	@ResponseBody
	public FriendListVO list(HttpServletRequest request){
		FriendListVO friendListVO = new FriendListVO();
		friendListVO.setList(sqlService.findByProperty(Friend.class, "self", getUser().getId()));
//		friendListVO.setList(friendService.findBySelf(getUser().getId()));
		
		ActionLogCache.insert(request, "查看好友列表");
		return friendListVO;
	}
}
