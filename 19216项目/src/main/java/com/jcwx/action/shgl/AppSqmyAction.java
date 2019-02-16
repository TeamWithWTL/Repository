package com.jcwx.action.shgl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqmyService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 社情民意App
 */
@Controller
@RequestMapping("/app/shgl/sqmy")
public class AppSqmyAction {
	
	private Logger log = Logger.getLogger(AppSqmyAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	
	
	@Autowired
	private SqmyService sqmyService;
	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private LyglService lyglService;
	
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String id,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode(); 
		try{
			  if(ajaxCmd == null){
					model.addAttribute("wgylist", null);
					
				}else{
					//分页查询
					Map<String, String> map = new HashMap<String, String>();
					map.put("wgyId", accCode);
					map.put("status", "2");//已发布的
					map.put("is_over", "0");//查询未结束的社情民意
//					Pagenate<ShglSqmyWgy> pagenate = sqmyService.findSqmyWgyByPage(pageNumber, pageSize, map);
					List<ShglSqmyWgy> wgylist = sqmyService.findSqmyWgyList(map);
					model.addAttribute("wgylist",wgylist);
					return "shgl/sqmy/app/app_sqmy_index#" + ajaxCmd;
				}
			}catch(Exception e){
				log.error("查询社情民意出错", e);
			}
			return "shgl/sqmy/app/app_sqmy_index";
	}

	
	/**
	 * 获取更多页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param content
	 * @param createTimes
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appGetMorePage")
	public Map appGetMorePage(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title,String XSstart_date,String XSend_date,
			HttpServletRequest request, HttpServletResponse response,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode(); 
		
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("wgyId", accCode);
		map.put("status", "2");//已发布的
		map.put("is_over", "0");//查询未结束的社情民意
		Pagenate<ShglSqmyWgy> pagenate = sqmyService.findSqmyWgyByPage(pageNumber, pageSize, map);
		
		List<ShglSqmyWgy> sqmyList = pagenate.getList();
		List<Map> dateList = new ArrayList<Map>();//存放Map
		for (int i = 0; i < sqmyList.size(); i++) {
			Map data = new HashMap();//存放对象
			ShglSqmyWgy  wgyInfo =  sqmyList.get(i);
			data.put("id", wgyInfo.getSqmyInfo().getId());
			data.put("title", wgyInfo.getSqmyInfo().getTitle());	//标题
			data.put("XSstart_date", wgyInfo.getSqmyInfo().getXSstart_date());	//开始时间
			data.put("XSend_date", wgyInfo.getSqmyInfo().getXSend_date());	//结束时间
			data.put("dcCount",wgyInfo.getDcCount());
			data.put("wcl",wgyInfo.getWcl());
			data.put("see",wgyInfo.getIsSee()=="2"?"未查看":"");
			dateList.add(data);
		}
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		valMap.put("data", dateList);
		return valMap;
	}
	
	/**
	 *  查询待办社情民意数量
	 */
	@ResponseBody
	@RequestMapping("/needSee")
	public Map<String, String> needSee( HttpSession session,HttpServletRequest req,Model model) {
		Map<String,String> hMap =  new HashMap<String, String>();
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode(); 
		
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("wgyId", accCode);
		map.put("status", "2");//已发布的
		map.put("is_over", "0");//查询未结束的社情民意
		map.put("isSee", "2");
		Pagenate<ShglSqmyWgy> pagenate = sqmyService.findSqmyWgyByPage(1, pageSize, map);
		int count = pagenate.getRsCnts();
		hMap.put("count", String.valueOf(count));
		
		return hMap;
	}

