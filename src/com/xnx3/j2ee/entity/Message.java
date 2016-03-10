package com.xnx3.j2ee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "message")
public class Message implements java.io.Serializable {
	/**
	 * 已读
	 */
	public final static Short MESSAGE_STATE_READ=1;
	/**
	 * 未读
	 */
	public final static Short MESSAGE_STATE_UNREAD=0;

	// Fields

	private Integer id;
	private Integer self;
	private Integer other;
	private Integer time;
	private Short state;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(Integer self, Integer other, Integer time, Short state) {
		this.self = self;
		this.other = other;
		this.time = time;
		this.state = state;
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

	@Column(name = "self", nullable = false)
	public Integer getSelf() {
		return this.self;
	}

	public void setSelf(Integer self) {
		this.self = self;
	}

	@Column(name = "other", nullable = false)
	public Integer getOther() {
		return this.other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	@Column(name = "time", nullable = false)
	public Integer getTime() {
		return this.time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	@Column(name = "state", nullable = false)
	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", self=" + self + ", other=" + other
				+ ", time=" + time + ", state=" + state + "]";
	}
	

}