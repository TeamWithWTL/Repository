package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;



@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_event_deal_table")
public class EventDeal implements Serializable {
	private String id;// ID
	private String fbrId;// 发布人姓名
	private String fbrName;// 发布人ID
	private String preRoleId;// 上一处理角色ID
	private String curRoleId;// 处理角色ID
	private String clrName;// 处理人ID
	private String clrId;// 处理人姓名
	private String content;// 处理意见
	private String dealStatus;// 处理状态(0未处理，1 转发 2 处理,3街道办信息原处理公开是否)
	private Date createDate;// 创建时间
	private Date dealDate;// 处理时间
	
	private String dealDateFrm;
	private Event event;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="fbr_id")
	public String getFbrId() {
		return fbrId;
	}
	public void setFbrId(String fbrId) {
		this.fbrId = fbrId;
	}
	@Column(name="fbr_name")
	public String getFbrName() {
		return fbrName;
	}
	public void setFbrName(String fbrName) {
		this.fbrName = fbrName;
	}
	@Column(name="cur_role_id")
	public String getCurRoleId() {
		return curRoleId;
	}
	public void setCurRoleId(String curRoleId) {
		this.curRoleId = curRoleId;
	}
	@Column(name="clr_name")
	public String getClrName() {
		return clrName;
	}
	public void setClrName(String clrName) {
		this.clrName = clrName;
	}
	@Column(name="clr_id")
	public String getClrId() {
		return clrId;
	}
	public void setClrId(String clrId) {
		this.clrId = clrId;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="deal_status")
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="deal_date")
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
		if (dealDate != null) {
			setDealDateFrm(DateUtils.formateDate(dealDate, "yyyy-MM-dd HH:mm"));
		}
	}
	
	@ManyToOne(targetEntity = Event.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id")
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	@Column(name="pre_role_id")
	public String getPreRoleId() {
		return preRoleId;
	}
	public void setPreRoleId(String preRoleId) {
		this.preRoleId = preRoleId;
	}
	@Transient
	public String getDealDateFrm() {
		return dealDateFrm;
	}
	public void setDealDateFrm(String dealDateFrm) {
		this.dealDateFrm = dealDateFrm;
	}

}
