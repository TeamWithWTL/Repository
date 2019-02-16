package com.jcwx.entity.pub;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
* @author MaBo
* 2017年5月23日
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_param_table")
public class SysParam implements Serializable {
	private String code;	// 参数编码
	private String name;	// 参数名称
	private String value1;	// 参数值1
	private String value2;	// 参数值2
	private String description;					// 参数描述
	private List<SysParamDesc> sysParamDesc;	// 参数项
	
	@Id
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getValue1() {
		return value1;
	}
	public String getValue2() {
		return value2;
	}
	public String getDescription() {
		return description;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(targetEntity=SysParamDesc.class, cascade={CascadeType.REMOVE}, fetch=FetchType.LAZY)
	@JoinColumn(name="code", referencedColumnName="code")
	@OrderBy("itemCode asc")
	public List<SysParamDesc> getSysParamDesc() {
		return sysParamDesc;
	}
	public void setSysParamDesc(List<SysParamDesc> sysParamDesc) {
		this.sysParamDesc = sysParamDesc;
	}
	
}
