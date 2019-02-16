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

import com.jcwx.entity.dflz.AccessoryEntity;
import com.jcwx.utils.DateUtils;


/**
 * @author msy
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_dtbb_table")
public class DtbbEntity implements Serializable{
	private String id ;						//动态播报id	
	private String title ;					//标题	
	private String content ;				//内容
	private Date createTime ;				//创建时间	
	private String userId ;					//发布人ID
	private String userName ;				//发布人姓名
	private String shStatus ;				//审核状态
	private String shUserId ;				//审核人ID
	private String shUserName;				//审核人姓名
	private List<DtbbAttrEntity> attrList;	//附件集合
	private List<DtbbYjEntity> YjList;		//留言集合
	private String createTimes;			//格式化时间
	
	private String ysPice;//压缩图片
	
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
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name ="create_time")
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
	@Column(name ="user_id")
	public String getUserId() {
		return userId;
	}
	@Column(name ="user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name ="user_name")
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
	@Column(name ="sh_user_id")
	public String getShUserId() {
		return shUserId;
	}
	public void setShUserId(String shUserId) {
		this.shUserId = shUserId;
	}
	
	@Column(name ="sh_user_name")
	public String getShUserName() {
		return shUserName;
	}
	public void setShUserName(String shUserName) {
		this.shUserName = shUserName;
	}
	
	@OneToMany(targetEntity = DtbbAttrEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dtbb_id", referencedColumnName = "id")
	public List<DtbbAttrEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<DtbbAttrEntity> attrList) {
		this.attrList = attrList;
	}
	
	@OneToMany(targetEntity = DtbbYjEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dtbb_id", referencedColumnName = "id")
	public List<DtbbYjEntity> getYjList() {
		return YjList;
	}
	public void setYjList(List<DtbbYjEntity> yjList) {
		YjList = yjList;
	}
	@Transient
	public String getYsPice() {
		return ysPice;
	}
	public void setYsPice(String ysPice) {
		this.ysPice = ysPice;
	}

}
