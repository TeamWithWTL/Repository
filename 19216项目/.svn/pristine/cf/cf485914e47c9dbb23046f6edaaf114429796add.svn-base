package com.jcwx.action.xtgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysMethod;
import com.jcwx.service.xtgl.CzanglService;
import com.jcwx.service.xtgl.XtcdglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
* @author MaBo
* 2017年5月31日
* 系统管理-操作按钮管理
*/
@Controller
@RequestMapping("/xtgl/czangl")
public class CzanglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(CzanglAction.class);
	
	@Autowired
	private CzanglService czanglService;
	@Autowired
	private XtcdglService xtcdglService;
	
	/**
	 * 首页数据查询
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param metName 按钮名称
	 * @param menuCode 菜单名称
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, Integer frameHeight, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String metName, String menuCode, String cdCode){
		try {
			model.addAttribute("frameHeight", frameHeight);
			
			log.info("metName==============" + metName);
			// 查询所有菜单
			List<SysMenu> allMenus = xtcdglService.findAllMenu();
			model.addAttribute("allMenus", allMenus);
			
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
//				model.addAttribute("headTitle",null);
//				model.addAttribute("cdCode",cdCode);
				model.addAttribute("menuCode", null);
			}else{
				// 分页查询角色信息
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("metName", metName);
				paramsMap.put("menuCode", menuCode);
				
				Pagenate<SysMethod> pagenate = czanglService.findMetByPage(pageNumber, pageSize, paramsMap);
//				SysMenu s = xtcdglService.findById(cdCode);//根据code查询菜单信息
//				model.addAttribute("headTitle",s.getMenuName());
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("metName", metName);
				model.addAttribute("menuCode", menuCode);
				return "xtgl/czangl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询系统菜单信息出错", e);
		}
		
		return "xtgl/czangl/index";
	}
	
	/**
	 * 显示添加、编辑按钮页面
	 * @param metCode 按钮编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/showAddEdit")
	public String showAddEdit(String metCode, Model model){
		log.info("按钮编码metCode=======" + metCode);
		SysMethod sysMethod = null;
		if(metCode != null && !metCode.equals("")){
			sysMethod = czanglService.findById(metCode);
		}
		
		List<SysMenu> allMenu = xtcdglService.findAllMenu();
		
		model.addAttribute("sysMethod", sysMethod);
		model.addAttribute("allMenu", allMenu);
		model.addAttribute("icons", sysMethod != null && sysMethod.getIcoUrl() != null && !sysMethod.getIcoUrl().equals("") ? sysMethod.getIcoUrl().split(" ")[1] : "");
		
		return "xtgl/czangl/addEdit";
	}
	
	/**
	 * 校验是按钮编码否存在
	 * @param methodCode 按钮编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCode")
	public String checkCode(String methodCode){
		log.info("methodCode=======" + methodCode);
		SysMethod sysMethod = czanglService.findById(methodCode);
		return sysMethod == null ? "{\"valid\":\"true\"}" : "{\"valid\":\"false\"}";
	}
	
	/**
	 * 保存、更新操作按钮
	 * @param sysMenu
	 * @param methodCodeOld 原按钮编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysMethod sysMethod, String methodCodeOld){
		try {
			if(methodCodeOld != null && !methodCodeOld.equals("")){
				// 更新
				sysMethod.setMethodCode(methodCodeOld);
			}
			czanglService.doSave(sysMethod);
			return "succ";
		} catch (Exception e) {
			log.error("操作按钮保存出错", e);
		}
		return "fail";
	}
	
	/**
	 * 删除操作按钮
	 * @param metCode 按钮编码
	 * @param menuCode 按钮所属菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDel")
	public String doDel(String metCode, String menuCode){
		try {
			// 查询是否存在使用此按钮的角色
			List<String> list = czanglService.findRoleByMenuMethod(menuCode, metCode);
			if(!list.isEmpty()){
				return "1";
			}
			
			// 删除操作按钮
			czanglService.delById(metCode);
			
			return "succ";
		} catch (Exception e) {
			log.error("操作按钮删除出错", e);
		}
		return "fail";
	}
	
}
