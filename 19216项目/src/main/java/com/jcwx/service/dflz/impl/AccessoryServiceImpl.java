package com.jcwx.service.dflz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.AccessoryDao;
import com.jcwx.entity.dflz.AccessoryEntity;
import com.jcwx.service.dflz.AccessoryService;
@Service
public class AccessoryServiceImpl implements AccessoryService {
	@Autowired
	private AccessoryDao accessoryDao;

	@Override
	public List<AccessoryEntity> findByYwId(String yw_id) {
		return accessoryDao.findByYwId(yw_id);
	}

	@Override
	public void saveOrUpdate(AccessoryEntity accessoryEntity) {
		accessoryDao.saveOrUpdate(accessoryEntity);
	}

	@Override
	public void save(AccessoryEntity accessoryEntity) {
		accessoryDao.save(accessoryEntity);
	}

	@Override
	public void delFj(String fjId) {
		accessoryDao.deleteById(AccessoryEntity.class, fjId);
	}

	
	
}
