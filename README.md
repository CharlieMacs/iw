# iw &nbsp;web 快速开发，基础功能集成底层
<br/>
整合权限、论坛、会员、日志、消息、系统、数据缓存、短信等功能
<br/>Spring4、Spring MVC4、Hibernate4、Shiro、<a href="http://github.com/xnx3/xnx3" target="_black">xnx3-2.2</a>

##快速开发示例
* 列表分页
	* [单数据表、分页示例](http://www.xnx3.com/software/iw/20160527/946.html)
	* [单数据表、搜索、分页示例](http://www.xnx3.com/software/iw/20160527/947.html)
	* [单数据表、搜索、排序、分页示例](http://www.xnx3.com/software/iw/20160524/944.html)
	* [多表查询、搜索、排序、分页示例](http://www.xnx3.com/software/iw/20160527/949.html)
	* [多数据表联合查询、搜索、排序、状态码js缓存](http://www.xnx3.com/software/iw/20160527/950.html)
* 日志
	* [添加日志记录](http://www.xnx3.com/software/iw/20160527/951.html)
* 文件上传
	* [OSS文件上传](http://www.xnx3.com/software/iw/20160601/952.html)
* 多语言支持
	* [中文、英文、...添加减少语言种类自由配置](http://www.xnx3.com/software/iw/20160623/953.html)
* 数据库操作
	* [事务](http://www.xnx3.com/software/iw/20160628/954.html)
	* [乐观锁](http://www.xnx3.com/software/iw/20160628/955.html)

<br/>
<br/>

## 目录说明
* /src								java源代码<br/>
* /src/com/xnx3/j2ee/bean			相关bean类<br/>
* /src/com/xnx3/j2ee/controller		springmvc控制器<br/>
* /src/com/xnx3/j2ee/controller/admin管理后台<br/>
* /src/com/xnx3/j2ee/dao			数据库相关操作<br/>
* /src/com/xnx3/j2ee/entity			数据库关联的实体类<br/>
* /src/com/xnx3/j2ee/generateCache	程序数据的缓存文件生成<br/>
* /src/com/xnx3/j2ee/interceptor		springmvc拦截器<br/>
* /src/com/xnx3/j2ee/service			service层<br/>
* /src/com/xnx3/j2ee/service/impl		service层的实现<br/>
* /src/com/xnx3/j2ee/servlet			servlet<br/>
* /src/com/xnx3/j2ee/shiro			shiro权限<br/>
* /src/com/xnx3/j2ee/tld				自定义标签<br/>
* /src/com/xnx3/j2ee/util			工具类<br/>
* /src/com/xnx3/j2ee/vo				json返回使用<br/>

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
架构设计：管雷鸣<br/>
参与：杨建伟、魏文群<br/>
联系QQ：921153866<br/>


