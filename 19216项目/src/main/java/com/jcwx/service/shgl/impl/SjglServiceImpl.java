package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.SjglDao;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.EventFlowRecordEntity;
import com.jcwx.entity.shgl.EventStatusEntity;
import com.jcwx.service.shgl.SjglService;
import com.jcwx.utils.Pagenate;

@Service("sjglService")
public class SjglServiceImpl implements SjglService {

	@Autowired
	private SjglDao sjglDao;

	@Override
	public Pagenate<EventEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		// TODO Auto-generated method stub
		return sjglDao.findByPage(pageNumber, pagesize, map);
	}

	@Override
	public EventEntity findById(String id) {
		// TODO Auto-generated method stub
		return sjglDao.findById(EventEntity.class, id);
	}

	@Override
	public List<EventEntity> findAllEvents(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sjglDao.findAllEvents(map);
	}

	@Override
	public void save(EventEntity eventEntity) {
		// TODO Auto-generated method stub
		sjglDao.save(eventEntity);
	}

	@Override
	public void update(EventEntity eventEntity) {
		// TODO Auto-generated method stub
		sjglDao.saveOrUpdate(eventEntity);
	}

	@Override
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String id[] = ids.split(";");
		for (int i = 0; i < id.length; i++) {
			sjglDao.deleteById(EventEntity.class, id[i]);
		}
	}

	@Override
	public void saveFlow(EventFlowRecordEntity eventflow) {
		// TODO Auto-generated method stub
		sjglDao.save(eventflow);
	}

	@Override
	public void saveSta(EventStatusEntity eventStatus) {
		// TODO Auto-generated method stub
		sjglDao.save(eventStatus);
	}

	@Override
	public EventFlowRecordEntity findByPara(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sjglDao.findByPara(map);
	}

	@Override
	public void updateFlow(EventFlowRecordEntity eventflowOld) {
		// TODO Auto-generated method stub
		sjglDao.saveOrUpdate(eventflowOld);
	}
}
