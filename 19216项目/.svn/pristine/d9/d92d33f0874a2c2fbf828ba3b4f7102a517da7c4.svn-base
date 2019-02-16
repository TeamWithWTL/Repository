package com.jcwx.dao.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.OrganizationalStructure;

/**
* @author MaBo
* 2017年7月24日
* 组织结构
*/
public interface OrganizationDao extends BaseDao {
	/**
	 * 根据参数查询组织结构
	 * @param params
	 * @return
	 */
	public List<OrganizationalStructure> findPartyByParams(Map<String, String> params);
	
	/**
	 * 根据上级ID查询组织结构
	 * @param pid
	 * @return
	 */
	public List<OrganizationalStructure> findByPid(String pid);
}
