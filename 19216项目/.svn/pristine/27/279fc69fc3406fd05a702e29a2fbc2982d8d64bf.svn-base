package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 * 社情民意网格员表
 * @author jiangkia
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_sqmy_wgy_table")
public class ShglSqmyWgy implements Serializable {

	private String id;
	private String wgyId;
	private String wgyName;
	private String fwzId;
	private int dcCount;
	private String wcl;
	
	private String isSee;
	private ShglSqmyEntity sqmyInfo;
	
	
	private List<ShglSqmyDc> sqmyDcList;
	
	
	private String fwzName;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="wgy_id")
	public String getWgyId() {
		return wgyId;
	}

	public void setWgyId(String wgyId) {
		this.wgyId = wgyId;
	}
	@Column(name="wgy_name")
	public String getWgyName() {
		return wgyName;
	}

	public void setWgyName(String wgyName) {
		this.wgyName = wgyName;
	}
	@Column(name="fwz_id")
	public String getFwzId() {
		return fwzId;
	}

	public void setFwzId(String fwzId) {
		this.fwzId = fwzId;
	}
	@Column(name="dc_count")
	public int getDcCount() {
		return dcCount;
	}

	public void setDcCount(int dcCount) {
		this.dcCount = dcCount;
	}
	@Column(name="wcl")
	public String getWcl() {
		return wcl;
	}

	public void setWcl(String wcl) {
		this.wcl = wcl;
	}
	@ManyToOne(targetEntity = ShglSqmyEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "sqmy_id")
	public ShglSqmyEntity getSqmyInfo() {
		return sqmyInfo;
	}

	public void setSqmyInfo(ShglSqmyEntity sqmyInfo) {
		this.sqmyInfo = sqmyInfo;
	}

	@OneToMany(targetEntity = ShglSqmyDc.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqmy_wgy_id", referencedColumnName = "id")
	public List<ShglSqmyDc> getSqmyDcList() {
		return sqmyDcList;
	}

	public void setSqmyDcList(List<ShglSqmyDc> sqmyDcList) {
		this.sqmyDcList = sqmyDcList;
	}
	@Formula("(select ct.name from shgl_service_station_table ct where ct.id = fwz_id )")
	public String getFwzName() {
		return fwzName;
	}

	public void setFwzName(String fwzName) {
		this.fwzName = fwzName;
	}
	@Column(name="is_see")
	public String getIsSee() {
		return isSee;
	}

	public void setIsSee(String isSee) {
		this.isSee = isSee;
	}

}
