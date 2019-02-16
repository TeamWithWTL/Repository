package com.jcwx.dao.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;
/**
 * 
 * 部门树 Dao
 * @author zhangkai 
 * 
*/
public interface DepartmentDao extends BaseDao {
	/**
	 * 根据参数查询部门结构
	 * @param params
	 * @return
	 */
	public List<SysDepartment> findDeptByParams(Map<String, String> params);
	
	/**
	 * 根据上级ID查询部门结构
	 * @param pid
	 * @return
	 */
	public List<SysDepartment> findByPid(String pid);
	/**
	 * 根据部门id查询部门人员
	 * @author 李伟
	 * @time 2017年11月6日下午3:36:00
	 * @param deptId
	 * @return
	 */
	public List<SysAccCountLazy> findAllPsons(String deptId);
	
}
