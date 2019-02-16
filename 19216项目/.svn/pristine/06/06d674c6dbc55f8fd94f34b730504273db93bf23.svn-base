package com.jcwx.entity.xtbg;

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
import org.springframework.format.annotation.DateTimeFormat;

import com.jcwx.utils.DateUtils;


/**
 * 会议管理实体类
 * @author 李伟
 * @time 2017年10月29日上午1:49:02
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_hygl_table")
public class MeetingEntity implements Serializable{
	private String id; //主键
	private String title;//标题
	private String venue;//会议地点
	private String content;//会议内容
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date create_date;//会议发布时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date start_date;//会议开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date end_date;//会议结束时间
	private String fmtCreate_date;//日期格式化
	private String fmtStart_date;//日期格式化
	private String fmtEnd_date;//日期格式化
	private String user_id;//发布人id
	private String user_name;//发布人name
	private String tj_status;//会议提交状态 1为提交2为保存
	private List<MeetingAcceEntity> meetAcceList;//会议附件集合，一对多关系
	private List<MeetingStaffEntity> meetStaffList;//会议参加人员id集合，一对多关系
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
		if (start_date !=null) {
			setFmtStart_date(DateUtils.formateDate(start_date, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
		if (end_date!=null) {
			setFmtEnd_date(DateUtils.formateDate(end_date, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTj_status() {
		return tj_status;
	}
	public void setTj_status(String tj_status) {
		this.tj_status = tj_status;
	}
	@OneToMany(targetEntity = MeetingAcceEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hygl_id", referencedColumnName = "id")
	public List<MeetingAcceEntity> getMeetAcceList() {
		return meetAcceList;
	}
	public void setMeetAcceList(List<MeetingAcceEntity> meetAcceList) {
		this.meetAcceList = meetAcceList;
	}
	@OneToMany(targetEntity = MeetingStaffEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hygl_id", referencedColumnName = "id")
	public List<MeetingStaffEntity> getMeetStaffList() {
		return meetStaffList;
	}
	public void setMeetStaffList(List<MeetingStaffEntity> meetStaffList) {
		this.meetStaffList = meetStaffList;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
		if (create_date!=null) {
			setFmtCreate_date(DateUtils.formateDate(create_date, "yyyy-MM-dd"));
		}
	}
	
	@Transient
	public String getFmtCreate_date() {
		return fmtCreate_date;
	}
	public void setFmtCreate_date(String fmtCreate_date) {
		this.fmtCreate_date = fmtCreate_date;
	}
	@Transient
	public String getFmtStart_date() {
		return fmtStart_date;
	}
	public void setFmtStart_date(String fmtStart_date) {
		this.fmtStart_date = fmtStart_date;
	}
	@Transient
	public String getFmtEnd_date() {
		return fmtEnd_date;
	}
	public void setFmtEnd_date(String fmtEnd_date) {
		this.fmtEnd_date = fmtEnd_date;
	}
	@Override
	public String toString() {
		return "MeetingEntity [id=" + id + ", title=" + title + ", venue=" + venue + ", content=" + content
				+ ", create_date=" + create_date + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", user_id=" + user_id + ", user_name=" + user_name + ", tj_status=" + tj_status + ", meetAcceList="
				+ meetAcceList + ", meetStaffList=" + meetStaffList + "]";
	}
	
	
}
