package com.jcwx.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * @author Gs
 * 2018年5月16日
 * 每日签到
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_sign_in_table")
public class SysSignInEntity implements Serializable{
	
	private String id;			// 主键
	private String addCode;		// 签到用户ID
	private String addName;		// 签到人姓名
	private Date signTimeAm;	// 上午签到时间
	private Date signTimePm;	// 下午签到时间
	private String commId;		// 所属社区
	private String fwzId;		// 所属服务站
	private String gridId;		// 所属网格
	
	private String FmtTimeAm;	// 格式化上午签到时间
	private String FmtTimePm;	// 格式化下午签到时间
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "add_code")
	public String getAddCode() {
		return addCode;
	}
	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	@Column(name = "add_name")
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	@Column(name = "sign_time_am")
	public Date getSignTimeAm() {
		return signTimeAm;
	}
	public void setSignTimeAm(Date signTimeAm) {
		this.signTimeAm = signTimeAm;
		if(null != signTimeAm){
			setFmtTimeAm(DateUtils.formateDate(signTimeAm, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	@Column(name = "sign_time_pm")
	public Date getSignTimePm() {
		return signTimePm;
	}
	public void setSignTimePm(Date signTimePm) {
		this.signTimePm = signTimePm;
		if(null != signTimePm){
			setFmtTimePm(DateUtils.formateDate(signTimePm, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}
	public void setCommId(String commId) {
		this.commId = commId;
	}
	@Column(name = "fwz_id")
	public String getFwzId() {
		return fwzId;
	}
	public void setFwzId(String fwzId) {
		this.fwzId = fwzId;
	}
	@Column(name = "grid_id")
	public String getGridId() {
		return gridId;
	}
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	@Transient
	public String getFmtTimeAm() {
		return FmtTimeAm;
	}
	public void setFmtTimeAm(String fmtTimeAm) {
		FmtTimeAm = fmtTimeAm;
	}
	@Transient
	public String getFmtTimePm() {
		return FmtTimePm;
	}
	public void setFmtTimePm(String fmtTimePm) {
		FmtTimePm = fmtTimePm;
	}
}
