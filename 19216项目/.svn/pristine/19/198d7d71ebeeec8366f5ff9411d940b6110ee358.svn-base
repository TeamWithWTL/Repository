package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.varia.StringMatchFilter;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.utils.Pagenate;

public interface SqglDao extends BaseDao {

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

//	/**
//	 * 获取行政区划列表
//	 */
//	public List<SysStrative> findAllStra();

//	/**
//	 * 根据id查询行政区划
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public SysStrative findStrById(String id);
	
	/**
	 * 删除社区
	 * @param ids
	 * @return
	 */
	public void del(String ids);
	
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
