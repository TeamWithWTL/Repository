package com.jcwx.action.xtbg;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.xtbg.TxlglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 通讯录管理
 * @author LiuMengMeng
 *
 */
@Controller
@RequestMapping("/xtbg/txlgl")
public class TxlglAction {
	
	private Logger log = Logger.getLogger(TxlglAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private TxlglService txlglService;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String name,String duty,String phone,String deptId,HttpSession session){
		//获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("accCode", sysAccCount.getAccCode());
			log.info("deptId====================" + deptId);
			try {
			
			//将查询条件再次返回页面
			model.addAttribute("deptId", deptId);
//			model.addAttribute("deptName", deptName);
			if(ajaxCmd == null){
				model.addAttribute("pagenate",null);
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("duty",duty);
				map.put("phone", phone);
				deptId = deptId == null ? "" : deptId;
				deptId = deptId.equals("root") ? "" : deptId;
				
				map.put("deptId", deptId);
				Pagenate<SysAccCount> pagenate = txlglService.getTxlglContent(pageNumber,pageSize,map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("deptId",deptId);
				return "xtbg/txlgl/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询通讯录管理出错。");
		}
		return "xtbg/txlgl/index";
	}

}
