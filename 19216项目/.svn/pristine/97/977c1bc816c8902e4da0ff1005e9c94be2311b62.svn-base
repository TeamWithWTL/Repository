package com.jcwx.service.dflz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.AppDzywDao;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.service.dflz.AppDzywService;
import com.jcwx.utils.Pagenate;
/**
 * app党政要闻 service 实现
 * @author 李伟
 * @time 2017年11月8日下午4:35:50
 */
@Service
public class AppDzywServiceImpl implements AppDzywService{
	@Autowired
	private AppDzywDao appDzywDao;
	/**
	 * app已审核要闻查询
	 */
	@Override
	public Pagenate<DzywEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return appDzywDao.findByPage(pageNumber,pagesize,map);
	}
	

}
