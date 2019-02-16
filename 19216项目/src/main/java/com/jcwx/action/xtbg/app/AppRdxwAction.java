package com.jcwx.action.xtbg.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.xtbg.RdxwAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * 热点新闻-App
 * @author wangjinxiao
 *
 */
@Controller
@RequestMapping("/app/xtbg/rdxw")
public class AppRdxwAction {
	
	private Logger log = Logger.getLogger(RdxwAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private RdxwService rdxwService;
	
	/**
	 * 列表显示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		
		try{
			if(ajaxCmd==null){
				model.addAttribute("pagenate",null);
			}else{
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("title", title);
				cxMap.put("code", "app");//区分手机端还是pc端
				Pagenate<RdxwEntity> pagenate = rdxwService.getRdxwContent(pageNumber, pageSize, cxMap);
				for(RdxwEntity rdxwEntity : pagenate.getList()){
					List<RdxwArrtsEntity> atList = rdxwEntity.getAttrList();
					if(!atList.isEmpty()){
						for(RdxwArrtsEntity taList : atList){
							if("img".equals(taList.getFileType().toLowerCase())){
								rdxwEntity.setYsPice(taList.getNewFileName());
								break;
							}
						}
					}
				}
				model.addAttribute("title",title);
				model.addAttribute("pagenate", pagenate);
				return "xtbg/rdxw/app/app_index_rdxw#" + ajaxCmd;
			}
		}catch (Exception e) {
			log.error("App端--------------查询热点新闻出错！", e);
		}
		
		return "xtbg/rdxw/app/app_index_rdxw";
}
	
	
	/**
	 * mui--获取下一页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rdxwNextContent")
	public Map<Object, Object> sqfwNestContent(Model model,Integer pageNumber, String title, HttpSession session){
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		//分页查询
		Map<String, String> cxMap = new HashMap<String, String>();
		cxMap.put("title", title);
		cxMap.put("code", "app");//区分手机端还是pc端
		Pagenate<RdxwEntity> pagenate = rdxwService.getRdxwContent(pageNumber, pageSize, cxMap);

		List<RdxwEntity> rdxwLists = pagenate.getList();
		if(rdxwLists.isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		RdxwEntity edxwEntity = new RdxwEntity();
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();//存放Map
		for(int i = 0; i<rdxwLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
			edxwEntity = rdxwLists.get(i);
		 String titleId = edxwEntity.getId();//标题id
		 String titleName = edxwEntity.getTitle();//标题名称
		 String createTime = edxwEntity.getCreateDateFrm();//创建时间
		 addMap.put("titleId", titleId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 addList.add(addMap);
		}
		returnMap.put("result", addList);
		return returnMap;
	}

	/**
	 * 跳转查看内容详情页
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("热点新闻ID：" + id);
		try {
			RdxwEntity rdxw = null;
			if(id != null && !id.equals("")){
				rdxw = rdxwService.getById(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("rdxw", rdxw);
		} catch (Exception e) {
			log.info("App端---查看热点新闻详情出错：",e);
		}
		return "xtbg/rdxw/app/app_rdxw_view";
	}
	
	
	//新增保存
	@RequestMapping(value = "/doAdd", produces = "text/html;charset=UTF-8;")
	@ResponseBody
	public String bbzksave(String title, HttpSession session) throws InterruptedException {
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			RdxwEntity rdxw = new RdxwEntity();
			rdxw.setTitle(title);
			rdxw.setUserId(sysAccCount.getAccCode());
			rdxw.setUserName(sysAccCount.getName());
			rdxwService.save(rdxw, null);
			return "{\"result\":\"succ\",\"pid\":\"" + rdxw.getId() + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"result\":\"error\"}";
	}
}
