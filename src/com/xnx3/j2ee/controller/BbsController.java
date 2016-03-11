package com.xnx3.j2ee.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.entity.PostComment;
import com.xnx3.j2ee.entity.PostData;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.service.PostCommentService;
import com.xnx3.j2ee.service.PostDataService;
import com.xnx3.j2ee.service.PostService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.DateUtil;

/**
 * 论坛，帖子处理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/bbs")
public class BbsController extends BaseController {
	
	@Resource
	private PostService postService;
	
	@Resource
	private GlobalService globalService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private PostDataService postDataService;
	
	@Resource
	private PostCommentService postCommentService;
	
	@Resource
	private PostClassService postClassService;
	
	@Resource
	private UserService userService;
	
	/**
	 * 发帖
	 * @return
	 */
	@RequiresPermissions("bbsAddPost")
	@RequestMapping("/addPost")
	public String addPost(
			@RequestParam(value = "classid", required = false) String classid,
			HttpServletRequest request,Model model){
		if(classid==null||classid.equals("")||classid.equals("null")){
			classid=Global.DEFAULT_BBS_CREATEPOST_CLASSID+"";
		}
		
		List<PostClass> postClassList = postClassService.findAll();
		
		model.addAttribute("postClassList", postClassList);
		model.addAttribute("classid", classid);
		return "bbs/addPost";
	}
	
	/**
	 * 发帖提交页面
	 * @param post
	 * @param text 帖子内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("bbsAddPost")
	@RequestMapping("/addPostSubmit")
	public String addPostSubmit(
			Post post, 
			@RequestParam(value = "text", required = true) String text,
			HttpServletRequest request , HttpServletResponse response,Model model){
		if(post.getTitle()==null||post.getTitle().length()<4||post.getTitle().length()>30){
			return error(model, "标题必须是2到15个汉字或者4到30个字母");
		}else if(text==null||text.length()<4){
			return error(model, "帖子内容必须是大于2个汉字或者4个字母");
		}else{
			String info="";	//截取简介文字,30字
			if(text.length()<60){
				info=text;
			}else{
				info=text.substring(0,60);
			}
			
			post.setAddtime(DateUtil.timeForUnix10());
			post.setInfo(info);
			post.setUserid(getUser().getId());
			post.setView(0);
			post.setState(Post.STATE_NORMAL);
			postService.save(post);
			
			PostData postData = new PostData();
			postData.setPostid(post.getId());
			postData.setText(text);
			postDataService.save(postData);
			
			Log log = new Log();
			log.setAddtime(new Date());
			log.setType(Log.typeMap.get("BBS_POST_ADD"));
			log.setGoalid(post.getId());
			log.setUserid(getUser().getId());
			log.setValue(post.getTitle());
			logService.save(log);
			
			return success(model, "发布成功！", "bbs/list.do?classid="+post.getClassid());
		}
	}
	
	/**
	 * 帖子列表
	 * @param post classid:0 所有；其余数字就是搜索的classid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("bbsList")
	@RequestMapping("/list")
	public String list(Post post,HttpServletRequest request , HttpServletResponse response,Model model){
		Sql sql = new Sql();
		String[] column = {"classid=","title","view>","info","addtime","userid="};
		String where = sql.generateWhere(request, column, "post.state = "+Post.STATE_NORMAL);
		int count = globalService.count("post", where);
		Page page = new Page(count, Global.PAGE_DEFAULT_EVERYNUMBER, request);
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT post.*, user.nickname, user.head FROM post LEFT JOIN user ON user.id = post.userid "+where+" ORDER BY post.id DESC",page);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "bbs/list";
	}
	
	/**
	 * 查看帖子详情
	 * @param post
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("bbsView")
	@RequestMapping("/view")
	public String view(Post post,HttpServletRequest request , HttpServletResponse response,Model model){
		if(post.getId()==null||post.getId()==0){
			return error(model, "帖子id未传入");
		}else{
			//查询帖子详情
			post=postService.findById(post.getId());
			post.setView(post.getView()+1);
			postService.save(post);
			
			Log log = new Log();
			log.setAddtime(new Date());
			log.setType(Log.typeMap.get("BBS_POST_VIEW"));
			log.setGoalid(post.getId());
			log.setUserid(getUser().getId());
			log.setValue(post.getTitle());
			logService.save(log);
			
			PostData postData = postDataService.findById(post.getId());
			String text = postData.getText();
			
			User postUser = userService.findById(post.getUserid());
			
			//查询回帖
			List list = postCommentService.commentAndUser(post.getId());
			//查看栏目相关信息
			PostClass postClass = postClassService.findById(post.getClassid());
			
			model.addAttribute("postUser", postUser);	//发帖用户的用户信息
			model.addAttribute("post",post);
			model.addAttribute("text",text);
			model.addAttribute("list",list);	//回帖列表
			model.addAttribute("postClass",postClass);
		}
		
		model.addAttribute("unreadMessage","");
		return "bbs/view";
	}
	
	/**
	 * 回帖处理
	 * @param post
	 * @param text 回帖内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("bbsAddComment")
	@RequestMapping("/addCommentSubmit.do")
	public String commentSubmit(Post post,String text,HttpServletRequest request , HttpServletResponse response,Model model){
		if(text==null||text.length()<2){
			return error(model, "评论最少输入1个汉字或者两个字符");
		}else{
			//先查询是不是有这个主贴
			Post p=postService.findById(post.getId());
			if(p!=null){
				PostComment postComment=new PostComment();
				postComment.setPostid(post.getId());
				postComment.setUserid(getUser().getId());
				postComment.setAddtime(DateUtil.timeForUnix10());
				postComment.setText(text);
				postCommentService.save(postComment);
				
				Log log = new Log();
				log.setAddtime(new Date());
				log.setType(Log.typeMap.get("BBS_POST_COMMENT_ADD"));
				log.setGoalid(postComment.getId());
				log.setUserid(getUser().getId());
				log.setValue(post.getTitle());
				logService.save(log);
				
				return success(model, "回复成功！","bbs/view.do?id="+post.getId());
			}else{
				return error(model, "主帖不存在！");
			}
		}
	}
	
}
