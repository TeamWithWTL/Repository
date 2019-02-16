package com.jcwx.entity.dflz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.annotations.GenericGenerator;
/**
 * 投诉举报附件实体类
 * @author 李伟
 * @time 2017年10月26日上午11:15:38
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_tsjb_attr_table")
public class CompAcceEntity implements Serializable{
	private String id; //举报附件id
	private String tsjb_id;//举报实体 关联id
	private String file_type;//附件文件类型
	private String old_fileName;//上传前文件名
	private String new_fileName;//上传后文件名
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTsjb_id() {
		return tsjb_id;
	}
	public void setTsjb_id(String tsjb_id) {
		this.tsjb_id = tsjb_id;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	@Column(name="old_filename")
	public String getOld_fileName() {
		return old_fileName;
	}
	public void setOld_fileName(String old_fileName) {
		this.old_fileName = old_fileName;
	}
	@Column(name="new_filename")
	public String getNew_fileName() {
		return new_fileName;
	}
	public void setNew_fileName(String new_fileName) {
		this.new_fileName = new_fileName;
	}
	@Override
	public String toString() {
		return "CompAcceEntity [id=" + id + ", tsjb_id=" + tsjb_id + ", file_type=" + file_type + ", old_fileName="
				+ old_fileName + ", new_fileName=" + new_fileName + "]";
	}
	
	
}
