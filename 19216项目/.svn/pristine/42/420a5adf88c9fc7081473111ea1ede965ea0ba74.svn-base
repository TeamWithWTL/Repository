package com.jcwx.entity.shgl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gaoshaui_2018-1-10
 * 任务处理
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shgl_task_deal_attrs_table")
public class RwclAttrEntity {
	
	private String id;						//id
	private String task_deal_id;			//任务ID
	private String file_type;				//文件类型
	private String old_filename;			//老文件名
	private String new_filename;			//新文件名
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTask_deal_id() {
		return task_deal_id;
	}
	public void setTask_deal_id(String task_deal_id) {
		this.task_deal_id = task_deal_id;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
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
