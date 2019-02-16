package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.ZfjgDao;
import com.jcwx.entity.shgl.ShglGmanagerEntity;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.service.shgl.ZfjgService;
import com.jcwx.utils.Pagenate;

@Service("zfjgService")
public class ZfjgServiceImpl implements ZfjgService {

	@Autowired
	private ZfjgDao zfjgDao;
	
	@Override
	public Pagenate<ShglGovernmentEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		return zfjgDao.findByPage1(pageNum, pageSize, paramMap);
	}

	@Override
	public List<ShglGovernmentEntity> findAllGover(Map<String, String> paramMap) {
		return zfjgDao.findAllGover(paramMap);
	}

	@Override
	public ShglGovernmentEntity findById(String id) {
		return zfjgDao.findById(ShglGovernmentEntity.class, id);
	}

	@Override
	public void save(ShglGovernmentEntity s) {
		zfjgDao.save(s);
	}

	@Override
	public void update(ShglGovernmentEntity s) {
		zfjgDao.saveOrUpdate(s);
	}

	@Override
	public void del(String ids) {
		String[] id = ids.split(";");
		for(String i:id){
			zfjgDao.deleteById(ShglGovernmentEntity.class, i);
		}
	}

	@Override
	public void saveFzr(ShglGmanagerEntity s) {
		zfjgDao.save(s);
	}

	@Override
	public void delFzr(String id) {
		zfjgDao.deleteById(ShglGmanagerEntity.class, id);
	}

	@Override
	public ShglGovernmentEntity findByName(Map<String, String> addMap) {
		return zfjgDao.findByName(addMap);
	}

	@Override
	public List<ShglGovernmentEntity> findGoverByName(Map<String, String> map) {
		return zfjgDao.findGoverByName(map);
	}

}
