package com.jcwx.action.pub;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.service.dflz.DzywService;
import com.jcwx.service.dflz.ExposureService;
import com.jcwx.service.pub.OrganizationService;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqmyService;
import com.jcwx.service.shzz.DtbbService;
import com.jcwx.service.shzz.ZxzmService;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.service.sjzx.tsrl.TsrkService;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author MaBo 2017年7月21日 系统首页
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/home")
public class HomeAction {

	private Logger log = Logger.getLogger(HomeAction.class);
	private static final int CHART_PAGESIZE = 8; // 柱状图每页显示条数
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize")); // 每页最大条数
	//事件类别，名和id 对应存放Map
	Map<String, String> sjlbMap = new HashMap<String, String>();
	//事件统计，社区名和id 对应存放Map
	Map<String, String> sqMap = new HashMap<String, String>();
	//事件统计，服务站名和id 对应存放Map
	Map<String, String> fwzMap = new HashMap<String, String>();
	//事件统计，网格名和id 对应存放Map
	Map<String, String> wgMap = new HashMap<String, String>();
	//事件来源，名和id 对应存放Map
	Map<String, String> sjlyMap = new HashMap<String, String>();

	@Autowired
	private RwtjService rwtjService;
	@Autowired
	private ZxzmService zxzmService;
	@Autowired
	private RdxwService rdxwService;
	@Autowired
	private DzywService dzywService;
	@Autowired
	private ExposureService exposureService;
	@Autowired
	private DtbbService dtbbService;
	@Autowired
	private EventService eventService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private SqmyService sqmyService;
	@Autowired
	private RwclService rwclService;
	@Autowired
	private TsrkService tsrkService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private OrganizationService orgService;

	
	/**
	 * 首页--跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("listIndex", null);
		model.addAttribute("listsSy", null);
		model.addAttribute("lists", null);
		model.addAttribute("addMap", null);
		model.addAttribute("mySjdbCnt", 0);
		model.addAttribute("myRwdbCnt", 0);
		return "home/home";
	}

	/**
	 * 首页--热点关注
	 * @param model
	 * @return
	 */
	@RequestMapping("/initRdgz")
	public String initRdgz(Model model, String ajaxCmd) {
		try {
			Map<String, String> addMap = new HashMap<String, String>();
			//查询所有已通过审核并且是热点的新闻
			List<RdxwEntity> rdxwList = rdxwService.getRdxwContentList(addMap);

			if (null != rdxwList && rdxwList.size() != 0) {
				for (int j = 0; j < rdxwList.size(); j++) {
					RdxwEntity rde = rdxwList.get(j);
					List<RdxwArrtsEntity> attrsRDE = rde.getAttrList();
					for (int i = 0; i < attrsRDE.size(); i++) {// 获取图片附件的第一张图片
						RdxwArrtsEntity attr = attrsRDE.get(i);
						if ("img".equals(attr.getFileType())) {
							int dot = attr.getNewFileName().lastIndexOf(".");
							dot = dot >= 0 ? dot : 0;
							String suffix = attr.getNewFileName().substring(dot);
							String name = attr.getNewFileName().substring(0, dot);
							String ysPice = name + "_1" + suffix;
							rde.setYsPice(ysPice);
							break;
						}
					}
				}
				if (rdxwList.size() > 1) {
					RdxwEntity listIndex = null;
					List<RdxwEntity> listsSy = new ArrayList<RdxwEntity>();
					for(int n = 0; n < rdxwList.size(); n++){
						String isTop = rdxwList.get(n).getIsTop();
						if("1".equals(isTop)){
							 listIndex = rdxwList.get(n);// 取置顶展示（展示图片的）
							 rdxwList.remove(n);// 移除
						}
					}
					if(listIndex == null){
						listIndex = rdxwList.get(0);
						rdxwList.remove(0);// 移除
					}
					listsSy = rdxwList;
					model.addAttribute("listIndex", listIndex);
					model.addAttribute("listsSy", listsSy);
				}else if(rdxwList.size() == 1) {
					RdxwEntity listIndex = rdxwList.get(0);// 取第一条展示（展示图片的）
					model.addAttribute("listIndex", listIndex);
					model.addAttribute("listsSy", null);
				}
			} else {
				model.addAttribute("listIndex", null);
				model.addAttribute("listsSy", null);
				log.info("[HomeAction]_[initRdgz]_没有热点新闻信息");
			}
			return "home/home#" + ajaxCmd;
		} catch (Exception e) {
			log.error("[HomeAction]_[initRdgz]_查询热点关注信息出错", e);
		}
		return "home/home";
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
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (role_code.indexOf(",")!=-1) {
			String[] roleCodes = role_code.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(role_code);//单角色直接添加
		}
		try {
			// 查询不同权限下的社区
			Map<String, String> comMap = new HashMap<String, String>();//社区查询条件
			String logCommId = sysAcc.getCommId();//登录人负责的社区id
			String logSsId = sysAcc.getSsId();//登录人负责的服务站id
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
			if ("admin".equals(isAdmin)) {
				List<ShglCommunityEntity> sqList = rwtjService.sqAll();
				if (all!="1"&&!"1".equals(all)) {//页面传的值，用来判断是显示前四，还是显示全部
					if (sqList.size()>4) {
						sqList = sqList.subList(0, 4);
					}
				}
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
					jsonAdd.put("id", sqId);
					jsonArray.add(jsonAdd);
				}
				returnJson.put("result", jsonArray);
				returnJson.put("code", 200);
			}else{
				
				//查询用户所负责的社区下的，所有服务站
				if ("shequ".equals(isAdmin)) {
					if (logCommId!=null&&!"".equals(logCommId)) {
						List<ShglServiceStationEntity> ssList = jmxxService.findSsListByCommId(logCommId);//以社区id 查询所有的服务站list
						if (all!="1"&&!"1".equals(all)) {//页面传的值，用来判断是显示前五，还是显示全部
							if (ssList.size()>5) {
								ssList = ssList.subList(0, 5);
							}
						}
						
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
							jsonAdd.put("id", sqId);
							//sqrkMap.put(sqName+"fwz", sqId);
							jsonArray.add(jsonAdd);
						}
						returnJson.put("result", jsonArray);
						returnJson.put("code", 200);
					}
					
				}
				if ("fuwuzhan".equals(isAdmin)) {
					if (logSsId!=null&&!"".equals(logSsId)) {
						List<ShglGridEntity>villList=jmxxService.findVillListByCommId(logSsId);//以服务站id 查询所有的小区list
						if (all!="1"&&!"1".equals(all)) {//页面传的值，用来判断是显示前五，还是显示全部
							if (villList.size()>5) {
								villList = villList.subList(0, 5);
							}
						}
						JSONArray jsonArray = new JSONArray();
						for (ShglGridEntity smanagerEntity : villList) {
							JSONObject jsonAdd = new JSONObject();
							// 网格名称
							String sqName = smanagerEntity.getName();
							String sqId = smanagerEntity.getId();
							// 某网格人口数量
							int counts = jmxxService.findVillBySqId(sqId);
							String sqrkCount = counts + "";
							jsonAdd.put("id", sqId);
							jsonAdd.put("sqName", sqName);
							jsonAdd.put("sqrkCount", sqrkCount);
							//sqrkMap.put(sqName+"wg", sqId);
							jsonArray.add(jsonAdd);
						}
						returnJson.put("result", jsonArray);
						returnJson.put("code", 200);
					}
					
				}
				}
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[HomeAction]_[initSqrk]_社区人口柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}

	/**
	 * 事件统计饼状图
	 */
	@ResponseBody
	@RequestMapping(value = "/initSjtj", produces = "text/html;charset=UTF-8")
	public String initSjtj(HttpSession session,String all) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		//获取当前登陆人的角色
		String roleCodes = accCount.getRole_code();
		JSONObject returnJson = new JSONObject();
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
			String roleCode = arrayList.get(arrayList.size()-1);
			JSONArray jsonArray = new JSONArray();
			Map<String, String> addMap = new HashMap<String, String>();
			if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
					|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
					|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
					|| roleCode.indexOf("13") != -1) {
				// 查询所有的社区
				List<ShglCommunityEntity> sqList = rwtjService.sqAll();
				if (all == null || "".equals(all)) {//页面传的值，用来判断是显示前五，还是显示全部
					if (sqList.size()>5) {
						sqList = sqList.subList(0, 5);
					}
				}
				for (ShglCommunityEntity ce : sqList) {
					JSONObject jsonAdd = new JSONObject();
					String sqName = ce.getName();// 社区名称
					String sqId = ce.getId();// 社区id
					addMap.put("sqId", sqId);
					int sjCount = eventService.findEventCntById(addMap);// 每个社区的人口
					jsonAdd.put("id", sqId);
					jsonAdd.put("name", sqName);
					jsonAdd.put("count", sjCount);
					jsonArray.add(jsonAdd);
				}
			}
			if (roleCode.indexOf("03") != -1) {
				String sqId = accCount.getCommId();// 所在社区ID
				List<ShglServiceStationEntity> fwzList = rwglService.findFwz(sqId);
				if (all == null || "".equals(all)) {//页面传的值，用来判断是显示前五，还是显示全部
					if (fwzList.size()>5) {
						fwzList = fwzList.subList(0, 5);
					}
				}
				for (ShglServiceStationEntity stationEntity : fwzList) {
					JSONObject jsonAdd = new JSONObject();
					String fwzName = stationEntity.getName();// 服务站名称
					String fwzId = stationEntity.getId();// 服务站ID
					addMap.put("fwzId", fwzId);
					int sjCount = eventService.findEventCntById(addMap);// 每个服务站的人口
					jsonAdd.put("id", fwzId);
					jsonAdd.put("name", fwzName);
					jsonAdd.put("count", sjCount);
					jsonArray.add(jsonAdd);
				}
			}
			if (roleCode.indexOf("02") != -1) {
					String fwzId = accCount.getSsId();// 服务站ID
					ShglServiceStationEntity stationEntity = rwglService.getFwz(fwzId);
					String fwzName = stationEntity.getName();//服务站名称
					JSONObject jsonAdd = new JSONObject();
					addMap.put("fwzId", fwzId);
					int sjCount = eventService.findEventCntById(addMap);// 所在服务站的人口
					jsonAdd.put("id", fwzId);
					jsonAdd.put("name", fwzName);
					jsonAdd.put("count", sjCount);
					jsonArray.add(jsonAdd);
			}
			returnJson.put("result", jsonArray);
			returnJson.put("code", 200);
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[HomeAction]_[initSjtj]_事件柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}
	
	/**
	 * 事件类别统计柱状图
	 */
	@ResponseBody
	@RequestMapping(value = "/initLbtj", produces = "text/html;charset=UTF-8")
	public String initLbtj(HttpSession session,String all) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		// 获取当前登陆人的角色
		String roleCodes = accCount.getRole_code();
		//所有事件类别
		List<SysParamDesc> paramDescs = sjzdService.findByPCode(Consts.EVENT_TYPE);
		if(null == all || "".equals(all)){
			if(paramDescs.size()>5){
				paramDescs = paramDescs.subList(0, 5);//取前五条
			}
		}
		
		JSONObject returnJson = new JSONObject();
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
			String roleCode = arrayList.get(arrayList.size()-1);
			JSONArray jsonArray = new JSONArray();
			Map<String, String> addMap = new HashMap<String, String>();
				// 查询所有的事件类别
				for (SysParamDesc ce : paramDescs) {
					JSONObject jsonAdd = new JSONObject();
					String lbName = ce.getItemName();// 事件类别名称
					String typeId = ce.getItemCode();// 事件类别Id
					if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
							|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
							|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
							|| roleCode.indexOf("13") != -1) {
						addMap.put("typeId", typeId);
					}
					if(roleCode.indexOf("03") != -1){
						String sqId = accCount.getCommId();//所在社区ID
						addMap.put("typeId", typeId);
						addMap.put("sqId", sqId);
					}
					if(roleCode.indexOf("02") != -1){
						String fwzId = accCount.getSsId();//所在服务站ID
						addMap.put("typeId", typeId);
						addMap.put("fwzId", fwzId);
					}
					int sjCount = eventService.findEventCntById(addMap);// 每类事件的数量
					jsonAdd.put("id", typeId);
					jsonAdd.put("name", lbName);
					jsonAdd.put("count", sjCount);
					jsonArray.add(jsonAdd);
				}
			returnJson.put("result", jsonArray);
			returnJson.put("code", 200);
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[HomeAction]_[initSjtj]_事件柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}
	
	/**
	 * 特殊人口
	 * @author 李伟
	 * @time 2017年12月1日上午12:18:11
	 * @param request
	 * @return
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
				if (all!="1"&&!"1".equals(all)) {//页面传的值，用来判断是显示前五，还是显示全部
					if (ListType.size()>5) {
						ListType = ListType.subList(0, 5);
					}
				}
			
				for (SysParamDesc sysParamDesc : ListType) {
					JSONObject jsonObject = new JSONObject();
					String itemCode = sysParamDesc.getValue1();//人员分类ID
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
					jsonObject.put("id", itemCode);
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
	/**
	 * 热点新闻查询
	 * @author 李伟
	 * @time 2017年11月30日上午9:49:26
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@RequestMapping("/rdList")
	public String rdList(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		
		try{
			if(ajaxCmd==null){
				model.addAttribute("pagenate",null);
			}else{
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("title", title);
				cxMap.put("code", "app");//只查询审核通过的，
				Pagenate<RdxwEntity> pagenate = rdxwService.getRdxwContent(pageNumber, pageSize, cxMap);
				model.addAttribute("title",title);
				model.addAttribute("pagenate", pagenate);
				return "/home/rdxwIndex#" + ajaxCmd;
			}
		}catch (Exception e) {
			log.error("查询热点新闻出错！");
		}
		return "/home/rdxwIndex";
	}
	
	/**
	 * 事件类别列表
	 */
	@RequestMapping("/sjlbList")
	public String sjlbList(Model model, String ajaxCmd,String sjName,String startDate, String endDate,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, HttpSession session) {
		
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		// 获取当前登陆人的角色
		String roleCodes = sysAccCount.getRole_code();
		//默认--获取三个月前的今天
		Map<String, String> dateMap = getThreeDate();
		if("".equals(startDate) || null == startDate){
			startDate = dateMap.get("firstTime");
		}
		if("".equals(endDate) || null == endDate){
			endDate = dateMap.get("lastTime");
		}
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("sjName", sjName);
				model.addAttribute("firstTime", startDate);
				model.addAttribute("lastTime", endDate);
			} else {
				Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
				String roleCode = arrayList.get(arrayList.size()-1);
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("startDate", startDate);
				cxMap.put("endDate", endDate);
				String sjTypeId =sjName;//事件类别ID
				Pagenate<Event> pagenate = null;
				if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
						|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
						|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
						|| roleCode.indexOf("13") != -1){
					cxMap.put("title", title);
					cxMap.put("sjTypeId", sjTypeId);//事件类别ID
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("03") != -1){
					String sqId = sysAccCount.getCommId();//当前登录人所在社区ID
					cxMap.put("title", title);
					cxMap.put("sqId", sqId);
					cxMap.put("sjTypeId", sjTypeId);//事件类别ID
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("02") != -1){
					String fwzId = sysAccCount.getSsId();//当前登录人所在服务站ID
					cxMap.put("title", title);
					cxMap.put("fwzId", fwzId);
					cxMap.put("sjTypeId", sjTypeId);//事件类别ID
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				model.addAttribute("pagenate", pagenate);
				return "/home/lbtj_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询事件出错！");
		}
		return "/home/lbtj_index";
	}
	
	/**
	 * 事件统计列表
	 */
	@RequestMapping("/sjtjList")
	public String sjtjList(Model model, String ajaxCmd,String sjtjName,String startDate, String endDate,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, HttpSession session) {
		
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		// 获取当前登陆人的角色
		String roleCodes = sysAccCount.getRole_code();
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		//默认--获取三个月前的今天
		Map<String, String> dateMap = getThreeDate();
		if("".equals(startDate) || null == startDate){
			startDate = dateMap.get("firstTime");
		}
		if("".equals(endDate) || null == endDate){
			endDate = dateMap.get("lastTime");
		}
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("sjtjName", sjtjName);
				model.addAttribute("firstTime", startDate);
				model.addAttribute("lastTime", endDate);
			} else {
				Collections.sort(arrayList);//排序保证高权限不会被低权限覆盖
				String roleCode = arrayList.get(arrayList.size()-1);
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("startDate", startDate);
				cxMap.put("endDate", endDate);
				Pagenate<Event> pagenate = null;
				if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
						|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
						|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
						|| roleCode.indexOf("13") != -1){
					String xqId = sjtjName;
					cxMap.put("title", title);
					cxMap.put("sqId", xqId);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("03") != -1){
					String sqId = sysAccCount.getCommId();//社区ID
					cxMap.put("title", title);
					cxMap.put("sqId", sqId);
					cxMap.put("fwzId", sjtjName);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("02") != -1){
					String fwzId = sysAccCount.getSsId();//服务站ID
					cxMap.put("title", title);
					cxMap.put("fwzId", sjtjName);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				model.addAttribute("pagenate", pagenate);
				return "/home/sjtj_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询事件出错！");
		}
		return "/home/sjtj_index";
	}
	
	/**
	 * 事件来源列表
	 */
	@RequestMapping("/sjlyList")
	public String sjlyList(Model model, String ajaxCmd,String sjlyName,String startDate, String endDate,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, HttpSession session) {
		
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		// 获取当前登陆人的角色
		String roleCodes = sysAccCount.getRole_code();
		//默认--获取三个月前的今天
		Map<String, String> dateMap = getThreeDate();
		if("".equals(startDate) || null == startDate){
			startDate = dateMap.get("firstTime");
		}
		if("".equals(endDate) || null == endDate){
			endDate = dateMap.get("lastTime");
		}
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("sjlyName", sjlyName);
				model.addAttribute("firstTime", startDate);
				model.addAttribute("lastTime", endDate);
			} else {
				Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
				String roleCode = arrayList.get(arrayList.size()-1);
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("startDate",startDate);
				cxMap.put("endDate",endDate);
				Pagenate<Event> pagenate = null;
				if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
						|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
						|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
						|| roleCode.indexOf("13") != -1){
					cxMap.put("title", title);
					cxMap.put("sjlyId",sjlyName);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("03") != -1){
					String sqId = sysAccCount.getCommId();//社区ID
					cxMap.put("title", title);
					cxMap.put("sqId", sqId);
					cxMap.put("sjlyId",sjlyName);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				if(roleCode.indexOf("02") != -1){
					String fwzId = sysAccCount.getSsId();//服务站ID
					cxMap.put("title", title);
					cxMap.put("fwzId", fwzId);
					cxMap.put("sjlyId",sjlyName);
					pagenate = eventService.getSjContent(pageNumber, pageSize, cxMap);
				}
				model.addAttribute("pagenate", pagenate);
				return "/home/sjly_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[HomeAction]_[sjlyList]_查询事件出错！");
		}
		return "/home/sjly_index";
	}
	
	/**
	 * 事件来源统计图
	 */
	@ResponseBody
	@RequestMapping(value = "/initSjly", produces = "text/html;charset=UTF-8")
	public String initSjly(HttpSession session, String lyId,String sqid,String fwzid) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		// 获取当前登陆人的角色
		String roleCodes = accCount.getRole_code();
		JSONObject returnJson = new JSONObject();
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
		if (roleCodes.indexOf(",")!=-1) {
			String[] roleCode = roleCodes.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
		}else {
			arrayList.add(roleCodes);//单角色直接添加
		}
		try {
			Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
			String roleCode = arrayList.get(arrayList.size()-1);
			JSONArray jsonArray = new JSONArray();
			Map<String, String> addMap = new HashMap<String, String>();
			List<SysParamDesc> lyList = sjzdService.findByPCode(Consts.EVENT_FROM_TYPE);
				// 查询所有的事件类别
				for (SysParamDesc ce : lyList) {
					JSONObject jsonAdd = new JSONObject();
					String lyName = ce.getItemName();// 事件来源名称
					String comeId = ce.getItemCode();// 事件来源Id
					if (roleCode.indexOf("04") != -1 || roleCode.indexOf("05") != -1 || roleCode.indexOf("06") != -1
							|| roleCode.indexOf("07") != -1 || roleCode.indexOf("08") != -1 || roleCode.indexOf("09") != -1
							|| roleCode.indexOf("10") != -1 || roleCode.indexOf("12") != -1 || roleCode.indexOf("99") != -1
							|| roleCode.indexOf("13") != -1) {
						addMap.put("comeId", comeId);
						//数据中心---事件来源统计查询条件--社区id，服务站id(2017.12.4-xushu)
						addMap.put("sqId", sqid);
						addMap.put("fwzId", fwzid);
					}
					if(roleCode.indexOf("03") != -1){
						String sqId = accCount.getCommId();//所在社区ID
						addMap.put("comeId", comeId);
						addMap.put("sqId", sqId);  
						//数据中心---事件来源统计查询条件--服务站id(2017.12.4-xushu)
						addMap.put("fwzId", fwzid);
					}
					if(roleCode.indexOf("02") != -1){
						String fwzId = accCount.getSsId();//所在服务站ID
						addMap.put("comeId", comeId);
						addMap.put("fwzId", fwzId);
					}
					int sjCount = eventService.findEventCntById(addMap);// 每类事件的数量
					jsonAdd.put("id", comeId);
					jsonAdd.put("name", lyName);
					jsonAdd.put("count", sjCount);
					jsonArray.add(jsonAdd);
				}
			returnJson.put("result", jsonArray);
			returnJson.put("code", 200);
		} catch (Exception e) {
			returnJson.put("code", 500);
			log.error("[HomeAction]_[initSjtj]_事件柱状统计数据加载出错", e);
		}
		return returnJson.toString();
	}
	
	/**
	 * 社区人员全图
	 * @author 李伟
	 * @time 2017年12月1日上午10:38:10
	 * @param all
	 * @param model
	 * @return
	 */
	@RequestMapping("/sqrktj")
	public String goSqrk(String all,Model model){
		model.addAttribute("all",all);
		return "/home/rktjIndex";
	}
	/**
	 * 特殊人口全图
	 * @author 李伟
	 * @time 2017年12月1日上午10:37:33
	 * @param all
	 * @param model
	 * @return
	 */
	@RequestMapping("/tsrk")
	public String goTsrk(String all,Model model){
		model.addAttribute("all",all);
		return "/home/tsrkIndex";
	}

	/**
	 * 特殊人口分类，查询
	 * @author 李伟
	 * @time 2017年12月1日下午1:41:40
	 * @param model
	 * @param ajaxCmd
	 * @param tsName
	 * @param pageNumber
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/tsrkList")
	public String tsrkList(Model model, String ajaxCmd,String tsId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) throws UnsupportedEncodingException {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("tsId",tsId);
		String roleCode = sysAccCount.getRole_code();// 获取当前登陆人的角色
		
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		if (roleCode.indexOf(",")!=-1) {
			String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(roleCode);//单角色直接添加
		}
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
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族
		model.addAttribute("sysStations", sysStations);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> cxMap = new HashMap<String, String>();
				
				Pagenate<ShglInmateEntity> pagenate = null;
				if (isAdmin=="admin") {//社区负责人以上人员，查询所有区的
					cxMap.put("itemCode", tsId);
				}else{
					if (isAdmin=="shequ") {//社区负责人，查询社区的
						String commId = sysAccCount.getCommId();
						cxMap.put("itemCode", tsId);
						cxMap.put("commId", commId);
					}else {
						if (isAdmin=="fuwuzhan") {//服务站负责人，查询服务站的
							String ssId = sysAccCount.getSsId();
							cxMap.put("itemCode", tsId);
							cxMap.put("ssId", ssId);
						}
					}
				}
					pagenate = jmxxService.getTsrkContent(pageNumber, pageSize, cxMap);
				model.addAttribute("pagenate", pagenate);
				return "/home/tsrkClassifyIndex#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询事件出错！");
		}
		return "/home/tsrkClassifyIndex";
	}
	

	
	@RequestMapping("/sjlb")
	public String goSjlb(String all,Model model){
		model.addAttribute("all",all);
		return "/home/lbtj";
	}
	
	@RequestMapping("/sjtj")
	public String goSjtj(String all,Model model){
		model.addAttribute("all",all);
		return "/home/sjtj";
	}
	
	/**
	 * 社区人口列表
	 * @author 李伟
	 * @time 2017年12月1日下午4:59:06
	 * @param model
	 * @param ajaxCmd
	 * @param sqName
	 * @param pageNumber
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sqrkList")
	public String sqrkList(Model model, String ajaxCmd,String sqName,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, HttpSession session) throws UnsupportedEncodingException {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("sqName",sqName);
		//sqName = new String(sqName.getBytes("ISO-8859-1"),"UTF-8");
		SysAccCount sysAcc = (SysAccCount) session.getAttribute("sysAccCount");
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		String role_code = sysAcc.getRole_code();
		if (role_code.indexOf(",")!=-1) {
			String[] roleCodes = role_code.split(",");//当前登录人员角色,多角色，转存list
			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
		}else {
			arrayList.add(role_code);//单角色直接添加
		}
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
		String sqId = null;
		String fwzId = null;
		String wgId = null;
		if (isAdmin=="admin") {
			sqId = sqName;//取出服务站或网格id
		}else{
			if (isAdmin=="shequ") {
				fwzId = sqName;
			}
			if (isAdmin=="fuwuzhan") {
				wgId = sqName;
			}
		}
		
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族
		model.addAttribute("sysStations", sysStations);
		
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("sqId", sqId);
				cxMap.put("fwzId", fwzId);
				cxMap.put("wgId", wgId);
				Pagenate<ShglInmateEntity> pagenate = jmxxService.getSqrkContent(pageNumber, pageSize, cxMap);
				model.addAttribute("pagenate", pagenate);
				return "/home/sqrkClassifyIndex#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询事件出错！");
		}
		return "/home/sqrkClassifyIndex";
	}
	/**
	 * 查询人员分类id
	 * @author 李伟
	 * @time 2017年12月1日下午5:45:06
	 * @param tsName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findClassifyByName")
	public String findClassifyByName(String tsName){
		String tsrkClassifyId=jmxxService.findClassifyByName(tsName);
		return tsrkClassifyId;
	}
	/***
	 * 查询社区，服务站，网格Id
	 * @author 李伟
	 * @time 2017年12月2日上午5:23:21
	 * @param sqName
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("/findsqIdByName")
//	public String findsqIdByName(String sqName,HttpSession session){
//		SysAccCount sysAcc = (SysAccCount) session.getAttribute("sysAccCount");
//		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
//		String role_code = sysAcc.getRole_code();
//		if (role_code.indexOf(",")!=-1) {
//			String[] roleCodes = role_code.split(",");//当前登录人员角色,多角色，转存list
//			arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
//		}else {
//			arrayList.add(role_code);//单角色直接添加
//		}
//		Collections.sort(arrayList);  //排序保证高权限不会被低权限覆盖
//		String isAdmin="no";
//		for (int i = 0; i < arrayList.size(); i++) {
//			SysRole sysRole = rwclService.findSysRoleById(arrayList.get(i));
//			Integer orderNo = 0;
//			if (sysRole!=null) {
//				orderNo = sysRole.getOrderNo();
//			}
//			if (orderNo>=4&&orderNo<=6) {
//				isAdmin="admin";//社区管理员以上的
//			}else {
//				if (orderNo==3) {
//					isAdmin="shequ";//社区管理员
//				}else {
//					if (orderNo==2) {
//						isAdmin="fuwuzhan";//服务站管理员
//					}
//				}
//				
//			}
//		}
//		
//		return jmxxService.findsqIdByName(sqName,isAdmin);
//	}
	
	/**
	 * 事件统计json转换值
	 */
