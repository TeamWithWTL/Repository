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
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author 高帅--2017年12月15日 App--社区管理
 */
@Controller
@RequestMapping("app/shgl/sqgl")
public class AppSqglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppSqglAction.class);

	@Autowired
	private SqglService sqglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private LyglService lyglService;

	/**
	 * app--社区列表展示
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name,
			HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);

		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
				String roleCode = acc.getRole_code();
				
				// 判断用户权限
				String isAdmin = jurisdiction(roleCode);
				model.addAttribute("roleCode", acc.getRole_code());
				
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
				Pagenate<ShglCommunityEntity> pagenate = sqglService.findByPage1(pageNumber, pageSize, map);
				
				Map<String, String> addMap = new HashMap<String, String>();
				Map<String, String> addMapCnt = new HashMap<String, String>();
				List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();

				if (pagenate.getList() != null) {
					for (ShglCommunityEntity community : pagenate.getList()) {
						List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
						String flag = "0";
						for (SysParamDesc sysParamDesc : xzqh) {
							if (sysParamDesc.getItemCode().equals(community.getStrative_id())) {
								community.setStrative_name(sysParamDesc.getItemName());
								flag = "1";
								break;
							}
						}
						if ("0".equals(flag)) {
							community.setStrative_name("");
						}
						int hsCount = 0;
						String sqId = community.getId();
						addMap.put("sqId", sqId);
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
						community.setSjCount(String.valueOf(hsCount));
					}
				}
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "shgl/sqgl/app/app_index_sqgl#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppSqglAction]_[index]_app查询社区管理信息出错", e);
		}
		return "shgl/sqgl/app/app_index_sqgl";
	}

	/**
	 * app--社区活动--mui_获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/sqglNextContent")
	public Map sqglNextContent(Model model, Integer pageNumber, String name, HttpSession session,
			HttpServletRequest req) {

		log.info("模糊查询字段>>>>>>>" + name);

		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();

		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);
		model.addAttribute("roleCode", acc.getRole_code());

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
			map.put("dpgridId", dpgridId);
		}
		if ("admin".equals(isAdmin)) {// 最高权限查所有
			map.put("dqCommId", null);
			map.put("dqSsId", null);
		}
		map.put("name", name);
		Pagenate<ShglCommunityEntity> pagenate = sqglService.findByPage1(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		Map<String, String> addTjMap = new HashMap<String, String>();
		Map<String, String> addMapCnt = new HashMap<String, String>();
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();

		if (pagenate.getList() != null) {
			for (ShglCommunityEntity community : pagenate.getList()) {
				List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
				String flag = "0";
				for (SysParamDesc sysParamDesc : xzqh) {
					if (sysParamDesc.getItemCode().equals(community.getStrative_id())) {
						community.setStrative_name(sysParamDesc.getItemName());
						flag = "1";
						break;
					}
				}
				if ("0".equals(flag)) {
					community.setStrative_name("");
				}
				int hsCount = 0;
				String sqId = community.getId();
				addTjMap.put("sqId", sqId);
				buildList = lyglService.getBuildList(addTjMap);
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
				community.setSjCount(String.valueOf(hsCount));
			}
		}

		List<Map> addList = new ArrayList<Map>();// 存放Map
		List<ShglCommunityEntity> sqLists = pagenate.getList();

		for (ShglCommunityEntity sqEntity : sqLists) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			addMap.put("sqId", sqEntity.getId());// 社区Id
			addMap.put("sqName", sqEntity.getName());// 社区名称
			addMap.put("xzqh", sqEntity.getStrative_name());// 行政区划
			addMap.put("sjCount", sqEntity.getSjCount());// 实际入住户数
			addMap.put("addName", sqEntity.getAdd_name());// 添加人
			addMap.put("addTime", sqEntity.getAddTimeFrm());// 添加时间
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

		log.info("查看_社区ID>>>>>>>" + id);

		ShglCommunityEntity community = null;
		// 存放第一条固定负责人数据
		ShglCmanagerEntity cmanager = null;
		// 存除第一条数据外的其他数据
		List<ShglCmanagerEntity> cmanagerList = new ArrayList<ShglCmanagerEntity>();
		Map<String, String> addMap = new HashMap<String, String>();
		Map<String, String> addMapCnt = new HashMap<String, String>();
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
		
		if (null != id && !"".equals(id)) {
			community = sqglService.findById1(id);
			for (int i = 0; i < community.getCmanagerList().size(); i++) {
				if (i == 0) {
					cmanager = community.getCmanagerList().get(0);
				} else {
					cmanagerList.add(community.getCmanagerList().get(i));
				}
			}
		}
		List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
		String flag = "0";
		for (SysParamDesc sysParamDesc : xzqh) {
			if (sysParamDesc.getItemCode().equals(community.getStrative_id())) {
				community.setStrative_name(sysParamDesc.getItemName());
				flag = "1";
				break;
			}
		}
		if ("0".equals(flag)) {
			community.setStrative_name("");
		}
		if(null != community){
			int hsCount = 0;
			String sqId = community.getId();
			addMap.put("sqId", sqId);
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
			community.setSjCount(String.valueOf(hsCount));
		}else{
			community.setSjCount(String.valueOf(0));
		}
		model.addAttribute("community", community);
		model.addAttribute("cmanager", cmanager);
		model.addAttribute("cmanagerList", cmanagerList);
		return "shgl/sqgl/app/app_sqgl_view";
	}

	/**
	 * 权限控制抽取方法
	 * 
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
