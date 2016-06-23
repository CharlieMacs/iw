package com.xnx3.j2ee.service.impl;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.OSSService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.ossbean.PutResult;

/**
 * 文件上传
 * @author 管雷鸣
 */
public class OSSServiceImpl implements OSSService {

	/**
	 * SpringMVC 带的文件上传
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param multipartFile SpringMVC接收的 {@link MultipartFile}
	 * @return {@link UploadFileVO}
	 */
	@Override
	public UploadFileVO upload(String filePath, MultipartFile multipartFile) {
		UploadFileVO vo = new UploadFileVO();
		if(multipartFile == null || multipartFile.isEmpty()){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_pleaseSelectUploadFile"));
			return vo;
		}
		
		PutResult pr = null;
		try {
			pr = OSSUtil.put(filePath, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			vo.setBaseVO(BaseVO.FAILURE, Global.getLanguage("oss_uploadFailure"));
			return vo;
		}
		
		vo.setPath(pr.getPath());
		vo.setFileName(pr.getFileName());
		vo.setUrl(pr.getUrl());
		
		return vo;
	}

	/**
	 * SpringMVC 上传图片文件，配置允许上传的文件后缀再 systemConfig.xml 的fileUpload节点
	 * @param filePath 上传后的文件所在OSS的目录、路径，如 "jar/file/"
	 * @param multipartFile SpringMVC接收的 {@link MultipartFile}
	 * @return {@link UploadFileVO}
	 */
	@Override
	public UploadFileVO uploadImage(String filePath, MultipartFile multipartFile) {
		UploadFileVO vo = new UploadFileVO();
		if(multipartFile == null){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_pleaseSelectUploadFile"));
			return vo;
		}
		
		//判断上传的文件后缀是否再指定的可上传文件里面
		String fileSuffix=com.xnx3.Lang.subString(multipartFile.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
		boolean find = false;	//是否再可上传后缀里发现此后缀
		String[] ia = Global.ossFileUploadImageSuffixList.split("\\|");
		for (int j = 0; j < ia.length; j++) {
			if(ia[j].length()>0){
				if(ia[j].equals(fileSuffix)){
					find = true;
					break;
				}
			}
		}
		if(!find){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_uploadFileNotInSuffixList"));
			return vo;
		}
		
		return upload(filePath, multipartFile);
	}
	
}