//	@ResponseBody
//	@RequestMapping("/sjtjJson")
//	public String sjtjJson(String sjtjName, HttpSession session) {
//		
//		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
//		// 获取当前登陆人的角色
//		String roleCodes = sysAccCount.getRole_code();
//		// 用户角色，保存list
//		ArrayList<String> arrayList = new ArrayList<String>();
//		if (roleCodes.indexOf(",") != -1) {
//			String[] roleCode = roleCodes.split(",");// 当前登录人员角色,多角色，转存list
//			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
//		} else {
//			arrayList.add(roleCodes);// 单角色直接添加
//		}
//		Collections.sort(arrayList); // 排序保证高权限不会被低权限覆盖
//		String roleCode = arrayList.get(arrayList.size() - 1);
//		String sjtjId = "";
//		if (roleCode == "03") {
//			// 查询所有的服务站
//			List<ShglServiceStationEntity> fwzList = rwtjService.fwzAll();
//			for (ShglServiceStationEntity serviceStationEntity : fwzList) {
//				fwzMap.put(serviceStationEntity.getName(), serviceStationEntity.getId());
//			}
//			sjtjId = fwzMap.get(sjtjName);
//		} else if (roleCode == "02") {
//			List<ShglGridEntity> wgList = rwtjService.wgAll();
//			for (ShglGridEntity gridEntity : wgList) {
//				wgMap.put(gridEntity.getName(), gridEntity.getId());
//			}
//			sjtjId = wgMap.get(sjtjName);
//		} else {
//			List<ShglCommunityEntity> sqList = rwtjService.sqAll();
//			for (ShglCommunityEntity communityEntity : sqList) {
//				sqMap.put(communityEntity.getName(), communityEntity.getId());
//			}
//			sjtjId = sqMap.get(sjtjName);
//		}
//		JSONObject returnJson = new JSONObject();
//		returnJson.put("sjtjId", sjtjId);
//		returnJson.put("code", 200);
//		return returnJson.toString();
//	}
	
	/**
	 * 事件类别json转换值
	 */
