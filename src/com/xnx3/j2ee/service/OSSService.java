package com.xnx3.j2ee.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse.Credentials;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.ossbean.CredentialsVO;

/**
 * OSS文件上传
 * @author 管雷鸣
 */
public interface OSSService {
	
	/**
	 * SpringMVC 带的文件上传
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param multipartFile SpringMVC接收的 {@link MultipartFile}
	 * @return {@link UploadFileVO}
	 */
	public UploadFileVO upload(String filePath, MultipartFile multipartFile);
	
	/**
	 * SpringMVC 上传图片文件，配置允许上传的文件后缀再 systemConfig.xml 的OSS节点
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param multipartFile SpringMVC接收的 {@link MultipartFile}
	 * @param maxWidth 上传图片的最大宽度，若超过这个宽度，会对图片进行等比缩放为当前宽度。
	 * @return {@link UploadFileVO}
	 */
	public UploadFileVO uploadImage(String filePath, MultipartFile multipartFile,int maxWidth);
	
	/**
	 * SpringMVC 上传图片文件，配置允许上传的文件后缀再 systemConfig.xml 的OSS节点
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param request SpringMVC接收的 {@link MultipartFile},若是有上传图片文件，会自动转化为{@link MultipartFile}保存
	 * @param formFileName form表单上传的单个图片文件，表单里上传文件的文件名
	 * @param maxWidth 上传图片的最大宽度，若超过这个宽度，会对图片进行等比缩放为当前宽度。
	 * @return {@link UploadFileVO} 若成功，则上传了文件并且上传成功
	 */
	public UploadFileVO uploadImage(String filePath, HttpServletRequest request,String formFileName, int maxWidth);
	
	/**
	 * SpringMVC 上传单个图片文件，配置允许上传的文件后缀再 systemConfig.xml 的OSS节点
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param request SpringMVC接收的 {@link MultipartFile},若是有上传图片文件，会自动转化为{@link MultipartFile}保存
	 * @param formFileName form表单上传的单个图片文件，表单里上传文件的文件名
	 * @return {@link UploadFileVO} 若成功，则上传了文件并且上传成功
	 */
	public UploadFileVO uploadImage(String filePath, HttpServletRequest request, String formFileName);
	
	/**
	 * SpringMVC 上传图片文件，配置允许上传的文件后缀再 systemConfig.xml 的OSS节点
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param multipartFile SpringMVC接收的 {@link MultipartFile}
	 * @return {@link UploadFileVO}
	 */
	public UploadFileVO uploadImage(String filePath, MultipartFile multipartFile);
	
	
	
	/**
	 * 创建 授权于OSS GetObject、PutObject 权限的临时账户（此只是针对 {@link OSSUtil#createSTS(String, String)}的简化 ）
	 * @return {@link Credentials}
	 */
	public CredentialsVO createGetAndPutObjectSTS();
}
