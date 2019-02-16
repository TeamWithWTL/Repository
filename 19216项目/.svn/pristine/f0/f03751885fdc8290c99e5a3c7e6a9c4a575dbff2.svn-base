package com.jcwx.entity.xtbg;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 *  公文处理
 * @author jiangkia
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_gwcl_table")
public class Document implements Serializable{

	private String id;			//主键
	private String type; 		//类型（1发文，2收文）
	private String title;	//标题
	private String num;	//文号
	private String level;	//密级
	private String dept;//发文部门
	private Date createDate;//发文日期
	private String createUserId;//发文人ID
	private String createUserName;//发文人姓名
	private String nwrSuggest;//拟文人意见
	private String ldSuggest;//领导意见
	private String dzbSuggest;//党政办意见
	private String jdbSuggest;//街道办意见
	private String result;//办理结果
	private String remark;//备注
	private String fwStep;//发文步骤（1下发党政办负责人审批，2领导审批结束,3转发）
	private String swStep;//收文步骤(1下发党政办负责人审批，2党政办审批结束，3，街道办审批，4街道办审批结束,5转发)
	private String jkStatus;//紧急程度(1，普通，2加急)
	private Date endDate;//结束时间
	private String isOver;//是否结束1，是，2否)
	
	
	private String createDatesh;
	private String endDatesh;
	private List<DocumentAttrs> attrs;
	
	private List<DocumentDeal> deals;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="num")
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column(name="level")
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Column(name="dept")
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if (createDate != null) {
			setCreateDatesh(DateUtils.formateDate(createDate, "yyyy-MM-dd"));
		}
	}
	@Column(name="create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name="create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	@Column(name="nwr_suggest")
	public String getNwrSuggest() {
		return nwrSuggest;
	}
	public void setNwrSuggest(String nwrSuggest) {
		this.nwrSuggest = nwrSuggest;
	}
	@Column(name="ld_suggest")
	public String getLdSuggest() {
		return ldSuggest;
	}
	public void setLdSuggest(String ldSuggest) {
		this.ldSuggest = ldSuggest;
	}
	@Column(name="dzb_suggest")
	public String getDzbSuggest() {
		return dzbSuggest;
	}
	public void setDzbSuggest(String dzbSuggest) {
		this.dzbSuggest = dzbSuggest;
	}
	@Column(name="jdb_suggest")
	public String getJdbSuggest() {
		return jdbSuggest;
	}
	public void setJdbSuggest(String jdbSuggest) {
		this.jdbSuggest = jdbSuggest;
	}
	@Column(name="result")
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fw_step")
	public String getFwStep() {
		return fwStep;
	}
	public void setFwStep(String fwStep) {
		this.fwStep = fwStep;
	}
	@Column(name="sw_step")
	public String getSwStep() {
		return swStep;
	}
	public void setSwStep(String swStep) {
		this.swStep = swStep;
	}
	@Column(name="jk_status")
	public String getJkStatus() {
		return jkStatus;
	}
	public void setJkStatus(String jkStatus) {
		this.jkStatus = jkStatus;
	}
	@Column(name="is_over")
	public String getIsOver() {
		return isOver;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	@OneToMany(targetEntity = DocumentAttrs.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "gwcl_id", referencedColumnName = "id")
	public List<DocumentAttrs> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<DocumentAttrs> attrs) {
		this.attrs = attrs;
	}
	@OneToMany(targetEntity = DocumentDeal.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "gw_id", referencedColumnName = "id")
	public List<DocumentDeal> getDeals() {
		return deals;
	}
	public void setDeals(List<DocumentDeal> deals) {
		this.deals = deals;
	}
	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		if (endDate != null) {
			setEndDatesh(DateUtils.formateDate(endDate, "yyyy-MM-dd"));
		}
	}
	@Transient
	public String getCreateDatesh() {
		return createDatesh;
	}
	public void setCreateDatesh(String createDatesh) {
		this.createDatesh = createDatesh;
	}
	@Transient
	public String getEndDatesh() {
		return endDatesh;
	}
	public void setEndDatesh(String endDatesh) {
		this.endDatesh = endDatesh;
	}

	
}
