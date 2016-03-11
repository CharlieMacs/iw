package com.xnx3.j2ee.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.MessageData;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.MessageDataService;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 信息管理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/message")
public class MessageAdminController extends BaseController {

	@Resource
	private MessageService messageService;
	
	@Resource
	private MessageDataService messageDataService;
	
	@Resource
	private GlobalService globalService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 信息列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminMessageList")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"id=","self=","other="};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("message", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		where = sql.generateWhere(request, column, "message.id=message_data.id","message");
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT message.*,message_data.content, (SELECT user.nickname FROM user WHERE user.id=message.other) AS other_nickname ,(SELECT user.nickname FROM user WHERE user.id=message.self) AS self_nickname FROM message ,message_data ,user "+where+" GROUP BY message.id ORDER BY message.id DESC", page);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "/admin/message/list";
	}
	
	/**
	 * 删除信息
	 * @return
	 */
	@RequiresPermissions("adminMessageDelete")
	@RequestMapping("delete")
	public String delete(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			Message m = messageService.findById(id);
			if(m!=null){
				messageService.delete(m);
				MessageData md = messageDataService.findById(m.getId());
				if(md!=null){
					messageDataService.delete(md);
					
					Log log = new Log();
					log.setAddtime(new Date());
					log.setType(Log.typeMap.get("ADMIN_SYSTEM_MESSAGE_DELETE"));
					log.setUserid(getUser().getId());
					log.setGoalid(m.getId());
					log.setValue(md.getContent());
					logService.save(log);
					
					return success(model, "删除成功","admin/message/list.do");
				}
			}
		}
		
		return error(model, "删除失败！");
	}
}
