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
import java.util.regex.Pattern;

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
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 楼宇管理
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/lygl")
public class LyglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SjzdService sjzdService;

	/**
	 * 首页数据
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
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, String gridId, String xqId, HttpServletRequest req) {
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
				return "shgl/lygl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询楼宇信息出错", e);
		}
		return "shgl/lygl/index";
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
		
		ShglBuildingEntity build = null;
		String commId = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);
		if (null != id && !"".equals(id)) {
			build = lyglService.findById1(id);
			commId = build.getVillage().getServiceStation().getCommunity().getId();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("commId", commId);
			model.addAttribute("ssList", ssList);
			Map<String, String> paramMap2 = new HashMap<String, String>();
			paramMap2.put("ssId", build.getVillage().getServiceStation().getId());
			List<ShglVillageEntity> vList = xqxxService.findAllVillages1(paramMap2);// 小区列表
			model.addAttribute("vList", vList);

			Map<String, String> paramMap3 = new HashMap<String, String>();
			paramMap3.put("ssId", build.getVillage().getServiceStation().getId());
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap3);// 网格列表
			model.addAttribute("gridList", gridList);
		}
		List<SysParamDesc> lylx = sjzdService.findByCode("10006").getSysParamDesc();// 楼宇类型
		model.addAttribute("build", build);
		model.addAttribute("lylx", lylx);
		return "shgl/lygl/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
	 * 
	 * @param req
	 * @param build
	 * @param id
	 * @param ssId
	 * @param vId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, ShglBuildingEntity build, String id, String commId, String gridId,
			String vId, String name) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		ShglGridEntity grid = wgglService.findById1(gridId);// 网格
		ShglVillageEntity village = xqxxService.findById1(vId);// 小区
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("vId", vId);
			List<ShglBuildingEntity> buildingEntities = lyglService.findAllBuilds(map);
			if (buildingEntities.size() > 0) {
				return "exists";
			}
			ShglBuildingEntity buildOld = lyglService.findById1(id);
			if (null == buildOld) {// 为null 则数据库中没有该数据
				build.setAdd_code(accCode);
				build.setAdd_name(accName);
				build.setAdd_time(new Date());
				build.setGrid(grid);
				build.setVillage(village);
				build.setCommId(commId);
				lyglService.save1(build);// 保存
			} else {
				BeanUtils.copyProperties(build, buildOld, new String[] { "add_code", "add_name", "add_time" });
				buildOld.setGrid(grid);
				buildOld.setVillage(village);
				buildOld.setCommId(commId);
				lyglService.update1(buildOld);// 修改
			}
			return "success";
		} catch (Exception e) {
			log.error("添加楼宇信息出错", e);
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
			lyglService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除楼宇信息出错", e);
		}
		return "error";
	}

	/**
	 * 获取小区列表
	 * 
	 * @param ssId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getData", produces = "text/html;charset=UTF-8")
	public String getComData(String ssId) {
		List<ShglVillageEntity> vList = new ArrayList<ShglVillageEntity>();
		if (ssId != null && !"".equals(ssId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ssId", ssId);
			vList = xqxxService.findAllVillages1(paramMap);
		}
		String returnJson = JSONArray.fromObject(vList).toString();
		return returnJson;
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/wggl/getSingleAreaData.do");
		model.addAttribute("url", "/shgl/lygl/saveArea.do");
		model.addAttribute("type", "ly");
		return "shgl/choseArea";
	}
	
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/lygl/importView";
	}

	//导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, String flag,HttpServletRequest req,
			String commId, String ssId, String gridId, String xqId, String name) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
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
		// 组织导出数据
		// 1.标题
		//String[] titles = {"社区名称", "服务站名称", "网格名称", "小区名称", "楼宇名称", "单元数", "楼层数", "每层户数", "楼宇类型"};
		String[] titles = {"楼宇名称", "单元数", "楼层数", "楼宇类型", "所属社区", "所属服务站", "所属网格", "所属小区", "添加人", "添加日期"};
		// 2.数据
		List<List<String>> data = new ArrayList<List<String>>();
		// 3.字典
		Map<String, String> lylx = sjzdService.findMapByPCode("10006");// 楼宇类型
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		dict.put("楼宇类型", lylx);
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		//根据查询和权限导出不同 的数据
		if(flag.equals("export")) {
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
			map.put("gridId", gridId);
			map.put("xq_id", xqId);
			map.put("name", name);
			List<ShglBuildingEntity> list = lyglService.findAllBuilds1(map);
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getUnit_cnt());
				tmp.add(list.get(i).getFloor_cnt());
				tmp.add(list.get(i).getLylx());
				tmp.add(list.get(i).getVillage().getServiceStation().getCommunity().getName());
				tmp.add(list.get(i).getVillage().getServiceStation().getName());
				tmp.add(list.get(i).getGrid().getName());
				tmp.add(list.get(i).getVillage().getName());
				tmp.add(list.get(i).getAdd_name());
				tmp.add(list.get(i).getAddTimeFrm());
				data.add(tmp);
			}
			fileName_d = "楼宇管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, dict);
		}else{
			// 组织模板数据
			String[] titles1 = {"楼宇名称", "单元数", "楼层数", "每层户数", "楼宇类型", "所属社区", "所属服务站", "所属网格", "所属小区"};
			List<String> tmp = new ArrayList<String>();
			tmp.add("楼宇名称");
			tmp.add("1");
			tmp.add("1");
			tmp.add("1");
			tmp.add("楼宇类型");
			tmp.add("所属社区");
			tmp.add("所属服务站");
			tmp.add("所属网格");
			tmp.add("所属小区");
			data.add(tmp);
			fileName_d = "楼宇管理_导入模板.xlsx";
			// 生成Excel文件
			fileName = ExcelUtils.createExcel(titles1, data, dict);
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
			String[] titles = {"楼宇名称", "单元数", "楼层数", "每层户数", "楼宇类型", "所属社区", "所属服务站", "所属网格", "所属小区"};
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
				Map<String, String> lylxMap = new HashMap<String, String>();// 楼宇类型
				List<SysParamDesc> paramdesc = sjzdService.findByPCode("10006");
				for(SysParamDesc p : paramdesc){
					lylxMap.put(p.getItemCode(), p.getItemName());
				}
				Pattern p = Pattern.compile("^[0-9]+$"); // 验证整数
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, String> dMap = dataList.get(i);
					String lName = dMap.get("楼宇名称") == null ? null : dMap.get("楼宇名称").trim();
					String dys = dMap.get("单元数") == null ? null : dMap.get("单元数").trim();
					String lcs = dMap.get("楼层数") == null ? null : dMap.get("楼层数").trim();
					String mchs = dMap.get("每层户数") == null ? null : dMap.get("每层户数").trim();
					String lylx = dMap.get("楼宇类型") == null ? null : dMap.get("楼宇类型").trim();
					String cName = dMap.get("所属社区") == null ? null : dMap.get("所属社区").trim();
					String sName = dMap.get("所属服务站") == null ? null : dMap.get("所属服务站").trim();
					String wName = dMap.get("所属网格") == null ? null : dMap.get("所属网格").trim();
					String dName = dMap.get("所属小区") == null ? null : dMap.get("所属小区").trim();
					
					if(null == lName || "".equals(lName)){
						errMsg2 += "第" + (i+2) + "行，楼宇名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == dys || "".equals(dys)){
						errMsg2 += "第" + (i+2) + "行，单元数为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}else if(!p.matcher(dys).matches()){
						errMsg2 += "第" + (i+2) + "行，单元数不是整数，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == lcs || "".equals(lcs)){
						errMsg2 += "第" + (i+2) + "行，楼层数为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}else if(!p.matcher(lcs).matches()){
						errMsg2 += "第" + (i+2) + "行，楼层数不是整数，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == mchs || "".equals(mchs)){
						errMsg2 += "第" + (i+2) + "行，每层户数为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}else if(!p.matcher(mchs).matches()){
						errMsg2 += "第" + (i+2) + "行，每层户数不是整数，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == lylx || "".equals(lylx)){
						errMsg2 += "第" + (i+2) + "行，楼宇类型为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}else{
						if(lylxMap.get(lylx) == null){
							errMsg2 += "第" + (i+2) + "行，楼宇类型与[楼宇类型]sheet页中的楼宇类型不一致，导入失败！\r" + System.getProperty("line.separator");
							flag = "1";
							continue;
						}
					}
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
					if(null == dName || "".equals(dName)){
						errMsg2 += "第" + (i+2) + "行，小区名称为空，导入失败！\r" + System.getProperty("line.separator");
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
					
					Map<String, String> addMapV = new HashMap<String, String>();
					addMapV.put("villName", dName);
					addMapV.put("ssId", serviceStationEntity.getId());
					ShglVillageEntity shglVillageEntity = xqxxService.findByName(addMapV);
					if(null == shglVillageEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下小区不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					Map<String, String> addMapG = new HashMap<String, String>();
					addMapG.put("grName", wName);
					addMapG.put("ssId", serviceStationEntity.getId());
					ShglGridEntity gridEntity = wgglService.findByName(addMapG);
					if(null == gridEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下网格不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> existLy = new HashMap<String, String>();
					existLy.put("name", lName);
					existLy.put("vId", shglVillageEntity.getId());	
				    List<ShglBuildingEntity> lylist = lyglService.findAllBuilds(existLy);
				    if(lylist != null && lylist.size()>0){
						errMsg2 += "第" + (i+2) + "行，该楼宇已存在！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
				    }
					ShglBuildingEntity build = new ShglBuildingEntity();
					build.setName(lName);
					build.setUnit_cnt(dys);
					build.setFloor_cnt(lcs);
					build.setFamily_cnt(mchs);
					build.setBuilding_type(lylx);
					build.setCommId(commId);
					build.setGrid(gridEntity);
					build.setVillage(shglVillageEntity);
					build.setAdd_code(acc.getAccCode());
					build.setAdd_name(acc.getName());
					build.setAdd_time(d);
					lyglService.save1(build);// 保存
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
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		if (id!=null&&!"".equals(id)) {
			ShglBuildingEntity buildingEntity = lyglService.findById1(id);
			model.addAttribute("buildingEntity",buildingEntity);
			if (buildingEntity!=null) {
				String commId = buildingEntity.getCommId();
				String commName = sqglService.findById1(commId).getName();
				model.addAttribute("commName",commName);
			}
		}
		return "shgl/lygl/view";
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
