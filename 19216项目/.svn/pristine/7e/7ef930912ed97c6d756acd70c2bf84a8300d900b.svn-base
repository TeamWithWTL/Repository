package com.jcwx.service.xtgl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.LoginDao;
import com.jcwx.dao.xtgl.JsglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.service.xtgl.JsglService;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月26日
* 系统管理-角色管理
*/
@Service("jsglService")
public class JsglServiceImpl implements JsglService {

	@Autowired
	private JsglDao jsglDao;
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public Pagenate<SysRole> findRoleByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return jsglDao.findRoleByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public List<SysRole> findAll() {
		return jsglDao.findAll(SysRole.class);
	}

	@Override
	public SysRole findById(String roleCode) {
		return jsglDao.findById(SysRole.class, roleCode);
	}

	@Override
	public void saveRole(SysRole sysRole) {
		jsglDao.save(sysRole);
	}

	@Override
	public void updRole(SysRole sysRole) {
		jsglDao.saveOrUpdate(sysRole);
	}

	@Override
	public List<SysAccCount> findAccountByRoleCode(String roleCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("roleCode", roleCode);
		return loginDao.findAccountByParams(paramsMap);
	}

	@Override
	public void delRoleByCode(String roleCode) {
		loginDao.deleteById(SysRole.class, roleCode);
	}

	@Override
	public void delRightsByRoleCode(String roleCode) {
		jsglDao.delRightsByRoleCode(roleCode);
	}

	@Override
	public List<SysRoleMenu> findRightsByRoleCode(String roleCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("roleCode", roleCode);
		return jsglDao.findRoleMenuByParams(paramsMap);
	}

	@Override
	public void assignRights(String roleCode, String rights) {
		String[] rightArr = rights.split(";");
		for(String right : rightArr){
			if(!right.equals("")){
				String menuCode = right.split("-")[0];
				String methodCodes = right.split("-").length > 1 ? right.split("-")[1] : "";
				if(!methodCodes.equals("")){
					methodCodes = methodCodes.substring(0, methodCodes.length()-1);	// 去除最后的,
				}
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setRoleCode(roleCode);
				sysRoleMenu.setMethodCodes(methodCodes);
				SysMenu menu = jsglDao.findById(SysMenu.class, menuCode);
				sysRoleMenu.setMenu(menu);
				jsglDao.save(sysRoleMenu);
			}
		}
	}

	@Override
	public List<SysRole> findall() {
		// TODO Auto-generated method stub
		return jsglDao.findall();
	}

}
