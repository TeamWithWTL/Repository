package com.jcwx.action.xtgl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.service.pub.LoginService;
import com.jcwx.service.xtgl.JsglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
* @author MaBo
* 2017年5月24日
* 角色管理
*/
@Controller
@RequestMapping("/xtgl/jsgl")
public class JsglAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(JsglAction.class);
	
	@Autowired
	private JsglService jsglService;
	@Autowired
	private LoginService loginService;
	
	/**
	 * 首页信息查询展示
	 * @param model
	 * @param ajaxCmd 是否ajax请求
	 * @param pageNumber 当前页码
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String roleName, String roleCode, String cdCode, HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		model.addAttribute("roleCode", acc.getRole_code());
		try {
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				// 分页查询角色信息
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("roleName", roleName);
				paramsMap.put("roleCode", roleCode);
				Pagenate<SysRole> pagenate = jsglService.findRoleByPage(pageNumber, pageSize, paramsMap);
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("roleName",roleName);
				return "xtgl/jsgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询角色信息出错", e);
		}
		
		return "xtgl/jsgl/index";
	}
	
	/**
	 * 显示添加、编辑角色页面
	 * @param roleCode 角色编码
	 * @return
	 */
	@RequestMapping("/showAddEdit")
	public String showAddEdit(String roleCode, Model model){
		log.info("角色编码roleCode=======" + roleCode);
		SysRole sysRole = null;
		if(roleCode != null && !roleCode.equals("")){
			sysRole = jsglService.findById(roleCode);
		}
		model.addAttribute("sysRole", sysRole);
		return "xtgl/jsgl/addEdit";
	}
	
	/**
	 * 验证角色编码是否存在
	 * @param roleCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCode")
	public String checkCode(String roleCode){
		log.info("roleCode=======" + roleCode);
		SysRole sysRole = jsglService.findById(roleCode);
		return sysRole == null ? "{\"valid\":\"true\"}" : "{\"valid\":\"false\"}";
	}
	
	/**
	 * 保存角色：保存或更新
	 * @param sysRole
	 * @param oldCode 原角色编号
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysRole sysRole, String oldCode, HttpSession session){
		try {
			log.info("角色编码============" + sysRole.getRoleCode());
			log.info("角色名称============" + sysRole.getRoleName());
			log.info("角色排序============" + sysRole.getOrderNo());
			log.info("角色备注============" + sysRole.getNote());
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			sysRole.setAddTime(new Date());
			sysRole.setAddCode(sysAccCount.getAccCode());
			sysRole.setAddName(sysAccCount.getName());
			if(oldCode == null || oldCode.equals("")){
				// 保存
				jsglService.saveRole(sysRole);
			}else{
				// 更新
				sysRole.setRoleCode(oldCode);
				jsglService.updRole(sysRole);
			}
			return "succ";
		} catch (Exception e) {
			log.error("保存角色出错", e);
		}
		return "fail";
	}
	
	/**
	 * 删除角色及其权限
	 * 当系统中依然由用户使用此角色时，不允许删除；超级管理员（99）不允许删除
	 * @param roleCode 角色编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDel")
	public String doDel(String roleCode){
		log.info("要删除的角色编码===========" + roleCode);
		try {
			if(roleCode.equals(ProjectUtils.getSysCfg("sysRoleCode"))){
				return "99";
			}
			// 查询系统中是否依然存在使用此角色的用户
			List<SysAccCount> list = jsglService.findAccountByRoleCode(roleCode);
			if(!list.isEmpty()){
				return "1";
			}
			
			// 删除角色的所有权限
			jsglService.delRightsByRoleCode(roleCode);
			
			// 删除角色
			jsglService.delRoleByCode(roleCode);
			
			return "succ";
		} catch (Exception e) {
			log.error("删除角色编码出错", e);
		}
		return "fail";
	}
	
	/**
	 * 权限清理
	 * 超级管理员权限（99）不允许清理
	 * @param roleCode 角色编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clrRights")
	public String clrRights(String roleCode){
		log.info("要清理的角色编码===========" + roleCode);
		try {
			if(roleCode.equals(ProjectUtils.getSysCfg("sysRoleCode"))){
				return "99";
			}
			// 删除角色的所有权限
			jsglService.delRightsByRoleCode(roleCode);
			return "succ";
		} catch (Exception e) {
			log.error("清理角色权限出错", e);
		}
		return "fail";
	}
	
	/**
	 * 显示权限分配页面
	 * @param roleCode 角色编码
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showAssignRights", produces="text/html;charset=UTF-8")
	public String showAssignRights( Model model,String roleCode){
		log.info("要分配权限的角色编码===========" + roleCode);
		SysRole sysRole = jsglService.findById(roleCode);
		
		// 查询角色拥有的权限
		List<SysRoleMenu> list = jsglService.findRightsByRoleCode(roleCode);
		Map<String, String> menuMap = new HashMap<String, String>();	// 所拥有的菜单权限
		Map<String, String> methodMap = new HashMap<String, String>();	// 所拥有的按钮权限列表
		if(list.size() > 0){
			for(SysRoleMenu sysRoleMenu : list){
				String menuCode = sysRoleMenu.getMenu().getMenuCode();
				String menuName = sysRoleMenu.getMenu().getMenuName();
				menuMap.put(menuCode, menuName);
				if(sysRoleMenu.getMethodCodes() != null && !sysRoleMenu.getMethodCodes().equals("")){
					for(String code : sysRoleMenu.getMethodCodes().split(",")){
						methodMap.put(code, code);
					}
				}
			}
		}
		
		// 查询所有的权限
		Map<String, List<SysMenu>> secondMenus = new HashMap<String, List<SysMenu>>();	// 二级菜单{一级菜单ID:[二级菜单, 二级菜单]}
		Map<String, List<SysMenu>> thirdMenus = new HashMap<String, List<SysMenu>>();	// 三级菜单{二级菜单ID:[三级菜单, 三级菜单]}
		List<SysMenu> firstMenus = loginService.findMenusByPid("");	// 获取一级菜单
		Map<String, List<SysMenu>> fMenus = new HashMap<String, List<SysMenu>>();
		fMenus.put("firstMenus", firstMenus);
		for(SysMenu fm : firstMenus){
			List<SysMenu> sysMenusS = loginService.findMenusByPid(fm.getMenuCode());
			if(!sysMenusS.isEmpty()){
				secondMenus.put(fm.getMenuCode(), sysMenusS);	// 组织二级菜单
				for(SysMenu sms : sysMenusS){
					List<SysMenu> sysMenusT = loginService.findMenusByPid(sms.getMenuCode());
					if(!sysMenusT.isEmpty()){
						thirdMenus.put(sms.getMenuCode(), sysMenusT);	// 组织三级菜单
					}
				}
			}
		}
		model.addAttribute("sysRole", sysRole);
		model.addAttribute("fMenus", fMenus);
		model.addAttribute("secondMenus", secondMenus);
		model.addAttribute("thirdMenus", thirdMenus);
		model.addAttribute("menuMap", menuMap);
		model.addAttribute("methodMap", methodMap);
		return "xtgl/jsgl/assignRights";
	}
	
	/**
	 * 权限分配
	 * 超级管理员权限（99）不允许分配
	 * @param roleCode 角色编码
	 * @param rights 权限（格式为：菜单-按钮,按钮;）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/assignRights")
	public String assignRights(String roleCode, String rights){
		try {
			log.info("要分配权限的角色========" + roleCode);
			log.info("要分配的权限========" + rights);
			if(roleCode.equals(ProjectUtils.getSysCfg("sysRoleCode"))){
				return "99";
			}
			// 将角色的权限清理后重新保存
			jsglService.delRightsByRoleCode(roleCode);
			jsglService.assignRights(roleCode, rights);
			return "succ";
		} catch (Exception e) {
			log.error("权限分配出错", e);
		}
		return "fail";
	}
}
