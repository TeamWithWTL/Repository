package com.jcwx.action.shzz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.HdglService;
import com.jcwx.service.shzz.HdtjService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 社会组织-活动统计 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/shzz/hdtj")
public class HdtjAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(HdtjAction.class);
	
	@Autowired
	private HdtjService hdtjService;
	@Autowired
	private HdglService hdglService;
	
	/**
	 * 组织活动统计列表
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,
				String name, String startTime, String endTime, HttpSession session){
			//获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("accCode", sysAccCount.getAccCode());
			// 获取当月第一天和最后一天  
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			String firstday, lastday; 
	        Calendar cale = null;  
	        // 获取前月的第一天  
	        cale = Calendar.getInstance();  
	        cale.add(Calendar.MONTH, 0);  
	        cale.set(Calendar.DAY_OF_MONTH, 1);  
	        firstday = format.format(cale.getTime());  
	        // 获取前月的最后一天  
	        cale = Calendar.getInstance();  
	        cale.add(Calendar.MONTH, 1);  
	        cale.set(Calendar.DAY_OF_MONTH, 0);  
	        lastday = format.format(cale.getTime());  
			try {
				model.addAttribute("name", name);
				model.addAttribute("firstday", firstday);
		        model.addAttribute("lastday", lastday);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("name",name);
				Pagenate<ZzxxEntity> pagenate = hdtjService.findZzByPage(pageNumber, pageSize, paramsMap);
				for(ZzxxEntity zz : pagenate.getList()){
					String zzId = zz.getId();
					Map<String, String> map = new HashMap<String, String>();
					map.put("zzId",zzId);
					map.put("startTime", startTime);  //开始时间
					map.put("endTime", endTime);      //结束时间
					List<HdglFkEntity> fkList = hdtjService.getFkCnt(map);
					zz.setSl(fkList.size());
				}
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdtj/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织活动统计列表出错：",e);
		}
		return "shzz/hdtj/index";
	}
	
	/**
	 * 查看活动反馈详情
	 * @param model
	 * @param zzId
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param session
	 * @return
	 */
	@RequestMapping("/goHdfkView")
	public String goHdfkView(Model model, String zzId,String ajaxCmd,
				@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,HttpSession session){
			try {
				if(ajaxCmd == null){
				model.addAttribute("zzId", zzId);
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("zzId",zzId);
				Pagenate<HdglFkEntity> pagenate = hdtjService.findFk2ByPage(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdtj/hdfk_view#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织活动反馈出错：",e);
		}
		return "shzz/hdtj/hdfk_view";
	}
	
	/**
	 * 获取反馈内容详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		HdglFkEntity fk = null;
		if (id!=null && !"".equals(id)) {
			fk = hdglService.findFkByFkId(id);
		}
		model.addAttribute("fk",fk);
		return  "shzz/hdtj/view";
	}
	
	/**
	 * 组织活动统计列表
	 * @param model
	 * @param session
	 * @return
	 */
	/*@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTime,HttpSession session){
			//获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("accCode", sysAccCount.getAccCode());
			try {
				model.addAttribute("title", title);
				model.addAttribute("createTime",createTime);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("title",title);
				paramsMap.put("createTime", createTime);
				Pagenate<HdglEntity> pagenate = hdtjService.findByPage(pageNumber, pageSize, paramsMap);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdtj/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织活动统计列表出错：",e);
		}
		return "shzz/hdtj/index";
	}*/
	
	/**
	 * 跳转组织反馈详情页
	 * @param model
	 * @param hdId
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param session
	 * @return
	 */
	/*@RequestMapping("/goZzfkView")
	public String goZzfkView(Model model, String hdId,String ajaxCmd,
				@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,HttpSession session){
			try {
				if(ajaxCmd == null){
				model.addAttribute("hdId", hdId);
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("hdglId",hdId);
				Pagenate<HdglFkEntity> pagenate = hdtjService.findFkByPage(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdtj/zzfk_view#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织活动反馈出错：",e);
		}
		return "shzz/hdtj/zzfk_view";
	}*/
	
}