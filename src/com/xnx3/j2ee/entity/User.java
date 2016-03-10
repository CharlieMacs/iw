package com.xnx3.j2ee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;		//用户id
	private String username;	//用户名
	private String email;		//邮箱
	private String password;	//加密后的密码
	private String head;		//头像
	private String nickname;	//昵称
	private String authority;	//用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5   目前最多存放10个字符
	private Integer regtime;	//注册时间,时间戳
	private Integer lasttime;	//最后登录时间,时间戳
	private String regip;		//注册ip
	private String salt;		//shiro加密使用
	private String phone;		//手机号
	private Integer currency;	//资金，可以是积分、金币、等等
	private Integer referrerid;	//推荐人的用户id。若没有推荐人则默认为0

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String email, String password,
			String nickname, String authority, Integer regtime,
			Integer lasttime, String regip, String lastip) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.authority = authority;
		this.regtime = regtime;
		this.lasttime = lasttime;
		this.regip = regip;
	}

	/** full constructor */
	public User(String username, String email, String password, String head,
			String nickname, String authority, Integer regtime,
			Integer lasttime, String regip) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.head = head;
		this.nickname = nickname;
		this.authority = authority;
		this.regtime = regtime;
		this.lasttime = lasttime;
		this.regip = regip;
	}

	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Column(name = "username", nullable = false, length = 15)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "email", unique = true, nullable = false, length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "head", length = 15)
	public String getHead() {
		return this.head;
	}
	
//	/**
//	 * 获得头像文件的跟路径
//	 * @return
//	 */
//	public String getHeadPath() {
//		String headPath = null;
//		if (this.getHead() == null || this.getHead().length()<3) {
//			headPath = "../img/common/user.png";
//		} else {
//			headPath = "../upload/userHead/" + this.getHead();
//		}
//		return headPath;
//	}

	
	public void setHead(String head) {
		this.head = head;
	}

	@Column(name = "nickname", nullable = false, length = 30)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "authority", nullable = false)
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Column(name = "regtime", nullable = false)
	public Integer getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Integer regtime) {
		this.regtime = regtime;
	}

	@Column(name = "lasttime", nullable = false)
	public Integer getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Integer lasttime) {
		this.lasttime = lasttime;
	}

	@Column(name = "regip", nullable = false, length = 15)
	public String getRegip() {
		return this.regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Integer getReferrerid() {
		return referrerid;
	}

	public void setReferrerid(Integer referrerid) {
		this.referrerid = referrerid;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", head=" + head + ", nickname="
				+ nickname + ", authority=" + authority + ", regtime="
				+ regtime + ", lasttime=" + lasttime + ", regip=" + regip
				+ "]";
	}

}