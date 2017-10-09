package com.xnx3.j2ee.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.CollectService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 用户关注
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/collect")
public class CollectController_ extends BaseController {
	
	@Resource
	private CollectService collectService;
	@Resource
	private SqlService sqlService;
	
	/**
	 * 添加关注的表单提交（演示）
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
	 * 关注列表,关注我的
	 */
	@RequestMapping("/myList")
	public String myList(HttpServletRequest request,Model model){
		Sql sql = new Sql(request);
		sql.appendWhere("collect.othersid="+getUser().getId());
		int count = sqlService.count("collect", sql.getWhere());
		Page page = new Page(count, Global.getInt("LIST_EVERYPAGE_NUMBER"), request);
		sql.setSelectFromAndPage("SELECT collect.*,user.nickname,user.head FROM collect,user", page);
		sql.appendWhere("collect.userid = user.id");
		sql.setDefaultOrderBy("collect.id DESC");
		List<Map<String, Object>> list = sqlService.findMapBySql(sql);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "iw/collect/list";
	}
	
	/**
	 * 关注列表,我关注别人的
	 */
	@RequestMapping("/otherList")
	public String otherList(HttpServletRequest request,Model model){
		Sql sql = new Sql(request);
		sql.appendWhere("collect.userid="+getUser().getId());
		int count = sqlService.count("collect", sql.getWhere());
		Page page = new Page(count, Global.getInt("LIST_EVERYPAGE_NUMBER"), request);
		sql.setSelectFromAndPage("SELECT collect.*,user.nickname,user.head FROM collect,user", page);
		sql.appendWhere("collect.othersid = user.id");
		sql.setDefaultOrderBy("collect.id DESC");
		List<Map<String, Object>> list = sqlService.findMapBySql(sql);
		
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
