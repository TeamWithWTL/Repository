package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月26日
* 系统管理-角色管理
*/
public interface JsglDao extends BaseDao {
	
	/**
	 * 分页查询角色
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 */
	public Pagenate<SysRole> findRoleByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据角色编码删除角色的所有权限
	 * @param roleCode 角色编码
	 */
	public void delRightsByRoleCode(String roleCode);
	
	/**
	 * 根据角色编码查询角色拥有的权限
	 * @param roleCode 角色编码
	 */
	public List<SysRoleMenu> findRoleMenuByParams(Map<String, String> params);
	
	/**
	 * 查询出超级管理员外的角色
	 * @return
	 */
	public List<SysRole> findall();
}
