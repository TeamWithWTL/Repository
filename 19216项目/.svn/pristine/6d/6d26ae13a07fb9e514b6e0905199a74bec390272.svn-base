package com.jcwx.service.shfw;

import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区活动意见留言 Service
 * @author zhangkai 
 *
 */
public interface YjlyService {
	
	/**
	 * 分页查询社区活动意见留言
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
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ShfwSqhdYjEntity findById(String id);
	
	/**
	 * 更新意见留言表中的分数
	 * @param id
	 * @param jf
	 */
	void updateScore(String id, Integer jf);
	
	/**
	 * 添加积分
	 * @param jf
	 */
	void save(ShfwJflsEntity jf);

	/**
	 * 保存留言信息
	 * @param sqhdYjEntity
	 */
	void saveLy(ShfwSqhdYjEntity sqhdYjEntity);
	
	/**
	 * 更新留言信息
	 * @param sqhdYjEntity
	 * @param id
	 */
	void updateLy(ShfwSqhdYjEntity sqhdYjEntity, String id);
	
	/**
	 * id查询留言用户信息
	 * @author 李伟
	 * @time 2017年12月6日上午5:42:17
	 * @param userId
	 * @return
	 */
	SysAccCount findLyUserById(String userId);
	
	/**
	 * 根据社区活动ID和留言人ID获取留言记录
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
