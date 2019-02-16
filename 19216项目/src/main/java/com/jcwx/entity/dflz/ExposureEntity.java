package com.jcwx.entity.dflz;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;

/**
 * 曝光台实体类
 * @author 李伟
 * @time 2017年10月21日下午4:46:18
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "dflz_bgt_table")
public class ExposureEntity implements Serializable{
	  private String id;   //曝光台id
	  private String title; //曝光台 新闻标题
	  private String content; //曝光台 新闻内容 
	  private Date create_time; //曝光台 曝光时间
	  private String fmtCreate_time;//时间格式化
	  private String user_id;	//曝光台  发布人编码
      private String user_name; //曝光台  发布人名称
      private List<ExpAcceEntity> expList; //附件集合一对多
      private String sh_status; //审核状态
      private String sh_user_id;//审核人id
	  private String sh_user_name;//审核人name
	  
	  private String ysPice;//压缩图片
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
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	/**
	 * 格式化时间
	 * @author 李伟
	 * @time 2017年11月2日下午4:46:35
	 * @param create_time
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
		if (create_time != null) {
			setFmtCreate_time(DateUtils.formateDate(create_time, "yyyy-MM-dd HH:mm"));
		}
	}
	
	@Transient
	public String getFmtCreate_time() {
		return fmtCreate_time;
	}
	public void setFmtCreate_time(String fmtCreate_time) {
		this.fmtCreate_time = fmtCreate_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	@OneToMany(targetEntity =ExpAcceEntity.class ,cascade = {CascadeType.REMOVE})
	@JoinColumn(name="new_id",referencedColumnName = "id")
	public List<ExpAcceEntity> getExpList() {
		return expList;
	}
	public void setExpList(List<ExpAcceEntity> expList) {
		this.expList = expList;
	}
	public String getSh_status() {
		return sh_status;
	}
	public void setSh_status(String sh_status) {
		this.sh_status = sh_status;
	}
	public String getSh_user_id() {
		return sh_user_id;
	}
	public void setSh_user_id(String sh_user_id) {
		this.sh_user_id = sh_user_id;
	}
	public String getSh_user_name() {
		return sh_user_name;
	}
	public void setSh_user_name(String sh_user_name) {
		this.sh_user_name = sh_user_name;
	}
	@Override
	public String toString() {
		return "ExposureEntity [id=" + id + ", title=" + title + ", content=" + content + ", create_time=" + create_time
				+ ", user_id=" + user_id + ", user_name=" + user_name + ", expList=" + expList + ", sh_status="
				+ sh_status + ", sh_user_id=" + sh_user_id + ", sh_user_name=" + sh_user_name + "]";
	}
	@Transient
	public String getYsPice() {
		return ysPice;
	}
	public void setYsPice(String ysPice) {
		this.ysPice = ysPice;
	}
} 