//	@ResponseBody
//	@RequestMapping("/sjlbJson")
//	public String sjlbJson(String sjlbName, HttpSession session) {
//		
//		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
//		// 获取当前登陆人的角色
//		String roleCodes = sysAccCount.getRole_code();
//		// 用户角色，保存list
//		ArrayList<String> arrayList = new ArrayList<String>();
//		if (roleCodes.indexOf(",") != -1) {
//			String[] roleCode = roleCodes.split(",");// 当前登录人员角色,多角色，转存list
//			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
//		} else {
//			arrayList.add(roleCodes);// 单角色直接添加
//		}
//		Collections.sort(arrayList); // 排序保证高权限不会被低权限覆盖
//		String roleCode = arrayList.get(arrayList.size() - 1);
//		String sjlbId = "";
//		List<SysParamDesc> paramDesc = sjzdService.findByPCode(Consts.EVENT_TYPE);
//		for(SysParamDesc paramDesc2 : paramDesc){
//			sjlbMap.put(paramDesc2.getItemName(), paramDesc2.getItemCode());
//		}
//		sjlbId = sjlbMap.get(sjlbName);
//		JSONObject returnJson = new JSONObject();
//		returnJson.put("sjlbId", sjlbId);
//		returnJson.put("code", 200);
//		return returnJson.toString();
//	}
	
	/**
	 * 事件来源json转换值
	 */
