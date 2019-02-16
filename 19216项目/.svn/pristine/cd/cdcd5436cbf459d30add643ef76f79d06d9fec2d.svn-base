package com.jcwx.entity.shzz;
import javax.persistence.Transient;
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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

@Entity
@Table(name = "shzz_zxjs_table")
public class ZxjsEntity implements java.io.Serializable {

	private String id;// 主键
	private String title;// 标题
	private String content;// 内容
	private Date createTime;// 创建时间
	private String userId;// 发布人ID
	private String userName;// 发布人姓名
	private String shStatus;	//审核状态 0待审核,1通过, 2不通过
	private String shUserId;	//审核人ID
	private String shUserName;	//审核人姓名
	private String createTimes;	//格式化创建时间

	private List<ZxjsAttrsEntity> attrList;	//附件列表
	
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
			setCreateTimes(DateUtils.formateDate(createTime, "yyyy-MM-dd"));
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
	@Column(name ="sh_status")
	public String getShStatus() {
		return shStatus;
	}

	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}

	@OneToMany(targetEntity = ZxjsAttrsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zxjs_id", referencedColumnName = "id")
	public List<ZxjsAttrsEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ZxjsAttrsEntity> attrList) {
		this.attrList = attrList;
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

	


}
