package com.jcwx.service.xtgl.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.CzanglDao;
import com.jcwx.entity.pub.SysMethod;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.service.xtgl.CzanglService;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月31日
* 系统管理-操作按钮管理
*/
@Service("czanglService")
public class CzanglServiceImpl implements CzanglService {

	@Autowired
	private CzanglDao czanglDao;
	
	@Override
	public Pagenate<SysMethod> findMetByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return czanglDao.findMetByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public SysMethod findById(String metCode) {
		return czanglDao.findById(SysMethod.class, metCode);
	}

	@Override
	public void doSave(SysMethod sysMethod) {
		czanglDao.saveOrUpdate(sysMethod);
	}

	@Override
	public List<String> findRoleByMenuMethod(String menuCode, String methodCode) {
		List<String> list = new ArrayList<String>();	// 要返回的角色列表
		
		// 根据菜单编号查询使用此菜单的角色
		List<SysRoleMenu> sysRoleMenus = czanglDao.findRoleMenuByMenu(menuCode);
		
		// 根据按钮编号筛选角色并组织角色列表
		for(SysRoleMenu sysRoleMenu : sysRoleMenus){
			String methods = sysRoleMenu.getMethodCodes();
			if(methods != null && !methods.equals("")){
				String[] metCodeArr = methods.split(",");
				for(String metCode : metCodeArr){
					if(!metCode.equals("") && metCode.equals(methodCode)){
						list.add(sysRoleMenu.getRoleCode());
					}
				}
			}
		}
		
		return list;
	}

	@Override
	public void delById(String methodCode) {
		czanglDao.deleteById(SysMethod.class, methodCode);
	}

}
