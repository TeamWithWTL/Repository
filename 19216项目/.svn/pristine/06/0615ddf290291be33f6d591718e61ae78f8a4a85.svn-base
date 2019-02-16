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
 * 
 * 部门Entity
 * @author zhangkai
 *
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_department_table")
public class SysDepartment implements Serializable {
	private String deptId;		//部门ID
	private String deptName;	//部门名称
	private String parentId;	// 上级部门ID
	private Integer orderNo;		//排序字段
	private String parentName;	//上级部门名称
	
	@Id
	@Column(name="dept_id")
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name="dept_name")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name="parent_id")
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name="order_no")
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@Formula("(select d.dept_name from sys_department_table d where d.dept_id=parent_id)")
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
