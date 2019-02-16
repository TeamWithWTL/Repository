package com.jcwx.service.dflz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.ExpAcceDao;
import com.jcwx.entity.dflz.AccessoryEntity;
import com.jcwx.entity.dflz.ExpAcceEntity;
import com.jcwx.service.dflz.ExpAcceService;
@Service
public class ExpAcceServiceImpl  implements ExpAcceService{
	@Autowired
	private ExpAcceDao expAcceDao;
	@Override
	public void saveOrUpdate(ExpAcceEntity expAcceEntity) {
		// TODO Auto-generated method stub
		expAcceDao.saveOrUpdate(expAcceEntity);
	}
	@Override
	public List<AccessoryEntity> findByYwId(String new_id) {
		// TODO Auto-generated method stub
		return expAcceDao.findByNewId(new_id);
	}
	@Override
	public void delFj(String fjId) {
		// TODO Auto-generated method stub
		expAcceDao.deleteById(ExpAcceEntity.class, fjId);
	}

}
