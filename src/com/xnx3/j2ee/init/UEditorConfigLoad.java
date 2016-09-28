package com.xnx3.j2ee.init;

import org.apache.log4j.Logger;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.net.OSSUtil;

/**
 * 初始化UEditor编辑器的一些配置
 * 目前这个没用到，初始化数据在 com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties
 * @author 管雷鸣
 */
public class UEditorConfigLoad {
	private static Logger logger = Logger.getLogger(UEditorConfigLoad.class);  
	
	public UEditorConfigLoad() {
		new OSSUtil();
		OSSClientProperties.useStatus = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.useOSS").equals("true");
		logger.info("UEditor used OSS : "+OSSClientProperties.useStatus);
		OSSClientProperties.bucketName = OSSUtil.bucketName;
		OSSClientProperties.key = OSSUtil.accessKeyId;
		OSSClientProperties.secret = OSSUtil.accessKeySecret;
		OSSClientProperties.autoCreateBucket = false;			//不自动创建Bucket，需手动创建
		
		String url = OSSUtil.url;
		int lastUrlx = url.lastIndexOf("/");
		if((lastUrlx+1) == url.length()){
			url = url.substring(0, lastUrlx);
		}
		
		//如果用的阿里云自带的外网域名，自动截取 endpoint
		if(url.indexOf(".aliyuncs.com") > -1){
			int start = url.indexOf("://")+3;
			int end = url.indexOf(".");
			if(url.substring(start,end).equals(OSSUtil.bucketName)){
				OSSClientProperties.ossCliendEndPoint = url.substring(0,start)+url.substring(end+1,url.length());
			}
		}else{
			OSSClientProperties.ossCliendEndPoint = url;
		}
		OSSClientProperties.ossEndPoint = url;
		OSSClientProperties.useCDN = false;		//默认为false，反正这几个point都一样，只要xnx3Config.xml 的 aliyunOSS.url 配置上CDN的请求域名，便是用cdn
		OSSClientProperties.cdnEndPoint = url;
		
		OSSClientProperties.useLocalStorager = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.useLocalStorager").equals("true");
//		OSSClientProperties.uploadBasePath = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.uploadBasePath");
		OSSClientProperties.astrictUpload = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.astrictUpload").equals("true");
		OSSClientProperties.useAsynUploader = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.useAsynUploader").equals("true");
		OSSClientProperties.astrictUploadMessage = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("UEditor.aliyunOSS.astrictUploadMessage");
		
		logger.info("load ueditor config , OSSClientProperties.astrictUpload : "+OSSClientProperties.astrictUpload);
	}
	
	public static void main(String[] args) {
		new UEditorConfigLoad();
	}
}
