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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * 楼宇管理
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_building_table")
public class ShglBuildingEntity implements Serializable {
	private String id;// id
	private ShglVillageEntity village;// 小区编号
	private ShglGridEntity grid;// 网格编号
	private String commId;// 社区id
	private String name;// 楼宇名称
	private String unit_cnt;// 单元数
	private String floor_cnt;// 楼层数
	private String family_cnt;// 每层户数
	private String building_type;// 楼宇类型 0.住宅 1.商铺
	private String lon;// 经度
	private String lat;// 纬度
	private String add_code;
	private String add_name;
	private Date add_time;

	private String addTimeFrm;
	private String lylx;
	//private List<ShglInmateEntity> jmList;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = ShglVillageEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "village_id", referencedColumnName = "id")
	public ShglVillageEntity getVillage() {
		return village;
	}

	public void setVillage(ShglVillageEntity village) {
		this.village = village;
	}

	@ManyToOne(targetEntity = ShglGridEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "grid_id", referencedColumnName = "id")
	public ShglGridEntity getGrid() {
		return grid;
	}

	public void setGrid(ShglGridEntity grid) {
		this.grid = grid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit_cnt() {
		return unit_cnt;
	}

	public void setUnit_cnt(String unit_cnt) {
		this.unit_cnt = StringUtil.filterchart(unit_cnt);
	}

	public String getFloor_cnt() {
		return floor_cnt;
	}

	public void setFloor_cnt(String floor_cnt) {
		this.floor_cnt = StringUtil.filterchart(floor_cnt);
	}

	public String getFamily_cnt() {
		return family_cnt;
	}

	public void setFamily_cnt(String family_cnt) {
		this.family_cnt = StringUtil.filterchart(family_cnt);
	}

	public String getBuilding_type() {
		return building_type;
	}

	public void setBuilding_type(String building_type) {
		this.building_type = StringUtil.filterchart(building_type);
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = StringUtil.filterchart(lon);
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = StringUtil.filterchart(lat);
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
		this.add_name = StringUtil.filterchart(add_name);
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

	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	@Formula("( select s.item_name from sys_param_desc_table s where s.item_code = building_type and s.code = '10006' )")
	public String getLylx() {
		return lylx;
	}

	public void setLylx(String lylx) {
		this.lylx = lylx;
	}
	
	/*@OneToMany(targetEntity = ShglInmateEntity.class, cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "building", referencedColumnName = "id")
	public List<ShglInmateEntity> getJmList() {
		return jmList;
	}

	public void setJmList(List<ShglInmateEntity> jmList) {
		this.jmList = jmList;
	}*/
	
}