	/**
	 * 网格员调查列表
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param wgyInfoId
	 * @param session
	 * @return
	 */
	@RequestMapping("/wgyDcList")
	public String wgyDcList(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String sqmyWgyId ,HttpSession session){
		
		if(sqmyWgyId == null ||"".equals(sqmyWgyId)){
			model.addAttribute("dcList", null);
			return "shgl/sqmy/app/app_sqmy_dcList#" + ajaxCmd;
		}else{
			ShglSqmyWgy wgy = sqmyService.findById(ShglSqmyWgy.class, sqmyWgyId);
			wgy.setIsSee("1");
			sqmyService.saveorupdate(wgy);
		}
		try{
			  if(ajaxCmd == null){
					model.addAttribute("dcList", null);
					model.addAttribute("sqmyWgyId",sqmyWgyId);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put("sqmyWgyId", sqmyWgyId);
					List<ShglSqmyDc> dcList = sqmyService.findAllDc(map);
					model.addAttribute("dcList",dcList);
					model.addAttribute("sqmyWgyId",sqmyWgyId);
					
					return "shgl/sqmy/app/app_sqmy_dcList#" + ajaxCmd;
				}
			}catch(Exception e){
				log.error("查询网格员调查列表出错", e);
			}
			return "shgl/sqmy/app/app_sqmy_dcList";
	}
	

	
	/**
	 * 添加住户
	 * 小区--楼宇--单元--室号
	 */
	@RequestMapping("/addDcPage")
	public String addDcPage(Model model,String sqmyWgyId) {
		model.addAttribute("sqmyWgyId",sqmyWgyId);
		return "shgl/sqmy/app/app_sqmy_addDc";
}
	

	@RequestMapping("/saveDcInfo")
	@ResponseBody
	public String saveDcInfo( HttpSession session, String sqmyWgyId,String title,String content){
		try {
		ShglSqmyWgy wgyInfo = sqmyService.findById(ShglSqmyWgy.class,sqmyWgyId);
		if(sqmyWgyId == null ||"".equals(sqmyWgyId)){
			return "{\"result\":\"fail\"}"; 
		}
			ShglSqmyDc dcInfo  = new ShglSqmyDc();
			dcInfo.setSqmyWgyId(sqmyWgyId);
			dcInfo.setTitle(title);
			dcInfo.setContent(content);
			sqmyService.save(dcInfo);
			//更新网格员调查信息
			int allCount = wgyInfo.getSqmyInfo().getDc_num();
			int curCount = wgyInfo.getDcCount()+1;
			//  创建一个数值格式化对象  
	        NumberFormat numberFormat = NumberFormat.getInstance();  
	        // 设置精确到小数点后2位  
	        numberFormat.setMaximumFractionDigits(2);  
	        //完成率
	        String wcl = numberFormat.format((float) curCount / (float) allCount * 100);
	        if(curCount>=allCount){
	        	wcl ="100";
	        }
	        wgyInfo.setDcCount(curCount);
	        wgyInfo.setWcl(wcl);
	        sqmyService.saveorupdate(wgyInfo);
			return "{\"result\":\"success\",\"pid\":\"" + dcInfo.getId() + "\"}";
			} catch (Exception e) {
				log.error("[AppSqmyAction]_[saveDcInfo]_出错", e);
			}
		return "{\"result\":\"fail\"}";
	}
	
	/**
	 * 跳转到社情民意详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/gosqmyxq")
	public String gosqmyxq(Model model,String id,String wgyid,HttpSession session){
		log.info("社情民意ID：" + id);
		try {
			ShglSqmyEntity sqmy = null;
			if(id != null && !"".equals(wgyid)){
				sqmy = sqmyService.getById(id);

			}
			if(wgyid != null && !"".equals(wgyid)){
				ShglSqmyWgy wgy = sqmyService.findById(ShglSqmyWgy.class, wgyid);
				wgy.setIsSee("1");
				sqmyService.saveorupdate(wgy);
//				//修改是否已查看状态
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("wgyId", accCode);
//				map.put("status", "2");
//				map.put("is_over", "0");
//				map.put("isSee", "2");
//				List<ShglSqmyWgy>  wgyList = sqmyService.findSqmyWgyList(map);
//				for (int i = 0; i < wgyList.size(); i++) {
//					ShglSqmyWgy  wgy = wgyList.get(i);
//					wgy.setIsSee("1");
//					sqmyService.saveorupdate(wgy);
//				}
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("sqmy", sqmy);
		} catch (Exception e) {
			log.error("跳转社情民意详情页面出错：",e);
		}
		return "shgl/sqmy/app/app_sqmy_view";
	}
	

	/**
	 * 跳转到社情民意详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/dcView")
	public String dcView(Model model , String dcId,HttpSession session){
		try {
			ShglSqmyDc dcInfo = null;
			if(dcId != null && !"".equals(dcId)){
				dcInfo = sqmyService.findById(ShglSqmyDc.class, dcId);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("dcInfo", dcInfo);
		} catch (Exception e) {
			log.error("[AppSqmyAction]_[dcView]：",e);
		}
		return "shgl/sqmy/app/app_sqmy_dcview";
	}
	
}