package com.jcwx.entity.pub;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
* @author MaBo
* 2017年5月23日
* 系统功能方法（按钮功能，增删改查等）
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_method_table")
public class SysMethod implements Serializable {
	private String methodCode;	// 功能编号
	private String methodName;	// 功能名称
	private String methodUrl;	// 功能URL
	private Integer orderNo;	// 功能排序
	private String menuCode;	// 所属菜单编号
	private String menuName;	// 所属菜单名称
	private String icoUrl;		// 图标
	
	@Id
	@Column(name="method_code")
	public String getMethodCode() {
		return methodCode;
	}
	@Column(name="method_name")
	public String getMethodName() {
		return methodName;
	}
	@Column(name="method_url")
	public String getMethodUrl() {
		return methodUrl;
	}
	@Column(name="menu_code")
	public String getMenuCode() {
		return menuCode;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	@Column(name="order_no")
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name="ico_url")
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	@Formula("(select a.menu_name from sys_menu_table a where a.menu_code=menu_code)")
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
}
