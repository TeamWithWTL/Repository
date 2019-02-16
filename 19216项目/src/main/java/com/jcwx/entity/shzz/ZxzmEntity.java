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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author xushu
 *	2017年10月24日
 *	在线招募
 */
@SuppressWarnings("serial")
@Entity
@Table(name="shzz_zxzm_table")
public class ZxzmEntity implements Serializable{
	
	private String id;               //主键
	private String title;            //标题
	private String content;          //内容
	private Date   create_time;      //创建时间 
    private String user_id;          //发布人id
    private String user_name;        //发布人姓名
    private String sh_status;        //审核状态0:待审核，1通过，2，未通过
    private String sh_user_id;       //审核人id
    private String sh_user_name;     //审核人姓名
    
	private String xs_create_time;   //格式化创建时间
	private List<ZxzmAttrsEntity>   attrList; //附件
	
	private String ysPice;//压缩图片
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
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
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
		if(null != create_time){
			setXs_create_time(DateUtils.formateDate(create_time, "yyyy-MM-dd HH:mm"));
		}
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
	@OneToMany(targetEntity = ZxzmAttrsEntity.class ,cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "zxjs_id", referencedColumnName = "id")
	public List<ZxzmAttrsEntity> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<ZxzmAttrsEntity> attrList) {
		this.attrList = attrList;
	}
	
	@Transient
	public String getXs_create_time() {
		return xs_create_time;
	}
	public void setXs_create_time(String xs_create_time) {
		this.xs_create_time = xs_create_time;
	}
	@Transient
	public String getYsPice() {
		return ysPice;
	}
	public void setYsPice(String ysPice) {
		this.ysPice = ysPice;
	}
}
