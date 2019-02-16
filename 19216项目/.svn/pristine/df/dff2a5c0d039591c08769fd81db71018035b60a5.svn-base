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
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author 高帅--（2017年12月18日）
 * App--社会管理--楼宇管理
 */
@Controller
@RequestMapping("/app/shgl/lygl")
public class AppLyglAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppLyglAction.class);

	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private JmxxService jmxxService;
	
	/**
	 * 首页数据
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, String gridId, String xqId, HttpServletRequest req) {
		
		log.info("模糊查询名称>>>>>>>"+name);
		log.info("所属社区Id>>>>>>>"+commId);
		log.info("所属服务站Id>>>>>>>"+ssId);
		log.info("所属网格Id>>>>>>>"+gridId);
		log.info("所属小区Id>>>>>>>"+xqId);
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		//社区下拉列表只显示当前用户负责的社区
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)||"wgy".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());
		}else if ("admin".equals(isAdmin)) {//社区下拉列表显示所有小区
			paramMap.put("dqCommId", null);
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		
		List<ShglVillageEntity> xqList = xqxxService.findAllVillages();
		
		model.addAttribute("comList", comList);
		model.addAttribute("xqList", xqList);
		try {
			if (null == ajaxCmd) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();//当前用户负责的社区Id
					map.put("dqCommId", dqCommId);
				}
				if ("fuwuzhan".equals(isAdmin)) {
					String dqSsId = acc.getSsId();//当前用户负责的服务站Id
					map.put("dqSsId", dqSsId);
				}
				if ("wgy".equals(isAdmin)) {
					String dqgridId = acc.getGridId();//当前用户负责的网格Id
					map.put("dqgridId", dqgridId);
				}
				if ("admin".equals(isAdmin)) {//最高权限查所有
					map.put("dqCommId", null);
					map.put("dqSsId", null);
				}
				map.put("name", name);
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("gridId", gridId);
				map.put("xqId", xqId);
				Pagenate<ShglBuildingEntity> pagenate = lyglService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "shgl/lygl/app/app_lygl_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppLyglAction]_[index]_查询楼宇信息出错", e);
		}
		return "shgl/lygl/app/app_lygl_index";
	}
	
	/**
	 * app--服务站管理--mui_获取下一页
	 */
	@ResponseBody
	@RequestMapping("/lyglNextContent")
	public Map lyglNextContent(Model model,Integer pageNumber, String name, String commId, String serId,
			String gridId, String xqId, HttpSession session, HttpServletRequest req){
		
		log.info("模糊查询名称>>>>>>>"+name);
		log.info("所属社区Id>>>>>>>"+commId);
		log.info("所属服务站Id>>>>>>>"+serId);
		log.info("所属网格Id>>>>>>>"+gridId);
		log.info("所属小区Id>>>>>>>"+xqId);
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)||"wgy".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		
		List<ShglVillageEntity> xqList = xqxxService.findAllVillages();
		
		model.addAttribute("comList", comList);
		model.addAttribute("xqList", xqList);
		
		// 分页查询
		Map<String, String> map = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)) {
			String dqCommId = acc.getCommId();//当前用户负责的社区Id
			map.put("dqCommId", dqCommId);
		}
		if ("fuwuzhan".equals(isAdmin)) {
			String dqSsId = acc.getSsId();//当前用户负责的服务站Id
			map.put("dqSsId", dqSsId);
		}
		if ("wgy".equals(isAdmin)) {
			String dqgridId = acc.getGridId();//当前用户负责的网格Id
			map.put("dqgridId", dqgridId);
		}
		if ("admin".equals(isAdmin)) {//最高权限查所有
			map.put("dqCommId", null);
			map.put("dqSsId", null);
		}
		map.put("name", name);
		map.put("ssId", serId);
		map.put("commId", commId);
		map.put("gridId", gridId);
		map.put("xqId", xqId);
		Pagenate<ShglBuildingEntity> pagenate = lyglService.findByPage1(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<Map> addList = new ArrayList<Map>();//存放Map
		List<ShglBuildingEntity> lyLists = pagenate.getList();
		
		for(ShglBuildingEntity lyEntity : lyLists){
			Map<String, String> addMap = new HashMap<String, String>();//存放对象
			addMap.put("lyId", lyEntity.getId());//楼宇Id
			addMap.put("lyName", lyEntity.getName());//楼宇名称
			addMap.put("addName", lyEntity.getAdd_name());//添加人
			addMap.put("addTime", lyEntity.getAddTimeFrm());//添加时间
			addMap.put("sqName", lyEntity.getVillage().getServiceStation().getCommunity().getName());//所属社区
			addMap.put("fwzName", lyEntity.getVillage().getServiceStation().getName());//所属服务站
			addMap.put("wgName", lyEntity.getGrid().getName());//所属网格
			addMap.put("xqName", lyEntity.getVillage().getName());//所属小区
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
		
		log.info("查看_楼宇ID>>>>>>>"+id);
		
		ShglBuildingEntity buildingEntity = null;
		if (null != id && !"".equals(id)) {
			
			buildingEntity = lyglService.findById1(id);
			model.addAttribute("buildingEntity",buildingEntity);
			
			if (buildingEntity!=null) {
				String commId = buildingEntity.getCommId();
				String commName = sqglService.findById1(commId).getName();
				model.addAttribute("commName",commName);
			}
			
			List<ShglInmateEntity> roomList = lyglService.getYzhRoom(id);
			
			model.addAttribute("roomList", roomList);
		}
		model.addAttribute("buildingEntity", buildingEntity);
		
		return "shgl/lygl/app/app_lygl_view";
	}
	
	/**
	 * 跳转居民列表
	 */
	@RequestMapping("/goJmListView")
	public String goJmListView(Model model, String id, String unit_no, String room_no) {
		
		log.info("查看_楼宇ID>>>>>>>"+id);
		
		List<ShglInmateEntity> jmList = null;
		if (null != id && !"".equals(id)) {
			jmList = jmxxService.findJm(id, unit_no, room_no);
		}
		model.addAttribute("jmList", jmList);
		model.addAttribute("lyId", id);
		model.addAttribute("unit_no", unit_no);
		model.addAttribute("room_no", room_no);
		
		return "shgl/lygl/app/app_jmlist_view";
	}
	
	
	/**
	 * 权限控制抽取方法
	 * @author 李伟
	 * @time 2017年12月12日下午3:41:49
	 * @param roleCode
	 */
	public String jurisdiction(String roleCode) {
		
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCode.indexOf(",")!=-1) {
			String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(roleCode);//单角色直接添加
		}
		Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
		String isAdmin="no";
		for (String role : arrayList) {
			if (Consts.ROLE_JDXXY.equals(role)||Consts.ROLE_JDGLY.equals(role)
					||Consts.ROLE_DZBGZRY.equals(role)||Consts.ROLE_YWBMLD.equals(role)
					||Consts.ROLE_YWBM.equals(role)||Consts.ROLE_SHZZGLY.equals(role)
					||Consts.ROLE_JDBDFLZGLRY.equals(role)||Consts.ROLE_JDBLD.equals(role)
					||Consts.ROLE_BMCY.equals(role)||Consts.ROLE_ADMIN.equals(role)) {
				isAdmin="admin";//社区管理员以上的
			}else {
				if (Consts.ROLE_SQGLY.equals(arrayList.get(arrayList.size()-1))) {
					isAdmin="shequ";//社区管理员
				}else if(Consts.ROLE_FWZGLY.equals(arrayList.get(arrayList.size()-1))){
						isAdmin="fuwuzhan";//服务站管理员
				}else{
					if (Consts.ROLE_WGY.equals(arrayList.get(arrayList.size()-1))) {
						isAdmin="wgy";//网格员
					}
				}
			}
		}
		return isAdmin;
	}

}
