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
 * 社会服务-政务信息Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial")
@Entity
@Table(name="shfw_zwxx_table") 
public class ShfwZwxxEntity implements Serializable {
	private String id;		//主键
	private String title;		//标题
	private String content;		//内容
	private Date createTime;	//创建时间
	private String userId;		//发布人ID
	private String userName;	//发布人姓名
	private String tjStatus;	//提交状态(1提交  2保存)
	private String shStatus;	//审核状态(0待审核  1通过  2未通过)
	private String shUserId;	//审核人ID
	private String shUserName;	//审核人姓名
	//private Integer orderby;	//排序字段
	private String  isTop;		//是否置顶(1是  2否)
	private String createTimeFmt;	//创建时间格式化
	
	private List<ShfwZwxxAttrsEntity> attrList;	//附件列表
	
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

	/*public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}*/
	
	@Column(name="is_top")
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	@Transient
	public String getCreateTimeFmt() {
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}
	
	@OneToMany(targetEntity = ShfwZwxxAttrsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zwxx_id", referencedColumnName = "id")
	public List<ShfwZwxxAttrsEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ShfwZwxxAttrsEntity> attrList) {
		this.attrList = attrList;
	}
}
