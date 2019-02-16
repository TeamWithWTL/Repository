package com.jcwx.action.shgl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglGmanagerEntity;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.service.shgl.ZfjgService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

/**
 * 政府机构Controller
 * @author wangjinxiao
 *
 */

@Controller
@RequestMapping("/shgl/zfjg")
public class ZfjgAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(ZfjgAction.class);

	@Autowired
	private ZfjgService zfjgService;
	@Autowired
	private SjzdService sjzdService;
	
	/**
	 * 列表页数据
	 * 
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
		model.addAttribute("roleCode", acc.getRole_code());
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("manager", manager);
				map.put("phone", phone);
				Pagenate<ShglGovernmentEntity> pagenate = zfjgService.findByPage1(pageNumber, pageSize, map);
				if (pagenate.getList() != null) {
					for (ShglGovernmentEntity community : pagenate.getList()) {
						List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
						String flag = "0";
						for(SysParamDesc sysParamDesc : xzqh){
							if(sysParamDesc.getItemCode().equals(community.getStrativeId())){
								community.setStrative_name(sysParamDesc.getItemName());
								flag = "1";
								break;
							}
						}
						if("0".equals(flag)){
							community.setStrative_name("");
						}
					}
				}
				model.addAttribute("pagenate", pagenate);
				return "shgl/zfjg/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询政府机构出错", e);
		}
		return "shgl/zfjg/index";
	}
	
	/**
	 * 跳转添加及修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id) {
		ShglGovernmentEntity governmentEntity = null;
		ShglGmanagerEntity gmanager = null;//存放第一条固定负责人数据
		List<ShglGmanagerEntity> gmanagerList = new ArrayList<ShglGmanagerEntity>();//存除第一条数据外的其他数据
 		if (null != id && !"".equals(id)) {
 			governmentEntity = zfjgService.findById(id);
			for(int i=0; i<governmentEntity.getGmList().size(); i++){
				if(i == 0){
					gmanager = governmentEntity.getGmList().get(0);
				}else{
					gmanagerList.add(governmentEntity.getGmList().get(i));
				}
			}
		}
		model.addAttribute("governmentEntity", governmentEntity);
		List<SysParamDesc> strList = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
//		List<SysStrative> strList = sqglService.findAllStra();
		model.addAttribute("strList", strList);
		model.addAttribute("gmanager", gmanager);
		model.addAttribute("gmanagerList", gmanagerList);
		return "shgl/zfjg/addEdit";
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
	public String doSave(HttpServletRequest req, ShglGovernmentEntity government, String id, String strativeId,
			String name, String managers, String phones) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("strative_id", strativeId);
			List<ShglGovernmentEntity> governmentEntities = zfjgService.findAllGover(map);
			if (governmentEntities.size() > 0) {
				return "exists";
			}
			ShglGovernmentEntity governmentOld = zfjgService.findById(id);
			if (null == governmentOld) {// 为null 则数据库中没有该数据
				government.setAddCode(accCode);
				government.setAddName(accName);
				government.setAddTime(new Date());
				zfjgService.save(government);// 保存
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglGmanagerEntity cmanager = new ShglGmanagerEntity();
					cmanager.setGoveId(government.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					zfjgService.saveFzr(cmanager);
//				}
			} else {
				BeanUtils.copyProperties(government, governmentOld,
						new String[] {"addCode", "addName", "addTime","gmList"});
				zfjgService.update(governmentOld);// 修改
				/***删除之前的负责人及负责人联系电话               -------------开始  **/
				List<ShglGmanagerEntity> cmList = governmentOld.getGmList();
				for(ShglGmanagerEntity cmanagerEntity : cmList){
					zfjgService.delFzr(cmanagerEntity.getId());
				}
				/***删除之前的负责人及负责人联系电话              -------------结束 **/
				String[] manager = managers.split(";");
				String[] phone = phones.split(";");
//				for(int i=0; i<manager.length; i++){
					ShglGmanagerEntity cmanager = new ShglGmanagerEntity();
					cmanager.setGoveId(governmentOld.getId());
					if(manager.length>0){
						cmanager.setManager(manager[0]);
					}
					if(phone.length>0){
						cmanager.setPhone(phone[0]);
					}
					zfjgService.saveFzr(cmanager);
//				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加政府机构出错", e);
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
			zfjgService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除网格信息出错", e);
		}
		return "error";
	}
	
	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
