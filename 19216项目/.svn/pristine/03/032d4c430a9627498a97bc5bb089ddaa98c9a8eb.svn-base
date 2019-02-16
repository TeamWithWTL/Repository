package com.jcwx.dao.shfw;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shfw.ShfwSqhdYjAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.utils.Pagenate;
/**
 * 
 * 社会服务-社区活动意见留言 Dao 
 * @author zhangkai
 *
 */
public interface YjlyDao extends BaseDao {
	
	/**
	 * 分页查询审核通过社区活动意见留言
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdYjEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 分页查询待审核社区活动意见留言
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdYjEntity> findDshLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 分页查询审核不通过社区活动意见留言
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdYjEntity> findBtgLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 分页查询未评分社区活动意见留言
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdYjEntity> findWpfLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据社区活动ID和留言人ID获取已反馈的留言记录
	 * @param sqhdId
	 * @param userId
	 * @return
	 */
	ShfwSqhdYjEntity getSqhdYjBySqhdId(String sqhdId, String userId);
	
	/**
	 * 根据社区活动ID和留言人ID获取未反馈的留言记录
	 * @param sqhdId
	 * @param userId
	 * @return
	 */
	ShfwSqhdYjEntity getSqhdYjByWfk(String sqhdId, String userId);
	
	/**
	 * 根据社区活动ID和留言人ID获取已反馈的留言记录
	 * @param sqhdId
	 * @param userId
	 * @return
	 */
	ShfwSqhdYjEntity getSqhdYjByYfk(String sqhdId, String userId);
	
	/**
	 * 根据社区活动ID获取报名记录
	 * @param sqhdId
	 * @param userId
	 * @return
	 */
	Pagenate<ShfwSqhdYjEntity> getSqhdBmjlByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 保存更新留言附件
	 * @param attrs
	 */
	void saveOrUpdateAttrs(ShfwSqhdYjAttrsEntity attrs);
	
}
