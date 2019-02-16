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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * 事件管理表
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_event_table")
public class EventEntity implements Serializable {
	private String id;// ID
	private ShglGridEntity grid;// 所属网格id
	private String comm_id;// 社区id
	private String title;// 事件标题
	private String content;// 事件内容
	private String type;// 事件类型
	private String typeName;// 事件类型名称
	private String apply_code;// 上报人编号
	private String apply_name;// 上报人姓名
	private Date apply_time;// 上报时间
	private String applyTimeFrm;// 上报时间格式化
	private String apply_lon;// 上报经度
	private String apply_lat;// 上报纬度
	private String apply_addr;// 上报地址
	
	private String dealStatus; // 处理状态

	List<EventAttrsEntity> eventattrs;// 附件

	private EventStatusEntity eventStastus;// 流转状态

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public String getApply_code() {
		return apply_code;
	}

	public String getApply_name() {
		return apply_name;
	}

	public Date getApply_time() {
		return apply_time;
	}

	@Transient
	public String getApplyTimeFrm() {
		return applyTimeFrm;
	}

	public String getApply_lon() {
		return apply_lon;
	}

	public String getApply_lat() {
		return apply_lat;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setApply_code(String apply_code) {
		this.apply_code = apply_code;
	}

	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
		if (apply_time != null) {
			setApplyTimeFrm(DateUtils.formateDate(apply_time, "yyyy-MM-dd HH:mm"));
		}
	}

	public void setApplyTimeFrm(String applyTimeFrm) {
		this.applyTimeFrm = applyTimeFrm;
	}

	public void setApply_lon(String apply_lon) {
		this.apply_lon = apply_lon;
	}

	public void setApply_lat(String apply_lat) {
		this.apply_lat = apply_lat;
	}

	@OneToMany(targetEntity = EventAttrsEntity.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	public List<EventAttrsEntity> getEventattrs() {
		return eventattrs;
	}

	public void setEventattrs(List<EventAttrsEntity> eventattrs) {
		this.eventattrs = eventattrs;
	}

	@OneToOne(targetEntity = EventStatusEntity.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName = "event_id")
	public EventStatusEntity getEventStastus() {
		return eventStastus;
	}

	public void setEventStastus(EventStatusEntity eventStastus) {
		this.eventStastus = eventStastus;
	}

	@ManyToOne(targetEntity = ShglGridEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "grid_id", referencedColumnName = "id")
	public ShglGridEntity getGrid() {
		return grid;
	}

	public void setGrid(ShglGridEntity grid) {
		this.grid = grid;
	}

	public String getComm_id() {
		return comm_id;
	}

	public void setComm_id(String comm_id) {
		this.comm_id = comm_id;
	}

	public String getApply_addr() {
		return apply_addr;
	}

	public void setApply_addr(String apply_addr) {
		this.apply_addr = apply_addr;
	}

	@Transient
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Transient
	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	
}
