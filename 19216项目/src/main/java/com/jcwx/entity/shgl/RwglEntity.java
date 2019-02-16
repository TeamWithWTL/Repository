package com.jcwx.entity.shgl;

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

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_task_table")
public class RwglEntity {
	
	private String id;					//ID
	private String title;				//标题	
	private String content;				//内容
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date create_date;			//创建时间
	private String fbr_id;				//发布人ID
	private String fbr_name;			//发布人姓名
	private String sq_id;				//社区ID
	private String fwz_id;				//发布人服务站ID
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finish_date;			//完成时间
	private String finish_dates;		//完成时间格式化
	private String create_dates;		//创建时间格式化
	private List<RwglAttrEntity> attrList;	//附件集合
	private List<RwClEntity> clEntities;		//留言集合
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
		if(create_date != null){
			setCreate_dates(DateUtils.formateDate(create_date,"yyyy-MM-dd"));
		}
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
	public Date getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
		if(finish_date != null){
			setFinish_dates(DateUtils.formateDate(finish_date,"yyyy-MM-dd" ));
		}
	}
	@Transient
	public String getFinish_dates() {
		return finish_dates;
	}
	public void setFinish_dates(String finish_dates) {
		this.finish_dates = finish_dates;
		
	}
	@Transient
	public String getCreate_dates() {
		return create_dates;
	}
	public void setCreate_dates(String create_dates) {
		this.create_dates = create_dates;
	}
	
	

	@OneToMany(targetEntity = RwglAttrEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", referencedColumnName = "id")
	public List<RwglAttrEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<RwglAttrEntity> attrList) {
		this.attrList = attrList;
	}
	
	@OneToMany(targetEntity = RwClEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", referencedColumnName = "id")
	public List<RwClEntity> getClEntities() {
		return clEntities;
	}
	public void setClEntities(List<RwClEntity> clEntities) {
		this.clEntities = clEntities;
	}

}
