package com.xnx3.j2ee.tld;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.xnx3.j2ee.Global;

/**
 * 调用语言包显示
 * @author 管雷鸣
 */
public class Language extends TagSupport {
	private String key;		//要调用的语言文字的key值
	private String remark;	//备注，程序中无任何作用，只是给开发人员自己看
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(Global.getLanguage(key));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
}
