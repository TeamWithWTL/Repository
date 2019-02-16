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
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.ExcelUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 居民信息
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/jmxx")
public class JmxxAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private XqxxService xqxxService;

	/**
	 * 首页数据
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param category
	 * @param commId
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, 
			String name, String category, String ssId, String commId, String gridId, String buildId, String roomNo, String unitNo, 
			String xqId, HttpServletRequest req) {
		//获取登录用户信息
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		//获取所有特殊人口类型列表
		List<SysParamDesc> typeList = jmxxService.getTypeList();
		
		String roleCode = acc.getRole_code();
		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);
		// 判断用户权限结束
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin) || "fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", acc.getCommId());// 社区下拉列表只显示当前用户负责的社区
		} else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);// 社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();// 当前用户负责的社区Id
					map.put("dqCommId", dqCommId);
				}
				if ("fuwuzhan".equals(isAdmin)) {
					String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
					map.put("dqSsId", dqSsId);
				}
				if ("admin".equals(isAdmin)) {// 最高权限查所有
					map.put("dqCommId", null);
					map.put("dqSsId", null);
				}
				map.put("name", name);
				map.put("category", category);
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("gridId", gridId);
				map.put("buildId", buildId);
				map.put("roomNo", roomNo);
				map.put("unitNo", unitNo);
				map.put("xqId", xqId);
				Pagenate<ShglInmateEntity> pagenate = jmxxService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				Map sysStations = sjzdService.findMapByPCode01("10007");// 民族
				model.addAttribute("sysStations", sysStations);
				log.info("size===========================" + pagenate.getRsCnts());
				return "shgl/jmxx/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询居民信息出错", e);
		}
		
		model.addAttribute("typeList", typeList);
		
		return "shgl/jmxx/index";
	}

	/**
	 * 跳转添加及修改界面
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id, HttpServletRequest request) {
		SysAccCount sysAcc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String roleCode = sysAcc.getRole_code();
		// 判断用户权限

		String isAdmin = jurisdiction(roleCode);
		// 判断用户权限结束

		ShglInmateEntity inmateEntity = null;
		String commId = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin) || "fuwuzhan".equals(isAdmin)) {
			paramMap.put("dqCommId", sysAcc.getCommId());// 社区下拉列表只显示当前用户负责的社区
		} else if ("admin".equals(isAdmin)) {
			paramMap.put("dqCommId", null);// 社区下拉列表显示所有小区
		}
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);
		String types = "";
		if (null != id && !"".equals(id)) {
			inmateEntity = jmxxService.findById1(id);
			commId = inmateEntity.getBuild().getVillage().getServiceStation().getCommunity().getId();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("commId", commId);
			model.addAttribute("ssList", ssList);

			Map<String, String> paramMap3 = new HashMap<String, String>();
			paramMap3.put("ssId", inmateEntity.getBuild().getVillage().getServiceStation().getId());
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap3);// 网格列表
			model.addAttribute("gridList", gridList);

			Map<String, String> paramMap4 = new HashMap<String, String>();
			if (null == inmateEntity.getBuild().getGrid() || "".equals(inmateEntity.getBuild().getGrid())) {
				paramMap4.put("gridId", null);
			} else {
				paramMap4.put("gridId", inmateEntity.getBuild().getGrid().getId());
			}

			List<ShglBuildingEntity> buildList = lyglService.findAllBuilds1(paramMap4);// 楼宇列表
			model.addAttribute("buildList", buildList);
			List<String> unitCnts = new ArrayList<String>();
			List<String> rooms = new ArrayList<String>();
			for (int i = 1; i <= Integer.parseInt(inmateEntity.getBuild().getUnit_cnt()); i++) {
				unitCnts.add(String.valueOf(i));
			}
			for (int y = 1; y <= Integer.parseInt(inmateEntity.getBuild().getFloor_cnt()); y++) {
				for (int j = 1; j <= Integer.parseInt(inmateEntity.getBuild().getFamily_cnt()); j++) {
					if (j < 10) {
						rooms.add(y + "0" + j);
					} else {
						rooms.add(String.valueOf(y) + String.valueOf(j));
					}
				}
			}
			for (ShglInmateTypeEntity shglInmateTypeEntity : inmateEntity.getInmateTList()) {
				if ("".equals(types)) {
					types = shglInmateTypeEntity.getCategory() + ",";
				} else {
					types = types + shglInmateTypeEntity.getCategory() + ",";
				}
			}
			model.addAttribute("unitCnts", unitCnts);
			model.addAttribute("rooms", rooms);
		}
		List<SysParamDesc> zzmm = sjzdService.findByCode("10001").getSysParamDesc();// 政治面貌
		List<SysParamDesc> hzgx = sjzdService.findByCode("10002").getSysParamDesc();// 户主关系
		// SysParamDesc
		// sysParamDesc=jmxxService.findHzidName("01");//查询户主关系字段表01 本人
		// hzgx.remove(sysParamDesc);//把户主本人字段移除
		List<SysParamDesc> hyzk = sjzdService.findByCode("10003").getSysParamDesc();// 婚姻状况
		List<SysParamDesc> whcd = sjzdService.findByCode("10004").getSysParamDesc();// 文化程度
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();// 人员分类
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族
		model.addAttribute("zzmm", zzmm);
		model.addAttribute("hzgx", hzgx);
		model.addAttribute("hyzk", hyzk);
		model.addAttribute("whcd", whcd);
		model.addAttribute("ryfl", ryfl);
		model.addAttribute("inmateEntity", inmateEntity);
		model.addAttribute("sysStations", sysStations);
		model.addAttribute("types", types);
		// 查询所有是户主的人员
		List<ShglInmateEntity> hzList = jmxxService.findHz();
		model.addAttribute("hzList", hzList);
		return "shgl/jmxx/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
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
	public String doSave(HttpServletRequest req, ShglInmateEntity inmate, String id, String commId, String ssId,
			String buildId, String unit_no, String room_no, String card_no, String birthdayFrm, String political, String type) {
		ShglBuildingEntity build = lyglService.findById1(buildId);// 楼宇
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", inmate.getName());
			map.put("buildId", buildId);
			map.put("room_no", room_no);
			map.put("unit_no", unit_no);
			List<ShglInmateEntity> inmateEntities = jmxxService.findAllInmates(map);
			if (inmateEntities.size() > 0) {
				return "exists";
			}
			ShglInmateEntity inmateOld = jmxxService.findById1(id);
			if (null == inmateOld) {// 为null 则数据库中没有该数据

				inmate.setSsId(ssId);
				inmate.setBuild(build);
				inmate.setCommId(commId);
				if(null == birthdayFrm || "".equals(birthdayFrm)){
					inmate.setBirthday(DateUtils.parseDate("1970-1-1", "yyyy-MM-dd"));
				}else{
					inmate.setBirthday(DateUtils.parseDate(birthdayFrm, "yyyy-MM-dd"));
				}
				jmxxService.save1(inmate);// 保存

				String[] type1 = type.split(",");
				for (String tString : type1) {
					if ("".equals(tString) || null == tString) {
						continue;
					}
					ShglInmateTypeEntity s = new ShglInmateTypeEntity();
					s.setInmate(inmate);
					s.setCategory(tString);
					jmxxService.saveLx(s);
				}
			} else {
				BeanUtils.copyProperties(inmate, inmateOld, new String[] { "inmateTList" });

				inmateOld.setSsId(ssId);
				inmateOld.setBuild(build);
				inmateOld.setCommId(commId);
				inmateOld.setPolitical(political); 
				inmateOld.setBirthday(DateUtils.parseDate(birthdayFrm, "yyyy-MM-dd"));
				jmxxService.update1(inmateOld);// 修改
				/********** 删除之前的类型 ***********/
				List<ShglInmateTypeEntity> list = inmateOld.getInmateTList();
				for (ShglInmateTypeEntity shglInmateTypeEntity : list) {
					jmxxService.delLx(shglInmateTypeEntity.getId());
				}
				/********* 删除之前的类型 **********/
				String[] type1 = type.split(",");
				for (String tString : type1) {
					ShglInmateTypeEntity s = new ShglInmateTypeEntity();
					s.setInmate(inmateOld);
					s.setCategory(tString);
					jmxxService.saveLx(s);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("添加居民信息出错", e);
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
			jmxxService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除居民信息出错", e);
		}
		return "error";
	}

	/**
	 * 获取楼宇列表
	 * @param ssId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getData", produces = "text/html;charset=UTF-8")
	public String getComData(String gridId) {
		List<ShglBuildingEntity> buildList = new ArrayList<ShglBuildingEntity>();
		if (null != gridId && !"".equals(gridId)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("gridId", gridId);
			buildList = lyglService.findAllBuilds1(paramMap);
		}
		String returnJson = JSONArray.fromObject(buildList).toString();
		return returnJson;
	}

	/**
	 * 根据id获取楼宇
	 * @param ssId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getBuildData", produces = "text/html;charset=UTF-8")
	public String getBuildData(String gridId) {
		ShglBuildingEntity buildingEntity = lyglService.findById1(gridId);
		String returnJson = JSONObject.fromObject(buildingEntity).toString();
		return returnJson;
	}

	/**
	 * 跳转查看界面
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model, String id, String flag) {
		//获取居民信息
		ShglInmateEntity inmateEntity = jmxxService.findById1(id);
		
		model.addAttribute("inmateEntity", inmateEntity);
		String cardNo = inmateEntity.getCard_no();
		Map<String, String> map = new HashMap<String, String>();
		List<ShglInmateEntity> inmateEntities = new ArrayList<ShglInmateEntity>();
		if(cardNo!=null &&!"".equals(cardNo)){
			map.put("card_no", cardNo);
			inmateEntities = jmxxService.findAllInmates1(map);
		}else{
			inmateEntities.add(inmateEntity);
		}
		model.addAttribute("pList", inmateEntities);
		List<SysParamDesc> zzmm = sjzdService.findByCode("10001").getSysParamDesc();// 政治面貌
		List<SysParamDesc> hyzk = sjzdService.findByCode("10003").getSysParamDesc();// 婚姻状况
		List<SysParamDesc> whcd = sjzdService.findByCode("10004").getSysParamDesc();// 文化程度
		List<SysParamDesc> ryfl = sjzdService.findByCode("10005").getSysParamDesc();// 人员分类
		List<SysParamDesc> sysStations = sjzdService.findByCode("10007").getSysParamDesc();// 民族
		//List<SysParamDesc> hzgx = sjzdService.findByCode("10002").getSysParamDesc();// 与户主关系
		String types = "";
		for (ShglInmateTypeEntity shglInmateTypeEntity : inmateEntity.getInmateTList()) {
			if ("".equals(types)) {
				types = shglInmateTypeEntity.getCategory() + ",";
			} else {
				types = types + shglInmateTypeEntity.getCategory() + ",";
			}
		}
		
		model.addAttribute("ryfl", ryfl);
		model.addAttribute("zzmm", zzmm);
		model.addAttribute("hyzk", hyzk);
		model.addAttribute("whcd", whcd);
		model.addAttribute("sysStations", sysStations);
		model.addAttribute("types", types);
		
		// model.addAttribute("hzgx",hzgx);
		// 查询此用记相关的家庭人员
		// String hzid = inmateEntity.getHzid();//是户主，hzid等同于本身id
		// ,不是户主，hzid则是户主人员的id
		// 以户主身份查询家庭人员
		/*
		 * List<ShglInmateEntity>familyList=jmxxService.findFamilyByHzId(hzid);
		 * for (ShglInmateEntity shglInmateEntity : familyList) { String
		 * relation = shglInmateEntity.getHm_relation();//户主关系 SysParamDesc
		 * sysParamDesc=jmxxService.findHzidName(relation);//查询户主关系字段表的名字
		 * if(sysParamDesc==null || sysParamDesc.getItemName()==null ||
		 * "".equals(sysParamDesc.getItemName())){ shglInmateEntity.setHzgx("");
		 * }else{ shglInmateEntity.setHzgx(sysParamDesc.getItemName()); } }
		 */
		
		// 地址查询家庭关系，，同一房间号判定为一家人
		String commId = inmateEntity.getCommId();	// 展示住户居住的社区id
		String ssId = inmateEntity.getSsId();	// 展示住户居住的服务站id
		String buildId = inmateEntity.getBuild().getId();	// 展示住户居住的楼宇id
		String villageId = inmateEntity.getBuild().getVillage().getId();	// 展示住户居住的楼宇的小区id
		String gridId = inmateEntity.getBuild().getGrid().getId();	// 展示住户居住的楼宇的网格id
		String unit_no = inmateEntity.getUnit_no();	// 展示住户居住的单元号
		String room_no = inmateEntity.getRoom_no();	// 展示住户居住的房间号
		String name = inmateEntity.getBuild().getName();	// 展示住户居住的楼栋号

		Map<String, String> tiesMap = new HashMap<String, String>();
		tiesMap.put("commId", commId);
		tiesMap.put("ssId", ssId);
		tiesMap.put("build", buildId);
		tiesMap.put("villageId", villageId);
		tiesMap.put("unit_no", unit_no);
		tiesMap.put("room_no", room_no);
		tiesMap.put("gridId", gridId);
		tiesMap.put("name", name);
		List<ShglInmateEntity> familyList = jmxxService.findFamilyByHzId(tiesMap);	// 地址查询家庭关系
		familyList.remove(inmateEntity);	// 先移除自身，
		// familyList.add(0, inmateEntity);	//再添加到第一位
		model.addAttribute("familyList", familyList);
		model.addAttribute("flag", flag);
		return "shgl/jmxx/view";
	}

	// 跳转到导入页面
	@RequestMapping("/shouImportView")
	public String shouImportView(Model model) {
		return "shgl/jmxx/importView";
	}

	// 导出
	@RequestMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, String flag, HttpServletRequest req,
			String commId, String ssId, String gridId, String xqId, String name, String inmateType) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		try {
			if (null != name) {// 防止导入出错--高帅（2017年12月14日--代码修改）
				name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 判断用户权限
		String isAdmin = jurisdiction(roleCode);
		
		flag = StringUtil.filterchart(flag);
		// 组织导出数据
		// 1.标题
		String[] titles ={"所属社区", "所属服务站", "所属小区", "所属网格","楼宇", "单元", "房间","姓名", "身份证号",  "联系电话", "与户主关系",  "民族", 
				"政治面貌", "婚姻状况", "文化程度","人员分类", "户籍所在", "工作单位",  "车辆号牌", "性别", "出生日期"};
		// 2.数据
		List<List<String>> data = new ArrayList<List<String>>();
		// 3.字典
		Map<String, String> zzmm = sjzdService.findMapByPCode("10001");// 政治面貌
//		Map<String, String> andHz = sjzdService.findMapByPCode01("10002");// 与户主关系
		Map<String, String> hyzk = sjzdService.findMapByPCode("10003");// 婚姻状况
		Map<String, String> whcd = sjzdService.findMapByPCode("10004");// 文化程度
//		Map<String, String> ryfl = sjzdService.findMapByPCode("10005");// 人员分类
		Map<String, String> ryfl = sjzdService.findMapByPCode01("10005");// 人员分类
//		Map<String, String> mz = sjzdService.findMapByPCode("10007");// 民族
		Map<String, String> mz = sjzdService.findMapByPCode01("10007");// 民族

		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		dict.put("政治面貌", zzmm);
//		dict.put("与户主关系", andHz);
		dict.put("婚姻状况", hyzk);
		dict.put("文化程度", whcd);
		dict.put("人员分类", ryfl);
		dict.put("民族", mz);
		String fileName = "";
		String fileName_d = ""; // 下载显示文件名
		if (flag.equals("export")) {
			Map<String, String> map = new HashMap<String, String>();
			if ("fuwuzhan".equals(isAdmin)) {
				map.put("dqSsId", acc.getSsId());// 只导出当前用户负责的服务站
			} else if ("shequ".equals(isAdmin)) {
				map.put("dqCommId", acc.getCommId());// 只导出当前用户负责社区下的所有服务站
			} else if ("admin".equals(isAdmin)) {
				map.put("dqSsId", null);// 导出所有
				map.put("dqCommId", null);
			}
			map.put("commId", commId);
			map.put("ssId", ssId);
			map.put("xqId", xqId);
			map.put("gridId", gridId);
			map.put("name", name);
			map.put("inmateType", inmateType);
			List<ShglInmateEntity> list = jmxxService.findAllInmates1(map);
			for (int i = 0; i < list.size(); i++) {
				List<String> tmp = new ArrayList<String>();
//				{"所属社区", "所属服务站","所属小区","所属网格",
//					"楼宇", "单元", "房间","姓名", "身份证号",  "联系电话", "与户主关系",  "民族", 
//					"政治面貌", "婚姻状况", "文化程度","人员分类", "户籍所在", "工作单位",  "车辆号牌", "性别", "出生日期"};
				tmp.add(list.get(i).getBuild().getVillage().getServiceStation().getCommunity().getName());
				tmp.add(list.get(i).getBuild().getVillage().getServiceStation().getName());
				tmp.add(list.get(i).getBuild().getVillage().getName());
				tmp.add(list.get(i).getBuild().getGrid().getName());
				
				tmp.add(list.get(i).getBuild().getName());
				tmp.add(list.get(i).getUnit_no());
				tmp.add(list.get(i).getRoom_no());
				tmp.add(list.get(i).getName());
				tmp.add(list.get(i).getCard_no());
				tmp.add(list.get(i).getTelephone());
				tmp.add(list.get(i).getHm_relation());
				tmp.add(mz.get(list.get(i).getNation()));
				
				tmp.add(zzmm.get(list.get(i).getPolitical()));
				tmp.add(hyzk.get(list.get(i).getMarriage()));
				tmp.add(whcd.get(list.get(i).getEducation()));
		

				String type = "";
				List<ShglInmateTypeEntity> list2 = list.get(i).getInmateTList();
				for (ShglInmateTypeEntity shglInmateTypeEntity : list2) {
					if ("".equals(type)) {
						if(null != shglInmateTypeEntity.getCategory() && !"".equals(shglInmateTypeEntity.getCategory())){
							type = ryfl.get(shglInmateTypeEntity.getCategory()) + ",";
						}
					} else {
						if(null != shglInmateTypeEntity.getCategory() && !"".equals(shglInmateTypeEntity.getCategory())){
							type = type + ryfl.get(shglInmateTypeEntity.getCategory()) + ",";
						}else{
							type = type+"";
						}
					}
				}
				tmp.add(type);
				
				tmp.add(list.get(i).getHouse_register());
				tmp.add(list.get(i).getWork_unit());
				tmp.add(list.get(i).getCar_no());
				
				tmp.add(list.get(i).getSex() == "E"?"女":"男");
				tmp.add(list.get(i).getBirthdayFrm());
				data.add(tmp);
			}
			fileName_d = "居民信息基本信息.xlsx";
			// 4.生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, dict);
		} else {
			// 组织模板数据
//			{"所属社区", "所属服务站","所属小区","所属网格",
//			"楼宇", "单元", "房间","姓名", "身份证号",  "联系电话", "与户主关系",  "民族", 
//			"政治面貌", "婚姻状况", "文化程度","人员分类", "户籍所在", "工作单位",  "车辆号牌", "性别", "出生日期"};
			List<String> tmp = new ArrayList<String>();
			
			tmp.add("博昌社区");
			tmp.add("博昌服务站");
			tmp.add("博昌小区");
			tmp.add("博昌网格");
			tmp.add("1");
			tmp.add("1");
			tmp.add("101");
			tmp.add("张三");
			tmp.add("370321888547547124");
			tmp.add("18854741475");
			tmp.add("本人");
			tmp.add("01(参见民族页签)");
			tmp.add("1(参见政治面貌页签)");
			tmp.add("1(参见婚姻状况页签)");
			tmp.add("1(参见文化程度页签)");
			
			tmp.add("01(参见人员分类页签)");
			tmp.add("博昌");
			tmp.add("某某单位");
			tmp.add("鲁c88888");
		
			tmp.add("男/女");
			tmp.add("参照：2017-01-01");
			data.add(tmp);
			fileName_d = "居民信息_导入模板.xlsx";
			// 生成Excel文件
			fileName = ExcelUtils.createExcel(titles, data, dict);
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
			String importPath = ProjectUtils.getSysCfg("importPath");// 导入路径
			// 上传文件到服务器
			List<Map<String, String>> fiList = FileUtil.fileUpload(importPath, myFile);
			// 读取Excel并校验文件
			String fileName = fiList.get(0).get("newName");
			log.info(fileName);
			String[] titles = {"所属社区", "所属服务站", "所属小区", "所属网格","楼宇", "单元", "房间","姓名", "身份证号",  "联系电话", "与户主关系",  "民族", 
					"政治面貌", "婚姻状况", "文化程度","人员分类", "户籍所在", "工作单位",  "车辆号牌", "性别", "出生日期"};
			// 基本校验
			Map<String, Object> checkResult = ExcelUtils.checkExcel(fileName, titles);
			String errMsg = checkResult.get("errMsg").toString();
			log.info(errMsg);
			if ("".equals(errMsg)) {
				// 文件基本校验通过，开始自定义数据校验
				@SuppressWarnings("unchecked")
				List<Map<String, String>> dataList = (List<Map<String, String>>) checkResult.get("dataList");
				String errMsg2 = "";// 判断是否存在数据
				String flag = "0";// 判断是否有错误提示
				Map<String, String> zzmm = sjzdService.findMapByPCode("10001");// 政治面貌
				Map<String, String> andHz = sjzdService.findMapByPCode01("10002");// 与户主关系
				Map<String, String> hyzk = sjzdService.findMapByPCode("10003");// 婚姻状况
				Map<String, String> whcd = sjzdService.findMapByPCode("10004");// 文化程度
				Map<String, String> ryfl = sjzdService.findMapByPCode01("10005");// 人员分类
				Map<String, String> mz = sjzdService.findMapByPCode01("10007");// 民族
				Pattern p = Pattern.compile("^[0-9]+$"); // 验证整数
				// 自定义数据校验通过，开始导入数据到数据库
				for (int i = 0; i < dataList.size(); i++) {
					try{
					Map<String, String> dMap = dataList.get(i);
					String cName = dMap.get("所属社区") == null ? null : dMap.get("所属社区").trim();
					String sName = dMap.get("所属服务站") == null ? null : dMap.get("所属服务站").trim();
					String gName = dMap.get("所属网格") == null ? null : dMap.get("所属网格").trim();
					String vName = dMap.get("所属小区") == null ? null : dMap.get("所属小区").trim();
					String build = dMap.get("楼宇") == null ? null : dMap.get("楼宇").trim();
					
					String unit = dMap.get("单元") == null ? null : dMap.get("单元").trim();
					String room = dMap.get("房间") == null ? null : dMap.get("房间").trim();
					String name = dMap.get("姓名") == null ? null : dMap.get("姓名").trim();
					String sex = dMap.get("性别") == null ? null : dMap.get("性别").trim();
					String birthday = dMap.get("出生日期") == null ? null : dMap.get("出生日期").trim();
					String cardNo = dMap.get("身份证号") == null ? null : dMap.get("身份证号").trim();
					String phone = dMap.get("联系电话") == null ? null : dMap.get("联系电话").trim();

					String hm_relation = dMap.get("与户主关系") == null ? null : dMap.get("与户主关系").trim();
					String nation = dMap.get("民族") == null ? null : dMap.get("民族").trim();
					String political = dMap.get("政治面貌") == null ? null : dMap.get("政治面貌").trim();
					String marriage = dMap.get("婚姻状况") == null ? null : dMap.get("婚姻状况").trim();
					String education = dMap.get("文化程度") == null ? null : dMap.get("文化程度").trim();

					String type = dMap.get("人员分类") == null ? null : dMap.get("人员分类").trim();
					String house_register = dMap.get("户籍所在") == null ? null : dMap.get("户籍所在").trim();
					String work_unit = dMap.get("工作单位") == null ? null : dMap.get("工作单位").trim();
					String car_no = dMap.get("车辆号牌") == null ? null : dMap.get("车辆号牌").trim();
					if (null == cName || "".equals(cName)||
						null == cName || "".equals(cName)||
						null == sName || "".equals(sName)||
						null == gName || "".equals(gName)||
						null == vName || "".equals(vName)||
						null == name || "".equals(name)||
						null == room || "".equals(room)||
						null == unit || "".equals(unit)) {
						continue;
					}
					if (null == cName || "".equals(cName)) {
						errMsg2 += "第" + (i + 2) + "行，社区名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == sName || "".equals(sName)) {
						errMsg2 += "第" + (i + 2) + "行，服务站名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == gName || "".equals(gName)) {
						errMsg2 += "第" + (i + 2) + "行，网格名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == vName || "".equals(vName)) {
						errMsg2 += "第" + (i + 2) + "行，小区名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == build || "".equals(build)) {
						errMsg2 += "第" + (i + 2) + "行，楼宇名称为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == unit || "".equals(unit)) {
						errMsg2 += "第" + (i + 2) + "行，单元为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					} else if (!p.matcher(unit).matches()) {
						errMsg2 += "第" + (i + 2) + "行，单元不是整数，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == room || "".equals(room)) {
						errMsg2 += "第" + (i + 2) + "行，房间为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					} else if (!p.matcher(room).matches()) {
						errMsg2 += "第" + (i + 2) + "行，房间不是整数，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					if (null == name || "".equals(name)) {
						errMsg2 += "第" + (i + 2) + "行，姓名为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
//					if (null == cardNo || "".equals(cardNo)) {
//						errMsg2 += "第" + (i + 2) + "行，身份证为空，导入失败！\r" + System.getProperty("line.separator");
//						flag = "1";
//						continue;
//					}
					if (null == hm_relation || "".equals(hm_relation)) {
						errMsg2 += "第" + (i + 2) + "行，与户主关系为空，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
//					else if(andHz.get(hm_relation) == null){
//						errMsg2 += "第" + (i + 2) + "行，户主关系与[与户主关系]sheet页中的户主关系编码不一致，导入失败！\r"
//								+ System.getProperty("line.separator");
//						flag = "1";
//						continue;
//					}
					if (null != nation && !"".equals(nation)) {
						if (mz.get(nation) == null) {
							errMsg2 += "第" + (i + 2) + "行，民族与[民族]sheet页中的民族编码不一致，导入失败！\r"
									+ System.getProperty("line.separator");
							flag = "1";
							continue;
						}
					}
					if (null != political && !"".equals(political)) {
						if (zzmm.get(political) == null) {
							errMsg2 += "第" + (i + 2) + "行，政治面貌与[政治面貌]sheet页中的政治面貌编码不一致，导入失败！\r"
									+ System.getProperty("line.separator");
							flag = "1";
							continue;
						}
					}
					if (null != marriage && !"".equals(marriage)) {
						if (hyzk.get(marriage) == null) {
							errMsg2 += "第" + (i + 2) + "行，婚姻状况与[婚姻状况]sheet页中的婚姻状况编码不一致，导入失败！\r"
									+ System.getProperty("line.separator");
							flag = "1";
							continue;
						}
					}
					if (null != education && !"".equals(education)) {
						if (whcd.get(education) == null) {
							errMsg2 += "第" + (i + 2) + "行，文化程度与[文化程度]sheet页中的文化程度编码不一致，导入失败！\r"
									+ System.getProperty("line.separator");
							flag = "1";
							continue;
						}
					}
					if (null != type && !"".equals(type)) {
						String[] types = type.split(",");
						String flag1 = "0";
						for (String tyString : types) {
							if (ryfl.get(tyString) == null) {
								errMsg2 += "第" + (i + 2) + "行，人员分类与[人员分类]sheet页中的人员分类编码不一致，导入失败！\r"
										+ System.getProperty("line.separator");
								flag = "1";
								flag1 = "1";
								break;
							}
						}
						if ("1".equals(flag1)) {
							continue;
						}
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
					addMapG.put("grName", gName);
					addMapG.put("ssId", serviceStationEntity.getId());
					ShglGridEntity gridEntity = wgglService.findByName(addMapG);
					if(null == gridEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下网格不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					Map<String, String> addMapV = new HashMap<String, String>();
					addMapV.put("villName", vName);
					addMapV.put("ssId", serviceStationEntity.getId());
					ShglVillageEntity shglVillageEntity = xqxxService.findByName(addMapV);
					if(null == shglVillageEntity){
						errMsg2 += "第" + (i+2) + "行，该所属服务站下小区不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					Map<String, String> addMapB = new HashMap<String, String>();
					addMapB.put("lyName", build);
					addMapB.put("vId", shglVillageEntity.getId());
					ShglBuildingEntity buildingEntity = lyglService.findByName(addMapB);// 楼宇
					if(null == buildingEntity){
//						buildingEntity = new ShglBuildingEntity();
//						buildingEntity.setAdd_name(acc.getName());
//						buildingEntity.setAdd_time(new Date());
//						buildingEntity.setGrid(gridEntity);
//						buildingEntity.setVillage(shglVillageEntity);
//						buildingEntity.setCommId(commId);
//						lyglService.save1(buildingEntity);// 保存
						errMsg2 += "第" + (i+2) + "行，该所属小区下楼宇不存在,导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("buildId", buildingEntity.getId());
					map.put("room_no", room);
					map.put("unit_no", unit);
					map.put("name", name);
					List<ShglInmateEntity> inmateEntities = jmxxService.findAllInmates(map);
					if (inmateEntities.size() > 0) {
						errMsg2 += "第" + (i + 2) + "行，此居民已存在，导入失败！\r" + System.getProperty("line.separator");
						flag = "1";
						continue;
					}
					
					ShglInmateEntity inmateEntity = new ShglInmateEntity();
					inmateEntity.setName(name);
					inmateEntity.setCard_no(cardNo);
					if("男".equals(sex)){
						inmateEntity.setSex("E");
					}else if("女".equals(sex)){
						inmateEntity.setSex("F");
					}else{
						inmateEntity.setSex("");
					}
					if(birthday == null || "".equals(birthday)){
//						inmateEntity.setBirthday(null);
					}else{
						inmateEntity.setBirthday(DateUtils.parseDate(birthday, "yyyy-MM-dd"));
					}
					
					inmateEntity.setTelephone(phone);
					inmateEntity.setNation(nation);
					if (hm_relation.equals("本人")) {
						inmateEntity.setHousemaster("1");
						String aa = andHz.get(hm_relation);
						inmateEntity.setHm_relation(hm_relation);
					} else {
						inmateEntity.setHousemaster("0");
						inmateEntity.setHm_relation(hm_relation);
					}
					inmateEntity.setCommId(commId);
					inmateEntity.setSsId(serviceStationEntity.getId());
					inmateEntity.setBuild(buildingEntity);
					inmateEntity.setUnit_no(unit);
					inmateEntity.setRoom_no(room);
					inmateEntity.setPolitical(political);
					inmateEntity.setMarriage(marriage);
					inmateEntity.setEducation(education);
					inmateEntity.setHouse_register(house_register);
					inmateEntity.setWork_unit(work_unit);
					inmateEntity.setCar_no(car_no);
					jmxxService.save1(inmateEntity);
					
					String[] type1 = type.split(",");
					for (String tString : type1) {
						if ("".equals(tString) || null == tString) {
							continue;
						}
						ShglInmateTypeEntity s = new ShglInmateTypeEntity();
						s.setInmate(inmateEntity);
						s.setCategory(tString);
						jmxxService.saveLx(s);
					}
					errMsg2 += "第" + (i + 2) + "行，导入成功！\r" + System.getProperty("line.separator");
				}catch(Exception e){
				errMsg2 += "第" + (i+2) + "行,数据有误,导入失败！\r" + System.getProperty("line.separator");
				}
				}
				if ("1".equals(flag)) {
					impResult = createErrFile(errMsg2);
				} else {
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
		String exportPath = ProjectUtils.getSysCfg("exportPath");// 导出路径
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

	@RequestMapping("/chooseBuild")
	public String chooseBuild(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String ssId, String commId,
			String gridId, HttpServletRequest req) {
		model.addAttribute("ssId", ssId);
		model.addAttribute("commId", commId);
		model.addAttribute("gridId", gridId);
		try {
			if (null == ajaxCmd) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("gridId", gridId);
				Pagenate<ShglBuildingEntity> pagenate = lyglService.findByPage1(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/jmxx/chooseBuild#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询楼宇信息出错", e);
		}
		return "shgl/jmxx/chooseBuild";
	}

	/**
	 * 权限控制抽取方法
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
