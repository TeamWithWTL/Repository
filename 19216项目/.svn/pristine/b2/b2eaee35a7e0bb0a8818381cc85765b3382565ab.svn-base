package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglAttrEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.utils.Pagenate;

public interface RwglService {

	Pagenate<RwglEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 保存下发任务
	 * @param id
	 * @param fName
	 * @param rwgl
	 * @param curRoleCode 
	 */
	void save(String id, String fName, RwglEntity rwgl, String curRoleCode);

	RwglEntity findId(String id);

	/**
	 *	我的待办任务
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwClEntity> getRwglMyContent(Integer pageNumber, int pagesize, Map<String, String> map);


	void upsave(RwglEntity rwgl);

	/**
	 * 删除任务
	 * @param ids
	 */
	void delRwContent(String ids,String fbrId);

	/**
	 * 根据角色查询
	 * @param rOLE_SQGLY
	 * @param zsId
	 * @param object 
	 * @return
	 */
	List<SysAccCount> findByCode(String rOLE_SQGLY,String zsId, String ztType);
	
	/**
	 * 根据受社区ID查询社区负责人
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
	 * 所在服务站
	 * @param fwzId
	 * @return
	 */
	ShglServiceStationEntity getFwz(String fwzId);

	/**
	 * 根据当前处理人查询下发任务
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwglEntity> getRwglMyXFList(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * app--保存任务附件
	 * @param docAttr
	 */
	void saveOrUpdateAttrs(RwglAttrEntity docAttr);

}
