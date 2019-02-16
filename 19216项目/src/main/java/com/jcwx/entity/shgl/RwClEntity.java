package com.jcwx.entity.shgl;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_task_deal_table")
public class RwClEntity {

	private String id; // id
	private String pre_emp; // 上一处理人ID
	private String cur_emp; // 当前处理人ID
	private String cur_role_id; // 当前处理角色ID
	private String content; // 反馈信息
	private Date starte_date; // 创建时间
	private Date end_date; // 处理时间
	private String is_down; // 是否已下发（1 是 2 否）
	private String is_back; // 是否已反馈（1 是 2 否）
	private String starte_dates; // 创建时间格式化
	private String end_dates; // 处理时间格式化
	private RwglEntity rwglEntity;
	private List<RwclAttrEntity> attrList;	//附件集合
	
	private String preName;//上一任处理人姓名
	private String curName;//当前处理人姓名

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPre_emp() {
		return pre_emp;
	}

	public void setPre_emp(String pre_emp) {
		this.pre_emp = pre_emp;
	}

	public String getCur_emp() {
		return cur_emp;
	}

	public void setCur_emp(String cur_emp) {
		this.cur_emp = cur_emp;
	}

	public String getCur_role_id() {
		return cur_role_id;
	}

	public void setCur_role_id(String cur_role_id) {
		this.cur_role_id = cur_role_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStarte_date() {
		return starte_date;
	}

	public void setStarte_date(Date starte_date) {
		this.starte_date = starte_date;
		if (starte_date != null) {
			setStarte_dates(DateUtils.formateDate(starte_date, "yyyy-MM-dd"));
		}
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
		if (end_date != null) {
			setEnd_dates(DateUtils.formateDate(end_date, "yyyy-MM-dd HH:mm"));
		}
	}

	public String getIs_down() {
		return is_down;
	}

	public void setIs_down(String is_down) {
		this.is_down = is_down;
	}

	@Transient
	public String getStarte_dates() {
		return starte_dates;
	}

	public void setStarte_dates(String starte_dates) {
		this.starte_dates = starte_dates;
	}

	@Transient
	public String getEnd_dates() {
		return end_dates;
	}

	public void setEnd_dates(String end_dates) {
		this.end_dates = end_dates;
	}

	public String getIs_back() {
		return is_back;
	}

	public void setIs_back(String is_back) {
		this.is_back = is_back;
	}
	@Formula("(select ss.name from sys_account_table ss where ss.acc_code = pre_emp )")
	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}
	@Formula("(select ct.name from sys_account_table ct where ct.acc_code = cur_emp )")
	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}
	@ManyToOne(targetEntity = RwglEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public RwglEntity getRwglEntity() {
		return rwglEntity;
	}

	public void setRwglEntity(RwglEntity rwglEntity) {
		this.rwglEntity = rwglEntity;
	}
	@OneToMany(targetEntity = RwclAttrEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_deal_id", referencedColumnName = "id")
	public List<RwclAttrEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<RwclAttrEntity> attrList) {
		this.attrList = attrList;
	}
}
