package com.jcwx.action.sjzx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据中心--人口数量统计
 * @author LiuMengMeng
 *
 */
@Controller
@RequestMapping("/sjzx/rksl")
public class RkslAction {
	private Logger log = Logger.getLogger(RkslAction.class);
	private static final int CHART_PAGESIZE = 8; // 柱状图每页显示条数
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private SqglService sqglService;
	@Autowired
	private RwtjService rwtjService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private RwclService rwclService;
	
	
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest req){
		 //获取当前登陆的角色
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		model.addAttribute("roleCode", acc.getRole_code());
		Map<String, String> paramMap1 = new HashMap<String, String>();
		List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
		model.addAttribute("sqList", sqList);
		model.addAttribute("pagenate", null);
		return "sjzx/rksl/index_rksl";
	}
	
	
	
	/**
	 * 社区人口统计柱状图
	 * @param pageNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initSqrk", produces = "text/html;charset=UTF-8")
	public String initSqrk(HttpServletRequest request,String all) {
		JSONObject returnJson = new JSONObject();
		SysAccCount sysAcc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = sysAcc.getAccCode();//用户id
		String role_code = sysAcc.getRole_code();//用户角色
		try {
			// 查询不同权限下的社区
			Map<String, String> comMap = new HashMap<String, String>();//社区查询条件
			String logCommId = sysAcc.getCommId();//登录人负责的社区id
			String logSsId = sysAcc.getSsId();//登录人负责的服务站id
			// 判断用户权限
			String isAdmin = jurisdiction(role_code);//高帅--（2017年12月25日修改）
			if (isAdmin=="admin") {
				List<ShglCommunityEntity> sqList = rwtjService.sqAll();
				JSONArray jsonArray = new JSONArray();
				for (ShglCommunityEntity ce : sqList) {
					JSONObject jsonAdd = new JSONObject();
					// 社区名称
					String sqName = ce.getName();
					String sqId = ce.getId();
					// 某社区人口数量
					int counts = jmxxService.findCountBySqId(sqId);
					String sqrkCount = counts + "";
					jsonAdd.put("sqName", sqName);
					jsonAdd.put("sqrkCount", sqrkCount);
					jsonArray.add(jsonAdd);
				}
				returnJson.put("result", jsonArray);
				returnJson.put("code", 200);
			}else{
				
				//查询用户所负责的社区下的，所有服务站
				if (isAdmin=="shequ") {
					if (logCommId!=null&&!"".equals(logCommId)) {
						List<ShglServiceStationEntity>ssList=jmxxService.findSsListByCommId(logCommId);//以社区id 查询所有的服务站list
						JSONArray jsonArray = new JSONArray();
						for (ShglServiceStationEntity smanagerEntity : ssList) {
							JSONObject jsonAdd = new JSONObject();
							// 服务站名称
							String sqName = smanagerEntity.getName();
							String sqId = smanagerEntity.getId();
							// 某服务站人口数量
							int counts = jmxxService.findSSBySqId(sqId);
							String sqrkCount = counts + "";
							jsonAdd.put("sqName", sqName);
							jsonAdd.put("sqrkCount", sqrkCount);
							jsonArray.add(jsonAdd);
						}
						returnJson.put("result", jsonArray);
						returnJson.put("code", 200);
					}
					
				}
				if (isAdmin=="fuwuzhan") {
					if (logSsId!=null&&!"".equals(logSsId)) {
						List<ShglGridEntity>villList=jmxxService.findVillListByCommId(logSsId);//以服务站id 查询所有的小区list
						JSONArray jsonArray = new JSONArray();
						for (ShglGridEntity smanagerEntity : villList) {
							JSONObject jsonAdd = new JSONObject();
							// 网格名称
							String sqName = smanagerEntity.getName();
							String sqId = smanagerEntity.getId();
							// 某网格人口数量
							int counts = jmxxService.findVillBySqId(sqId);
							String sqrkCount = counts + "";
							jsonAdd.put("sqName", sqName);
							jsonAdd.put("sqrkCount", sqrkCount);
							jsonArray.add(jsonAdd);
						}
						returnJson.put("result", jsonArray);
						returnJson.put("code", 200);
					}
					
				}
				}
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[RkslAction]_[initSqrk]_社区人口柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}

	/*@ResponseBody
	@RequestMapping(value = "/initSqrk", produces = "text/html;charset=UTF-8")
	public String initSqrk(HttpSession session,@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String commId,String ssId) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String roleCode = accCount.getRole_code();//当前登录的角色
		JSONObject returnJson = new JSONObject();
		log.info("所选社区ID======:" + commId);
		log.info("所选服务站ID======:"+ ssId);
		
		try {
			JSONArray jsonArray = new JSONArray();
			// 查询不同权限下的社区
			Map<String, String> comMap = new HashMap<String, String>();//社区查询条件
			comMap.put("commId", commId);
			comMap.put("ssId", ssId);
//			Pagenate<ShglInmateEntity> pagenate = tsrkService.findByPage(pageNumber,CHART_PAGESIZE,comMap);
			if( roleCode.indexOf("99") != -1|| roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1 || roleCode.indexOf("07") != -1 ||
					roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1 || roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1){
			// 查询所有的社区
			List<ShglCommunityEntity> sqList = rwtjService.sqAll();
			
			for (ShglCommunityEntity ce : sqList) {
				JSONObject jsonAdd = new JSONObject();
				// 社区名称
				String sqName = ce.getName();
				String sqId = ce.getId();
				comMap.put("sqId", sqId);
				// 某社区人口数量
				int counts = jmxxService.findCountBySqId(sqId);
				String sqrkCount = counts + "";
				jsonAdd.put("sqName", sqName);
				jsonAdd.put("sqrkCount", sqrkCount);
				jsonArray.add(jsonAdd);
			}
			}
			if(roleCode.indexOf("03") != -1){
				String sqId = accCount.getCommId();//所在社区ID
				List<ShglServiceStationEntity> fwzList = rwglService.findFwz(sqId);
				for (ShglServiceStationEntity stationEntity : fwzList) {
					JSONObject jsonAdd = new JSONObject();
					//服务站名称
					String fwzName = stationEntity.getName();
					//服务站ID
					String fwzId = stationEntity.getId();
					comMap.put("fwzId", fwzId);
					int counts = jmxxService.findSSBySqId(sqId);
					String sqrkCount = counts + "";
					jsonAdd.put("name", fwzName);
					jsonAdd.put("sqrkCount", sqrkCount);
					jsonArray.add(jsonAdd);
				}
			}
//			returnJson.put("pagenate", pagenate);
			returnJson.put("result", jsonArray);
			returnJson.put("code", 200);
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[RkslAction]_[initSqrk]_数据中心--人口数量柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}*/
	
	
	/**
	 * 权限控制抽取方法
	 * 
	 * @author 李伟
	 * @time 2017年12月12日下午3:41:49
	 * @param roleCode
	 * @return
	 */
	public String jurisdiction(String roleCode) {
		ArrayList<String> arrayList = new ArrayList<String>();// 用户角色，保存list
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
				} else {
					if (Consts.ROLE_FWZGLY.equals(arrayList.get(arrayList.size() - 1))) {
						isAdmin = "fuwuzhan";// 服务站管理员
					}
				}
			}
		}

		return isAdmin;
	}
}
