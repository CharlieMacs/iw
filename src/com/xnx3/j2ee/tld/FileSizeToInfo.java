package com.xnx3.j2ee.tld;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.xnx3.Lang;

/**
 * 存储空间，B、KB、MB、GB的显示，传入基本的B，然后自动计算是KB、MB、GB显示
 * <br/>传入字节数剧，返回大小的描述信息，小数点后保留两位。
 * <br/>如传入：1234435742，返回：1.15 GB
 * @author 管雷鸣
 */
public class FileSizeToInfo extends TagSupport {
	private long size;	//传入的以B为单位的长度

	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(Lang.fileSizeToInfo(size));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
}
