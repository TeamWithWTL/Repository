/**
 * 
 */
package com.jcwx.entity.xtbg;

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

import com.jcwx.entity.shzz.DtbbAttrEntity;
import com.jcwx.utils.DateUtils;

/**
 * @author m
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="xtbg_tzgg_table")
public class TzggEntity {
	
	private String id;							//ID
	private String title;						//标题
	private String content;						//内容
	private Date createDate;					//创建时间
	private String createUserId;				//下发人ID
	private String createUserName;				//下发人姓名
	private String status;						//下发状态
	private List<TzggArrtEntity> attrList;	//附件集合
	private String createDates;
/*	private List<TzggRyEntity> ryList;	//		建议集合
*/	
	private String ysPice;                       //图片
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")	
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	@Column(name = "create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if (createDate != null) {
			setCreateDates(DateUtils.formateDate(createDate, "yyyy-MM-dd"));
		}
	}
	@Transient
	public String getCreateDates() {
		return createDates;
	}
	public void setCreateDates(String createDates) {
		this.createDates = createDates;
	}
	@Column(name = "xf_status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	@OneToMany(targetEntity = TzggArrtEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "ry_id", referencedColumnName = "id")
	public List<TzggArrtEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<TzggArrtEntity> attrList) {
		this.attrList = attrList;
	}
	
	
	
	
	/*@OneToMany(targetEntity = TzggArrtEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "tzgg_id", referencedColumnName = "id")
	public List<TzggRyEntity> getRyList() {
		return ryList;
	}
	public void setRyList(List<TzggRyEntity> ryList) {
		this.ryList = ryList;
	}*/
	
	@Transient
	public String getYsPice() {
		return ysPice;
	}
	public void setYsPice(String ysPice) {
		this.ysPice = ysPice;
	}
}
