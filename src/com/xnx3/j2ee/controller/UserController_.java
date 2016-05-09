package com.xnx3.j2ee.controller;

import java.io.File;
import java.io.IOException;
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
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
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
	private LogService logService;
	
	/**
	 * 用户个人中心
	 * @return View
	 */
	@RequiresPermissions("userInfo")
	@RequestMapping("/info")
	public String userInfo(){
		return "iw/user/info";
	}
	
	/**
	 * 更改用户当前头像
	 * @param file {@link MultipartFile}上传的文件
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws IOException
	 * @return View
	 */
	@RequiresPermissions("userUploadHead")
	@RequestMapping("/uploadHead")
	public String uploadHead(@RequestParam("head") MultipartFile file, HttpServletRequest request , HttpServletResponse response,Model model) throws IOException{
		if(!file.isEmpty()){
	        ServletContext sc = request.getSession().getServletContext();
	        String dir=sc.getRealPath("/upload/userHead");	//设定文件保存的目录
	        String fileSuffix=com.xnx3.Lang.subString(file.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
	        String fileName=getUser().getId()+"."+fileSuffix;
	        if(fileSuffix.equals("png")||fileSuffix.equals("jpg")){
	        	//将图片保存到目标文件
		        FileUtils.writeByteArrayToFile(new File(dir,fileName), file.getBytes());
		        
		        //更新图片到数据库
		        User user =userService.findById(getUser().getId());
		        user.setHead(fileName);
		        userService.save(user);
		        
		        setUserForSession(user);
				logService.insert("USER_UPDATEHEAD");
		        return success(model, "保存成功！", "user/info.do");
	        }else{
	        	return error(model, "当前格式为："+fileSuffix+"，请上传png、jpg格式的图片！");
	        }
		}else{
			return error(model, "上传的文件不能为空！");
		}
	}
	
	/**
	 * 修改姓名,传入nickname
	 * @param user {@link User}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("userUpdateNickName")
	@RequestMapping("updateNickName")
	public String updateNickName(HttpServletRequest request,Model model){
		BaseVO baseVO = userService.updateNickName(request);
		if(baseVO.getResult() == BaseVO.FAILURE){
			return error(model, baseVO.getInfo());
		}else{
			return success(model, "修改成功！", "user/info.do");
		}
	}
	
	/**
	 * 修改密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param model {@link Model}
	 * @return {@link BaseVO}
	 */
	@RequiresPermissions("userUpdatePassword")
	@RequestMapping("updatePassword")
	public BaseVO updatePassword(String oldPassword,String newPassword,Model model){
		BaseVO baseVO = new BaseVO();
		if(oldPassword==null||newPassword==null){
			baseVO.setResult(BaseVO.FAILURE);
			baseVO.setInfo("请输入密码");
		}else{
			User uu=userService.findById(getUser().getId());
			if(oldPassword.equals(uu.getPassword())){
				String md5Password = new Md5Hash(newPassword, uu.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
				uu.setPassword(md5Password);
				userService.save(uu);
				
				logService.insert("USER_UPDATEPASSWORD");
			}else{
				baseVO.setResult(BaseVO.FAILURE);
				baseVO.setInfo("原密码错误！");
			}
		}
		return baseVO;
	}

	/**
	 * 用户自己获取自己的邀请注册网址页面
	 * @return View
	 */
	@RequiresPermissions("userInvite")
	@RequestMapping("invite")
	public String invite(){
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
			Model model){
		
		//验证邮箱
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			MailUtil.sendMail(email, "邀请", "内容");
			
			logService.insert("USER_EMAIL_INVITE", email);
			return success(model, "邀请邮件发送完毕", "user/info.do");
		}else{
			return error(model, "请填写合法邮箱");
		}
	}
	
	/**
	 * 用户退出，页面跳转提示。
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("userLogout")
	@RequestMapping("logout")
	public String logout(Model model){
		userService.logout();
		return success(model, "注销登录成功", "login.do");
	}
	
}
