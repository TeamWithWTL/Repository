package com.jcwx.entity.shgl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员分类
 * @author wangjinxiao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_inmate_type_table")
public class ShglInmateTypeEntity implements Serializable{
	
	private String id;
	private String category;	//类别id
	
	private ShglInmateEntity inmate;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@ManyToOne(targetEntity = ShglInmateEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "inmate_id")
	public ShglInmateEntity getInmate() {
		return inmate;
	}
	public void setInmate(ShglInmateEntity inmate) {
		this.inmate = inmate;
	}


	
}
