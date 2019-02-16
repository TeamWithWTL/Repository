package com.jcwx.entity.shzz;

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
 * 组织活动留言Entity
 * @author m
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_hdgl_yj_table")
public class HdglYjEntity {
	private String id; 					//id
	private String hdglId;				//活动管理id
	private String content;				//建议留言
	private Date createTime;			//创建时间
	private String userId;				//发布人id
	private String userName;			//发布人姓名
	private String 	status;				//审核状态  (0 未审核  1 审核通过 2审核不通过)
	private String createTimeF;			//创建时间
	private String picpath;             //头像路径
	private String duty;               //职务
	
	@Transient
	public String getCreateTimeF() {
		return createTimeF;
	}
	public void setCreateTimeF(String createTimeF) {
		this.createTimeF = createTimeF;
		
	}
	@Formula("( select s.pic_path from sys_account_table s where s.acc_code = user_id )")
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	@Formula("( select s.duty from sys_account_table s where s.acc_code = user_id )")
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
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
		if(null != createTime){
			setCreateTimeF(DateUtils.formateDate(createTime, "yyyy-MM-dd HH:mm"));
		}
	}
	
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "sh_status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "hdgl_id")
	public String getHdglId() {
		return hdglId;
	}
	public void setHdglId(String hdglId) {
		this.hdglId = hdglId;
	}
	
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
