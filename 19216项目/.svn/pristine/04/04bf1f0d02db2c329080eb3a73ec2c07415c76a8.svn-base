package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 部门管理 Dao
 * @author zhangkai 
 * 
*/
public interface DeptglDao extends BaseDao {
	/**
	 * 分页查询部门结构
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<SysDepartment> findDepartmentByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 查询某部门的直接下级
	 * @param pid
	 * @return
	 */
	public List<SysDepartment> findByParentId(String pid);
	
}
