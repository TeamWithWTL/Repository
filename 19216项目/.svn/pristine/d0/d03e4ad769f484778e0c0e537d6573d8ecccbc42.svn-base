package com.jcwx.action.shgl;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventAttrs;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.util.Area;
import com.jcwx.entity.util.Gps;
import com.jcwx.entity.xtbg.DocumentAttrs;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SjglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.PointInPolygon;
import com.jcwx.utils.PositionUtil;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.SpatialRelationUtil;
import com.jcwx.utils.SpatialRelationUtil.Point;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/shgl/sjgl")
public class EventAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(EventAction.class);
	@Autowired
	private EventService eventService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private SjzdService sjzdService;
	
	@Autowired
	private SqglService sqglService;
	
	@Autowired
	private FwzglService fwzglService;
	
	@Autowired
	private SjglService sjglService;
	

	@Autowired
	private RwglService rwglService;
	
	
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 首页数据
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param req
	 * @return
	 */
	@RequestMapping("/myevent")
	public String myevent(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		String commId = acc.getCommId();
		String	ssId = acc.getSsId();
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("applyTime", applyTime);
				map.put("commId", commId);
				map.put("ssId", ssId);
				if(roleCode.indexOf(Consts.ROLE_FWZGLY)>-1||roleCode.indexOf(Consts.ROLE_SQGLY)>-1||roleCode.indexOf(Consts.ROLE_JDXXY)>-1){
					map.put("roleCode", roleCode);
				}else{
					map.put("clrId", accCode);
				}
				Pagenate<EventDeal> pagenate = eventService.findEventDealByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sjgl/sjgl_myevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/sjgl_myevent";
	}

	/**
	 * 处理记录
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param req
	 * @return
	 */
	@RequestMapping("/myrecored")
	public String myrecored(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("applyTime", applyTime);
				map.put("clrId", accCode);
				map.put("dealStatus", "1;2;5");//处理结束
				Pagenate<Event> pagenate = eventService.findEventByPage(pageNumber, pageSize, map);
		
				model.addAttribute("pagenate", pagenate);
				return "shgl/sjgl/sjgl_myrecored#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/sjgl_myrecored";
	}
	
	/**
	 * 事件总览
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param req
	 * @return
	 */
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
				return "shgl/sjgl/sjgl_allevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/sjgl_allevent";
	}
	
	/**
	 * 跳转查看界面
	 * 
	 * @return
	 */
	@RequestMapping("/eventview")
	public String goView(Model model, String id) {
		Map<String, String> eventTypeMap = sjzdService.findMapByPCode("10008");// 事件类型
		Event event = eventService.findEventById(id);
//		String lon = event.getApplyLon();
//		String lat = event.getApplyLat();
//		   Gps gps = new Gps(Double.parseDouble(lat), Double.parseDouble(lon));  
//	        System.out.println("gps :" + gps);  
//	        Gps gcj = PositionUtil.gps84_To_Gcj02(gps.getWgLat(), gps.getWgLon());  
//	        System.out.println("gcj :" + gcj);  
//	        Gps star = PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());  
//	        System.out.println("star:" + star);  
//	        Gps bd = PositionUtil.gcj02_To_Bd09(gcj.getWgLat(), gcj.getWgLon()); 
//	        event.setApplyLat(String.valueOf(bd.getWgLat()));
//	        event.setApplyLon(String.valueOf(bd.getWgLon()));
		model.addAttribute("event", event);
		model.addAttribute("eventTypeMap",eventTypeMap);
		return "shgl/sjgl/sjgl_view";
		}
	
	
	/**
	 * 跳转处理界面
	 * @return
	 */
	@RequestMapping("/eventDealPage")
	public String goDeal(Model model, String id) {
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		model.addAttribute("eventDeal", eventDeal);
		return "shgl/sjgl/sjgl_deal";
	}
	

	/**
	 * 处理事件
	 * @param model
	 * @param id 
	 * @param content 内容
	 * @param isPub 是否公开
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/eventDeal")
	public String dealEvent(Model model, String id,String content,String isPub,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");

		Date currentDate = new Date();
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		//如果为查询到处理记录,或者这条事件已经被处理了,返回
		if(eventDeal == null || Consts.EVENT_STATUS_DEAL.equals(eventDeal.getDealStatus()) || Consts.EVENT_STATUS_XXYDONE.equals(eventDeal.getDealStatus())){
			return "false";
		}
		
		//更新公开状态
		Event event = eventDeal.getEvent();
	
		String curentRoleId = eventDeal.getCurRoleId();
		//如果是服务站管理员 上报,将该事件归到该服务站所在的社区,服务站'
		if(Consts.ROLE_FWZGLY.equals(curentRoleId)){
			String ssId = acc.getSsId();
			String commId = acc.getCommId();
			event.setSsId(ssId);
			event.setCommId(commId);
		}
		
		event.setIsPub(isPub);

		
		//更新处理状态
		eventDeal.setClrId(acc.getAccCode());
		eventDeal.setClrName(acc.getName());
		eventDeal.setDealDate(currentDate);
		//如果是业务部门处理公开不公开 设置状态为4
		if(Consts.EVENT_STATUS_XXYDEAL.equals(eventDeal.getDealStatus())){
			eventDeal.setDealStatus("4");
		}else{
			eventDeal.setContent(content);
			eventDeal.setDealStatus(Consts.EVENT_STATUS_DEAL);
		}
		
		eventService.upDateEventDeal(eventDeal);
		//如果是街道办领导或者是业务部门人员 需要转发给街道办信息员处理是否公开
		if(Consts.ROLE_YWBM.equals(curentRoleId)||Consts.ROLE_JDBLD.equals(curentRoleId)){
			//下一角色处理记录
			EventDeal eventDealNext = new EventDeal();
			eventDealNext.setFbrId(acc.getAccCode());
			eventDealNext.setFbrName(acc.getName());
			eventDealNext.setCreateDate(currentDate);
			eventDealNext.setEvent(eventDeal.getEvent());
			eventDealNext.setPreRoleId(eventDeal.getCurRoleId());
			eventDealNext.setDealStatus(Consts.EVENT_STATUS_XXYDEAL);
			//判断处理角色
//			String getNextRoleId = findNextRoleId(curentRoleId);
			eventDealNext.setCurRoleId(Consts.ROLE_JDXXY);
			eventService.upDateEventDeal(eventDealNext);
		}else{
			//处理后 是否结束状态更新为 结束 
			event.setIsOver("1");
		}
		eventService.upDateEvent(event);
		return "success";
	}

	
	/**
	 * 跳转上报界面
	 * @return
	 */
	@RequestMapping("/eventReportPage")
	public String reportPage(Model model, String id,String curRoleId) {
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		model.addAttribute("eventDeal", eventDeal);
		model.addAttribute("curRoleId", curRoleId);
		return "shgl/sjgl/sjgl_report";
	}
	
	/**
	 * 跳转上报界面
	 * @return
	 */
	@RequestMapping("/toServicePage")
	public String toServicePage(Model model, String id,String curRoleId) {
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		model.addAttribute("eventDeal", eventDeal);
		model.addAttribute("curRoleId", curRoleId);
		return "shgl/sjgl/sjgl_toService";
	}
	/**
	 * 上报处理
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/eventReport")
	public String eventreport(Model model, String id,String content,String clrId,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
	
		Date currentDate = new Date();
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		if(eventDeal == null){
			return "error";
		}
		
		Event event = eventDeal.getEvent();
		String curentRoleId = eventDeal.getCurRoleId();
		//判断处理角色
		String getNextRoleId = findNextRoleId(curentRoleId);
		if("error".equals(getNextRoleId)){
			return "error";
		}
		if(Consts.ROLE_JDBLD.equals(getNextRoleId) ||Consts.ROLE_YWBM.equals(getNextRoleId) ){
			if(clrId ==null && "".equals(clrId)){
				return "error";
			}	
		}
				
		//如果是服务站管理员 上报,将该事件归到该服务站所在的社区,服务站'
		if(Consts.ROLE_FWZGLY.equals(curentRoleId)){
			String ssId = acc.getSsId();
			String commId = acc.getCommId();
			event.setSsId(ssId);
			event.setCommId(commId);
			eventService.upDateEvent(event);
		}

		
		eventDeal.setClrId(acc.getAccCode());
		eventDeal.setContent(content);
		eventDeal.setClrName(acc.getName());
		eventDeal.setDealDate(currentDate);
		eventDeal.setDealStatus(Consts.EVENT_STATUS_REPORT);
		eventService.upDateEventDeal(eventDeal);
		
		
		//下一角色处理记录
		EventDeal eventDealNext = new EventDeal();
		eventDealNext.setFbrId(acc.getAccCode());
		eventDealNext.setFbrName(acc.getName());
		eventDealNext.setCreateDate(currentDate);
		eventDealNext.setEvent(eventDeal.getEvent());
		eventDealNext.setPreRoleId(eventDeal.getCurRoleId());
		eventDealNext.setDealStatus(Consts.EVENT_STATUS_UNDO);
		eventDealNext.setCurRoleId(getNextRoleId);
		if(Consts.ROLE_JDBLD.equals(getNextRoleId) || Consts.ROLE_YWBM.equals(getNextRoleId) ){
			String clr_id = clrId.replace(";", "").replace("；", "");
			 SysAccCountLazy accObj = yhglService.findByCode(clr_id);
			 eventDealNext.setClrId(clr_id);
			 eventDealNext.setClrName(accObj.getName());
		}
		
		eventService.upDateEventDeal(eventDealNext);
		return "success";
	}

	
	/**
	 * 社区管理员下发服务站
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toServiceStation")
	public String toServiceStation(Model model, String id,String content,String ssId,HttpServletRequest req) {
		
		if(ssId ==null && "".equals(ssId)){
			return "error";
		}	
		ssId = ssId.replace(";", "");
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		Date currentDate = new Date();
		EventDeal eventDeal = null;
		if (id != null && !"".equals(id)) {
		   eventDeal = eventService.findEventDealById(id);
		}
		if(eventDeal == null){
			return "error";
		}
		
		Event event = eventDeal.getEvent();
		String curentRoleId = eventDeal.getCurRoleId();
				
		//更新事件所属服务站
		event.setSsId(ssId);
		eventService.upDateEvent(event);

		eventDeal.setClrId(acc.getAccCode());
		eventDeal.setContent(content);
		eventDeal.setClrName(acc.getName());
		eventDeal.setDealDate(currentDate);
		eventDeal.setDealStatus(Consts.EVENT_STATUS_TOSERVICE);
		eventService.upDateEventDeal(eventDeal);
		
		
		//下一角色处理记录
		EventDeal eventDealNext = new EventDeal();
		eventDealNext.setFbrId(acc.getAccCode());
		eventDealNext.setFbrName(acc.getName());
		eventDealNext.setCreateDate(currentDate);
		eventDealNext.setEvent(eventDeal.getEvent());
		eventDealNext.setPreRoleId(eventDeal.getCurRoleId());
		eventDealNext.setDealStatus(Consts.EVENT_STATUS_UNDO);
		eventDealNext.setCurRoleId(Consts.ROLE_FWZGLY);//设置处理角色为服务站管理员
		eventService.upDateEventDeal(eventDealNext);
		return "success";
	}
	
	@RequestMapping("/showTree")
	public String showTree(Model model, String curRoleId) {
		log.info("当前处理人角色>>>>>>>"+curRoleId);
		model.addAttribute("curRoleId", curRoleId);
		return "/shgl/sjgl/sjgl_membertree";
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
			nodeMap.put("pId", "root");
			nodeList.add(nodeMap);
		}
		// 添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "root");
		rootNode.put("name", "服务站列表");
		rootNode.put("pId", "0");
		rootNode.put("open", "true");
		rootNode.put("nocheck", "true");
		nodeList.add(rootNode);
		return JSONArray.fromObject(nodeList).toString();
		}
		//信息员选择街道办领导
		if(Consts.ROLE_JDXXY.equals(curRoleId)){
		List<SysAccCount>  accList = rwglService.findByCode(Consts.ROLE_JDBLD, null,null);
		for (SysAccCount sqryLists : accList) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			String id = sqryLists.getAccCode();
			String name = sqryLists.getName();
			nodeMap.put("id", id);
			nodeMap.put("name", name);
			nodeMap.put("pId", "root");
			nodeList.add(nodeMap);
		}
		// 添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "root");
		rootNode.put("name", "街道办领导");
		rootNode.put("pId", "0");
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
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
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
			// 添加根节点
			Map<String, String> rootNode = new HashMap<String, String>();
			rootNode.put("id", "root");
			rootNode.put("name", "博昌街道");
			rootNode.put("pId", "0");
			rootNode.put("open", "true");
			rootNode.put("nocheck", "true");
			nodeList.add(rootNode);
			return JSONArray.fromObject(nodeList).toString();
		}
		return null;
	}
	
	
	
	
	/**
	 * 根据当前角色查询下一角色ID
	 * @param curentRoleId
	 * @return
	 */
	private String findNextRoleId(String curentRoleId) {

		if(Consts.ROLE_FWZGLY.equals(curentRoleId)){
			return Consts.ROLE_SQGLY;
		}
		if(Consts.ROLE_SQGLY.equals(curentRoleId)){
			return Consts.ROLE_JDXXY;
		}
		if(Consts.ROLE_JDXXY.equals(curentRoleId)){
			return Consts.ROLE_JDBLD;
		}
		if(Consts.ROLE_JDBLD.equals(curentRoleId)){
			return Consts.ROLE_YWBM;
		}
		return "error";
	}
	
	/**
	 * 自己发布的事件列表
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param applyTime
	 * @param req
	 * @return
	 */
	@RequestMapping("/myPubEvent")
	public String myPubEvent(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime, String sjly,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		List<SysParamDesc> eventFromTypeMap = sjzdService.findByPCode("10023");
		model.addAttribute("eventFromType", eventFromTypeMap);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("applyTime", applyTime);
				map.put("eventfrm", sjly);
				map.put("applyCode", acc.getAccCode()); // 事件添加人是自己
				Pagenate<Event> pagenate = eventService.findEventByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sjgl/sjgl_mypubevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[EventAction]_[myPubEvent_]查询事件信息出错", e);
		}
		return "shgl/sjgl/sjgl_mypubevent";
	}
	
	// 跳转添加事件页面
	@RequestMapping("/addEvent")
	public String addEvent(Model model) {
		List<SysParamDesc> eventType = sjzdService.findByPCode("10008"); //查询事件类别
		List<SysParamDesc> eventFromTypeMap = sjzdService.findByPCode("10023"); //查询事件来源
		model.addAttribute("eventType", eventType);
		model.addAttribute("eventFromType", eventFromTypeMap);
		return "/shgl/sjgl/add";
	}
	
	/**
	 * 跳转事件地图绘制
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseEventArea", produces = "text/html;charset=UTF-8")
	public String choseEventArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/sqgl/getSingleAreaData.do");
		// model.addAttribute("url", "/shgl/lygl/saveArea.do");
		model.addAttribute("type", "addsj"); // 新增事件
		return "shgl/choseArea";
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
//			boolean isok = PointInPolygon.isInclude(Double.parseDouble(lon),Double.parseDouble(lat),areas);
//			if(isok){
////				return commObj.getId();
//				System.out.println("aaa_所属社区Id:"+commObj.getId());
//			}
			boolean isok1 = SpatialRelationUtil.isPolygonContainsPoint(areas,new Area(Double.parseDouble(lon),Double.parseDouble(lat)));
			if(isok1){
//				System.out.println("bbb_所属社区Id:"+commObj.getId());
				return commObj.getId();
			}
		}
		return "";
	}
	
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
			boolean isok = SpatialRelationUtil.isPolygonContainsPoint(areas,new Area(Double.parseDouble(lon),Double.parseDouble(lat)));
			if(isok){
				return ssObj.getId();
			}
		}
		return "";
	}
	
	/**
	 * pc端添加保存事件
	 * @param model
	 * @param req
	 * @param title
	 * @param addr
	 * @param content
	 * @param lon
	 * @param lat
	 * @param fName
	 * @param sjlx
	 * @param sjly
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savePCReport")
	public String savePCReport(Model model,HttpServletRequest req,String fName,String title,String addr,String content,String lon,String lat,String sjlx, String sjly) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try{
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			Event event  = new Event();
			event.setTitle(title);
			event.setContent(content);
			event.setApplyAddr(addr);
			event.setApplyLat(lat);
			event.setApplyLon(lon);
			event.setApplyTime(new Date());
			event.setApplyCode(acc.getAccCode());
			event.setApplyName(acc.getName());
			event.setType(sjlx);
			event.setEventfrm(sjly);
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
			
			try{
			if(fName !=null && !"".equals(fName)){
				String[] path = fName.split(">");
				for(String p : path){
					if(!p.equals("")){
						JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
						String newFileName = jsStr.getString("newName");
						String oldFileName = jsStr.getString("oldName");
						String fileType = jsStr.getString("type").toLowerCase();
						EventAttrs eventAttr = new EventAttrs();
						eventAttr.setNewFilename(newFileName);
						eventAttr.setEventId(event.getId());
						eventAttr.setOldFilename(oldFileName);
						eventAttr.setFileType(fileType);
						eventService.saveOrUpdateAttrs(eventAttr);
					}
				}
				}
			}catch (Exception e) {
				log.error("保存附件失败!");
			}	
			EventDeal deal = new EventDeal();
			deal.setEvent(event);
			deal.setCurRoleId(Consts.ROLE_FWZGLY);
			deal.setCreateDate(new Date());
			deal.setFbrId(acc.getAccCode());
			deal.setFbrName(acc.getName());
			deal.setDealStatus(Consts.EVENT_STATUS_UNDO);
			eventService.upDateEventDeal(deal);
			//return "{\"result\":\"success\",\"pid\":\"" + event.getId() + "\"}";
			resultMap.put("result", "success");
			resultMap.put("pid", event.getId());
			return JSONObject.fromObject(resultMap).toString();
		} catch (Exception e) {
			log.error("[EventAction]_[savePCReport]_出错", e);
		}
		//return "{\"result\":\"fail\"}";
		resultMap.put("result", "fail");
		return JSONObject.fromObject(resultMap).toString();
	}
	
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids,HttpSession session) {
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		// String roleCode = accCount.getRole_code();
		log.info("事件id====" + ids);
		try {
			//sjglService.delete(ids);
			eventService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("[EventAction]_[goDel]_事件删除出错", e);
		}
		return "fail";
	}
	
}
