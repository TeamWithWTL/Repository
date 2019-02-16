package com.jcwx.entity.shgl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
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
@Table(name = "shgl_task_table")
public class ShglTaskEntity {
	
	private String id;// ID
	private String title;// 任务标题
	private String content;// 任务内容
	private Date createDate;// 创建时间
	private Date finishDate;// 完成时间
	private String fbrId;// 发布人id
	private String fbrName;// 发布人姓名
	private String sqId;// 社区id
	private String fwzId;// 服务站id
	
	private String startDate;//开始日期
	private String endDate;//结束日期
	private String sqName;// 社区名称
	private String fwzName;// 服务站名称
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if(null != createDate){
			setStartDate(DateUtils.formateDate(createDate, "yyyy-MM-dd"));
		}
	}
	@Column(name = "finish_date")
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
		if(null != finishDate){
			setEndDate(DateUtils.formateDate(finishDate, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	@Column(name = "fbr_id")
	public String getFbrId() {
		return fbrId;
	}
	public void setFbrId(String fbrId) {
		this.fbrId = fbrId;
	}
	@Column(name = "fbr_name")
	public String getFbrName() {
		return fbrName;
	}
	public void setFbrName(String fbrName) {
		this.fbrName = fbrName;
	}
	@Column(name = "sq_id")
	public String getSqId() {
		return sqId;
	}
	public void setSqId(String sqId) {
		this.sqId = sqId;
	}
	@Column(name = "fwz_id")
	public String getFwzId() {
		return fwzId;
	}
	public void setFwzId(String fwzId) {
		this.fwzId = fwzId;
	}
	@Transient
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Transient
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Formula("(select ct.name from shgl_community_table ct where ct.id = sq_id )")
	public String getSqName() {
		return sqName;
	}
	public void setSqName(String sqName) {
		this.sqName = sqName;
	}
	@Formula("(select ss.name from shgl_service_station_table ss where ss.id = fwz_id )")
	public String getFwzName() {
		return fwzName;
	}
	public void setFwzName(String fwzName) {
		this.fwzName = fwzName;
	}
}
