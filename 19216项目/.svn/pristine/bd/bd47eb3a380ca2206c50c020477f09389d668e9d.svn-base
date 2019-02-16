package com.jcwx.action.shgl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcwx.action.xtgl.YhglAction;
import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 服务站管理
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/fwzgl")
public class FwzglAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private RwclService rwclService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private JmxxService jmxxService;

	/**
	 * 首页数据
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param commId
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String commId,
			HttpServletRequest req, String manager, String phone) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		//判断用户权限结束
		
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);
		try {
			if (null == ajaxCmd) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();//当前用户负责的社区Id
					map.put("dqCommId", dqCommId);
				}
				if ("fuwuzhan".equals(isAdmin)) {
					String dqSsId = acc.getSsId();//当前用户负责的服务站Id
					map.put("dqSsId", dqSsId);
				}
				if ("admin".equals(isAdmin)) {//最高权限查所有
					map.put("dqCommId", null);
					map.put("dqSsId", null);
				}
				map.put("name", name);
				map.put("commId", commId);
				map.put("manager", manager);
				map.put("phone", phone);
				Pagenate<ShglServiceStationEntity> pagenate = fwzglService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/fwzgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询服务站信息出错", e);
		}
		return "shgl/fwzgl/index";
	}
	/**
	 * 权限控制抽取方法
	 * @author 李伟
	 * @time 2017年12月12日下午3:41:49
	 * @param roleCode
	 * @return
	 */
	public String jurisdiction(String roleCode) {
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
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
				}else {
					if (Consts.ROLE_FWZGLY.equals(arrayList.get(arrayList.size()-1))) {
						isAdmin="fuwuzhan";//服务站管理员
					}
				}
			}
		}
		
		return isAdmin;
	}

	/**
	 * 跳转添加及修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		String isAdmin = jurisdiction(roleCode);
		
		ShglServiceStationEntity serviceStation = null;
		ShglSmanagerEntity smanager = null;//存放第一条固定数据
		List<ShglSmanagerEntity> smanageList = new ArrayList<ShglSmanagerEntity>();//存除第一条数据外的其他数据
		if (null != id && !"".equals(id)) {
			serviceStation = fwzglService.findById1(id);
			for(int i=0; i<serviceStation.getSmanageList().size(); i++){
				if(i==0){
					smanager = serviceStation.getSmanageList().get(0);
				}else{
					smanageList.add(serviceStation.getSmanageList().get(i));
				}
			}
		}
		model.addAttribute("serviceStation", serviceStation);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);
		model.addAttribute("smanager", smanager);
		model.addAttribute("smanageList", smanageList);
		return "shgl/fwzgl/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
	 * 
	 * @param community
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, ShglServiceStationEntity serviceStation, String id, String commId,
			String name, String managers, String phones) {
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		ShglCommunityEntity community = sqglService.findById1(commId);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("commId", commId);
			List<ShglServiceStationEntity> serviceStationEntities = fwzglService.findAllCom(map);
			if (serviceStationEntities.size() > 0) {
				return "exists";
			}
			ShglServiceStationEntity serviceStationOld = fwzglService.findById1(id);
			if (null == serviceStationOld) {// 为null 则数据库中没有该数据
				serviceStation.setAdd_code(accCode);
				serviceStation.setAdd_name(accName);
				serviceStation.setAdd_time(new Date());
				serviceStation.setCommunity(community);
				fwzglService.save1(serviceStation);// 保存
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglSmanagerEntity cmanager = new ShglSmanagerEntity();
					cmanager.setSsId(serviceStation.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					fwzglService.saveFzr(cmanager);
//				}
			} else {
				BeanUtils.copyProperties(serviceStation, serviceStationOld,
						new String[] { "area", "add_code", "add_name", "add_time", "area_color","lon" ,"lat","line_color", "smanageList" });
				serviceStationOld.setCommunity(community);
				fwzglService.update1(serviceStationOld);// 修改
				/***删除之前的负责人及负责人联系电话               -------------开始  **/
				List<ShglSmanagerEntity> cmList = serviceStationOld.getSmanageList();
				for(ShglSmanagerEntity cmanagerEntity : cmList){
					fwzglService.delFzr(cmanagerEntity.getId());
				}
				/***删除之前的负责人及负责人联系电话              -------------结束 **/
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglSmanagerEntity cmanager = new ShglSmanagerEntity();
					cmanager.setSsId(serviceStationOld.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					fwzglService.saveFzr(cmanager);
//				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加服务站信息出错", e);
		}
		return "error";
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids) {
		try {
			fwzglService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除服务站信息出错", e);
		}
		return "error";
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/fwzgl/getAreaData.do");
		model.addAttribute("url", "/shgl/fwzgl/saveArea.do");
		model.addAttribute("bzType", "fwz");
		return "shgl/choseArea";
	}
	
	/**
	 * 跳转地图标注
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseBz", produces = "text/html;charset=UTF-8")
	public String choseBz(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/fwzgl/getSingleAreaData.do");
		String lon = "";    //经度
		String lat = "";    //纬度
		if (id != null && !"".equals(id)) {
			ShglServiceStationEntity ss = fwzglService.findById1(id);
			lon = ss.getLon();
			lat = ss.getLat();
		}
		model.addAttribute("type", "fwzbz");
		model.addAttribute("lon", lon);
		model.addAttribute("lat", lat);
		return "shgl/choseArea";
	}
	
	/**
	 * 保存服务站标注
	 * 
	 * @param req
	 * @param id
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveBz")
	public String saveBz(HttpServletRequest req, String lon, String lat,String id) {
		try {
			ShglServiceStationEntity stationEntity = fwzglService.findById1(id);
			if (null != stationEntity) {
				stationEntity.setLon(lon);
				stationEntity.setLat(lat);
				fwzglService.update1(stationEntity);// 修改
			} else {
				return "error";
			}
			return "success";
		} catch (Exception e) {
			log.error("添加服务站标注出错", e);
		}
		return "error";
	}
	
	/**
	 * 获取范围数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getAreaData", produces = "text/html;charset=UTF-8")
	public String getAreaData(String id, String type) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		List<ShglServiceStationEntity> stationList = fwzglService.findAllCom1(paramMap);
		List<List<String>> listArea = new ArrayList<List<String>>();
		if (null != id && !"".equals(id)) {
			ShglServiceStationEntity stationEntity = fwzglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(stationEntity.getArea());//0
			list1.add(stationEntity.getName());//1
			list1.add(stationEntity.getId());//2
			list1.add(stationEntity.getArea_color());//3
			list1.add(stationEntity.getLine_color());//4
			list1.add("2");//5
			list1.add(stationEntity.getLon());//6
			list1.add(stationEntity.getLat());//7
			//添加联系人
			if(stationEntity.getSmanageList().size()>0){
				list1.add(stationEntity.getSmanageList().get(0).getManager());//8
				list1.add(stationEntity.getSmanageList().get(0).getPhone());//9
			}else{
				list1.add("暂未设定");
				list1.add("暂未设定");
			}
			list1.add(stationEntity.getId());//10
			//网格数量
			Map<String,String> wgMap = new HashMap<String,String>();
			wgMap.put("ssId", stationEntity.getId());
			List<ShglGridEntity> wgList = wgglService.findAllSer1(wgMap);
			list1.add(String.valueOf(wgList.size()));//11
			// 人口数量
			Map<String,String> jmMap = new HashMap<String,String>();
			wgMap.put("ssId", stationEntity.getId());
			Long count = jmxxService.countPerNumbByparam(jmMap);
			list1.add(String.valueOf(count));
			listArea.add(list1);
			if (null != type && !"".equals(type)) {
				List<String> list3 = new ArrayList<String>();
				list3.add(stationEntity.getCommunity().getArea());
				list3.add(stationEntity.getCommunity().getName());
				list3.add(stationEntity.getCommunity().getId());
				list3.add(stationEntity.getCommunity().getArea_color());
				list3.add(stationEntity.getCommunity().getLine_color());
				list3.add("1");
				list3.add(stationEntity.getLon());
				list3.add(stationEntity.getLat());
				if(stationEntity.getSmanageList().size()>0){
					list3.add(stationEntity.getSmanageList().get(0).getManager());
					list3.add(stationEntity.getSmanageList().get(0).getPhone());
				}else{
					list3.add("暂未设定");
					list3.add("暂未设定");
				}
				list3.add(stationEntity.getId());
				//网格数量
				Map<String,String> wgMapo = new HashMap<String,String>();
				wgMapo.put("ssId", stationEntity.getId());
				List<ShglGridEntity> wgListo = wgglService.findAllSer1(wgMapo);
				list1.add(String.valueOf(wgListo.size()));//11
				// 人口数量
				Map<String,String> jmMapo = new HashMap<String,String>();
				jmMapo.put("ssId", stationEntity.getId());
				Long counto = jmxxService.countPerNumbByparam(jmMapo);
				list3.add(String.valueOf(counto));
				listArea.add(list3);
			}
		}
		if (stationList.size() > 0) {
			for (ShglServiceStationEntity com : stationList) {
				if (null != com.getArea() && !"".equals(com.getArea())) {
					List<String> list2 = new ArrayList<String>();
					list2.add(com.getArea());
					list2.add(com.getName());
					list2.add(com.getId());
					list2.add(com.getArea_color());
					list2.add(com.getLine_color());
					list2.add("2");
					list2.add(com.getLon());
					list2.add(com.getLat());
					if(com.getSmanageList().size()>0){
						list2.add(com.getSmanageList().get(0).getManager());
						list2.add(com.getSmanageList().get(0).getPhone());
					}else{
						list2.add("暂未设定");
						list2.add("暂未设定");
					}
					list2.add(com.getId());
					//网格数量
					Map<String,String> wgMap = new HashMap<String,String>();
					wgMap.put("ssId", com.getId());
					List<ShglGridEntity> wgList = wgglService.findAllSer1(wgMap);
					list2.add(String.valueOf(wgList.size()));//11
					// 人口数量
					Map<String,String> jmMap = new HashMap<String,String>();
					jmMap.put("ssId", com.getId());
					Long count = jmxxService.countPerNumbByparam(jmMap);
					list2.add(String.valueOf(count));//12
					listArea.add(list2);
				}
			}
		}
		log.info("returnStr=============" + JSONArray.fromObject(listArea).toString());
		return JSONArray.fromObject(listArea).toString();
	}
	
	/**
	 * 获取单条范围数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getSingleAreaData", produces = "text/html;charset=UTF-8")
	public String getSingleAreaData(String id) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		List<List<String>> listArea = new ArrayList<List<String>>();
		if (id != null && !"".equals(id)) {
			ShglServiceStationEntity ss = fwzglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(ss.getArea());
			list1.add(ss.getName());
			list1.add(ss.getId());
			list1.add(ss.getArea_color());
			list1.add(ss.getLine_color());
			listArea.add(list1);
		}
		log.info("returnStr=============" + JSONArray.fromObject(listArea).toString());
		return JSONArray.fromObject(listArea).toString();
	}

	/**
	 * 保存服务站范围
	 * 
	 * @param req
	 * @param id
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveArea")
	public String saveArea(HttpServletRequest req, String id, String area, String areaColor, String lineColor) {
		try {
			ShglServiceStationEntity stationEntity = fwzglService.findById1(id);
			if (null != stationEntity) {
				stationEntity.setArea(area);
				stationEntity.setArea_color(areaColor);
				stationEntity.setLine_color(lineColor);
				fwzglService.update1(stationEntity);// 修改
			} else {
				return "error";
			}
			return "success";
		} catch (Exception e) {
			log.error("添加服务站范围出错", e);
		}
		return "error";
	}
	
	/**
	 * 跳转查看负责人界面
	 * 
	 * @return
	 */
	@RequestMapping("/gofzrView")
	public String goView(Model model, String id) {
		if (null != id && !"".equals(id)) {
			ShglServiceStationEntity stationEntity = fwzglService.findById1(id);
			if(stationEntity.getSmanageList().size()>0){
				model.addAttribute("fzrList",stationEntity.getSmanageList());
			}
		}
		return "shgl/fwzgl/fzrview";
	}
	
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/fwzgl/importView";
	}

	//导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, String flag,String commId,String name) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		try {
			if(null != name){//防止导入出错--高帅（2017年12月14日--代码修改）
				name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		//判断用户权限结束
		
		flag = StringUtil.filterchart(flag);
		// 1.数据
		List<List<String>> data = new ArrayList<List<String>>();
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		if(flag.equals("export")) {
			// 组织导出数据
			// 2.标题
			String[] titles = {"服务站名称", "所属社区", "添加人", "添加日期", "服务站描述", "负责人及联系方式"};
			Map<String, String> map = new HashMap<String, String>();
			if ("fuwuzhan".equals(isAdmin)) {
				map.put("dqSsId", acc.getSsId());//只导出当前用户负责的服务站
			}else if ("shequ".equals(isAdmin)) {
				map.put("dqCommId", acc.getCommId());//只导出当前用户负责社区下的所有服务站
			}else if ("admin".equals(isAdmin)) {
				map.put("dqSsId", null);//导出所有小区
				map.put("dqCommId", null);
			}
			map.put("commId", commId);
			map.put("name", name);
			List<ShglServiceStationEntity> list = fwzglService.findAllCom1(map);
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getCommunity().getName());
				tmp.add(list.get(i).getAdd_name());
				tmp.add(list.get(i).getAddTimeFrm());
				tmp.add(list.get(i).getDescription());
				List<ShglSmanagerEntity> list1 = list.get(i).getSmanageList();
				String manager = "";
				for(ShglSmanagerEntity shglCmanagerEntity : list1){
					if("".equals(manager)){
						manager = shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}else{
						manager = manager + shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}
				}
				tmp.add(manager);
				data.add(tmp);
			}
			fileName_d = "服务站管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}else{
			// 组织导出数据
			// 2.标题
			String[] titles = {"服务站名称", "所属社区", "服务站描述", "负责人", "联系方式"};
			// 组织模板数据
			List<String> tmp = new ArrayList<String>();
			tmp.add("博昌服务站");
			tmp.add("博昌社区");
			tmp.add("博昌服务站描述");
			tmp.add("张三");
			tmp.add("17854245365");
			data.add(tmp);
			fileName_d = "服务站管理_导入模板.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}
		
		String xlsxFile = null;
		try {
			xlsxFile = FileUtil.downLoadFileByStream(fileName, fileName_d, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xlsxFile;
	}
	
	// 导入
	@ResponseBody
	@RequestMapping("/impFile")
	public String importFile(@RequestParam("myFile") CommonsMultipartFile[] myFile, HttpServletRequest req)
			throws Exception {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String impResult = ""; // 上传结果
		try {

			MultipartFile file = myFile[0];
			// 校验文件是否为空
			if (file.isEmpty()) {
				impResult = "empty";
				return "{impResult:'" + impResult + "'}";
			}
			String importPath = ProjectUtils.getSysCfg("importPath");//导入路径
			// 上传文件到服务器
			List<Map<String, String>> fiList = FileUtil.fileUpload(importPath, myFile);
			// 读取Excel并校验文件
			String fileName = fiList.get(0).get("newName");
			log.info(fileName);
			String[] titles = {"服务站名称", "所属社区", "服务站描述", "负责人", "联系方式"};
			// 基本校验
			Map<String, Object> checkResult = ExcelUtils.checkExcel(fileName, titles);
			String errMsg = checkResult.get("errMsg").toString();
			log.info(errMsg);
			if ("".equals(errMsg)) {
				// 文件基本校验通过，开始自定义数据校验
				@SuppressWarnings("unchecked")
				List<Map<String, String>> dataList = (List<Map<String, String>>) checkResult.get("dataList");
				// 自定义数据校验通过，开始导入数据到数据库
				String errMsg2 = "";//判断是否存在数据
				String flag = "0";//判断是否有错误提示
				Date d = new Date();
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, String> dMap = dataList.get(i);
					String cName = dMap.get("所属社区") == null ? null : dMap.get("所属社区").trim();
					String sName = dMap.get("服务站名称") == null ? null : dMap.get("服务站名称").trim();
					String dName = dMap.get("服务站描述") == null ? null : dMap.get("服务站描述").trim();
					String cmanName = dMap.get("负责人") == null ? null : dMap.get("负责人").trim();
					String phoneNum = dMap.get("联系方式") == null ? null : dMap.get("联系方式").trim();
					
					if(null == cName || "".equals(cName)){
						errMsg2 += "第" + (i+2) + "行，社区名称为空,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> addMap = new HashMap<String, String>(); 
					addMap.put("sqName", cName);
					ShglCommunityEntity communityEntity = sqglService.findByName(addMap);
					if(null == communityEntity){
						errMsg2 += "第" + (i+2) + "行,所属社区不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					String commId = communityEntity.getId();//所属社区Id
					if(null == sName || "".equals(sName)){
						errMsg2 += "第" + (i+2) + "行，服务站名称为空,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> addMapSs = new HashMap<String, String>();
					addMapSs.put("ssName", sName);
					addMapSs.put("commId", commId);
					ShglServiceStationEntity serviceStationEntity = fwzglService.findByName(addMapSs);
					if(null != serviceStationEntity){
						errMsg2 += "第" + (i+2) + "行，该所属社区下服务站已存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					ShglServiceStationEntity serviceStation = new ShglServiceStationEntity();
					serviceStation.setAdd_code(acc.getAccCode());
					serviceStation.setAdd_name(acc.getName());
					serviceStation.setAdd_time(d);
					serviceStation.setName(sName);
					serviceStation.setDescription(dName);
					serviceStation.setCommunity(communityEntity);
					fwzglService.save1(serviceStation);// 保存服务站信息
					
					ShglSmanagerEntity cmanager = new ShglSmanagerEntity();
					cmanager.setSsId(serviceStation.getId());
					cmanager.setManager(cmanName);
					cmanager.setPhone(phoneNum);
					fwzglService.saveFzr(cmanager);//保存服务站负责人信息
					
					errMsg2 += "第" + (i+2) + "行，导入成功！\r" + System.getProperty("line.separator");
				}
				if("1".equals(flag)){
					impResult = createErrFile(errMsg2);
				}else{
					impResult = "succ";
				}
				
			} else {
				impResult = createErrFile(errMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			impResult = createErrFile("导入失败，请检查数据格式");
		}
		return "{impResult:'" + impResult + "'}";
	}

	private String createErrFile(String errMsg) {
		String exportPath = ProjectUtils.getSysCfg("exportPath");//导出路径
		String fileName = getNow("yyyyMMddHHmmSS") + RandomStringUtils.randomAlphanumeric(10) + ".txt";
		File file = new File(exportPath + "/" + File.separator + fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(file);
			fWriter.write(errMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fWriter != null) {
				try {
					fWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return exportPath + "/" + fileName;
	}

	public static String getNow(String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(new Date());
	}
}
