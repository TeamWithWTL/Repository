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

/**
 * 
 * 社会服务-社区活动意见留言Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial")  
@Entity
@Table(name="shfw_sqhd_yj_table")
public class ShfwSqhdYjEntity implements Serializable {
	private String id;			//主键
	private String sqhdId;	   	//社区活动ID
	private String content;	  	//建议留言
	private Date createTime; 	//创建时间
	private String userId;		//发布人ID
	private String userName;	//发布人姓名
	private String shStatus;	//审核状态（0待审核  1通过  2未通过）
	private Integer integral;	//积分
	private String score_type; //积分状态， 0 未设置积分，1 已设置积分。
	private String createTimeFmt;	//创建时间格式化
	private String role;			//标识发布人角色是普通用户，还是其他用户
	private String userPic;			//发布人头像
	private String isSignup;		//是否需要报名(1是 2否)
	private String isBack;			//是否反馈(1是 2否)
	private List<ShfwSqhdYjAttrsEntity> attrList; //留言附件列表
	
	@Transient
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="sqhd_id")
	public String getSqhdId() {
		return sqhdId;
	}
	public void setSqhdId(String sqhdId) {
		this.sqhdId = sqhdId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(null != createTime){
			setCreateTimeFmt(DateUtils.formateDate(createTime, "yyyy-MM-dd HH:mm"));
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
	@Column(name="sh_status")
	public String getShStatus() {
		return shStatus;
	}
	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	@Transient
	public String getCreateTimeFmt() {
		return createTimeFmt;
	}
	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}
	@Formula("(select p.pic_path from sys_account_table p where p.acc_code = user_id )")
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	
	public String getScore_type() {
		return score_type;
	}
	public void setScore_type(String score_type) {
		this.score_type = score_type;
	}
	@Column(name="is_signup")
	public String getIsSignup() {
		return isSignup;
	}
	public void setIsSignup(String isSignup) {
		this.isSignup = isSignup;
	}
	@Column(name="is_back")
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@OneToMany(targetEntity = ShfwSqhdYjAttrsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "yj_id", referencedColumnName = "id")
	public List<ShfwSqhdYjAttrsEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<ShfwSqhdYjAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	
}
