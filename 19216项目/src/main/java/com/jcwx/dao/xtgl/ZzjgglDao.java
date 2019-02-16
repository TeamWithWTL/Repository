package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年7月25日
* 组织架构管理
*/
public interface ZzjgglDao extends BaseDao {
	
	/**
	 * 分页查询组织架构
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<OrganizationalStructure> findZzjgByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 查询某机构的直接下级
	 * @param pid
	 * @return
	 */
	public List<OrganizationalStructure> findByParentId(String pid);
}
