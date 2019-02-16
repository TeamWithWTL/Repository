package com.jcwx.service.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;

/**
 * 
 * 部门树 Service
 * @author zhangkai
 *
 */
public interface DepartmentService {
	
	/**
	 * 根据参数查询部门结构
	 * @param params
	 * @return
	 */
	public List<SysDepartment> findByParams(Map<String, String> params);
	
	/**
	 * 根据部门编号查询此部门的所有上级部门
	 * @param deptId
	 * @return
	 */
	public List<SysDepartment> findAllParents(String deptId);
	
	/**
	 * 根据部门编号查询此部门的所有下级部门
	 * @param deptId
	 * @return
	 */
	public List<SysDepartment> findAllChildren(String deptId);
	
	/**
	 * 根据ID查询部门信息
	 * @param id
	 * @return
	 */
	public SysDepartment findById(String id);
	/**
	 * 根据部门id查询部门人员
	 * @author 李伟
	 * @time 2017年11月6日下午3:32:35
	 * @param deptId
	 * @return
	 */
	public List<SysAccCountLazy> findAllPsons(String deptId);
	
}
