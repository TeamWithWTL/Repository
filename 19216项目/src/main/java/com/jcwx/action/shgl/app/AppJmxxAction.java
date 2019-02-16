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
import org.springframework.beans.BeanUtils;
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
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author 高帅--（2017年12月18日） App--社区管理--基础信息
 */
@Controller
@RequestMapping("/app/shgl/jmxx")
public class AppJmxxAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppJmxxAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private SjzdService sjzdService;

	/**
	 * app--首页数据
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, String gridId, String buildId, String xqId, String roomNo, String unitNo, HttpServletRequest req) {

		log.info("模糊查询名称>>>>>>>" + name);
		log.info("所属社区Id>>>>>>>" + commId);
		log.info("所属服务站Id>>>>>>>" + ssId);
		log.info("所属网格Id>>>>>>>" + gridId);
		log.info("所属楼宇Id>>>>>>>" + buildId);
		log.info("所属房间Id>>>>>>>" + roomNo);
		log.info("所属单元Id>>>>>>>" + unitNo);
		log.info("所属小区Id>>>>>>>" + xqId);
		
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
				model.addAttribute("pageCnts", null);
				model.addAttribute("pageSize", null);
			} else {
				// 分页查询
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
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("gridId", gridId);
				map.put("buildId", buildId);
				map.put("roomNo", roomNo);
				map.put("unitNo", unitNo);
				map.put("xqId", xqId);
				Pagenate<ShglInmateEntity> pagenate = jmxxService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());

				List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族
				model.addAttribute("sysStations", sysStations);

				return "shgl/jmxx/app/app_jmxx_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppJmxxAction]_[index]_查询居民信息出错", e);
		}
		return "shgl/jmxx/app/app_jmxx_index";
	}

	/**
	 * app--服务站管理--mui_获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/jmxxNextContent")
	public Map jmxxNextContent(Model model, Integer pageNumber, String name, String commId, String serId, String gridId,
			HttpSession session, String buildId, String xqId, String roomNo, String unitNo, HttpServletRequest req) {
 
		log.info("模糊查询名称>>>>>>>" + name);
		log.info("所属社区Id>>>>>>>" + commId);
		log.info("所属服务站Id>>>>>>>" + serId);
		log.info("所属网格Id>>>>>>>" + gridId);
		log.info("所属楼宇Id>>>>>>>" + buildId);
		log.info("所属房间Id>>>>>>>" + roomNo);
		log.info("所属单元Id>>>>>>>" + unitNo);
		log.info("所属小区Id>>>>>>>" + xqId);

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

		// 分页查询
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
		map.put("ssId", serId);
		map.put("commId", commId);
		map.put("gridId", gridId);
		map.put("buildId", buildId);
		map.put("roomNo", roomNo);
		map.put("unitNo", unitNo);
		map.put("xqId", xqId);
		Pagenate<ShglInmateEntity> pagenate = jmxxService.findByPage1(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());

		List<Map> addList = new ArrayList<Map>();// 存放Map
		List<ShglInmateEntity> jmxxLists = pagenate.getList();

		for (ShglInmateEntity jmxxEntity : jmxxLists) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			addMap.put("id", jmxxEntity.getId());// 居民id
			addMap.put("jmxxName", jmxxEntity.getName());// 姓名
			addMap.put("addName", jmxxEntity.getSex());// 性别
			addMap.put("addTime", jmxxEntity.getBirthdayFrm());// 出生日期
			addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}

	/**
	 * 跳转添加及修改界面
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id, String flag, String agoId, String lyflag, String lyId,
			String unitNo, String roomNo, HttpServletRequest request) {

		SysAccCount sysAcc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String roleCode = sysAcc.getRole_code();

		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);

		ShglInmateEntity inmateEntity = null;
		String commId = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin) || "fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
			paramMap.put("dqCommId", sysAcc.getCommId());// 社区下拉列表只显示当前用户负责的社区
		} else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);// 社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);

		String types = "";
		if (null != id && !"".equals(id)) {
			// 查询居民信息
			inmateEntity = jmxxService.findById1(id);
			commId = inmateEntity.getBuild().getVillage().getServiceStation().getCommunity().getId();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);
			// 服务站列表
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);
			model.addAttribute("commId", commId);
			model.addAttribute("ssList", ssList);

			Map<String, String> paramMap3 = new HashMap<String, String>();
			paramMap3.put("ssId", inmateEntity.getBuild().getVillage().getServiceStation().getId());
			// 网格列表
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap3);
			model.addAttribute("gridList", gridList);

			Map<String, String> paramMap4 = new HashMap<String, String>();
			if (null == inmateEntity.getBuild().getGrid() || "".equals(inmateEntity.getBuild().getGrid())) {
				paramMap4.put("gridId", null);
			} else {
				paramMap4.put("gridId", inmateEntity.getBuild().getGrid().getId());
			}
			// 楼宇列表
			List<ShglBuildingEntity> buildList = lyglService.findAllBuilds1(paramMap4);
			model.addAttribute("buildList", buildList);
			// 单元列表
			List<String> unitCnts = new ArrayList<String>();
			// 房间列表
			List<String> rooms = new ArrayList<String>();
			for (int i = 1; i <= Integer.parseInt(inmateEntity.getBuild().getUnit_cnt()); i++) {
				unitCnts.add(String.valueOf(i));
			}
			for (int y = 1; y <= Integer.parseInt(inmateEntity.getBuild().getFloor_cnt()); y++) {
				for (int j = 1; j <= Integer.parseInt(inmateEntity.getBuild().getFamily_cnt()); j++) {
					if (j < 10) {
						rooms.add(y + "0" + j);
					} else {
						rooms.add(String.valueOf(y) + String.valueOf(j));
					}
				}
			}
			for (ShglInmateTypeEntity shglInmateTypeEntity : inmateEntity.getInmateTList()) {
				if ("".equals(types)) {
					types = shglInmateTypeEntity.getCategory() + ",";
				} else {
					types = types + shglInmateTypeEntity.getCategory() + ",";
				}
			}
			model.addAttribute("unitCnts", unitCnts);
			model.addAttribute("rooms", rooms);
		}
		// 政治面貌
		List<SysParamDesc> zzmm = sjzdService.findByCode("10001").getSysParamDesc();
		// 户主关系
		List<SysParamDesc> hzgx = sjzdService.findByCode("10002").getSysParamDesc();
		// 婚姻状况
		List<SysParamDesc> hyzk = sjzdService.findByCode("10003").getSysParamDesc();
		// 文化程度
		List<SysParamDesc> whcd = sjzdService.findByCode("10004").getSysParamDesc();
		// 人员分类
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();
		// 民族
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();
		model.addAttribute("zzmm", zzmm);
		model.addAttribute("hzgx", hzgx);
		model.addAttribute("hyzk", hyzk);
		model.addAttribute("whcd", whcd);
		model.addAttribute("ryfl", ryfl);
		model.addAttribute("inmateEntity", inmateEntity);
		model.addAttribute("sysStations", sysStations);
		model.addAttribute("types", types);
		model.addAttribute("flag", flag);
		model.addAttribute("agoId", agoId);//从居民列表传来的标识
		model.addAttribute("lyflag", lyflag);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("lyId", lyId);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("unitNo", unitNo);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("roomNo", roomNo);//从楼宇传过来的条件，只是携带标识
		// 查询所有是户主的人员
		List<ShglInmateEntity> hzList = jmxxService.findHz();
		model.addAttribute("hzList", hzList);
		return "shgl/jmxx/app/app_jmxx_add";
	}

	/**
	 * 添加、编辑 保存数据
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, ShglInmateEntity inmate, String commId, String ssId, String buildId,
			String unit_no, String room_no, String types) {

		log.info("所属社区Id>>>>>>>" + commId);
		log.info("所属服务站Id>>>>>>>" + ssId);
		log.info("所属楼宇Id>>>>>>>" + buildId);
		log.info("所属房间Id>>>>>>>" + room_no);
		log.info("所属单元Id>>>>>>>" + unit_no);
		log.info("人员分类>>>>>>>" + types);

		// 楼宇
		ShglBuildingEntity build = null;
		if (null != buildId && !"".equals(buildId)) {
			build = lyglService.findById1(buildId);
		}
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", inmate.getId());
			map.put("name", inmate.getName());
			map.put("buildId", buildId);
			map.put("room_no", room_no);
			map.put("unit_no", unit_no);
			List<ShglInmateEntity> inmateEntities = jmxxService.findAllInmates(map);
			if (inmateEntities.size() > 0) {
				return "exists";
			}
			ShglInmateEntity inmateOld = jmxxService.findById1(inmate.getId());
			if (null == inmateOld) {// 为null 则数据库中没有该数据

				inmate.setSsId(ssId);
				inmate.setBuild(build);
				inmate.setCommId(commId);
				inmate.setBirthday(DateUtils.parseDate(inmate.getBirthdayFrm(), "yyyy-MM-dd"));
				jmxxService.save1(inmate);// 保存

				String[] type1 = types.split(",");
				for (String tString : type1) {
					if ("".equals(tString) || null == tString) {
						continue;
					}
					ShglInmateTypeEntity s = new ShglInmateTypeEntity();
					s.setInmate(inmate);
					s.setCategory(tString);
					jmxxService.saveLx(s);
				}
			} else {
				BeanUtils.copyProperties(inmate, inmateOld, new String[] { "inmateTList" });

				inmateOld.setSsId(ssId);
				inmateOld.setBuild(build);
				inmateOld.setCommId(commId);
				inmateOld.setBirthday(DateUtils.parseDate(inmate.getBirthdayFrm(), "yyyy-MM-dd"));
				jmxxService.update1(inmateOld);// 修改
				/********** 删除之前的类型 ***********/
				List<ShglInmateTypeEntity> list = inmateOld.getInmateTList();
				for (ShglInmateTypeEntity shglInmateTypeEntity : list) {
					jmxxService.delLx(shglInmateTypeEntity.getId());
				}
				/********* 删除之前的类型 **********/
				String[] type1 = types.split(",");
				for (String tString : type1) {
					ShglInmateTypeEntity s = new ShglInmateTypeEntity();
					s.setInmate(inmateOld);
					s.setCategory(tString);
					jmxxService.saveLx(s);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("[AppJmxxAction]_[doSave]_添加居民信息出错", e);
		}
		return "error";
	}

	/**
	 * 跳转查看界面
	 */
	@RequestMapping("/goView")
	public String goView(Model model, String id, String lyflag, String lyId, String unitNo,
			String roomNo, HttpServletRequest req) {

		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		// 只有网格员可以修改居民信息
		if (roleCode.indexOf("01") != -1) {
			model.addAttribute("roleCodes", roleCode);
		} else {
			model.addAttribute("roleCodes", null);
		}

		// 查询居民信息
		ShglInmateEntity inmateEntity = jmxxService.findById1(id);
		model.addAttribute("inmateEntity", inmateEntity);
		model.addAttribute("lyId", lyId);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("unitNo", unitNo);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("roomNo", roomNo);//从楼宇传过来的条件，只是携带标识

		List<SysParamDesc> zzmm = sjzdService.findByCode("10001").getSysParamDesc();// 政治面貌
		List<SysParamDesc> hyzk = sjzdService.findByCode("10003").getSysParamDesc();// 婚姻状况
		List<SysParamDesc> whcd = sjzdService.findByCode("10004").getSysParamDesc();// 文化程度
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();// 人员分类
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族

		String types = "";
		for (ShglInmateTypeEntity shglInmateTypeEntity : inmateEntity.getInmateTList()) {
			if ("".equals(types)) {
				types = shglInmateTypeEntity.getCategory() + ",";
			} else {
				types = types + shglInmateTypeEntity.getCategory() + ",";
			}
		}
		model.addAttribute("ryfl", ryfl);
		model.addAttribute("zzmm", zzmm);
		model.addAttribute("hyzk", hyzk);
		model.addAttribute("whcd", whcd);
		model.addAttribute("sysStations", sysStations);
		model.addAttribute("types", types);
		// 地址查询家庭关系，同一房间号判定为一家人
		String commId = inmateEntity.getCommId();// 展示住户居住的社区id
		String ssId = inmateEntity.getSsId();// 展示住户居住的服务站id
		String buildId = inmateEntity.getBuild().getId();// 展示住户居住的楼宇id
		String villageId = inmateEntity.getBuild().getVillage().getId();// 展示住户居住的楼宇的小区id
		String gridId = inmateEntity.getBuild().getGrid().getId();// 展示住户居住的楼宇的网格id
		String unit_no = inmateEntity.getUnit_no();// 展示住户居住的单元号
		String room_no = inmateEntity.getRoom_no();// 展示住户居住的房间号
		String name = inmateEntity.getBuild().getName();// 展示住户居住的楼栋号

		Map<String, String> tiesMap = new HashMap<String, String>();
		tiesMap.put("commId", commId);
		tiesMap.put("ssId", ssId);
		tiesMap.put("build", buildId);
		tiesMap.put("villageId", villageId);
		tiesMap.put("unit_no", unit_no);
		tiesMap.put("room_no", room_no);
		tiesMap.put("gridId", gridId);
		tiesMap.put("name", name);
		List<ShglInmateEntity> familyList = jmxxService.findFamilyByHzId(tiesMap);// 地址查询家庭关系
		familyList.remove(inmateEntity);// 先移除自身，
		model.addAttribute("familyList", familyList);
		model.addAttribute("lyflag", lyflag);//从楼宇传过来的条件，只是携带标识
		return "shgl/jmxx/app/app_jmxx_view";
	}
	
	/**
	 * 跳转查看界面
	 */
	@RequestMapping("/goViewBycy")
	public String goViewBycy(Model model, String id, String agoId, String lyflag, String lyId, String unitNo,
			String roomNo, HttpServletRequest req) {

		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		// 只有网格员可以修改居民信息
		if (roleCode.indexOf("01") != -1) {
			model.addAttribute("roleCodes", roleCode);
		} else {
			model.addAttribute("roleCodes", null);
		}

		// 查询居民信息
		ShglInmateEntity inmateEntity = jmxxService.findById1(id);
		model.addAttribute("inmateEntity", inmateEntity);
		model.addAttribute("agoId", agoId);
		model.addAttribute("lyflag", lyflag);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("lyId", lyId);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("unitNo", unitNo);//从楼宇传过来的条件，只是携带标识
		model.addAttribute("roomNo", roomNo);//从楼宇传过来的条件，只是携带标识

		List<SysParamDesc> zzmm = sjzdService.findByCode("10001").getSysParamDesc();// 政治面貌
		List<SysParamDesc> hyzk = sjzdService.findByCode("10003").getSysParamDesc();// 婚姻状况
		List<SysParamDesc> whcd = sjzdService.findByCode("10004").getSysParamDesc();// 文化程度
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();// 人员分类
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族

		String types = "";
		for (ShglInmateTypeEntity shglInmateTypeEntity : inmateEntity.getInmateTList()) {
			if ("".equals(types)) {
				types = shglInmateTypeEntity.getCategory() + ",";
			} else {
				types = types + shglInmateTypeEntity.getCategory() + ",";
			}
		}
		model.addAttribute("ryfl", ryfl);
		model.addAttribute("zzmm", zzmm);
		model.addAttribute("hyzk", hyzk);
		model.addAttribute("whcd", whcd);
		model.addAttribute("sysStations", sysStations);
		model.addAttribute("types", types);
		
		return "shgl/jmxx/app/app_jmxx_viewforcy";
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
					|| Consts.ROLE_ADMIN.equals(role)
					|| "99".equals(role)) {
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
