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

Web project
/src								java源代码
/src/com/xnx3/j2ee/bean			相关bean类
/src/com/xnx3/j2ee/controller		springmvc控制器
/src/com/xnx3/j2ee/controller/admin管理后台
/src/com/xnx3/j2ee/dao			数据库相关操作
/src/com/xnx3/j2ee/entity			数据库关联的实体类
/src/com/xnx3/j2ee/generateCache	程序数据的缓存文件生成
/src/com/xnx3/j2ee/interceptor		springmvc拦截器
/src/com/xnx3/j2ee/service			service层
/src/com/xnx3/j2ee/service/impl		service层的实现
/src/com/xnx3/j2ee/servlet			servlet
/src/com/xnx3/j2ee/shiro			shiro权限
/src/com/xnx3/j2ee/tld				自定义标签
/src/com/xnx3/j2ee/util			工具类
/src/com/xnx3/j2ee/vo				json返回使用

/*.do 、 /*.jsp 、 /*.html  根目录下的do/html/jsp页面可以直接访问，无需登陆
com.xnx3.j2ee.Global						基础配置、集中管理。全局控制，全局的参数、常亮，都是在这里
com.xnx3.j2ee.Global.get("systemName");	直接调用system表配置的值
src/systemConfig.xml						系统某些实用功能的配置文件所在

jsp页面可用参数：
	用户信息参数：${user} 调用user表的字段，如${user.id}
	未读信息条数：${unreadMessage}