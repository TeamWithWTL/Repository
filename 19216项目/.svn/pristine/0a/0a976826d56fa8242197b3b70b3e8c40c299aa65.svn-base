package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.SqglDao;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.utils.Pagenate;

@Service("sqglService")
public class SqglServiceImpl implements SqglService {

	@Autowired
	private SqglDao sqglDao;

//	@Override
//	public List<SysStrative> findAllStra() {
//		return sqglDao.findAllStra();
//	}
//
//	@Override
//	public SysStrative findStrById(String id) {
//		return sqglDao.findStrById(id);
//	}

	@Override
	public void delete(String ids) {
//		String id[] = ids.split(";");
//		for (int i = 0; i < id.length; i++) {
//			sqglDao.deleteById(ShglCommunityEntity.class, id[i]);
//		}
		sqglDao.del(ids);
	}

	@Override
	public Pagenate<ShglCommunityEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		return sqglDao.findByPage1(pageNum, pageSize, paramMap);
	}

	@Override
	public List<ShglCommunityEntity> findAllCom1(Map<String, String> paramMap) {
		return sqglDao.findAllCom1(paramMap);
	}

	@Override
	public ShglCommunityEntity findById1(String id) {
		return sqglDao.findById(ShglCommunityEntity.class, id);
	}

	@Override
	public void save1(ShglCommunityEntity community) {
		sqglDao.save(community);
	}

	@Override
	public void saveFzr(ShglCmanagerEntity cmanger) {
		sqglDao.save(cmanger);
	}

	@Override
	public void update1(ShglCommunityEntity communityOld) {
		sqglDao.saveOrUpdate(communityOld);
	}

	@Override
	public void deleteFzr(String ids) {
		sqglDao.deleteById(ShglCmanagerEntity.class, ids);
	}
	
	@Override
	public List<ShglCommunityEntity> findAllCom2(Map<String, String> paramMap) {
		return sqglDao.findAllCom2(paramMap);
	}

	@Override
	public ShglCommunityEntity findByName(Map<String, String> addMap) {
		return sqglDao.findByName(addMap);
	}
	
	@Override
	public List<ShglCommunityEntity> findAllCom(Map<String, String> map) {
		return sqglDao.findAllCom(map);
	}
}
