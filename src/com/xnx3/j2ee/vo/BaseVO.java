package com.xnx3.j2ee.vo;

/**
 * <b>result</b>：执行成功{@link #SUCCESS}/失败{@link #FAILURE}
 * <br/><b>info</b>：执行结果，若成功，此项可忽略，若失败，返回失败原因
 * <br/>(所有JSON返回值VO的父类)
 * @author 管雷鸣
 *
 */
public class BaseVO extends com.xnx3.BaseVO {
	
	public BaseVO(){
		super();
	}
	
	/**
	 * 将 {@link com.xnx3.BaseVO} 转为 {@link com.xnx3.j2ee.vo.BaseVO}
	 * @param baseVO
	 */
	public void setBaseVOForSuper(com.xnx3.BaseVO baseVO){
		setBaseVO(baseVO.getResult(), baseVO.getInfo());
	}
	
}
