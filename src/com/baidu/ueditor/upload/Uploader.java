package com.baidu.ueditor.upload;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

//import com.aliyun.openservices.oss.OSSClient;
//import com.aliyun.openservices.oss.model.Bucket;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.qikemi.packages.alibaba.aliyun.oss.BucketService;
//import com.qikemi.packages.alibaba.aliyun.oss.OSSClientFactory;
import com.qikemi.packages.alibaba.aliyun.oss.properties.OSSClientProperties;
import com.qikemi.packages.baidu.ueditor.upload.AsynUploaderThreader;
import com.qikemi.packages.baidu.ueditor.upload.SynUploader;
import com.qikemi.packages.utils.SystemUtil;
import com.xnx3.j2ee.func.AttachmentFile;
import com.xnx3.j2ee.shiro.ShiroFunc;

/**
 * 同步上传文件到阿里云OSS<br>
 * 
 * @create date : 2014年10月28日 上午22:15:00
 * @Author XieXianbin<a.b@hotmail.com>
 * @Source Repositories Address:
 *         <https://github.com/qikemi/UEditor-for-aliyun-OSS>
 */
public class Uploader {
	private static Logger logger = Logger.getLogger(Uploader.class);

	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public State doExecss(){
		State state = null;
		state = BinaryUploader.save(this.request, this.conf);
		JSONObject stateJson = new JSONObject(state.toJSONString());
		String bucketName = OSSClientProperties.bucketName;
//		OSSClient client = OSSClientFactory.createOSSClient();
		if (OSSClientProperties.useAsynUploader) {
			AsynUploaderThreader asynThreader = new AsynUploaderThreader();
//			asynThreader.init(stateJson, client, this.request);
			asynThreader.init(stateJson, this.request);
			Thread uploadThreader = new Thread(asynThreader);
			uploadThreader.start();
		} else {
			SynUploader synUploader = new SynUploader();
//			synUploader.upload(stateJson, client, this.request);
			synUploader.upload(stateJson, this.request);
		}
		if (false == OSSClientProperties.useLocalStorager) {
			String uploadFilePath = (String) this.conf.get("rootPath") + (String) stateJson.get("url");
			File uploadFile = new File(uploadFilePath);
			if (uploadFile.isFile() && uploadFile.exists()) {
				uploadFile.delete();
			}
		}
		state.putInfo("url", OSSClientProperties.ossEndPoint + stateJson.getString("url"));
		return state;
	}
	
	public final State doExec() {
		State state = null;
		//是否有限制某用户上传
		if(OSSClientProperties.astrictUpload){
			if(!ShiroFunc.getUEditorAllowUpload()){
				return new BaseState(false, OSSClientProperties.astrictUploadMessage);
			}
		}
		
		String filedName = (String) this.conf.get("fieldName");
		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf);
			JSONObject stateJson = new JSONObject(state.toJSONString());
			// 判别云同步方式
			if (OSSClientProperties.useStatus) {

				String bucketName = OSSClientProperties.bucketName;
//				OSSClient client = OSSClientFactory.createOSSClient();
				// auto create Bucket to default zone
//				if (OSSClientProperties.autoCreateBucket) {
//					Bucket bucket = BucketService.create(client, bucketName);
//					logger.debug("Bucket 's " + bucket.getName() + " Created.");
//				}

				// upload type
				if (OSSClientProperties.useAsynUploader) {
					AsynUploaderThreader asynThreader = new AsynUploaderThreader();
//					asynThreader.init(stateJson, client, this.request);
					asynThreader.init(stateJson, this.request);
					Thread uploadThreader = new Thread(asynThreader);
					uploadThreader.start();
				} else {
					SynUploader synUploader = new SynUploader();
//					synUploader.upload(stateJson, client, this.request);
					synUploader.upload(stateJson, this.request);
				}

				// storage type
				if (false == OSSClientProperties.useLocalStorager) {
					String uploadFilePath = (String) this.conf.get("rootPath") + (String) stateJson.get("url");
					File uploadFile = new File(uploadFilePath);
					if (uploadFile.isFile() && uploadFile.exists()) {
						uploadFile.delete();
					}
				}

				state.putInfo(
						"url",
						OSSClientProperties.ossEndPoint
								+ stateJson.getString("url"));
			} else {
				//绝对路径，而非原本的相对路径
				state.putInfo("url",  AttachmentFile.netUrl() + SystemUtil.getProjectName()
						+ stateJson.getString("url"));
			}
		}
		/*
		 * { "state": "SUCCESS", "title": "1415236747300087471.jpg", "original":
		 * "a.jpg", "type": ".jpg", "url":
		 * "/upload/image/20141106/1415236747300087471.jpg", "size": "18827" }
		 */
		logger.debug(state.toJSONString());
		return state;
	}
}
