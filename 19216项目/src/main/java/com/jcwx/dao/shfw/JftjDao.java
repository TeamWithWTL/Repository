package com.jcwx.dao.shfw;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-积分统计 Dao 
 * @author zhangkai
 *
 */
public interface JftjDao extends BaseDao {
	
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
	
}
