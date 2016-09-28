package com.xnx3.j2ee.tld;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 输出图片时，图片路径的判断，判断其是相对路径还是绝对路径。如果是相对路径，则加入网址前缀，拼接为绝对路径
 * @author 管雷鸣
 */
public class ImgUrl extends TagSupport {
	private String prefixUrl;		//如果图片路径img是一个相对路径，则加入此作为网址前缀，拼接为绝对路径
	private String img;			//图片路径
	
	public String getPrefixUrl() {
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		String url = img;
		if(url.indexOf("://") == -1){
			url = this.prefixUrl+url;
		}
		
		try {
			writer.print(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
}
