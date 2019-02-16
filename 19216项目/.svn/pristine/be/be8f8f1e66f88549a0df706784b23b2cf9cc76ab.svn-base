package com.jcwx.action.shgl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.util.Area;
import com.jcwx.entity.util.Gps;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.PositionUtil;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.SpatialRelationUtil;

/**
 * app 事件相关
 * @author jiangkia
 *
 */
@Controller
@RequestMapping("/app/sjgl")
public class AppEventAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(AppEventAction.class);
	@Autowired
	private EventService eventService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private DepartmentService departmentService;
	
	
	
	/**
	 * 查询我的上报
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/myReport")
	public String myReport(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req) {
	
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("pageCnts", 0);
				model.addAttribute("pageSize", 0);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("applyCode", accCode);
				Pagenate<Event> pagenate = eventService.appfindEventByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "shgl/sjgl/app/app_sjgl_myreport#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppEventAction]_[myReport]查询事件信息出错", e);
		}
		return "shgl/sjgl/app/app_sjgl_myreport";
	}
	
	
	
	/**
	 * app分页查询 上报
	 */
	@ResponseBody
	@RequestMapping("/appGetMoreReprot")
	public Map appGetMorePage(Model model, HttpServletRequest request, HttpServletResponse response,
			String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		try {
			SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
			String accCode = acc.getAccCode();
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("applyCode", accCode);
			Pagenate<Event> pagenate = eventService.findEventByPage(pageNumber,pageSize, paramsMap);
			
			List<Event> eventList = pagenate.getList();
			List<Map> dateList = new ArrayList<Map>();
			for (int i = 0; i < eventList.size(); i++) {
				Map data = new HashMap();
				Event event = eventList.get(i);
				data.put("id", event.getId());
				data.put("title", event.getTitle());
				data.put("content", event.getContent());
				data.put("showDate", event.getApplyTimeFrm());
				dateList.add(data);
			}
			valMap.put("data", dateList);
		} catch (Exception e) {
			log.error("[AppEventAction]_[appGetMoreReprot]查询事件信息出错", e);
		}
		return valMap;
	}
	
	
	/**
	 * 跳转添加事件页面
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/addReportPage")
	public String addReportPage(Model model,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		List eventType = sjzdService.findByPCode("10008");// 查询事件类别 
		String roleFlag = "0";
		if(roleCode.indexOf("01") != -1){
			roleFlag = "1";
		}
		model.addAttribute("eventType", eventType);
		model.addAttribute("roleFlag", roleFlag);
		return "shgl/sjgl/app/app_sjgl_addreport";
	}
	/**
	 * 查看上报事件内容
	 * @param model
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("/reportView")
	public String reportView(Model model,HttpServletRequest req,String id) {
		Map<String, String> eventTypeMap = sjzdService.findMapByPCode("10008");// 事件类型
		Event event = eventService.findEventById(id);
		model.addAttribute("event", event);
		model.addAttribute("eventTypeMap", eventTypeMap);
		String path = ProjectUtils.getSysCfg("appDownloadPath");
		model.addAttribute("path", path);
		return "shgl/sjgl/app/app_sjgl_reportView";
	}
	
	/**
	 * 保存事件
	 * @param model
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveReport")
	public String saveReport(Model model,HttpServletRequest req,String isAndroid,String title,String addr,String content,String lon,String lat,String fName,String sjlx) {
	try{
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		
		Event event  = new Event();
		event.setTitle(title);
		event.setContent(content);
		event.setApplyAddr(addr);

		event.setApplyTime(new Date());
		event.setApplyCode(acc.getAccCode());
		event.setApplyName(acc.getName());
		event.setType(sjlx);
		if("".equals(lon)||"".equals(lat)){
			lon ="0";
			lat = "0";
		}
//		if("true".equals(isAndroid)){
//	        Gps gcj = PositionUtil.bd09_To_Gcj02(Double.parseDouble(lat), Double.parseDouble(lon));
//	        Gps gb =  PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
//	        lat = String.valueOf(gb.getWgLat());
//	        lon = String.valueOf(gb.getWgLon());
//		}
		  if(lon!=null &&!"".equals(lon)&&lat!=null &&!"".equals(lat)){
//		        Gps gcj = PositionUtil.bd09_To_Gcj02(Double.parseDouble(lat), Double.parseDouble(lon));
//		        Gps gb =  PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
//		        lat = String.valueOf(gb.getWgLat());
//		        lon = String.valueOf(gb.getWgLon());
//	    		event.setApplyLat(String.valueOf(gb.getWgLat()));
//	    		event.setApplyLon(String.valueOf(gb.getWgLon()));
	        Gps gcj = PositionUtil.bd09_To_Gcj02(Double.parseDouble(lat), Double.parseDouble(lon));
	        Gps gb =  PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
	        lat = String.valueOf(gb.getWgLat());
	        lon = String.valueOf(gb.getWgLon());
	        }
//	        else{
    		event.setApplyLat(lat);
    		event.setApplyLon(lon);
//	        }
//		if(roleCode.equals("14")){
			event.setEventfrm(Consts.EVENT_FROM_COMMONAPP); // 事件来源：公共app
//		}else{
//			event.setEventfrm(Consts.EVENT_FROM_MANAGEAPP); // 事件来源：管理端app
//		}
		String commId = this.getCommId(lon,lat);
		String ssId = "";
		if(!"".equals(commId)){
			ssId = this.getSsId(lon,lat,commId);
		}
		event.setCommId(commId);
		event.setSsId(ssId);
		event.setIsPub("2");
		event.setIsOver("2");
		eventService.upDateEvent(event);
		
		EventDeal deal = new EventDeal();
		deal.setEvent(event);
		deal.setCurRoleId(Consts.ROLE_FWZGLY);
		deal.setCreateDate(new Date());
		deal.setFbrId(acc.getAccCode());
		deal.setFbrName(acc.getName());
		deal.setDealStatus(Consts.EVENT_STATUS_UNDO);
		eventService.upDateEventDeal(deal);
		return "{\"result\":\"success\",\"pid\":\"" + event.getId() + "\"}";
		} catch (Exception e) {
			log.error("[AppEventAction]_[saveReport]_出错", e);
		}
	//return "fail";
	return "{\"result\":\"fail\"}";
	}
	
	/**
	 * 查看上报事件内容
	 * @param model
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("/eventView")
	public String eventView(Model model,HttpServletRequest req,String id,String tagFlag) {
		Map<String, String> eventTypeMap = sjzdService.findMapByPCode("10008");// 事件类型
		Event event = eventService.findEventById(id);
		model.addAttribute("event", event);
		model.addAttribute("eventTypeMap", eventTypeMap);
		String path = ProjectUtils.getSysCfg("appDownloadPath");
		model.addAttribute("path", path);
		model.addAttribute("tagFlag", tagFlag);
		return "shgl/sjgl/app/app_sjgl_eventView";
	}
	
	@RequestMapping("/eventfunList")
	public String appventTag(Model model,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		return "shgl/sjgl/app/app_sjgl_eventfunList";
	}
	/**
	 * 我待处理事件
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/myEvent")
	public String myevet(Model model, String ajaxCmd,@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		String commId = acc.getCommId();
		String	ssId = acc.getSsId();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("pageCnts", 0);
				model.addAttribute("pageSize", 0);
				model.addAttribute("tagFlag", "1");
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("commId", commId);
				map.put("ssId", ssId);
				if(roleCode.indexOf(Consts.ROLE_FWZGLY)>-1||roleCode.indexOf(Consts.ROLE_SQGLY)>-1||roleCode.indexOf(Consts.ROLE_JDXXY)>-1){
					map.put("roleCode", roleCode);
				}else{
					map.put("clrId", accCode);
				}
				Pagenate<EventDeal> pagenate = eventService.findEventDealByPage(pageNumber,pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				model.addAttribute("tagFlag", "1");
				return "shgl/sjgl/app/app_sjgl_myevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("[AppEventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/app/app_sjgl_myevent";
	}
	
	/**
	 * 加载更多我得待处理事件
	 * @param model
	 * @param request
	 * @param response
	 * @param ajaxCmd
	 * @param pageNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appGetMoreEvent")
	public Map appGetMoreEvent(Model model, HttpServletRequest request, HttpServletResponse response,
			String ajaxCmd, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		String commId = acc.getCommId();
		String	ssId = acc.getSsId();
		// 分页查询用户信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("commId", commId);
		map.put("ssId", ssId);
		if(roleCode.indexOf(Consts.ROLE_FWZGLY)>-1||roleCode.indexOf(Consts.ROLE_SQGLY)>-1||roleCode.indexOf(Consts.ROLE_JDXXY)>-1){
			map.put("roleCode", roleCode);
		}else{
			map.put("clrId", accCode);
		}
		Pagenate<EventDeal> pagenate = eventService.findEventDealByPage(pageNumber, pageSize, map);
		List<EventDeal>  eventList  = pagenate.getList();
		List<Map> dateList = new ArrayList<Map>();

		for (int i = 0; i < eventList.size(); i++) {
			Map data = new HashMap();
			EventDeal  deal =  eventList.get(i);
			data.put("id",  deal.getEvent().getId());
			data.put("title",  deal.getEvent().getTitle());
			data.put("content", deal.getEvent().getContent());
			data.put("showDate", deal.getEvent().getApplyTimeFrm());
			data.put("dealStatus", deal.getDealStatus());
			data.put("curRoleId", deal.getCurRoleId());
			dateList.add(data);
		}
		
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		valMap.put("data", dateList);
		return valMap;
	}
	

	@RequestMapping("/allEvent")
	public String allEvent(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,String isOver,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("pageCnts", 0);
				model.addAttribute("pageSize", 0);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("isOver", isOver);
				map.put("title", title);
				map.put("applyTime", applyTime);
				//添加权限控制，社区管理员，服务站管理员只能查看自己权限内的事件
//				String commId = map.get("commId");
//				String ssId = map.get("ssId");
				if(roleCode.indexOf(Consts.ROLE_SQGLY)>-1){
					map.put("commId", acc.getCommId());
				}else if(roleCode.indexOf(Consts.ROLE_FWZGLY)>-1){
					map.put("ssId", acc.getSsId());
				}
				Pagenate<Event> pagenate = eventService.appfindEventByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "shgl/sjgl/app/app_sjgl_allevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/app/app_sjgl_allevent";
	}
	
	@ResponseBody
	@RequestMapping("/appGetMoreAllEvent")
	public Map appGetMoreAllEvent(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,String isOver,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		try {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("isOver", isOver);
				map.put("title", title);
				map.put("applyTime", applyTime);
				//添加权限控制，社区管理员，服务站管理员只能查看自己权限内的事件
//				String commId = map.get("commId");
//				String ssId = map.get("ssId");
				if(Consts.ROLE_SQGLY.equals(roleCode)){
					map.put("commId", acc.getCommId());
				}else if(Consts.ROLE_FWZGLY.equals(roleCode)){
					map.put("ssId", acc.getSsId());
				}
				Pagenate<Event> pagenate = eventService.appfindEventByPage(pageNumber, pageSize, map);

				List<Event> eventList = pagenate.getList();
				List<Map> dateList = new ArrayList<Map>();
				for (int i = 0; i < eventList.size(); i++) {
					Map data = new HashMap();
					Event event = eventList.get(i);
					data.put("id", event.getId());
					data.put("title", event.getTitle());
					data.put("content", event.getContent());
					data.put("showDate", event.getApplyTimeFrm());
					dateList.add(data);
				}
				valMap.put("data", dateList);
		} catch (Exception e) {
			log.error("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return valMap;
	}
	/**
	 * 处理记录
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/myRecord")
	public String myRecord(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("pageCnts", 0);
				model.addAttribute("pageSize", 0);
				model.addAttribute("tagFlag", "2");
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("clrId", accCode);
				map.put("dealStatus", "1;2;5");//处理结束
				Pagenate<Event> pagenate = eventService.findEventByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				model.addAttribute("tagFlag", "2");
				return "shgl/sjgl/app/app_sjgl_myrecord#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/app/app_sjgl_myrecord";
	}
	
	/**
	 * 加载更多处理记录
	 * @param model
	 * @param request
	 * @param response
	 * @param ajaxCmd
	 * @param pageNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appGetMoreRecord")
	public Map appGetMoreRecord(Model model, HttpServletRequest request, HttpServletResponse response,
			String ajaxCmd, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		Map<String, String> map = new HashMap<String, String>();
		map.put("clrId", accCode);
		map.put("dealStatus", "1;2;5");//处理结束
		Pagenate<Event> pagenate = eventService.findEventByPage(pageNumber, pageSize, map);
		List<Event>  eventList  = pagenate.getList();
		List<Map> dateList = new ArrayList<Map>();

		for (int i = 0; i < eventList.size(); i++) {
			Map data = new HashMap();
			Event  event =  eventList.get(i);
			data.put("id",  event.getId());
			data.put("title",  event.getTitle());
			data.put("content", event.getContent());
			data.put("showDate", event.getApplyTimeFrm());
			dateList.add(data);
		}
		
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		valMap.put("data", dateList);
		return valMap;
	}	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/loadTreeData", produces = "text/html;charset=UTF-8")
	public String loadTreeData(HttpSession session,String curRoleId,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();
		
		//社区管理员 选择服务站
		if(Consts.ROLE_SQGLY.equals(curRoleId)){
		String commId = acc.getCommId();//获取社区ID
		if(commId ==null ||"".equals(commId)){
			log.error("[社区管理员获取所在社区ID 为空! error:1001]");
			return "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("commId", commId);
		List<ShglServiceStationEntity> ssLists = fwzglService.findAllCom1(map);
		for (ShglServiceStationEntity ssEntities : ssLists) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			String id = ssEntities.getId();
			String name = ssEntities.getName();
			nodeMap.put("id", id);
			nodeMap.put("name", name);
			nodeMap.put("pId", "fwzList");
			nodeList.add(nodeMap);
		}
		// 添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "fwzList");
		rootNode.put("name", "服务站列表");
		rootNode.put("pId", "root");
		rootNode.put("open", "true");
		rootNode.put("nocheck", "true");
		nodeList.add(rootNode);
		return JSONArray.fromObject(nodeList).toString();
		}
		if(Consts.ROLE_JDXXY.equals(curRoleId)){
		// 添加成员
		List<SysAccCount>  accList = rwglService.findByCode(Consts.ROLE_JDBLD, null,null);
		for (SysAccCount sqryLists : accList) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			String id = sqryLists.getAccCode();// 社区负责人ID
			String name = sqryLists.getName();// 社区负责人姓名
			nodeMap.put("id", id);
			nodeMap.put("name", name);
			nodeMap.put("pId", "jdbldlist");
			nodeList.add(nodeMap);
		}
		// 添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "jdbldlist");
		rootNode.put("name", "街道办领导");
		rootNode.put("pId", "root");
		rootNode.put("open", "true");
		rootNode.put("nocheck", "true");
		nodeList.add(rootNode);
		return JSONArray.fromObject(nodeList).toString();
		}
		//街道办领导选择部门成员
		if(Consts.ROLE_JDBLD.equals(curRoleId)){

			//查询部门结构
			Map<String, String> params = new HashMap<String, String>();
			List<SysDepartment> list = departmentService.findByParams(params);
			//组织节点
			for(SysDepartment department : list){
				String code = department.getDeptId();
				String name = department.getDeptName();
				String pid = department.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", "root");
				nodeMap.put("nocheck", "true");
				nodeList.add(nodeMap);
			}
			//添加成员
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("isDept", "1");
			List<SysAccCount> userlist = yhglService.findByParam(paramMap);
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				SysAccCount te = userlist.get(i);
				nodeMap.put("id", te.getAccCode());
				nodeMap.put("name", te.getName());
				//如果使自己过滤掉
				if(accCode.equals(te.getAccCode())){
					continue;
				}
				nodeMap.put("pId", te.getDeptId());
				nodeList.add(nodeMap);
				
			}
			return JSONArray.fromObject(nodeList).toString();
		}
		return null;
	}
/************************内部方法**********************************/	

	/**
	 * 查询所属服务站ID
	 * @param lon
	 * @param lat
	 * @param commId
	 * @return
	 */
	private String getSsId(String lon, String lat, String commId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("commId", commId);
		List<ShglServiceStationEntity>  ssList= fwzglService.findAllCom1(map);
		for (int i = 0; i < ssList.size(); i++) {
			ShglServiceStationEntity ssObj = ssList.get(i);
			String area = ssObj.getArea();
		
			String[] areaArray = area.split(";");
			 List<Area> areas = new ArrayList<Area>();
			if(areaArray.length>0){
				for (int j = 0; j < areaArray.length; j++) {
					String areatemp = areaArray[j];
					if(areatemp == null || "".equals(areatemp)){
						continue;
					}
					String lontemp = areatemp.split(",")[0];
					String lattemp = areatemp.split(",")[1];
					areas.add(new Area(Double.parseDouble(lontemp),Double.parseDouble(lattemp)));
				}
			}
			boolean isok =SpatialRelationUtil.isPolygonContainsPoint(areas,new Area(Double.parseDouble(lon),Double.parseDouble(lat)));
			if(isok){
				return ssObj.getId();
			}
		}
		return "";
	}

	/**
	 * 获取所属社区ID
	 * @param lon
	 * @param lat
	 * @return
	 */
	private String getCommId(String lon, String lat) {
		Map<String, String> map = new HashMap<String, String>();
		List<ShglCommunityEntity>  commList= sqglService.findAllCom1(map);
		for (int i = 0; i < commList.size(); i++) {
			ShglCommunityEntity commObj = commList.get(i);
			String area = commObj.getArea();
			String[] areaArray = area.split(";");
			 List<Area> areas = new ArrayList<Area>();
			if(areaArray.length>0){
				for (int j = 0; j < areaArray.length; j++) {
					String areatemp = areaArray[j];
					if(areatemp == null || "".equals(areatemp)){
						continue;
					}
					String lontemp = areatemp.split(",")[0];
					String lattemp = areatemp.split(",")[1];
					areas.add(new Area(Double.parseDouble(lontemp),Double.parseDouble(lattemp)));
				}
			}
			boolean isok = SpatialRelationUtil.isPolygonContainsPoint(areas,new Area(Double.parseDouble(lon),Double.parseDouble(lat)));
			if(isok){
				return commObj.getId();
			}
		}
		return "";
	}
	
}
