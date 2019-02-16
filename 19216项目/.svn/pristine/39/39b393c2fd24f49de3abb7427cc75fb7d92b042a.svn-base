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

import org.hibernate.annotations.GenericGenerator;

import com.jcwx.utils.DateUtils;
import com.jcwx.utils.StringUtil;

/**
 * 居民信息
 * 
 * @author 唐寿强
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "shgl_inmate_table")
public class ShglInmateEntity implements Serializable {
	private String id;
	private ShglBuildingEntity build;// 楼宇
	private String commId;// 社区id
	private String ssId;// 服务站id
	private String unit_no;// 单元号
	private String room_no;// 房间号
	private String name;// 姓名
	private String sex;// 性别 E.男;F.女
	private Date birthday;// 出生日期

	private String birthdayFrm;// 出日期格式化

	private String card_no;// 身份证号
	private String telephone;// 联系电话
	private String political;// 政治面貌
	private String nation;// 民族
	private String housemaster;// 是否户主
	private String hm_relation;// 与户主关系
	private String house_register;// 户籍所在地
	private String marriage;// 婚姻状况
	private String education;// 文化程度
	private String work_unit;// 工作单位
	private String car_no;// 车牌号
	private String hzid;
	private List<ShglInmateTypeEntity> inmateTList;//人员分类
	private String hzgx; //户主关系
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	@ManyToOne(targetEntity = ShglBuildingEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "building", referencedColumnName = "id")
	public ShglBuildingEntity getBuild() {
		return build;
	}

	public String getUnit_no() {
		return unit_no;
	}

	public String getRoom_no() {
		return room_no;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getCard_no() {
		return card_no;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getPolitical() {
		return political;
	}

	public String getNation() {
		return nation;
	}

	public String getHousemaster() {
		return housemaster;
	}

	public String getHm_relation() {
		return hm_relation;
	}

	public String getHouse_register() {
		return house_register;
	}

	public String getMarriage() {
		return marriage;
	}

	public String getEducation() {
		return education;
	}

	public String getWork_unit() {
		return work_unit;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBuild(ShglBuildingEntity build) {
		this.build = build;
	}

	public void setUnit_no(String unit_no) {
		this.unit_no = StringUtil.filterchart(unit_no);
	}

	public void setRoom_no(String room_no) {
		this.room_no = StringUtil.filterchart(room_no);
	}

	public void setName(String name) {
		this.name = StringUtil.filterchart(name);
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		if (birthday != null) {
			setBirthdayFrm(DateUtils.formateDate(birthday, "yyyy-MM-dd"));
		}
	}

	public void setCard_no(String card_no) {
		this.card_no = StringUtil.filterchart(card_no);
	}

	public void setTelephone(String telephone) {
		this.telephone = StringUtil.filterchart(telephone);
	}

	public void setPolitical(String political) {
		this.political = StringUtil.filterchart(political);
	}

	public void setNation(String nation) {
		this.nation = StringUtil.filterchart(nation);
	}

	public void setHousemaster(String housemaster) {
		this.housemaster = StringUtil.filterchart(housemaster);
	}

	public void setHm_relation(String hm_relation) {
		this.hm_relation = StringUtil.filterchart(hm_relation);
	}

	public void setHouse_register(String house_register) {
		this.house_register = StringUtil.filterchart(house_register);
	}

	public void setMarriage(String marriage) {
		this.marriage = StringUtil.filterchart(marriage);
	}

	public void setEducation(String education) {
		this.education = StringUtil.filterchart(education);
	}

	public void setWork_unit(String work_unit) {
		this.work_unit = StringUtil.filterchart(work_unit);
	}

	public void setCar_no(String car_no) {
		this.car_no = StringUtil.filterchart(car_no);
	}

	@Transient
	public String getBirthdayFrm() {
		return birthdayFrm;
	}

	public void setBirthdayFrm(String birthdayFrm) {
		this.birthdayFrm = birthdayFrm;
	}

	@Column(name = "comm_id")
	public String getCommId() {
		return commId;
	}

	@Column(name = "ss_id")
	public String getSsId() {
		return ssId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	
	@OneToMany(targetEntity = ShglInmateTypeEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "inmate_id", referencedColumnName = "id")
	public List<ShglInmateTypeEntity> getInmateTList() {
		return inmateTList;
	}

	public void setInmateTList(List<ShglInmateTypeEntity> inmateTList) {
		this.inmateTList = inmateTList;
	}

	public String getHzid() {
		return hzid;
	}

	public void setHzid(String hzid) {
		this.hzid = hzid;
	}
	@Transient
	public String getHzgx() {
		return hzgx;
	}

	public void setHzgx(String hzgx) {
		this.hzgx = hzgx;
	}
	
	
}
