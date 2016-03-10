package com.xnx3.j2ee.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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
public class Log implements java.io.Serializable {
	
	private Integer id;
	private Integer userid;
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
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public Integer getGoalid() {
		return goalid;
	}
	public void setGoalid(Integer goalid) {
		this.goalid = goalid;
	}
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		if(value!=null){
			if(value.length()>20){
				value = value.substring(0, 20);
			}
		}
		
		this.value = value;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public short getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(short isdelete) {
		this.isdelete = isdelete;
	}

	
	
}
