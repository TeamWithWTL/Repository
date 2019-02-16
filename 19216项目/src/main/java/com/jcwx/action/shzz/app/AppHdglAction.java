package com.jcwx.action.shzz.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.shzz.HdglAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.HdglService;
import com.jcwx.service.shzz.ZzxxService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
/**
 * 活动管理
 * @author 李伟
 * @time 2017年11月13日上午8:01:46
 */
@Controller
@RequestMapping("/app/shzz/appHdgl")
public class AppHdglAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(HdglAction.class);//log4j
	
	@Autowired
	private HdglService hdglService;
	@Autowired
	private ZzxxService zzxxService;
	
	/**
	 * 跳转并查询
	 * @author 李伟
	 * @time 2017年11月13日上午8:00:47
	 * @param ajaxCmd
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/toHdgl")
	public String toHdgl(String ajaxCmd,Model model,String title,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
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
				String code = "app";
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("app", code);
				Pagenate<HdglEntity> pagenate = hdglService.getHdglContent(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "/shzz/hdgl/app/appIndex#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[AppHdglAction]_[toHdgl]_查询党政要闻列表出错", e);
		}
		return "/shzz/hdgl/app/appIndex";
	}
	
	/**
	 * 查询下一页组织活动 并分页
	 * @author 李伟
	 * @time 2017年11月8日下午5:15:22
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hdglNextContent")
	public Map hdglNestContent(Model model,String title,Integer pageNumber,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
		String code = "app";
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("app", code);
		Pagenate<HdglEntity> pagenate = hdglService.getHdglContent(pageNumber, pageSize, map);
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<HdglEntity> sqfwLists = pagenate.getList();
		HdglEntity hdglEntity = new HdglEntity();
	
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<sqfwLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
			hdglEntity = sqfwLists.get(i);
		 String titleId = hdglEntity.getId();//标题id
		 String titleName = hdglEntity.getTitle();//标题名称
		 String createTime = hdglEntity.getCreateTimes();//创建时间
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
	 * 查看详情
	 * @author 李伟
	 * @time 2017年11月13日上午10:26:34
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model,String id,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String zh_type =acc.getZh_type();
		String role_code = acc.getRole_code();
		List<String> arrayList=new ArrayList<String>();
		if (role_code!=null &&!"".equals(role_code)) {
			if (role_code.indexOf(",")!=-1) {
				String[] roleCodes = role_code.split(",");//当前登录人员角色
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(role_code);
			}
		}
		if (arrayList.contains("09")) {	//判断是不是社会组织管理员，并回显页面
			model.addAttribute("isManager","yes");
		}else{
			model.addAttribute("isManager","no");
		}
		model.addAttribute("zh_type",zh_type);	//回显用户身份,判断是不是法人
		if (zh_type!=null &&!"".equals(zh_type)&&"3".equals(zh_type)) {	//判断是不是法人身份
			String accCode = acc.getAccCode();
			List<ZzxxEntity> zzxxEntityList=hdglService.findZzxmByAccCode(accCode);
			model.addAttribute("zzxxEntityList",zzxxEntityList);	//回显当前法人，代表的组织集合
		}
		
		//判断登录用户是否存在组织
		ZzxxEntity zz = zzxxService.getZzByFrId1(acc.getAccCode());
		model.addAttribute("zz", zz);
		
		List<HdglYjEntity> hdglLyList = new ArrayList<HdglYjEntity>();	//存放留言集合
		List<HdglFkEntity>fkList=new ArrayList<HdglFkEntity>();
		try {
			if (id !=null &&!"".equals(id)) {
				HdglEntity hdglEntity = hdglService.findById(id);	//活动查询
				hdglLyList=hdglService.findByHdId(id);	//留言查询
				fkList=hdglService.findYfkByHdId(id);	//反馈查询
				for (HdglFkEntity hdglFkEntity : fkList) {
					ZzxxEntity zzxxEntity=hdglService.findZzById(hdglFkEntity.getZzId());	//查询每一个反馈对应的组织
					hdglFkEntity.setZzxxEntity(zzxxEntity);
				}
				model.addAttribute("fkList",fkList);	//反馈信息
				model.addAttribute("detailEntity",hdglEntity);
				model.addAttribute("hdglLyList",hdglLyList);
			}
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path",path);
		} catch (Exception e) {
			log.error("AppHdglAction类goView方法查询详情出错",e);
		}
		return "/shzz/hdgl/app/detail";
	}
	
	/**
	 * 保存留言
	 * @author 李伟
	 * @time 2017年11月15日上午10:02:55
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSaveLy")
	public String doSaveLy(String id,String content,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");//获取当前用户
		try {
			//查询当前登录用户是否对当前组织活动已留言
			HdglYjEntity ly = hdglService.getHdYjByhdId(id, acc.getAccCode());
			if(ly==null){
				hdglService.saveLy(id,content,acc);
				return "success";
			}else{
				return "yly";
			}
		} catch (Exception e) {
			log.error("AppHdglAction类doSaveLy方法保存留言信息出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 活动管理留言查询
	 * @author 李伟
	 * @time 2017年11月19日上午9:34:49
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param hdglId
	 * @return
	 */
	@RequestMapping("/initHdglLyView")
	public String initDtbbLyView(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String hdglId) {
		log.info("活动管理ID：" + hdglId);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("hdglId", hdglId);
				//map.put("code", "app");
				Pagenate<HdglYjEntity> pagenate = hdglService.findHdyjFistPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdgl/app/detail#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("App端--查看活动管理留言出错：", e);
		}
		return "shzz/hdgl/app/detail";
	}
	
	/**
	 * 留言下一页
	 * @author 李伟
	 * @time 2017年11月19日上午9:33:49
	 * @param model
	 * @param pageNumber
	 * @param hdglId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/HdglNextLy")
	public Map<Object, Object> DtbbNextLy(Model model, Integer pageNumber, String hdglId, HttpSession session) {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		// 分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("hdglId", hdglId);
		//map.put("code", "app");
		Pagenate<HdglYjEntity> pagenate = hdglService.findHdyjFistPage(pageNumber, pageSize, map);
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
	
	/**
	 * 保存组织活动反馈，加附件id传值
	 * @param hdglFkEntity
	 * @param id
	 * @param content
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveFk", produces = "text/html;charset=UTF-8;")
	public String saveFk(HdglFkEntity hdglFkEntity, String id, String content, HttpServletRequest req){
		log.info("组织活动ID==" + id);
		//获取登录用户信息
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			//获取组织
			ZzxxEntity zz = zzxxService.getZzByFrId1(acc.getAccCode());
			//获取组织活动
			HdglEntity zzhd = hdglService.findById(id);
			if(zz!=null){
				String zzId = zz.getId();
				HdglFkEntity fk = hdglService.getFkByHdIdZzId(id, zzId);
				if(fk!=null){
					if("2".equals(zzhd.getType_two()) ){
						fk.setIsBack("1"); 	//需要反馈
					}
					fk.setShStatus("0"); //未审核
					fk.setContent(content);
					hdglService.saveFk(fk, fk.getId());
					return "{\"result\":\"success\",\"pid\":\"" + fk.getId() + "\"}";
				}else{
					return "{\"result\":\"bnfk\"}";
				}
			}else{
				return "{\"result\":\"bnfk\"}";
			}
		} catch (Exception e) {
			log.error("AppHdglAction类 saveFk方法出错",e);
		}
		return "{\"result\":\"fail\"}";
	}
}
