package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.SxtglDao;
import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.service.shgl.SxtglService;
import com.jcwx.utils.Pagenate;

@Service("sxtglService")
public class SxtglServiceImpl implements SxtglService {

	@Autowired
	private SxtglDao sxtglDao;

	@Override
	public Pagenate<CameraEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		// TODO Auto-generated method stub
		return sxtglDao.findByPage(pageNumber, pagesize, map);
	}

	@Override
	public CameraEntity findById(String id) {
		// TODO Auto-generated method stub
		return sxtglDao.findById(CameraEntity.class, id);
	}

	@Override
	public List<CameraEntity> findAllCas(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sxtglDao.findAllCas(map);
	}

	@Override
	public void save(CameraEntity camera) {
		// TODO Auto-generated method stub
		sxtglDao.save(camera);
	}

	@Override
	public void update(CameraEntity camera) {
		// TODO Auto-generated method stub
		sxtglDao.saveOrUpdate(camera);
	}

	@Override
	public void delete(String ids) {
		// TODO Auto-generated method stub
		String id[] = ids.split(";");
		for (int i = 0; i < id.length; i++) {
			sxtglDao.deleteById(CameraEntity.class, id[i]);
		}
	}

}
