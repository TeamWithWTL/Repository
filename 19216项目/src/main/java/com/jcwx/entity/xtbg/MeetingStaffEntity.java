package com.jcwx.entity.xtbg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 参会人员实体类
 * @author 李伟
 * @time 2017年10月29日上午1:52:35
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_hygl_ry_table")
public class MeetingStaffEntity implements Serializable{
	private String  id; //主键
	private String hygl_id;//会议管理id
	private String acc_code;//参会人员id
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHygl_id() {
		return hygl_id;
	}
	public void setHygl_id(String hygl_id) {
		this.hygl_id = hygl_id;
	}
	public String getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(String acc_code) {
		this.acc_code = acc_code;
	}
	@Override
	public String toString() {
		return "MeetingStaffEntity [id=" + id + ", hygl_id=" + hygl_id + ", acc_code=" + acc_code + "]";
	}
	
}