//	@ResponseBody
//	@RequestMapping("/sjlyJson")
//	public String sjlyJson(String sjlyName, HttpSession session) {
//		
//		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
//		// 获取当前登陆人的角色
//		String roleCodes = sysAccCount.getRole_code();
//		// 用户角色，保存list
//		ArrayList<String> arrayList = new ArrayList<String>();
//		if (roleCodes.indexOf(",") != -1) {
//			String[] roleCode = roleCodes.split(",");// 当前登录人员角色,多角色，转存list
//			arrayList = new ArrayList<String>(Arrays.asList(roleCode));
//		} else {
//			arrayList.add(roleCodes);// 单角色直接添加
//		}
//		Collections.sort(arrayList); // 排序保证高权限不会被低权限覆盖
//		String roleCode = arrayList.get(arrayList.size() - 1);
//		String sjlyId = "";
//		List<SysParamDesc> lyList = sjzdService.findByPCode(Consts.EVENT_FROM_TYPE);
//		for(SysParamDesc paramDesc : lyList){
//			sjlyMap.put(paramDesc.getItemName(), paramDesc.getItemCode());
//		}
//		sjlyId = sjlyMap.get(sjlyName);
//		JSONObject returnJson = new JSONObject();
//		returnJson.put("sjlyId", sjlyId);
//		returnJson.put("code", 200);
//		return returnJson.toString();
//	}
	
	/**
	 * 获取当前时间往前推三个月的时间段--shuai
	 * @return
	 */
	private Map getThreeDate(){
		
		String firstTime = "";
		String lastTime = "";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式化日期
		Date date = new Date();//获取当前日期
		
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(date); 
		calendarEnd.add(calendarEnd.DATE, +1);
		Date strEnd = calendarEnd.getTime(); 
		lastTime = format.format(strEnd);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date); 
		
		if(date.getMonth() == 1){//当前月等于1时
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//
			calendar.add(calendar.MONTH, -2);//当前月减两个月
			Date str = calendar.getTime(); 
			firstTime = format.format(str);
		}
		if(date.getMonth() == 2){//当前月等于2时
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//当前年减一年
			calendar.add(calendar.MONTH, -1);//当前月减一个月
			Date str = calendar.getTime(); 
			firstTime = format.format(str);
		}
		if(date.getMonth() == 3){//当前月等于3时
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//当前年减一年
			Date str = calendar.getTime(); 
			firstTime = format.format(str);
		}else{//当前月大于3时
			calendar.add(calendar.MONTH, -3);//当前月减三个月
			Date str = calendar.getTime(); 
			firstTime = format.format(str);
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("firstTime", firstTime);
		returnMap.put("lastTime", lastTime);
		return returnMap;
	}
}
