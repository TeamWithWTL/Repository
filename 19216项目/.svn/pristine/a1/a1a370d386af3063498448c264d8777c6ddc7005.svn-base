package com.jcwx.dao.shfw;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区活动 Dao 
 * @author zhangkai
 *
 */
public interface SqhdDao extends BaseDao {
	
	/**
	 * 分页查询社区活动
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 查询是否有热点社区活动
	 * @param isHot 是否热点
	 * @return
	 */
	ShfwSqhdEntity getByIsHot(String isHot);
	
	/**
	 * 获取所有的社区活动
	 * @return
	 */
	List<ShfwSqhdEntity> getHdList();
	
	/**
	 * 获取所有的社区
	 * @return
	 */
	List<ShglCommunityEntity> getCommList();
	
}
