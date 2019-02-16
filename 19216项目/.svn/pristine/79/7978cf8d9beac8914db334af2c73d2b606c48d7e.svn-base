package com.jcwx.action.pub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.service.pub.LoginService;

/**
 * @author MaBo 
 * 2017年5月23日 
 * 系统登录
 */
@Controller
@RequestMapping("/login")
public class LoginAction {

	private Logger logger = Logger.getLogger(LoginAction.class);

	@Autowired
	private LoginService loginService;

	/**
	 * 登录验证
	 * @param sysAccCount 账号登录信息
	 * @param authCode 验证码
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@ResponseBody
	@RequestMapping("/checkLogin")
	public String checkLogin(SysAccCountLazy sysAccCount, String authCode, HttpSession session) {
		try {
			// 验证码校验
			String _authCode = session.getAttribute("authCode")==null?"":session.getAttribute("authCode").toString();
			if(_authCode == null || _authCode.equals("")){
				return "noCode";
			}else if(!_authCode.equals(authCode)){
				return "authCodeErr";
			}
			
			// 验证账户是否存在
			String accCode = sysAccCount.getAccCode();
			String pwd = sysAccCount.getPwd();
			SysAccCount accCount = loginService.checkLogin(accCode, pwd);
			if (null == accCount) {
				return "fail";
			}
			if(accCount.getRole_code().indexOf("14") !=-1 || accCount.getRole_code().indexOf("01") !=-1){
				return "noPower";
			}
			// 验证账户是否有效
			String validFlag = accCount.getValidFlag();
			if(!"1".equals(validFlag)){
				return "expired";
			}
			
			// 根据角色编码获取角色的菜单权限（系统最多支持三级菜单）
			List<SysMenu> firstMenus = new ArrayList<SysMenu>(); // 一级菜单
			Map<String, List<SysMenu>> secondMenus = new HashMap<String, List<SysMenu>>(); // 二级菜单{一级菜单ID:[二级菜单, 二级菜单]}
			Map<String, List<SysMenu>> thirdMenus = new HashMap<String, List<SysMenu>>(); // 三级菜单{二级菜单ID:[三级菜单, 三级菜单]}
			if (!"admin".equals(accCode)) {
				// 普通角色
				List<SysAccRole> roleCodes = accCount.getSysaccrole();
				StringBuffer buf = new StringBuffer();
				for(SysAccRole roles : roleCodes){
					buf. append(roles.getRoleCode()+",");
				}
				 String f = buf.toString();
				 String roles [] = f.split(",");
				for(String roleCode : roles){
					List<SysMenu> first = new ArrayList<SysMenu>(); // 存放一级菜单
					first = loginService.findRoleMenusByPid(roleCode, null);
					if(!first.isEmpty()){
						if(!firstMenus.isEmpty()){
							firstMenus.removeAll(first);
							firstMenus.addAll(first);
						}else{
							firstMenus.addAll(first);
						}
					}
					  for (SysMenu fm : first) {
						List<SysMenu> sysMenusS = loginService.findRoleMenusByPid(roleCode, fm.getMenuCode());
						List<SysMenu> mapSecondList = secondMenus.get(fm.getMenuCode());
						if (!sysMenusS.isEmpty()) {
							if(mapSecondList != null){
								mapSecondList.removeAll(sysMenusS);
								mapSecondList.addAll(sysMenusS);
								secondMenus.put(fm.getMenuCode(), mapSecondList); 
							}else{
								secondMenus.put(fm.getMenuCode(), sysMenusS); // 组织二级菜单
							}
							for (SysMenu sms : sysMenusS) {
								List<SysMenu> sysMenusT = loginService.findRoleMenusByPid(roleCode, sms.getMenuCode());
								List<SysMenu> mapThirdList = thirdMenus.get(sms.getMenuCode());
								if (!sysMenusT.isEmpty()) {
									if (mapThirdList != null) {
										mapThirdList.removeAll(sysMenusT);
										mapThirdList.addAll(sysMenusT);
										thirdMenus.put(sms.getMenuCode(), mapThirdList); // 组织三级菜单
									}else{
										thirdMenus.put(sms.getMenuCode(), sysMenusT); // 组织三级菜单
									}
								}
							}
						}
					}
				}
			} else {
				// 系统的超级管理员，拥有所有功能菜单，直接从菜单表中查询
				firstMenus = loginService.findMenusByPid(null); // 获取一级菜单
				for (SysMenu fm : firstMenus) {
					List<SysMenu> sysMenusS = loginService.findMenusByPid(fm.getMenuCode());
					if (!sysMenusS.isEmpty()) {
						secondMenus.put(fm.getMenuCode(), sysMenusS); // 组织二级菜单
						for (SysMenu sms : sysMenusS) {
							List<SysMenu> sysMenusT = loginService.findMenusByPid(sms.getMenuCode());
							if (!sysMenusT.isEmpty()) {
								thirdMenus.put(sms.getMenuCode(), sysMenusT); // 组织三级菜单
							}
						}
					}
				}
			}
			// 获取首次加载的URL
			String loadUrl = "";
			if (!firstMenus.isEmpty()) {
				SysMenu firstMenu = firstMenus.get(0);
				String menuCodeF = firstMenu.getMenuCode();
				if (secondMenus.get(menuCodeF) == null || secondMenus.get(menuCodeF).isEmpty()) {
					loadUrl = firstMenu.getUrl();
				} else {
					SysMenu sencondMenu = secondMenus.get(menuCodeF).get(0);
					String menuCodeS = sencondMenu.getMenuCode();
					if (thirdMenus.get(menuCodeS) == null || thirdMenus.get(menuCodeS).isEmpty()) {
						loadUrl = sencondMenu.getUrl();
					} else {
						SysMenu thirdMenu = thirdMenus.get(menuCodeS).get(0);
						loadUrl = thirdMenu.getUrl();
					}
				}
			}

			// 将用户账户信息和用户详情存入Session
			session.setAttribute("sysAccCount", accCount);
			// 将用户的菜单功能和首次需要加载的URL存入Session
			
			//排序
			   
	        Collections.sort(firstMenus, new Comparator<SysMenu>(){  
	            /*  
	             * int compare(Student o1, Student o2) 返回一个基本类型的整型，  
	             * 返回负数表示：o1 小于o2，  
	             * 返回0 表示：o1和o2相等，  
	             * 返回正数表示：o1大于o2。  
	             */  
	            public int compare(SysMenu o1, SysMenu o2) {  
	                //按照学生的年龄进行升序排列  
	                if(o1.getOrderNo() > o2.getOrderNo()){  
	                    return 1;  
	                }  
	                if(o1.getOrderNo() == o2.getOrderNo()){  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        });   
	        for (List<SysMenu> value : secondMenus.values()) {  
		        Collections.sort(value, new Comparator<SysMenu>(){   
		            public int compare(SysMenu o1, SysMenu o2) {  
		              
		                //按照学生的年龄进行升序排列  
		                if(o1.getOrderNo() > o2.getOrderNo()){  
		                    return 1;  
		                }  
		                if(o1.getOrderNo() == o2.getOrderNo()){  
		                    return 0;  
		                }  
		                return -1;  
		            }  
		        }); 
	        } 
	        for (List<SysMenu> value : thirdMenus.values()) {  
		        Collections.sort(value, new Comparator<SysMenu>(){   
		            public int compare(SysMenu o1, SysMenu o2) {  
		              
		                //按照学生的年龄进行升序排列  
		                if(o1.getOrderNo() > o2.getOrderNo()){  
		                    return 1;  
		                }  
		                if(o1.getOrderNo() == o2.getOrderNo()){  
		                    return 0;  
		                }  
		                return -1;  
		            }  
		        }); 
	        } 
			session.setAttribute("firstMenus", firstMenus);
			session.setAttribute("secondMenus", secondMenus);
			session.setAttribute("thirdMenus", thirdMenus);
			session.setAttribute("loadUrl", loadUrl);
			return "succ";
		} catch (Exception e) {
			logger.error("登录验证出错", e);
		}
		return "fail";
	}
}
