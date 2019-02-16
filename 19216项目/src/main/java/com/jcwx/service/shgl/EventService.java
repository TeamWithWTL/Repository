package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventAttrs;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.utils.Pagenate;

public interface EventService {
	/**
	 * 保存
	 * 
	 * @param id
	 */
	public void saveorupdate(Object entity);
	
	public void upDateEvent(Event event);
	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<Event> findEventByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	
	Pagenate<EventDeal> findEventDealByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据id查询事件
	 * 
	 * @param id
	 * @return
	 */
	public Event findEventById(String id);


	EventDeal findEventDealById(String id);
	
	
	public void upDateEventDeal(EventDeal eventDeal);


	/**
	 * 根据社区Id查询事件数量
	 * @return
	 */
	int findEventCntById(Map<String, String> addMap);


	/**
	 * 查询我的未处理事件
	 * @param map
	 * @return
	 */
	List<EventDeal> findMyEventNo(Map<String, String> map);
	
	/**
	 * 保存更新附件 
	 * @param eventEntity
	 */
	public void saveOrUpdateAttrs(EventAttrs eventEntity);
	
	/**
	 * 查询事件列表
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	public Pagenate<Event> getSjContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);
	
	/**
	 * 查询事件处理列表
	 * @param map
	 * @return
	 */
	public List<EventDeal> findEventDealList(Map<String, String> map);
	
	/**
	 * 查询我的代办数量
	 * @param map
	 * @return
	 */
	public int myEventCount(Map<String, String> map);
	
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
	public Pagenate<Event> appfindEventByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 批量删除事件
	 * @param ids
	 */
	public void delete(String ids);
}
