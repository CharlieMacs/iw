package com.xnx3.j2ee.vo;

/**
 * aliyun OSS文件上传
 * @author 管雷鸣
 */
public class UploadFileVO extends BaseVO {
	
	private String fileName;	//上传成功后的文件名，如 "xnx3.jar"
	private String path;		//上传成功后的路径，如 "/jar/file/xnx3.jar"
	
	public UploadFileVO() {
	}
	
	/**
	 * 这是OSS上传成功后的返回值
	 * @param fileName 上传成功后的文件名，如 "xnx3.jar"
	 * @param path 上传成功后的路径，如 "/jar/file/xnx3.jar"
	 */
	public UploadFileVO(String fileName,String path) {
		this.fileName = fileName;
		this.path = path;
	}
	
	/**
	 * 上传成功后的文件名，如 "xnx3.jar"
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 上传成功后的文件名，如 "xnx3.jar"
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 上传成功后的路径，如 "/jar/file/xnx3.jar"
	 * @return
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 上传成功后的路径，如 "/jar/file/xnx3.jar"
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PutResult [fileName=" + fileName + ", path=" + path + "]";
	}
}
