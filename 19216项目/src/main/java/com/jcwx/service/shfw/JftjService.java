package com.jcwx.service.shfw;

import java.util.Map;

import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-积分统计 Service 
 * @author zhangkai
 *
 */
public interface JftjService {
	
	/**
	 * 分页查询每个用户总积分
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<SysAccCountLazy> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 分页查询每个用户积分流水
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwJflsEntity> getByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 添加积分兑换分数
	 * @param jf
	 */
	void save(ShfwJflsEntity jf);
	
}
