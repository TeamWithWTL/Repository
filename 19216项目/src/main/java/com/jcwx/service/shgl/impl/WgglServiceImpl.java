package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.WgglDao;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.utils.Pagenate;

@Service("wgglService")
public class WgglServiceImpl implements WgglService {

	@Autowired
	private WgglDao wgglDao;


	@Override
	public void delete(String ids) {
		wgglDao.del(ids);
	}

	@Override
	public Pagenate<ShglGridEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		return wgglDao.findByPage1(pageNum, pageSize, paramMap);
	}

	@Override
	public List<ShglGridEntity> findAllSer1(Map<String, String> paramMap) {
		return wgglDao.findAllCom1(paramMap);
	}

	@Override
	public ShglGridEntity findById1(String id) {
		return wgglDao.findById(ShglGridEntity.class, id);
	}

	@Override
	public void save1(ShglGridEntity grid) {
		wgglDao.save(grid);
	}

	@Override
	public void update1(ShglGridEntity grid) {
		wgglDao.saveOrUpdate(grid);
	}

	@Override
	public void saveFzr(ShglGridManagerEntity s) {
		wgglDao.save(s);
	}

	@Override
	public void delFzr(String id) {
		wgglDao.deleteById(ShglGridManagerEntity.class, id);
	}

	@Override
	public List<ShglGridManagerEntity> findGridManager(String gridId) {
		return wgglDao.findGridManager(gridId);
	}

	@Override
	public ShglGridEntity findByName(Map<String, String> addMapG) {
		return wgglDao.findByName(addMapG);
	}

	@Override
	public List<ShglGridEntity> findAllSer(Map<String, String> map) {
		return wgglDao.findAllSer(map);
	}

	@Override
	public List<ShglGridEntity> getWgBySqId(Map<String, String> addMap) {
		return wgglDao.getWgById(addMap);
	}

}
