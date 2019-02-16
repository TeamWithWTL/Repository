package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.utils.Pagenate;

public interface RwglDao extends BaseDao{

	Pagenate<RwglEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 我的待办任务
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwClEntity> getRwglMyContent(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 根据角色查询
	 * @param roleCode
	 * @param ztType 
	 * @param obObjectject 
	 * @return 
	 */
	List<SysAccCount> findByCode(String roleCode,String zsId, String ztType);

	/**
	 * 根据社区ID查询所有的社区负责人
	 * @param id
	 * @return
	 */
	List<ShglCmanagerEntity> findSqryById(String id);

	/**
	 * 根据社区ID查询服务站
	 * @param sqId
	 * @return
	 */
	List<ShglServiceStationEntity> findFwz(String sqId);

	/**
	 * 根据服务站ID查询网格
	 * @param fwzId
	 * @return
	 */
	List<ShglGridEntity> findWg(String fwzId);

	Pagenate<RwClEntity> getRwclContent(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据当前处理人查询下发任务
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwglEntity> getRwglMyXFList(Integer pageNumber, int pagesize, Map<String, String> map);

}
