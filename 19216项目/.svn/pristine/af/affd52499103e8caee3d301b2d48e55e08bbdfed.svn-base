package com.jcwx.action.pub.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.service.pub.LoginService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.service.xtbg.DocumentService;
import com.jcwx.service.xtbg.MeetingService;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

/**
 * @author gaoshuai_2017年11月8日_app--首页
 */
@Controller
@RequestMapping("/app/home")
public class AppHomeAction {
	
	private Logger log = Logger.getLogger(AppHomeAction.class);

	@Autowired
	private DocumentService docService;
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private RdxwService rdxwService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private XqxxService xqxxService;
	
	
	/**
	 * app--首页跳转
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpSession session, Model model, HttpServletRequest req){
		try {
			
			/*****app--消息提醒*****/
			String gw = "0";
			String hy = "0";
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			String accCode = acc.getAccCode();
			//一键拨打电话 (只有普通用户显示)--2107.12.5 xushu
			String roleCode = acc.getRole_code();
			model.addAttribute("roleCode", roleCode);
			SysParam sp = sjzdService.findByCode("10024");
			String phone = sp.getValue1();
			model.addAttribute("phone", phone);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("clrId", accCode);
			Pagenate<DocumentDeal> pagenate = docService.findDocumentDealByPage(1, -1, map);
			if(pagenate.getList().size()>0){
				gw = "1";
			}
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("accCode", accCode);
			map1.put("nowTime", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
			Pagenate<MeetingEntity> pagenate1 = meetingService.findByPage(1, -1, map1);
			if(pagenate1.getList().size()>0){
				hy = "1";
			}
			model.addAttribute("gw", gw);
			model.addAttribute("hy", hy);
			/***************************************app-首页-热点新闻-轮播-start*****************************************/
			Map<String, String> addMap = new HashMap<String, String>();
			addMap.clear();
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
							String ysPice = name +"_1"+ suffix;
							rde.setYsPice(ysPice);
							break;
						}
					}
				}
				List<RdxwEntity> listsSy = new ArrayList<RdxwEntity>();
				if (rdxwList.size() > 3) {
					listsSy = rdxwList.subList(0, 3);
					rdxwList = listsSy;
				}
				model.addAttribute("rdxwList", rdxwList);
			} else {
				model.addAttribute("rdxwList", null);
			}
			
			//查询所有已通过审核并且不是热点的新闻
			addMap.put("noRd", "noRd");//标识
			List<RdxwEntity> nordxwList = rdxwService.getRdxwContentList(addMap);
			
			if (null != nordxwList && nordxwList.size() != 0) {
				
				for (int j = 0; j < nordxwList.size(); j++) {
					RdxwEntity rdeNO = nordxwList.get(j);
					List<RdxwArrtsEntity> attrsNO = rdeNO.getAttrList();
					for (int i = 0; i < attrsNO.size(); i++) {// 获取图片附件的第一张图片
						RdxwArrtsEntity attr = attrsNO.get(i);
						if ("img".equals(attr.getFileType())) {
							int dot = attr.getNewFileName().lastIndexOf(".");
							dot = dot >= 0 ? dot : 0;
							String suffix = attr.getNewFileName().substring(dot);
							String name = attr.getNewFileName().substring(0, dot);
							String ysPice = name + "_1" + suffix;
							rdeNO.setYsPice(ysPice);
							break;
						}
					}
				}
				List<RdxwEntity> listsNO = new ArrayList<RdxwEntity>();
				if (nordxwList.size() > 3) {
					listsNO = nordxwList.subList(0, 3);
					nordxwList = listsNO;
				}
				model.addAttribute("nordxwList", nordxwList);
			} else {
				model.addAttribute("nordxwList", null);
			}
		} catch (Exception e) {
			log.error("[AppHomeAction]_[index]_首页信息加载出错",e);
		}
		return "apppub/home";
	}
	
	/**
	 * 基础信息跳转
	 */
	@RequestMapping("/jcxx")
	public String goJcxx(Model model, HttpServletRequest req){
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roles = acc.getRole_code();
		if(null != roles && !"".equals(roles)){
			model.addAttribute("roles", roles);
		}else {
			model.addAttribute("roles", null);
		}
		
		//判断用户权限
		String isAdmin = jurisdiction(roles);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> addMap = new HashMap<String, String>();
		if ("shequ".equals(isAdmin)) {
			String dqCommId = acc.getCommId();//当前用户负责的社区Id
			map.put("dqCommId", dqCommId);
		}
		if ("fuwuzhan".equals(isAdmin)) {
			String dqSsId = acc.getSsId();//当前用户负责的服务站Id
			map.put("dqSsId", dqSsId);
		}
		if ("wgy".equals(isAdmin)) {
			String dqgridId = acc.getGridId();//当前用户负责的网格Id
			map.put("dqgridId", dqgridId);
		}
		if ("admin".equals(isAdmin)) {//最高权限查所有
			map.put("dqCommId", null);
			map.put("dqSsId", null);
		}
		// 小区特殊条件--开始
		if ("shequ".equals(isAdmin)) {
			String dqCommId = acc.getCommId();// 当前用户负责的社区Id
			addMap.put("dqCommId", dqCommId);
		}
		// App--网格、小区同级如何处理，暂时查看所有的小区（高帅-2017年12月19日）
		if ("fuwuzhan".equals(isAdmin) || "wgy".equals(isAdmin)) {
			String dqSsId = acc.getSsId();// 当前用户负责的服务站Id
			addMap.put("dqSsId", dqSsId);
		}
		if ("admin".equals(isAdmin)) {// 最高权限查所有
			addMap.put("dqCommId", null);
			addMap.put("dqSsId", null);
		}
		// 小区特殊条件--结束
		
		// 权限范围内的社区数
		int sqCount = 0;
		Pagenate<ShglCommunityEntity> pagenateSq = sqglService.findByPage1(-1, -1, map);
		if(!pagenateSq.getList().isEmpty()){
			sqCount = pagenateSq.getList().size();
		}
		// 权限范围内的服务站数
		int fwzCount = 0;
		Pagenate<ShglServiceStationEntity> pagenateFwz = fwzglService.findByPage1(-1, -1, map);
		if(!pagenateFwz.getList().isEmpty()){
			fwzCount = pagenateFwz.getList().size();
		}
		// 权限范围内的网格数
		int wgCount = 0;
		Pagenate<ShglGridEntity> pagenateWg = wgglService.findByPage1(-1, -1, map);
		if(!pagenateWg.getList().isEmpty()){
			wgCount = pagenateWg.getList().size();
		}
		// 权限范围内的小区数
		int xqCount = 0;
		Pagenate<ShglVillageEntity> pagenateXq = xqxxService.findByPage1(-1, -1, addMap);
		if(!pagenateXq.getList().isEmpty()){
			xqCount = pagenateXq.getList().size();
		}
		// 权限范围内的楼宇数
		int lyCount = 0;
		Pagenate<ShglBuildingEntity> pagenateLy = lyglService.findByPage1(-1, -1, map);
		if(!pagenateLy.getList().isEmpty()){
			lyCount = pagenateLy.getList().size();
		}
		// 权限范围内的居民数
		int jmCount = 0;
		Pagenate<ShglInmateEntity> pagenateJm = jmxxService.findByPage1(-1, -1, map);
		if(!pagenateJm.getList().isEmpty()){
			jmCount = pagenateJm.getList().size();
		}
		model.addAttribute("sqCount", sqCount);
		model.addAttribute("fwzCount", fwzCount);
		model.addAttribute("wgCount", wgCount);
		model.addAttribute("xqCount", xqCount);
		model.addAttribute("lyCount", lyCount);
		model.addAttribute("jmCount", jmCount);
		
		return "/apppub/app_jcxx_index";
	}
	
	/**
	 * 任务管理跳转
	 */
	@RequestMapping("/torwgllb")
	public String rwgllbs(Model model, HttpServletRequest req){
		
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roles = acc.getRole_code();
		if(null != roles && !"".equals(roles)){
			model.addAttribute("roles", roles);
		}else {
			model.addAttribute("roles", null);
		}
		return "shgl/rwgl/app/app_rwgl_index";
	}
	
	/**
	 * 跳转到App-个人中心
	 * @return
	 */
	@RequestMapping("/toUserInfo")
	public String toUserInfo(Model model, HttpSession session) {
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		SysAccCountLazy accCount2 = yhglService.findByCode(accCount.getAccCode());
		int jfCount = accCount.getIntegral();
		model.addAttribute("jfCount", jfCount);
		model.addAttribute("accCount", accCount2);
		return "apppub/app_user";
	}
	
	/**
	 * 跳转到App-个人中心-修改密码
	 * @return
	 */
	@RequestMapping("/toUpdataPwd")
	public String toUpdataPwd() {
		return "apppub/app_updata_pwd";
	}
	
	/**
	 * 跳转到App-个人中心-服务热线展示
	 * @return
	 */
	@RequestMapping("/toUpdataXx")
	public String toUpdataXx(Model model, HttpSession session) {
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String accCode = accCount.getAccCode();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		
		Map<String, String> addMap = new HashMap<String,String>();
		addMap.put("accCode", accCode);
		SysAccMore accMore = loginService.findSysMore(addMap);//归属信息
		if(accMore == null){
			model.addAttribute("accMore", null);
			model.addAttribute("sqLists", null);
			model.addAttribute("fwzLists", null);
			model.addAttribute("wgLists", null);
		}else{
			List<ShglCmanagerEntity> sqLists = null;
			List<ShglSmanagerEntity> fwzLists = null;
			List<ShglGridManagerEntity> wgLists = null;
			//社区负责人
			String commId = accMore.getCommId();
			if(null != commId || !"".equals(commId)){
				sqLists = rwglService.findSqryById(commId);
			}
			//服务站负责人
			String ssId = accMore.getSsId();
			if(null != commId || !"".equals(commId)){
				fwzLists = fwzglService.findFwzManager(ssId);
			}
			//网格负责人
			String gridId = accMore.getGridId();
			if(null != commId || !"".equals(commId)){
				wgLists = wgglService.findGridManager(gridId);
			}
			
			model.addAttribute("accMore", accMore);
			model.addAttribute("sqLists", sqLists);
			model.addAttribute("fwzLists", fwzLists);
			model.addAttribute("wgLists", wgLists);
		}
		model.addAttribute("comList", comList);
		
		return "apppub/app_updata_Xx";
	}
	
	/**
	 * 保存居民归属信息
	 */
	@ResponseBody
	@RequestMapping("/doSaveXx")
	public String doSaveXx(String commId, String ssId, String gridId, HttpSession session){
		
		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
		String accCode = accCount.getAccCode();
		
		Map<String, String> addMap = new HashMap<String,String>();
		addMap.put("accCode", accCode);
		SysAccMore more1 = loginService.findSysMore(addMap);//归属信息
		
		SysAccMore accMore = new SysAccMore();
		if(more1 != null){
			more1.setCommId(commId);
			if(ssId != null && !"".equals(ssId)){
				more1.setSsId(ssId);
			}else{
				ssId = "null";
				more1.setSsId(ssId);
			}
			if(gridId != null && !"".equals(gridId)){
				more1.setGridId(gridId);
			}else{
				gridId = "null";
				more1.setGridId(gridId);
			}
			loginService.updataSysM(more1);
			return "upSuc";
		}
		
		accMore.setAccCode(accCode);
		accMore.setCommId(commId);
		accMore.setSsId(ssId);
		accMore.setGridId(gridId);
		loginService.saveSysM(accMore);
		return "success";
	}
	
	/**
	 * 执行App-个人中心-修改密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savePwd")
	public String savePwd(HttpSession session, HttpServletRequest req) {

		try {
			String oldpwd = req.getParameter("oldpwd");// 原密码
			String newpwd = req.getParameter("newpwd");// 新密码
			String truepwd = req.getParameter("truepwd");// 确认密码

			log.info("oldpwd>>>>>>>" + oldpwd);
			log.info("newpwd>>>>>>>" + newpwd);
			log.info("newpwd>>>>>>>" + newpwd);

			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			String pass = sysAccCount.getPwd();// 登录人的密码
			String oldpwdNews = StringUtil.toMD5(oldpwd).toUpperCase();
			String newpwdNews = StringUtil.toMD5(newpwd).toUpperCase();
			
			if (!pass.equals(oldpwdNews)) {// 原密码不相同
				return "oldpwdno";
			}
			if (pass.equals(newpwdNews)) {// 新密码与原密码相同
				return "newpwdno";
			}
			if (!newpwd.equals(truepwd)) {// 新密码与原密码输入不一致
				return "truepwdno";
			}
			
			String acccode = sysAccCount.getAccCode();// 登录人的账户
			SysAccCountLazy countLazy = yhglService.findByCode(acccode);
			String p2 = StringUtil.toMD5(truepwd).toUpperCase();// 修改密码页面---新密码
			countLazy.setPwd(p2);
			yhglService.updateAcc(countLazy);
		} catch (Exception e) {
			log.error("[AppLoginAction]_[savePwd]_保存密码错误", e);
		}
		return "succ";
	}
	
	/**
	 * 保存新头像
	 */
	@RequestMapping("/doUpdatePices")
	@ResponseBody
	public Object doUpdatePic(HttpSession session,String picePath, HttpServletRequest request) {
		try {
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			String acccode = sysAccCount.getAccCode();// 登录人的账户
//			SysAccCountLazy a = yhglService.findByCode(acccode);
//			a.setPic_path(picePath);
//			yhglService.updateAcc(a);
			return "{\"result\":\"success\",\"pid\":\""+acccode+"\"}";
		} catch (Exception e) {
			log.info("上传头像出错", e);
		}
		return "{\"result\":\"fail\"}";
	}
	
	/**
	 * 权限控制抽取方法
	 * @author 李伟
	 * @time 2017年12月12日下午3:41:49
	 * @param roleCode
	 */
	public String jurisdiction(String roleCode) {
		
		//用户角色，保存list
		ArrayList<String> arrayList=new ArrayList<String>();
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
				}else if(Consts.ROLE_FWZGLY.equals(arrayList.get(arrayList.size()-1))){
						isAdmin="fuwuzhan";//服务站管理员
				}else{
					if (Consts.ROLE_WGY.equals(arrayList.get(arrayList.size()-1))) {
						isAdmin="wgy";//网格员
					}
				}
			}
		}
		return isAdmin;
	}
}
