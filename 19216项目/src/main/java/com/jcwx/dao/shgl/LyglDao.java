package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.utils.Pagenate;

/**
 * 楼宇管理
 * @author wangjinxiao
 *
 */
public interface LyglDao extends BaseDao {

	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglBuildingEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 根据条件查询数据
	 * 
	 * @param map
	 * @return
	 */
	List<ShglBuildingEntity> findAllBuilds1(Map<String, String> map);

	/**
	 * 删除
	 * @param ids
	 */
	public void del(String ids);
	
	/**
	 * 根据楼宇名称和小区ID查询楼宇
	 * @param addMapLy
	 * @return
	 */
	ShglBuildingEntity findByName(Map<String, String> addMapLy);

	/**
	 * 根据名称判断是否存在
	 * @param map
	 * @return
	 */
	List<ShglBuildingEntity> findAllBuilds(Map<String, String> map);
	
	/**
	 * 根据楼号获取有住户的单元及房间
	 * @param id
	 * @return
	 */
	List<ShglInmateEntity> getYzhRoom(String id);

	/**
	 * 根据社区ID查询下属楼宇
	 * @param addMap
	 * @return
	 */
	List<ShglBuildingEntity> getBuildList(Map<String, String> addMap);
	
}
