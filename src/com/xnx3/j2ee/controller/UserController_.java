package com.xnx3.j2ee.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.ActionLogCache;
import com.xnx3.j2ee.func.OSS;
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
	 * @return View
	 */
	@RequiresPermissions("userInfo")
	@RequestMapping("/info")
	public String userInfo(HttpServletRequest request){
		ActionLogCache.insert(request, "个人中心");
		return "iw/user/info";
	}
	
	/**
	 * 上传头像
	 * @param file
	 */
	public String uploadHead(@RequestParam("head") MultipartFile file,Model model, HttpServletRequest request){
		UploadFileVO v = OSS.uploadImage(Global.get("USER_HEAD_PATH"), file);
		if(v.getResult() == UploadFileVO.SUCCESS){
			ActionLogCache.insert(request, "上传头像", "成功，头像文件路径："+Global.get("USER_HEAD_PATH")+v.getPath());
			return success(model, "上传成功，文件路径："+v.getPath());
		}else{
			ActionLogCache.insert(request, "上传头像", "失败："+v.getInfo());
			return error(model, v.getInfo());
		}
	}
	
	/**
	 * 更改用户当前头像
	 * @param file {@link MultipartFile}上传的文件
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	@RequiresPermissions("userUploadHead")
	@RequestMapping("/uploadHead")
	public String uploadHead(@RequestParam("head") MultipartFile file, HttpServletRequest request , HttpServletResponse response,Model model) throws IOException{
		if(!file.isEmpty()){
	        ServletContext sc = request.getSession().getServletContext();
	        String dir=sc.getRealPath("/upload/userHead");	//设定文件保存的目录
	        String fileSuffix=com.xnx3.Lang.subString(file.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
//	        String fileName=getUser().getId()+"."+fileSuffix;	//此种方式不利于CDN缓存
	        String fileName=Lang.uuid()+"."+fileSuffix;
	        if(fileSuffix.equals("png")||fileSuffix.equals("jpg")){
	        	//将图片保存到目标文件
		        FileUtils.writeByteArrayToFile(new File(dir,fileName), file.getBytes());
		        
		        //更新图片到数据库
		        User user =sqlService.findById(User.class, getUser().getId());
		        user.setHead(fileName);
		        sqlService.save(user);
		        
		        setUserForSession(user);
		        ActionLogCache.insert(request, "上传头像", "成功，头像文件："+user.getHead());
		        return success(model, "保存成功！", "user/info.do");
	        }else{
	        	ActionLogCache.insert(request, "上传头像", "失败：当前格式为："+fileSuffix+"，只允许上传png、jpg格式的图片！");
	        	return error(model, "当前格式为："+fileSuffix+"，请上传png、jpg格式的图片！");
	        }
		}else{
			ActionLogCache.insert(request, "上传头像", "失败：上传的文件为空");
			return error(model, "上传的文件不能为空！");
		}
	}
	
	/**
	 * 修改昵称,传入nickname
	 * @param user {@link User}
	 */
	@RequiresPermissions("userUpdateNickName")
	@RequestMapping("updateNickName")
	public String updateNickName(HttpServletRequest request,Model model){
		BaseVO baseVO = userService.updateNickName(request);
		if(baseVO.getResult() == BaseVO.FAILURE){
			ActionLogCache.insert(request, "修改昵称", "失败："+baseVO.getInfo());
			return error(model, baseVO.getInfo());
		}else{
			ActionLogCache.insert(request, "修改昵称");
			return success(model, "修改成功！", "user/info.do");
		}
	}
	
	/**
	 * 修改密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	@RequiresPermissions("userUpdatePassword")
	@RequestMapping("updatePassword")
	public BaseVO updatePassword(HttpServletRequest request, String oldPassword,String newPassword,Model model){
		if(oldPassword==null){
			ActionLogCache.insert(request, "修改密码", "失败：未输入密码");
			return error("请输入旧密码");
		}else{
			User uu=sqlService.findById(User.class, getUser().getId());
			if(oldPassword.equals(uu.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					ActionLogCache.insert(request, "修改密码", "成功");
					return success();
				}else{
					ActionLogCache.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(vo.getInfo());
				}
			}else{
				ActionLogCache.insert(request, "修改密码", "失败：原密码错误");
				return error("原密码错误！");
			}
		}
	}

	/**
	 * 用户自己获取自己的邀请注册网址页面
	 */
	@RequiresPermissions("userInvite")
	@RequestMapping("invite")
	public String invite(HttpServletRequest request){
		ActionLogCache.insert(request, "获取邀请码注册网址");
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
	 * @param model {@link Model}
	 * @return View
	 */
	@RequestMapping("logout")
	public String logout(Model model, HttpServletRequest request){
		ActionLogCache.insert(request, "注销登录");
		userService.logout();
		return success(model, "注销登录成功", "login.do");
	}
	
}
