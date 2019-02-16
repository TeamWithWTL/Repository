package com.jcwx.entity.shfw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 社会服务-政务信息附件Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial")
@Entity
@Table(name="shfw_zwxx_attrs_table") 
public class ShfwZwxxAttrsEntity implements Serializable {
	private String id;				//主键
	private String zwxxId;			//政务信息ID
	private String fileType;		//文件类型
	private String oldFilename;		//源文件名
	private String newFilename;		//新文件名（含路径）
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="zwxx_id")
	public String getZwxxId() {
		return zwxxId;
	}
	public void setZwxxId(String zwxxId) {
		this.zwxxId = zwxxId;
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
