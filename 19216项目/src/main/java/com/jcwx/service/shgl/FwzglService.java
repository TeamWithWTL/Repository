package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.utils.Pagenate;

public interface FwzglService {

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
	public List<ShglServiceStationEntity> findAllCom1(Map<String, String> paramMap);

	/**
	 * 根据id查询服务站
	 * 
	 * @param id
	 * @return
	 */
	public ShglServiceStationEntity findById1(String id);

	/**
	 * 保存
	 * 
	 * @param serviceStation
	 */
	public void save1(ShglServiceStationEntity serviceStation);

	/**
	 * 编辑保存
	 * 
	 * @param serviceStationOld
	 */
	public void update1(ShglServiceStationEntity serviceStationOld);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);
	
	/**
	 * 保存负责人
	 * @param s
	 */
	public void saveFzr(ShglSmanagerEntity s);
	
	/**
	 * 删除负责人
	 * @param id
	 */
	public void delFzr(String id);
	
	/**
	 * 根据条件查询服务站
	 * @param paramMap
	 * @return
	 */
	public List<ShglServiceStationEntity> findAllCom2(Map<String, String> paramMap);

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
