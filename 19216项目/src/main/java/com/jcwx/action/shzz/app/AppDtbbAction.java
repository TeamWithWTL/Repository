package com.jcwx.action.shzz.app;

import java.util.ArrayList;
import java.util.Date;
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
import com.jcwx.entity.shzz.DtbbAttrEntity;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.service.shzz.DtbbService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 动态播报-App
 * @author wangjinxiao
 *
 */
@Controller
@RequestMapping("/app/shzz/dtbb")
public class AppDtbbAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(AppDtbbAction.class);
	
	

	@Autowired
	private DtbbService dtbbService;
	
	/**
	 * 跳转到动态播报首页
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping("/toDtbb")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber, String title, HttpSession session){
			//获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("accCode", sysAccCount.getAccCode());
			try {
				if(ajaxCmd == null){
					model.addAttribute("pagenate", null);
				}else{
					//分页查询
					Map<String, String> Map = new HashMap<String, String>();
					Map.put("title", title);
					Map.put("code", "app");//区分手机端还是pc端
					Pagenate<DtbbEntity> pagenate = dtbbService.getDtbbContent(pageNumber, pageSize, Map);
					for(DtbbEntity dtbbEntity : pagenate.getList()){
						List<DtbbAttrEntity> aList = dtbbEntity.getAttrList();
						if(!aList.isEmpty()){
							for(DtbbAttrEntity attrEntity : aList){
								if("img".equals(attrEntity.getFileType().toLowerCase())){
									dtbbEntity.setYsPice(attrEntity.getNew_filename());
									break;
								}
							}
						}
					}
					model.addAttribute("title", title);
					model.addAttribute("pagenate", pagenate);
					return "shzz/dtbb/app/app_index_dtbb#"+ajaxCmd;
					}
				} catch (Exception e) {
					log.error("App端----------查询动态播报出错。", e);
				}
		return "shzz/dtbb/app/app_index_dtbb";
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
	@RequestMapping("/dtbbNextContent")
	public Map<Object, Object> sqfwNestContent(Model model,Integer pageNumber, String title, HttpSession session){
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		//分页查询
		Map<String, String> Map = new HashMap<String, String>();
		Map.put("title", title);
		Map.put("code", "app");//区分手机端还是pc端
		Pagenate<DtbbEntity> pagenate = dtbbService.getDtbbContent(pageNumber, pageSize, Map);

		List<DtbbEntity> dtbbLists = pagenate.getList();
		if(dtbbLists.isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		DtbbEntity dtbbEntity = new DtbbEntity();
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();//存放Map
		for(int i = 0; i<dtbbLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
			dtbbEntity = dtbbLists.get(i);
		 String titleId = dtbbEntity.getId();//标题id
		 String titleName = dtbbEntity.getTitle();//标题名称
		 String createTime = dtbbEntity.getCreateTimes();//创建时间
		 addMap.put("titleId", titleId);
		 addMap.put("titleName", titleName);
		 addMap.put("createTime", createTime);
		 addList.add(addMap);
		}
		returnMap.put("result", addList);
		return returnMap;
	}
	
	/**
	 * 跳转查看详情页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("动态播报信息ID：" + id);
		try {
			DtbbEntity dtbb = null;
			List<DtbbYjEntity> dtbblyList = new ArrayList<DtbbYjEntity>();
			if(id != null && !id.equals("")){
				dtbb = dtbbService.findById(id);
				dtbblyList = dtbbService.findByDtId(id);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path", path);
			model.addAttribute("dtbb", dtbb);
			model.addAttribute("dtbbly", dtbblyList);
		} catch (Exception e) {
			log.info("App端---查看动态播报详情出错：",e);
		}
		return "shzz/dtbb/app/app_dtbb_view";
	}
	
	/**
	 * 保存留言
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/doSaveLy")
	@ResponseBody
	public String doSaveLy(HttpSession session, String id, String content) {
		log.info("动态播报信息ID：" + id);
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		try {
			DtbbYjEntity dtbbYjEntity = new DtbbYjEntity();
			dtbbYjEntity.setContent(content);
			dtbbYjEntity.setDtbb_id(id);
			dtbbYjEntity.setCreateTime(new Date());
			dtbbYjEntity.setUserId(sysAccCount.getAccCode());
			dtbbYjEntity.setUserName(sysAccCount.getName());
			dtbbYjEntity.setStatus("0");
			dtbbService.saveLy(dtbbYjEntity);
			return "success";
		} catch (Exception e) {
			log.info("App端---保存动态播报留言出错：",e);
		}
		return "error";
	}
	
	/**
	 * mui--留言详情
	 * @param session
	 * @param id
	 * @param sqhdId--社区活动ID
	 * @return
	 */
	@RequestMapping("/initDtbbLyView")
	public String initDtbbLyView(HttpSession session, Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String dtbbId) {
		log.info("动态播报ID：" + dtbbId);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("dtbbId", dtbbId);
				map.put("code", "app");
				Pagenate<DtbbYjEntity> pagenate = dtbbService.getByDtId(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/dtbb/app/app_dtbb_view#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("App端--查看动态播报留言出错：", e);
		}
		return "shzz/dtbb/app/app_dtbb_view";
	}

	/**
	 * mui--获取留言下一页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/DtbbNextLy")
	public Map<Object, Object> DtbbNextLy(Model model, Integer pageNumber, String dtbbId, HttpSession session) {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		// 分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("dtbbId", dtbbId);
		map.put("code", "app");
		Pagenate<DtbbYjEntity> pagenate = dtbbService.getByDtId(pageNumber, pageSize, map);
		if(pagenate.getList().isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();// 存放Map
		for (int i = 0; i < pagenate.getList().size(); i++) {
			Map<String, String> addMap = new HashMap<String, String>();// 存放对象
			String content = pagenate.getList().get(i).getContent();// 留言内容
			String userName = pagenate.getList().get(i).getUserName();// 发布人姓名
			String createTime = pagenate.getList().get(i).getCreateTimeF();// 创建时间
			String userPic = pagenate.getList().get(i).getPicpath();// 发布人头像
			String duty = pagenate.getList().get(i).getDuty();// 发布人职务
			addMap.put("content", content);
			addMap.put("userName", userName);
			addMap.put("createTime", createTime);
			addMap.put("userPic", userPic);
			addMap.put("duty", duty);
			addList.add(addMap);
		}
		returnMap.put("result", addList);
		return returnMap;
	}
	
}
