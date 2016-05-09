package com.xnx3.j2ee.controller;

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
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostClass;
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
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.PostVO;

/**
 * 论坛，帖子处理
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/bbs")
public class BbsController_ extends BaseController {
	
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
	 * @param classid 要发表到哪个分类
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("bbsAddPost")
	@RequestMapping("/addPost")
	public String addPost(
			@RequestParam(value = "classid", required = false) String classid,
			Model model){
		if(classid==null||classid.equals("")||classid.equals("null")){
			classid=Global.DEFAULT_BBS_CREATEPOST_CLASSID+"";
		}
		
		List<PostClass> postClassList = postClassService.findAll();
		
		model.addAttribute("postClassList", postClassList);
		model.addAttribute("classid", classid);
		return "iw/bbs/addPost";
	}
	
	
	/**
	 * 发帖提交页面
	 * @param post {@link Post}
	 * @param text 帖子内容
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("bbsAddPost")
	@RequestMapping("/addPostSubmit")
	public String addPostSubmit(HttpServletRequest request, Model model){
		BaseVO baseVO = postService.savePost(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "操作成功！", "bbs/view.do?id="+baseVO.getInfo());
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	
	/**
	 * 帖子列表
	 * @param post {@link Post}，其中classid: 
	 * 				<ul>
	 * 					<li>0:所有
	 * 					<li>其余数字就是搜索的classid
	 * 				</ul>
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("bbsList")
	@RequestMapping("/list")
	public String list(Post post,HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"classid=","title","view>","info","addtime","userid="};
		String where = sql.generateWhere(request, column, "state = "+Post.STATE_NORMAL+" AND isdelete = "+Post.ISDELETE_NORMAL);
		int count = globalService.count("post", where);
		Page page = new Page(count, Global.PAGE_DEFAULT_EVERYNUMBER, request);
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT post.*, user.nickname, user.head FROM post LEFT JOIN user ON user.id = post.userid "+where+" ORDER BY post.id DESC",page);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		return "iw/bbs/list";
	}
	
	/**
	 * 查看帖子详情
	 * @param post {@link Post}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("bbsView")
	@RequestMapping("/view")
	public String view(@RequestParam(value = "id", required = true) int id,Model model){
		PostVO postVO = postService.read(id);
		if(postVO.getResult() == PostVO.SUCCESS){
			//查询回帖
			List commentList = postCommentService.commentAndUser(postVO.getPost().getId(),10);
			model.addAttribute("postVO", postVO);
			model.addAttribute("commentList", commentList);
			return "iw/bbs/view";
		}else{
			return error(model, postVO.getInfo());
		}
	}
	
	/**
	 * 回帖处理
	 * @param post {@link Post}
	 * @param text 回帖内容
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("bbsAddComment")
	@RequestMapping("/addCommentSubmit.do")
	public String commentSubmit(HttpServletRequest request,Model model){
		BaseVO baseVO = postCommentService.addComment(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "回复成功！","bbs/view.do?id="+request.getParameter("postid"));
		}else{
			return error(model, baseVO.getInfo());
		}
	}
}
