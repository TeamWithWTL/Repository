package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysMethod;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月31日
* 系统管理-操作按钮管理
*/
public interface CzanglService {
	/**
	 * 分页查询操作按钮
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	public Pagenate<SysMethod> findMetByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据按钮编号查询操作按钮
	 * @param metCode
	 * @return
	 */
	public SysMethod findById(String metCode);
	
	/**
	 * 保存、更新操作按钮
	 * @param sysMethod
	 */
	public void doSave(SysMethod sysMethod);
	
	/**
	 * 根据菜单编号和按钮编号查询使用此权限的角色
	 * @param menuCode 菜单编号
	 * @param methodCode 按钮编号
	 * @return
	 */
	public List<String> findRoleByMenuMethod(String menuCode, String methodCode);
	
	/**
	 * 根据ID删除操作按钮
	 * @param methodCode
	 */
	public void delById(String methodCode);
}
