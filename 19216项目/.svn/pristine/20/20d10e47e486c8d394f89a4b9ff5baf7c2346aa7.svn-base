package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_event_table")
public class Event  implements Serializable {
	private String id;// ID
	private String commId;// 社区id
	private String ssId;// 服务站ID
	private String title;// 事件标题
	private String content;// 事件内容
	private String type;// 事件类型
	private String applyCode;// 上报人编号
	private String applyName;// 上报人姓名
	private Date applyTime;// 上报时间
	private String applyLon;// 上报经度
	private String applyLat;// 上报纬度
	private String applyAddr;// 上报地址

	private String isPub;
	private String isOver;
	private String applyTimeFrm;// 上报时间格式化
	
	private String typeName;//事件类别名称
	
	private String eventfrm; //事件来源
	
	private String dealStatus; // 处理状态(对象属性,不对应数据库字段)
	
	List<EventAttrs> eventAttrs;// 附件

	List<EventDeal> eventDeals;
	
	
	
	private String ssName;
	private String commName;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="comm_id")
	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	@Column(name="ss_id")
	public String getSsId() {
		return ssId;
	}

	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="apply_code")
	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	@Column(name="apply_name")
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	@Column(name="apply_time")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
		if (applyTime != null) {
			setApplyTimeFrm(DateUtils.formateDate(applyTime, "yyyy-MM-dd HH:mm"));
		}
	}

	@Column(name="apply_lon")
	public String getApplyLon() {
		return applyLon;
	}

	public void setApplyLon(String applyLon) {
		this.applyLon = applyLon;
	}

	@Column(name="apply_lat")
	public String getApplyLat() {
		return applyLat;
	}

	public void setApplyLat(String applyLat) {
		this.applyLat = applyLat;
	}

	@Column(name="apply_addr")
	public String getApplyAddr() {
		return applyAddr;
	}

	public void setApplyAddr(String applyAddr) {
		this.applyAddr = applyAddr;
	}

	@Transient
	public String getApplyTimeFrm() {
		return applyTimeFrm;
	}

	public void setApplyTimeFrm(String applyTimeFrm) {
		this.applyTimeFrm = applyTimeFrm;
	}

	@OneToMany(targetEntity = EventAttrs.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	public List<EventAttrs> getEventAttrs() {
		return eventAttrs;
	}

	public void setEventAttrs(List<EventAttrs> eventAttrs) {
		this.eventAttrs = eventAttrs;
	}
	@OneToMany(targetEntity = EventDeal.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	public List<EventDeal> getEventDeals() {
		return eventDeals;
	}

	public void setEventDeals(List<EventDeal> eventDeals) {
		this.eventDeals = eventDeals;
	}

	@Column(name="is_pub")
	public String getIsPub() {
		return isPub;
	}

	public void setIsPub(String isPub) {
		this.isPub = isPub;
	}
	@Column(name="is_over")
	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	@Formula("(select sdt.item_name from sys_param_desc_table sdt where sdt.item_code = type and sdt.code = '10008')")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name="eventfrm")
	public String getEventfrm() {
		return eventfrm;
	}

	public void setEventfrm(String eventfrm) {
		this.eventfrm = eventfrm;
	}
	
	@Transient
	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	@Formula("(select ct.name from shgl_service_station_table ct where ct.id = ss_id )")
	public String getSsName() {
		return ssName;
	}

	public void setSsName(String ssName) {
		this.ssName = ssName;
	}
	@Formula("(select ct.name from shgl_community_table ct where ct.id = comm_id )")
	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

}
