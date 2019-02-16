package com.jcwx.action.dflz.app;

import java.util.ArrayList;
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

import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.AppDzywService;
import com.jcwx.service.dflz.DzywService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * app党风廉政
 * @author 李伟
 * @time 2017年11月8日下午4:04:34
 */
@Controller
@RequestMapping("/app/dflz/appDzyw")
public class AppDzywAction {
	
	private Logger log = Logger.getLogger(AppDzywAction.class);//log4j
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	
	@Autowired
	private AppDzywService appDzywService;
	@Autowired
	private DzywService dzywService;
	/**
	 * 跳转查询廉政要闻
	 * @author 李伟
	 * @time 2017年11月10日下午3:18:00
	 * @return
	 */
	@RequestMapping("/toDzyw")
	public String toDzyw(String ajaxCmd,Model model,String title,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)req.getSession().getAttribute("sysAccCount");
				String accCode = sysAccCount.getAccCode();
				String roleCode = sysAccCount.getRole_code();
				model.addAttribute("roleCode", roleCode);
				model.addAttribute("accCode", accCode);
			
				try {
					model.addAttribute("title", title);
					if(ajaxCmd == null){
						model.addAttribute("pagenate", null);
					}else{
						//分页查询
						//String code = "app";
						Map<String, String> map = new HashMap<String, String>();
						map.put("title", title);
						//map.put("app", code);
						Pagenate<DzywEntity> pagenate = appDzywService.findByPage(pageNumber, pageSize, map);
						
						model.addAttribute("pagenate", pagenate);
						model.addAttribute("pageCnts", pagenate.getPgCnts());
						model.addAttribute("pageSize", pagenate.getPageSize());
					
						return "/dflz/dzyw/app/appIndex#" + ajaxCmd;
					}
				} catch (Exception e) {
					log.error("[AppDzywAction]_[toDzyw]_查询党政要闻列表出错", e);
				}
		
		return "dflz/dzyw/app/appIndex";
	}
	
	/**
	 * 查询下一页已审核要闻 
	 * @author 李伟
	 * @time 2017年11月8日下午5:15:22
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dzywNextContent")
	public Map dzywNestContent(Model model,String title,Integer pageNumber,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
			//	String code = "app";
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				//map.put("app", code);
				Pagenate<DzywEntity> pagenate = appDzywService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				List<DzywEntity> sqfwLists = pagenate.getList();
				DzywEntity dzywEntity = new DzywEntity();
				
				List<Map> addList = new ArrayList<Map>();//存放Map
				for(int i = 0; i<sqfwLists.size(); i++){
					Map<String, String> addMap = new HashMap<String,String>();//存放对象
				dzywEntity = sqfwLists.get(i);
				 String titleId = dzywEntity.getId();//标题id
				 String titleName = dzywEntity.getTitle();//标题名称
				 String createTime = dzywEntity.getFmtCreate_time();//创建时间
				 String ysPice = dzywEntity.getYsPice();
				 addMap.put("titleId", titleId);
				 addMap.put("titleName", titleName);
				 addMap.put("createTime", createTime);
				 addMap.put("ysPice", ysPice);
				 
				 addList.add(addMap);
				}
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				returnMap.put("result", addList);
				return returnMap;
	}
	/**
	 * 跳转详情页面
	 * @author 李伟
	 * @time 2017年11月8日下午5:14:49
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model,String id){
		DzywEntity dzywEntity = null;
		try {
			if (id!=null&&!"".equals(id)) {
				 dzywEntity = dzywService.findById(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path",path);
			model.addAttribute("detailEntity",dzywEntity);
		} catch (Exception e) {
			log.error("AppDzywAction类 goView方法 查询单条详情要闻出错  ",e);
		}
	
		return "/dflz/dzyw/app/detail";
	}
}
