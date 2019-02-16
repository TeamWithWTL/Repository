package com.jcwx.entity.shzz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author msy
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_dtbb_attrs_table")
public class DtbbAttrEntity implements Serializable{
	private String id;					//附件id
	private String dtbb_id;				//动态播报ID
	private String fileType;			//文件类型
	private String old_filename;		//原文件名
	public String getOld_filename() {
		return old_filename;
	}
	public void setOld_filename(String old_filename) {
		this.old_filename = old_filename;
	}
	public String getNew_filename() {
		return new_filename;
	}
	public void setNew_filename(String new_filename) {
		this.new_filename = new_filename;
	}
	private String new_filename;		//新文件名
	
	
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
	
	
	@Column(name ="file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getDtbb_id() {
		return dtbb_id;
	}
	public void setDtbb_id(String dtbb_id) {
		this.dtbb_id = dtbb_id;
	}
	
	

}
