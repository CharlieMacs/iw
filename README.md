# iw &nbsp;web 快速开发，基础功能集成底层
<br/>
<br/>
使用框架：<br/>
hibernate 4.2<br/>
spring 4.2<br/>
spring mvc 4.2<br/>
shiro 1.2.3<br/>
urlrewritefilter 4.0.3<br/>
xnx3 2.0<br/>
fastdfs<br/>

Web project<br/>
/src								java源代码<br/>
/src/com/xnx3/j2ee/bean			相关bean类<br/>
/src/com/xnx3/j2ee/controller		springmvc控制器<br/>
/src/com/xnx3/j2ee/controller/admin管理后台<br/>
/src/com/xnx3/j2ee/dao			数据库相关操作<br/>
/src/com/xnx3/j2ee/entity			数据库关联的实体类<br/>
/src/com/xnx3/j2ee/generateCache	程序数据的缓存文件生成<br/>
/src/com/xnx3/j2ee/interceptor		springmvc拦截器<br/>
/src/com/xnx3/j2ee/service			service层<br/>
/src/com/xnx3/j2ee/service/impl		service层的实现<br/>
/src/com/xnx3/j2ee/servlet			servlet<br/>
/src/com/xnx3/j2ee/shiro			shiro权限<br/>
/src/com/xnx3/j2ee/tld				自定义标签<br/>
/src/com/xnx3/j2ee/util			工具类<br/>
/src/com/xnx3/j2ee/vo				json返回使用<br/>

/*.do 、 /*.jsp 、 /*.html  根目录下的do/html/jsp页面可以直接访问，无需登陆<br/>
com.xnx3.j2ee.Global						基础配置、集中管理。全局控制，全局的参数、常亮，都是在这里<br/>
com.xnx3.j2ee.Global.get("systemName");	直接调用system表配置的值<br/>
src/systemConfig.xml						系统某些实用功能的配置文件所在<br/>
<br/><br/>
<h2>JSP页面全局参数获取</h2>
<pre>
	用户信息参数：${user}，如调用user表的id为${user.id}<br/>
	未读信息条数：${unreadMessage}
</pre>

<br/>
<br/>
前端开发：杨建伟<br/>
架构设计：管雷鸣<br/>
联系QQ：921153866<br/>


