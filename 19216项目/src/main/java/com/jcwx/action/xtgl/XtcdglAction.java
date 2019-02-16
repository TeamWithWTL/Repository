package com.jcwx.action.xtgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.service.pub.LoginService;
import com.jcwx.service.xtgl.XtcdglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;

/**
* @author MaBo
* 2017年5月27日
* 系统菜单管理
*/
@Controller
@RequestMapping("/xtgl/xtcdgl")
public class XtcdglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(XtcdglAction.class);
	
	@Autowired
	private XtcdglService xtcdglService;
	@Autowired
	private LoginService loginService;
	
	/**
	 * 首页信息查询展示
	 * @param model
	 * @param ajaxCmd 是否ajax请求
	 * @param pageNumber 当前页码
	 * @param menuName 菜单名称
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, Integer frameHeight, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String menuName, String menuCode, HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		model.addAttribute("roleCode", acc.getRole_code());
		try {
			log.info("menuName==============" + menuName);
			log.info("menuCode==============" + menuCode);
			model.addAttribute("frameHeight", frameHeight);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				// 分页查询角色信息
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("menuName", menuName);
				paramsMap.put("menuCode", menuCode);
				Pagenate<SysMenu> pagenate = xtcdglService.findMenuByPage(pageNumber, pageSize, paramsMap);
				
				Map<String, String> menuMap = new HashMap<String, String>();
				for(SysMenu sysMenu : xtcdglService.findAllMenu()){
					String code = sysMenu.getMenuCode();
					String name = sysMenu.getMenuName();
					menuMap.put(code, name);
				}
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("menuMap", menuMap);
				model.addAttribute("menuCode", menuCode);
				model.addAttribute("menuName", menuName);
				return "xtgl/xtcdgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询系统菜单信息出错", e);
		}
		
		return "xtgl/xtcdgl/index";
	}
	
	/**
	 * 菜单树节点数据加载
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/initMenuTree", produces="text/html;charset=UTF-8")
	public String initMenuTree(String id){
		List<SysMenu> sysMenus = loginService.findMenusByPid(id);
		List<Map<String, String>> nodes = new ArrayList<Map<String,String>>();
		for(SysMenu sysMenu : sysMenus){
			Map<String, String> nodeMap = new HashMap<String, String>();
			String menuCode = sysMenu.getMenuCode();	// 菜单编号
			String menuName = sysMenu.getMenuName();	// 菜单名称
			nodeMap.put("id", menuCode);
			nodeMap.put("name", menuName);
			List<SysMenu> children = loginService.findMenusByPid(menuCode);
			if(children.isEmpty()){
				nodeMap.put("isParent", "false");
			}else{
				nodeMap.put("isParent", "true");
			}
			nodes.add(nodeMap);
		}
		return JSONArray.fromObject(nodes).toString();
	}
	
	/**
	 * 显示添加、编辑菜单页面
	 * @param menuCode 菜单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/showAddEdit")
	public String showAddEdit(String menuCode, Model model){
		log.info("菜单编码menuCode=======" + menuCode);
		SysMenu sysMenu = null;
		if(menuCode != null && !menuCode.equals("")){
			sysMenu = xtcdglService.findById(menuCode);
		}
		
		List<SysMenu> allMenu = xtcdglService.findAllMenu();
		
		model.addAttribute("sysMenu", sysMenu);
		model.addAttribute("allMenu", allMenu);
		model.addAttribute("icons", sysMenu != null && sysMenu.getIcoUrl() != null && !sysMenu.getIcoUrl().equals("") ? sysMenu.getIcoUrl() : "");
		
		return "xtgl/xtcdgl/addEdit";
	}
	
	/**
	 * 保存、更新系统菜单
	 * @param sysMenu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysMenu sysMenu){
		try {
			String menuCode = sysMenu.getMenuCode();
			if(menuCode == null || menuCode.equals("")){
				// 保存
				sysMenu.setMenuCode(null);
			}
			if(sysMenu.getParentId() != null && "".equals(sysMenu.getParentId())){
				sysMenu.setParentId(null);
			}
			xtcdglService.doSave(sysMenu);
			return "succ";
		} catch (Exception e) {
			log.error("系统菜单保存出错", e);
		}
		return "fail";
	}
	
	/**
	 * 删除系统菜单
	 * @param menuCode 菜单编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDel")
	public String doDel(String menuCode){
		try {
			// 判断是否有使用此菜单的角色
			List<String> roles = xtcdglService.findRoleMenuByMenuCode(menuCode);
			if(!roles.isEmpty()){
				return "1";
			}
			// 判断此菜单下是否存在子菜单
			List<SysMenu> children = loginService.findMenusByPid(menuCode);
			if(!children.isEmpty()){
				return "2";
			}
			// 删除菜单
			xtcdglService.delMenuById(menuCode);
			return "succ";
		} catch (Exception e) {
			log.error("系统菜单删除出错", e);
		}
		return "fail";
	}
}
