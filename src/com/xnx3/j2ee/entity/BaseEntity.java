package com.xnx3.j2ee.entity;

public class BaseEntity implements java.io.Serializable {
	/**
	 * 信息状态，是否已删除：未删除，正常状态
	 */
	public final static Short ISDELETE_NORMAL=0;
	
	/**
	 * 信息状态，是否已删除：已删除
	 */
	public final static Short ISDELETE_DELETE=1;
	
	public Short isdelete;

}
