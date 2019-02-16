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
 * 网格管理
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_grid_table")
public class ShglGridEntity implements Serializable {
	private String id;// id
	private ShglServiceStationEntity serviceStation;// 所属服务站
	private String name;// 名称
	private String area;// 范围
	private String add_code;// 添加人编号
	private String add_name;// 添加人姓名
	private Date add_time;// 添加时间
	private String area_color;// 区域颜色
	private String line_color;// 边框颜色
	private String description; //描述
	private List<ShglGridManagerEntity> gridList;//负责人

	private String addTimeFrm;// 添加时间格式化
	private String sjCount;// 实际入住户数
	
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = StringUtil.filterchart(area);
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

	public String getArea_color() {
		return area_color;
	}

	public String getLine_color() {
		return line_color;
	}

	public void setArea_color(String area_color) {
		this.area_color = StringUtil.filterchart(area_color);
	}

	public void setLine_color(String line_color) {
		this.line_color = StringUtil.filterchart(line_color);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtil.filterchart(description);
	}
	@OneToMany(targetEntity = ShglGridManagerEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "grid_id")
	public List<ShglGridManagerEntity> getGridList() {
		return gridList;
	}

	public void setGridList(List<ShglGridManagerEntity> gridList) {
		this.gridList = gridList;
	}
	@Transient
	public String getSjCount() {
		return sjCount;
	}

	public void setSjCount(String sjCount) {
		this.sjCount = sjCount;
	}
}
