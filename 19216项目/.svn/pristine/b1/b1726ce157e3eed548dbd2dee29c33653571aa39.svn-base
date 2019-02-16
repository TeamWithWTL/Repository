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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;

/**
 * @author 高帅--2017年12月15日 App--社会管理--网格管理
 */
@Controller
@RequestMapping("/app/shgl/wggl")
public class AppWgglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppWgglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private LyglService lyglService;

	/**
	 * App--首页数据
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String serId,
			String commId, HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);
		log.info("所属社区Id>>>>>>>" + commId);
		log.info("所属服务站Id>>>>>>>" + serId);
		
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
			if (null == ajaxCmd) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();// 当前用户负责的社区Id
					map.put("dqCommId", dqCommId);
				}
				if ("fuwuzhan".equals(isAdmin)) {
					String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
					map.put("dqSsId", dqSsId);
				}
				if ("wgy".equals(isAdmin)) {
					String dpgridId = acc.getGridId();// 当前用户负责的网格Id
					map.put("dqgridId", dpgridId);
				}
				if ("admin".equals(isAdmin)) {// 最高权限查所有
					map.put("dqCommId", null);
					map.put("dqSsId", null);
				}
				map.put("name", name);
				map.put("commId", commId);
				map.put("serId", serId);
				Pagenate<ShglGridEntity> pagenate = wgglService.findByPage1(pageNumber, pageSize, map);
				
				List<ShglGridEntity> gridList = pagenate.getList();
				Map<String, String> addMap = new HashMap<String, String>();
				Map<String, String> addMapCnt = new HashMap<String, String>();
				List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
				
				
				if(gridList.size()>0){
					for(ShglGridEntity gridEntity : gridList){
						int hsCount = 0;
						String wgId = gridEntity.getId();
						addMap.put("wgId", wgId);
						buildList = lyglService.getBuildList(addMap);
						if(buildList.size()>0){
							for(ShglBuildingEntity buildingEntity : buildList){
								// 楼宇Id
								String buildId = buildingEntity.getId();
								addMapCnt.put("buildId", buildId);
								// 该楼宇单元数
								String unitCnt = buildingEntity.getUnit_cnt();
								for(int i=0; i<Integer.parseInt(unitCnt); i++){
									addMapCnt.put("unitCnt", String.valueOf(i + 1));
									hsCount += jmxxService.findLyHsCount(addMapCnt);
								}
							}
						}
						gridEntity.setSjCount(String.valueOf(hsCount));
					}
				}
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "shgl/wggl/app/app_wggl_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppWgglAction]_[index]_查询网格信息出错", e);
		}
		return "shgl/wggl/app/app_wggl_index";
	}

	/**
	 * app--服务站管理--mui_获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/wgglNextContent")
	public Map wgglNextContent(Model model, Integer pageNumber, String name, String commId, String serId,
			HttpSession session, HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);
		log.info("社区Id>>>>>>>" + commId);
		log.info("服务站Id>>>>>>>" + serId);

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
		if ("fuwuzhan".equals(isAdmin)) {
			String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
			map.put("dqSsId", dqSsId);
		}
		if ("admin".equals(isAdmin)) {// 最高权限查所有
			map.put("dqCommId", null);
			map.put("dqSsId", null);
		}
		map.put("name", name);
		map.put("commId", commId);
		map.put("serId", serId);
		Pagenate<ShglGridEntity> pagenate = wgglService.findByPage1(pageNumber, pageSize, map);
		
		List<ShglGridEntity> gridList = pagenate.getList();
		Map<String, String> addTsMap = new HashMap<String, String>();
		Map<String, String> addMapCnt = new HashMap<String, String>();
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
		
		
		if(gridList.size()>0){
			for(ShglGridEntity gridEntity : gridList){
				int hsCount = 0;
				String wgId = gridEntity.getId();
				addTsMap.put("wgId", wgId);
				buildList = lyglService.getBuildList(addTsMap);
				if(buildList.size()>0){
					for(ShglBuildingEntity buildingEntity : buildList){
						// 楼宇Id
						String buildId = buildingEntity.getId();
						addMapCnt.put("buildId", buildId);
						// 该楼宇单元数
						String unitCnt = buildingEntity.getUnit_cnt();
						for(int i=0; i<Integer.parseInt(unitCnt); i++){
							addMapCnt.put("unitCnt", String.valueOf(i + 1));
							hsCount += jmxxService.findLyHsCount(addMapCnt);
						}
					}
				}
				gridEntity.setSjCount(String.valueOf(hsCount));
			}
		}
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());

		List<Map> addList = new ArrayList<Map>();// 存放Map
		List<ShglGridEntity> wgLists = pagenate.getList();

		for (ShglGridEntity wgEntity : wgLists) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			addMap.put("wgId", wgEntity.getId());// 网格Id
			addMap.put("wgName", wgEntity.getName());// 网格名称
			addMap.put("sqName", wgEntity.getServiceStation().getCommunity().getName());// 所属社区名称
			addMap.put("fwzName", wgEntity.getServiceStation().getName());// 所属服务站名称
			addMap.put("sjCount", wgEntity.getSjCount());// 实际入住户数
			addMap.put("addName", wgEntity.getAdd_name());// 添加人
			addMap.put("addTime", wgEntity.getAddTimeFrm());// 添加时间
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

		log.info("查看_网格ID>>>>>>>" + id);

		ShglGridEntity grid = null;
		// 负责人第一条数据
		ShglGridManagerEntity gmanager = null;
		List<ShglGridManagerEntity> gmList = new ArrayList<ShglGridManagerEntity>();
		Map<String, String> addTsMap = new HashMap<String, String>();
		Map<String, String> addMapCnt = new HashMap<String, String>();
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
		
		if (null != id && !"".equals(id)) {
			grid = wgglService.findById1(id);
			for (int i = 0; i < grid.getGridList().size(); i++) {
				if (i == 0) {
					gmanager = grid.getGridList().get(0);
				} else {
					gmList.add(grid.getGridList().get(i));
				}
			}
		}
		if(null != grid){
			int hsCount = 0;
			String wgId = grid.getId();
			addTsMap.put("wgId", wgId);
			buildList = lyglService.getBuildList(addTsMap);
			if(buildList.size()>0){
				for(ShglBuildingEntity buildingEntity : buildList){
					// 楼宇Id
					String buildId = buildingEntity.getId();
					addMapCnt.put("buildId", buildId);
					// 该楼宇单元数
					String unitCnt = buildingEntity.getUnit_cnt();
					for(int i=0; i<Integer.parseInt(unitCnt); i++){
						addMapCnt.put("unitCnt", String.valueOf(i + 1));
						hsCount += jmxxService.findLyHsCount(addMapCnt);
					}
				}
			}
			grid.setSjCount(String.valueOf(hsCount));
		}else{
			grid.setSjCount(String.valueOf(0));
		}
		model.addAttribute("grid", grid);
		model.addAttribute("gmanager", gmanager);
		model.addAttribute("gmList", gmList);
		return "shgl/wggl/app/app_wggl_view";
	}

	/**
	 * 获取服务站列表
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getComData", produces = "text/html;charset=UTF-8")
	public String getComData(String comId, HttpServletRequest request) {
		
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		// 判断用户权限
		ArrayList<String> arrayList = new ArrayList<String>();// 用户角色，保存list
		if (roleCode.indexOf(",") != -1) {
			String[] roleCodes = roleCode.split(",");// 当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		} else {
			arrayList.add(roleCode);// 单角色直接添加
		}

		Collections.sort(arrayList); // 排序保证高权限不会被低权限覆盖
		String isAdmin = "no";
		if ("04".equals(arrayList.get(arrayList.size() - 1)) || "05".equals(arrayList.get(arrayList.size() - 1))
				|| "06".equals(arrayList.get(arrayList.size() - 1)) || "07".equals(arrayList.get(arrayList.size() - 1))
				|| "08".equals(arrayList.get(arrayList.size() - 1)) || "09".equals(arrayList.get(arrayList.size() - 1))
				|| "10".equals(arrayList.get(arrayList.size() - 1)) || "12".equals(arrayList.get(arrayList.size() - 1))
				|| "13".equals(arrayList.get(arrayList.size() - 1))
				|| "99".equals(arrayList.get(arrayList.size() - 1))) {
			isAdmin = "admin";// 社区管理员以上的
		} else {
			if ("03".equals(arrayList.get(arrayList.size() - 1))) {
				isAdmin = "shequ";// 社区管理员
			} else {
				if ("02".equals(arrayList.get(arrayList.size() - 1))) {
					isAdmin = "fuwuzhan";// 服务站管理员
				} else {
					if ("01".equals(arrayList.get(arrayList.size() - 1))) {
						isAdmin = "wgy";// 网格管理员
					}
				}
			}
		}
		List<ShglServiceStationEntity> ssList = new ArrayList<ShglServiceStationEntity>();
		if (null != comId && !"".equals(comId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("commId", comId);
			if ("fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
				paramMap.put("dqSsId", acc.getSsId());// 下拉菜单只显示当前用户负责的服务站
			}
			ssList = fwzglService.findAllCom1(paramMap);
		}
		String returnJson = JSONArray.fromObject(ssList).toString();
		return returnJson;
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