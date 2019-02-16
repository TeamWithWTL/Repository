package com.jcwx.entity.shgl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.StringUtil;

/**
 * 网格负责人
 * @author wangjinxiao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_grid_manager_table")
public class ShglGridManagerEntity implements Serializable{
	
	private String id;
	private String gridId;//网格id
	private String manager;//负责人
	private String phone; //联系电话
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "grid_id")
	public String getGridId() {
		return gridId;
	}
	public void setGridId(String gridId) {
		this.gridId = gridId;
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
