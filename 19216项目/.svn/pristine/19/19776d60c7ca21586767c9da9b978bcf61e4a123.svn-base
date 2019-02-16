package com.jcwx.entity.shgl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * 事件流转记录表
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "event_flow_record_table")
public class EventFlowRecordEntity {
	private String id;// ID
	private String status_id;// 所属流转状态编号
	private String result;// 处理结果
	private String done_code;// 处理人编号
	private String done_name;// 处理人姓名
	private Date done_time;// 处理时间
	private String doneTimeFrm;// 处理时间格式化
	private String is_open;// 是否公开0.否 1.是
	private String done_status;// 是否处理 0.未处理 1.已处理

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public String getStatus_id() {
		return status_id;
	}

	public String getResult() {
		return result;
	}

	public String getDone_code() {
		return done_code;
	}

	public String getDone_name() {
		return done_name;
	}

	public Date getDone_time() {
		return done_time;
	}

	@Transient
	public String getDoneTimeFrm() {
		return doneTimeFrm;
	}

	public String getIs_open() {
		return is_open;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setDone_code(String done_code) {
		this.done_code = done_code;
	}

	public void setDone_name(String done_name) {
		this.done_name = done_name;
	}

	public void setDone_time(Date done_time) {
		this.done_time = done_time;
		if (done_time != null) {
			setDoneTimeFrm(DateUtils.formateDate(done_time, "yyyy-MM-dd"));
		}
	}

	public void setDoneTimeFrm(String doneTimeFrm) {
		this.doneTimeFrm = doneTimeFrm;
	}

	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}

	public String getDone_status() {
		return done_status;
	}

	public void setDone_status(String done_status) {
		this.done_status = done_status;
	}

}
