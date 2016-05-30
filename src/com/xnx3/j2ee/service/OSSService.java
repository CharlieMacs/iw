package com.xnx3.j2ee.service;

import org.springframework.web.multipart.MultipartFile;
import com.xnx3.j2ee.vo.UploadFileVO;

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
	 * @return {@link UploadFileVO}
	 */
	public UploadFileVO uploadImage(String filePath, MultipartFile multipartFile);
	
}
