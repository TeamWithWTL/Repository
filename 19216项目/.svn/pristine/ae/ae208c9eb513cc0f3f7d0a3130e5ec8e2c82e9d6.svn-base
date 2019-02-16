package com.jcwx.entity.xtbg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 会议附件实体类
 * @author 李伟
 * @time 2017年10月29日上午1:50:32
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_hygl_ry_attrs_table")
public class MeetingAcceEntity implements Serializable {
	private String id;			//主键
	private String hygl_id; 		//会议管理ID
	private String scr_id;     //上传人id
	private String file_type;	//附件类型
	private String old_fileName;	//旧文件名
	private String new_fileName;	//新文件名
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHygl_id() {
		return hygl_id;
	}
	public void setHygl_id(String hygl_id) {
		this.hygl_id = hygl_id;
	}
	@Column(name="file_type")
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
		return "MeetingAcceEntity [id=" + id + ", hygl_id=" + hygl_id + ", file_type=" + file_type + ", old_fileName="
				+ old_fileName + ", new_fileName=" + new_fileName + "]";
	}
	public String getScr_id() {
		return scr_id;
	}
	public void setScr_id(String scr_id) {
		this.scr_id = scr_id;
	}
	
	
}
