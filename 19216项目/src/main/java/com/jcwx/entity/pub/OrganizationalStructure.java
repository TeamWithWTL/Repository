package com.jcwx.entity.pub;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
* @author MaBo
* 2017年7月24日
* 组织结构
*/
@SuppressWarnings("serial")
@Entity
@Table(name="organizational_structure_table")
public class OrganizationalStructure implements Serializable {
	private String orgId;		// 组织ID
	private String orgName;		// 组织名称
	private String parentId;	// 上级ID
	private String parentName;	// 上级名称
	private Integer orderNo;	// 排序
	
	@Id
	@Column(name="org_id")
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getOrgId() {
		return orgId;
	}
	@Column(name="org_name")
	public String getOrgName() {
		return orgName;
	}
	@Column(name="parent_id")
	public String getParentId() {
		return parentId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Formula("(select a.org_name from organizational_structure_table a where a.org_id=parent_id)")
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Column(name="order_no")
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
}
