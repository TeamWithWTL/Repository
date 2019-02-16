package com.jcwx.service.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.XtcdglDao;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.service.xtgl.XtcdglService;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月27日
* 系统管理-系统菜单管理
*/
@Service("/xtcdglService")
public class XtcdglServiceImpl implements XtcdglService {

	@Autowired
	private XtcdglDao xtcdglDao;
	
	@Override
	public Pagenate<SysMenu> findMenuByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return xtcdglDao.findMenuByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public List<SysMenu> findAllMenu() {
		return xtcdglDao.findAll(SysMenu.class);
	}

	@Override
	public SysMenu findById(String menuCode) {
		return xtcdglDao.findById(SysMenu.class, menuCode);
	}

	@Override
	public void doSave(SysMenu sysMenu) {
		xtcdglDao.saveOrUpdate(sysMenu);
	}

	@Override
	public List<String> findRoleMenuByMenuCode(String menuCode) {
		return xtcdglDao.findRoleMenuByMenuCode(menuCode);
	}

	@Override
	public void delMenuById(String menuCode) {
		xtcdglDao.deleteById(SysMenu.class, menuCode);
	}

}
