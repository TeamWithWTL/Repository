package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.EventDao;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventAttrs;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.service.shgl.EventService;
import com.jcwx.utils.Pagenate;

@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;
	
	@Override
	public Pagenate<Event> findEventByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		return eventDao.findEventByPage(pageNumber, pagesize, map);
	}
	@Override
	public Pagenate<Event> appfindEventByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		return eventDao.appfindEventByPage(pageNumber, pagesize, map);
	}
	
	@Override
	public Event findEventById(String id) {
		return eventDao.findById(Event.class, id);
	}

	@Override
	public EventDeal findEventDealById(String id) {
		return eventDao.findById(EventDeal.class, id);
	}

	@Override
	public Pagenate<EventDeal> findEventDealByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return eventDao.findEventDealByPage(pageNumber, pagesize, map);
	}


	@Override
	public void upDateEventDeal(EventDeal eventDeal) {
		 eventDao.saveOrUpdate(eventDeal);
	}

	@Override
	public int findEventCntById(Map<String, String> addMap) {
		return eventDao.findCountById(addMap);
	}

	@Override
	public List<EventDeal> findMyEventNo(Map<String, String> map) {
		return eventDao.findMyEventNo(map);
	}

	@Override
	public void upDateEvent(Event event) {
		 eventDao.saveOrUpdate(event);
	}

	@Override
	public void saveOrUpdateAttrs(EventAttrs eventEntity) {
		 eventDao.saveOrUpdate(eventEntity);
	}

	@Override
	public Pagenate<Event> getSjContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		return eventDao.getSjContent(pageNumber,pagesize,cxMap);
	}
	
	@Override
	public List<EventDeal> findEventDealList(Map<String, String> map){
		return eventDao.findEventDealList(map);
	}

	@Override
	public int myEventCount(Map<String, String> map) {
		return eventDao.myEventCount(map);
	}
	
	@Override
	public List<Event> findAllEvents(Map<String, String> map){
		return eventDao.findAllEvents(map);
	}
	
	@Override
	public List<Event> findRecentEvents(Map<String, String> map){
		return eventDao.findRecentEvents(map);
	}
	
	@Override
	public void delete(String ids) {
		String id[] = ids.split(";");
		for (int i = 0; i < id.length; i++) {
			eventDao.deleteById(Event.class, id[i]);
		}
	}
	@Override
	public void saveorupdate(Object entity) {
		eventDao.save(entity);
		
	}
}