//		model.addAttribute("dataurl", "/shgl/wggl/getSingleAreaData.do");
		model.addAttribute("url", "/shgl/zfjg/saveArea.do");
		model.addAttribute("type", "ly");
		return "shgl/choseArea";
	}
	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/zfjg/importView";
	}

	//导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, 
			String flag, String name) throws UnsupportedEncodingException {
		flag = StringUtil.filterchart(flag);
		if(null != name){//防止导入出错--高帅（2017年12月14日--代码修改）
			name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
		}
		// 1.数据
		List<List<String>> data = new ArrayList<List<String>>();
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		if(flag.equals("export")) {
			// 组织导出数据
			// 2.标题
			String[] titles = {"机构名称", "行政区划", "添加人", "添加日期", "机构描述", "负责人及联系方式"};
			Map<String, String> addMap = new HashMap<String, String>();
			addMap.put("name", name);
			List<ShglGovernmentEntity> list = zfjgService.findGoverByName(addMap);
			
			for(int i=0; i<list.size(); i++){
				List<String> tmp = new ArrayList<String>();
				
				tmp.add(list.get(i).getName());
				
				List<SysParamDesc> xzqh = sjzdService.findByCode("10015").getSysParamDesc();// 行政区划
				String flages = "0";
				for (SysParamDesc sysParamDesc : xzqh) {
					if (sysParamDesc.getItemCode().equals(list.get(i).getStrativeId())) {
						list.get(i).setStrative_name(sysParamDesc.getItemName());
						flages = "1";
						break;
					}
				}
				if ("0".equals(flages)) {
					list.get(i).setStrative_name("");
				}
				tmp.add(list.get(i).getStrative_name());
				tmp.add(list.get(i).getAddName());
				tmp.add(list.get(i).getAddTimeFrm());
				tmp.add(list.get(i).getDescription());
				
				List<ShglGmanagerEntity> list1 = list.get(i).getGmList();
				String manager = "";
				for(ShglGmanagerEntity shglCmanagerEntity : list1){
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
			fileName_d = "政府机构管理基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, null);
		}else{
			// 组织导出数据
			// 2.标题
			String[] titles = {"机构名称", "行政区划", "机构描述", "负责人", "联系方式"};
			// 组织模板数据
			List<String> tmp = new ArrayList<String>();
			tmp.add("机构名称");
			tmp.add("行政区划");
			tmp.add("机构描述");
			tmp.add("张三");
			tmp.add("18857424875");
			data.add(tmp);
			fileName_d = "政府机构管理_导入模板.xlsx";
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
			String[] titles = {"机构名称", "行政区划", "机构描述", "负责人", "联系方式"};
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
//				Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
//				Pattern p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
//				Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的 
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, String> dMap = dataList.get(i);
					String cName = dMap.get("机构名称") == null ? null : dMap.get("机构名称").trim();
					String strName = dMap.get("行政区划") == null ? null : dMap.get("行政区划").trim();
					String sName = dMap.get("机构描述") == null ? null : dMap.get("机构描述").trim();
					String mName = dMap.get("负责人") == null ? null : dMap.get("负责人").trim();
					String phone = dMap.get("联系方式") == null ? null : dMap.get("联系方式").trim();
					if(null == cName || "".equals(cName)){
						errMsg2 += "第" + (i+2) + "行，机构名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(null == strName || "".equals(strName)){
						errMsg2 += "第" + (i+2) + "行，行政区划为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if(!"博昌街道".equals(strName)){
						errMsg2 += "第" + (i+2) + "行，行政区划不存在，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> addMap = new HashMap<String, String>();
					addMap.put("jgName", cName);
					ShglGovernmentEntity governmentEntity = zfjgService.findByName(addMap);
					if(null != governmentEntity){
						errMsg2 += "第" + (i+2) + "行，政府机构已存在，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					ShglGovernmentEntity government = new ShglGovernmentEntity();
					government.setAddCode(acc.getAccCode());
					government.setAddName(acc.getName());
					government.setAddTime(d);
					government.setName(cName);
					government.setDescription(sName);
					government.setStrativeId("1");//博昌街道
					zfjgService.save(government);// 保存政府机构信息
					
					ShglGmanagerEntity cmanager = new ShglGmanagerEntity();
					cmanager.setGoveId(government.getId());
					cmanager.setManager(mName);
					cmanager.setPhone(phone);
					zfjgService.saveFzr(cmanager);//保存政府机构法人信息
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
