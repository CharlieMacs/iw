package com.xnx3.j2ee.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author 管雷鸣
 * 
 */
@Entity
@Table(name = "log")
public class Log extends BaseEntity {
	/**
	 * 取type的值，根据name获取数字
	 */
	public static Map<String, Short> typeMap = new HashMap<String, Short>();	
	/**
	 * 根据type的值，取type的说明描述
	 */
	public static Map<Short, String> typeDescriptionMap = new HashMap<Short, String>();
	
	private Integer id; 
	private Integer userid;	//用户id，User.id
	private short type;
	private Integer goalid;	//目标id，如操作的帖子，则为帖子id，如果为消息，则为消息id
	private String value;
	private Date addtime;
	private short isdelete;	//此条纪录是否已删除，1删除，0未删除正常状态
	
	public Log() {
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 用户id， {@link User#id}
	 * @return
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 用户id， {@link User#id}
	 * @param userid
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 日志类型，其值位于src下 systemConfig.xml 的 logTypeList节点。
	 * <br/>使用时为 {@link Log#typeMap}.get("logTypeList节点配置的程序内调用的名字")
	 * @return
	 */
	public short getType() {
		return type;
	}
	/**
	 * 日志类型，其值位于src下 systemConfig.xml 的 logTypeList节点。
	 * <br/>使用时为 {@link Log#typeMap}.get("logTypeList节点配置的程序内调用的名字")
	 * @param type
	 */
	public void setType(short type) {
		this.type = type;
	}
	/**
	 * 操作的目标id。如发布了一片帖子，则这里便是对帖子进行的操作，这里为帖子的id
	 * @return
	 */
	public Integer getGoalid() {
		return goalid;
	}
	/**
	 * 操作的目标id。如发布了一片帖子，则这里便是对帖子进行的操作，这里为帖子的id
	 * @param goalid
	 */
	public void setGoalid(Integer goalid) {
		this.goalid = goalid;
	}
	/**
	 * 此日志的记录备注内容，20个字符以内
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 此日志的记录备注内容，传入的文字自动截取前20个字符
	 * @param value 值
	 */
	public void setValue(String value) {
		if(value!=null){
			if(value.length()>20){
				value = value.substring(0, 20);
			}
		}
		
		this.value = value;
	}
	/**
	 * 添加时间，Linux时间戳
	 * @return
	 */
	public Date getAddtime() {
		return addtime;
	}
	/**
	 * 添加时间，Linux时间戳
	 * @param addtime
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	/**
	 * 此日志是否被删除
	 * 		<ul>
	 * 			<li> {@link Log#ISDELETE_NORMAL}：未删除，正常状态 </li>
	 * 			<li> {@link Log#ISDELETE_DELETE}：已经删除 </li>
	 * 		</ul>
	 * @return
	 */
	public short getIsdelete() {
		return isdelete;
	}
	/**
	 * 	此日志是否被删除
	 * 		<ul>
	 * 			<li> {@link Log#ISDELETE_NORMAL}：未删除，正常状态 </li>
	 * 			<li> {@link Log#ISDELETE_DELETE}：已经删除 </li>
	 * 		</ul>
	 * @param isdelete
	 */
	public void setIsdelete(short isdelete) {
		this.isdelete = isdelete;
	}

}
