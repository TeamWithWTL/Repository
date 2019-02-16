package com.jcwx.entity.shzz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 	2017年10月24日
 * @author xushu
 * 	社会组织信息-附件
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_zzxx_attrs_table")
public class ZzxxAttrsEntity implements Serializable{
    
	private String id;           //主键
	private String shzzxx_id;    //社会组织信息ID
	private String fileType;    //文件类型
	private String oldFilename; //旧文件名
	private String newFilename; //新文件名
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShzzxx_id() {
		return shzzxx_id;
	}
	public void setShzzxx_id(String shzzxx_id) {
		this.shzzxx_id = shzzxx_id;
	}
	@Column(name="file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Column(name="old_filename")
	public String getOldFilename() {
		return oldFilename;
	}
	public void setOldFilename(String oldFilename) {
		this.oldFilename = oldFilename;
	}
	@Column(name="new_filename")
	public String getNewFilename() {
		return newFilename;
	}
	public void setNewFilename(String newFilename) {
		this.newFilename = newFilename;
	}
}
