package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.FwzglDao;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.utils.Pagenate;

@Service("fwzglService")
public class FwzglServiceImpl implements FwzglService {

	@Autowired
	private FwzglDao fwzglDao;

	@Override
	public void delete(String ids) {
		fwzglDao.del(ids);
	}

	@Override
	public Pagenate<ShglServiceStationEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		return fwzglDao.findByPage1(pageNum, pageSize, paramMap);
	}

	@Override
	public List<ShglServiceStationEntity> findAllCom1(Map<String, String> paramMap) {
		return fwzglDao.findAllSs1(paramMap);
	}

	@Override
	public ShglServiceStationEntity findById1(String id) {
		return fwzglDao.findById(ShglServiceStationEntity.class, id);
	}

	@Override
	public void save1(ShglServiceStationEntity serviceStation) {
		fwzglDao.save(serviceStation);
	}

	@Override
	public void update1(ShglServiceStationEntity serviceStationOld) {
		fwzglDao.saveOrUpdate(serviceStationOld);
	}

	@Override
	public void saveFzr(ShglSmanagerEntity s) {
		fwzglDao.save(s);
	}

	@Override
	public void delFzr(String id) {
		fwzglDao.deleteById(ShglSmanagerEntity.class, id);
	}
	
	@Override
	public List<ShglServiceStationEntity> findAllCom2(Map<String, String> paramMap) {
		return fwzglDao.findAllSs2(paramMap);
	}

	@Override
	public ShglServiceStationEntity findByName(Map<String, String> addMapSs) {
		return fwzglDao.findByName(addMapSs);
	}

	@Override
	public List<ShglServiceStationEntity> findAllCom(Map<String, String> map) {
		return fwzglDao.findAllCom(map);
	}

	@Override
	public List<ShglSmanagerEntity> findFwzManager(String ssId) {
		return fwzglDao.findFwzManager(ssId);
	}
	
}
