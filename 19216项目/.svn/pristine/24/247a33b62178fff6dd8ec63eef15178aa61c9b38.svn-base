package com.jcwx.entity.pub;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
* @author xushu
* 2016年12月2日
* 用户对应的多角色表
*/
@SuppressWarnings("serial")
@Entity
@Table(name="sys_acc_role_table")
public class SysAccRole implements Serializable {
	private String id;          //主键ID
	private String accCode;    //用户id
	private String roleCode;	// 角色编码
	private String roleName;	// 角色名称
	
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
	@Column(name="role_name")
	public String getRoleName() {
		return roleName;
	}
	@Column(name="acc_code")
	public String getAccCode() {
		return accCode;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
