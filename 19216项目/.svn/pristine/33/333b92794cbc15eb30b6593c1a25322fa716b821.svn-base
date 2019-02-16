package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月27日
* 系统管理-系统菜单管理
*/
public interface XtcdglDao extends BaseDao {
	
	/**
	 * 分页查询系统菜单
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 */
	public Pagenate<SysMenu> findMenuByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据菜单编号查询使用此菜单的角色
	 * @param menuCode 菜单编号
	 * @return
	 */
	public List<String> findRoleMenuByMenuCode(String menuCode);
}
