package com.jcwx.action.shzz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.ZxjsEntity;
import com.jcwx.service.shzz.ZxjsService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;


/**
 * @author LiuMengMeng
 *
 */
@Controller
@RequestMapping("/app/shzz/zxjs")
public class AppZxjsAction {
	private Logger log = Logger.getLogger(AppZxjsAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private ZxjsService zxjsService;
	
	@RequestMapping("/index")
	public String index(HttpSession session, Model model,String ajaxCmd,@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTimes){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
	
		try {
			model.addAttribute("title", title);
			model.addAttribute("createTimes",createTimes);
			if(ajaxCmd == null){
			model.addAttribute("pagenate", null);
		}else{
			Map<String, String> cxMap = new HashMap<String, String>();
			cxMap.put("title",title);
			cxMap.put("createTimes", createTimes);
			cxMap.put("appCode", "app");//手机端标识
			Pagenate<ZxjsEntity> pagenate = zxjsService.getZxjsContent(pageNumber, pageSize, cxMap);
			model.addAttribute("pagenate", pagenate);
			model.addAttribute("title",title);
			model.addAttribute("createTimes",createTimes);
			model.addAttribute("pageCnts", pagenate.getPgCnts());
			model.addAttribute("pageSize", pagenate.getPageSize());
			return "shzz/zxjs/app/index_zxjs_app#"+ajaxCmd;
		}
		} catch (Exception e) {
			log.error("查询中心介绍出错!");
		}

			return "shzz/zxjs/app/index_zxjs_app";
	}
	
	
/**
 * 获取更多页
 * @param model
 * @param ajaxCmd
 * @param pageNumber
 * @param title
 * @param createTimes
 * @param request
 * @param response
 * @return
 */
	@ResponseBody
	@RequestMapping("/appGetMorePage")
	public Map appGetMorePage(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title,String content,String createTimes,
			HttpServletRequest request, HttpServletResponse response){
		
		//分页查询
		Map<String, String> cxMap = new HashMap<String, String>();
		cxMap.put("title",title);
		cxMap.put("content", content);
		cxMap.put("createTimes", createTimes);
		Pagenate<ZxjsEntity> pagenate = zxjsService.getZxjsContent(pageNumber,pageSize, cxMap);
		
		List<ZxjsEntity> zxjsList = pagenate.getList();
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<Map> dateList = new ArrayList<Map>();//存放Map
		for (int i = 0; i < zxjsList.size(); i++) {
			Map data = new HashMap();//存放对象
			ZxjsEntity  tempPyry =  zxjsList.get(i);
			data.put("id",tempPyry.getId());
			data.put("title",tempPyry.getTitle());
			data.put("content", tempPyry.getContent());
			data.put("createTimes",tempPyry.getCreateTimes());
	
			dateList.add(data);
		}
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		valMap.put("data", dateList);
		return valMap;
	}
	
	
	/**
	 * 跳转查看详情页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("中心介绍信息ID：" + id);
		try {
			ZxjsEntity zxjs = null;
			if(id != null && !id.equals("")){
				zxjs = zxjsService.getById(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("zxjs", zxjs);
		} catch (Exception e) {
			log.error("查看政务信息详情出错：",e);
		}
		return "shzz/zxjs/app/view_zxjs_app";
	}
	
	
}

