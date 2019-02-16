package com.jcwx.action.shgl.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author 高帅--2017年12月15日 App--社会管理--小区管理
 */
@Controller
@RequestMapping("/app/shgl/xqxx")
public class AppXqxxAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppXqxxAction.class);

	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private SqglService sqglService;

	/**
	 * app--首页数据
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);
		log.info("所属社区Id>>>>>>>" + commId);
		log.info("所属服务站Id>>>>>>>" + ssId);
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();

		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin) || "fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());// 社区下拉列表只显示当前用户负责的社区
		} else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);// 社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);

		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();// 当前用户负责的社区Id
					map.put("dqCommId", dqCommId);
				}
				
				// App--网格、小区同级如何处理，暂时查看所有的小区（高帅-2017年12月19日）
				if ("fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
					String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
					map.put("dqSsId", dqSsId);
				}
				if ("admin".equals(isAdmin)) {// 最高权限查所有
					map.put("dqCommId", null);
					map.put("dqSsId", null);
				}
				map.put("name", name);
				map.put("ssId", ssId);
				map.put("commId", commId);
				Pagenate<ShglVillageEntity> pagenate = xqxxService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());

				return "shgl/xqxx/app/app_xqxx_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppXqxxAction]_[index]_查询小区信息出错", e);
		}
		return "shgl/xqxx/app/app_xqxx_index";
	}

	/**
	 * app--小区信息管理--mui_获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/xqxxNextContent")
	public Map xqxxNextContent(Model model, Integer pageNumber, String name, String commId, String ssId,
			HttpSession session, HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);
		log.info("社区Id>>>>>>>" + commId);
		log.info("服务站Id>>>>>>>" + ssId);

		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();

		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);

		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin) || "fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());// 社区下拉列表只显示当前用户负责的社区
		} else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);// 社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);

		// 分页查询用户信息
		Map<String, String> map = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)) {
			String dqCommId = acc.getCommId();// 当前用户负责的社区Id
			map.put("dqCommId", dqCommId);
		}
		if ("fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
			String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
			map.put("dqSsId", dqSsId);
		}
		if ("admin".equals(isAdmin)) {// 最高权限查所有
			map.put("dqCommId", null);
			map.put("dqSsId", null);
		}
		map.put("name", name);
		map.put("ssId", ssId);
		map.put("commId", commId);
		Pagenate<ShglVillageEntity> pagenate = xqxxService.findByPage1(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());

		List<Map> addList = new ArrayList<Map>();// 存放Map
		List<ShglVillageEntity> xqxxLists = pagenate.getList();

		for (ShglVillageEntity xqxxEntity : xqxxLists) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			addMap.put("xqxxId", xqxxEntity.getId());// 小区Id
			addMap.put("xqxxName", xqxxEntity.getName());// 小区名称
			addMap.put("sqName", xqxxEntity.getServiceStation().getCommunity().getName());// 所属社区名称
			addMap.put("fwzName", xqxxEntity.getServiceStation().getName());// 所属服务站名称
			addMap.put("addName", xqxxEntity.getAdd_name());// 添加人
			addMap.put("addTime", xqxxEntity.getAddTimeFrm());// 添加时间
			addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}

	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/goView")
	public String goView(Model model, String id) {

		log.info("查看_小区ID>>>>>>>" + id);

		ShglVillageEntity village = null;
		ShglVmanagerEntity vmanager = null;
		List<ShglVmanagerEntity> vList = new ArrayList<ShglVmanagerEntity>();
		if (id != null && !"".equals(id)) {
			village = xqxxService.findById1(id);
			for (int i = 0; i < village.getVmanList().size(); i++) {
				if (i == 0) {
					vmanager = village.getVmanList().get(0);
				} else {
					vList.add(village.getVmanList().get(i));
				}
			}
		}
		model.addAttribute("village", village);
		model.addAttribute("vmanager", vmanager);
		model.addAttribute("vList", vList);
		return "shgl/xqxx/app/app_xqxx_view";
	}

	/**
	 * 权限控制抽取方法
	 * @author 李伟
	 * @time 2017年12月12日下午3:41:49
	 * @param roleCode
	 */
	public String jurisdiction(String roleCode) {

		// 用户角色，保存list
		ArrayList<String> arrayList = new ArrayList<String>();
		if (roleCode.indexOf(",") != -1) {
			String[] roleCodes = roleCode.split(",");// 当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		} else {
			arrayList.add(roleCode);// 单角色直接添加
		}
		Collections.sort(arrayList); // 排序保证高权限不会被低权限覆盖
		String isAdmin = "no";
		for (String role : arrayList) {
			if (Consts.ROLE_JDXXY.equals(role) || Consts.ROLE_JDGLY.equals(role) || Consts.ROLE_DZBGZRY.equals(role)
					|| Consts.ROLE_YWBMLD.equals(role) || Consts.ROLE_YWBM.equals(role)
					|| Consts.ROLE_SHZZGLY.equals(role) || Consts.ROLE_JDBDFLZGLRY.equals(role)
					|| Consts.ROLE_JDBLD.equals(role) || Consts.ROLE_BMCY.equals(role)
					|| Consts.ROLE_ADMIN.equals(role)) {
				isAdmin = "admin";// 社区管理员以上的
			} else {
				if (Consts.ROLE_SQGLY.equals(arrayList.get(arrayList.size() - 1))) {
					isAdmin = "shequ";// 社区管理员
				} else if (Consts.ROLE_FWZGLY.equals(arrayList.get(arrayList.size() - 1))) {
					isAdmin = "fuwuzhan";// 服务站管理员
				} else {
					if (Consts.ROLE_WGY.equals(arrayList.get(arrayList.size() - 1))) {
						isAdmin = "wgy";// 网格员
					}
				}
			}
		}
		return isAdmin;
	}

}
