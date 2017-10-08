package com.xnx3.j2ee.func;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.aliyuncs.green.model.v20160621.TextKeywordFilterResponse;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.net.MailUtil;
import com.xnx3.net.TxtFilterUtil;

/**
 * 文本过滤，检测文本是否是合法的
 * @author 管雷鸣
 */
public class TextFilter {
	
	static TxtFilterUtil txtFilterUtil;
	static String receiveEmail = "";
	
	static{
		receiveEmail = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("aliyunTextFilter.receiveEmail");
		String regionId = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("aliyunTextFilter.regionId");
		String accessKeyId = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("aliyunTextFilter.accessKeyId");
		String accessKeySecret = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("aliyunTextFilter.accessKeySecret");
		txtFilterUtil = new TxtFilterUtil(regionId, accessKeyId, accessKeySecret);
	}
	
	public static boolean filter(HttpServletRequest request, String title, String url, String text) {
		if(text == null || text.length() == 0){
			return false;
		}
		List<TextKeywordFilterResponse.KeywordResult> list = txtFilterUtil.filterGainList(text);
		if(list.size() > 0){
			//发现了违规，那么读取此用户发违规的条数，此次登陆Session中，记录了几次违规记录
			Object textIllegalNumber = request.getSession().getAttribute("textIllegalNumber");
			int IllegalNumber = 0;
			if(textIllegalNumber != null){
				IllegalNumber = (int) textIllegalNumber;
			}
			IllegalNumber++;		//增加一次纪律记录
			request.getSession().setAttribute("textIllegalNumber", IllegalNumber);
			
			//如果是第一次或者第五次，则发送邮件记录
			if(IllegalNumber - 1 == 0 || IllegalNumber - 5 == 0){
				int length = list.size();
				String IllegalText = "";
				for (int i = 0; i < length; i++) {
					TextKeywordFilterResponse.KeywordResult kr = list.get(i);
					IllegalText += MailUtil.BR + (i+1) +"."+kr.getKeywordCtx();
				}
				
				String sendContent = "";	//发送邮件的内容
				if(IllegalNumber - 5 == 0){
					sendContent = "警告，当前此用户已发现五次疑似违规文本记录！"+MailUtil.BR+MailUtil.BR;
				}
				sendContent = sendContent+"疑似违规内容如下："+IllegalText+MailUtil.BR+url;
				MailUtil.sendMail(receiveEmail, title, sendContent);
			}
			return true;
		}
		return false;
	}

	
}
