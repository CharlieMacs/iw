package com.xnx3.j2ee.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.CollectService;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.net.MailUtil;

/**
 * 用户User的相关操作
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/collect")
public class CollectController_ extends BaseController {
	
	@Resource
	private CollectService collectService;
	@Resource
	private GlobalService globalService;
	
	/**
	 * 用户个人中心
	 * @return View
	 */
	@RequestMapping("/add")
	public String add(){
		return "iw/collect/add";
	}
	
	/**
	 * 关注提交
	 * @param othersid 要关注的目标用户的userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/addSubmit")
	public String addSubmit(@RequestParam(value = "othersid", required = true) int othersid,Model model){
		BaseVO baseVO =  collectService.addCollect(othersid);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "关注成功","collect/list.do");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	
	/**
	 * 我的关注列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		Sql sql = new Sql(request);
		sql.appendWhere("collect.userid="+getUser().getId());
		int count = globalService.count("collect", sql.getWhere());
		Page page = new Page(count, Global.PAGE_DEFAULT_EVERYNUMBER, request);
		sql.setSelectFromAndPage("SELECT collect.* , (SELECT nickname FROM user WHERE user.id = collect.othersid) AS nickname FROM collect", page);
		sql.setDefaultOrderBy("collect.id DESC");
		List<Map<String, String>> list = globalService.findMapBySql(sql);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "iw/collect/list";
	}
	
	/**
	 * 取消关注
	 * @param othersid 要取消关注的用户id
	 * @param model
	 * @return
	 */
	@RequestMapping("/cancelCollect")
	public String cancelCollect(@RequestParam(value = "othersid", required = true) int othersid,Model model){
		BaseVO baseVO = collectService.cancelCollect(othersid);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "取消成功","collect/list.do");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
}
