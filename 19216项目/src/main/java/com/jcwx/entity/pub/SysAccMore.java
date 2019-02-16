package com.jcwx.entity.pub;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author 奇点（2017年12月21日）
 * app--居民归属信息
 */
@Entity
@Table(name = "sys_acc_more_table")
public class SysAccMore {
	
	private String id;
	private String accCode;//用户名
	private String commId;// 社区id
	private String ssId;// 服务站id
	private String gridId;// 网格id
	
	private String sqName;// 社区名称
	private String fwzName;// 服务站名称
	private String wgName;// 网格名称
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "acc_code")
	public String getAccCode() {
		return accCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	@Column(name = "commId")
	public String getCommId() {
		return commId;
	}
	public void setCommId(String commId) {
		this.commId = commId;
	}
	@Column(name = "ssId")
	public String getSsId() {
		return ssId;
	}
	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	@Column(name = "gridId")
	public String getGridId() {
		return gridId;
	}
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	@Formula("(select ct.name from shgl_community_table ct where ct.id = commId )")
	public String getSqName() {
		return sqName;
	}
	public void setSqName(String sqName) {
		this.sqName = sqName;
	}
	@Formula("(select ss.name from shgl_service_station_table ss where ss.id = ssId )")
	public String getFwzName() {
		return fwzName;
	}
	public void setFwzName(String fwzName) {
		this.fwzName = fwzName;
	}
	@Formula("(select gr.name from shgl_grid_table gr where gr.id = gridId )")
	public String getWgName() {
		return wgName;
	}
	public void setWgName(String wgName) {
		this.wgName = wgName;
	}
}
