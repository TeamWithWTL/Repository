package com.jcwx.action.shfw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.service.shfw.JftjService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 社会服务-积分统计 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/shfw/jftj")
public class JftjAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(JftjAction.class);
	
	@Autowired
	private JftjService jftjService;
	@Autowired
	private YhglService yhglService;
	
	/**
	 * 首页列表展示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name	--姓名
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String name, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		String roleCode = sysAccCount.getRole_code();
		
		try {
			model.addAttribute("name", name);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				Pagenate<SysAccCountLazy> pagenate = jftjService.findByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/jftj/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询积分统计列表出错", e);
		}
		return "shfw/jftj/index";
	}
	
	/**
	 * 积分流水列表展示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param accCode --用户ID
	 * @param startDate --开始日期
	 * @param endDate	--结束日期
	 * @param session
	 * @return
	 */
	@RequestMapping("/jflsList")
	public String jflsList(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, 
			String accCode, String startDate, String endDate, HttpSession session){
		try {
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
	        
			model.addAttribute("accCode", accCode);
			model.addAttribute("firstday", firstday);
	        model.addAttribute("lastday", lastday);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("accCode", accCode);
				map.put("startDate", startDate);
				map.put("endDate", endDate);
				Pagenate<ShfwJflsEntity> pagenate = jftjService.getByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/jftj/jflsList#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询积分流水列表出错", e);
		}
		return "shfw/jftj/jflsList";
	}
	
	/**
	 * 跳转积分兑换页面
	 * @param session
	 * @param accCode --用户ID
	 * @param model
	 * @return
	 */
	@RequestMapping("/goJfdh")
	public String goJfdh(HttpSession session, String accCode, Model model) {
		log.info("用户ID：" + accCode);
		try {
			ShfwJflsEntity jf = null;
			
			model.addAttribute("jf", jf);
			model.addAttribute("accCode", accCode);
		} catch (Exception e) {
			log.error("跳转积分兑换页面出错：",e);
		}
		return "shfw/jftj/jfdh";
	}
	
	/**
	 * 添加积分兑换分数
	 * @param jf
	 * @param accCode	--用户ID
	 * @param integral	--积分
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(ShfwJflsEntity jf, String accCode, String integral, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			SysAccCountLazy acc = yhglService.findByCode(accCode);
			Integer zjf = acc.getIntegral();
			int integral1 = Integer.parseInt(integral);
			if(zjf >= integral1){
				//新增
				jf.setCreateUserId(sysAccCount.getAccCode());
				jf.setCreateUserName(sysAccCount.getName());
				jftjService.save(jf);
				Integer syjf = zjf - integral1;
				acc.setIntegral(syjf);
				yhglService.updateAccJf(accCode);
				return "success";
			}else{
				return "jfbz";
			}
		} catch (Exception e) {
			log.error("保存积分兑换出错：",e);
		}
		return "fail";
	}
	
}
