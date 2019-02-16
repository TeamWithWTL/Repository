/**
 * 
 */
package com.jcwx.entity.xtbg;

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
@Table(name="xtbg_tzgg_attrs_table")
public class TzggArrtEntity {
	
	private String id;				//ID
	private String ry_id;			//通知公告ID
	private String fileType;		//文件类型
	private String old_filename;		//原文件名
	private String new_filename;		//新文件名
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")	
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Column(name="file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
	public String getRy_id() {
		return ry_id;
	}
	public void setRy_id(String ry_id) {
		this.ry_id = ry_id;
	}
	public String getNew_filename() {
		return new_filename;
	}
	public void setNew_filename(String new_filename) {
		this.new_filename = new_filename;
	}
	public String getOld_filename() {
		return old_filename;
	}
	public void setOld_filename(String old_filename) {
		this.old_filename = old_filename;
	}
	
	
}
