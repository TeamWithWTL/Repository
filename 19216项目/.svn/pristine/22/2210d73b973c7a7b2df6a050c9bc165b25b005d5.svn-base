package com.jcwx.entity.shzz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 	2017年10月21日
 * 	中心介绍-附件
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_zxjs_attrs_table")
public class ZxjsAttrsEntity implements Serializable{
	
	private String id;			//主键
	private String zxjsId; 		//中心介绍id
	private String fileType;	//附件类型
	private String oldFileName;	//旧文件名
	private String newFileName;	//新文件名
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Column(name = "old_filename")
	public String getOldFileName() {
		return oldFileName;
	}
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	@Column(name = "new_filename")
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	@Column(name="zxjs_id")
	public String getZxjsId() {
		return zxjsId;
	}
	public void setZxjsId(String zxjsId) {
		this.zxjsId = zxjsId;
	}
	
}
