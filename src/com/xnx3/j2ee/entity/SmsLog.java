package com.xnx3.j2ee.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SmsLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sms_log")
public class SmsLog implements java.io.Serializable {
	/**
	 * 登录
	 */
	public final static short TYPE_LOGIN = 1;
	/**
	 * 找回密码
	 */
	public final static short TYPE_FIND_PASSWORD = 2;
	/**
	 * 绑定手机
	 */
	public final static short TYPE_BIND_PHONE = 3;
	
	/**
	 * 已使用
	 */
	public final static short USED_TRUE = 1;
	
	/**
	 * 未使用
	 */
	public final static short USED_FALSE = 0;
	
	
	// Fields

	private Integer id;
	private String code;
	private Integer userid;
	private Short used;
	private Short type;
	private Integer addtime;
	private String phone;
	private String ip;

	// Constructors

	/** default constructor */
	public SmsLog() {
	}

	/** minimal constructor */
	public SmsLog(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SmsLog(Integer id, String code, Integer userid, Short used,
			Short type, Integer addtime) {
		this.id = id;
		this.code = code;
		this.userid = userid;
		this.used = used;
		this.type = type;
		this.addtime = addtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "used")
	public Short getUsed() {
		return this.used;
	}

	public void setUsed(Short used) {
		this.used = used;
	}

	@Column(name = "type")
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "addtime")
	public Integer getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	

}