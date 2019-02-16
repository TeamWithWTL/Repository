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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_sqmy_dc_table")
public class ShglSqmyDc implements Serializable{

	private String id;
	private String sqmyWgyId;
	private String title;
	private String content;
	private Date createDate;

	private String createDateFom;
	
	private List<ShglSqmyDcAttrs>   attrList;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="sqmy_wgy_id")
	public String getSqmyWgyId() {
		return sqmyWgyId;
	}

	public void setSqmyWgyId(String sqmyWgyId) {
		this.sqmyWgyId = sqmyWgyId;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if(null != createDate){
			setCreateDateFom(DateUtils.formateDate(createDate, "yyyy-MM-dd HH:mm"));
		}
	} 
	@OneToMany(targetEntity = ShglSqmyDcAttrs.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dc_id", referencedColumnName = "id")
	public List<ShglSqmyDcAttrs> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ShglSqmyDcAttrs> attrList) {
		this.attrList = attrList;
	}

	@Transient
	public String getCreateDateFom() {
		return createDateFom;
	}

	public void setCreateDateFom(String createDateFom) {
		this.createDateFom = createDateFom;
		
	}


	}
