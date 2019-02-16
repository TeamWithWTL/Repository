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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.service.sjzx.tsrl.TsrkService;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据中心--户数统计
 *
 */

@Controller
@RequestMapping("/sjzx/hstj")
public class HstlAction {
	private Logger log = Logger.getLogger(HstlAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));

	@Autowired
	private TsrkService tsrkService;
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
			
		return "sjzx/hstj/index_hstj";
	}
	
	/**
	 * 户数统计柱状图
	 * @param request
	 * @param all
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initHstj", produces = "text/html;charset=UTF-8")
	public String initSqrk(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		SysAccCount sysAcc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = sysAcc.getAccCode();//用户id
		String role_code = sysAcc.getRole_code();//用户角色
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (role_code.indexOf(",")!=-1) {
			String[] roleCodes = role_code.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(role_code);//单角色直接添加
		}
		try {
			// 查询不同权限下的社区
			Map<String, String> addMap = new HashMap<String, String>();//社区查询条件
			String logCommId = sysAcc.getCommId();//登录人负责的社区id
			String logSsId = sysAcc.getSsId();//登录人负责的服务站id
			Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
			String isAdmin="no";
			for (int i = 0; i < arrayList.size(); i++) {
				SysRole sysRole = rwclService.findSysRoleById(arrayList.get(i));
				Integer orderNo = 0;
				if (sysRole!=null) {
					orderNo = sysRole.getOrderNo();
				}
				if (orderNo>=4&&orderNo<=6) {
					isAdmin="admin";//社区管理员以上的
				}else {
					if (orderNo==3) {
						isAdmin="shequ";//社区管理员
					}else {
						if (orderNo==2) {
							isAdmin="fuwuzhan";//服务站管理员
						}
					}
					
				}
			}
			if (isAdmin=="admin") {
				List<ShglCommunityEntity> sqList = rwtjService.sqAll();//所有社区
				JSONArray jsonArray = new JSONArray();
				for (ShglCommunityEntity ce : sqList) {
					JSONObject jsonAdd = new JSONObject();
					// 社区名称
					String sqName = ce.getName();
					String sqId = ce.getId();
					addMap.put("sqId", sqId);
					//某社区房屋出租数量
					int counts = tsrkService.findCountFwcz(addMap);
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
							String fwzName = smanagerEntity.getName();
							String fwzId = smanagerEntity.getId();
							addMap.put("fwzId", fwzId);
							
							//某服务站房屋出租数量
							int counts =  tsrkService.findCountFwcz(addMap);
							String sqrkCount = counts + "";
							jsonAdd.put("sqName", fwzName);
							jsonAdd.put("sqrkCount", sqrkCount);
							jsonArray.add(jsonAdd);
						}
						returnJson.put("result", jsonArray);
						returnJson.put("code", 200);
					}
					
				}
				if (isAdmin=="fuwuzhan") {
					String fwzId = sysAcc.getSsId();// 服务站ID
					if (logSsId!=null&&!"".equals(logSsId)) {
						List<ShglGridEntity>villList=jmxxService.findVillListByCommId(logSsId);//以服务站id 查询所有的小区list
						JSONArray jsonArray = new JSONArray();
						for (ShglGridEntity smanagerEntity : villList) {
							JSONObject jsonAdd = new JSONObject();
							// 网格名称
							String wgName = smanagerEntity.getName();
							String xq_Id = smanagerEntity.getId();
							addMap.put("xq_Id", xq_Id);
							// 某网格人口数量
							int counts = tsrkService.findCountFwcz(addMap);
							String sqrkCount = counts + "";
							jsonAdd.put("sqName", wgName);
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
			log.error("[HstjAction]_[initFwcz]_数据中心--户数统计数据加载出错", e);
		}
		return returnJson.toString();
	}
	
}
