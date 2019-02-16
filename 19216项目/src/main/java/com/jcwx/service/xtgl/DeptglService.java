package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 部门管理 Service
 * @author zhangkai
 *
 */
public interface DeptglService {
	/**
	 * 分页查询部门结构
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<SysDepartment> findDepartmentByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public SysDepartment findById(String id);
	
	/**
	 * 更新部门结构
	 * @param os
	 */
	public void updateOs(SysDepartment os);
	
	/**
	 * 查询某部门的直接下级
	 * @param pid
	 * @return
	 */
	public List<SysDepartment> findByParentId(String pid);
	
	/**
	 * 删除部门结构
	 * @param deptIds 部门编码，多个编码之间用;分隔，存在下级的部门结构不予删除
	 */
	public void delDeptByIds(String deptIds);
	
	/**
	 * 保存部门结构
	 * @param deptStr 组织架构（id,pid,name;......）
	 */
	public void saveDept(String deptStr);

}
