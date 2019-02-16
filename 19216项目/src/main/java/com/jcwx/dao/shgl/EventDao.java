package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.utils.Pagenate;

public interface EventDao extends BaseDao {
	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<Event> findEventByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	Pagenate<Event> appfindEventByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	Pagenate<EventDeal> findEventDealByPage(Integer pageNumber, int pagesize,Map<String, String> map);
	

	/**
	 * 根据社区Id查询事件数量
	 * @param addMap
	 * @return
	 */
	int findCountById(Map<String, String> addMap);

	/**
	 * 查询我的未处理事件
	 * @param map
	 * @return
	 */
	List<EventDeal> findMyEventNo(Map<String, String> map);

	/**
	 * 查询事件列表
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	Pagenate<Event> getSjContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);
	
	/**
	 * 查询事件处理列表
	 * @param map
	 * @return
	 */
	public List<EventDeal> findEventDealList(Map<String, String> map);

	/**
	 * 我得待办数量
	 * @param map
	 * @return
	 */
	int myEventCount(Map<String, String> map);

	/**
	 * 查询所有事件
	 * @param map
	 * @return
	 */
	public List<Event> findAllEvents(Map<String, String> map);
	
	/**
	 * 查询最近的事件(默认3个月)
	 * @param map
	 * @return
	 */
	public List<Event> findRecentEvents(Map<String, String> map);
}
