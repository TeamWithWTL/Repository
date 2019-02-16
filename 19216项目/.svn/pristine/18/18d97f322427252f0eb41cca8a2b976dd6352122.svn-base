package com.jcwx.entity.shgl;

import java.io.Serializable;
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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * 小区信息
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_village_table")
public class ShglVillageEntity implements Serializable {
	private String id;// id
	private ShglServiceStationEntity serviceStation;// 服务站编号
	private String name;// 小区名称
	private String add_code;
	private String add_name;
	private Date add_time;
	private String description;//描述
	private List<ShglVmanagerEntity> vmanList;//负责人
	
	private String addTimeFrm;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = ShglServiceStationEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ss_id", referencedColumnName = "id")
	public ShglServiceStationEntity getServiceStation() {
		return serviceStation;
	}

	public void setServiceStation(ShglServiceStationEntity serviceStation) {
		this.serviceStation = serviceStation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
	}

	public String getAdd_code() {
		return add_code;
	}

	public void setAdd_code(String add_code) {
		this.add_code = StringUtil.filterchart(add_code);
	}

	public String getAdd_name() {
		return add_name;
	}

	public void setAdd_name(String add_name) {
		this.add_name = add_name;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
		if (add_time != null) {
			setAddTimeFrm(DateUtils.formateDate(add_time, "yyyy-MM-dd"));
		}
	}

	@Transient
	public String getAddTimeFrm() {
		return addTimeFrm;
	}

	public void setAddTimeFrm(String addTimeFrm) {
		this.addTimeFrm = addTimeFrm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtil.filterchart(description);
	}
	@OneToMany(targetEntity  = ShglVmanagerEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "vill_id")
	public List<ShglVmanagerEntity> getVmanList() {
		return vmanList;
	}

	public void setVmanList(List<ShglVmanagerEntity> vmanList) {
		this.vmanList = vmanList;
	}
	
	
}
