package com.jcwx.entity.shgl;

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

/**
 * 社会管理--任务管理
 *  	2017年10月27日
 *  
 * @author gaoshuai
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_task_deal_table")
public class ShglTaskDealEntity {
	
	
	private String id;// ID
	private String preEmp;//上一处理人id
	private String curEmp;// 发布人姓名
	private String content;// 反馈信息
	private Date startTime;// 开始时间
	private Date endTime;// 完成时间
	private String idDown;// 是否已下发
	private String isBack;// 是否反馈
	
	private String startDates;//开始日期
	private String endDates;//结束日期
	
	private ShglTaskEntity shglTaskEntity;
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "pre_emp")
	public String getPreEmp() {
		return preEmp;
	}
	public void setPreEmp(String preEmp) {
		this.preEmp = preEmp;
	}
	@Column(name = "cur_emp")
	public String getCurEmp() {
		return curEmp;
	}
	public void setCurEmp(String curEmp) {
		this.curEmp = curEmp;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "starte_date")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		if(startTime != null){
			setStartDate(DateUtils.formateDate(startTime, "yyyy-MM-dd HH:mm"));
		}
	}
	@Column(name = "end_date")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		if(endTime != null){
			setEndDate(DateUtils.formateDate(endTime, "yyyy-MM-dd HH:mm"));
		}
	}
	@Column(name = "is_down")
	public String getIdDown() {
		return idDown;
	}
	public void setIdDown(String idDown) {
		this.idDown = idDown;
	}
	@Column(name = "is_back")
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Transient
	public String getStartDate() {
		return startDates;
	}
	public void setStartDate(String startDate) {
		this.startDates = startDate;
	}
	@Transient
	public String getEndDate() {
		return endDates;
	}
	public void setEndDate(String endDate) {
		this.endDates = endDate;
	}
	@ManyToOne(targetEntity = ShglTaskEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public ShglTaskEntity getShglTaskEntity() {
		return shglTaskEntity;
	}
	public void setShglTaskEntity(ShglTaskEntity shglTaskEntity) {
		this.shglTaskEntity = shglTaskEntity;
	}
}
