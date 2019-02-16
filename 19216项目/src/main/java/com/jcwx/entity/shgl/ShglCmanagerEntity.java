package com.jcwx.entity.shgl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.StringUtil;

/**
 * 社区负责人
 * @author wangjinxiao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shgl_cmanager_table")
public class ShglCmanagerEntity implements Serializable{
	
	private String id;
	private String commId;  //社区管理ID
	private String manager; //负责人
	private String phone;   //联系方式
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}
	public void setCommId(String commId) {
		this.commId = commId;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = StringUtil.filterchart(manager);
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = StringUtil.filterchart(phone);
	}
}
