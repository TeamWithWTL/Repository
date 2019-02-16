package com.jcwx.entity.shfw;

import java.io.Serializable;
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
 * 
 * 社会服务-社区活动积分流水Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial") 
@Entity
@Table(name="shfw_jfls_table")
public class ShfwJflsEntity implements Serializable {
	private String id;				//主键
	private String accCode;			//用户ID
	private String type;			//类型（1加 2减）
	private String content;			//描述
	private Integer integral;		//积分
	private Date createDate;		//创建时间
	private String createUserId;	//创建人ID
	private String createUserName; //创建人姓名
	private String createDateFmt;	//创建时间格式化
	private String name;			//居民姓名
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="acc_code")
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
			setCreateDateFmt(DateUtils.formateDate(createDate, "yyyy-MM-dd"));
		}
	}
	@Column(name="create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name="create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@Transient
	public String getCreateDateFmt() {
		return createDateFmt;
	}
	public void setCreateDateFmt(String createDateFmt) {
		this.createDateFmt = createDateFmt;
	}
	@Formula("(select a.name from sys_account_table a where a.acc_code = acc_code)")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
