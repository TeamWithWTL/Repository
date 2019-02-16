package com.jcwx.entity.shzz;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author xushu
 *	2017年10月23日
 *	组织信息
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shzz_zzxx_table")
public class ZzxxEntity implements Serializable{
	
	private String id;               //主键
	private String name;             //组织名称
	private String sqid;             //社区id
	private String fwid;             //服务站id
	private String gridid;           //网格id
	private Date create_time;        //成立时间
	private String brief;            //简介
	private String zzxz;             //组织性质
	private String zzjg;             //组织机构代码
	private String shxydm;           //社会信用代码
	private String yyzz_img_url;     //营业执照
	private String phone;            //联系方式
	private String fr_id;            //法人ID
	private String fr_name;          //法人姓名
	private String fr_phone;        //法人联系方式
	private String sc_status;       //审查状态（1默认通过）
	private Date   enter_date;      //入驻时间
	
	private String xs_enter_date;  //格式化入驻时间
	private String xscreate_timeFmt; //格式化组织成立时间
	private String zzxzName;       //组织性质名称 
	private List<ZzxxAttrsEntity>   attrList; //附件
	private Integer sl;						//参与活动数量
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
		if(null != create_time){
			setXscreate_timeFmt(DateUtils.formateDate(create_time, "yyyy-MM-dd"));
		}
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = StringUtil.filterchart(brief);
	}
	public String getZzxz() {
		return zzxz;
	}
	public void setZzxz(String zzxz) {
		this.zzxz = zzxz;
	}
	public String getZzjg() {
		return zzjg;
	}
	public void setZzjg(String zzjg) {
		this.zzjg = zzjg;
	}
	public String getShxydm() {
		return shxydm;
	}
	public void setShxydm(String shxydm) {
		this.shxydm = shxydm;
	}
	public String getYyzz_img_url() {
		return yyzz_img_url;
	}
	public void setYyzz_img_url(String yyzz_img_url) {
		this.yyzz_img_url = yyzz_img_url;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = StringUtil.filterchart(phone);
	}
	public String getFr_id() {
		return fr_id;
	}
	public void setFr_id(String fr_id) {
		this.fr_id = fr_id;
	}
	public String getFr_name() {
		return fr_name;
	}
	public void setFr_name(String fr_name) {
		this.fr_name = fr_name;
	}
	public String getFr_phone() {
		return fr_phone;
	}
	public void setFr_phone(String fr_phone) {
		this.fr_phone = fr_phone;
	}
	public String getSc_status() {
		return sc_status;
	}
	public void setSc_status(String sc_status) {
		this.sc_status = sc_status;
	}
	public Date getEnter_date() {
		return enter_date;
	}
	public void setEnter_date(Date enter_date) {
		this.enter_date = enter_date;
		if(null != enter_date){
			setXs_enter_date(DateUtils.formateDate(enter_date, "yyyy-MM-dd"));
		}
	}
	@OneToMany(targetEntity = ZzxxAttrsEntity.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shzzxx_id", referencedColumnName = "id")
	public List<ZzxxAttrsEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<ZzxxAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	@Transient
	public String getXs_enter_date() {
		return xs_enter_date;
	}
	public void setXs_enter_date(String xs_enter_date) {
		this.xs_enter_date = xs_enter_date;
	}
	
	@Formula("( select g.item_name from sys_param_desc_table g where g.value1 = zzxz and g.code ='10011' )")
	public String getZzxzName() {
		return zzxzName;
	}
	public void setZzxzName(String zzxzName) {
		this.zzxzName = zzxzName;
	}
	public String getSqid() {
		return sqid;
	}
	public void setSqid(String sqid) {
		this.sqid = sqid;
	}
	public String getFwid() {
		return fwid;
	}
	public void setFwid(String fwid) {
		this.fwid = fwid;
	}
	public String getGridid() {
		return gridid;
	}
	public void setGridid(String gridid) {
		this.gridid = gridid;
	}
	@Transient
	public String getXscreate_timeFmt() {
		return xscreate_timeFmt;
	}
	public void setXscreate_timeFmt(String xscreate_timeFmt) {
		this.xscreate_timeFmt = xscreate_timeFmt;
	}
	@Transient
	public Integer getSl() {
		return sl;
	}
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	
}
