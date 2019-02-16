package com.jcwx.service.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.OrganizationalStructure;

/**
* @author MaBo
* 2017年7月24日
* 组织结构
*/
public interface OrganizationService {
	
	/**
	 * 根据参数查询组织
	 * @param params
	 * @return
	 */
	public List<OrganizationalStructure> findByParams(Map<String, String> params);
	
	/**
	 * 根据组织编号查询此组织的所有上级组织
	 * @param orgCode
	 * @return
	 */
	public List<OrganizationalStructure> findAllParents(String orgCode);
	
	/**
	 * 根据组织编号查询此组织的所有下级组织
	 * @param orgCode
	 * @return
	 */
	public List<OrganizationalStructure> findAllChildren(String orgCode);
	
	/**
	 * 根据ID查询组织信息
	 * @param id
	 * @return
	 */
	public OrganizationalStructure findById(String id);
}
