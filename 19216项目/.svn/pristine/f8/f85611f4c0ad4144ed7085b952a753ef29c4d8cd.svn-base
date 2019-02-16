package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.utils.Pagenate;

public interface XqxxDao extends BaseDao {

	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglVillageEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据条件查询
	 * @param map
	 * @return
	 */
	List<ShglVillageEntity> findAllVillages1(Map<String, String> map);
	
	/**
	 * 
	 * @param ids
	 */
	public void del(String ids);

	/**
	 * 根据小区名称查询是否存在
	 * @param addMapV
	 * @return
	 */
	ShglVillageEntity findByName(Map<String, String> addMapV);

	/**
	 * 根据小区名称查询是否存在
	 * @param map
	 * @return
	 */
	List<ShglVillageEntity> findAllVillages(Map<String, String> map);
	
	/**
	 * 获取所有小区
	 * @param map
	 * @return
	 */
	List<ShglVillageEntity> findAllVillages();
	
}
