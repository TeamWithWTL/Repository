package com.jcwx.entity.shgl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 事件附件表
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_event_attrs_table")
public class EventAttrsEntity implements Serializable {
	private String id;
	private String event_id;
	private String file_type;
	private String old_filename;
	private String new_filename;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public String getFile_type() {
		return file_type;
	}

	public String getOld_filename() {
		return old_filename;
	}

	public String getNew_filename() {
		return new_filename;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public void setOld_filename(String old_filename) {
		this.old_filename = old_filename;
	}

	public void setNew_filename(String new_filename) {
		this.new_filename = new_filename;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

}
