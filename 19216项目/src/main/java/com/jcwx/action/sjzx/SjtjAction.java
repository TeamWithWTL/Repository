package com.jcwx.action.sjzx;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.SjglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.sjzx.SjtjService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author xushu
 *	2017年10月27日
 *	事件统计
 */
@Controller
@RequestMapping("/sjzx/sjtj")
public class SjtjAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(SjtjAction.class);
	
	@Autowired
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SjtjService sjtjService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private SjglService sjglService;
	
	/**
	 * 区域事件统计首页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param startTime  开始时间  
	 * @param endTime    结束时间
	 * @param commId     社区
	 * @param ssId       服务站
	 * @param gridId     网格
	 * @return
	 */
	@RequestMapping("/index")
	public String indexSjtj(Model model, String ajaxCmd,String startTime,String endTime,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			String roles [] = acc.getRole_code().split(",");
			String roleCode = "";
			for(String role : roles){
				//街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = acc.getRole_code();
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("comList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
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
	        model.addAttribute("commId", commId);
			model.addAttribute("ssId", ssId);
			if (ajaxCmd == null) {
				model.addAttribute("firstday", firstday);
		        model.addAttribute("lastday", lastday);
				model.addAttribute("pagenate", null);
			}else{
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				
			}
		}catch(Exception e) {
			log.error("查询区域事件统计信息出错", e);
		}
		return "sjzx/sjtj/index";
	}
	
	@ResponseBody
	@RequestMapping("/initCharts")
	public Map<Object, Object> initCharts(Model model,
			String startTime,String endTime,String commId, String ssId, HttpServletRequest req){
		//获取当前登陆的角色
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roles [] = acc.getRole_code().split(",");
		String roleCode = "";
		for(String role : roles){
			//街道管理员,这几个角色进来，和街道管理员拥有相同的权限
			if(role.equals("04") || role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
				roleCode = "05";
				break;
			}else if(role.equals("03")){  //社区管理员
				roleCode = "03";
				break;
			}else if(role.equals("02")){  //服务站管理员
				roleCode = "02";
				break;
			}else{
			    roleCode = acc.getRole_code();
			}
		}
		//查询事件类型
		List<SysParamDesc> sjlx = sjzdService.findByCode("10008").getSysParamDesc();//事件分类
		// 查询条件
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("startTime", startTime);
		paramsMap.put("endTime", endTime);
		if(roleCode.equals("05") || roleCode.equals("99")){
			paramsMap.put("commId", commId);
			paramsMap.put("ssId",   ssId);
		}else if(roleCode.equals("03")){
			paramsMap.put("commId", acc.getCommId());
			paramsMap.put("ssId",   ssId);
		}else{
			paramsMap.put("commId", acc.getCommId());
			paramsMap.put("ssId",   acc.getSsId());
		}
		//按条件查询事件统计的数据
//		Pagenate<Event> pagenate = sjtjService.findByPage(1, pageSize, paramsMap);
		List<Event> pagenate = sjtjService.findEvent(paramsMap);
		//柱状图
		Map<Object, Object> mapT = new HashMap<Object, Object>();
		String [] listAllLX = new String [sjlx.size()];
		int [] listAllSL = new int [sjlx.size()];
		for(int i=0;i<sjlx.size();i++){
			  String code = sjlx.get(i).getItemCode(); //事件类型编码
		      listAllLX[i] = sjlx.get(i).getItemName();  //事件类型名称
		      List<String> listAllSj = new ArrayList<String>();
		      for(int j=0;j<pagenate.size();j++){
		    	  List<String> listSj = new ArrayList<String>();
		    	  if(code.equals(pagenate.get(j).getType())){
		    		  listSj.add(pagenate.get(j).getId());
		    	  }
		    	  listAllSj.addAll(listSj);
		      }
		      listAllSL[i] = listAllSj.size();
		}
		mapT.put("listAllLX", listAllLX);
		mapT.put("listAllSL", listAllSL);
		return mapT;
	}
	
	/**
	 * 事件状态统计
	 * @param model
	 * @param ajaxCmd
	 * @param startTime
	 * @param endTime
	 * @param commId
	 * @param ssId 
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/sjzttjindex")
	public String sjzttjindex(Model model, String ajaxCmd,String startTime,String endTime,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			String roles [] = acc.getRole_code().split(",");
			String roleCode = "";
			for(String role : roles){
				//街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = acc.getRole_code();
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("comList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
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
	        model.addAttribute("commId", commId);
			model.addAttribute("ssId", ssId);
			if (ajaxCmd == null) {
				model.addAttribute("firstday", firstday);
		        model.addAttribute("lastday", lastday);
		        model.addAttribute("cmd", "table1");
				model.addAttribute("pagenate", null);
			}else{
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("startTime", startTime);  //开始时间
				map.put("endTime", endTime);      //结束时间
				if(roleCode.equals("05") || roleCode.equals("99")){   //街道管理员，超级管理员
					map.put("commId", commId);           //社区id
					map.put("ssId", ssId);               //服务站id
				}else if(roleCode.equals("03")){                        //社区管理员
					map.put("commId", acc.getCommId());  //当前登陆者所在的社区id
					map.put("ssId", ssId);               //服务站id 
				}else if(roleCode.equals("02")){                        //服务站管理员
					map.put("commId", acc.getCommId());  //当前登陆者所在的社区id
					map.put("ssId", acc.getSsId());      //当前登陆者所在的服务站id 
				}
				if(ajaxCmd.equals("table1")){
					map.put("isOver", "2");  //未处理
				}else if(ajaxCmd.equals("table2")){
					map.put("isOver", "1");  //已处理
				}
				Pagenate<Event> pagenate = sjtjService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("cmd", ajaxCmd);
				return "sjzx/sjtj/sjzttjindex#" + ajaxCmd;
			}
		}catch(Exception e) {
			log.error("查询事件状态统计信息出错", e);
		}
		return "sjzx/sjtj/sjzttjindex";
	}
	
	/**
	 * 事件来源统计
	 * @param model
	 * @param ajaxCmd
	 * @param commId
	 * @param ssId
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/sjlytjindex")
	public String sjlytjindex(Model model, String ajaxCmd,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			//当前登陆者的角色id
			//多角色取登陆者最高权限的角色
			String roleCodes = acc.getRole_code();
			String roles [] = roleCodes.split(",");
			String roleCode = "";
			for(String role : roles){
				 //街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = roleCodes;
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("sqList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
			List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织分类
			model.addAttribute("zzlx", zzlx);
		}catch(Exception e) {
			log.error("事件来源统计信息出错", e);
		}
		return "sjzx/sjtj/sjlytjindex";
	}
	
	/**
	 * 事件完成率统计
	 * @param model
	 * @param ajaxCmd
	 * @param commId
	 * @param ssId
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/sjwzltjindex")
	public String sjwzltjindex(Model model, String ajaxCmd,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			String roles [] = acc.getRole_code().split(",");
			String roleCode = "";
			for(String role : roles){
				//街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = acc.getRole_code();
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("comList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
		}catch(Exception e) {
			log.error("查询事件完成率统计信息出错", e);
		}
		return "sjzx/sjtj/sjwcltjindex";
	}
	
	/**
	 * 事件完成率
	 * @param model
	 * @param startTime
	 * @param endTime
	 * @param commId
	 * @param ssId
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initChartswcl")
	public Map<Object, Object> initChartswcl(Model model,
			String commId, String ssId, HttpServletRequest req){
		//获取当前登陆的角色
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roles [] = acc.getRole_code().split(",");
		String roleCode = "";
		for(String role : roles){
			//街道管理员,这几个角色进来，和街道管理员拥有相同的权限
			if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
				roleCode = "05";
				break;
			}else if(role.equals("03")){  //社区管理员
				roleCode = "03";
				break;
			}else if(role.equals("02")){  //服务站管理员
				roleCode = "02";
				break;
			}else{
			    roleCode = acc.getRole_code();
			}
		}
		Map<Object, Object> mapT = new HashMap<Object, Object>();
		// 查询条件
		Map<String, String> paramsMap = new HashMap<String, String>();
		if(roleCode.equals("05") || roleCode.equals("99")){
			paramsMap.put("commId", commId);
			paramsMap.put("ssId",   ssId);
		}else if(roleCode.equals("03")){
			paramsMap.put("commId", acc.getCommId());
			paramsMap.put("ssId",   ssId);
		}else{
			paramsMap.put("commId", acc.getCommId());
			paramsMap.put("ssId",   acc.getSsId());
		}
		//按条件查询事件统计的数据
	   Pagenate<Event> pagenate = sjtjService.findByPage(1, pageSize, paramsMap);
	   int zs = pagenate.getRsCnts();   //总数
	   paramsMap.put("isOver",  "1");   //已结束的事件
	   Pagenate<Event> ywc = sjtjService.findByPage(1, pageSize, paramsMap);
	   int ycsj = ywc.getRsCnts();
	   // 创建一个数值格式化对象  
       NumberFormat numberFormat = NumberFormat.getInstance();  
       // 设置精确到小数点后2位  
       numberFormat.setMaximumFractionDigits(2);  
       //完成率
       String wcl ="0";
       if(ycsj != 0){
    	   wcl  =  numberFormat.format((float) ycsj / (float) zs * 100);
       }
	   mapT.put("wcl", wcl);
	   return mapT;
	}
}