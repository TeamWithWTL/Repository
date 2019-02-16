package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * 摄像头管理表
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "camera_table")
public class CameraEntity implements Serializable {
	private String id;// id
	private ShglGridEntity grid;// 网格编号
	private String commId;// 社区id
	private String name;// 名称
	private String url;// 数据连接
	private String lon;// 经度
	private String lat;// 纬度
	private String add_code;
	private String add_name;
	private Date add_time;

	private String addTimeFrm;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	@ManyToOne(targetEntity = ShglGridEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "grid_id", referencedColumnName = "id")
	public ShglGridEntity getGrid() {
		return grid;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getLon() {
		return lon;
	}

	public String getLat() {
		return lat;
	}

	public String getAdd_code() {
		return add_code;
	}

	public String getAdd_name() {
		return add_name;
	}

	public Date getAdd_time() {
		return add_time;
	}

	@Transient
	public String getAddTimeFrm() {
		return addTimeFrm;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGrid(ShglGridEntity grid) {
		this.grid = grid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setAdd_code(String add_code) {
		this.add_code = add_code;
	}

	public void setAdd_name(String add_name) {
		this.add_name = add_name;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
		if (add_time != null) {
			setAddTimeFrm(DateUtils.formateDate(add_time, "yyyy-MM-dd"));
		}
	}

	public void setAddTimeFrm(String addTimeFrm) {
		this.addTimeFrm = addTimeFrm;
	}

	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

}
