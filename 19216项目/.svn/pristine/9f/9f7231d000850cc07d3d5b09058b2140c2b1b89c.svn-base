package com.jcwx.service.dflz;

import java.util.Map;

import com.jcwx.entity.dflz.CompAcceEntity;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.utils.Pagenate;

/**
 * app投诉举报service接口
 * @author 李伟
 * @time 2017年11月9日上午8:20:13
 */
public interface AppComplainService {
	/**
	 * app查询投诉举报
	 * @author 李伟
	 * @time 2017年11月9日上午8:40:13
	 * @param pageNum
	 * @param pagesize
	 * @param hashMap
	 * @return
	 */
	Pagenate<ComplainEntity> findByPage(Integer pageNum, int pagesize, Map<String, String> hashMap);
	/**
	 * app添加投诉举报
	 * @author 李伟
	 * @time 2017年11月9日下午1:22:23
	 * @param sysacc
	 * @param complainEntity
	 * @param sjlx 
	 */
	void save(SysAccCount sysacc, ComplainEntity complainEntity, String sjlx);
	/**
	 * app添加修改投诉附件
	 * @author 李伟
	 * @time 2017年11月18日下午1:48:04
	 * @param compAcceEntity
	 */
	void saveOrUpdateCompArrt(CompAcceEntity compAcceEntity);
	
}
