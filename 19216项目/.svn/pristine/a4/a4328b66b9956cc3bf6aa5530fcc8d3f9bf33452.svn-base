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

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 社区管理Controller
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/sqgl")
public class SqglAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(SqglAction.class);

	@Autowired	
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private RwclService rwclService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private LyglService lyglService;
	
	/**
	 * 列表页数据
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param rolecode
	 * @param accCode
	 * @param cdCode
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name,
			HttpServletRequest req, String manager, String phone) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
//		String roleCode = acc.getSysRole().getRoleCode();
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
				//判断用户权限结束
		
		model.addAttribute("roleCode", acc.getRole_code());
		try {
			if (ajaxCmd == null) {
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
				map.put("manager", manager);
				map.put("phone", phone);
				Pagenate<ShglCommunityEntity> pagenate = sqglService.findByPage1(pageNumber, pageSize, map);
				
				Map<String, String> addMap = new HashMap<String, String>();
				Map<String, String> addMapCnt = new HashMap<String, String>();
				List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
				
				if (pagenate.getList() != null) {
					for (ShglCommunityEntity community : pagenate.getList()) {
						List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
						String flag = "0";
						for(SysParamDesc sysParamDesc : xzqh){
							if(sysParamDesc.getItemCode().equals(community.getStrative_id())){
								community.setStrative_name(sysParamDesc.getItemName());
								flag = "1";
								break;
							}
						}
						if("0".equals(flag)){
							community.setStrative_name("");
						}
						
						int hsCount = 0;
						String sqId = community.getId();
						addMap.put("sqId", sqId);
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
						community.setSjCount(String.valueOf(hsCount));
					}
				}
				model.addAttribute("pagenate", pagenate);
				return "shgl/sqgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询社区管理信息出错", e);
		}
		return "shgl/sqgl/index";
	}

	/**
	 * 跳转添加及修改界面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id) {
		ShglCommunityEntity community = null;
		ShglCmanagerEntity cmanager = null;//存放第一条固定负责人数据
		List<ShglCmanagerEntity> cmanagerList = new ArrayList<ShglCmanagerEntity>();//存除第一条数据外的其他数据
 		if (null != id && !"".equals(id)) {
			community = sqglService.findById1(id);
			for(int i=0; i<community.getCmanagerList().size(); i++){
				if(i == 0){
					cmanager = community.getCmanagerList().get(0);
				}else{
					cmanagerList.add(community.getCmanagerList().get(i));
				}
			}
			List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
			String flag = "0";
			for (SysParamDesc sysParamDesc : xzqh) {
				if (sysParamDesc.getItemCode().equals(community.getStrative_id())) {
					community.setStrative_name(sysParamDesc.getItemName());
					flag = "1";
					break;
				}
			}
			if ("0".equals(flag)) {
				community.setStrative_name("");
			}
		}
 		
		model.addAttribute("community", community);
		List<SysParamDesc> strList = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
//		List<SysStrative> strList = sqglService.findAllStra();
		model.addAttribute("strList", strList);
		model.addAttribute("cmanager", cmanager);
		model.addAttribute("cmanagerList", cmanagerList);
		return "shgl/sqgl/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
	 * @param community
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, ShglCommunityEntity community, String id, String strative_id,
			String name, String managers, String phones) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("strative_id", strative_id);
			List<ShglCommunityEntity> communityEntities = sqglService.findAllCom(map);
			if (communityEntities.size() > 0) {
				return "exists";
			}
			ShglCommunityEntity communityOld = sqglService.findById1(id);
			if (communityOld == null) {// 为null 则数据库中没有该数据
				community.setAdd_code(accCode);
				community.setAdd_name(accName);
				community.setAdd_time(new Date());
				sqglService.save1(community);// 保存
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglCmanagerEntity cmanager = new ShglCmanagerEntity();
					cmanager.setCommId(community.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					sqglService.saveFzr(cmanager);
//				}
			} else {
				BeanUtils.copyProperties(community, communityOld,
						new String[] { "area", "add_code", "add_name", "add_time", "cmanagerList","area_color","line_color","lon","lat" });
				sqglService.update1(communityOld);// 修改
				/***删除之前的负责人及负责人联系电话               -------------开始  **/
				List<ShglCmanagerEntity> cmList = communityOld.getCmanagerList();
				for(ShglCmanagerEntity cmanagerEntity : cmList){
					sqglService.deleteFzr(cmanagerEntity.getId());
				}
				/***删除之前的负责人及负责人联系电话              -------------结束 **/
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglCmanagerEntity cmanager = new ShglCmanagerEntity();
					cmanager.setCommId(communityOld.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					sqglService.saveFzr(cmanager);
//				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加社区信息出错", e);
		}
		return "error";
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids) {
		try {
			sqglService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除社区信息出错", e);
		}
		return "error";
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/sqgl/getAreaData.do");
		model.addAttribute("url", "/shgl/sqgl/saveArea.do");
		model.addAttribute("bzType", "sq");
		return "shgl/choseArea";
	}
	/**
	 * 跳转地图标注
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseBz", produces = "text/html;charset=UTF-8")
	public String choseBz(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/sqgl/getSingleAreaData.do");
		String lon = "";    //经度
		String lat = "";    //纬度
		if (id != null && !"".equals(id)) {
			ShglCommunityEntity comm = sqglService.findById1(id);
			 lon = comm.getLon();     
			 lat = comm.getLat();    
		}
		model.addAttribute("type", "sqfwzx");
		model.addAttribute("lon", lon);
		model.addAttribute("lat", lat);
		return "shgl/choseArea";
	}
	
	/**
	 * 保存社区服务中心标注
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
			ShglCommunityEntity comm = sqglService.findById1(id);
			if (null != comm) {
				comm.setLon(lon);
				comm.setLat(lat);
				sqglService.update1(comm);// 修改
			} else {
				return "error";
			}
			return "success";
		} catch (Exception e) {
			log.error("添加社区服务中心标注出错", e);
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
	public String getAreaData(String id) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		List<ShglCommunityEntity> communityList = sqglService.findAllCom1(paramMap);
		List<List<String>> listArea = new ArrayList<List<String>>();
		if (null != id && !"".equals(id)) {
			ShglCommunityEntity community = sqglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(community.getArea());
			list1.add(community.getName());
			list1.add(community.getId());
			list1.add(community.getArea_color());
			list1.add(community.getLine_color());
			list1.add("1");
			//经度
			list1.add(community.getLon());
		    //纬度
			list1.add(community.getLat());
			//添加社区联系人 
			List<ShglCmanagerEntity> lxrList = community.getCmanagerList();
			if(lxrList !=null && lxrList.size()>0){
				list1.add(lxrList.get(0).getManager());
				list1.add(lxrList.get(0).getPhone());
			}else{
				list1.add("暂未设置");
				list1.add("暂未设置");
			}
			//服务站数量
			Map<String,String> ssMap = new HashMap<String,String>();
			ssMap.put("commId", community.getId());
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom2(ssMap);
			list1.add(String.valueOf(ssList.size()));//10
			//网格数量
			Map<String,String> wgMap = new HashMap<String,String>();
			wgMap.put("commId", community.getId());
			List<ShglGridEntity> wgList = wgglService.findAllSer1(wgMap);
			list1.add(String.valueOf(wgList.size()));//11
			// 人口数量
			Map<String,String> jmMap = new HashMap<String,String>();
			jmMap.put("commId", community.getId());
			Long count = jmxxService.countPerNumbByparam(jmMap);
			list1.add(String.valueOf(count));
			listArea.add(list1);
		}
		if (communityList.size() > 0) {
			for (ShglCommunityEntity com : communityList) {
				if (com.getArea() != null && !"".equals(com.getArea())) {
					List<String> list2 = new ArrayList<String>();
					list2.add(com.getArea());//0
					list2.add(com.getName());//1
					list2.add(com.getId());//2
					list2.add(com.getArea_color());//3
					list2.add(com.getLine_color());//4
					list2.add("1");//5
					//经度
					list2.add(com.getLon());//6
				    //纬度
					list2.add(com.getLat());//7
					//添加社区联系人 
					List<ShglCmanagerEntity> lxrList = com.getCmanagerList();
					if(lxrList !=null && lxrList.size()>0){
						list2.add(lxrList.get(0).getManager());//8
						list2.add(lxrList.get(0).getPhone());//9
					}else{
						list2.add("暂未设置");
						list2.add("暂未设置");
					}
					//服务站数量
					Map<String,String> ssMap = new HashMap<String,String>();
					ssMap.put("commId", com.getId());
					List<ShglServiceStationEntity> ssList = fwzglService.findAllCom2(ssMap);
					list2.add(String.valueOf(ssList.size()));//10
					//网格数量
					Map<String,String> wgMap = new HashMap<String,String>();
					wgMap.put("commId", com.getId());
					List<ShglGridEntity> wgList = wgglService.findAllSer1(wgMap);
					list2.add(String.valueOf(wgList.size()));//11
					
					// 人口数量
					Map<String,String> jmMap = new HashMap<String,String>();
					jmMap.put("commId", com.getId());
					Long count = jmxxService.countPerNumbByparam(jmMap);
					list2.add(String.valueOf(count));
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
			ShglCommunityEntity comm = sqglService.findById1(id);
			List<String> list1 = new ArrayList<String>();
			list1.add(comm.getArea());
			list1.add(comm.getName());
			list1.add(comm.getId());
			list1.add(comm.getArea_color());
			list1.add(comm.getLine_color());
			
			//经度
			list1.add(comm.getLon());
		    //纬度
			list1.add(comm.getLat());
			listArea.add(list1);
		}
		log.info("returnStr=============" + JSONArray.fromObject(listArea).toString());
		return JSONArray.fromObject(listArea).toString();
	}

	/**
	 * 保存社区范围
	 * @param req
	 * @param id
	 * @param area
	 * @param areaColor
	 * @param lineColor
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveArea")
	public String saveArea(HttpServletRequest req, String id, String area, String areaColor, String lineColor) {
		try {
			ShglCommunityEntity community = sqglService.findById1(id);
			if (null != community) {
				community.setArea(area);
				community.setArea_color(areaColor);
				community.setLine_color(lineColor);
				sqglService.update1(community);// 修改
			} else {
				return "error";
			}
			return "success";
		} catch (Exception e) {
			log.error("添加社区范围出错", e);
		}
		return "error";
	}
	
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/sqgl/importView";
	}

	//导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, String flag,String name) {
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
		
		flag = StringUtil.filterchart(flag);
		// 1.数据
		List<List<String>> data = new ArrayList<List<String>>();
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		if(flag.equals("export")) {
			// 组织导出数据
			// 2.标题
			String[] titles = {"社区名称", "行政区划", "添加人", "添加日期", "社区描述", "负责人联系方式"};
			Map<String, String> map = new HashMap<String, String>();
			if ("shequ".equals(isAdmin)) {
				map.put("dqCommId", acc.getCommId());//只导出当前用户负责社区下的所有服务站
			}else if ("admin".equals(isAdmin)) {
				map.put("dqCommId", null);
			}
			map.put("name", name);
			
			List<ShglCommunityEntity> list = sqglService.findAllCom1(map);
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getStrative_name());
				tmp.add(list.get(i).getAdd_name());
				tmp.add(list.get(i).getAddTimeFrm());
				tmp.add(list.get(i).getDescription());
				List<ShglCmanagerEntity> list1 = list.get(i).getCmanagerList();
				String manager = "";
				for(ShglCmanagerEntity shglCmanagerEntity : list1){
					if("".equals(manager)){
						manager = shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}else{
						manager = manager + shglCmanagerEntity.getManager() + "：" + shglCmanagerEntity.getPhone() + "；";
					}
				}
				tmp.add(manager);
				data.add(tmp);
			}
			fileName_d = "社区管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}else{
			// 组织模板数据
			// 标题
			String[] titles = {"社区名称", "行政区划", "社区描述", "负责人", "联系方式"};
			// 组织模板数据
			List<String> tmp = new ArrayList<String>();
			tmp.add("博昌社区");
			tmp.add("行政区划");
			//tmp.add("社区添加人");
			//tmp.add("社区添加日期");
			tmp.add("社区描述");
			tmp.add("社区负责人");
			tmp.add("社区负责人联系方式");
			data.add(tmp);
			fileName_d = "社区管理_导入模板.xlsx";
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
			String[] titles = {"社区名称", "行政区划", "社区描述", "负责人", "联系方式"};
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
					String cName = dMap.get("社区名称") == null ? null : dMap.get("社区名称").trim();
					String xzqhName = dMap.get("行政区划") == null ? null : dMap.get("行政区划").trim();
					String sName = dMap.get("社区描述") == null ? null : dMap.get("社区描述").trim();
					String fzrName = dMap.get("负责人") == null ? null : dMap.get("负责人").trim();
					String phone = dMap.get("联系方式") == null ? null : dMap.get("联系方式").trim();
					if(null == cName || "".equals(cName)){
						errMsg2 += "第" + (i+1) + "行，社区名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", cName);
					ShglCommunityEntity communityEntities = sqglService.findByName(map);
					if (null!=communityEntities) {
						errMsg2 += "第" + (i+1) + "行，社区名称已存在，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == xzqhName || "".equals(xzqhName)){
						errMsg2 += "第" + (i+2) + "行，行政区划为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == fzrName || "".equals(fzrName)){
						errMsg2 += "第" + (i+4) + "行，负责人为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == phone ||  "".equals(phone)){
						errMsg2 += "第" + (i+5) + "行，联系方式为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}else{
						ShglCommunityEntity community = new ShglCommunityEntity();
						community.setStrative_id("1");//博昌街道
						community.setName(cName);
						community.setDescription(sName);
						community.setAdd_code(acc.getAccCode());
						community.setAdd_name(acc.getName());
						community.setAdd_time(d);
						community.setManagers(fzrName);
						community.setPhones(phone);
						sqglService.save1(community);// 保存社区
						
						ShglCmanagerEntity cmanager = new ShglCmanagerEntity();
						cmanager.setCommId(community.getId());
						cmanager.setManager(fzrName);
						cmanager.setPhone(phone);
						sqglService.saveFzr(cmanager); //保存社区负责人及联系方式
						errMsg2 += "导入成功！\r" + System.getProperty("line.separator");
					}
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
