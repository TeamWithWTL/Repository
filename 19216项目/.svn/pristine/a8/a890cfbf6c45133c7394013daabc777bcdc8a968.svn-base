package com.jcwx.entity.shzz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author m
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_hdgl_fk_attrs_table")
public class HdglFkAttrEntity {
	
	private String id;				//id	
	private String zxjsId;			//反馈ID
	private String fileType;		//文件类型
	private String oldFilename;		//文件原名
	private String newFilename;		//新文件名称
	
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "zxjs_id")
	public String getZxjsId() {
		return zxjsId;
	}
	public void setZxjsId(String zxjsId) {
		this.zxjsId = zxjsId;
	}
	
	
	@Column(name = "old_filename")
	public String getOldFilename() {
		return oldFilename;
	}
	public void setOldFilename(String oldFilename) {
		this.oldFilename = oldFilename;
	}
	
	@Column(name = "new_filename")
	public String getNewFilename() {
		return newFilename;
	}
	public void setNewFilename(String newFilename) {
		this.newFilename = newFilename;
	}
	
	@Column(name = "file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
