package com.jcwx.dao.pub;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author Gs
 * 2018年5月17日
 * 签到
 */
public interface SysSignInDao extends BaseDao{

	/**
	 * 判断今日上午是否签到
	 * @param addCode
	 * @param startTime
	 * @param lunchTime
	 * @param flag 
	 * @return
	 */
	SysSignInEntity findContent(String addCode, Date startTime,
			Date lunchTime, String flag);

	/**
	 * 获取签到记录
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<SysSignInEntity> getSignInList(Integer pageNumber, int pagesize,
			Map<String, String> map);

	/**
	 * 本月实际签到次数
	 * @param accCode
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	List<SysSignInEntity> getCouunt(String accCode, Date sTime, Date eTime);

}
