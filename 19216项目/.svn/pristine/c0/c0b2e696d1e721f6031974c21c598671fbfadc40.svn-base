package com.jcwx.entity.dflz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 曝光台附件实体类
 * @author 李伟
 * @time 2017年10月21日下午4:54:39
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_bgt_attrs_table")
public class ExpAcceEntity implements Serializable {
	 private String id;    //曝光台附件id
	 private String new_id;  //曝光台附件关联曝光台id
	 private String file_type; //曝光台附件文件类型
	 private String old_fileName; //曝光台附件 旧文件名
	 private String new_fileName;  //曝光台附件 新文件名
	 
	 @Id
	 @GeneratedValue(generator = "uuid")
	 @GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNew_id() {
		return new_id;
	}
	public void setNew_id(String new_id) {
		this.new_id = new_id;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	@Column(name="old_filename") //实体类属性对应表字段 
	public String getOld_fileName() {
		return old_fileName;
	}
	public void setOld_fileName(String old_fileName) {
		this.old_fileName = old_fileName;
	}
	@Column(name="new_filename") //实体类属性对应表字段 
	public String getNew_fileName() {
		return new_fileName;
	}
	public void setNew_fileName(String new_fileName) {
		this.new_fileName = new_fileName;
	}
	 
	 
}
