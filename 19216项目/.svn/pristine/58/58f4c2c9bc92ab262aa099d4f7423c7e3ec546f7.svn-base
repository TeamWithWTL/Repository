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
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 小区信息
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/xqxx")
public class XqxxAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private RwclService rwclService;

	/**
	 * 首页数据
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param gridId
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, HttpServletRequest req, String manager, String phone) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
	
		
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
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("manager", manager);
				map.put("phone", phone);
				Pagenate<ShglVillageEntity> pagenate = xqxxService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/xqxx/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询小区信息出错", e);
		}
		return "shgl/xqxx/index";
	}

	/**
	 * 跳转添加及修改界面
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id,HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		//判断用户权限
		String isAdmin = jurisdiction(roleCode);
				//判断用户权限结束
		ShglVillageEntity village = null;
		ShglVmanagerEntity vmanager = null;
		List<ShglVmanagerEntity> vList = new ArrayList<ShglVmanagerEntity>();
		String commId = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());//社区下拉列表只显示当前用户负责的社区
		}else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);
		if (id != null && !"".equals(id)) {
			village = xqxxService.findById1(id);
			for(int i=0; i<village.getVmanList().size(); i++){
				if(i==0){
					vmanager = village.getVmanList().get(0);
				}else{
					vList.add(village.getVmanList().get(i));
				}
			}
			
			commId = village.getServiceStation().getCommunity().getId();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("commId", commId);
			model.addAttribute("ssList", ssList);
		}
		model.addAttribute("village", village);
		model.addAttribute("vmanager", vmanager);
		model.addAttribute("vList", vList);
		return "shgl/xqxx/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
	 * @param community
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, ShglVillageEntity village, 
			String id, String ssId, String name, String managers, String phones, String desId) {
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		ShglServiceStationEntity station = fwzglService.findById1(ssId);// 服务站
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("ssId", ssId);
			List<ShglVillageEntity> villageEntities = xqxxService.findAllVillages(map);
			if (villageEntities.size() > 0) {
				return "exists";
			}
			ShglVillageEntity villageOld = null;
			if(null != id && !"".equals(id)){
				villageOld = xqxxService.findById1(id);
			}
			if (villageOld == null) {// 为null 则数据库中没有该数据
				village.setName(name);
				village.setAdd_code(accCode);
				village.setAdd_name(accName);
				village.setAdd_time(new Date());
				village.setDescription(desId);
				village.setServiceStation(station);
				xqxxService.save1(village);// 保存
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglVmanagerEntity cmanager = new ShglVmanagerEntity();
					cmanager.setVillId(village.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					xqxxService.saveFzr(cmanager);
//				}
			} else {
				villageOld.setName(name);
				villageOld.setDescription(desId);
				villageOld.setServiceStation(station);
				xqxxService.update1(villageOld);// 修改
				/**删除之前的负责人--开始**/
				List<ShglVmanagerEntity> vlist = villageOld.getVmanList();
				for(ShglVmanagerEntity vmanagerEntity : vlist){
					xqxxService.delFzr(vmanagerEntity.getId());
				}
				/**删除之前的负责人--结束**/
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglVmanagerEntity cmanager = new ShglVmanagerEntity();
					cmanager.setVillId(villageOld.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					xqxxService.saveFzr(cmanager);
//				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加小区信息出错", e);
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
			xqxxService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除小区信息出错", e);
		}
		return "error";
	}

	/**
	 * 获取网格列表
	 * @param ssId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getData", produces = "text/html;charset=UTF-8")
	public String getComData(String ssId, HttpServletRequest req) {
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		
		List<ShglGridEntity> gridList = new ArrayList<ShglGridEntity>();
		List<ShglGridEntity> gridListWg = new ArrayList<ShglGridEntity>();
		if (ssId != null && !"".equals(ssId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ssId", ssId);
			gridList = wgglService.findAllSer1(paramMap);
			
			//App--如果是网格员，只显示所在网格(高帅-2017年12月19日)
			if(roleCode.indexOf("01")!=-1 && gridList.size()>0){
				String griId = acc.getGridId();
				for(int i=0; i<gridList.size(); i++){
					if(griId.equals(gridList.get(i).getId())){
						gridListWg.add(gridList.get(i));
					}
				}
				String returnJson = JSONArray.fromObject(gridListWg).toString();
				return returnJson;
			}
		}
		String returnJson = JSONArray.fromObject(gridList).toString();
		return returnJson;
	}
	
	/**
	 * 获取小区列表
	 * @param ssId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getXqData", produces = "text/html;charset=UTF-8")
	public String getXqData(String ssId, HttpServletRequest req) {
		
		List<ShglVillageEntity> xqList = new ArrayList<ShglVillageEntity>();
		if (ssId != null && !"".equals(ssId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ssId", ssId);
			xqList = xqxxService.findAllVillages1(paramMap);
		}
		String returnJson = JSONArray.fromObject(xqList).toString();
		return returnJson;
	}
	
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/xqxx/importView";
	}

	//导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, String flag,HttpServletRequest req,String commId,String ssId,String name) throws UnsupportedEncodingException {
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
			String[] titles = {"小区名称", "所属社区", "所属服务站", "添加人", "添加日期", "小区描述", "负责人及联系方式"};
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
			
			List<ShglVillageEntity> list = xqxxService.findAllVillages1(map);
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getServiceStation().getCommunity().getName());
				tmp.add(list.get(i).getServiceStation().getName());
				tmp.add(list.get(i).getAdd_name());
				tmp.add(list.get(i).getAddTimeFrm());
				tmp.add(list.get(i).getDescription());
				List<ShglVmanagerEntity> list1 = list.get(i).getVmanList();
				String manager = "";
				for(ShglVmanagerEntity shglCmanagerEntity : list1){
					if("".equals(manager)){
						if(null != shglCmanagerEntity.getManager() && !"".equals(shglCmanagerEntity.getManager())){
							if(null != shglCmanagerEntity.getPhone() && !"".equals(shglCmanagerEntity.getPhone())){
								manager = shglCmanagerEntity.getManager() + ": " + shglCmanagerEntity.getPhone();
							}else{
								manager = shglCmanagerEntity.getManager()+": 联系方式为空";
							}
						}else{
							if(null != shglCmanagerEntity.getPhone() && !"".equals(shglCmanagerEntity.getPhone())){
								manager = "负责人姓名为空: "+shglCmanagerEntity.getPhone();
							}
						}
					}else{
						if(null != shglCmanagerEntity.getManager() && !"".equals(shglCmanagerEntity.getManager())){
							if(null != shglCmanagerEntity.getPhone() && !"".equals(shglCmanagerEntity.getPhone())){
								manager = manager+shglCmanagerEntity.getManager() + ": " + shglCmanagerEntity.getPhone()+";";
							}else{
								manager = manager+shglCmanagerEntity.getManager()+": 联系方式为空"+";";
							}
						}else{
							if(null != shglCmanagerEntity.getPhone() && !"".equals(shglCmanagerEntity.getPhone())){
								manager = manager+"负责人姓名为空: "+shglCmanagerEntity.getPhone()+";";
							}
						}
					}
				}
				tmp.add(manager);
				data.add(tmp);
			}
			fileName_d = "小区管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}else{
			// 组织导出数据
			// 2.标题
			String[] titles = {"小区名称", "所属社区", "所属服务站", "小区描述", "负责人", "联系方式"};
			// 组织模板数据
			List<String> tmp = new ArrayList<String>();
			tmp.add("博昌小区");
			tmp.add("博昌社区");
			tmp.add("博昌服务站");
			tmp.add("博昌小区描述博昌小区描述博昌小区描述博昌小区描述");
			tmp.add("张三");
			tmp.add("17845454545");
			data.add(tmp);
			fileName_d = "小区管理_导入模板.xlsx";
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
			String[] titles = {"小区名称", "所属社区", "所属服务站", "小区描述", "负责人", "联系方式"};
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
					String sName = dMap.get("所属服务站") == null ? null : dMap.get("所属服务站").trim();
					String wName = dMap.get("小区名称") == null ? null : dMap.get("小区名称").trim();
					String dName = dMap.get("小区描述") == null ? null : dMap.get("小区描述").trim();
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
					addMapV.put("villName", wName);
					addMapV.put("ssId", serviceStationEntity.getId());
					ShglVillageEntity shglVillageEntity = xqxxService.findByName(addMapV);
					if(null != shglVillageEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下小区已存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					ShglVillageEntity village = new ShglVillageEntity();
					village.setAdd_code(acc.getAccCode());
					village.setAdd_name(acc.getName());
					village.setAdd_time(d);
					village.setServiceStation(serviceStationEntity);
					village.setDescription(dName);
					village.setName(wName);
					xqxxService.save1(village);//保存小区信息
					
					ShglVmanagerEntity cmanager = new ShglVmanagerEntity();
					cmanager.setVillId(village.getId());
					cmanager.setManager(cmanName);
					cmanager.setPhone(phoneNum);
					xqxxService.saveFzr(cmanager);//保存小区负责人信息
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
