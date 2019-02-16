package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年7月25日
* 组织架构管理
*/
public interface ZzjgglService {
	
	/**
	 * 分页查询组织架构
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<OrganizationalStructure> findZzjgByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public OrganizationalStructure findById(String id);
	
	/**
	 * 更新组织架构
	 * @param os
	 */
	public void updateOs(OrganizationalStructure os);
	
	/**
	 * 查询某机构的直接下级
	 * @param pid
	 * @return
	 */
	public List<OrganizationalStructure> findByParentId(String pid);
	
	/**
	 * 删除组织机构
	 * @param orgCodes 机构编码，多个编码之间用;分隔，存在下级的机构不予删除
	 */
	public void delOrgs(String orgCodes);
	
	/**
	 * 保存组织架构
	 * @param orgStr 组织架构（id,pid,name;......）
	 */
	public void saveOrg(String orgStr);
	
}
