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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * @author m
 */
@SuppressWarnings("serial")
@Entity
@Table(name ="shzz_hdgl_fk_table")
public class HdglFkEntity implements Serializable{
	private String id;				//主键
	private String hdglId;			//活动ID
	private String zzId;			//组织ID
	private String content;			//反馈内容
	private String shStatus;		//审核状态(0待审核  1通过  2未通过)
	private Date backTime;			//反馈时间
	private String fmtBackTime;		//时间格式化
	private List<HdglFkAttrEntity> fkAttrList; //反馈附件集合   liwei
	private ZzxxEntity zzxxEntity;
	private String isBack;			//是否反馈(1是 2否)
	private String zzName;			//社会组织名称
	private String hdName;			//组织活动名称
	
	@Transient
	public ZzxxEntity getZzxxEntity() {
		return zzxxEntity;
	}
	public void setZzxxEntity(ZzxxEntity zzxxEntity) {
		this.zzxxEntity = zzxxEntity;
	}
	@OneToMany(targetEntity = HdglFkAttrEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zxjs_id", referencedColumnName = "id")
	public List<HdglFkAttrEntity> getFkAttrList() {
		return fkAttrList;
	}
	public void setFkAttrList(List<HdglFkAttrEntity> fkAttrList) {
		this.fkAttrList = fkAttrList;
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
	
	@Column(name ="hdgl_id")
	public String getHdglId() {
		return hdglId;
	}
	public void setHdglId(String hdglId) {
		this.hdglId = hdglId;
	}
	
	@Column(name ="zz_id")
	public String getZzId() {
		return zzId;
	}
	public void setZzId(String zzId) {
		this.zzId = zzId;
	}
	
	@Column(name ="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name ="sh_status")
	public String getShStatus() {
		return shStatus;
	}
	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}
	
	@Column(name ="back_time")
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
		if (backTime!=null) {
			setFmtBackTime(DateUtils.formateDate(backTime, "yyyy-MM-dd HH:mm"));
		}
	}
	
	@Transient
	public String getFmtBackTime() {
		return fmtBackTime;
	}
	public void setFmtBackTime(String fmtBackTime) {
		this.fmtBackTime = fmtBackTime;
	}
	
	@Column(name ="is_back")
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	@Formula("(select z.name from shzz_zzxx_table z where z.id = zz_id)")
	public String getZzName() {
		return zzName;
	}
	public void setZzName(String zzName) {
		this.zzName = zzName;
	}
	
	@Formula("(select h.title from shzz_hdgl_table h where h.id = hdgl_id)")
	public String getHdName() {
		return hdName;
	}
	public void setHdName(String hdName) {
		this.hdName = hdName;
	}
	
}
