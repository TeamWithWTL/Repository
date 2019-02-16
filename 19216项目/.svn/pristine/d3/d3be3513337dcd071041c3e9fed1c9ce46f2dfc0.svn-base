package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysMethod;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月31日
* 系统管理-操作按钮管理
*/
public interface CzanglDao extends BaseDao {
	/**
	 * 分页查询操作按钮
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<SysMethod> findMetByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据菜单编号查询使用此权限的角色
	 * @param menuCode 菜单编号
	 * @return
	 */
	public List<SysRoleMenu> findRoleMenuByMenu(String menuCode);
}
