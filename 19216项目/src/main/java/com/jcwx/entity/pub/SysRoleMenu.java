package com.jcwx.entity.pub;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* @author MaBo
* 2017年5月23日
* 角色菜单表
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_role_menu_table")
public class SysRoleMenu implements Serializable {
	private String id;			// 主键
	private String roleCode;	// 角色编码
	private SysMenu menu;		// 菜单
	private String methodCodes;	// 功能(功能之间用,分隔)
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	@Column(name="role_code")
	public String getRoleCode() {
		return roleCode;
	}
	@OneToOne(targetEntity=SysMenu.class, fetch=FetchType.LAZY)
	@JoinColumn(name="menu_code")
	public SysMenu getMenu() {
		return menu;
	}
	@Column(name="method_codes")
	public String getMethodCodes() {
		return methodCodes;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}
	public void setMethodCodes(String methodCodes) {
		this.methodCodes = methodCodes;
	}
	
}
