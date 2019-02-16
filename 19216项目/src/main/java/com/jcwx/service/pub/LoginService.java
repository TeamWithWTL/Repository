package com.jcwx.service.pub;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.shgl.ShglInmateEntity;

/**
* @author MaBo
* 2017年5月24日
* 系统登录
*/
public interface LoginService {
	
	/**
	 * 登录验证
	 * @param accCode
	 * @param pwd
	 * @return
	 */
	public SysAccCount checkLogin(String accCode, String pwd);
	
	/**
	 * 根据父级ID查询某角色拥有的菜单权限
	 * @param roleCode
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findRoleMenusByPid(String roleCode, String pid);
	
	/**
	 * 根据父级ID查询功能菜单
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findMenusByPid(String pid);

	public void save(SysAccCount saveAcc);

	/**
	 * 获取居民归属信息
	 * @param addMap
	 * @return
	 */
	public SysAccMore findSysMore(Map<String, String> addMap);

	/**
	 * 保存居民归属信息
	 * @param accMore
	 */
	public void saveSysM(SysAccMore accMore);

	/**
	 * 修改居民归属信息
	 * @param more1
	 */
	public void updataSysM(SysAccMore more1);

	/**
	 * 校验居民身份证是否存在
	 * @param idCard
	 * @return
	 */
	public ShglInmateEntity checkCardId(String idCard);
}
