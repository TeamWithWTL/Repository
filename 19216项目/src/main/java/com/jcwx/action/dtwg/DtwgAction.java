package com.jcwx.action.dtwg;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.xtgl.YhglAction;
import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SjglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shgl.ZfjgService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 网格地图
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/wgdt")
public class DtwgAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SjglService sjglService;
	@Autowired
	private ZfjgService zfjgService;

	@Autowired
	private EventService eventService;

	@RequestMapping("/index")
	public String index(HttpSession session, Model model) {
		// Map<String, String> paramMap = new HashMap<String, String>();
		// List<ShglCommunityEntity> comList =
		// sqglService.findAllCom1(paramMap);
		// model.addAttribute("comList", comList);
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();// 人员分类
		model.addAttribute("ryfl", ryfl);

		/*
		 * 通过用户角色查询地图下拉列表查看权限
		 */
		boolean isHisDataErr = false; // 如果用户表历史数据有问题,必须字段为空,返回提示信息
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String rolesStr = accCount.getRole_code();
		if (rolesStr != null && !"".equals(rolesStr.trim())) {
			String[] rolesArr = rolesStr.split(",");
			List<String> roleList = new ArrayList<String>();
			if (rolesArr != null && rolesArr.length > 0) {
				for (String role : rolesArr) {
					if (role != null && !"".equals(role.trim())) {
						roleList.add(role);
					}
				}
			}
			if (roleList != null && roleList.size() > 0) { // 权限列表不为空
				// 最高权限：查看所有社区
				if (roleList.contains(Consts.ROLE_ADMIN) || roleList.contains(Consts.ROLE_BMCY)
						|| roleList.contains(Consts.ROLE_JDBLD) || roleList.contains(Consts.ROLE_JDBDFLZGLRY)
						|| roleList.contains(Consts.ROLE_SHZZGLY) || roleList.contains(Consts.ROLE_YWBM)
						|| roleList.contains(Consts.ROLE_YWBMLD) || roleList.contains(Consts.ROLE_DZBGZRY)
						|| roleList.contains(Consts.ROLE_JDGLY) || roleList.contains(Consts.ROLE_JDXXY)) {
					Map<String, String> paramMap = new HashMap<String, String>();
					List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
					model.addAttribute("comList", comList);
					// 社区管理员权限：查看社区下的所有服务站
				} else if (roleList.contains(Consts.ROLE_SQGLY)) {
					String commId = accCount.getCommId();
					if (commId != null && !"".equals(commId.trim())) {
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("commId", commId);
						List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap); // 查询对应社区下所有服务站
						ShglCommunityEntity comm = sqglService.findById1(commId); // 查询对应社区
						List<ShglCommunityEntity> comList = new ArrayList<ShglCommunityEntity>();
						if (comm != null) {
							comList.add(comm);
						}
						if (comList != null && comList.size() > 0 && ssList != null && ssList.size() > 0) {
							model.addAttribute("comList", comList);
							model.addAttribute("ssList", ssList);
						} else {
							isHisDataErr = true;
						}
					} else {
						isHisDataErr = true;
					}
					// 服务站管理员权限：查看服务站下的所有网格
				} else if (roleList.contains(Consts.ROLE_FWZGLY)) {
					String commId = accCount.getCommId();
					String ssId = accCount.getSsId();
					if (ssId != null && !"".equals(ssId.trim())) {
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("ssId", ssId);
						List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap); // 查询指定服务站下的网格
						ShglCommunityEntity comm = sqglService.findById1(commId); // 查询对应社区
						List<ShglCommunityEntity> comList = new ArrayList<ShglCommunityEntity>();
						if (comm != null) {
							comList.add(comm);
						}
						ShglServiceStationEntity ss = fwzglService.findById1(ssId); // 查询对应服务站
						List<ShglServiceStationEntity> ssList = new ArrayList<ShglServiceStationEntity>();
						if (ss != null) {
							ssList.add(ss);
						}
						if (comList != null && comList.size() > 0 && ssList != null && ssList.size() > 0
								&& ssList != null && ssList.size() > 0) {
							model.addAttribute("comList", comList);
							model.addAttribute("ssList", ssList);
							model.addAttribute("gridList", gridList);
						} else {
							isHisDataErr = true;
						}
					} else {
						isHisDataErr = true;
					}
					// 没有查看权限：直接不显示菜单,进不了页面
				} else {

				}
			}
		}
		if (isHisDataErr) {
			model.addAttribute("hisDataErr", "此帐号没有指定对应的社区或服务站!");
		}

		return "dtwg/index";
	}

	/**
	 * 人口查询
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param type
	 * @return
	 */
	@RequestMapping("/getPerData")
	public String getPerData(Model model, String ajaxCmd, String type, String commId, String ssId, String gridId,
			String tsrkName) {
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("inmateType", type);
				map.put("commId", commId);
				map.put("ssId", ssId);
				map.put("gridId", gridId);
				map.put("name", tsrkName);
				List<ShglInmateEntity> inmateEntities = jmxxService.findAllInmates1(map);
				model.addAttribute("inmateEntities", inmateEntities);
				return "dtwg/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[DtwgAction]_[getPerData]_人口查询出错", e);
			return "dtwg/index#" + ajaxCmd;
		}
		return "dtwg/index";
	}

	/**
	 * 房查询
	 * 
	 * @param commId
	 * @param ssId
	 * @param gridId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/findHomes", produces = "text/html;charset=UTF-8")
	public String findHomes(String commId, String ssId, String gridId, String name) {
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("commId", commId);
		paramMap.put("ssId", ssId);
		paramMap.put("gridId", gridId);
		paramMap.put("name", name);
		buildList = lyglService.findAllBuilds1(paramMap);
		String returnJson = JSONArray.fromObject(buildList).toString();
		return returnJson;
	}

	/**
	 * 事件查询
	 * 
	 * @param commId
	 * @param ssId
	 * @param gridId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/findEvents", produces = "text/html;charset=UTF-8")
	public String findEvents(String commId, String ssId, String gridId, String name) {
		List<EventEntity> eventsList = new ArrayList<EventEntity>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("commId", commId);
		paramMap.put("ssId", ssId);
		paramMap.put("gridId", gridId);
		paramMap.put("title", name);
		eventsList = sjglService.findAllEvents(paramMap);
		if (eventsList.size() > 0) {
			for (EventEntity event : eventsList) {
				SysParamDesc sysParamDesc = sjzdService.findDescByCode(event.getType(), "10008");
				if (sysParamDesc != null) {
					event.setTypeName(sysParamDesc.getItemName());
				}

				// 查询事件状态
				String eventId = event.getId();
				if (eventId != null && !"".equals(eventId.trim())) {
					Map<String, String> conditionMap = new HashMap<String, String>();
					conditionMap.put("eventId", eventId);
					List<EventDeal> eventDealList = eventService.findEventDealList(conditionMap);
					if (eventDealList != null && eventDealList.size() > 0) {
						EventDeal eventDeal = eventDealList.get(0); // 倒序排列取第一个(最后的处理状态)
						if (eventDeal != null && eventDeal.getDealStatus() != null) {
							event.setDealStatus(eventDeal.getDealStatus()); // 事件处理状态
						}
					}
				}
			}
		}
		String returnJson = JSONArray.fromObject(eventsList).toString();
		return returnJson;
	}

	/**
	 * 事件查询-新
	 * 
	 * @param commId
	 * @param ssId
	 * @param gridId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/findEvents2", produces = "text/html;charset=UTF-8")
	public String findEvents2(String commId, String ssId, String gridId, String name) {
		List<Event> eventsList = new ArrayList<Event>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("commId", commId);
		paramMap.put("ssId", ssId);
		paramMap.put("gridId", gridId);
		paramMap.put("title", name);
		paramMap.put("isOver", "1"); // 查询已结束的事件
		// eventsList = eventService.findAllEvents(paramMap);
		eventsList = eventService.findRecentEvents(paramMap);
		if (eventsList.size() > 0) {
			for (Event event : eventsList) {
				SysParamDesc sysParamDesc = sjzdService.findDescByCode(event.getType(), "10008");
				if (sysParamDesc != null) {
					event.setTypeName(sysParamDesc.getItemName());
				}

				// 查询事件状态
				String eventId = event.getId();
				if (eventId != null && !"".equals(eventId.trim())) {
					Map<String, String> conditionMap = new HashMap<String, String>();
					conditionMap.put("eventId", eventId);
					List<EventDeal> eventDealList = eventService.findEventDealList(conditionMap);
					if (eventDealList != null && eventDealList.size() > 0) {
						EventDeal eventDeal = eventDealList.get(0); // 倒序排列取第一个(最后的处理状态)
						if (eventDeal != null && eventDeal.getDealStatus() != null) {
							event.setDealStatus(eventDeal.getDealStatus()); // 事件处理状态
						}
					}
				}
			}
		}
		// String returnJson = JSONArray.fromObject(eventsList).toString();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jsonArray = JSONArray.fromObject(eventsList, jsonConfig);
		String returnJson = jsonArray.toString();
		return returnJson;
	}

	/**
	 * 机构查询
	 * 
	 * @param commId
	 * @param ssId
	 * @param gridId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/findGovernments", produces = "text/html;charset=UTF-8")
	public String findGovernments(String commId, String ssId, String gridId, String name) {
		List<ShglGovernmentEntity> goveList = new ArrayList<ShglGovernmentEntity>();
		Map<String, String> paramMap = new HashMap<String, String>();
		// paramMap.put("commId", commId);
		// paramMap.put("ssId", ssId);
		// paramMap.put("gridId", gridId);
		paramMap.put("name", name);
		goveList = zfjgService.findGoverByName(paramMap);
		String returnJson = JSONArray.fromObject(goveList).toString();
		return returnJson;
	}

	/**
	 * 跳转查看楼宇详情页
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model, String id) {
		ShglBuildingEntity buildingEntity = lyglService.findById1(id);
		model.addAttribute("build", buildingEntity);
		model.addAttribute("units", Integer.parseInt(buildingEntity.getUnit_cnt()));
		model.addAttribute("familyCnt", Integer.parseInt(buildingEntity.getFamily_cnt()));
		model.addAttribute("floors", Integer.parseInt(buildingEntity.getFloor_cnt()));
		model.addAttribute("rooms", Integer.parseInt(buildingEntity.getFamily_cnt()));
		/*
		 * model.addAttribute("families",
		 * Integer.parseInt(buildingEntity.getFamily_cnt()) *
		 * Integer.parseInt(buildingEntity.getFloor_cnt()));
		 */
		// 每层户数 * 层数 * 单元数
		// model.addAttribute("families",
		// Integer.parseInt(buildingEntity.getFamily_cnt()) *
		// Integer.parseInt(buildingEntity.getFloor_cnt()) *
		// Integer.parseInt(buildingEntity.getUnit_cnt()));
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("buildId", buildingEntity.getId());
		Long perNumb = jmxxService.countPerNumbByparam(paramMap);
		// 实际入住户数和总人数
		Map<String, String> addMap = new HashMap<String, String>();
		addMap.put("buildId", buildingEntity.getId());
		int hsCount = 0;
		int families = 0;
		for (int i = 0; i < Integer.parseInt(buildingEntity.getUnit_cnt()); i++) {
			addMap.put("unitCnt", String.valueOf(i + 1));
			hsCount = jmxxService.findLyHsCount(addMap);
			families = families + hsCount;
		}

		/*
		 * Map<String, Integer> map2 = new HashMap<String, Integer>();
		 * 
		 * for(int i = 1; i<=Integer.parseInt(buildingEntity.getUnit_cnt());
		 * i++){ String unitNo = String.valueOf(i); for(int j = 1;
		 * j<Integer.parseInt(buildingEntity.getFloor_cnt()); j++){ for(int k =
		 * 0; k<Integer.parseInt(buildingEntity.getFamily_cnt()); k++){ String
		 * roomNo = unitNo+j+"0"+(k+1); Map<String, String> map = new
		 * HashMap<String, String>(); map.put("buildId", id); map.put("roomNo",
		 * roomNo); map.put("unitNo", unitNo); Pagenate<ShglInmateEntity>
		 * pagenate = jmxxService.findByPage1(1, -1, map); map2.put(roomNo,
		 * pagenate.getRsCnts()); } } }
		 * 
		 * model.addAttribute("map2", map2);
		 */
		model.addAttribute("families", families);
		model.addAttribute("perNumb", perNumb);
		return "dtwg/viewBuilding";
	}

	@ResponseBody
	@RequestMapping("/initBuildColor")
	public Map initBuildColor(String buildingId) {
		Map<String, String> roomMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("buildId", buildingId);
		List<ShglInmateEntity> inmateList = jmxxService.findAllInmates(map);
		for (int i = 0; i < inmateList.size(); i++) {
			ShglInmateEntity inmate = inmateList.get(i);
			roomMap.put(inmate.getUnit_no() + inmate.getRoom_no(), "");
		}
		return roomMap;
	}

	/**
	 * 跳转查看事件详情页
	 * 
	 * @return
	 */
	@RequestMapping("/goViewEvent")
	public String goViewEvent(Model model, String id) {
		Map<String, String> eventTypeMap = sjzdService.findMapByPCode("10008");// 事件类型
		Event event = eventService.findEventById(id);
		// String lon = event.getApplyLon();
		// String lat = event.getApplyLat();
		// Gps gps = new Gps(Double.parseDouble(lat), Double.parseDouble(lon));
		// System.out.println("gps :" + gps);
		// Gps gcj = PositionUtil.gps84_To_Gcj02(gps.getWgLat(),
		// gps.getWgLon());
		// System.out.println("gcj :" + gcj);
		// Gps star = PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
		// System.out.println("star:" + star);
		// Gps bd = PositionUtil.gcj02_To_Bd09(gcj.getWgLat(), gcj.getWgLon());
		// event.setApplyLat(String.valueOf(bd.getWgLat()));
		// event.setApplyLon(String.valueOf(bd.getWgLon()));
		model.addAttribute("event", event);
		model.addAttribute("eventTypeMap", eventTypeMap);
		return "shgl/sjgl/sjgl_view";
		// List<SysParamDesc> sysStations =
		// sjzdService.findByCode("10008").getSysParamDesc();// 事件类型
		// model.addAttribute("sysStations", sysStations);
		// /*
		// EventEntity eventEntity = sjglService.findById(id);
		// model.addAttribute("event", eventEntity);
		// */
		// Event event = eventService.findEventById(id);
		// model.addAttribute("event", event);
		// /*
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("statusId", eventEntity.getEventStastus().getId());
		// map.put("done_status", "1");
		// EventFlowRecordEntity eventflow = sjglService.findByPara(map);
		// model.addAttribute("eventflow", eventflow);
		// */
		// Map<String, String> conditionMap = new HashMap<String, String>();
		// conditionMap.put("eventId", id);
		// List<EventDeal> eventDealList =
		// eventService.findEventDealList(conditionMap);
		// if(eventDealList!=null && eventDealList.size()>0){
		// EventDeal eventDeal = eventDealList.get(0); // 倒序排列取第一个(最后的处理状态)
		// if(eventDeal != null){
		// if(eventDeal.getDealStatus() != null){
		// model.addAttribute("dealStatus", eventDeal.getDealStatus()); //
		// 事件处理状态
		// }
		// if(eventDeal.getClrName() != null){
		// model.addAttribute("clrName", eventDeal.getClrName()); // 事件处理人
		// }
		// if(eventDeal.getDealDate() != null){
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// model.addAttribute("dealDate", df.format(eventDeal.getDealDate()));
		// // 事件处理事件
		// }
		// if(eventDeal.getContent() != null){
		// model.addAttribute("content", eventDeal.getContent()); // 处理意见(结果)
		// }
		// if(eventDeal.getEvent()!=null &&
		// eventDeal.getEvent().getIsOver()!=null){
		// model.addAttribute("isOver", eventDeal.getEvent().getIsOver()); //
		// 事件是否结束
		// }
		// }
		// }
		// return "dtwg/viewEvent";
	}

	/**
	 * 跳转查看政府机构详情页
	 * 
	 * @return
	 */
	// @RequestMapping("/goViewGovement")
	// public String goViewGovement(Model model, String id) {
	// ShglBuildingEntity buildingEntity = lyglService.findById1(id);
	// model.addAttribute("build", buildingEntity);
	// model.addAttribute("units",
	// Integer.parseInt(buildingEntity.getUnit_cnt()));
	// model.addAttribute("floors",
	// Integer.parseInt(buildingEntity.getFloor_cnt()));
	// model.addAttribute("rooms",
	// Integer.parseInt(buildingEntity.getFamily_cnt()));
	// model.addAttribute("families",
	// Integer.parseInt(buildingEntity.getFamily_cnt()) *
	// Integer.parseInt(buildingEntity.getFloor_cnt()));
	// Map<String, String> paramMap = new HashMap<String, String>();
	// paramMap.put("buildId", buildingEntity.getId());
	// Long perNumb = jmxxService.countPerNumbByparam(paramMap);
	// model.addAttribute("perNumb", perNumb);
	// return "dtwg/view";
	// }

	/**
	 * 楼宇房间人员查询
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param ssId
	 * @param commId
	 * @param gridId
	 * @param buildId
	 * @param roomNo
	 * @param unitNo
	 * @param req
	 * @return
	 */
	@RequestMapping("/getPersons")
	public String getPersons(Model model, String ajaxCmd, String buildId, String roomNo, String unitNo,
			HttpServletRequest req) {
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("buildId", buildId);
				map.put("roomNo", roomNo);
				map.put("unitNo", unitNo);
				Pagenate<ShglInmateEntity> pagenate = jmxxService.findByPage1(1, -1, map);
				model.addAttribute("pagenate", pagenate);
				return "dtwg/viewBuilding#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("查询居民信息出错", e);
		}
		return "dtwg/viewBuilding";
	}

	/**
	 * 通讯录树节点数据加载
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initTree", produces = "text/html;charset=UTF-8")
	public String initTree(HttpSession session, String flag) {

		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String rolesStr = accCount.getRole_code();
		boolean hasSuperAdmin = false; // 是否超级管理员
		boolean hasCommAdmin = false; // 是否社区管理员
		boolean hasSsAdmin = false; // 是否服务站管理员
		boolean hasNoAuth = false;
		if (rolesStr != null && !"".equals(rolesStr.trim())) {
			String[] rolesArr = rolesStr.split(",");
			List<String> roleList = new ArrayList<String>();
			if (rolesArr != null && rolesArr.length > 0) {
				for (String role : rolesArr) {
					if (role != null && !"".equals(role.trim())) {
						roleList.add(role);
					}
				}
			}
			if (roleList != null && roleList.size() > 0) { // 权限列表不为空
				// 最高权限
				if (roleList.contains(Consts.ROLE_ADMIN) || roleList.contains(Consts.ROLE_BMCY)
						|| roleList.contains(Consts.ROLE_JDBLD) || roleList.contains(Consts.ROLE_JDBDFLZGLRY)
						|| roleList.contains(Consts.ROLE_SHZZGLY) || roleList.contains(Consts.ROLE_YWBM)
						|| roleList.contains(Consts.ROLE_YWBMLD) || roleList.contains(Consts.ROLE_DZBGZRY)
						|| roleList.contains(Consts.ROLE_JDGLY) || roleList.contains(Consts.ROLE_JDXXY)) {
					hasSuperAdmin = true;
					// 社区管理员权限
				} else if (roleList.contains(Consts.ROLE_SQGLY)) {
					hasCommAdmin = true;
					// 服务站管理员权限
				} else if (roleList.contains(Consts.ROLE_FWZGLY)) {
					hasSsAdmin = true;
					// 没有权限
				} else {
					hasNoAuth = true;
				}
			}
		}

		// 组织树
		List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
		// 通讯录
		// List<SysStrative> strList = sqglService.findAllStra();
		List<SysParamDesc> strList = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
		// 1.初始化Tree的根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("name", strList.get(0).getItemName());
		rootNode.put("isParent", "true");
		rootNode.put("open", "true");
		rootNode.put("id", strList.get(0).getItemCode());
		rootNode.put("pId", "0");
		nodes.add(rootNode);
		// 2.组织Tree节点数据
		// 2.1.1根据通讯录ID查询分组
		Map<String, String> map = new HashMap<String, String>();
		map.put("strative_id", strList.get(0).getItemCode());
		List<ShglCommunityEntity> communityEntities = sqglService.findAllCom1(map);// 社区
		for (ShglCommunityEntity comm : communityEntities) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put("id", comm.getId());
			nodeMap.put("pId", comm.getStrative_id());
			nodeMap.put("name", comm.getName());
			nodeMap.put("area", comm.getArea());
			nodeMap.put("zoom", "15");
			nodeMap.put("typeId", "1");
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("commId", comm.getId());
			nodeMap.put("open", "false");
			List<ShglServiceStationEntity> serviceStationEntities = fwzglService.findAllCom1(map1);// 服务站
			if (serviceStationEntities.isEmpty()) {// 如果下一级分组为空
				nodeMap.put("isParent", "false");
			} else {
				nodeMap.put("isParent", "true");
				for (ShglServiceStationEntity ser : serviceStationEntities) {
					Map<String, String> nodeMap1 = new HashMap<String, String>();
					nodeMap1.put("id", ser.getId());
					nodeMap1.put("pId", comm.getId());
					nodeMap1.put("name", ser.getName());
					nodeMap1.put("area", ser.getArea());
					nodeMap1.put("zoom", "16");
					nodeMap1.put("typeId", "2");
					nodeMap1.put("open", "false");
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("ssId", ser.getId());
					List<ShglGridEntity> gridEntities = wgglService.findAllSer1(map2);// 网格
					if (gridEntities.size() <= 0) {// 如果下一级分组为空
						nodeMap1.put("isParent", "false");
					} else {
						nodeMap1.put("isParent", "true");
						for (ShglGridEntity grid : gridEntities) {
							Map<String, String> nodeMap2 = new HashMap<String, String>();
							nodeMap2.put("id", grid.getId());
							nodeMap2.put("pId", ser.getId());
							nodeMap2.put("name", grid.getName());
							nodeMap2.put("area", grid.getArea());
							nodeMap2.put("zoom", "17");
							nodeMap2.put("typeId", "3");
							nodeMap2.put("isParent", "false");
							nodeMap2.put("open", "false");
							nodes.add(nodeMap2);
						}
					}
					nodes.add(nodeMap1);
				}
			}
			nodes.add(nodeMap);
		}
		log.info("tree======11111111111=========" + JSONArray.fromObject(nodes).toString());
		return JSONArray.fromObject(nodes).toString();
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/findInmate", produces = "text/html;charset=UTF-8")
	public String findInmate(String commId, String ssId, String gridId, String inmateType, String tsrkName) {
		List<ShglInmateEntity> inmateList = new ArrayList<ShglInmateEntity>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("commId", commId);
		paramMap.put("ssId", ssId);
		paramMap.put("gridId", gridId);
		paramMap.put("inmateType", inmateType);
		paramMap.put("name", tsrkName);
		inmateList = jmxxService.findAllInmates1(paramMap);
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "inmateTList" }); // 只要设置这个数组，指定过滤哪些字段。
		// 组成JSON数组
		JSONArray json = JSONArray.fromObject(inmateList, config);
		String returnJson = json.toString();
		return returnJson;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/checkAccountRelation", produces = "text/html;charset=UTF-8")
	public String checkAccountRelation(HttpSession session) {
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String rolesStr = accCount.getRole_code();
		boolean isHisDataErr = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (rolesStr != null && !"".equals(rolesStr.trim())) {
			String[] rolesArr = rolesStr.split(",");
			List<String> roleList = new ArrayList<String>();
			if (rolesArr != null && rolesArr.length > 0) {
				for (String role : rolesArr) {
					if (role != null && !"".equals(role.trim())) {
						roleList.add(role);
					}
				}
			}
			if (roleList != null && roleList.size() > 0) { // 权限列表不为空
				// 最高权限：不进行校验
				if (roleList.contains(Consts.ROLE_ADMIN) || roleList.contains(Consts.ROLE_BMCY)
						|| roleList.contains(Consts.ROLE_JDBLD) || roleList.contains(Consts.ROLE_JDBDFLZGLRY)
						|| roleList.contains(Consts.ROLE_SHZZGLY) || roleList.contains(Consts.ROLE_YWBM)
						|| roleList.contains(Consts.ROLE_YWBMLD) || roleList.contains(Consts.ROLE_DZBGZRY)
						|| roleList.contains(Consts.ROLE_JDGLY) || roleList.contains(Consts.ROLE_JDXXY)) {
					// 社区管理员权限：校验用户社区id是否为空
				} else if (roleList.contains(Consts.ROLE_SQGLY)) {
					String commId = accCount.getCommId();
					if (commId == null || "".equals(commId)) {
						isHisDataErr = true;
					}
					// 服务站管理员权限：校验用户服务站id是否为空
				} else if (roleList.contains(Consts.ROLE_FWZGLY)) {
					String ssId = accCount.getSsId();
					if (ssId == null || "".equals(ssId)) {
						isHisDataErr = true;
					}
				} else {
					isHisDataErr = true;
				}
			}
		}
		if (isHisDataErr) {
			resultMap.put("success", false);
			resultMap.put("hisDataErr", "此帐号没有指定对应的社区或服务站!");
		} else {
			resultMap.put("success", true);
		}
		String returnJson = JSONArray.fromObject(resultMap).toString();
		return returnJson;
	}
}
