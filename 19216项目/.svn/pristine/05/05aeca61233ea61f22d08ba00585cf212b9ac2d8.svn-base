package com.jcwx.action.shfw.app;

import java.util.ArrayList;
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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.service.shfw.SqhdService;
import com.jcwx.service.shfw.YjlyService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author gaoshuai 2017年11月13日 App--社区活动
 */
@Controller
@RequestMapping("/app/shfw/sqhd")
public class AppSqhdAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize")); // 每页最大条数
	private Logger log = Logger.getLogger(AppSqhdAction.class);

	@Autowired
	private SqhdService sqhdService;
	@Autowired
	private YjlyService yjlyService;

	/**
	 * app--首页展示
	 */
	@RequestMapping("/toSqhd")
	public String toSqhd(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, HttpSession session) {
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("title", title);
			} else {

				// 获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
				model.addAttribute("roleCode", sysAccCount.getRole_code());
				model.addAttribute("accCode", sysAccCount.getAccCode());

				// 分页查询
				String code = "app";
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("app", code);
				Pagenate<ShfwSqhdEntity> pagenate = sqhdService.findByPage(pageNumber, pageSize, map);

				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());

				return "shfw/sqhd/app/app_index_sqhd#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppSqhdAction]_[toSqhd]_查询社区活动列表出错", e);
		}
		return "shfw/sqhd/app/app_index_sqhd";
	}

	/**
	 * mui--获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/sqhdNextContent")
	public Map sqhdNextContent(Model model, Integer pageNumber, String title, HttpSession session) {

		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());

		// 分页查询
		String code = "app";
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("app", code);
		Pagenate<ShfwSqhdEntity> pagenate = sqhdService.findByPage(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());

		List<ShfwSqhdEntity> sqhdLists = pagenate.getList();
		ShfwSqhdEntity sqhdEntity = new ShfwSqhdEntity();
		List<Map> addList = new ArrayList<Map>();// 存放Map
		for (int i = 0; i < sqhdLists.size(); i++) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			sqhdEntity = sqhdLists.get(i);
			String titleId = sqhdEntity.getId();// 标题id
			String titleName = sqhdEntity.getTitle();// 标题名称
			String createTime = sqhdEntity.getCreateDateFmt();// 创建时间
			addMap.put("titleId", titleId);
			addMap.put("titleName", titleName);
			addMap.put("createTime", createTime);
			addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}

	/**
	 * 跳转查看页面
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, Model model, String sqhdId) {

		log.info("社区活动ID>>>>>>>" + sqhdId);

		try {
			// 获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");

			ShfwSqhdEntity sqhd = null;
			ShfwSqhdYjEntity yj = null;
			ShfwSqhdYjEntity yj2 = null;
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			if (sqhdId != null && !sqhdId.equals("")) {
				sqhd = sqhdService.getById(sqhdId);
				yj = yjlyService.getSqhdYjByWfk(sqhdId, sysAccCount.getAccCode());
				yj2 = yjlyService.getSqhdYjByYfk(sqhdId, sysAccCount.getAccCode());
			}
			model.addAttribute("sqhd", sqhd);
			model.addAttribute("yj", yj);
			model.addAttribute("yj2", yj2);
			model.addAttribute("path", path);
		} catch (Exception e) {
			log.error("[AppSqhdAction]_[goView]_查看社区活动详情出错：", e);
		}
		return "shfw/sqhd/app/app_sqhd_view";
	}

	/**
	 * mui--留言详情
	 */
	@RequestMapping("/initSqhdView")
	public String initSqhdView(HttpSession session, Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String sqhdId) {

		log.info("社区活动ID>>>>>>>" + sqhdId);

		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				String code = "app";
				String path = ProjectUtils.getSysCfg("appDownloadPath");
				Map<String, String> map = new HashMap<String, String>();
				map.put("sqhdId", sqhdId);
				map.put("app", code);
				Pagenate<ShfwSqhdYjEntity> pagenate = yjlyService.findByPage(pageNumber, pageSize, map);

				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				model.addAttribute("path", path);
				return "shfw/sqhd/app/app_sqhd_view#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppSqhdAction]_[goView]_查看社区活动详情出错：", e);
		}
		return "shfw/sqhd/app/app_sqhd_view";
	}

	/**
	 * mui--获取留言下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/sqhdNextXq")
	public Map sqhdNextXq(Model model, Integer pageNumber, String sqhdId, HttpSession session) {

		// 获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());

		// 分页查询
		String code = "app";
		Map<String, String> map = new HashMap<String, String>();
		map.put("sqhdId", sqhdId);
		map.put("app", code);
		Pagenate<ShfwSqhdYjEntity> pagenate = yjlyService.findByPage(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());

		List<ShfwSqhdYjEntity> sqhdXqLists = pagenate.getList();
		ShfwSqhdYjEntity sqhdXqEntity = new ShfwSqhdYjEntity();
		List<Map> addList = new ArrayList<Map>();// 存放Map
		for (int i = 0; i < sqhdXqLists.size(); i++) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			sqhdXqEntity = sqhdXqLists.get(i);
			String content = sqhdXqEntity.getContent();// 留言内容
			String userName = sqhdXqEntity.getUserName();// 发布人姓名
			String createTime = sqhdXqEntity.getCreateTimeFmt();// 创建时间
			String userPic = sqhdXqEntity.getUserPic();// 发布人头像
			addMap.put("content", content);
			addMap.put("userName", userName);
			addMap.put("createTime", createTime);
			addMap.put("userPic", userPic);
			addList.add(addMap);
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		returnMap.put("result", addList);
		return returnMap;
	}

	/**
	 * 社区活动报名
	 */
	@RequestMapping("/saveSignup")
	@ResponseBody
	public String saveSignup(HttpSession session, String id) {

		log.info("社区活动ID>>>>>>>" + id);

		try {
			// 获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			ShfwSqhdYjEntity sqhdYjEntity = new ShfwSqhdYjEntity();
			sqhdYjEntity.setSqhdId(id);
			sqhdYjEntity.setCreateTime(new Date());
			sqhdYjEntity.setUserId(sysAccCount.getAccCode());
			sqhdYjEntity.setUserName(sysAccCount.getName());
			sqhdYjEntity.setShStatus("0"); // 未审核
			sqhdYjEntity.setIsSignup("1"); // 需报名
			sqhdYjEntity.setIsBack("2"); // 未反馈
			sqhdYjEntity.setScore_type("0"); // 未设置积分
			yjlyService.saveLy(sqhdYjEntity);
			return "success";
		} catch (Exception e) {
			log.error("[AppSqhdAction]_[saveSignup]_App端---保存社区活动留言出错：", e);
		}
		return "error";
	}

	/**
	 * 保存留言
	 */
	@RequestMapping("/doSaveLy")
	@ResponseBody
	public String doSaveLy(HttpSession session, String id, String content) {

		log.info("社区活动ID>>>>>>>" + id);

		try {
			// 获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
			// 查询当前登录用户是否对当前社区活动已留言
			ShfwSqhdYjEntity sqhdYjEntity = yjlyService.getSqhdYjBySqhdId(id, sysAccCount.getAccCode());
			ShfwSqhdYjEntity yjly = yjlyService.getSqhdYjByWfk(id, sysAccCount.getAccCode());
			ShfwSqhdYjEntity yjly2 = yjlyService.getSqhdYjByYfk(id, sysAccCount.getAccCode());
			if (null == sqhdYjEntity && null == yjly && null == yjly2) {
				sqhdYjEntity = new ShfwSqhdYjEntity();
				sqhdYjEntity.setContent(content);
				sqhdYjEntity.setSqhdId(id);
				sqhdYjEntity.setCreateTime(new Date());
				sqhdYjEntity.setUserId(sysAccCount.getAccCode());
				sqhdYjEntity.setUserName(sysAccCount.getName());
				sqhdYjEntity.setShStatus("0"); // 未审核
				sqhdYjEntity.setIsBack("1"); // 已反馈
				sqhdYjEntity.setIsSignup("2"); //无需报名
				sqhdYjEntity.setScore_type("0"); // 未设置积分
				sqhdYjEntity.setIntegral(0); // 设置积分 默认0
				yjlyService.saveLy(sqhdYjEntity);
				return "{\"result\":\"success\",\"pid\":\"" + sqhdYjEntity.getId() + "\"}";
			} else if (null != yjly && !"1".equals(yjly.getIsBack())) {
				yjly.setIsBack("1");
				yjly.setContent(content);
				yjly.setIntegral(0); // 设置积分 默认0
				yjlyService.updateLy(yjly, yjly.getId());
				return "{\"result\":\"success\",\"pid\":\"" + yjly.getId() + "\"}";
			} else if(null != yjly2){
				return "{\"result\":\"yfk\"}";
			}else{
				return "{\"result\":\"yfk\"}";
				
			}
		} catch (Exception e) {
			log.error("[AppSqhdAction]_[doSaveLy]_App端---保存社区活动留言出错：", e);
		}
		return "{\"result\":\"error\"}";
	}
}
