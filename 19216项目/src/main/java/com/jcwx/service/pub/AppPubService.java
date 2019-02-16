package com.jcwx.service.pub;

public interface AppPubService {
	/**
	 * 保存附件公共方法
	 * 
	 * @param id
	 * @param moduleName
	 * @param oldFileName
	 * @param newFileName
	 */
	public void saveFile(String id, String moduleName, String oldFileName, String newFileName);

}
