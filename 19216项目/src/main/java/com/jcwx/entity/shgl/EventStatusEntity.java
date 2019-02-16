package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * 事件流转状态表
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_event_status_table")
public class EventStatusEntity implements Serializable {
	private String id;
	private String event_id;// 事件编号
	private Date start_time;// 开始时间
	private String startTimeFrm;
	private Date end_time;// 结束时间
	private String endTimeFrm;
	private String curent_node;// 当前流转节点编号
	private String status;// 状态 1.进行中 2.已结束

	List<EventFlowRecordEntity> eventFlowRecords;// 事件流转记录

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public Date getStart_time() {
		return start_time;
	}

	@Transient
	public String getStartTimeFrm() {
		return startTimeFrm;
	}

	public Date getEnd_time() {
		return end_time;
	}

	@Transient
	public String getEndTimeFrm() {
		return endTimeFrm;
	}

	public String getCurent_node() {
		return curent_node;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
		if (start_time != null) {
			setStartTimeFrm(DateUtils.formateDate(start_time, "yyyy-MM-dd"));
		}
	}

	public void setStartTimeFrm(String startTimeFrm) {
		this.startTimeFrm = startTimeFrm;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
		if (end_time != null) {
			setEndTimeFrm(DateUtils.formateDate(end_time, "yyyy-MM-dd"));
		}
	}

	public void setEndTimeFrm(String endTimeFrm) {
		this.endTimeFrm = endTimeFrm;
	}

	public void setCurent_node(String curent_node) {
		this.curent_node = curent_node;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(targetEntity = EventFlowRecordEntity.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id", referencedColumnName = "id")
	public List<EventFlowRecordEntity> getEventFlowRecords() {
		return eventFlowRecords;
	}

	public void setEventFlowRecords(List<EventFlowRecordEntity> eventFlowRecords) {
		this.eventFlowRecords = eventFlowRecords;
	}

}
