package com.jcwx.service.sjzx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.sjzx.SjtjDao;
import com.jcwx.entity.shgl.Event;
import com.jcwx.service.sjzx.SjtjService;
import com.jcwx.utils.Pagenate;

@Service("SjtjService")
public class SjtjServiceImpl implements SjtjService{
	
	@Autowired
	private SjtjDao sjtjDao;

	@Override
	public Pagenate<Event> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> paramsMap) {
		return sjtjDao.findByPage(pageNumber,pagesize,paramsMap);
	}

	@Override
	public List<Event> findEvent(Map<String, String> paramsMap) {
		return sjtjDao.findEvent(paramsMap);
	}
}
