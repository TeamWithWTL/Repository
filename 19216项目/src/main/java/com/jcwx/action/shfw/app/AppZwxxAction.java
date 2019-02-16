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
import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.service.shfw.ZwxxService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author gaoshuai		
 * 	2017年11月13日
 * 	App--政务信息
 */
@Controller
@RequestMapping("/app/shfw/zwxx")
public class AppZwxxAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(AppZwxxAction.class);
	
	@Autowired
	private ZwxxService zwxxService;
	
	/**
	 * app--首页列表展示
	 */
	@RequestMapping("/toZwxx")
	public String toZwxx(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		
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
				Pagenate<ShfwZwxxEntity> pagenate = zwxxService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				return "shfw/zwxx/app/app_index_zwxx#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppZwxxAction]_[toZwxx]_查询政务信息列表出错", e);
		}
		return "shfw/zwxx/app/app_index_zwxx";
	}
	
	/**
	 * mui--获取下一页
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/zwxxNextContent")
	public Map zwxxNextContent(Model model,Integer pageNumber, String title, HttpSession session){
		
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		
		//分页查询
		String code = "app";
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("app", code);
		Pagenate<ShfwZwxxEntity> pagenate = zwxxService.findByPage(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<ShfwZwxxEntity> zwxxLists = pagenate.getList();
		ShfwZwxxEntity zwxxEntity = new ShfwZwxxEntity();
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<zwxxLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
			zwxxEntity = zwxxLists.get(i);
		 String titleId = zwxxEntity.getId();//标题id
		 String titleName = zwxxEntity.getTitle();//标题名称
		 String createTime = zwxxEntity.getCreateTimeFmt();//创建时间
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
	public String goView(HttpSession session, String zwxxId, Model model) {
		
		log.info("政务信息ID>>>>>>>"+zwxxId);
		
		try {
			ShfwZwxxEntity zwxx = null;
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			if(zwxxId != null && !zwxxId.equals("")){
				zwxx = zwxxService.getById(zwxxId);
			}
			model.addAttribute("zwxx", zwxx);
			model.addAttribute("path", path);
		} catch (Exception e) {
			log.error("[AppZwxxAction]_[goView]_查看政务信息详情出错：",e);
		}
		return "shfw/zwxx/app/app_zwxx_view";
	}
}
