package com.jcwx.dao.shfw;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shfw.ShfwSqfwEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区服务 Dao 
 * @author zhangkai
 *
 */
public interface SqfwDao extends BaseDao {
	
	/**
	 * 分页查询社区服务
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqfwEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 查询是否有热点社区服务
	 * @param isHot 是否热点
	 * @return
	 */
	ShfwSqfwEntity getByIsHot(String isHot);
	
}
