package com.jcwx.dao.dflz.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.dflz.ExpAcceDao;
import com.jcwx.entity.dflz.AccessoryEntity;
/**
 * 曝光附件Dao实现类
 * @author 李伟
 * @time 2017年10月26日上午10:32:41
 */
@SuppressWarnings("unchecked")
@Repository
public class ExpAcceDaoImpl extends BaseDaoImpl implements ExpAcceDao{

	@Override
	public List<AccessoryEntity> findByNewId(String new_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
