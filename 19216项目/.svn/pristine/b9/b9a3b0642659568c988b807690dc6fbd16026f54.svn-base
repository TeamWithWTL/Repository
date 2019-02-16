package com.jcwx.action.pub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.pub.app.AppSysSignInAction;
import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.entity.pub.SysSignInSetEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.service.pub.SysSignInService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author Gs
 * 2018年5月16日
 * App--网格员签到
 * 		1.要求每天签到两次，上午下午各一次（以12点为分割线）
 * 		2.必须在网格员所管理的网格内签到，超出范围不允许签到。
 */
@SuppressWarnings("static-access")
@Controller
@RequestMapping("/shgl/signIn")
public class SysSignInAction {
	
	private Logger log = Logger.getLogger(AppSysSignInAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize")); // 每页最大条数
	
	@Autowired
	private SysSignInService sysSignInService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private WgglService wgglService;
	
	
	/**
	 * 签到记录-- 分页查询签到人
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, String commId, String ssId, String gridId, String months,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpSession session) {
		try {
			// 判断条件
			Map<String, String> pdMap = getMaps();
			// 本月应签到次数
			int rTimes = 0;
			Date sTime = null;
			Date eTime = null;
			
			SysAccCount acc = (SysAccCount) session.getAttribute("sysAccCount");// 获取用户信息
			String roleCode = acc.getRole_code();								// 获取用户角色
			String isAdmin = jurisdiction(roleCode);							// 判断用户权限
			model.addAttribute("roleCode", roleCode);							
			Map<String, String> paramMap = new HashMap<String, String>();
			if ("shequ".equals(isAdmin)||"fuwuzhan".equals(isAdmin)) {
				paramMap.put("dqCommId", acc.getCommId());						//社区下拉列表只显示当前用户负责的社区
			}else if ("admin".equals(isAdmin)) {
				paramMap.put("dqCommId", null);									//社区下拉列表显示所有小区
			}
			List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
			Map<String, String> map = new HashMap<String, String>();
			List<ShglGridEntity> gridEntities = wgglService.findAllSer1(map);
			Map<String, String> gridMap = new HashMap<String, String>();
			if(null != gridEntities){
				for(ShglGridEntity entity : gridEntities){
					gridMap.put(entity.getId(), entity.getName());
				}
			}
			model.addAttribute("gridMap", gridMap);
			model.addAttribute("comList", comList);
			model.addAttribute("months", months);
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> addMap = new HashMap<String, String>();
				if ("shequ".equals(isAdmin)) {
					String dqCommId = acc.getCommId();						// 当前用户负责的社区Id
					addMap.put("dqCommId", dqCommId);
				}
				if ("fuwuzhan".equals(isAdmin)) {
					String dqSsId = acc.getSsId();							// 当前用户负责的服务站Id
					addMap.put("dqSsId", dqSsId);
				}
				if ("admin".equals(isAdmin)) {								// 最高权限查所有
					addMap.put("dqCommId", null);
					addMap.put("dqSsId", null);
				}
				addMap.put("commId", commId);
				addMap.put("ssId", ssId);
				addMap.put("gridId", gridId);
				addMap.put("roleCode", "01");
				Pagenate<SysAccCount> pagenate = yhglService.getGridPeople(pageNumber, pageSize, addMap);
				
				Map<String, Integer> returnMap = new HashMap<String, Integer>();
				// 获取应签到和实际签到
				if(null != months && !"".equals(months)){
					String yearStr = months.split("-")[0];
					String monthStr = months.split("-")[1];
					// 改月开始时间
					Calendar calendar = DateUtils.getMonthFisrt(yearStr, monthStr);
					sTime = calendar.getTime();
					// 改月结束日期
					Calendar calendar1 = DateUtils.getMonthLast(yearStr, monthStr);
					eTime = calendar1.getTime();
					if(null != pagenate){
						for(SysAccCount accCount : pagenate.getList()){
							String accCode = accCount.getAccCode();
							// 应签到次数
							rTimes = Integer.valueOf(pdMap.get(monthStr.replace("0", "")));
							// 本月实际签到次数
							int yTimes = 0;
							List<SysSignInEntity> list = sysSignInService.getCouunt(accCode, sTime, eTime);
							if(!list.isEmpty()){
								for(SysSignInEntity entity : list){
									if(null != entity.getSignTimeAm() && null != entity.getSignTimePm()){
										yTimes = yTimes + 2;
										continue;
									}
									if(null != entity.getSignTimeAm() || null != entity.getSignTimePm()){
										yTimes = yTimes + 1;
										continue;
									}
								}
							}
							returnMap.put(accCode+"ss", yTimes);
							returnMap.put(accCode, rTimes*2);
						}
					}
					model.addAttribute("months", months);
				}else{
					// 本月开始时间
					Calendar calendar = DateUtils.getNowMonthFisrt();
					sTime = calendar.getTime();
					// 改月结束日期
					Calendar calendar1 = DateUtils.getNowMonthLast();
					eTime = calendar1.getTime();
					Calendar calendar2 = calendar.getInstance();
					int mtr = calendar2.get(calendar.MONTH)+1;
					if(null != pagenate){
						for(SysAccCount accCount : pagenate.getList()){
							String accCode = accCount.getAccCode();
							// 应签到次数
							rTimes = Integer.valueOf(pdMap.get(String.valueOf(mtr)));
							// 本月实际签到次数
							int yTimes = 0;
							List<SysSignInEntity> list = sysSignInService.getCouunt(accCode, sTime, eTime);
							if(!list.isEmpty()){
								for(SysSignInEntity entity : list){
									if(null != entity.getSignTimeAm() && null != entity.getSignTimePm()){
										yTimes = yTimes + 2;
										continue;
									}
									if(null != entity.getSignTimeAm() || null != entity.getSignTimePm()){
										yTimes = yTimes + 1;
										continue;
									}
								}
							}
							returnMap.put(accCode+"ss", yTimes);
							returnMap.put(accCode, rTimes*2);
						}
					}
					model.addAttribute("months", DateUtils.formateDate(new Date(), "yyyy-MM"));
				}
				
				
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("returnMap", returnMap);
				return "shgl/signIn_index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[SysSignInAction]_[index]_分页查询签到人出错。", e);	
		}
		return "shgl/signIn_index";
	}
	
	/**
	 * 签到记录详情
	 */
	@RequestMapping("/signInXp")
	public String signInXp(Model model, String ajaxCmd, String months, String accCode,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpSession session) {
		try {
			Map<String, String> map = getMaps();
			// 本月应签到次数
			int rTimes = 0;
			// 本月实际签到次数
			int yTimes = 0;
			Date sTime = null;
			Date eTime = null;
			if(null != months && !"".equals(months)){
				String yearStr = months.split("-")[0];
				String monthStr = months.split("-")[1];
				// 改月开始时间
				Calendar calendar = DateUtils.getMonthFisrt(yearStr, monthStr);
				sTime = calendar.getTime();
				// 改月结束日期
				Calendar calendar1 = DateUtils.getMonthLast(yearStr, monthStr);
				eTime = calendar1.getTime();
				// 改月最大天数
				rTimes = Integer.valueOf(map.get(monthStr.replace("0", "")));
				List<SysSignInEntity> list = sysSignInService.getCouunt(accCode, sTime, eTime);
				if(!list.isEmpty()){
					for(SysSignInEntity entity : list){
						if(null != entity.getSignTimeAm() && null != entity.getSignTimePm()){
							yTimes = yTimes + 2;
							continue;
						}
						if(null != entity.getSignTimeAm() || null != entity.getSignTimePm()){
							yTimes = yTimes + 1;
							continue;
						}
					}
				}
				model.addAttribute("months", months);
			}else{
				// 本月开始时间
				Calendar calendar = DateUtils.getNowMonthFisrt();
				sTime = calendar.getTime();
				// 改月结束日期
				Calendar calendar1 = DateUtils.getNowMonthLast();
				eTime = calendar1.getTime();
//				rTimes = calendar1.get(calendar.DAY_OF_MONTH);
				Calendar calendar2 = calendar.getInstance();
				int mtr = calendar2.get(calendar.MONTH)+1;
				rTimes = Integer.valueOf(map.get(String.valueOf(mtr)));
				// 出去每个月的周六周天
//				ssD = DateUtils.getMonthSundayAndStaturday(null, null);
				List<SysSignInEntity> list = sysSignInService.getCouunt(accCode, sTime, eTime);
				if(!list.isEmpty()){
					for(SysSignInEntity entity : list){
						if(null != entity.getSignTimeAm() && null != entity.getSignTimePm()){
							yTimes = yTimes + 2;
							continue;
						}
						if(null != entity.getSignTimeAm() || null != entity.getSignTimePm()){
							yTimes = yTimes + 1;
							continue;
						}
					}
				}
				model.addAttribute("months", DateUtils.formateDate(new Date(), "yyyy-MM"));
			}
			model.addAttribute("accCode", accCode);
			model.addAttribute("rTimes", rTimes*2);
			model.addAttribute("yTimes", yTimes);
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("accCode", accCode);
				if(null != months && !"".equals(months)){
					paramMap.put("sTime", DateUtils.formateDate(sTime, "yyyy-MM-dd HH:mm:ss"));
					paramMap.put("eTime", DateUtils.formateDate(eTime, "yyyy-MM-dd HH:mm:ss"));
				}else{
					Calendar calendarSt = Calendar.getInstance(java.util.Locale.CHINA);
					calendarSt.set(calendarSt.DAY_OF_MONTH, calendarSt.getActualMinimum(calendarSt.DAY_OF_MONTH));
					Calendar calendarEt = Calendar.getInstance(java.util.Locale.CHINA);
					calendarEt.set(calendarEt.DAY_OF_MONTH, calendarEt.getActualMaximum(calendarEt.DAY_OF_MONTH));
					paramMap.put("sTime", DateUtils.formateDate(calendarSt.getTime(), "yyyy-MM-dd HH:mm:ss"));
					paramMap.put("eTime", DateUtils.formateDate(calendarEt.getTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				paramMap.put("accCode", accCode);
				Pagenate<SysSignInEntity> pagenate = sysSignInService.getSignInList(pageNumber, pageSize, paramMap);
				model.addAttribute("pagenate", pagenate);
				return "shgl/signIn_index_xq#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[SysSignInAction]_[signInXp]_查询签到记录详情出错。", e);	
		}
		return "shgl/signIn_index_xq";
	}
	
	/**
	 * 设置每月的应签到次数
	 * @param model
	 * @return
	 */
	@RequestMapping("/setMonth")
	public String setMonths(Model model){
		// 查询所有的设置每月签到记录的数据
		List<SysSignInSetEntity> entity = sysSignInService.findAllSignMonth();
		SysSignInSetEntity inSetEntity = null;
		if(!entity.isEmpty()){
			inSetEntity = entity.get(0);
		}
		model.addAttribute("cts", inSetEntity);
		return "shgl/set_month_count";
	}
	
	/**
	 * 保存每月的应签到次数
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSaveMonth")
	public String doSaveMonth(SysSignInSetEntity entity, String id){
		try {
			// 保存
			sysSignInService.saveSetMonth(entity, id);
			return "success";
		} catch (Exception e) {
			log.error("[SysSignInAction]_[doSaveMonth]_保存每月应到次数错误", e);
		}
		return "error";
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
	
	
	public Map<String, String> getMaps(){
		Map<String,	String> map = new HashMap<String, String>();
		// 查询所有的设置每月签到记录的数据
		List<SysSignInSetEntity> entitys = sysSignInService.findAllSignMonth();
		if(!entitys.isEmpty()){
			for(SysSignInSetEntity setEntity : entitys){
				map.put("1", setEntity.getOneMonth());
				map.put("2", setEntity.getTwoMonth());
				map.put("3", setEntity.getThreeMonth());
				map.put("4", setEntity.getFourMonth());
				map.put("5", setEntity.getFiveMonth());
				map.put("6", setEntity.getSixMonth());
				map.put("7", setEntity.getSevenMonth());
				map.put("8", setEntity.getEightMonth());
				map.put("9", setEntity.getNineMonth());
				map.put("10", setEntity.getTenMonth());
				map.put("11", setEntity.getElevenMonth());
				map.put("12", setEntity.getTwelveMonth());
			}
		}else{
			map.put("1", "31");
			map.put("2", "28");
			map.put("3", "30");
			map.put("4", "30");
			map.put("5", "31");
			map.put("6", "30");
			map.put("7", "31");
			map.put("8", "31");
			map.put("9", "30");
			map.put("10", "31");
			map.put("11", "30");
			map.put("12", "31");
		}
		return map;
	}
	
}
