package com.jcwx.action.shgl;

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
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;

/**
 * @author 任务管理_gaoshuai_2018-1-20
 *
 */
@Controller
@RequestMapping("/shgl/rwgl")
public class RwglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(RwglAction.class);

	@Autowired
	private RwglService rwglService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private RwclService rwclService;
	@Autowired
	private RwtjService rwtjService;
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 社会管理--任务管理--我的下发
	 */
	@RequestMapping("/myXF")
	public String index(Model model, String ajaxCmd, String title,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpSession session) {
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
				return "shgl/rwgl/index_myXF#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[index]_查询任务管理出错。", e);
		}
		return "shgl/rwgl/index_myXF";
	}
	

	/**
	 * 社会管理--任务管理--我的下发详情
	 */
	@RequestMapping("/myXFXq")
	public String myXFXq(Model model, String ajaxCmd, String title, String taskId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) {
		
		log.info("标题模糊查询>>>>>>>"+title);
		log.info("任务ID>>>>>>>"+taskId);
		
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
				return "shgl/rwgl/index_myXFXq#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[index]_查询任务管理出错。", e);
		}
		return "shgl/rwgl/index_myXFXq";
	}

	/**
	 * 跳转添加页面
	 */
	@RequestMapping("/addTask")
	public String addTask(Model model) {
		return "/shgl/rwgl/add";
	}

	/**
	 * 待办事项
	 */
	@RequestMapping("/myReceive")
	public String myReceive(Model model, String ajaxCmd, String title,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) {
		
		log.info("标题模糊查询>>>>>>>"+title);
		
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("cur_emp", sysAccCount.getAccCode());
				Pagenate<RwClEntity> pagenate = rwclService.getRwglContent(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/rwgl/myreceive#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[myReceive]_查询任务管理出错", e);
		}
		return "shgl/rwgl/myreceive";
	}

	/**
	 * 处理记录
	 */
	@RequestMapping("/myDeal")
	public String myDeal(Model model, String ajaxCmd, String title,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) {
		
		log.info("标题模糊查询>>>>>>>"+title);
		
		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("cur_emp", sysAccCount.getAccCode());
				Pagenate<RwClEntity> pagenate = rwglService.getRwclContent(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/rwgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[myDeal]_查询任务管理出错。", e);
		}
		return "shgl/rwgl/index";
	}
	
	/**
	 * 任务汇总-奇点（2017年12月20日）
	 */
	@RequestMapping("/rwhz")
	public String rwHz(Model model, String ajaxCmd,String title,String startD,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpSession session){
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title", title);
				Map.put("startD", startD);
				Pagenate<RwglEntity> pagenate = rwglService.getRwglContent(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/rwgl/index_rwhz#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwglAction]_[rwHz]_查询任务汇总出错", e);
		}
		return "shgl/rwgl/index_rwhz";
	}

	/**
	 * 跳转处理任务页面
	 */
	@RequestMapping("/goDeal")
	public String goDeal(Model model, String dlId) {
		log.info("任务处理id>>>>>>>"+dlId);
		model.addAttribute("dlId", dlId);
		return "/shgl/rwgl/deal";
	}

	/**
	 * 任务删除
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids, HttpSession session) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String farId = accCount.getAccCode();
		
		log.info("需删除的任务id>>>>>>>" + ids);
		log.info("发布人id>>>>>>>" + farId);
		try {
			rwglService.delRwContent(ids, farId);
			return "success";
		} catch (Exception e) {
			log.error("[RwglAction]_[goDel]_任务删除出错", e);
		}
		return "fail";
	}

	/**
	 * 跳转到任务下发任务人员树
	 */
	@RequestMapping("/showTree")
	public String showTree(Model model, String delId, String ztType) {
		
		log.info("任务处理Id>>>>>>>"+delId);
		log.info("转发任务状态标识>>>>>>>"+ztType);
		
		model.addAttribute("delId", delId);
		model.addAttribute("ztType", ztType);
		return "/shgl/rwgl/membertree";
	}

	/**
	 * 加载人员树
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTreeData", produces = "text/html;charset=UTF-8")
	public String showTree(HttpSession session) {

		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String roleCode = accCount.getRole_code();// 获取当前登录人的角色
		String accCode = accCount.getAccCode();// 获取当前登录人的ID
		
		List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();
		List<ShglCommunityEntity> sqList = new ArrayList<ShglCommunityEntity>();
		if (roleCode.indexOf("12") != -1) {// 街道办领导
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
				//如果是自己过滤掉
				if(accCode.equals(te.getAccCode())){
					continue;
				}
				if(te.getRole_code().indexOf("12")!=-1){
					continue;
				}
				nodeMap.put("pId", te.getDeptId());
				nodeList.add(nodeMap);
			}
		}
		if (roleCode.indexOf("13") != -1) {// 业务部门工作人员
			// 查询社区结构
			sqList = rwtjService.sqAll();
			// 社区结构节点
			for (ShglCommunityEntity sqLists : sqList) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				String sqId = sqLists.getId();// 社区ID
				String sqName = sqLists.getName();// 社区名称
				nodeMap.put("id", sqId);
				nodeMap.put("pId", "root");
				nodeMap.put("name", sqName);
				nodeMap.put("nocheck", "true");
				nodeList.add(nodeMap);
			}
			// 添加成员
			List<SysAccCount> sqLists1 = rwglService.findByCode(Consts.ROLE_SQGLY, null,null);
			for (SysAccCount sqryLists : sqLists1) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				String id = sqryLists.getAccCode();// 社区负责人ID
				String name = sqryLists.getName();// 社区负责人姓名
				String sqId = sqryLists.getCommId();// 社区ID
				if (sqId != null && !"".equals(sqId)) {
					nodeMap.put("id", id);
					nodeMap.put("name", name);
					nodeMap.put("pId", sqId);
					nodeList.add(nodeMap);
				}
			}
		}
		if (roleCode.indexOf("03") != -1) {// 社区管理员
			String sqId = accCount.getCommId();// 当前登录人所在社区
			List<ShglServiceStationEntity> fwzList = rwglService.findFwz(sqId);
			for (ShglServiceStationEntity stationEntity : fwzList) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				String fwzId = stationEntity.getId();// 服务站ID
				String fwzName = stationEntity.getName();// 服务站名称
				nodeMap.put("id", fwzId);
				nodeMap.put("name", fwzName);
				nodeMap.put("pId", "root");
				nodeMap.put("nocheck", "true");
				nodeList.add(nodeMap);
			}
			List<SysAccCount> fwzLists = rwglService.findByCode(Consts.ROLE_FWZGLY, sqId,null);
			for (int i = 0; i < fwzLists.size(); i++) {
				SysAccCount accCount2 = fwzLists.get(i);
				Map<String, String> nodeMap = new HashMap<String, String>();
				String fwzglyId = accCount2.getAccCode();// 服务站管理员ID
				String fwzglyName = accCount2.getName();// 服务站管理员姓名
				String fwzId = accCount2.getSsId();// 服务站ID
				if (fwzId != null && !"".equals(fwzId)) {
					nodeMap.put("id", fwzglyId);
					nodeMap.put("name", fwzglyName);
					nodeMap.put("pId", fwzId);
					nodeList.add(nodeMap);
				}
			}
		}
		if (roleCode.indexOf("02") != -1) {// 服务站管理员
			String fwzId = accCount.getSsId();// 服务站ID
			List<ShglGridEntity> gridList = rwglService.findWg(fwzId);
			for (ShglGridEntity gridEntity : gridList) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				String wgId = gridEntity.getId();// 网格ID
				String wgName = gridEntity.getName();// 网格名称
				nodeMap.put("id", wgId);
				nodeMap.put("name", wgName);
				nodeMap.put("pId", "root");
				nodeMap.put("nocheck", "true");
				nodeList.add(nodeMap);
			}
			List<SysAccCount> wgyLists = rwglService.findByCode(Consts.ROLE_WGY, fwzId,"1");
			for (SysAccCount accCount2 : wgyLists) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				String wgyId = accCount2.getAccCode();// 网格员ID
				String wgyName = accCount2.getName();// 网格员姓名
				String wgId = accCount2.getGridId();// 网格Id
				if (wgId != null && !"".equals(wgId)) {
					nodeMap.put("id", wgyId);
					nodeMap.put("name", wgyName);
					nodeMap.put("pId", wgId);
					nodeList.add(nodeMap);
				}
			}
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

	/**
	 * 保存下发任务
	 */
	@ResponseBody
	@RequestMapping("/addRw")
	public String addRw(RwglEntity jsEntity, String fName, String jsrIds,
			HttpServletRequest req, HttpSession session) {
		
		log.info("任务实体>>>>>>>"+jsEntity);
		log.info("附件>>>>>>>"+fName);
		log.info("接收人ID>>>>>>>"+jsrIds);

		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		String roles = sysAccCount.getRole_code();
		String curRoleCodes = "";
		
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roles.indexOf(",")!=-1) {
			String[] roleCode = roles.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roles);//单角色直接添加
		}
		//排序保证高权限不会被低权限覆盖
		Collections.sort(arrayList);
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
		
		if(roleCode.indexOf("12") !=-1){//街道办领导
			 curRoleCodes = findNextRoleCode("12");// 当前处理人角色Code
		}
		if(roleCode.indexOf("13") !=-1){//业务部门工作人员
			 curRoleCodes = findNextRoleCode("13");
		}
		if(roleCode.indexOf("03") !=-1){//社区管理员
			 curRoleCodes = findNextRoleCode("03");
		}
		if(roleCode.indexOf("02") !=-1){//服务站管理员
			 curRoleCodes = findNextRoleCode("02");
		}
		if ("error".equals(curRoleCodes) || "".equals(curRoleCodes)) {// 判断角色
			return "error";
		}
		
		RwglEntity rwglEntity = new RwglEntity();
		rwglEntity.setTitle(jsEntity.getTitle());// 任务标题
		rwglEntity.setContent(jsEntity.getContent());// 任务内容
		rwglEntity.setFbr_id(sysAccCount.getAccCode());// 发布人ID
		rwglEntity.setFbr_name(sysAccCount.getName());// 发布人姓名
		rwglEntity.setFwz_id(sysAccCount.getSsId());// 服务站ID
		rwglEntity.setSq_id(sysAccCount.getCommId());// 社区ID
		rwglEntity.setCreate_date(new Date());// 任务下发时间
		rwglEntity.setFinish_date(jsEntity.getFinish_date());// 任务完成时间
		rwglService.save(jsrIds, fName, rwglEntity, curRoleCodes);
		return "success";
	}

	/**
	 * 下发任务
	 */
	@ResponseBody
	@RequestMapping("/goXiaoF")
	public String goXiaoF(String jsrIds, String delId, String ztType, HttpServletRequest req, HttpSession session) {
		
		log.info("下发人ID>>>>>>>"+jsrIds);
		log.info("处理任务ID>>>>>>>"+delId);

		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		String preId = sysAccCount.getAccCode();

		RwClEntity clEntity = rwclService.findRwclById(delId);
		if (clEntity != null) {
			
				clEntity.setIs_down("1");
				rwclService.save(clEntity);
				
				String[] jsrIdes = jsrIds.split(";");
				for(String curId : jsrIdes){
				SysAccCount accCount = yhglService.findSysAccCountByCode(jsrIdes[0]);
				// 任务转发
					RwClEntity clEntity2 = new RwClEntity();
					clEntity2.setRwglEntity(clEntity.getRwglEntity());
					clEntity2.setPre_emp(preId);
					clEntity2.setCur_emp(curId);
					clEntity2.setCur_role_id(accCount.getRole_code());
					clEntity2.setStarte_date(clEntity.getStarte_date());
					clEntity2.setIs_down("2");
					clEntity2.setIs_back("2");
					rwclService.save(clEntity2);
				}
		} else {
			return "error";
		}
		return "success";
	}

	/**
	 * 任务反馈
	 */
	@ResponseBody
	@RequestMapping("/doFeedback")
	public String doFeedback(String dlId, HttpServletRequest req, String content,
		   String fName, HttpSession session) {
		
		log.info("任务处理ID>>>>>>>"+dlId);
		
		RwClEntity clEntity = rwclService.findRwclById(dlId);
		if (clEntity != null) {
			clEntity.setContent(content);
			clEntity.setEnd_date(new Date());
			clEntity.setIs_back("1");
			rwclService.save(clEntity);
			rwclService.saveDealAttrs(clEntity,fName);
		} else {
			return "error";
		}
		return "success";
	}

	/**
	 * 处理详情查看
	 */
	@RequestMapping("/clView")
	public String clView(String clId, Model model, HttpServletRequest req, String content,
			HttpSession session) {
		
		log.info("任务处理ID>>>>>>>"+clId);
		
		RwClEntity rwcl = null;
		if (clId != null) {
			rwcl = rwclService.findId(clId);
		}
		model.addAttribute("rwcl", rwcl);
		return "/shgl/rwgl/clView";
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
			for(int i = 0; i < xfrLists.size()-1; i++){//下发人去重操作
				for(int j = xfrLists.size()-1; j>i ; j--){
					if(xfrLists.get(j).getPre_emp().equals(xfrLists.get(i).getPre_emp())){
						xfrLists.remove(j);
					}
				}
			}
		}
		model.addAttribute("rwgls", rwgls);
		model.addAttribute("rwLists", xfrLists);
		model.addAttribute("rwgl", rwgl);
		return "/shgl/rwgl/index_rwhzView";
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
