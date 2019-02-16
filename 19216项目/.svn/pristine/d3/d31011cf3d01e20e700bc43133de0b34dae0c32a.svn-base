package com.jcwx.entity.shfw;

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
import com.jcwx.utils.StringUtil;

/**
 * 
 * 社会服务-社区活动Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial")  
@Entity
@Table(name="shfw_sqhd_table") 
public class ShfwSqhdEntity implements Serializable {
	private String id;			//主键
	private String title;		//标题
	private String content;		//内容
	private Integer integral;	//活动积分
	private Date createDate;	//创建时间 
	private String userId;		//发布人ID
	private String userName;	//发布人姓名
	private Date startDate;		//活动开始时间
	private Date endDate;		//活动结束时间
	private String fbStatus;	//发布状态(1保存   2发布)
	private String shStatus;	//审核状态(0待审核  1通过  2未通过)
	private String shUserId;	//审核人ID
	private String shUserName;	//审核人姓名
	private String isHot;		//是否热点(1是  2否)
	private String isSignup;	//是否需要报名(1是 2否)
	private String hdStatus;		//活动状态(0未开始 1进行中 2结束)
	private String createDateFmt;	//创建时间格式化
	private String startDateFmt;	//活动开始时间格式化
	private String endDateFmt;		//活动结束时间格式化
	private List<ShfwSqhdYjEntity> yjList;	//留言集合
	
	private List<ShfwSqhdAttrsEntity> attrList;	//附件列表
	
	private String commId;				//所属社区ID
	private String commName;			//社区名称
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
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
		this.title = StringUtil.filterchart(title);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if(null != createDate){
			setCreateDateFmt(DateUtils.formateDate(createDate, "yyyy-MM-dd HH:mm"));
		}
	}
	
	@Column(name="user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	@Column(name="fb_status")
	public String getFbStatus() {
		return fbStatus;
	}

	public void setFbStatus(String fbStatus) {
		this.fbStatus = fbStatus;
	}
	
	@Column(name="sh_status")
	public String getShStatus() {
		return shStatus;
	}

	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}
	
	@Column(name="sh_user_id")
	public String getShUserId() {
		return shUserId;
	}

	public void setShUserId(String shUserId) {
		this.shUserId = shUserId;
	}
	
	@Column(name="sh_user_name")
	public String getShUserName() {
		return shUserName;
	}

	public void setShUserName(String shUserName) {
		this.shUserName = shUserName;
	}
	
	@Column(name="is_hot")
	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	@Column(name="is_signup")
	public String getIsSignup() {
		return isSignup;
	}

	public void setIsSignup(String isSignup) {
		this.isSignup = isSignup;
	}

	@Transient
	public String getCreateDateFmt() {
		return createDateFmt;
	}

	public void setCreateDateFmt(String createDateFmt) {
		this.createDateFmt = createDateFmt;
	}
	
	@Column(name="hd_status")
	public String getHdStatus() {
		return hdStatus;
	}

	public void setHdStatus(String hdStatus) {
		this.hdStatus = hdStatus;
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
	
	@OneToMany(targetEntity = ShfwSqhdAttrsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqhd_id", referencedColumnName = "id")
	public List<ShfwSqhdAttrsEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ShfwSqhdAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	
	@Column(name="comm_id")
	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}
	
	@Formula("(select c.name from shgl_community_table c where c.id = comm_id)")
	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}
	
	@OneToMany(targetEntity = ShfwSqhdYjEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqhd_id", referencedColumnName = "id")
	public List<ShfwSqhdYjEntity> getYjList() {
		return yjList;
	}

	public void setYjList(List<ShfwSqhdYjEntity> yjList) {
		this.yjList = yjList;
	}
	
}
