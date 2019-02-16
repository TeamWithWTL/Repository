package com.jcwx.dao.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.entity.shgl.ShglInmateEntity;

/**
* @author MaBo
* 2017年5月24日
* 系统登录
*/
public interface LoginDao extends BaseDao {
	
	/**
	 * 根据指定参数查询账户信息
	 * @param paramsMap
	 * @return
	 */
	public List<SysAccCount> findAccountByParams(Map<String, String> paramsMap);
	
	/**
	 * 根据父级ID查询某角色拥有的菜单权限
	 * @param paramsMap
	 * @return
	 */
	public List<SysRoleMenu> findRoleMenusByPid(String roleCode, String menuPid);
	
	/**
	 * 根据父级ID查询功能菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findMenusByPid(String pid);

	/**
	 * 根据ID查询居民归属信息
	 * @param addMap
	 * @return
	 */
	public SysAccMore findSysMore(Map<String, String> addMap);

	/**
	 * 校验居民身份证是否存在
	 * @param idCard
	 * @return
	 */
	public ShglInmateEntity checkCardId(String idCard);
}
