package com.jcwx.action.sjzx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 *	数据中心--特殊人口统计
 * LiuMengMeng
 */
@Controller
@RequestMapping("/sjzx/tsrk")
public class TsrkAction {
	
	private Logger log = Logger.getLogger(TsrkAction.class);
	private static final int CHART_PAGESIZE = 8; // 柱状图每页显示条数

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private RwclService rwclService;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest req){
		 //获取当前登陆的角色
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		model.addAttribute("roleCode", acc.getRole_code());
			
		Map<String, String> paramMap1 = new HashMap<String, String>();
		List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
		model.addAttribute("sqList", sqList);
		 
		model.addAttribute("pagenate", null);
		return "sjzx/tsrk/index_tsrk";
	}
	
	/**
	 * 加载特殊人口统计图
	 */
	
	@ResponseBody
	@RequestMapping(value = "/initTsrkBar", produces = "text/html;charset=UTF-8")
	public String initTsrkBar(HttpServletRequest request,String all) {
		SysAccCount sysAcc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String role_code = sysAcc.getRole_code();
		
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (role_code.indexOf(",")!=-1) {
			String[] roleCodes = role_code.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(role_code);//单角色直接添加
		}
		Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
		String isAdmin="no";
		if ("04".equals(arrayList.get(arrayList.size()-1))||"05".equals(arrayList.get(arrayList.size()-1))
				||"06".equals(arrayList.get(arrayList.size()-1))||"07".equals(arrayList.get(arrayList.size()-1))
				||"08".equals(arrayList.get(arrayList.size()-1))||"09".equals(arrayList.get(arrayList.size()-1))
				||"10".equals(arrayList.get(arrayList.size()-1))||"12".equals(arrayList.get(arrayList.size()-1))
				||"13".equals(arrayList.get(arrayList.size()-1))||"99".equals(arrayList.get(arrayList.size()-1))) {
			isAdmin="admin";//社区管理员以上的
		}else {
			if ("03".equals(arrayList.get(arrayList.size()-1))) {
				isAdmin="shequ";//社区管理员
			}else {
				if ("02".equals(arrayList.get(arrayList.size()-1))) {
					isAdmin="fuwuzhan";//服务站管理员
				}
			}
		}
		
		JSONObject returnJson = new JSONObject();
		try {
				JSONArray jsonArray = new JSONArray();
				List <SysParamDesc>  ListType = sjzdService.findByPCode("10005");	//人员分类
				
			
				for (SysParamDesc sysParamDesc : ListType) {
					JSONObject jsonObject = new JSONObject();
					String  itemCode = sysParamDesc.getValue1();//人员分类ID
					String itemName = sysParamDesc.getItemName();//人员分类名字
					Map<String, String> map = new HashMap<String, String>();
					if ("admin".equals(isAdmin)) {//社区负责人以上人员，查询所有区的
						map.put("itemCode", itemCode);
					}else{
						if ("shequ".equals(isAdmin)) {//社区负责人，查询社区的
							String commId = sysAcc.getCommId();
							map.put("itemCode", itemCode);
							map.put("commId", commId);
						}else {
							if ("fuwuzhan".equals(isAdmin)) {//服务站负责人，查询服务站的
								String ssId = sysAcc.getSsId();
								map.put("itemCode", itemCode);
								map.put("ssId", ssId);
							}
						}
					}
					int tsrkCount=jmxxService.findInmateById(map);//查单一分类的居民数量
					jsonObject.put("name", itemName);
					jsonObject.put("count", tsrkCount );
					jsonArray.add(jsonObject);
					//tsrkMap.put(itemName, itemCode);//以name为Key 
				}
				//Collections.sort(jsonArray);
				
				//排序
			    JSONArray sortedJsonArray = new JSONArray();
			    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
			    for (int i = 0; i < jsonArray.size(); i++) {
			        jsonValues.add(jsonArray.getJSONObject(i));
			    }
			    Collections.sort( jsonValues, new Comparator<JSONObject>() {
			        //You can change "Name" with "ID" if you want to sort by ID
			        private static final String KEY_NAME = "name";
			        @Override
			        public int compare(JSONObject a, JSONObject b) {
			            String valA = new String();
			            String valB = new String();
			            try {
			                valA = (String) a.get(KEY_NAME);
			                valB = (String) b.get(KEY_NAME);
			            } 
			            catch (JSONException e) {
			                //do something
			            }
			            return -valA.compareTo(valB);
			            //if you want to change the sort order, simply use the following:
			            //return -valA.compareTo(valB);
			        }
			    });
			    for (int i = 0; i < jsonArray.size(); i++) {
			        sortedJsonArray.add(jsonValues.get(i));
			    }
				returnJson.put("result", sortedJsonArray);
				returnJson.put("code", 200);
		
			
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("加载特殊人口统计图出错", e);
		}
		return returnJson.toString();
	}
}