package com.xnx3.j2ee.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.PayLogService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 在线支付日志管理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/payLog")
public class PayLogAdminController_ extends BaseController{

	@Resource
	private PayLogService payLogService;
	
	@Resource
	private GlobalService globalService;
	
	/**
	 * 日志列表
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminPayLogList")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
//		Sql sql = new Sql();
//		String[] column = {"userid=","orderno","channel"};
//		String where = sql.generateWhere(request, column, null);
//		int count = globalService.count("pay_log", where);
//		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
//		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT pay_log.*,(SELECT user.nickname FROM user WHERE user.id=pay_log.userid) AS nickname FROM pay_log "+where,page);
		Sql sql = new Sql(request);
		sql.setSearchTable("pay_log");
		sql.setSearchColumn(new String[]{"userid=","orderno","channel"});
		int count = globalService.count("pay_log", sql.getWhere());
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		sql.setSelectFromAndPage("SELECT pay_log.*,(SELECT user.nickname FROM user WHERE user.id=pay_log.userid) AS nickname FROM pay_log ", page);
		sql.setDefaultOrderBy("pay_log.id DESC");
		List<Map<String, String>> list = globalService.findMapBySql(sql);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/iw/admin/payLog/list";
	}
	
}
