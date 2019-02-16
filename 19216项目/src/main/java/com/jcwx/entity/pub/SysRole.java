package com.jcwx.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
* @author MaBo
* 2016年12月2日
* 系统角色
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_role_table")
public class SysRole implements Serializable {
	private String roleCode;	// 角色编码
	private String roleName;	// 角色名称
	private String note;		// 备注
	private Integer orderNo;	// 排序
	private String addCode;		// 添加人编号
	private String addName;		// 添加人姓名
	private Date addTime;		// 添加时间
	private String addTimeFmt;	// 格式化的添加时间
	
	private String yhxzjs;         //用户选中角色的标志1选中
	@Id
	@Column(name="role_code")
	public String getRoleCode() {
		return roleCode;
	}
	@Column(name="role_name")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setRoleName(String roleName) {
		this.roleName = StringUtil.filterchart(roleName);
	}
	@Column(name="add_time")
	public Date getAddTime() {
		return addTime;
	}
	@Transient
	public String getAddTimeFmt() {
		return addTimeFmt;
	}
	public String getNote() {
		return note;
	}
	@Column(name="order_no")
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
		if(addTime != null){
			setAddTimeFmt(DateUtils.formateDate(addTime, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	public void setAddTimeFmt(String addTimeFmt) {
		this.addTimeFmt = addTimeFmt;
	}
	public void setNote(String note) {
		this.note = StringUtil.filterchart(note);
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name="add_code")
	public String getAddCode() {
		return addCode;
	}
	@Column(name="add_name")
	public String getAddName() {
		return addName;
	}
	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	@Transient
	public String getYhxzjs() {
		return yhxzjs;
	}
	public void setYhxzjs(String yhxzjs) {
		this.yhxzjs = yhxzjs;
	}
}
