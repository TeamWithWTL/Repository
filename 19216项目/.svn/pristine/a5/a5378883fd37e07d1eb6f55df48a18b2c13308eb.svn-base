/**
 * 
 */
package com.jcwx.entity.xtbg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author m
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="xtbg_tzgg_ry_table")
public class TzggRyEntity implements Serializable{
	
	private String id;						//id
	private String tzgg_id;					//公告ID
	private String receivce_id;				//接收人ID
	private String receivce_name;			//接收人姓名
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")	
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getTzgg_id() {
		return tzgg_id;
	}
	public void setTzgg_id(String tzgg_id) {
		this.tzgg_id = tzgg_id;
	}
	public String getReceivce_id() {
		return receivce_id;
	}
	public void setReceivce_id(String receivce_id) {
		this.receivce_id = receivce_id;
	}
	public String getReceivce_name() {
		return receivce_name;
	}
	public void setReceivce_name(String receivce_name) {
		this.receivce_name = receivce_name;
	}
	
	

}
