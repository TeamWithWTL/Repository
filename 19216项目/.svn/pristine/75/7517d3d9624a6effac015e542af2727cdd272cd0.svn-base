package com.jcwx.action.shgl.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.thoughtworks.xstream.alias.ClassMapper.Null;
/**
 * 任务管理app_gaoshaui_2018-1-16
 */
@Controller
@RequestMapping("/app/shgl/appRwgl")
public class AppRwglAction {
	
	private Logger log = Logger.getLogger(AppRwglAction.class);//log4j
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	
	@Autowired
	private RwclService rwclService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private YhglService yhglService;
	
	
	/**
	 * 跳转任务管理页面
	 */
	@RequestMapping("/toRwgl")
	public String toRwgl(String ajaxCmd,Model model,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
		
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)req.getSession().getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleCode = sysAccCount.getRole_code();
		model.addAttribute("roleCode", roleCode);
		model.addAttribute("accCode", accCode);
		
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (roleCode.indexOf(",")!=-1) {
			String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(roleCode);//单角色直接添加
		}
		if (arrayList.contains("01")) {//判断是不是网格员
			model.addAttribute("isWgy","yes");
		}else{
			model.addAttribute("isWgy","no");
		}
		try {
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("accCode", accCode);
				map.put("stamp", "1");//加入查询标记  1为未处理的任务 
				Pagenate<RwClEntity> pagenate = rwclService.findClByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
			
				return "/shgl/rwgl/app/app_rwgl_myreceive#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppRwglAction]_[toRwgl]_查询任务管理列表出错", e);
		}
		return "shgl/rwgl/app/app_rwgl_myreceive";
	}
	
	/**
	 *  查询待办任务
	 */
	@ResponseBody
	@RequestMapping("/noDealCount")
	public Map<String, String> noDealCount( HttpSession session,HttpServletRequest req,Model model) {
		
		Map<String,String> hMap =  new HashMap<String, String>();
		SysAccCount accCode = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String acc_code = accCode.getAccCode();
		// 分页查询用户信息
		Map<String, String> addMap = new HashMap<String, String>();
		addMap.put("accCode", acc_code);
		addMap.put("stamp", "1");//加入查询标记  1为未处理的任务 
		int rwcount = rwclService.findNoDealRwCount(addMap);
		hMap.put("rwcount", String.valueOf(rwcount));
		
		return hMap;
	}
	
	/**
	 * 查询已处理任务
	 */
	@RequestMapping("/toFinishRwgl")
	public String toFinishRwgl(String ajaxCmd,Model model,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)req.getSession().getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleCode = sysAccCount.getRole_code();
		model.addAttribute("roleCode", roleCode);
		model.addAttribute("accCode", accCode);
		try {
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("accCode", accCode);
				map.put("stamp", "2");//加入查询标记  2为已处理的任务 
				Pagenate<RwClEntity> pagenate = rwclService.findClByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
			
				return "/shgl/rwgl/app/app_rwgl_cljl#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppRwglAction]_[toRwgl]_查询任务管理列表出错", e);
		}
		return "shgl/rwgl/app/app_rwgl_cljl";
	}
	
	/**
	 * 查询下一页未处理任务
	 */
	@ResponseBody
	@RequestMapping("/rwglNextContent")
	public Map rwglNextContent(Model model,Integer pageNumber,HttpServletRequest req){
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		
		String isWgy = "";
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (roleCode.indexOf(",")!=-1) {
			String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(roleCode);//单角色直接添加
		}
		if (arrayList.contains("01")) {//判断是不是网格员
			isWgy = "yes";
		}else{
			isWgy = "no";
		}
		
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("accCode", accCode);
		map.put("stamp", "1");//加入查询标记  1为未处理的任务 
		Pagenate<RwClEntity> pagenate = rwclService.findClByPage(pageNumber, pageSize, map);
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<RwClEntity> sqfwLists = pagenate.getList();
		RwClEntity taskDealEntity = new RwClEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<sqfwLists.size(); i++){
			
		 Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 taskDealEntity = sqfwLists.get(i);
		 String titleId = taskDealEntity.getId();//标题id
		 String titleName = taskDealEntity.getRwglEntity().getTitle();//标题名称
		 String createTime = taskDealEntity.getRwglEntity().getCreate_dates();//创建时间
		 addMap.put("titleId", titleId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 addMap.put("isBack", taskDealEntity.getIs_back());
		 addMap.put("isDown", taskDealEntity.getIs_down());
		 
		 addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		returnMap.put("isWgys", isWgy);
		return returnMap;
	}
	
	/**
	 * 查询下一页，已处理任务
	 */
	@ResponseBody
	@RequestMapping("/rwglFinishlNextContent")
	public Map rwglFinishNextContent(Model model,Integer pageNumber,HttpServletRequest req){
		//获取用户信息
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("accCode", accCode);
		map.put("stamp", "2");//加入查询标记  2为已处理的任务 
		Pagenate<RwClEntity> pagenate = rwclService.findClByPage(pageNumber, pageSize, map);
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<RwClEntity> sqfwLists = pagenate.getList();
		RwClEntity taskDealEntity = new RwClEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<sqfwLists.size(); i++){
			
		 Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 taskDealEntity = sqfwLists.get(i);
		 String titleId = taskDealEntity.getRwglEntity().getId();//标题id
		 String titleName = taskDealEntity.getRwglEntity().getTitle();//标题名称
		 String createTime = taskDealEntity.getRwglEntity().getCreate_dates();//创建时间
		 addMap.put("titleId", titleId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 
		 addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 跳转下发页面并回显相关数据
	 */
	@RequestMapping("/goIssue")
	public String goIssue(String rwglId,Model model,HttpServletRequest request){
		
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		if (rwglId!=null&&!"".equals(rwglId)) {
			model.addAttribute("rwglId",rwglId);
		}
		// 获取登录用户信息
		String accCode = acc.getAccCode();
		String roleId = acc.getRole_code();
		String curRoleCodes = "";
		
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleId.indexOf(",")!=-1) {
			String[] roleCode = roleId.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleId);//单角色直接添加
		}
		Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
		String roleCode = "";
		if(arrayList.size()>=2){
			if("12".equals(arrayList.get(arrayList.size()-2))){
				roleCode = "12";
			}else{
				roleCode = arrayList.get(arrayList.size()-1);
			}
		}else{
			roleCode = arrayList.get(arrayList.size()-1);
		}
	 	List<SysAccCount> sysAccCountList = new  ArrayList<SysAccCount>();
		if(roleCode.indexOf("12") !=-1){//街道办领导
			 curRoleCodes = findNextRoleCode("12");// 当前处理人角色Code
			 Map<String,String> paramMap = new HashMap<String, String>();
			 paramMap.put("isDept", "1");
			 sysAccCountList = yhglService.findByParam(paramMap);
		}
		if(roleCode.indexOf("13") !=-1){//业务部门工作人员
			 curRoleCodes = findNextRoleCode("13");
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_SQGLY, null,null);
		}
		if(roleCode.indexOf("03") !=-1){//社区管理员
			 curRoleCodes = findNextRoleCode("03");
			 String sqId = acc.getCommId();//所在社区Id
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_FWZGLY, sqId,null);
		}
		if(roleCode.indexOf("02") !=-1){//服务站管理员
			 curRoleCodes = findNextRoleCode("02");
			 String fwzId = acc.getSsId();//所在服务站Id
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_WGY, fwzId,"1");
		}
		if ("error".equals(curRoleCodes) || "".equals(curRoleCodes)) {// 判断角色
			model.addAttribute("mapSysRole",null);
			return "shgl/rwgl/app/appIssue";
		}
		//服务站查询网格角色
		SysRole sysRole2 = rwclService.findSysRoleById(curRoleCodes);
		//按角色存放工作人员
		Map<SysRole, List<SysAccCount>> mapSysRole = new HashMap<SysRole, List<SysAccCount>>();
		List<SysAccCount> removeList = new ArrayList<SysAccCount>();
		if(sysAccCountList.size()>0){
			for(int i = 0; i < sysAccCountList.size(); i++){
				if(accCode.equals(sysAccCountList.get(i).getAccCode()) || 
						sysAccCountList.get(i).getRole_code().indexOf("12")!=-1){//清除本人和街道办领导
					removeList.add(sysAccCountList.get(i));
				}
			}
		}
		sysAccCountList.removeAll(removeList);
		mapSysRole.put(sysRole2, sysAccCountList);//已角色类为key，把角色下的用户集合放入map
		model.addAttribute("mapSysRole",mapSysRole);
		
		return "/shgl/rwgl/app/appIssue";
	}
	
	/**
	 * 向下级下发任务
	 */
	@ResponseBody
	@RequestMapping("/issue")
	public String issue(String rwglId,String content,String personIds,String curRoleId,HttpServletRequest request){
		
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();//当前登录用户id
		try {
			rwclService.issue(rwglId,content,personIds,curRoleId,accCode);
			return "success";
		} catch (Exception e) {
		
		}
		return "fail";
	}
	
	/**
	 * 代办任务详情查看
	 */
	@RequestMapping("/goView")
	public String clView(String id, Model model, HttpServletRequest req, String content, HttpSession session) {
		
		log.info("任务处理ID>>>>>>>"+id);
		
		RwClEntity rwcl = null;
		if (id != null) {
			rwcl = rwclService.findId(id);
		}
		String path = ProjectUtils.getSysCfg("appDownloadPath");
		model.addAttribute("path",path);
		model.addAttribute("taskDealEntity", rwcl);
		
		return "/shgl/rwgl/app/appDetail";
	}
	
	/**
	 * 社会管理--任务管理--我的下发--app
	 */
	@RequestMapping("/myXF")
	public String index(Model model, String ajaxCmd, String title,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) {
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		try {
			model.addAttribute("title", title);
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("fbr_id", sysAccCount.getAccCode());
				Pagenate<RwglEntity> pagenate = rwglService.getRwglMyXFList(pageNumber, pageSize, Map);
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				return "shgl/rwgl/app/app_index_myXF#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppRwglAction]_[index]_查询app任务管理出错。", e);
		}
		return "shgl/rwgl/app/app_index_myXF";
	}
	
	/**
	 * 社会管理--任务管理--我的下发详情--app
	 */
	@RequestMapping("/myXFXq")
	public String myXFXq(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title,
			String taskId, HttpSession session) {
		
		log.info("被下发任务ID>>>>>>>"+taskId);
		
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		try {
			model.addAttribute("title", title);
			model.addAttribute("rwId", taskId);
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("rwId", taskId);
				Map.put("fbr_id", sysAccCount.getAccCode());
				Pagenate<RwClEntity> pagenate = rwglService.getRwglMyContent(pageNumber, pageSize, Map);
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				return "shgl/rwgl/app/app_rwgl_myXFXq#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[myXFXq]_查询我的下发详情出错。", e);
		}
		return "shgl/rwgl/app/app_rwgl_myXFXq";
	}
	
	
	/**
	 * 查询下一页，我的下发详情
	 */
	@ResponseBody
	@RequestMapping("/nextXFXq")
	public Map nextXFXq(Model model,Integer pageNumber,HttpServletRequest req, String title,
			String taskId, HttpSession session){
		
		log.info("被下发任务ID>>>>>>>"+taskId);
		
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		//分页查询
		Map<String, String> Map = new HashMap<String, String>();
		Map.put("title", title);
		Map.put("rwId", taskId);
		Map.put("fbr_id", sysAccCount.getAccCode());
		Pagenate<RwClEntity> pagenate = rwglService.getRwglMyContent(pageNumber, pageSize, Map);
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<RwClEntity> rwglEntities = pagenate.getList();
		RwClEntity taskDealEntity = new RwClEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		
		for(int i = 0; i<rwglEntities.size(); i++){
			
		 Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 taskDealEntity = rwglEntities.get(i);
		 String rwId = taskDealEntity.getId();//任务id
		 String titleName = taskDealEntity.getRwglEntity().getTitle();//标题名称
		 String createTime = taskDealEntity.getRwglEntity().getCreate_dates();//创建时间
		 addMap.put("rwId", rwId);
		 addMap.put("titleName", titleName);
		 addMap.put("curName", taskDealEntity.getCurName());//当前处理人姓名
		 addMap.put("isBack", taskDealEntity.getIs_back());//是否已反馈
		 addMap.put("createTime", createTime);
		 
		 addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 跳转到新建下发任务界面
	 */
	@RequestMapping("/addRwgl")
	public String addRwgl(Model model,HttpServletRequest request, HttpSession session){
		
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleId = sysAccCount.getRole_code();
		String curRoleCodes = "";
		
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleId.indexOf(",")!=-1) {
			String[] roleCode = roleId.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleId);//单角色直接添加
		}
		Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
		String roleCode = "";
		if(arrayList.size()>=2){
			if("12".equals(arrayList.get(arrayList.size()-2))){
				roleCode = "12";
			}else{
				roleCode = arrayList.get(arrayList.size()-1);
			}
		}else{
			roleCode = arrayList.get(arrayList.size()-1);
		}
	 	List<SysAccCount> sysAccCountList = new  ArrayList<SysAccCount>();
		if(roleCode.indexOf("12") !=-1){//街道办领导
			 curRoleCodes = findNextRoleCode("12");// 当前处理人角色Code
			 Map<String,String> paramMap = new HashMap<String, String>();
			 paramMap.put("isDept", "1");
			 sysAccCountList = yhglService.findByParam(paramMap);
		}
		if(roleCode.indexOf("13") !=-1){//业务部门工作人员
			 curRoleCodes = findNextRoleCode("13");
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_SQGLY, null,null);
		}
		if(roleCode.indexOf("03") !=-1){//社区管理员
			 curRoleCodes = findNextRoleCode("03");
			 String sqId = sysAccCount.getCommId();//所在社区Id
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_FWZGLY, sqId,null);
		}
		if(roleCode.indexOf("02") !=-1){//服务站管理员
			 curRoleCodes = findNextRoleCode("02");
			 String fwzId = sysAccCount.getSsId();//所在服务站Id
			 sysAccCountList = rwglService.findByCode(Consts.ROLE_WGY, fwzId,"1");
		}
		if ("error".equals(curRoleCodes) || "".equals(curRoleCodes)) {// 判断角色
			model.addAttribute("mapSysRole",null);
			return "shgl/rwgl/app/app_rwgl_addRwgl";
		}
		SysRole sysRole2 = rwclService.findSysRoleById(curRoleCodes);//服务站查询网格角色
		//按角色存放工作人员
		Map<SysRole, List<SysAccCount>> mapSysRole = new HashMap<SysRole, List<SysAccCount>>();
		List<SysAccCount> removeList = new ArrayList<SysAccCount>();
		if(sysAccCountList.size()>0){
			for(int i = 0; i < sysAccCountList.size(); i++){
				if(accCode.equals(sysAccCountList.get(i).getAccCode()) || 
						sysAccCountList.get(i).getRole_code().indexOf("12")!=-1){//清除本人
					removeList.add(sysAccCountList.get(i));
				}
			}
		}
		sysAccCountList.removeAll(removeList);
		mapSysRole.put(sysRole2, sysAccCountList);//已角色类为key，把角色下的用户集合放入map
		model.addAttribute("mapSysRole",mapSysRole);
		
		return "shgl/rwgl/app/app_rwgl_addRwgl";
	}
	
	/**
	 * 保存下发任务
	 */
	@ResponseBody
	@RequestMapping("/doAddRw")
	public String doAddRw(RwglEntity jsEntity, String titleName, String rwContent, String jsrIds, String curRoleId,
			HttpServletRequest req, HttpSession session) {
		
		log.info("任务名称>>>>>>>"+titleName);
		log.info("任务内容>>>>>>>"+rwContent);
		log.info("处理人角色ID>>>>>>>"+curRoleId);
		log.info("接收人ID>>>>>>>"+jsrIds);

		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			RwglEntity rwglEntity = new RwglEntity();
			rwglEntity.setTitle(titleName);// 任务标题
			rwglEntity.setContent(rwContent);// 任务内容
			rwglEntity.setFbr_id(sysAccCount.getAccCode());// 发布人ID
			rwglEntity.setFbr_name(sysAccCount.getName());// 发布人姓名
			rwglEntity.setFwz_id(sysAccCount.getSsId());// 服务站ID
			rwglEntity.setSq_id(sysAccCount.getCommId());// 社区ID
			rwglEntity.setCreate_date(new Date());// 任务下发时间
			rwglEntity.setFinish_date(jsEntity.getFinish_date());// 任务完成时间
			rwglService.upsave(rwglEntity);
			//下发或转发任务
			String[] clrArray = jsrIds.split(";");
			for(String curId : clrArray){
				RwClEntity deal  = new RwClEntity();
				if("".equals(curId)){
					continue;
				}
				RwglEntity rwgl = new RwglEntity();
				rwgl.setId(rwglEntity.getId());//任务ID
				rwgl.setTitle(rwglEntity.getTitle());//任务标题
				deal.setRwglEntity(rwgl);
				deal.setPre_emp(sysAccCount.getAccCode());//发布人ID
				deal.setCur_emp(curId);//当前处理人ID
				deal.setCur_role_id(curRoleId);//当前处理人角色ID
				deal.setStarte_date(rwglEntity.getCreate_date());
				deal.setIs_down("2");//1是已下发，2是未下发
				deal.setIs_back("2");//1是已反馈，2是未反馈
				deal.setEnd_date(rwgl.getFinish_date());
				rwclService.save(deal);
			}
			return "{\"result\":\"success\",\"pid\":\""+rwglEntity.getId()+"\"}";
		} catch (Exception e) {
			log.error("[AppRwglAction]_[doAddRw]_出错", e);
		}
		return "{\"result\":\"fail\"}";
	}
	
	/**
	 * 查询下一页，已处理任务
	 */
	@ResponseBody
	@RequestMapping("/nextMyxf")
	public Map nextMyxf(Model model,Integer pageNumber,HttpServletRequest req, String title){
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
		Map<String, String> Map = new HashMap<String, String>();
		Map.put("title", title);
		Map.put("fbr_id", acc.getAccCode());
		Pagenate<RwglEntity> pagenate = rwglService.getRwglMyXFList(pageNumber, pageSize, Map);
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<RwglEntity> rwglEntities = pagenate.getList();
		RwglEntity taskDealEntity = new RwglEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		
		for(int i = 0; i<rwglEntities.size(); i++){
			
		 Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 taskDealEntity = rwglEntities.get(i);
		 String rwId = taskDealEntity.getId();//任务id
		 String titleName = taskDealEntity.getTitle();//标题名称
		 String createTime = taskDealEntity.getCreate_dates();//创建时间
		 addMap.put("rwId", rwId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 
		 addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 任务汇总--app
	 * 奇点（2017年12月20日）
	 */
	@RequestMapping("/toRwhz")
	public String toRwhz(Model model, String ajaxCmd,String title,String startD,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpSession session){
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("startD", startD);
				Pagenate<RwglEntity> pagenate = rwglService.getRwglContent(pageNumber, pageSize, Map);
				
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				model.addAttribute("pagenate", pagenate);
				
				return "shgl/rwgl/app/app_rwgl_rwhz#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppRwglAction]_[toRwhz]_查询任务汇总出错", e);
		}
		return "shgl/rwgl/app/app_rwgl_rwhz";
	}
	
	/**
	 * 查询下一页，任务汇总--app
	 */
	@ResponseBody
	@RequestMapping("/nextRwhz")
	public Map nextRwhz(Model model,Integer pageNumber,HttpServletRequest req, String title){
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
		Map<String, String> Map = new HashMap<String, String>();
		Map.put("title", title);
		Pagenate<RwglEntity> pagenate = rwglService.getRwglContent(pageNumber, pageSize, Map);
		
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<RwglEntity> rwglEntities = pagenate.getList();
		RwglEntity taskDealEntity = new RwglEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		
		for(int i = 0; i<rwglEntities.size(); i++){
			
		 Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 taskDealEntity = rwglEntities.get(i);
		 String rwId = taskDealEntity.getId();//任务id
		 String titleName = taskDealEntity.getTitle();//标题名称
		 String createTime = taskDealEntity.getCreate_dates();//创建时间
		 addMap.put("rwId", rwId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 
		 addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 任务汇总-处理详情查看
	 * 奇点（2017年12月20日）
	 */
	@RequestMapping("/rwhzView")
	public String rwhzXq(String rwId, Model model, HttpServletRequest req,HttpSession session) {
		
		log.info("任务ID>>>>>>>"+rwId);
		
		RwglEntity rwgl = null;
		List<RwClEntity> rwgls = null;
		List<RwClEntity> xfrLists = new ArrayList<RwClEntity>();
		if (rwId != null) {
			rwgls = rwclService.findRwcl(rwId);
			xfrLists = rwclService.findRwcl(rwId);
			rwgl = rwglService.findId(rwId);
			for(int i = 0; i < xfrLists.size()-1; i++){
				for(int j = xfrLists.size()-1; j>i ; j--){
					if(xfrLists.get(j).getPre_emp().equals(xfrLists.get(i).getPre_emp())){
						xfrLists.remove(j);
					}
				}
			}
		}
		String path = ProjectUtils.getSysCfg("appDownloadPath");
		model.addAttribute("path",path);
		model.addAttribute("rwgls", rwgls);
		model.addAttribute("rwLists", xfrLists);
		model.addAttribute("rwgl", rwgl);
		return "shgl/rwgl/app/app_rwgl_rwhzView";
	}
	
	/**
	 * 任务反馈
	 */
	@ResponseBody
	@RequestMapping(value = "/goFk",produces = "text/html;charset=UTF-8;")
	public String goFk(String id,String content,String fName){
		try {
			RwClEntity taskDealEntity = rwclService.findId(id);
			taskDealEntity.setIs_back("1");//改为已反馈
			taskDealEntity.setEnd_date(new Date());//反馈时间
			taskDealEntity.setContent(content);//反馈意见
			rwclService.upadateRc(taskDealEntity);
			return "{\"result\":\"success\",\"pid\":\""+taskDealEntity.getId()+"\"}";
		} catch (Exception e) { 
		log.error("[AppRwglAction]_[goFk]_任务反馈错误");
		}
		return "{\"result\":\"fail\"}";
	}
	
	/**
	 * 判断下级角色
	 */
	private String findNextRoleCode(String curRoleCode) {
		if (curRoleCode.equals(Consts.ROLE_JDBLD)) {
			return Consts.ROLE_BMCY;
		}
		if(curRoleCode.equals(Consts.ROLE_BMCY)){
			return Consts.ROLE_SQGLY;
		}
		if (curRoleCode.equals(Consts.ROLE_SQGLY)) {
			return Consts.ROLE_FWZGLY;
		}
		if (curRoleCode.equals(Consts.ROLE_FWZGLY)) {
			return Consts.ROLE_WGY;
		}
		return "error";
	}
}
