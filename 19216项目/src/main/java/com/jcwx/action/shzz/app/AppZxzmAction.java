package com.jcwx.action.shzz.app;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.ZxzmAttrsEntity;
import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.service.shzz.ZxzmService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 在线招募-App
 * @author wangjinxiao
 *
 */
@Controller
@RequestMapping("/app/shzz/zxzm")
public class AppZxzmAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(AppZxzmAction.class);
		
	@Autowired
	private ZxzmService zxzmService;
	
	/**
	 * 跳转到在线招募信息首页
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toZxzm")
	public String ShzzxxContent(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String title,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCode = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCode);
		
		try{
		  if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("code", "app");//区分手机端还是pc端
				Pagenate<ZxzmEntity> pagenate = zxzmService.findByPage(pageNumber, pageSize, map);
				for(ZxzmEntity zxzmEntity : pagenate.getList()){
					List<ZxzmAttrsEntity> alist = zxzmEntity.getAttrList();
					if(!alist.isEmpty()){
						for(ZxzmAttrsEntity attrsEntity : alist){
							if("img".equals(attrsEntity.getFileType().toLowerCase())){
								zxzmEntity.setYsPice(attrsEntity.getNewFilename());
								break;
							}
						}
					}
				}
				model.addAttribute("title", title);
				model.addAttribute("pagenate", pagenate);
				return "shzz/zxzm/app/app_index_zxzm#" + ajaxCmd;
			}
		}catch(Exception e){
			log.error("App端------查询在线招募出错", e);
		}
		return "shzz/zxzm/app/app_index_zxzm";
	}
	
	/**
	 * mui--获取下一页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/zxzmNextContent")
	public Map<Object, Object> sqfwNestContent(Model model,Integer pageNumber, String title, HttpSession session){
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("code", "app");//区分手机端还是pc端
		Pagenate<ZxzmEntity> pagenate = zxzmService.findByPage(pageNumber, pageSize, map);
		
		List<ZxzmEntity> zxzmLists = pagenate.getList();
		if(zxzmLists.isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		ZxzmEntity zxzmEntity = new ZxzmEntity();
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();//存放Map
		for(int i = 0; i<zxzmLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
		 zxzmEntity = zxzmLists.get(i);
		 String titleId = zxzmEntity.getId();//标题id
		 String titleName = zxzmEntity.getTitle();//标题名称
		 String createTime = zxzmEntity.getXs_create_time();//创建时间
		 addMap.put("titleId", titleId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 addList.add(addMap);
		}
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 查看在线招募详情
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("在线招募ID：" + id);
		try {
			ZxzmEntity zxzm = null;
			if(id != null && !id.equals("")){
				zxzm = zxzmService.getById(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("zxzm", zxzm);
		} catch (Exception e) {
			log.error("App端--查看在线招募详情出错：",e);
		}
		return "shzz/zxzm/app/app_zxzm_view";
	}
	
}
