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
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
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
 * 网格管理
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/wggl")
public class WgglAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private RwclService rwclService;
	
	/**
	 * 首页数据
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param serId
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String serId,
			String commId, HttpServletRequest req, String manager, String phone) {
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
		// Map<String, String> paramMap = new HashMap<String, String>();
		// List<ServiceStationEntity> ssList =
		// fwzglService.findAllCom(paramMap);
		// model.addAttribute("ssList", ssList);
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
				map.put("serId", serId);
				map.put("manager", manager);
				map.put("phone", phone);
				Pagenate<ShglGridEntity> pagenate = wgglService.findByPage1(pageNumber, pageSize, map);
				
				List<ShglGridEntity> gridList = pagenate.getList();
				Map<String, String> addMap = new HashMap<String, String>();
				Map<String, String> addMapCnt = new HashMap<String, String>();
				List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
				
				
				if(gridList.size()>0){
					for(ShglGridEntity gridEntity : gridList){
						int hsCount = 0;
						String wgId = gridEntity.getId();
						addMap.put("wgId", wgId);
						buildList = lyglService.getBuildList(addMap);
						if(buildList.size()>0){
							for(ShglBuildingEntity buildingEntity : buildList){
								// 楼宇Id
								String buildId = buildingEntity.getId();
								addMapCnt.put("buildId", buildId);
								// 该楼宇单元数
								String unitCnt = buildingEntity.getUnit_cnt();
								for(int i=0; i<Integer.parseInt(unitCnt); i++){
									addMapCnt.put("unitCnt", String.valueOf(i + 1));
									hsCount += jmxxService.findLyHsCount(addMapCnt);
								}
							}
						}
						gridEntity.setSjCount(String.valueOf(hsCount));
					}
				}
				
				model.addAttribute("pagenate", pagenate);
				return "shgl/wggl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询网格信息出错", e);
		}
		return "shgl/wggl/index";
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
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		//判断用户权限结束
		
		ShglGridEntity grid = null;
		ShglGridManagerEntity gmanager = null;//负责人第一条数据
		List<ShglGridManagerEntity> gmList = new ArrayList<ShglGridManagerEntity>();
		String commId = "";
		String commName = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);
		if (null != id && !"".equals(id)) {
			grid = wgglService.findById1(id);
			for(int i=0; i<grid.getGridList().size(); i++){
				if(i == 0){
					gmanager = grid.getGridList().get(0);
				}else{
					gmList.add(grid.getGridList().get(i));
				}
			}
			commId = grid.getServiceStation().getCommunity().getId();
			commName = grid.getServiceStation().getCommunity().getName();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("commId", commId);
			model.addAttribute("commName", commName);
			model.addAttribute("ssList", ssList);
		}
		model.addAttribute("grid", grid);
		model.addAttribute("gmanager", gmanager);
		model.addAttribute("gmList", gmList);
		return "shgl/wggl/addEdit";
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
	public String doSave(HttpServletRequest req, ShglGridEntity grid, String id, String ssId, String name, String managers, String phones) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		ShglServiceStationEntity station = fwzglService.findById1(ssId);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("ssId", ssId);
			List<ShglGridEntity> gridEntities = wgglService.findAllSer(map);
			if (gridEntities.size() > 0) {
				return "exists";
			}
			ShglGridEntity gridOld = wgglService.findById1(id);
			if (null == gridOld) {// 为null 则数据库中没有该数据
				grid.setAdd_code(accCode);
				grid.setAdd_name(accName);
				grid.setAdd_time(new Date());
				grid.setServiceStation(station);
				wgglService.save1(grid);// 保存
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglGridManagerEntity cmanager = new ShglGridManagerEntity();
					cmanager.setGridId(grid.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					wgglService.saveFzr(cmanager);
//				}
			} else {
				BeanUtils.copyProperties(grid, gridOld, new String[] { "area", "add_code", "add_name", "add_time", "area_color", "line_color", "gridList"});
				gridOld.setServiceStation(station);
				wgglService.update1(gridOld);// 修改
				/***删除之前的负责人及负责人联系电话               -------------开始  **/
				List<ShglGridManagerEntity> cmList = gridOld.getGridList();
				for(ShglGridManagerEntity cmanagerEntity : cmList){
					wgglService.delFzr(cmanagerEntity.getId());
				}
				/***删除之前的负责人及负责人联系电话              -------------结束 **/
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglGridManagerEntity cmanager = new ShglGridManagerEntity();
					cmanager.setGridId(gridOld.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					wgglService.saveFzr(cmanager);
//				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加网格信息出错", e);
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
			String[] id = ids.split(";");
			for(String iString : id){
				Map<String, String> paramMap4 = new HashMap<String, String>();
				paramMap4.put("gridId", iString);
				List<ShglBuildingEntity> buildList = lyglService.findAllBuilds1(paramMap4);// 楼宇列表
				if(buildList.size()>0){
					return "czLy";//存在楼宇
				}
			}
			wgglService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除网格信息出错", e);
		}
		return "error";
	}

	/**
	 * 获取服务站列表
	 * 
	 * @param comId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getComData", produces = "text/html;charset=UTF-8")
	public String getComData(String comId,HttpServletRequest request) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		 String roleCode = acc.getRole_code();
		//判断用户权限
				ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
				if (roleCode.indexOf(",")!=-1) {
					String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
					arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
				}else {
					arrayList.add(roleCode);//单角色直接添加
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
						}else{
							if ("01".equals(arrayList.get(arrayList.size()-1))) {
								isAdmin="wgy";//网格管理员
							}
						}
					}
				}
				//判断用户权限结束
		
		List<ShglServiceStationEntity> ssList = new ArrayList<ShglServiceStationEntity>();
		if (null != comId && !"".equals(comId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("commId", comId);
			if ("fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
				paramMap.put("dqSsId", acc.getSsId());//下拉菜单只显示当前用户负责的服务站
			}
			ssList = fwzglService.findAllCom1(paramMap);
		}
		String returnJson = JSONArray.fromObject(ssList).toString();
		return returnJson;
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/wggl/getAreaData.do");
		model.addAttribute("url", "/shgl/wggl/saveArea.do");
		return "shgl/choseArea";
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
		List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap);
		List<List<String>> listArea = new ArrayList<List<String>>();
		if (null != id && !"".equals(id)) {
			ShglGridEntity grid = wgglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(grid.getArea());
			list1.add(grid.getName());
			list1.add(grid.getId());
			list1.add(grid.getArea_color());
			list1.add(grid.getLine_color());
			list1.add("3");
			list1.add(type);
			list1.add(grid.getServiceStation().getCommunity().getName());
			list1.add(grid.getServiceStation().getName());
			Long count = jmxxService.countPerNumb(grid.getId());
			list1.add(count.toString());
			listArea.add(list1);
			if (null != type && !"".equals(type)) {
				list1 = new ArrayList<String>();
				list1.add(grid.getServiceStation().getArea());
				list1.add(grid.getServiceStation().getName());
				list1.add(grid.getServiceStation().getId());
				list1.add(grid.getServiceStation().getArea_color());
				list1.add(grid.getServiceStation().getLine_color());
				list1.add("2");
				listArea.add(list1);
			}
		}
		if (gridList.size() > 0) {
			for (ShglGridEntity com : gridList) {
				if (null != com.getArea() && !"".equals(com.getArea())) {
					List<String> list2 = new ArrayList<String>();
					list2.add(com.getArea());
					list2.add(com.getName());
					list2.add(com.getId());
					list2.add(com.getArea_color());
					list2.add(com.getLine_color());
					list2.add("3");
					list2.add(type);
					list2.add(com.getServiceStation().getCommunity().getName());
					list2.add(com.getServiceStation().getName());
					Long count = jmxxService.countPerNumb(com.getId());
					list2.add(count.toString());
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
			ShglGridEntity grid = wgglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(grid.getArea());
			list1.add(grid.getName());
			list1.add(grid.getId());
			list1.add(grid.getArea_color());
			list1.add(grid.getLine_color());
			listArea.add(list1);
		}
		log.info("returnStr=============" + JSONArray.fromObject(listArea).toString());
		return JSONArray.fromObject(listArea).toString();
	}

	/**
	 * 保存网格范围
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
			ShglGridEntity grid = wgglService.findById1(id);
			if (null != grid) {
				grid.setArea(area);
				grid.setArea_color(areaColor);
				grid.setLine_color(lineColor);
				wgglService.update1(grid);// 修改
			} else {
				return "error";
			}
			return "success";
		} catch (Exception e) {
			log.error("添加网格范围出错", e);
		}
		return "error";
	}
	
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/wggl/importView";
	}

	//导出
	@ResponseBody
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String flag,HttpServletRequest req,String commId,String ssId, String name) throws UnsupportedEncodingException {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		if(null != name){//防止导入出错--高帅（2017年12月14日--代码修改）
			name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
		}
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
		
		flag = StringUtil.filterchart(flag);
		// 1.数据
		List<List<String>> data = new ArrayList<List<String>>();
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		if(flag.equals("export")) {
			// 组织导出数据
			// 2.标题
			String[] titles = {"网格名称", "所属社区", "所属服务站", "添加人", "添加日期", "网格描述", "负责人及联系方式"};
			Map<String, String> map = new HashMap<String, String>();
			if ("fuwuzhan".equals(isAdmin)) {
				map.put("dqSsId", acc.getSsId());//只导出当前用户负责的服务站
			}else if ("shequ".equals(isAdmin)) {
				map.put("dqCommId", acc.getCommId());//只导出当前用户负责社区下的所有服务站
			}else if ("admin".equals(isAdmin)) {
				map.put("dqSsId", null);//导出所有
				map.put("dqCommId", null);
			}
			map.put("ssId", ssId);
			map.put("commId", commId);
			map.put("name", name);
			List<ShglGridEntity> list = wgglService.findAllSer1(map);
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getServiceStation().getCommunity().getName());
				tmp.add(list.get(i).getServiceStation().getName());
				tmp.add(list.get(i).getAdd_name());
				tmp.add(list.get(i).getAddTimeFrm());
				tmp.add(list.get(i).getDescription());
				List<ShglGridManagerEntity> list1 = list.get(i).getGridList();
				String manager = "";
				for(ShglGridManagerEntity shglCmanagerEntity : list1){
					if("".equals(manager)){
						manager = shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}else{
						manager = manager + shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}
				}
				tmp.add(manager);
				data.add(tmp);
			}
			fileName_d = "网格管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}else{
			// 组织导出数据
			// 2.标题
			String[] titles = {"网格名称", "所属社区", "所属服务站", "网格描述", "负责人", "联系方式"};
			// 组织模板数据
			List<String> tmp = new ArrayList<String>();
			tmp.add("博昌网格");
			tmp.add("博昌社区");
			tmp.add("博昌服务站");
			tmp.add("博昌网格描述博昌网格描述博昌网格描述博昌网格描述");
			tmp.add("博昌网格负责人");
			tmp.add("17845454545");
			data.add(tmp);
			fileName_d = "网格管理_导入模板.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}
		
		//String xlsxFile = null;
		try {
			FileUtil.downLoadFileByStream(fileName, fileName_d, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	return "";
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
			String[] titles = {"网格名称", "所属社区", "所属服务站", "网格描述", "负责人", "联系方式"};
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
					String wName = dMap.get("网格名称") == null ? null : dMap.get("网格名称").trim();
					String cName = dMap.get("所属社区") == null ? null : dMap.get("所属社区").trim();
					String sName = dMap.get("所属服务站") == null ? null : dMap.get("所属服务站").trim();
					String dName = dMap.get("网格描述") == null ? null : dMap.get("网格描述").trim();
					String cmanName = dMap.get("负责人") == null ? null : dMap.get("负责人").trim();
					String phoneNum = dMap.get("联系方式") == null ? null : dMap.get("联系方式").trim();
					
					if(null == cName || "".equals(cName)){
						errMsg2 += "第" + (i+2) + "行，社区名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == sName || "".equals(sName)){
						errMsg2 += "第" + (i+2) + "行，服务站名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == wName || "".equals(wName)){
						errMsg2 += "第" + (i+2) + "行，网格名称为空，导入失败！\r" + System.getProperty("line.separator");
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
					Map<String, String> addMapSs = new HashMap<String, String>();
					addMapSs.put("ssName", sName);
					addMapSs.put("commId", commId);
					ShglServiceStationEntity serviceStationEntity = fwzglService.findByName(addMapSs);
					if(null == serviceStationEntity){
						errMsg2 += "第" + (i+2) + "行，该所属社区下服务站不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					Map<String, String> addMapG = new HashMap<String, String>();
					addMapG.put("grName", wName);
					addMapG.put("ssId", serviceStationEntity.getId());
					ShglGridEntity gridEntity = wgglService.findByName(addMapG);
					if(null != gridEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下网格已存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					ShglGridEntity grid = new ShglGridEntity();
					grid.setAdd_code(acc.getAccCode());
					grid.setAdd_name(acc.getName());
					grid.setAdd_time(d);
					grid.setServiceStation(serviceStationEntity);
					grid.setDescription(dName);
					grid.setName(wName);
					wgglService.save1(grid);// 保存网格信息
					
					ShglGridManagerEntity cmanager = new ShglGridManagerEntity();
					cmanager.setGridId(grid.getId());
					cmanager.setManager(cmanName);
					cmanager.setPhone(phoneNum);
					wgglService.saveFzr(cmanager);// 保存网格负责人信息
					
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
	
	/**
	 * 获取社区下的服务站
	 * @param comId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getComData2", produces = "text/html;charset=UTF-8")
	public String getComData2(String comId,HttpServletRequest request) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		
		List<ShglServiceStationEntity> ssList = new ArrayList<ShglServiceStationEntity>();
		if(comId!=null && !"".equals(comId)){
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("commId", comId);
			ssList.addAll(fwzglService.findAllCom1(paramMap));
		}
		String returnJson = JSONArray.fromObject(ssList).toString();
		return returnJson;
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
	
}
