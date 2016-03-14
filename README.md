# iw
web 快速开发，基础功能集成底层

使用框架：
hibernate 4.2
spring 4.2
spring mvc 4.2
shiro 1.2.3
urlrewritefilter 4.0.3
xnx3 1.5
fastdfs

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
<br/>
jsp页面可用参数：<br/>
	用户信息参数：<pre>${user}<br/>如调用user表的id为${user.id}</pre><br/>
	
	未读信息条数：<pre>${unreadMessage}</pre>
