package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.utils.Pagenate;

public interface XqxxService {

	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglVillageEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	public ShglVillageEntity findById1(String id);

	/**
	 * 根据条件查询
	 * @param map
	 * @return
	 */
	List<ShglVillageEntity> findAllVillages1(Map<String, String> map);

	/**
	 * 保存
	 * @param village
	 */
	public void save1(ShglVillageEntity village);

	/**
	 * 编辑保存
	 * @param villageOld
	 */
	public void update1(ShglVillageEntity villageOld);

	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids);
	
	/**
	 * 保存负责人
	 * @param s
	 */
	public void saveFzr(ShglVmanagerEntity s);
	
	/**
	 * 删除负责人
	 * @param id
	 */
	public void delFzr(String id);

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
