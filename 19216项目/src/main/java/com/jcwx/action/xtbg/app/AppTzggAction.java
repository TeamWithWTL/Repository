package com.jcwx.action.xtbg.app;

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

import com.jcwx.action.shzz.app.AppDtbbAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.entity.xtbg.TzggArrtEntity;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.service.xtbg.TzggRyService;
import com.jcwx.service.xtbg.TzggService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 通知公告-App
 * @author wangjinxiao
 *
 */
@Controller
@RequestMapping("/app/xtbg/tzgg")
public class AppTzggAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppTzggAction.class);
	
	@Autowired
	private TzggRyService tzggRyService;
	@Autowired
	private TzggService tzggService;
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
		
		try{
			if(ajaxCmd==null){
				model.addAttribute("pagenate",null);
			}else{
				Pagenate<TzggEntity> pagenate = new Pagenate<TzggEntity>();
				List<TzggRyEntity> tzggIds = tzggRyService.findByAccCode(sysAccCount.getAccCode());//根据接收人的id查询通知公告的id
				if(!tzggIds.isEmpty()){
					String[] tzggIDs = new String[tzggIds.size()];
					for(int i=0; i<tzggIds.size(); i++){
						tzggIDs[i] = tzggIds.get(i).getTzgg_id();
					}
					Map<String, Object> cxMap = new HashMap<String, Object>();
					cxMap.put("title", title);
					cxMap.put("tzggIDs", tzggIDs);
					if(tzggIDs.length>0){
						pagenate = tzggRyService.getTzggContent(pageNumber, pageSize, cxMap);
						for(TzggEntity tzggEntity : pagenate.getList()){
							List<TzggArrtEntity> aList = tzggEntity.getAttrList();
							if(!aList.isEmpty()){
								for(TzggArrtEntity arrtEntity : aList){
									if("img".equals(arrtEntity.getFileType().toLowerCase())){
										tzggEntity.setYsPice(arrtEntity.getNew_filename());
										break;
									}
								}
							}
						}
					}
				}
				model.addAttribute("title",title);
				model.addAttribute("pagenate", pagenate);
				return "xtbg/tzgg/app/app_index_tzgg#" + ajaxCmd;
			}
		}catch (Exception e) {
			log.error("App端--------------查询通知公告出错！", e);
		}
		
		return "xtbg/tzgg/app/app_index_tzgg";
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
	@RequestMapping("/tzggNextContent")
	public Map<Object, Object> sqfwNestContent(Model model,Integer pageNumber, String title, HttpSession session){
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		List<TzggRyEntity> tzggIds = tzggRyService.findByAccCode(sysAccCount.getAccCode());
		String[] tzggIDs = new String[tzggIds.size()];
		for(int i=0; i<tzggIds.size(); i++){
			tzggIDs[i] = tzggIds.get(i).getTzgg_id();
		}
		Map<String, Object> cxMap = new HashMap<String, Object>();
		cxMap.put("title", title);
		cxMap.put("tzggIDs", tzggIDs);
		Pagenate<TzggEntity> pagenate = tzggRyService.getTzggContent(pageNumber, pageSize, cxMap);

		List<TzggEntity> tzggLists = pagenate.getList();
		if(tzggLists.isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();//存放Map
		for(int i = 0; i<tzggLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 String titleId = tzggLists.get(i).getId();//标题id
		 String titleName = tzggLists.get(i).getTitle();//标题名称
		 String createTime = tzggLists.get(i).getCreateDates();//创建时间
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
		log.info("通知公告信息ID：" + id);
		try {
			TzggEntity tzgg = null;
			if(id != null && !id.equals("")){
				tzgg = tzggService.findById(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("tzgg", tzgg);
		} catch (Exception e) {
			log.info("App端----查看通知公告详情出错：",e);
		}
		return "xtbg/tzgg/app/app_tzgg_view";
	}
}	
