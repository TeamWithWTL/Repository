package com.jcwx.service.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.ws.WSDao;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.service.ws.WSService;

@Service("wSService")
public class WSServiceImpl implements WSService {

	@Autowired
	private WSDao wsDao;
	
	@Override
	public boolean saveOrUpdateRdxwArrt(RdxwArrtsEntity rdxwArrtsEntity) {
		// TODO Auto-generated method stub
		return wsDao.saveOrUpdateRdxwArrt(rdxwArrtsEntity);
	}

}
