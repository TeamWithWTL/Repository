package com.jcwx.action.sjzx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.jcwx.service.sjzx.HouseholdSurveyService;
import com.jcwx.utils.ProjectUtils;
/**
 * 入户调查Action
 * @author 李伟
 * @time 2017年11月4日上午8:46:33
 */
@Controller
@RequestMapping("/sjzx/rhdc")
public class HouseholdSurveyAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(HouseholdSurveyAction.class);
	
	@Autowired
	HouseholdSurveyService householdSurveyService;
	/**
	 * 跳转主页
	 * @author 李伟
	 * @time 2017年11月4日下午1:12:11
	 * @param model
	 * @param ajaxCmd
	 * @param startTime
	 * @param endTime
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String indexSjtj(Model model, String ajaxCmd,String startTime,String endTime,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			model.addAttribute("roleCode", acc.getRole_code());
			
			/*Map<String, String> paramMap1 = new HashMap<String, String>();*/
			/*List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap1);// 社区列表
			model.addAttribute("comList", comList);*/
		
		/*	List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("ssList", ssList);*/
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
	      //  model.addAttribute("commId", commId);
			//model.addAttribute("ssId", ssId);
			if (ajaxCmd == null) {
				model.addAttribute("firstday", firstday);
		        model.addAttribute("lastday", lastday);
				model.addAttribute("pagenate", null);
			}else{
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				
			}
		}catch(Exception e) {
			log.info("查询事件统计信息出错", e);
		}
		return "sjzx/rhdc/index";
	}
	
	/**
	 * 加载图表数据
	 * @author 李伟
	 * @time 2017年11月4日下午1:11:49
	 * @param model
	 * @param startTime
	 * @param endTime
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initCharts")
	public Map<Object, Object> initCharts(Model model,
			String startTime,String endTime, HttpServletRequest req){
//		// 查询条件
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("startTime", startTime);
//		paramsMap.put("endTime", endTime);
//		//按条件查询社情民意总量的数据
//		Pagenate<ShglSqmyEntity> pagenate=householdSurveyService.findByPage(1, pageSize, paramsMap);
//		//pagenate.getList().get(0).getSqmyzh().get(0).getWgy_id();
//		//柱状图
//		Map<Object, Object> mapT = new HashMap<Object, Object>();//存放任务名，和任务完成量
//		String [] listAllLX = new String [pagenate.getList().size()];//以社情民意的任务总量来设置数组长度，存放社情民意title名
//		int [] listAllSL = new int [pagenate.getList().size()];//同上，存放对应每个社情民意的任务完成量
//		for(int i=0;i<pagenate.getList().size();i++){//以社情民意任务的数据总数为循环
//			String title = pagenate.getList().get(i).getTitle();
//			if (title.length()>4) {
//				title=title.substring(0,4);
//			}
//			  listAllLX[i] = title;  //社情民意任务title
//		      listAllSL[i]=pagenate.getList().get(i).getSqmyzh().size();//社情民意下的任务总量，也就是完成量
//		}
//		mapT.put("listAllLX", listAllLX);
//		mapT.put("listAllSL", listAllSL);
//		return mapT;
		return null;
	}
}
