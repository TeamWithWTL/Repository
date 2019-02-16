package com.jcwx.service.shzz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shzz.HdtjDao;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.HdtjService;
import com.jcwx.utils.Pagenate;

@Service("hdtjService")
public class HdtjServiceImpl implements HdtjService {
	
	@Autowired
	private HdtjDao hdtjDao;

	@Override
	public Pagenate<HdglEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return hdtjDao.findByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<HdglFkEntity> findFkByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return hdtjDao.findFkByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<ZzxxEntity> findZzByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return hdtjDao.findZzByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public Pagenate<HdglFkEntity> findFk2ByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return hdtjDao.findFk2ByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public List<HdglFkEntity> getFkCnt(Map<String, String> paramsMap) {
		return hdtjDao.getFkCnt(paramsMap);
	}
	
}
