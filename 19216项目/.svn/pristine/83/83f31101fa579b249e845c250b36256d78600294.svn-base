package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
 * 社区管理表
 * 
 * @author wjx
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_community_table")
public class ShglCommunityEntity implements Serializable {
	private String id;// id
	private String strative_id;// 所属行政区划编号
	private String name;// 社区名称
	private String area;// 社区范围
	private String add_code;// 添加人编号
	private String add_name;// 添加人姓名
	private Date add_time;// 添加时间
	private String addTimeFrm;// 添加时间格式化
	private String area_color;// 区域颜色
	private String line_color;// 边框颜色
	private String description;//描述
	private List<ShglCmanagerEntity> cmanagerList;//负责人
	private String lon;           //社区服务中心注点--经度
	private String lat;           //社区服务中心注点--纬度
	
	private String strative_name;// 所属行政区划名称
	private String managers;//存负责人的字段
	private String phones;//存联系电话的字段
	private String sjCount;// 实际入住户数
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrative_id() {
		return strative_id;
	}

	public void setStrative_id(String strative_id) {
		this.strative_id = strative_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = StringUtil.filterchart(area);
	}

	public String getAdd_code() {
		return add_code;
	}

	public void setAdd_code(String add_code) {
		this.add_code = StringUtil.filterchart(add_code);
	}

	public String getAdd_name() {
		return add_name;
	}

	public void setAdd_name(String add_name) {
		this.add_name = StringUtil.filterchart(add_name);
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
		if (add_time != null) {
			setAddTimeFrm(DateUtils.formateDate(add_time, "yyyy-MM-dd"));
		}
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Transient
	public String getAddTimeFrm() {
		return addTimeFrm;
	}

	public void setAddTimeFrm(String addTimeFrm) {
		this.addTimeFrm = addTimeFrm;
	}

	@Transient
	public String getStrative_name() {
		return strative_name;
	}

	public void setStrative_name(String strative_name) {
		this.strative_name = strative_name;
	}

	public String getArea_color() {
		return area_color;
	}

	public String getLine_color() {
		return line_color;
	}

	public void setArea_color(String area_color) {
		this.area_color = area_color;
	}

	public void setLine_color(String line_color) {
		this.line_color = line_color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtil.filterchart(description);
	}
	
	@OneToMany(targetEntity = ShglCmanagerEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name="comm_id")
	public List<ShglCmanagerEntity> getCmanagerList() {
		return cmanagerList;
	}

	public void setCmanagerList(List<ShglCmanagerEntity> cmanagerList) {
		this.cmanagerList = cmanagerList;
	}

	@Transient
	public String getManagers() {
		return managers;
	}

	public void setManagers(String managers) {
		this.managers = managers;
	}
	
	@Transient
	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}
	@Transient
	public String getSjCount() {
		return sjCount;
	}

	public void setSjCount(String sjCount) {
		this.sjCount = sjCount;
	}
}
