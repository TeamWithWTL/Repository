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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * 
 * 社会服务-社区服务Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial") 
@Entity
@Table(name="shfw_sqfw_table")
public class ShfwSqfwEntity implements Serializable {
	private String id;			//主键
	private String typeOne;		//一级类型
	private String typeTwo;		//二级类型
	private String title;		//标题
	private String content;		//内容
	private Date createTime;	//创建时间
	private String userId;		//发布人ID
	private String userName;	//发布人姓名
	private String tjStatus;	//提交状态(1提交  2保存)
	private String shStatus;	//审核状态(0待审核  1通过  2未通过)
	private String shUserId;	//审核人ID
	private String shUserName;	//审核人姓名
	private String isHot;		//是否热点(1是  2否)
	private String createTimeFmt;	//创建时间格式化
	
	private List<ShfwSqfwAttrsEntity> attrList;	//附件列表
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="type_one")
	public String getTypeOne() {
		return typeOne;
	}
	
	public void setTypeOne(String typeOne) {
		this.typeOne = typeOne;
	}
	
	@Column(name="type_two")
	public String getTypeTwo() {
		return typeTwo;
	}

	public void setTypeTwo(String typeTwo) {
		this.typeTwo = typeTwo;
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
	
	@Column(name="tj_status")
	public String getTjStatus() {
		return tjStatus;
	}

	public void setTjStatus(String tjStatus) {
		this.tjStatus = tjStatus;
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

	@Transient
	public String getCreateTimeFmt() {
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}
	
	@OneToMany(targetEntity = ShfwSqfwAttrsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqfw_id", referencedColumnName = "id")
	public List<ShfwSqfwAttrsEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ShfwSqfwAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	
}
