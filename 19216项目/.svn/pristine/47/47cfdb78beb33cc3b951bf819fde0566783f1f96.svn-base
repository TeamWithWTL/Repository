package com.jcwx.entity.shgl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author xushu
 *	2017年11月03日
 *	社情民意_住户表
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shgl_sqmy_zh_table")
public class ShglSqmyZhEntity implements Serializable{
      
	private String id;       //主键
	private String sqmy_id;  //社情民意ID
	private String wgy_id;   //网格员ID
	private String sq_id;    //社区ID
	private String fwz_id;   //服务站ID
	private String xq_id;    //小区ID
	private String ly_id;    //楼宇ID
	private String unit_num; //单元号
	private String room_num; //室号
	
	private String wgyName;//网格员姓名
	
	private String xqname;   //小区名称
	private String lyname;   //楼宇名称
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSqmy_id() {
		return sqmy_id;
	}
	public void setSqmy_id(String sqmy_id) {
		this.sqmy_id = sqmy_id;
	}
	public String getWgy_id() {
		return wgy_id;
	}
	public void setWgy_id(String wgy_id) {
		this.wgy_id = wgy_id;
	}
	public String getSq_id() {
		return sq_id;
	}
	public void setSq_id(String sq_id) {
		this.sq_id = sq_id;
	}
	public String getFwz_id() {
		return fwz_id;
	}
	public void setFwz_id(String fwz_id) {
		this.fwz_id = fwz_id;
	}
	public String getXq_id() {
		return xq_id;
	}
	public void setXq_id(String xq_id) {
		this.xq_id = xq_id;
	}
	public String getLy_id() {
		return ly_id;
	}
	public void setLy_id(String ly_id) {
		this.ly_id = ly_id;
	}
	public String getUnit_num() {
		return unit_num;
	}
	public void setUnit_num(String unit_num) {
		this.unit_num = unit_num;
	}
	public String getRoom_num() {
		return room_num;
	}
	public void setRoom_num(String room_num) {
		this.room_num = room_num;
	}
	@Formula("(select ct.name from sys_account_table ct where ct.acc_code = wgy_id )")
	public String getWgyName() {
		return wgyName;
	}
	public void setWgyName(String wgyName) {
		this.wgyName = wgyName;
	}
	@Formula("( select g.name from shgl_village_table g where g.id = xq_id)")
	public String getXqname() {
		return xqname;
	}
	public void setXqname(String xqname) {
		this.xqname = xqname;
	}
	@Formula("( select g.name from shgl_building_table g where g.id = ly_id)")
	public String getLyname() {
		return lyname;
	}
	public void setLyname(String lyname) {
		this.lyname = lyname;
	}
}
