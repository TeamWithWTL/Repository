package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.EventFlowRecordEntity;
import com.jcwx.utils.Pagenate;

public interface SjglDao extends BaseDao {
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
	 * 根据条件查询
	 * 
	 * @param map
	 * @return
	 */
	List<EventEntity> findAllEvents(Map<String, String> map);

	/**
	 * 根据条件查询流转记录
	 * 
	 * @param map
	 * @return
	 */
	public EventFlowRecordEntity findByPara(Map<String, String> map);

}
