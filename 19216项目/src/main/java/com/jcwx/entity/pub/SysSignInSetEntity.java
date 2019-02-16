package com.jcwx.entity.pub;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Gs
 * 2018年5月30日
 * 每月应签到次数设置
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_sign_in_set_table")
public class SysSignInSetEntity implements Serializable{
	
	private String id;			// 主键
	private String oneMonth;		
	private String twoMonth;	
	private String threeMonth;	
	private String fourMonth;	
	private String fiveMonth;	
	private String sixMonth;	
	private String sevenMonth;	
	private String eightMonth;
	private String nineMonth;
	private String tenMonth;
	private String elevenMonth;
	private String twelveMonth;
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "one_month")
	public String getOneMonth() {
		return oneMonth;
	}
	public void setOneMonth(String oneMonth) {
		this.oneMonth = oneMonth;
	}
	@Column(name = "two_month")
	public String getTwoMonth() {
		return twoMonth;
	}
	public void setTwoMonth(String twoMonth) {
		this.twoMonth = twoMonth;
	}
	@Column(name = "three_month")
	public String getThreeMonth() {
		return threeMonth;
	}
	public void setThreeMonth(String threeMonth) {
		this.threeMonth = threeMonth;
	}
	@Column(name = "four_month")
	public String getFourMonth() {
		return fourMonth;
	}
	public void setFourMonth(String fourMonth) {
		this.fourMonth = fourMonth;
	}
	@Column(name = "five_month")
	public String getFiveMonth() {
		return fiveMonth;
	}
	public void setFiveMonth(String fiveMonth) {
		this.fiveMonth = fiveMonth;
	}
	@Column(name = "six_month")
	public String getSixMonth() {
		return sixMonth;
	}
	public void setSixMonth(String sixMonth) {
		this.sixMonth = sixMonth;
	}
	@Column(name = "seven_month")
	public String getSevenMonth() {
		return sevenMonth;
	}
	public void setSevenMonth(String sevenMonth) {
		this.sevenMonth = sevenMonth;
	}
	@Column(name = "eight_month")
	public String getEightMonth() {
		return eightMonth;
	}
	public void setEightMonth(String eightMonth) {
		this.eightMonth = eightMonth;
	}
	@Column(name = "nine_month")
	public String getNineMonth() {
		return nineMonth;
	}
	public void setNineMonth(String nineMonth) {
		this.nineMonth = nineMonth;
	}
	@Column(name = "ten_month")
	public String getTenMonth() {
		return tenMonth;
	}
	public void setTenMonth(String tenMonth) {
		this.tenMonth = tenMonth;
	}
	@Column(name = "eleven_month")
	public String getElevenMonth() {
		return elevenMonth;
	}
	public void setElevenMonth(String elevenMonth) {
		this.elevenMonth = elevenMonth;
	}
	@Column(name = "twelve_month")
	public String getTwelveMonth() {
		return twelveMonth;
	}
	public void setTwelveMonth(String twelveMonth) {
		this.twelveMonth = twelveMonth;
	}
}
