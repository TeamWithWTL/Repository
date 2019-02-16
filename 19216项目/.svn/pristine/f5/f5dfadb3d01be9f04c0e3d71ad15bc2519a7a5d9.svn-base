package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.utils.Pagenate;

public interface FwzglDao extends BaseDao {
	
	/**
	 * 分页查询数据
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<ShglServiceStationEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap);

	/**
	 * 根据条件查询List
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ShglServiceStationEntity> findAllSs1(Map<String, String> paramMap);
	
	/**
	 * 删除
	 * @param ids
	 */
	public void del(String ids);
	
	/**
	 * 根据条件查询服务站
	 * @param paramMap
	 * @return
	 */
	public List<ShglServiceStationEntity> findAllSs2(Map<String, String> paramMap);

	/**
	 * 根据服务站名称查询
	 * @param addMapSs
	 * @return
	 */
	public ShglServiceStationEntity findByName(Map<String, String> addMapSs);

	/**
	 * 根据服务站名称查询是否已存在
	 * @param map
	 * @return
	 */
	public List<ShglServiceStationEntity> findAllCom(Map<String, String> map);
	
	/**
	 * 根据服务站Id查询负责人
	 * @param ssId
	 * @return
	 */
	List<ShglSmanagerEntity> findFwzManager(String ssId);
	
}
