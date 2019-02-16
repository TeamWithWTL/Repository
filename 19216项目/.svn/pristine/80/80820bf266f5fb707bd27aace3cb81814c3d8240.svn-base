package com.jcwx.entity.shgl;

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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author xushu
 *	2017年11月03日
 *	社情民意
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shgl_sqmy_table")
public class ShglSqmyEntity implements Serializable{
	
   private String id;           //主键
   private String sq_id;        //社区ID
   private String title;        //标题
   private String content;      //描述
   private Date   start_date;   //开始时间
   private Date   end_date;     //结束时间
   private int    dc_num;       //调查频率(调查的户数)
   private String status;       //下发状态  (1保存，2下发)
   private String fbr_id;       //发布人ID
   private String fbr_name;     //发布人姓名
   private Date   create_date;  //发布时间
   private String is_over;      //是否结束0未结束1结束
   
   private List<ShglSqmyWgy>   wgyList; //社情民意_住户表
   private List<ShglSqmyAttrsEntity>   attrList; //社情民意_附件表
   
   private String XSstart_date;  //格式化开始时间
   private String XSend_date;    //格式化结束时间
   private String XScreate_date; //格式化下发时间
   
   private String commName;
    @Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
    public String getId() {
    	return id;
    }
    public void setId(String id) {
    	this.id = id;
    }
	public String getSq_id() {
		return sq_id;
	}
	public void setSq_id(String sq_id) {
		this.sq_id = sq_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtil.filterchart(title);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = StringUtil.filterchart(content);
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
		if(null != start_date){
			setXSstart_date(DateUtils.formateDate(start_date, "yyyy-MM-dd"));
		}
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
		if(null != end_date){
			setXSend_date(DateUtils.formateDate(end_date, "yyyy-MM-dd"));
		}
	}
	public int getDc_num() {
		return dc_num;
	}
	public void setDc_num(int dc_num) {
		this.dc_num = dc_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFbr_id() {
		return fbr_id;
	}
	public void setFbr_id(String fbr_id) {
		this.fbr_id = fbr_id;
	}
	public String getFbr_name() {
		return fbr_name;
	}
	public void setFbr_name(String fbr_name) {
		this.fbr_name = fbr_name;
	}
	@Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
		if(null != create_date){
			setXScreate_date(DateUtils.formateDate(create_date, "yyyy-MM-dd"));
		}
	}
	public String getIs_over() {
		return is_over;
	}
	public void setIs_over(String is_over) {
		this.is_over = is_over;
	}
	@OneToMany(targetEntity = ShglSqmyAttrsEntity.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqmy_id", referencedColumnName = "id")
	public List<ShglSqmyAttrsEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<ShglSqmyAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	@Transient
	public String getXSstart_date() {
		return XSstart_date;
	}
	public void setXSstart_date(String xSstart_date) {
		XSstart_date = xSstart_date;
	}
	@Transient
	public String getXSend_date() {
		return XSend_date;
	}
	public void setXSend_date(String xSend_date) {
		XSend_date = xSend_date;
	}
	@Transient
	public String getXScreate_date() {
		return XScreate_date;
	}
	public void setXScreate_date(String xScreate_date) {
		XScreate_date = xScreate_date;
	}
	@OneToMany(targetEntity = ShglSqmyWgy.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sqmy_id", referencedColumnName = "id")
	public List<ShglSqmyWgy> getWgyList() {
		return wgyList;
	}
	public void setWgyList(List<ShglSqmyWgy> wgyList) {
		this.wgyList = wgyList;
	}
	@Formula("(select ct.name from shgl_community_table ct where ct.id = sq_id )")
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
}
