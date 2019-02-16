package com.jcwx.service.shfw.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shfw.YjlyDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.service.shfw.YjlyService;
import com.jcwx.utils.Pagenate;

@Service("yjlyService") 
public class YjlyServiceImpl implements  YjlyService {
	
	@Autowired
	private YjlyDao yjlyDao;

	@Override
	public Pagenate<ShfwSqhdYjEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return yjlyDao.findByPage(pageNumber, pageSize, paramsMap);
	}
	
	@Override
	public Pagenate<ShfwSqhdYjEntity> findDshLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return yjlyDao.findDshLyByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> findBtgLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return yjlyDao.findBtgLyByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> findWpfLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return yjlyDao.findWpfLyByPage(pageNumber, pageSize, paramsMap);
	}
	
	@Override
	public ShfwSqhdYjEntity findById(String id) {
		return yjlyDao.findById(ShfwSqhdYjEntity.class, id);
	}

	@Override
	public void updateScore(String id, Integer jf) {
		ShfwSqhdYjEntity yjly = findById(id);
		if(yjly!=null){
			yjly.setIntegral(jf);
			yjly.setShStatus("1");
			yjlyDao.saveOrUpdate(yjly);
		}
	}

	@Override
	public void save(ShfwJflsEntity jf) {
		jf.setType("1");			//加积分
		jf.setCreateDate(new Date());
		yjlyDao.save(jf);
	}

	@Override
	public void saveLy(ShfwSqhdYjEntity sqhdYjEntity) {
		yjlyDao.save(sqhdYjEntity);
	}
	
	@Override
	public void updateLy(ShfwSqhdYjEntity sqhdYjEntity, String id) {
		ShfwSqhdYjEntity yjly =findById(id);
		yjlyDao.saveOrUpdate(yjly);
	}
	
	/**
	 * id查询留言用户信息
	 */
	@Override
	public SysAccCount findLyUserById(String userId) {
		SysAccCount sysAccCount = yjlyDao.findById(SysAccCount.class, userId);
		return sysAccCount;
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjBySqhdId(String sqhdId, String userId) {
		return yjlyDao.getSqhdYjBySqhdId(sqhdId, userId);
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjByWfk(String sqhdId, String userId) {
		return yjlyDao.getSqhdYjByWfk(sqhdId, userId);
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjByYfk(String sqhdId, String userId) {
		return yjlyDao.getSqhdYjByYfk(sqhdId, userId);
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> getSqhdBmjlByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return yjlyDao.getSqhdBmjlByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public void saveOrUpdateAttrs(ShfwSqhdYjAttrsEntity attrs) {
		yjlyDao.saveOrUpdateAttrs(attrs);
	}

}
