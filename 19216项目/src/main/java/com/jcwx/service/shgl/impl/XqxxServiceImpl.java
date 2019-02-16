package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.XqxxDao;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.utils.Pagenate;

@Service("xqxxService")
public class XqxxServiceImpl implements XqxxService {

	@Autowired
	private XqxxDao xqxxDao;

	@Override
	public void delete(String ids) {
		xqxxDao.del(ids);
	}

	@Override
	public Pagenate<ShglVillageEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		return xqxxDao.findByPage1(pageNumber, pagesize, map);
	}

	@Override
	public ShglVillageEntity findById1(String id) {
		return xqxxDao.findById(ShglVillageEntity.class, id);
	}

	@Override
	public List<ShglVillageEntity> findAllVillages1(Map<String, String> map) {
		return xqxxDao.findAllVillages1(map);
	}

	@Override
	public void save1(ShglVillageEntity village) {
		xqxxDao.save(village);
	}

	@Override
	public void update1(ShglVillageEntity villageOld) {
		xqxxDao.saveOrUpdate(villageOld);
	}

	@Override
	public void saveFzr(ShglVmanagerEntity s) {
		xqxxDao.save(s);
	}

	@Override
	public void delFzr(String id) {
		xqxxDao.deleteById(ShglVmanagerEntity.class, id);
	}

	@Override
	public ShglVillageEntity findByName(Map<String, String> addMapV) {
		return xqxxDao.findByName(addMapV);
	}

	@Override
	public List<ShglVillageEntity> findAllVillages(Map<String, String> map) {
		return xqxxDao.findAllVillages(map);
	}

	@Override
	public List<ShglVillageEntity> findAllVillages() {
		return xqxxDao.findAllVillages();
	}

}
