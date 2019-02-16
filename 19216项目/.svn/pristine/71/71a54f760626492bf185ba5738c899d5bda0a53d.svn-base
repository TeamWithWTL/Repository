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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.varia.StringMatchFilter;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * 服务站管理
 * 
 * @author wjx
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_service_station_table")
public class ShglServiceStationEntity implements Serializable {
	private String id;// id
	private ShglCommunityEntity community;// 所在社区
	private String name;// 服务站名称
	private String area;// 范围
	private String add_code;// 添加人编码
	private String add_name;// 添加人姓名
	private Date add_time;// 添加时间
	private String area_color;// 区域颜色
	private String line_color;// 边框颜色
	private String description; //描述
	private String lon;           //服务站标注点--经度
	private String lat;           //服务站标注点--纬度
	private List<ShglSmanagerEntity> smanageList;//服务站负责人
	
	private String addTimeFrm;// 添加时间格式化

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = ShglCommunityEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "comm_id", referencedColumnName = "id")
	public ShglCommunityEntity getCommunity() {
		return community;
	}

	public void setCommunity(ShglCommunityEntity community) {
		this.community = community;
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

	public String getArea_color() {
		return area_color;
	}

	public String getLine_color() {
		return line_color;
	}

	public void setArea_color(String area_color) {
		this.area_color = StringUtil.filterchart(area_color);
	}

	public void setLine_color(String line_color) {
		this.line_color = StringUtil.filterchart(line_color);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtil.filterchart(description);
	}
	
	@OneToMany(targetEntity = ShglSmanagerEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "ss_id")
	public List<ShglSmanagerEntity> getSmanageList() {
		return smanageList;
	}

	public void setSmanageList(List<ShglSmanagerEntity> smanageList) {
		this.smanageList = smanageList;
	}
	
	
}
