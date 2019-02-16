package com.jcwx.action.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.xtgl.XtcdglService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.StringUtil;

/**
 * @author MaBo 2017年5月24日 框架页面
 */
@Controller
@RequestMapping("/")
public class MainAction {

	private Logger logger = Logger.getLogger(MainAction.class);
	private String pwd = ""; // 新密码
	@Autowired
	private RwclService rwclService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private XtcdglService xtcdglService;

	@Autowired
	private EventService eventService;
	@Autowired
	private RwglService rwglService;
	/**
	 * 框架页面显示
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	public String index() {
		return "main";
	}

	/**
	 * 校验是否存在二级菜单
	 * 
	 * @param menuCode
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/checkSecond")
	public String checkSecond(String menuCode, HttpSession session) {
		logger.info("菜单编号==============" + menuCode);
		Map<String, List<SysMenu>> secondMenus = (Map<String, List<SysMenu>>) session.getAttribute("secondMenus");
		return secondMenus.get(menuCode) == null ? "0" : "1";
	}
	

	/**
	 * 显示二级菜单页面
	 * 
	 * @param menuCode
	 *            一级菜单编号
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/secondMain")
	public String secondMain(String menuCode, HttpSession session, Model model,String menuType) {
		// 查询一级菜单信息
		SysMenu sysMenu = xtcdglService.findById(menuCode);

		// 获取当前用户的二级菜单
		Map<String, List<SysMenu>> secondMenus = (Map<String, List<SysMenu>>) session.getAttribute("secondMenus");
		List<SysMenu> secondMenuList = secondMenus.get(menuCode);
		Map<String, List<SysMenu>> thirdMenus = (Map<String, List<SysMenu>>) session.getAttribute("thirdMenus");
		for (SysMenu menu : secondMenus.get(menuCode)) {
			String menuCodeS = menu.getMenuCode();
			if (thirdMenus.get(menuCodeS) != null && !thirdMenus.get(menuCodeS).isEmpty()) {
				List<SysMenu> thirdMenuList = thirdMenus.get(menuCodeS);
				menu.setThirdMenus(thirdMenuList);
			}
		}
		String url = "";
		if (secondMenuList.get(0).getThirdMenus() != null) {
			url = secondMenuList.get(0).getThirdMenus().get(0).getUrl(); // 页面首次需要加载的URL
		} else {
			url= secondMenuList.get(0).getUrl(); // 页面首次需要加载的URL
		}
		if("task".equals(menuType)){
			url = "shgl/rwgl/myReceive.do";
		}
		model.addAttribute("loadUrl", url); // 页面首次需要加载的URL	
		logger.info("secondMenuList=======" + secondMenuList);
		model.addAttribute("menuType", menuType);
		model.addAttribute("secondMenuList", secondMenuList);
		model.addAttribute("firstMenuName", sysMenu.getMenuName());
		return "secondMain";
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goExit")
	public String logout(HttpSession session) {
		session.setAttribute("sysAccCount", null);
		session.setAttribute("firstMenus", null);
		session.setAttribute("secondMenus", null);
		session.setAttribute("thirdMenus", null);
		session.setAttribute("loadUrl", null);
		session.invalidate();
		return "succ";
	}

	/**
	 * 跳转到修改密码界面
	 */
	@RequestMapping("/goUpdatepwd")
	public String updatepwd(HttpSession session, Model model) {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		SysAccCountLazy sysacc = yhglService.findByCode(sysAccCount.getAccCode());
		model.addAttribute("sysacc", sysacc);
		return "update_pwd";
	}

	/**
	 * 保存新密码
	 */
	@RequestMapping("/doUpdatepwd")
	@ResponseBody
	public Object doUpdatepw(String pwd, HttpSession session) {
		try {
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			String acccode = sysAccCount.getAccCode();// 登录人的账户
			SysAccCountLazy a = yhglService.findByCode(acccode);
			String p2 = StringUtil.toMD5(pwd);// 修改密码页面---新密码
			a.setPwd(p2);
			yhglService.updateAcc(a);
			return "success";
		} catch (Exception e) {
			logger.info("修改密码信息出错", e);
		}
		return "error";
	}
	
