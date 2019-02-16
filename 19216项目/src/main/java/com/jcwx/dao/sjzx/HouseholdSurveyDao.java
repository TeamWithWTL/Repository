package com.jcwx.dao.sjzx;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.utils.Pagenate;

/**
 * 入户调查Dao接口
 * @author 李伟
 * @time 2017年11月4日上午8:35:55
 */
public interface HouseholdSurveyDao extends BaseDao{
	/**
	 * 查询社情民意
	 * @author 李伟
	 * @time 2017年11月4日上午9:52:49
	 * @param pageNumber
	 * @param pagesize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShglSqmyEntity> findByPage(int pageNumber, int pagesize, Map<String, String> paramsMap);

}
