package com.jcwx.service.pub;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.entity.pub.SysSignInSetEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author Gs
 * 2018年5月17日
 * 签到
 */
public interface SysSignInService {

	/**
	 * 判断今日上午是否签到
	 * @param string
	 * @param startTime
	 * @param lunchTime
	 * @param flag 
	 * @return
	 */
	SysSignInEntity findContent(String string, Date startTime,
			Date lunchTime, String flag);

	/**
	 * 保存签到信息
	 * @param signInEntity
	 */
	void saveContent(SysSignInEntity signInEntity);

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
	 * 本月时间签到次数
	 * @param accCode
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	List<SysSignInEntity> getCouunt(String accCode, Date sTime, Date eTime);

	/**
	 * 下午签到
	 * @param entityAm
	 */
	void updataPm(SysSignInEntity entityAm);

	/**
	 * 查询所有的设置每月签到次数记录
	 * @return
	 */
	List<SysSignInSetEntity> findAllSignMonth();

	/**
	 * 保存每月应签到次数
	 * @param entity
	 * @param id
	 */
	void saveSetMonth(SysSignInSetEntity entity, String id);

}
