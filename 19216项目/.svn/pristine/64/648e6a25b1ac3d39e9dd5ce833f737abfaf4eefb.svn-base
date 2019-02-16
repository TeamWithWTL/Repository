package com.jcwx.action.shfw.app;

import java.util.ArrayList;
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
import com.jcwx.entity.shfw.ShfwSqfwEntity;
import com.jcwx.service.shfw.SqfwService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author gaoshuai
 * 	2017年11月9日
 * 	App--社区服务
 */
@Controller
@RequestMapping("/app/shfw/sqfw")
public class AppSqfwAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(AppSqfwAction.class);
	
	@Autowired
	private SqfwService sqfwService;
	
	/**
	 * 首页列表展示
	 */
	@RequestMapping("/toSqfw")
	public String toSqfw(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		
		try {
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
				model.addAttribute("title", title);
			}else{
				
				//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
				model.addAttribute("roleCode", sysAccCount.getRole_code());
				model.addAttribute("accCode", sysAccCount.getAccCode());
				
				//分页查询
				String code = "app";
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("app", code);
				Pagenate<ShfwSqfwEntity> pagenate = sqfwService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "/shfw/sqfw/app/app_index_sqfw#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppSqfwAction]_[toSqfw]_查询社区服务列表出错", e);
		}
		return "/shfw/sqfw/app/app_index_sqfw";
	}
	
	/**
	 * mui--获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/sqfwNextContent")
	public Map sqfwNestContent(Model model,Integer pageNumber, String title, HttpSession session){
		
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		
		//分页查询
		String code = "app";
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("app", code);
		Pagenate<ShfwSqfwEntity> pagenate = sqfwService.findByPage(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<ShfwSqfwEntity> sqfwLists = pagenate.getList();
		ShfwSqfwEntity sqfwEntity = new ShfwSqfwEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<sqfwLists.size(); i++){
		Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 sqfwEntity = sqfwLists.get(i);
		 String titleId = sqfwEntity.getId();//标题id
		 String titleName = sqfwEntity.getTitle();//标题名称
		 String createTime = sqfwEntity.getCreateTimeFmt();//创建时间
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
	public String goView(HttpSession session, Model model, String sqfwId) {
		
		log.info("社区服务Id>>>>>>>" + sqfwId);
		
		try {
			ShfwSqfwEntity sqfw = null;
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			if(sqfwId != null && !sqfwId.equals("")){
				sqfw = sqfwService.getById(sqfwId);
			}
			model.addAttribute("sqfw", sqfw);
			model.addAttribute("path", path);
		} catch (Exception e) {
			log.error("查看社区服务详情出错：",e);
		}
		return "shfw/sqfw/app/app_sqfw_view";
	}
}
