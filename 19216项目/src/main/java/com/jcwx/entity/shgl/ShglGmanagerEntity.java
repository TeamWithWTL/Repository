package com.jcwx.entity.shgl;

import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.StringUtil;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 政府机构负责人
 * @author wangjinxiao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_gmanager_table")
public class ShglGmanagerEntity implements Serializable{
	
	private String id;
	private String goveId;//政府机构Id
	private String manager;//负责人
	private String phone; //联系方式
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "gove_id")
	public String getGoveId() {
		return goveId;
	}
	public void setGoveId(String goveId) {
		this.goveId = goveId;
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
