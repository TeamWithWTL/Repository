package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.utils.Pagenate;

public interface SxtglDao extends BaseDao {
	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<CameraEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据条件查询数据
	 * 
	 * @param map
	 * @return
	 */
	List<CameraEntity> findAllCas(Map<String, String> map);

}
