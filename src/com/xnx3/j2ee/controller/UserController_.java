package com.xnx3.j2ee.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.ActionLogCache;
import com.xnx3.j2ee.func.AttachmentFile;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.net.MailUtil;

/**
 * 用户User的相关操作
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/user")
public class UserController_ extends BaseController {
	@Resource
	private MessageService messageService;
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	/**
	 * 用户个人中心
	 */
	@RequiresPermissions("userIndex")
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		ActionLogCache.insert(request, "个人中心");
		model.addAttribute("user", getUser());
		return "iw/user/index";
	}
	
	/**
	 * 上传头像
	 * @param file 上传的头像文件，input type="file" name="head"
	 */
	@RequiresPermissions("userUploadHead")
	@RequestMapping("/uploadHead")
	public String uploadHead(@RequestParam("head") MultipartFile file,Model model, HttpServletRequest request){
		//上传头像，并进行超过宽度200px的图像进行压缩
		UploadFileVO v = userService.updateHeadByOSS(request, "head", 400);
		if(v.getResult() == UploadFileVO.SUCCESS){
			ActionLogCache.insert(request, "上传头像", "成功，头像文件路径："+Global.get("USER_HEAD_PATH")+v.getPath());
			return success(model, "上传成功");
		}else{
			ActionLogCache.insert(request, "上传头像", "失败："+v.getInfo());
			return error(model, v.getInfo());
		}
	}
	
	/**
	 * 修改昵称,传入nickname
	 * @param user {@link User}
	 */
	@RequiresPermissions("userUpdateNickName")
	@RequestMapping("updateNickName")
	public String updateNickName(HttpServletRequest request,Model model){
		BaseVO baseVO = userService.updateNickname(request);
		if(baseVO.getResult() == BaseVO.FAILURE){
			ActionLogCache.insert(request, "修改昵称", "失败："+baseVO.getInfo());
			return error(model, baseVO.getInfo());
		}else{
			ActionLogCache.insert(request, "修改昵称");
			return success(model, "修改成功！");
		}
	}
	
	/**
	 * 修改密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	@RequiresPermissions("userUpdatePassword")
	@RequestMapping("updatePassword")
	public String updatePassword(HttpServletRequest request, String oldPassword,String newPassword,Model model){
		if(oldPassword==null){
			ActionLogCache.insert(request, "修改密码", "失败：未输入密码");
			return error(model, "请输入旧密码");
		}else{
			User uu=sqlService.findById(User.class, getUser().getId());
			//将输入的原密码进行加密操作，判断原密码是否正确
			
			if(new Md5Hash(oldPassword, uu.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString().equals(uu.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					ActionLogCache.insert(request, "修改密码", "成功");
					return success(model, "修改成功");
				}else{
					ActionLogCache.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(model, vo.getInfo());
				}
			}else{
				ActionLogCache.insert(request, "修改密码", "失败：原密码错误");
				return error(model, "原密码错误！");
			}
		}
	}

	/**
	 * 用户自己获取自己的邀请注册网址页面
	 */
	@RequiresPermissions("userInvite")
	@RequestMapping("invite")
	public String invite(HttpServletRequest request, Model model){
		ActionLogCache.insert(request, "获取邀请码注册网址");
		model.addAttribute("user", getUser());
		return "iw/user/invite";
	}
	
	/**
	 * 通过邮件邀请用户注册
	 * @param email 要发送的邮件地址
	 * @param text	发送的邮件内容
	 * @param model {@link Model}
	 */
	@RequiresPermissions("userInviteEmail")
	@RequestMapping("inviteEmail")
	public String inviteEmail(
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "text", required = true) String text,
			Model model, HttpServletRequest request){
		
		//验证邮箱
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			MailUtil.sendMail(email, "邀请", "内容");
			
			ActionLogCache.insert(request, "邮件邀请用户注册",email);
			return success(model, "邀请邮件发送完毕", "user/info.do");
		}else{
			ActionLogCache.insert(request, "邮件邀请用户注册","出错：不是合法邮箱："+email);
			return error(model, "请填写合法邮箱");
		}
	}
	
	/**
	 * 用户退出，页面跳转提示。
	 */
	@RequestMapping("logout")
	public String logout(Model model, HttpServletRequest request){
		ActionLogCache.insert(request, "注销登录");
		userService.logout();
		return success(model, "注销登录成功", "login.do");
	}
	
	/**
	 * 我邀请注册的用户列表，我的1级下线，直属下线
	 */
	@RequestMapping("inviteList")
	public String inviteList(HttpServletRequest request,Model model){
		List<User> list = sqlService.findBySqlQuery("SELECT * FROM user WHERE referrerid = "+getUserId()+" ORDER BY id DESC", User.class);
		
		ActionLogCache.insert(request, "查看我邀请的用户，当前1级下线人数："+list.size()+"人");
		model.addAttribute("userList", list);
		model.addAttribute("size", list.size());
		return "iw/user/inviteList";
	}
}
