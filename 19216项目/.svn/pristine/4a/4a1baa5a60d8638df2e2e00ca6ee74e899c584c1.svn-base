package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.LyglDao;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.utils.Pagenate;

@Service("lyglService")
public class LyglServiceImpl implements LyglService {

	@Autowired
	private LyglDao lyglDao;

	@Override
	public void delete(String ids) {
		/*String id[] = ids.split(";");
		for (int i = 0; i < id.length; i++) {
			lyglDao.deleteById(ShglBuildingEntity.class, id[i]);
		}*/
		
		lyglDao.del(ids);
	}

	@Override
	public Pagenate<ShglBuildingEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		return lyglDao.findByPage1(pageNumber, pagesize, map);
	}

	@Override
	public List<ShglBuildingEntity> findAllBuilds1(Map<String, String> map) {
		return lyglDao.findAllBuilds1(map);
	}

	@Override
	public void save1(ShglBuildingEntity build) {
		lyglDao.save(build);
	}

	@Override
	public ShglBuildingEntity findById1(String id) {
		return lyglDao.findById(ShglBuildingEntity.class, id);
	}

	@Override
	public void update1(ShglBuildingEntity buildOld) {
		lyglDao.saveOrUpdate(buildOld);
	}

	@Override
	public ShglBuildingEntity findByName(Map<String, String> addMapLy) {
		return lyglDao.findByName(addMapLy);
	}

	@Override
	public List<ShglBuildingEntity> findAllBuilds(Map<String, String> map) {
		return lyglDao.findAllBuilds(map);
	}

	@Override
	public List<ShglInmateEntity> getYzhRoom(String id) {
		return lyglDao.getYzhRoom(id);
	}

	@Override
	public List<ShglBuildingEntity> getBuildList(Map<String, String> addMap) {
		return lyglDao.getBuildList(addMap);
	}

}
