package com.jcwx.entity.dflz;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;


/**
 * 投诉处理实体类
 * @author 李伟
 * @time 2017年10月26日上午11:17:05
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_tsjb_handle_table")
public class ComplainHandleEntity implements Serializable {
	private String id; //id
	//private String tsjb_id; //举报人id
	private String acc_code;//处理人
	private String content;//处理内容
	private Date create_date;//创建时间
	private Date handle_date;//处理时间
	private String fmtCreate_date;//时间格式化
	private String fmtHandle_date;//时间格式化
	private String cl_status;//是否处理1为处理2为未处理
	private String pf_status; //是否转派1为未转派党风廉政处理，2 为已派发具体人员处理
	private ComplainEntity complainEntity;//多对一
	 @Id
	 @GeneratedValue(generator = "uuid")
	 @GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*@Column(name="tsjb_id")
	public String getTsjb_id() {
		return tsjb_id;
	}
	public void setTsjb_id(String tsjb_id) {
		this.tsjb_id = tsjb_id;
	}*/
	public String getAcc_code() {
		return acc_code;
	}
	public void setAcc_code(String acc_code) {
		this.acc_code = acc_code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
		if (create_date != null) {
			setFmtCreate_date(DateUtils.formateDate(create_date, "yyyy-MM-dd"));
		}
	}
	
	
	public Date getHandle_date() {
		return handle_date;
	}
	public void setHandle_date(Date handle_date) {
		this.handle_date = handle_date;
		if (handle_date != null) {
			setFmtHandle_date(DateUtils.formateDate(handle_date, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	public String getCl_status() {
		return cl_status;
	}
	public void setCl_status(String cl_status) {
		this.cl_status = cl_status;
	}
	public String getPf_status() {
		return pf_status;
	}
	public void setPf_status(String pf_status) {
		this.pf_status = pf_status;
	}
	
	@ManyToOne(targetEntity = ComplainEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name ="tsjb_id")
	public ComplainEntity getComplainEntity() {
		return complainEntity;
	}
	public void setComplainEntity(ComplainEntity complainEntity) {
		this.complainEntity = complainEntity;
	}
	@Transient
	public String getFmtCreate_date() {
		return fmtCreate_date;
	}
	public void setFmtCreate_date(String fmtCreate_date) {
		this.fmtCreate_date = fmtCreate_date;
	}
	@Transient
	public String getFmtHandle_date() {
		return fmtHandle_date;
	}
	public void setFmtHandle_date(String fmtHandle_date) {
		this.fmtHandle_date = fmtHandle_date;
	}
	
	
	
}
