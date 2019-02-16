package com.jcwx.service.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会组织-活动统计 Service
 * @author zhangkai
 *
 */
public interface HdtjService {
	
	/**
	 * 分页查询已结束的组织活动
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<HdglEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据活动ID获取对应活动的反馈信息
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<HdglFkEntity> findFkByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 分页查询所有组织
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ZzxxEntity> findZzByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据组织ID获取对应活动的反馈信息
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<HdglFkEntity> findFk2ByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据组织ID获取某个组织参与活动数量
	 * @param paramsMap
	 * @return
	 */
	List<HdglFkEntity> getFkCnt(Map<String, String> paramsMap);
	
}