	/**
	 * 保存新头像
	 */
	@RequestMapping("/doUpdatePic")
	@ResponseBody
	public Object doUpdatePic(HttpSession session,String picePath) {
		try {
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			String acccode = sysAccCount.getAccCode();// 登录人的账户
			SysAccCountLazy a = yhglService.findByCode(acccode);
			a.setPic_path(picePath);
			yhglService.updateAcc(a);
			return "success";
		} catch (Exception e) {
			logger.info("上传头像出错", e);
		}
		return "error";
	}

	/**
	 * 验证原密码是否存在
	 * 
	 * @param password
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPwd")
	public String checkPwd(String password, HttpSession session) {
		logger.info("password=======" + password);
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		SysAccCountLazy sysacc = yhglService.findByCode(sysAccCount.getAccCode());
		String pass = sysacc.getPwd();// 登录人的密码
		String passNews = StringUtil.toMD5(password).toUpperCase();
		if (passNews.equals(pass)) {// 原密码相同
			return "{\"valid\":\"true\"}";
		} else {// 原密码不同
			return "{\"valid\":\"false\"}";
		}

	}

	/**
	 * 验证新，原密码是否相同
	 * 
	 * @param pwd
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPwd1")
	public String checkPwd1(String pwd, HttpSession session) {
		logger.info("password=======" + pwd);
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		String pass = sysAccCount.getPwd();// 登录人的密码
		if (StringUtil.toMD5(pwd).toLowerCase().equals(pass)) {// 新密码与原密码相同
			return "{\"valid\":\"false\"}";
		} else {// 新密码不同
			this.pwd = pwd;
			return "{\"valid\":\"true\"}";
		}

	}

	/**
	 * 验证新，确认密码是否相同
	 * 
	 * @param pwd1
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPwd2")
	public String checkPwd2(String pwd1, HttpSession session) {
		logger.info("pwd=======" + pwd);
		logger.info("pwd1=======" + pwd1);
		if (!pwd.equals(pwd1)) {// 新密码与确认密码不相同
			return "{\"valid\":\"false\"}";
		} else {// 新密码不同
			return "{\"valid\":\"true\"}";
		}

	}

	/**
	 * 显示修改个人信息页面
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public String update(HttpSession session, Model model) {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		SysAccCountLazy sysacc = yhglService.findByCode(sysAccCount.getAccCode());
		model.addAttribute("sysacc", sysacc);
		return "update_myInfo";
	}

	/**
	 * 修改
	 * 
	 * @param sdesc
	 *            用户详情
	 * @param code
	 *            用户账号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysRole role, SysAccCount sdesc, String code, SysAccCountLazy sacc) {
		try {
//			SysAccCount sysaccdesc = yhglService.findDescByCode(code);
//			BeanUtils.copyProperties(sdesc, sysaccdesc, new String[] { "accCode", "isLeader" });
//			yhglService.updateAccDesc(sysaccdesc);// 修改用户信息详细
			SysAccCountLazy sysacc = yhglService.findByCode(code);
			BeanUtils.copyProperties(sacc, sysacc, new String[] { "accCode", "pwd", "sysRole", "sysDate", "sysAccdesc",
					"appSerial", "validFlag", "partyProup", "token_id", "partyFee" });
			yhglService.updateAcc(sysacc);// 保存用户信息
			return "success";

		} catch (Exception e) {
			logger.info("添加用户信息出错", e);
		}
		return "error";
	}

	
	
	
	
	/**
	 *  查询待办任务
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/schedule")
	public Map<String, String> schedule( HttpSession session,HttpServletRequest req,Model model) {
		Map<String,String> hMap =  new HashMap<String, String>();
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		String acc_code = acc.getAccCode();
		// 分页查询用户信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("commId", acc.getCommId());
		map.put("ssId", acc.getSsId());
		if(roleCode.indexOf(Consts.ROLE_FWZGLY)>-1||roleCode.indexOf(Consts.ROLE_SQGLY)>-1||roleCode.indexOf(Consts.ROLE_JDXXY)>-1){
			map.put("roleCode", roleCode);
		}else{
			map.put("clrId", acc_code);
		}
		int eventcount = eventService.myEventCount(map);
		
		Map<String, String> addMap = new HashMap<String, String>();
		addMap.put("accCode", acc_code);
		addMap.put("stamp", "1");//加入查询标记  1为未处理的任务 
		int rwcount = rwclService.findNoDealRwCount(addMap);
		hMap.put("rwcount", String.valueOf(rwcount));
		hMap.put("eventCount", String.valueOf(eventcount));
		
		return hMap;
	}
}
