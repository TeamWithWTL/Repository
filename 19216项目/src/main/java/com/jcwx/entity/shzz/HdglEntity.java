package com.jcwx.entity.shzz;

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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author m
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_hdgl_table")
public class HdglEntity implements Serializable {
	private String id;					//id
	private String title;				//标题
	private String content;				//内容
	private Date createTime;			//创建时间
	private String userId;				//发布人ID
	private String userName;			//发布人姓名
	private String isFeedback;			//是否反馈
	private String shStatus;			//审核状态(0待审核  1通过  2未通过)
	private String shUserId;			//审核人ID
	private String shUserName;			//审核人姓名
	private String is_hot;				//是否热点
	private String type_one;			//一级类别
	private String type_two;			//二级类别
	private List<HdglAttrEntity> attrList;		//附件集合
	private List<HdglYjEntity> YjList;			//留言集合
	private List<HdglFkEntity> fkList;			//法人反馈集合   liwei
	private String createTimes;
	private Date startDate;		//活动开始时间
	private Date endDate;		//活动结束时间
	private String startDateFmt;	//活动开始时间格式化
	private String endDateFmt;		//活动结束时间格式化
	private String hdStatus;		//活动状态(0未开始 1进行中 2结束)
	
	@OneToMany(targetEntity = HdglFkEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hdgl_id", referencedColumnName = "id")
	public List<HdglFkEntity> getFkList() {
		return fkList;
	}
	public void setFkList(List<HdglFkEntity> fkList) {
		this.fkList = fkList;
	}
	@OneToMany(targetEntity = HdglAttrEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hdgl_id", referencedColumnName = "id")
	public List<HdglAttrEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<HdglAttrEntity> attrList) {
		this.attrList = attrList;
	}
	
	@OneToMany(targetEntity = HdglYjEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hdgl_id", referencedColumnName = "id")
	public List<HdglYjEntity> getYjList() {
		return YjList;
	}
	public void setYjList(List<HdglYjEntity> yjList) {
		YjList = yjList;
	}
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 40)
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
		this.title = StringUtil.filterchart(title);
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if (createTime != null) {
			setCreateTimes(DateUtils.formateDate(createTime, "yyyy-MM-dd HH:mm"));
		}
	}
	@Transient
	public String getCreateTimes() {
		return createTimes;
	}
	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "is_feedback")
	public String getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}
	
	@Column(name = "sh_status")
	public String getShStatus() {
		return shStatus;
	}
	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}
	
	@Column(name = "sh_user_id")
	public String getShUserId() {
		return shUserId;
	}
	public void setShUserId(String shUserId) {
		this.shUserId = shUserId;
	}
	
	@Column(name = "sh_user_name")
	public String getShUserName() {
		return shUserName;
	}
	public void setShUserName(String shUserName) {
		this.shUserName = shUserName;
	}
	
	public String getType_one() {
		return type_one;
	}
	public void setType_one(String type_one) {
		this.type_one = type_one;
	}
	public String getType_two() {
		return type_two;
	}
	public void setType_two(String type_two) {
		this.type_two = type_two;
	}
	public String getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}
	@Column(name="start_date")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		if(null != startDate){
			setStartDateFmt(DateUtils.formateDate(startDate, "yyyy-MM-dd"));
		}
	}
	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		if(null != endDate){
			setEndDateFmt(DateUtils.formateDate(endDate, "yyyy-MM-dd"));
		}
	}
	@Transient
	public String getStartDateFmt() {
		return startDateFmt;
	}
	public void setStartDateFmt(String startDateFmt) {
		this.startDateFmt = startDateFmt;
	}
	@Transient
	public String getEndDateFmt() {
		return endDateFmt;
	}
	public void setEndDateFmt(String endDateFmt) {
		this.endDateFmt = endDateFmt;
	}
	@Column(name="hd_status")
	public String getHdStatus() {
		return hdStatus;
	}
	public void setHdStatus(String hdStatus) {
		this.hdStatus = hdStatus;
	}
	
}
