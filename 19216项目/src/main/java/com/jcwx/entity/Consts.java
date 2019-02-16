package com.jcwx.entity;


/**
 * 系统常量
 * @author jiangkai
 */
public class Consts {
	
	
	// ------------------------------------- 行政区划级别 -------------------------------------
	/**
	 * 行政区划级别-省级
	 */
	public static String STRATIVE_LEVEL_PROV = "1";
	/**
	 * 行政区划级别-市级
	 */
	public static String STRATIVE_LEVEL_CITY = "2";
	/**
	 * 行政区划级别-区县级
	 */
	public static String STRATIVE_LEVEL_COUNTRY = "3";
	/**
	 * 行政区划级别-乡镇、街道级
	 */
	public static String STRATIVE_LEVEL_TOWN = "4";
	/**
	 * 行政区划级别-其他级别
	 */
	public static String STRATIVE_LEVEL_OTHER = "5";
	
	// ------------------------------------- 性别 -------------------------------------
	/**
	 * 性别-男
	 */
	public static String SEX_MAN = "1";
	/**
	 * 性别-女
	 */
	public static String SEX_WOMAN = "0";
	
	// ------------------------------------- 角色ID -------------------------------------
	/**
	 * 网格员
	 */
	public static String ROLE_WGY = "01";
	/**
	 *  服务站管理员
	 */
	public static String ROLE_FWZGLY = "02";
	/**
	 * 社区管理员
	 */
	public static String ROLE_SQGLY = "03";
	/**
	 * 街道信息员
	 */
	public static String ROLE_JDXXY = "04";
	/**
	 * 街道管理员
	 */
	public static String ROLE_JDGLY = "05";
	/**
	 * 党政办工作人员
	 */
	public static String ROLE_DZBGZRY = "06";
	/**
	 * 业务部门领导
	 */
	public static String ROLE_YWBMLD = "07";
	/**
	 * 业务部门工作人员
	 */
	public static String ROLE_YWBM = "08";
	/**
	 * 社会组织管理员
	 */
	public static String ROLE_SHZZGLY = "09";
	/**
	 * 街道办党风廉政管理人员
	 */
	public static String ROLE_JDBDFLZGLRY = "10";
	/**
	 * 街道办领导
	 */
	public static String ROLE_JDBLD = "12";
	/**
	 * 部门成员
	 */
	public static String ROLE_BMCY = "13";
	/**
	 * 普通用户
	 */
	public static String ROLE_PTYH = "14";
	/**
	 * 最高权限账号
	 */
	public static String ROLE_ADMIN = "99";
	// ------------------------------------- 事件处理状态-------------------------------------
	/**
	 * 事件处理状态-未处理
	 */
	public static String EVENT_STATUS_UNDO = "0";
	/**
	 * 事件处理状态-上报
	 */
	public static String EVENT_STATUS_REPORT = "1";
	/**
	 * 事件处理状态-处理
	 */
	public static String EVENT_STATUS_DEAL = "2";
	/**
	 * 事件处理状态-信息员处理
	 */
	public static String EVENT_STATUS_XXYDEAL = "3";
	/**
	 * 事件处理状态-信息员处理完成
	 */
	public static String EVENT_STATUS_XXYDONE= "4";
	/**
	 * 事件处理状态-社区管理员下发服务站
	 */
	public static String EVENT_STATUS_TOSERVICE= "5";
	// ------------------------------------- 事件类型-------------------------------------
	/**
	 * 事件类型
	 */
	public static String EVENT_TYPE= "10008";
	
	// ------------------------------------- 事件来源 -------------------------------------
	
	/**
	 * 事件来源 - 类型
	 */
	public static String EVENT_FROM_TYPE= "10023";

	/**
	 * 事件来源 - 管理端app
	 */
	public static String EVENT_FROM_MANAGEAPP = "1";
	
	/**
	 * 事件来源 - 公共app
	 */
	public static String EVENT_FROM_COMMONAPP = "2";
	
	/**
	 * 事件来源 - 电话
	 */
	public static String EVENT_FROM_PHONE = "3";
	
	/**
	 * 事件来源 - 来信来访
	 */
	public static String EVENT_FROM_PETITION = "4";
	
	/**
	 * 事件来源 - 其他
	 */
	public static String EVENT_FROM_OTHER = "5";
	
	// ------------------------------------- 特殊人口类别-------------------------------------
	/**
	 * 特殊人口类别
	 */
	public static String PEOPLE_TYPE_TSRK = "10005";
	// ------------------------------------- 公文类别 -------------------------------------
	
	/**
	 *公文类别  发文
	 */
	public static String DOC_TYPE_FW = "1";
	
	/**
	 * 公文类别  收文
	 */
	public static String DOC_TYPE_SW = "2";
	
	// ------------------------------------- 账号类型 -------------------------------------
	
	/**
	 *用户类型_系统用户
	 */
	public static String USER_TYPE_SYS = "1";
	
	/**
	 * 用户类型_居民用户
	 */
	public static String USER_TYPE_JM= "2";
	/**
	 * 用户类型_法人用户
	 */
	public static String USER_TYPE_FR = "3";
	/**
	 * 用户类型_部门用户
	 */
	public static String USER_TYPE_DEPT = "4";
}
