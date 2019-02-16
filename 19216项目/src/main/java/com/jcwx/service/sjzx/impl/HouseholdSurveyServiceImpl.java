package com.jcwx.service.sjzx.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.sjzx.HouseholdSurveyDao;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.service.sjzx.HouseholdSurveyService;
import com.jcwx.utils.Pagenate;
/**
 * 入户调查service接口实现
 * @author 李伟
 * @time 2017年11月4日上午8:43:47
 */
@Service
public class HouseholdSurveyServiceImpl implements HouseholdSurveyService{
	@Autowired
	HouseholdSurveyDao householdSurveyDao;
	/**
	 * 查询社情民意
	 */
	@Override
	public Pagenate<ShglSqmyEntity> findByPage(int pageNumber, int pagesize, Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		return householdSurveyDao.findByPage(pageNumber, pagesize, paramsMap);
	}

}
