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
* 2017年5月23日
* 参数明细表
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_param_desc_table")
public class SysParamDesc implements Serializable {
	private String id;			// 主键
	private String code;		// 参数
	private String name;        // 参数名称
	private String itemCode;	// 参数项编码
	private String itemName;	// 参数项名称
	private String value1;		// 参数项值1
	private String value2;		// 参数项值2
	private String description;	// 描述
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	@Column(name="item_code")
	public String getItemCode() {
		return itemCode;
	}
	@Column(name="item_name")
	public String getItemName() {
		return itemName;
	}
	public String getValue1() {
		return value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	@Formula("( select s.name from sys_param_table s where s.code = code  )")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
