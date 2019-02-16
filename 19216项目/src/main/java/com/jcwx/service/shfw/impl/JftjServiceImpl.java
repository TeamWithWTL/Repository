package com.jcwx.service.shfw.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shfw.JftjDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.service.shfw.JftjService;
import com.jcwx.utils.Pagenate;

@Service("jftjService") 
public class JftjServiceImpl implements JftjService {
	
	@Autowired
	private JftjDao jftjDao;

	@Override
	public Pagenate<SysAccCountLazy> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return jftjDao.findByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<ShfwJflsEntity> getByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return jftjDao.getByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public void save(ShfwJflsEntity jf) {
		jf.setType("2");			//减积分(积分兑换)
		jf.setCreateDate(new Date());
		jftjDao.save(jf);
	}

}
