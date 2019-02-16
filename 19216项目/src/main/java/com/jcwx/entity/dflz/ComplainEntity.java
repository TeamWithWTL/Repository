package com.jcwx.entity.dflz;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
/**
 * 投诉实体类
 * @author 李伟
 * @time 2017年10月26日上午11:16:42
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_tsjb_table")
public class ComplainEntity implements Serializable{
	private String id;//投诉id
	private String jb_type;//投诉类型
	private String title;//投诉标题
	private String content; //投诉内容
	private Date create_time;//创建时间
	private String fmtCreate_time;//时间格式化
	private String user_id;//投诉人id
	private String user_name;//投诉人name
	private String is_nm;//是否匿名
	private String sh_user_id;//审核人id
	private String sh_user_name;//审核人name
	private String sh_status;//审核状态
	
	private List<CompAcceEntity> compAcceList; //投诉附件
	private List<ComplainHandleEntity> compHandList;
	//private ComplainHandleEntity complainHandleEntity;//投诉处理信息
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJb_type() {
		return jb_type;
	}
	public void setJb_type(String jb_type) {
		this.jb_type = jb_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	/**
	 * 格式化时间
	 * @author 李伟
	 * @time 2017年11月2日下午4:46:35
	 * @param create_time
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
		if (create_time != null) {
			setFmtCreate_time(DateUtils.formateDate(create_time, "yyyy-MM-dd"));
		}
	}
	
	@Transient
	public String getFmtCreate_time() {
		return fmtCreate_time;
	}
	public void setFmtCreate_time(String fmtCreate_time) {
		this.fmtCreate_time = fmtCreate_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getIs_nm() {
		return is_nm;
	}
	public void setIs_nm(String is_nm) {
		this.is_nm = is_nm;
	}
	public String getSh_user_id() {
		return sh_user_id;
	}
	public void setSh_user_id(String sh_user_id) {
		this.sh_user_id = sh_user_id;
	}
	public String getSh_user_name() {
		return sh_user_name;
	}
	public void setSh_user_name(String sh_user_name) {
		this.sh_user_name = sh_user_name;
	}
	public String getSh_status() {
		return sh_status;
	}
	public void setSh_status(String sh_status) {
		this.sh_status = sh_status;
	}
	@OneToMany(targetEntity = CompAcceEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "tsjb_id", referencedColumnName = "id")
	public List<CompAcceEntity> getCompAcceList() {
		return compAcceList;
	}
	public void setCompAcceList(List<CompAcceEntity> compAcceList) {
		this.compAcceList = compAcceList;
	}
	/*@OneToOne(targetEntity = ComplainHandleEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName = "tsjb_id")
	public ComplainHandleEntity getComplainHandleEntity() {
		return complainHandleEntity;
	}
	public void setComplainHandleEntity(ComplainHandleEntity complainHandleEntity) {
		this.complainHandleEntity = complainHandleEntity;
	}
	@Override
	public String toString() {
		return "ComplainEntity [id=" + id + ", jb_type=" + jb_type + ", title=" + title + ", content=" + content
				+ ", create_time=" + create_time + ", user_id=" + user_id + ", user_name=" + user_name + ", is_nm="
				+ is_nm + ", sh_user_id=" + sh_user_id + ", sh_user_name=" + sh_user_name + ", sh_status=" + sh_status
				+ ", compAcceList=" + compAcceList + ", complainHandleEntity=" + complainHandleEntity + "]";
	}*/
	@OneToMany(targetEntity = ComplainHandleEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "tsjb_id", referencedColumnName = "id")
	public List<ComplainHandleEntity> getCompHandList() {
		return compHandList;
	}
	public void setCompHandList(List<ComplainHandleEntity> compHandList) {
		this.compHandList = compHandList;
	}
	
	@Override
	public String toString() {
		return "ComplainEntity [id=" + id + ", jb_type=" + jb_type + ", title=" + title + ", content=" + content
				+ ", create_time=" + create_time + ", user_id=" + user_id + ", user_name=" + user_name + ", is_nm="
				+ is_nm + ", sh_user_id=" + sh_user_id + ", sh_user_name=" + sh_user_name + ", sh_status=" + sh_status
				+ ", compAcceList=" + compAcceList + ", compHandList=" + compHandList + "]";
	}
	
	
	
	
}  
