package com.xnx3.j2ee.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.ossbean.PutResult;

/**
 * 附件的操作，如OSS、或服务器本地文件
 * 如果时localFile ，则需要设置 AttachmentFile.netUrl
 * @author 管雷鸣
 */
public class AttachmentFile {
	public static String mode;	//当前文件附件存储使用的模式，用的阿里云oss，还是服务器本身磁盘进行存储
	public static final String MODE_ALIYUN_OSS = "aliyunOSS";		//阿里云OSS模式存储
	public static final String MODE_LOCAL_FILE = "localFile";		//服务器本身磁盘进行附件存储
	
	//文件路径，文件所在。oss则为OSSUtil.url， localFile则是存储到磁盘，访问时自然就是主域名
	public static String netUrl = null;
	
	//如果附件保存在当前服务器上，则保存的路径是哪个
	public static String localFilePath = "";	
	
	static{
		mode = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("attachmentFile.mode");
		localFilePath = Global.getProjectPath();
		System.out.println("localFilePath : "+localFilePath);
	}
	
	/**
	 * 判断当前文件附件存储使用的是哪种模式，存储到什么位置
	 * @param mode 存储的代码，可直接传入如 {@link #MODE_ALIYUN_OSS}
	 * @return 是否使用
	 * 			<ul>
	 * 				<li>true ： 是此种模式</li>
	 * 				<li>false ： 不是此种模式</li>
	 * 			</ul>
	 */
	public static boolean isMode(String mode){
		if(mode == null || AttachmentFile.mode == null){
			return false;
		}
		return AttachmentFile.mode.equals(mode);
	}
	
	
	/**
	 * 获取附件访问的url地址
	 * @return 返回如 http://res.weiunity.com/ 
	 */
	public static String netUrl(){
		if(netUrl == null){
			if(isMode(MODE_ALIYUN_OSS)){
				return OSSUtil.url;
			}else if(isMode(MODE_LOCAL_FILE)){
				//如果有当前网站的域名，那么返回域名，格式如"http://www.xnx3.com/" 。如果没有，则返回"/"
				return "http://localhost:8081/";
			}else{
				return "";
			}
		}
		return netUrl;
	}
	
	/**
	 * 给出文本内容，写出文件
	 * @param path 写出的路径,上传后的文件所在的目录＋文件名，如 "jar/file/xnx3.html"
	 * @param text 文本内容
	 */
	public static void putStringFile(String path, String text){
		if(isMode(MODE_ALIYUN_OSS)){
			OSSUtil.putStringFile(path, text);
		}else if(isMode(MODE_LOCAL_FILE)){
			FileUtil.write(localFilePath+path, text);
		}
	}
	
	/**
	 * 上传本地文件
	 * @param filePath 上传后的文件所在的目录、路径，如 "jar/file/"
	 * @param localPath 本地要上传的文件的绝对路径，如 "/jar_file/iw.jar"
	 * @return {@link PutResult} 若失败，返回null
	 */
	public static PutResult put(String filePath, String localPath){
		if(isMode(MODE_ALIYUN_OSS)){
			return OSSUtil.put(filePath, localPath);
		}else if(isMode(MODE_LOCAL_FILE)){
			PutResult pr = new PutResult();
			
			File localFile = new File(localPath);
			if(localFile == null){
				//本地文件不存在
				return pr;
			}
			try {
				InputStream localInput = new FileInputStream(localFile);
				//将保存到本地
				return put(filePath, localInput);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new PutResult();
	}
	
	/**
	 * 上传文件。上传后的文件名固定
	 * @param path 上传到哪里，包含上传后的文件名，如"image/head/123.jpg"
	 * @param inputStream 文件
	 * @return {@link PutResult}
	 */
	public static PutResult put(String path,InputStream inputStream){
		PutResult pr = new PutResult();
		if(isMode(MODE_ALIYUN_OSS)){
			return OSSUtil.put(path, inputStream);
		}else if(isMode(MODE_LOCAL_FILE)){
			File file = new File(localFilePath+path);
			OutputStream os;
			try {
				os = new FileOutputStream(file);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				inputStream.close();
				
				pr.setFileName(file.getName());
				pr.setPath(file.getPath());
				pr.setUrl(AttachmentFile.netUrl()+file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return pr;
	}
	
	/**
	 * 传入一个路径，得到其源代码(文本)
	 * @param path 要获取的文本内容的路径，如  site/123/index.html
	 * @return 返回其文本内容。若找不到，或出错，则返回 null
	 */
	public static String getTextByPath(String path){
		OSSObject ossObject = OSSUtil.getOSSClient().getObject(OSSUtil.bucketName, path);
		if(ossObject == null){
			return "";
		}else{
			if(isMode(MODE_ALIYUN_OSS)){
				try {
					return IOUtils.toString(ossObject.getObjectContent(), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}else if(isMode(MODE_LOCAL_FILE)){
				return FileUtil.read(localFilePath+path, FileUtil.UTF8);
			}
		}
		
		return null;
	}
	
	/**
	 * 删除文件
	 * @param filePath 文件所在的路径，如 "jar/file/xnx3.jpg"
	 */
	public static void deleteObject(String filePath){
		if(isMode(MODE_ALIYUN_OSS)){
			OSSUtil.getOSSClient().deleteObject(OSSUtil.bucketName, filePath);
		}else if(isMode(MODE_LOCAL_FILE)){
			FileUtil.deleteFile(localFilePath+filePath);
		}
		
	}
	
	/**
	 * 复制文件
	 * @param originalFilePath 原本文件所在的路径(相对路径，非绝对路径，操作的是当前附件文件目录下)
	 * @param newFilePath 复制的文件所在的路径，所放的路径。(相对路径，非绝对路径，操作的是当前附件文件目录下)
	 */
	public static void copyObject(String originalFilePath, String newFilePath){
		if(isMode(MODE_ALIYUN_OSS)){
			OSSUtil.getOSSClient().copyObject(OSSUtil.bucketName, originalFilePath, OSSUtil.bucketName, newFilePath);
		}else if(isMode(MODE_LOCAL_FILE)){
			FileUtil.copyFile(localFilePath + originalFilePath, localFilePath + newFilePath);
		}
	}
	
	/**
	 * 上传文件。UEditor使用
	 * @param filePath 上传到哪里，包含上传后的文件名，如"image/head/123.jpg"
	 * @param content 文件
	 * @param meta {@link com.aliyun.oss.model.ObjectMetadata}其他属性、说明
	 */
	public static void putForUEditor(String filePath, InputStream input, ObjectMetadata meta){
		if(isMode(MODE_ALIYUN_OSS)){
			com.aliyun.oss.model.ObjectMetadata m = new com.aliyun.oss.model.ObjectMetadata();
			m.setContentLength(meta.getContentLength());
			m.setUserMetadata(meta.getUserMetadata());
			OSSUtil.getOSSClient().putObject(OSSUtil.bucketName, filePath, input, m);
		}else if(isMode(MODE_LOCAL_FILE)){
			put(filePath, input);
		}
	}
}
