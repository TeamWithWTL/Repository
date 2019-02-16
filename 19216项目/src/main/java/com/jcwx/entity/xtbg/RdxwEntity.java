package com.jcwx.entity.xtbg;

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
@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_rdxw_table")
public class RdxwEntity implements Serializable{
	private String id;
	private String title;			//标题
	private String content;			//内容	
	private Date createDate;		//创建时间
	private String userId;			//创建人ID
	private String userName;		//创建人姓名
	private String shStatus;		//审核状态 0待审核,1审核通过,2审核不通过
	private String shUserId;		//审核人ID
	private String shUserName;		//审核人姓名
	private String createDateFrm;	//格式化创建时间
	private String isHot;			//是否热点 1是2否
	private String isTop;			//是否置顶 1是2否
	private String summary ;		//摘要
	private String types ;			//新闻类别
	private String picPath;			//封面图片路径
	
	
	private List<RdxwArrtsEntity> attrList;	//附件列表
	
	 private String ysPice;//压缩图片
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")	
	@Column(name = "id", unique = true, nullable = false, length = 40)
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

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if (createDate != null) {
			setCreateDateFrm(DateUtils.formateDate(createDate, "yyyy-MM-dd HH:mm"));
		}
	}

	@Column(name= "create_user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name= "create_user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name= "sh_status")
	public String getShStatus() {
		return shStatus;
	}

	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}

	@Column(name= "sh_user_id")
	public String getShUserId() {
		return shUserId;
	}

	public void setShUserId(String shUserId) {
		this.shUserId = shUserId;
	}

	@Column(name= "sh_user_name")
	public String getShUserName() {
		return shUserName;
	}

	public void setShUserName(String shUserName) {
		this.shUserName = shUserName;
	}

	@Transient
	public String getCreateDateFrm() {
		return createDateFrm;
	}

	public void setCreateDateFrm(String createDateFrm) {
		this.createDateFrm = createDateFrm;
	}


	@OneToMany(targetEntity = RdxwArrtsEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rdxw_id", referencedColumnName = "id")
	public List<RdxwArrtsEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<RdxwArrtsEntity> attrList) {
		this.attrList = attrList;
	}
	@Transient
	public String getYsPice() {
		return ysPice;
	}

	public void setYsPice(String ysPice) {
		this.ysPice = ysPice;
	}
	
	
	@Column(name="is_hot")
	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	@Column(name= "summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = StringUtil.filterchart(summary);
	}
	@Column(name= "types")
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	@Column(name="is_top")
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	@Column(name = "pic_path")
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
}
