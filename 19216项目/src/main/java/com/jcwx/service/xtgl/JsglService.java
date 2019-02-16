package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月26日
* 系统管理-角色管理
*/
public interface JsglService {
	
	/**
	 * 分页查询角色
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 */
	public Pagenate<SysRole> findRoleByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 查询所有的角色
	 * @return
	 */
	public List<SysRole> findAll();
	
	/**
	 * 根据ID查询角色
	 * @param roleCode
	 * @return
	 */
	public SysRole findById(String roleCode);
	
	/**
	 * 保存角色
	 * @param sysRole
	 */
	public void saveRole(SysRole sysRole);
	
	/**
	 * 更新角色
	 * @param sysRole
	 */
	public void updRole(SysRole sysRole);
	
	/**
	 * 根据角色编码查询用户信息
	 * @param roleCode 角色编码
	 * @return
	 */
	public List<SysAccCount> findAccountByRoleCode(String roleCode);
	
	/**
	 * 根据角色编码删除角色
	 * @param roleCode 角色编码
	 */
	public void delRoleByCode(String roleCode);
	
	/**
	 * 根据角色编码删除角色的所有权限
	 * @param roleCode 角色编码
	 */
	public void delRightsByRoleCode(String roleCode);
	
	/**
	 * 根据角色编码查询角色拥有的权限
	 * @param roleCode 角色编码
	 */
	public List<SysRoleMenu> findRightsByRoleCode(String roleCode);
	
	/**
	 * 分配权限
	 * @param roleCode
	 * @param rights
	 */
	public void assignRights(String roleCode, String rights);
	
	/**
	 * 查询出超级管理员外的角色
	 * @return
	 */
	public List<SysRole> findall();
}
