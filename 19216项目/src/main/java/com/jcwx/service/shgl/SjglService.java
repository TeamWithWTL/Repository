package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.EventFlowRecordEntity;
import com.jcwx.entity.shgl.EventStatusEntity;
import com.jcwx.utils.Pagenate;

public interface SjglService {
	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<EventEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public EventEntity findById(String id);

	/**
	 * 根据本条件查询数据
	 * 
	 * @param map
	 * @return
	 */
	List<EventEntity> findAllEvents(Map<String, String> map);

	/**
	 * 保存
	 * 
	 * @param build
	 */
	public void save(EventEntity eventEntity);

	/**
	 * 编辑 保存
	 * 
	 * @param buildOld
	 */
	public void update(EventEntity eventEntity);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 保存流转记录
	 * 
	 * @param eventflow
	 */
	public void saveFlow(EventFlowRecordEntity eventflow);

	/**
	 * 保存流转状态
	 * 
	 * @param eventStatus
	 */
	public void saveSta(EventStatusEntity eventStatus);

	/**
	 * 根据条件查询流转记录
	 * 
	 * @param map
	 * @return
	 */
	public EventFlowRecordEntity findByPara(Map<String, String> map);

	/**
	 * 更新状态流转记录
	 * 
	 * @param eventflowOld
	 */
	public void updateFlow(EventFlowRecordEntity eventflowOld);
}
