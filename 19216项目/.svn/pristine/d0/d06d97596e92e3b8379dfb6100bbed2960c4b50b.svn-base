package com.jcwx.dao.ws.impl;

import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.ws.WSDao;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;

/**
 * 
 * @author wangjinxiao
 *
 */
@Repository
public class WSDaoImpl extends BaseDaoImpl implements WSDao {

	@Override
	public boolean saveOrUpdateRdxwArrt(RdxwArrtsEntity rdxwArrtsEntity) {
		try {
			super.saveOrUpdate(rdxwArrtsEntity);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
