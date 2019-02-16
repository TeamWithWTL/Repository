package com.jcwx.entity.pub;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author MaBo 2016年12月2日 系统菜单
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_menu_table")
public class SysMenu implements Serializable {
	private String menuCode; // 菜单编码
	private String parentId; // 上级编码
	private String menuName; // 菜单名称
	private String aliasName; // 别名
	private String url; // 链接URL
	private Integer orderNo; // 排序
	private String icoUrl; // 图标路径
	private List<SysMethod> methods; // 菜单下所包含的功能

	private List<SysMenu> thirdMenus;

	@Id
	@Column(name = "menu_code")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getMenuCode() {
		return menuCode;
	}

	@Column(name = "menu_name")
	public String getMenuName() {
		return menuName;
	}

	@Column(name = "alias_name")
	public String getAliasName() {
		return aliasName;
	}

	public String getUrl() {
		return url;
	}

	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "ico_url")
	public String getIcoUrl() {
		return icoUrl;
	}

	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}

	@OneToMany(targetEntity = SysMethod.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_code", referencedColumnName = "menu_code")
	@OrderBy("order_no asc")
	public List<SysMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<SysMethod> methods) {
		this.methods = methods;
	}

	@Column(name = "parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Transient
	public List<SysMenu> getThirdMenus() {
		return thirdMenus;
	}

	public void setThirdMenus(List<SysMenu> thirdMenus) {
		this.thirdMenus = thirdMenus;
	}

}
