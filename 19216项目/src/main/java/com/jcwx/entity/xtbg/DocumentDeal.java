package com.jcwx.entity.xtbg;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.entity.shgl.Event;


@SuppressWarnings("serial")
@Entity
@Table(name = "xtbg_gwcl_deal_table")
public class DocumentDeal implements Serializable{

	private String id;				//主键
	private String zfrId;		//转发人ID
	private String zfrName;		//转发人姓名
	private String clrId;		//处理人ID
	private String clrName;		//处理人姓名
	private String status;		//处理状态(0待处理，1已处理)
	private String content;//内容
	private Date dealDate;//处理时间
	private Date createDate;//创建时间
	
	private Document document;

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name="zfr_id")
	public String getZfrId() {
		return zfrId;
	}
	public void setZfrId(String zfrId) {
		this.zfrId = zfrId;
	}
	@Column(name="zfr_name")
	public String getZfrName() {
		return zfrName;
	}
	public void setZfrName(String zfrName) {
		this.zfrName = zfrName;
	}
	@Column(name="clr_id")
	public String getClrId() {
		return clrId;
	}
	public void setClrId(String clrId) {
		this.clrId = clrId;
	}
	@Column(name="clr_name")
	public String getClrName() {
		return clrName;
	}
	public void setClrName(String clrName) {
		this.clrName = clrName;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="deal_date")
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@ManyToOne(targetEntity = Document.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "gw_id")
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
}
