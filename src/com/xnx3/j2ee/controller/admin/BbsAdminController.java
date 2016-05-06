package com.xnx3.j2ee.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.BaseEntity;
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.entity.PostComment;
import com.xnx3.j2ee.entity.PostData;
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
import com.xnx3.j2ee.vo.BaseVO;

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
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsPostList")
	@RequestMapping("postList")
	public String postList(HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"classid=","title","view","info","addtime(date:yyyy-MM-dd hh:mm:ss)>"};
		String where = sql.generateWhere(request, column, " isdelete = "+BaseEntity.ISDELETE_NORMAL);
		int count = globalService.count("post", where);
		Page page = new Page(count, Global.PAGE_ADMIN_DEFAULT_EVERYNUMBER, request);
		List<Post> list = globalService.findBySqlQuery("SELECT * FROM post", where+" ORDER BY id DESC", page,Post.class);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "/iw/admin/bbs/postList";
	}
	
	/**
	 * 新增、修改帖子
	 * @param id 帖子id，Post.id
	 * @param model {@link Model}
	 * @return View
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
		return "iw/admin/bbs/post";
	}
	
	/**
	 * 添加、编辑时保存帖子
	 * @param id 帖子id，Post.id
	 * @param classid 分类id
	 * @param title 帖子标题
	 * @param text 帖子内容
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsPost")
	@RequestMapping("savePost")
	public String savePost(HttpServletRequest request,Model model){
		BaseVO baseVO = postService.savePost(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "操作成功！", "admin/bbs/postList.do?classid="+request.getParameter("classid"));
		}else{
			return error(model, baseVO.getInfo());
		}
	}

	/**
	 * 板块列表
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
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
		return "/iw/admin/bbs/classList";
	}
	
	
	/**
	 * 删除帖子
	 * @param id 帖子id，Post.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsDeletePost")
	@RequestMapping("deletePost")
	public String deletePost(@RequestParam(value = "id", required = true) int id, Model model){
		BaseVO baseVO = postService.deletePost(id);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "删除成功");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	
	/**
	 * 添加板块页面
	 * @return View
	 */
	@RequiresPermissions("adminBbsAddClass")
	@RequestMapping("addClass")
	public String addClass(){
		return "iw/admin/bbs/class";
	}
	
	
	/**
	 * 添加／修改板块提交页面
	 * @param postClass {@link PostClass}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsSaveClass")
	@RequestMapping("saveClass")
	public String saveClass(HttpServletRequest request, Model model){
		BaseVO baseVO = postClassService.savePostClass(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "操作成功","admin/bbs/classList.do");
		}else{
			return error(model, baseVO.getInfo());
		}
	}

	/**
	 * 编辑板块
	 * @param id 板块id，PostClass.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsEditClass")
	@RequestMapping("editClass")
	public String editClass(@RequestParam(value = "id", required = true) int id,Model model){
		if(id>0){
			PostClass postClass = postClassService.findById(id);
			if(postClass!=null){
				model.addAttribute("postClass", postClass);
				return "iw/admin/bbs/class";
			}else{
				return "板块不存在";
			}
		}else{
			return error(model, "传入的参数不正确");
		}
	}
	

	/**
	 * 删除板块
	 * @param id 板块id，PostClass.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsDeleteClass")
	@RequestMapping("deleteClass")
	public String deleteClass(@RequestParam(value = "id", required = true) int id, Model model){
		BaseVO baseVO = postClassService.deletePostClass(id);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return error(model, "删除成功");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	

	/**
	 * 评论列表
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
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
		return "/iw/admin/bbs/commentList";
	}
	

	/**
	 * 删除帖子评论
	 * @param id 帖子评论的id，PostComment.id
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("adminBbsDeletePostComment")
	@RequestMapping("deleteComment")
	public String deleteComment(@RequestParam(value = "id", required = true) int id, Model model){
		BaseVO baseVO = postCommentService.deleteComment(id);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "删除成功");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	
}
