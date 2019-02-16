package com.jcwx.entity.shgl;

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
 * 政府机构信息
 * @author wangjinxiao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_government_table")
public class ShglGovernmentEntity implements Serializable{
	
	private String id;
	private String strativeId; //行政区划编号
	private String name; //机构名称
	private String lon; //经度
	private String lat; //纬度
	private String addCode; //添加人编号
	private String addName; //姓名
	private String addTimeFrm;// 添加时间格式化
	private Date addTime;    //添加时间
	private String description; //机构描述
	private List<ShglGmanagerEntity> gmList;//负责人
	
	private String strative_name;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "strative_id")
	public String getStrativeId() {
		return strativeId;
	}
	public void setStrativeId(String strativeId) {
		this.strativeId = strativeId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = StringUtil.filterchart(lon);
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = StringUtil.filterchart(lat);
	}
	@Column(name = "add_code")
	public String getAddCode() {
		return addCode;
	}
	public void setAddCode(String addCode) {
		this.addCode = StringUtil.filterchart(addCode);
	}
	@Column(name = "add_name")
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = StringUtil.filterchart(addName);
	}
	
	@Transient
	public String getAddTimeFrm() {
		return addTimeFrm;
	}
	public void setAddTimeFrm(String addTimeFrm) {
		this.addTimeFrm = addTimeFrm;
	}
	
	@Column(name = "add_time")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
		if(null != addTime){
			setAddTimeFrm(DateUtils.formateDate(addTime, "yyyy-MM-dd"));
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = StringUtil.filterchart(description);
	}
	@OneToMany(targetEntity = ShglGmanagerEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "gove_id")
	public List<ShglGmanagerEntity> getGmList() {
		return gmList;
	}
	public void setGmList(List<ShglGmanagerEntity> gmList) {
		this.gmList = gmList;
	}
	@Transient
	public String getStrative_name() {
		return strative_name;
	}
	public void setStrative_name(String strative_name) {
		this.strative_name = strative_name;
	}

}
