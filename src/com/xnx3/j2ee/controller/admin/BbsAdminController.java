package com.xnx3.j2ee.controller.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.service.PostCommentService;
import com.xnx3.j2ee.service.PostDataService;
import com.xnx3.j2ee.service.PostService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 论坛，帖子
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/admin/bbs")
public class BbsAdminController extends BaseController {
	
	@Resource
	private PostService postService;
	
	@Resource
	private PostDataService postDataService;
	
	@Resource
	private PostCommentService postCommentService;
	
	@Resource
	private PostClassService postClassService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private GlobalService globalService;
	
	@Resource
	private LogService logService;

	/**
	 * 帖子列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsPostList")
	@RequestMapping("postList")
	public String postList(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"classid=","title","view","info","addtime(date:yyyy-MM-dd hh:mm:ss)>"};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("post", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		List<Post> list = globalService.findBySqlQuery("SELECT * FROM post", where+" ORDER BY id DESC", page,Post.class);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/admin/bbs/postList";
	}
	
	/**
	 * 新增、修改帖子
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsPost")
	@RequestMapping("post")
	public String post(@RequestParam(value = "id", defaultValue = "0", required = false) int id,Model model){
		if(id > 0){
			Post post = postService.findById(id);
			if(post != null){
				PostData postData = postDataService.findById(id);
				model.addAttribute("post", post);
				model.addAttribute("postData", postData);
			}else{
				return error(model, "帖子不存在！");
			}
		}
		return "admin/bbs/post";
	}
	
	/**
	 * 添加、编辑时保存帖子
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsPost")
	@RequestMapping("savePost")
	public String savePost(
			@RequestParam(value = "id", defaultValue = "0", required = false) int id,
			@RequestParam(value = "classid", defaultValue = "0", required = true) int classid,
			@RequestParam(value = "title", defaultValue = "", required = false) String title,
			@RequestParam(value = "text", defaultValue = "", required = false) String text,
			Model model){
		if(classid == 0){
			return error(model, "请选择发布的板块");
		}
		if(title.equals("")){
			return error(model, "标题不能为空");
		}
		if(text.equals("")){
			return error(model, "内容不能为空");
		}
		
		Post post = new com.xnx3.j2ee.entity.Post();
		PostData postData = new PostData();
		if(id != 0){
			post = postService.findById(id);
			if(post == null){
				return error(model, "帖子不存在！");
			}else{
				post.setId(id);
				postData = postDataService.findById(post.getId());
			}
		}else{
			post.setAddtime(com.xnx3.DateUtil.timeForUnix10());
			post.setState(Post.STATE_NORMAL);
			post.setUserid(getUser().getId());
			post.setView(0);
		}
		
		String info="";	//截取简介文字,30字
		if(text.length()<60){
			info=text;
		}else{
			info=text.substring(0,60);
		}
		
		post.setTitle(title);
		post.setClassid(classid);
		post.setInfo(info);
		postService.save(post);
		
		if(postData.getPostid()==null){
			postData.setPostid(post.getId());
		}
		postData.setText(text);
		postDataService.save(postData);
		
		return success(model, "保存成功", "admin/bbs/postList.do?classid="+post.getClassid());
	}

	/**
	 * 板块列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsClassList")
	@RequestMapping("classList")
	public String classList(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"id=","name"};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("post_class", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		where = sql.generateWhere(request, column, null);
		List<PostClass> list = globalService.findBySqlQuery("SELECT * FROM post_class", where, page,PostClass.class);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "/admin/bbs/classList";
	}
	
	
	/**
	 * 删除帖子
	 * @return
	 */
	@RequiresPermissions("adminBbsDeletePost")
	@RequestMapping("deletePost")
	public String deletePost(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			Post p = postService.findById(id);
			if(p!=null){
				postService.delete(p);
				logService.insert(p.getId(), "ADMIN_SYSTEM_BBS_POST_DELETE", p.getTitle());
				return success(model, "删除成功", "admin/bbs/postList.do");
			}
		}
		
		return error(model, "删除失败");
	}
	
	/**
	 * 添加板块页面
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsAddClass")
	@RequestMapping("addClass")
	public String addClass(Model model){
		return "admin/bbs/class";
	}
	
	
	/**
	 * 添加／修改板块提交页面
	 * @param postClass
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsSaveClass")
	@RequestMapping("saveClass")
	public String saveClass(PostClass postClass, Model model){
		postClassService.save(postClass);
		if(postClass.getId()>0){
			new Bbs().postClass(postClassService.findAll());
			logService.insert(postClass.getId(), "ADMIN_SYSTEM_BBS_CLASS_SAVE", postClass.getName());
			return success(model, "保存成功","admin/bbs/classList.do");
		}else{
			return error(model, "保存失败");
		}
	}

	/**
	 * 编辑板块
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsEditClass")
	@RequestMapping("editClass")
	public String editClass(@RequestParam(value = "id", required = true) int id,Model model){
		if(id>0){
			PostClass postClass = postClassService.findById(id);
			if(postClass!=null){
				model.addAttribute("postClass", postClass);
				return "admin/bbs/class";
			}else{
				return "板块不存在";
			}
		}else{
			return error(model, "传入的参数不正确");
		}
	}
	

	/**
	 * 删除板块
	 * @return
	 */
	@RequiresPermissions("adminBbsDeleteClass")
	@RequestMapping("deleteClass")
	public String deleteClass(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			PostClass pc = postClassService.findById(id);
			if(pc!=null){
				postClassService.delete(pc);
				logService.insert(pc.getId(), "ADMIN_SYSTEM_BBS_CLASS_DELETE", pc.getName());
				return success(model, "删除成功", "admin/bbs/classList.do");
			}
		}
		
		return error(model, "删除失败");
	}
	

	/**
	 * 评论列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("adminBbsPostCommentList")
	@RequestMapping("commentList")
	public String commentList(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"postid=","userid="};
		String where = sql.generateWhere(request, column, null);
		int count = globalService.count("post_comment", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		List<Post> list = globalService.findBySqlQuery("SELECT * FROM post_comment", where+" ORDER BY id DESC", page,PostComment.class);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/admin/bbs/commentList";
	}
	

	/**
	 * 删除帖子评论
	 * @return
	 */
	@RequiresPermissions("adminBbsDeletePostComment")
	@RequestMapping("deleteComment")
	public String deleteComment(@RequestParam(value = "id", required = true) int id, Model model){
		if(id>0){
			PostComment pc = postCommentService.findById(id);
			if(pc!=null){
				postCommentService.delete(pc);
				logService.insert(pc.getPostid(), "BBS_POST_DELETE_COMMENT", pc.getText());
				return success(model, "删除成功");
			}
		}
		
		return error(model, "删除失败");
	}
	
}
