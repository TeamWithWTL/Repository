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

import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.AppExposureService;
import com.jcwx.service.dflz.ExposureService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

@Controller
@RequestMapping("/app/dflz/appExpo")
public class AppExposureAction {
	private Logger log = Logger.getLogger(AppDzywAction.class);//log4j
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	
	@Autowired
	private AppExposureService appExposureService;
	@Autowired
	private ExposureService exposureService;
	
	/**
	 * 跳转查询爆光台
	 * @author 李伟
	 * @time 2017年11月10日下午3:19:46
	 * @return
	 */
	@RequestMapping("/toExpo")
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
						Pagenate<ExposureEntity> pagenate = appExposureService.findByPage(pageNumber, pageSize, map);
						model.addAttribute("pagenate", pagenate);
						model.addAttribute("pageCnts", pagenate.getPgCnts());
						model.addAttribute("pageSize", pagenate.getPageSize());
						return "/dflz/bggl/app/appIndex#" + ajaxCmd;
					}
				} catch (Exception e) {
					log.error("[AppExposureAction]_[toExpo]_查询曝光台列表出错", e);
				}
		
		return "dflz/bggl/app/appIndex";
	}
	/**
	 * 已审核曝光信息查询下一页
	 * @author 李伟
	 * @time 2017年11月8日下午6:04:29
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @param model
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/expoNextContent")
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
				Pagenate<ExposureEntity> pagenate = appExposureService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				List<ExposureEntity> sqfwLists = pagenate.getList();
				ExposureEntity exposureEntity = new ExposureEntity();
				
				List<Map> addList = new ArrayList<Map>();//存放Map
				for(int i = 0; i<sqfwLists.size(); i++){
					Map<String, String> addMap = new HashMap<String,String>();//存放对象
					exposureEntity = sqfwLists.get(i);
				 String titleId = exposureEntity.getId();//标题id
				 String titleName = exposureEntity.getTitle();//标题名称
				 String createTime = exposureEntity.getFmtCreate_time();//创建时间
				 addMap.put("titleId", titleId);
				 addMap.put("titleName", titleName);
				 addMap.put("createTime", createTime);
				 addList.add(addMap);
				}
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				returnMap.put("result", addList);
				return returnMap;
	}
	/**
	 * 查询详情
	 * @author 李伟
	 * @time 2017年11月8日下午6:08:38
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model,String id){
		try {
			if (id !=null &&!"".equals(id)) {
				ExposureEntity exposureEntity = exposureService.findById(id);
				model.addAttribute("detailEntity",exposureEntity);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path",path);
		} catch (Exception e) {
			log.error("AppExposureAtion类 goView方法 查询详情信息出错",e);
		}
		return "/dflz/bggl/app/detail";
	}
}
