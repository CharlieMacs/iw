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
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 日志管理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/log")
public class LogAdminController extends BaseController{

	@Resource
	private LogService logService;
	
	@Resource
	private GlobalService globalService;
	
	@RequiresPermissions("adminLogList")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"userid","type","goalid=","addtime"};
		String where = sql.generateWhere(request, column, "isdelete = 0");
		int count = globalService.count("log", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT log.*,(SELECT user.nickname FROM user WHERE user.id=log.userid) AS nickname FROM log "+where+" ORDER BY log.id DESC",page);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/admin/log/list";
	}
	
}
