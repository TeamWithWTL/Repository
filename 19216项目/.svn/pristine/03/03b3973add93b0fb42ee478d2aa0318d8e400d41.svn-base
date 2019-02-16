package com.jcwx.entity.dflz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 党政要闻附件实体类
 * @author 李伟
 * @time 2017年10月21日下午3:57:45
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_yw_attrs_table")
public class AccessoryEntity implements Serializable{
	
	private String id;  //附件id
	 private String yw_id;// 要闻id
	 private String file_type;//文件类型
	 private String old_fileName;//旧文件名
	 private String new_fileName;//新文件名
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	@Column(name="file_type") //实体类属性对应表字段
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
	@Override
	public String toString() {
		return "AccessoryEntity [id=" + id + ", yw_id=" + yw_id + ", file_type=" + file_type + ", old_fileName="
				+ old_fileName + ", new_fileName=" + new_fileName + "]";
	}
	 
	 
}