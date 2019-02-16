package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.utils.Pagenate;

public interface WgglDao extends BaseDao {

	/**
	 * 分页查询数据
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<ShglGridEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap);

	/**
	 * 根据条件查询网格List
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<ShglGridEntity> findAllCom1(Map<String, String> paramMap);
	
	/**
	 * 删除
	 * @param ids
	 */
	public void del(String ids);

	/**
	 * 根据网格Id查询负责人
	 * @param gridId
	 * @return
	 */
	public List<ShglGridManagerEntity> findGridManager(String gridId);

	/**
	 * 根据网格名称查询是否存在网格
	 * @param addMapG
	 * @return
	 */
	public ShglGridEntity findByName(Map<String, String> addMapG);

	/**
	 * 根据网格名称查询是否存在网格
	 * @param map
	 * @return
	 */
	public List<ShglGridEntity> findAllSer(Map<String, String> map);

	/**
	 * 根据社区 ID 查询所有的网格
	 * @param addMap
	 * @return
	 */
	public List<ShglGridEntity> getWgById(Map<String, String> addMap);
}
