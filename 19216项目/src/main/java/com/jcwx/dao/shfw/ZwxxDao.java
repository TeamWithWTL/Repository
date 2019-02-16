package com.jcwx.dao.shfw;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-政务信息 Dao
 * @author zhangkai 
 *
 */
public interface ZwxxDao extends BaseDao {
	
	/**
	 * 分页查询政务信息
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwZwxxEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
}
