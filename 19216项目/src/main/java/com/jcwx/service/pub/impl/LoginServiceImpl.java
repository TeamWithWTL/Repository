package com.jcwx.service.pub.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.LoginDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.service.pub.LoginService;
import com.jcwx.utils.StringUtil;

/**
* @author MaBo
* 2017年5月24日
* 系统登录
*/
@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDao loginDao;
	
	@Override
	public SysAccCount checkLogin(String accCode, String pwd) {
		try{
		if(null != pwd && !"".equals(pwd)){
			// 将用户的密码转为MD5
			pwd = StringUtil.toMD5(pwd);
		}
		
		// 查询用户是否存在
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("accCode", accCode);
		paramsMap.put("pwd", pwd);
		List<SysAccCount> list = loginDao.findAccountByParams(paramsMap);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
		}catch(Exception e){
			return null; 
		}
	}

	@Override
	public List<SysMenu> findRoleMenusByPid(String roleCode, String pid) {
		List<SysRoleMenu> roleMenus = loginDao.findRoleMenusByPid(roleCode, pid);
		List<SysMenu> menus = new ArrayList<SysMenu>();
		for(SysRoleMenu roleMenu : roleMenus){
			menus.add(roleMenu.getMenu());
		}
		return menus;
	}

	@Override
	public List<SysMenu> findMenusByPid(String pid) {
		return loginDao.findMenusByPid(pid);
	}

	@Override
	public void save(SysAccCount saveAcc) {
		loginDao.save(saveAcc);
	}

	@Override
	public SysAccMore findSysMore(Map<String, String> addMap) {
		return loginDao.findSysMore(addMap);
	}

	@Override
	public void saveSysM(SysAccMore accMore) {
		loginDao.save(accMore);
	}

	@Override
	public void updataSysM(SysAccMore more1) {
		loginDao.saveOrUpdate(more1);
	}

	@Override
	public ShglInmateEntity checkCardId(String idCard) {
		return loginDao.checkCardId(idCard);
	}

}
