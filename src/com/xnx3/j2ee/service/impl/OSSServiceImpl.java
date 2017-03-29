package com.xnx3.j2ee.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse.Credentials;
import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.OSSService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.media.ImageUtil;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.ossbean.CredentialsVO;
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
		
		String fileSuffix=com.xnx3.Lang.subString(multipartFile.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
		try {
			return uploadImage(filePath, multipartFile.getInputStream(), fileSuffix);
		} catch (IOException e) {
			e.printStackTrace();
			vo.setBaseVO(BaseVO.FAILURE, e.getMessage());
			return vo;
		}
	}

	private UploadFileVO uploadImage(String filePath, InputStream inputStream, String fileSuffix) {
		UploadFileVO vo = new UploadFileVO();
		boolean find = false;	//是否再可上传后缀里发现此后缀
		String[] ia = Global.ossFileUploadImageSuffixList.split("\\|");
		for (int j = 0; j < ia.length; j++) {
			if(ia[j].length()>0){
				if(ia[j].equalsIgnoreCase(fileSuffix)){
					find = true;
					break;
				}
			}
		}
		if(!find){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_uploadFileNotInSuffixList"));
			return vo;
		}
		
		if(inputStream == null){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_pleaseSelectUploadFile"));
			return vo;
		}
		
		PutResult pr = null;
		pr = OSSUtil.put(filePath, "."+fileSuffix, inputStream);
		
		vo.setPath(pr.getPath());
		vo.setFileName(pr.getFileName());
		vo.setUrl(pr.getUrl());
		
		return vo;
	}
	
	@Override
	public CredentialsVO createGetAndPutObjectSTS() {
		String policy = "{\n" +
				"    \"Version\": \"1\", \n" +
				"    \"Statement\": [\n" +
				"        {\n" +
				"            \"Action\": [\n" +
				"                \"oss:PutObject\", \n" +
				"                \"oss:GetObject\" \n" +
				"            ], \n" +
				"            \"Resource\": [\n" +
				"                \"acs:oss:*:*:*\"\n" +
				"            ], \n" +
				"            \"Effect\": \"Allow\"\n" +
				"        }\n" +
				"    ]\n" +
				"}";
		String id = "";
		if(ShiroFunc.getUser() == null){
			id = Lang.uuid();
		}else{
			id = "user"+ShiroFunc.getUser().getId();
		}
		
		Credentials credentials = OSSUtil.createSTS(id, policy);
		CredentialsVO cVO = new CredentialsVO();
		if(credentials == null){
			cVO.setBaseVO(com.xnx3.BaseVO.FAILURE, "创建失败");
		}else{
			cVO.setAccessKeyId(credentials.getAccessKeyId());
			cVO.setAccessKeySecret(credentials.getAccessKeySecret());
			cVO.setExpiration(credentials.getExpiration());
			cVO.setSecurityToken(credentials.getSecurityToken());
		}
		
		return cVO;
	}

	@Override
	public UploadFileVO uploadImage(String filePath, MultipartFile multipartFile, int maxWidth) {
		UploadFileVO vo = new UploadFileVO();
		if(multipartFile == null){
			vo.setBaseVO(UploadFileVO.FAILURE, Global.getLanguage("oss_pleaseSelectUploadFile"));
			return vo;
		}
		
		String fileSuffix=com.xnx3.Lang.subString(multipartFile.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
		try {
			return uploadImage(filePath, ImageUtil.proportionZoom(multipartFile.getInputStream(), maxWidth, fileSuffix), fileSuffix);
		} catch (IOException e) {
			e.printStackTrace();
			vo.setBaseVO(BaseVO.FAILURE, e.getMessage());
			return vo;
		}
	}

	@Override
	public UploadFileVO uploadImage(String filePath,HttpServletRequest request,String formFileName, int maxWidth) {
		UploadFileVO uploadFileVO = new UploadFileVO();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			List<MultipartFile> imageList = multipartRequest.getFiles(formFileName);  
			if(imageList.size()>0 && !imageList.get(0).isEmpty()){
				MultipartFile multi = imageList.get(0);
				String fileSuffix=com.xnx3.Lang.subString(multi.getOriginalFilename(), ".", null, 3);	//获得文件后缀，以便重命名
				try {
					uploadFileVO = uploadImage(filePath,ImageUtil.proportionZoom(multi.getInputStream(), maxWidth, fileSuffix) , fileSuffix);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				uploadFileVO.setResult(UploadFileVO.NOTFILE);
				uploadFileVO.setInfo(Global.getLanguage("oss_uploadNotFile"));
			}
	    }else{
	    	uploadFileVO.setResult(UploadFileVO.NOTFILE);
			uploadFileVO.setInfo(Global.getLanguage("oss_uploadNotFile"));
	    }
		return uploadFileVO;
	}

	@Override
	public UploadFileVO uploadImage(String filePath, HttpServletRequest request, String formFileName) {
		UploadFileVO uploadFileVO = new UploadFileVO();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			List<MultipartFile> imageList = multipartRequest.getFiles(formFileName);  
			
			if(imageList.size()>0 && !imageList.get(0).isEmpty()){
				uploadFileVO = uploadImage(filePath, imageList.get(0));
			}else{
				uploadFileVO.setResult(UploadFileVO.NOTFILE);
				uploadFileVO.setInfo(Global.getLanguage("oss_uploadNotFile"));
			}
	    }else{
	    	uploadFileVO.setResult(UploadFileVO.NOTFILE);
			uploadFileVO.setInfo(Global.getLanguage("oss_uploadNotFile"));
	    }
		return uploadFileVO;
	}
	
}
