package com.jcwx.entity.pub;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author MaBo 2016年12月2日 系统用户 延迟加载用户详情和用户角色（如果不需要将信息存入Sessioin，可用此实体类）
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_account_table")
public class SysAccCountLazy implements Serializable {
	private String accCode;                // 用户登录名
	private String pwd;                    // 用户密码
	private String role_code;              // 用户角色
	private String deptId;     //用户部门
	private String commId;                 // 社区id
	private String ssId;                  // 服务站id
	private String gridId;                // 网格id
	private String phone;                 // 手机号
	private String cardNo;                // 身份证号
	private String validFlag;             // 有效标识 0-无效、1-有效
	private String appSerial;             // 手机串号
	private Date addTime;                 // 创建日期
	private String addTimeFmt;            // 格式化创建日期
	
	private String name;                  //用户名
	private String sex;                   //性别
	private String duty;                  //职务
	private String pic_path;              //头像路径
	private String fzr_type;              //是否是主要负责人1是2否
	private String office_tel;            //办公室电话
	private Integer integral;             //积分（默认为0）
	private String zh_type;               //账号类型1内部 2注册
	private Integer orderNo;				  //排序默认是0
	
	private List<SysAccRole> sysaccrole;	//用户对应的多角色表
	
	private String commName;               //社区名称
	private String ssName;                 //服务站名称
	private String gridName;               //网格名称
	private String wcl;                    //网格员针对社情民意调查的完成率
	private String deptName;	          //所在部门名称
	private String subName;//截取用户名
	@Id
	@Column(name = "acc_code")
	public String getAccCode() {
		return accCode;
	}

	public String getPwd() {
		return pwd;
	}

	@Column(name = "add_time")
	public Date getAddTime() {
		return addTime;
	}

	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
		if (addTime != null) {
			setAddTimeFmt(DateUtils.formateDate(addTime, "yyyy-MM-dd"));
		}
	}
	@Transient
	public String getAddTimeFmt() {
		return addTimeFmt;
	}

	public void setAddTimeFmt(String addTimeFmt) {
		this.addTimeFmt = addTimeFmt;
	}

	@Column(name = "app_serial")
	public String getAppSerial() {
		return appSerial;
	}

	public String getPhone() {
		return phone;
	}

	@Column(name = "card_no")
	public String getCardNo() {
		return cardNo;
	}

	@Column(name = "valid_flag")
	public String getValidFlag() {
		return validFlag;
	}

	public void setAppSerial(String appSerial) {
		this.appSerial = StringUtil.filterchart(appSerial);
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Column(name = "dept_id")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}

	@Column(name = "ss_id")
	public String getSsId() {
		return ssId;
	}

	@Column(name = "grid_id")
	public String getGridId() {
		return gridId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	public void setSsId(String ssId) {
		this.ssId = ssId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	@OneToMany(targetEntity = SysAccRole.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "acc_code", referencedColumnName = "acc_code")
	public List<SysAccRole> getSysaccrole() {
		return sysaccrole;
	}
	public void setSysaccrole(List<SysAccRole> sysaccrole) {
		this.sysaccrole = sysaccrole;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
		if(name != null && name.length()>5){
			setSubName(name.substring(0,6)+"..");
		}else{
			setSubName(name);
		}
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = StringUtil.filterchart(duty);
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getFzr_type() {
		return fzr_type;
	}

	public void setFzr_type(String fzr_type) {
		this.fzr_type = fzr_type;
	}

	public String getOffice_tel() {
		return office_tel;
	}

	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getZh_type() {
		return zh_type;
	}

	public void setZh_type(String zh_type) {
		this.zh_type = zh_type;
	}

	@Formula("( select g.name from shgl_community_table g where g.id = comm_id)")
	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	@Formula("( select g.name from shgl_service_station_table g where g.id = ss_id)")
	public String getSsName() {
		return ssName;
	}

	public void setSsName(String ssName) {
		this.ssName = ssName;
	}

	@Formula("( select g.name from shgl_grid_table g where g.id = grid_id)")
	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	@Transient
	public String getWcl() {
		return wcl;
	}

	public void setWcl(String wcl) {
		this.wcl = wcl;
	}
	@Formula("(SELECT a.dept_name FROM sys_department_table  a WHERE a.dept_id = dept_id)")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Transient
	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
