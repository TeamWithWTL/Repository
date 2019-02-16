/**
 * 
 */
package com.jcwx.service.xtbg.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.DeptPersonDao;
import com.jcwx.dao.xtbg.TzggRyDao;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.service.xtbg.TzggRyService;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */

@Service("tzggRyService")
public class TzggRyServiceImpl implements TzggRyService{
	
	
	@Autowired
	private TzggRyDao tzggRyDao;
	
	@Autowired
	private DeptPersonDao departmentPersonelDao;


	/* (non-Javadoc)
	 * @see com.jcwx.service.xtbg.TzggRyService#saveOrUpdate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveOrUpdate(String pid,String tid,String pname) {
		TzggRyEntity tzggRyEntity = new TzggRyEntity();
		tzggRyEntity.setTzgg_id(pid);//父id
		tzggRyEntity.setReceivce_name(pname);
		tzggRyEntity.setReceivce_id(tid);
		tzggRyDao.saveOrUpdate(tzggRyEntity);
		
	}


	@Override
	public void save(TzggRyEntity tzggRy) {
		tzggRyDao.save(tzggRy);
		
	}


	@Override
	public List<TzggRyEntity> findByAccCode(String id) {
		// TODO Auto-generated method stub
		return tzggRyDao.findByAccCode(id);
	}


	@Override
	public Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tzggRyDao.getTzggContent(pageNumber, pageSize, map);
	}

}
