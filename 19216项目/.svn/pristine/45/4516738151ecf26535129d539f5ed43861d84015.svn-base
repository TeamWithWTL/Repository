package com.jcwx.action.sjzx;

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
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.service.shgl.EventService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 数据中心--事件完成率统计
 *
 */
@Controller
@RequestMapping("/sjzx/sjwcl")
public class SjwclAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(SjwclAction.class);
	
	@Autowired
	private EventService eventService;
	
	
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTimes,HttpSession session){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
				String accCode = sysAccCount.getAccCode();
				String roleCode = sysAccCount.getRole_code();
				model.addAttribute("roleCode", roleCode);
			try {
//				model.addAttribute("title", title);
//				model.addAttribute("createTimes",createTimes);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				
				Map<String, String> map = new HashMap<String, String>();
//				cxMap.put("title",title);
//				cxMap.put("createTimes", createTimes);
				map.put("roleCode", roleCode);
				Pagenate<EventDeal> pagenate = eventService.findEventDealByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sjgl/sjgl_myevent#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询时间完成率出错。");
		}
			return "shgl/sjgl/sjgl_myevent";
	}

	
	
}
