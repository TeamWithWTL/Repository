package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.utils.Pagenate;

public interface WgglService {

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
	public List<ShglGridEntity> findAllSer1(Map<String, String> paramMap);
	
	/**
	 * 根据id查询服务站
	 * 
	 * @param id
	 * @return
	 */
	public ShglGridEntity findById1(String id);

	/**
	 * 保存
	 * 
	 * @param serviceStation
	 */
	public void save1(ShglGridEntity grid);

	/**
	 * 编辑保存
	 * 
	 * @param serviceStationOld
	 */
	public void update1(ShglGridEntity grid);

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
	public void saveFzr(ShglGridManagerEntity s);
	
	/**
	 * 删除负责人
	 * @param id
	 */
	public void delFzr(String id);

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
	public List<ShglGridEntity> getWgBySqId(Map<String, String> addMap);

}
