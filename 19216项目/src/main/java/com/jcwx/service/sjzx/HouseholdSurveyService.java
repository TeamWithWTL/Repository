package com.jcwx.service.sjzx;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.utils.Pagenate;

/**
 * 入户调查service接口
 * @author 李伟
 * @time 2017年11月4日上午8:42:04
 */
public interface HouseholdSurveyService {
	/**
	 * 查询社情民意
	 * @author 李伟
	 * @time 2017年11月4日上午9:46:46
	 * @param pageNumber 初始页
	 * @param pagesize 分页数
	 * @param paramsMap 查询条件
	 * @return
	 */
	Pagenate<ShglSqmyEntity> findByPage(int pageNumber, int pagesize, Map<String, String> paramsMap);
	
	
}
