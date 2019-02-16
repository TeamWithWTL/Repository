/**
 * 
 */
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
@Table(name ="shzz_hdgl_attrs_table")
public class HdglAttrEntity {
	
	private String id;					//id
	private String hdglId;				//活动管理ID		
	private String fileType;			//文件类型
	private String old_filename;			//原文件名
	private String new_filename;			//新文件名
	
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
	
	@Column(name ="hdgl_id")
	public String getHdglId() {
		return hdglId;
	}
	public void setHdglId(String hdglId) {
		this.hdglId = hdglId;
	}
	
	@Column(name ="file_type")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
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
	
	
}
