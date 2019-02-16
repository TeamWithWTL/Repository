package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.utils.Pagenate;

public interface SqglService {

	/**
	 * 分页查询数据
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<ShglCommunityEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 根据条件查询社区List
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ShglCommunityEntity> findAllCom1(Map<String, String> paramMap);

	/**
	 * 根据Id查询社区数据
	 * 
	 * @param id
	 * @return
	 */
	public ShglCommunityEntity findById1(String id);

//	/**
//	 * 获取行政区划列表
//	 */
//	public List<SysStrative> findAllStra();
//
//	/**
//	 * 根据id查询行政区划
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public SysStrative findStrById(String id);

	/**
	 * 保存
	 * 
	 * @param community
	 */
	public void save1(ShglCommunityEntity community);
	
	/**
	 * 保存负责人
	 * @param cmanger
	 */
	public void saveFzr(ShglCmanagerEntity cmanger);

	/**
	 * 编辑
	 * 
	 * @param communityOld
	 */
	public void update1(ShglCommunityEntity communityOld);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void deleteFzr(String ids);
	
	/**
	 * 根据条件查询社区
	 * @param paramMap
	 * @return
	 */
	public List<ShglCommunityEntity> findAllCom2(Map<String, String> paramMap);

	/**
	 * 根据社区名称查询
	 * @param addMap 
	 * @return
	 */
	public ShglCommunityEntity findByName(Map<String, String> addMap);

	/**
	 * 根据社区名称查询是否已存在
	 * @param map
	 * @return
	 */
	public List<ShglCommunityEntity> findAllCom(Map<String, String> map);
}
