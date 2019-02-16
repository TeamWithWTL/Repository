package com.jcwx.action.shzz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.HdglService;
import com.jcwx.service.shzz.ZzxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;

/**
 * @author m
 */
@Controller
@RequestMapping("/shzz/hdgl")
public class HdglAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(HdglAction.class);
	
	@Autowired
	private HdglService hdglService;
	@Autowired
	private SjzdService sjzdService; 
	@Autowired
	private ZzxxService zzxxService;
	
	/**
	 * 跳转到活动管理首页
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTime,HttpSession session){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
				model.addAttribute("roleCode", sysAccCount.getRole_code());
				model.addAttribute("accCode", sysAccCount.getAccCode());
				
			try {
				model.addAttribute("title", title);
				model.addAttribute("createTime",createTime);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title",title);
				Map.put("createTime", createTime);
				Pagenate<HdglEntity> pagenate = hdglService.getHdglContent(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdgl/hdgl_index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织活动列表出错：",e);
		}
		return "shzz/hdgl/hdgl_index";
	}
	
	/**
	 * 查看活动详情，并审核留言列表
	 * @author 李伟
	 * @time 2017年11月24日上午9:55:05
	 * @param session
	 * @param id
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model,String ajaxCmd,String ajaxCmdFk,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber) {
		log.info("活动管理信息ID：" + id);
		SysAccCount acc = (SysAccCount) session.getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		//String role_code = acc.getRole_code();	//当前用户角色
		/*List<String> arrayList=new ArrayList<String>();
		if (role_code!=null &&!"".equals(role_code)) {
			if (role_code.indexOf(",")!=-1) {
				String[] roleCodes = role_code.split(",");	//当前登录人员角色
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(role_code);
			}
		}
		if (arrayList.contains("09") || arrayList.contains("99")) {	//判断是不是社会组织管理员或者管理员，并回显页面
			model.addAttribute("isManager","yes");
		}else{
			model.addAttribute("isManager","no");
		}
		model.addAttribute("roleCode", role_code);*/
		try {
			HdglEntity hdgl = null;
			if(id != null && !"".equals(id)){
				hdgl = hdglService.findById(id);
			}
			model.addAttribute("hdgl", hdgl);
			//留言
			if (ajaxCmd==null) {
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("hdglId", id);
				Map.put("role_code", roleCode);
				Pagenate<HdglYjEntity> pagenate=hdglService.findLyByPage(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/hdgl/view#"+ajaxCmd;
			}
			
			//获取该组织活动可参与组织列表
			List<HdglFkEntity> fkList = hdglService.findFkByHdId(id);
			model.addAttribute("fkList", fkList);
			
			/**
			 * 反馈 审核通过  需求已改
			if (ajaxCmdFk==null) {
				model.addAttribute("pagenateFk", null);
			}else{
				
				Map<String, String> fkMap = new HashMap<String, String>();
				fkMap.put("hdgl_id", id);
				//fkMap.put("role_code", role_code);
				Pagenate<HdglFkEntity> pagenateFk=hdglService.findFkByPage(pageNumber, pageSize, fkMap);
				List<HdglFkEntity> hdglList = pagenateFk.getList();
				for (HdglFkEntity hdglFkEntity : hdglList) {
					ZzxxEntity zzxxEntity = hdglService.findZzById(hdglFkEntity.getZzId());
					hdglFkEntity.setZzxxEntity(zzxxEntity);
				}
				model.addAttribute("pagenateFk", pagenateFk);
				return "shzz/hdgl/view#"+ajaxCmdFk;
			}
			*/
		} catch (Exception e) {
			log.error("查看活动管理详情  和留言审核 出错：",e);
		}
		return "shzz/hdgl/view";
	}
	
	/*@RequestMapping("/showFk")
	public String showFk(){
		return "shzz/hdgl/view";
	}*/
	
	/**
	 * 跳转到新增修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model,String id, String code){
		try {
			HdglEntity hdgl = null;
			if(id != null && !id.equals("")){
				hdgl = hdglService.findById(id);
			}
			
			//获取可反馈组织列表
			List<HdglFkEntity> fkList = hdglService.findFkByHdId(id);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for(HdglFkEntity fk : fkList){
				Map<String, String> map = new HashMap<String, String>();
				String zz_id = fk.getZzId();
				ZzxxEntity zzxxEntity = zzxxService.getById(zz_id);
				map.put("zzId", zzxxEntity.getId());
				map.put("name", zzxxEntity.getName());
				list.add(map);
			}
			
			//获取一级社区服务列表
			List<SysParam> oneFwList = sjzdService.getParamDescList1(code);
			
			//获取二级社区服务列表
			List<SysParamDesc> twoFwList = sjzdService.getEjFwList1(code);
			
			model.addAttribute("zzList", list);
			model.addAttribute("oneFwList", oneFwList);
			model.addAttribute("twoFwList", twoFwList);
			model.addAttribute("hdgl", hdgl);
		} catch (Exception e) {
			log.info("跳转添加修改页面出错：",e);
		}
		return "shzz/hdgl/index_addEdit";
	}
	
	/**
	 * 保存组织活动
	 * @param hdgl
	 * @param id
	 * @param fName
	 * @param startDateFmt
	 * @param zzIds
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HdglEntity hdgl, String id, String fName, String startDateFmt, String zzIds, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			HdglEntity h = hdglService.getById(id);
			 Date date = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date start = sdf.parse(hdgl.getStartDateFmt());
//		     Calendar c = Calendar.getInstance();              
//		     c.setTime(date);                                 
//		     int year1 = c.get(Calendar.YEAR);
//		     int month1 = c.get(Calendar.MONTH)+1;
//		     int day1 = c.get(Calendar.DAY_OF_MONTH); 
//		     String year =  Integer.toString(year1);
//		     String month =  "";
//		     String day = "";
//		     if(month1 < 10){
//		    	 month = "0" + Integer.toString(month1);
//		     }else{
//		    	 month = Integer.toString(month1);
//		     }
//		     if(day1 < 10){
//		    	 day = "0" + Integer.toString(day1);
//		     }else{
//		    	 day = Integer.toString(day1);
//		     }
//		     String currentDate = year+"-"+month+"-"+day;
			if(h == null){
				//新增
				if(start.before(date)){
					hdgl.setHdStatus("1");	//进行中
				}else{
					hdgl.setHdStatus("0");	//未开始
				}
				hdgl.setUserId(sysAccCount.getAccCode());
				hdgl.setUserName(sysAccCount.getName());
				hdglService.save(hdgl, fName, zzIds);
			}else{
				//修改
				if(start.before(date)){
					hdgl.setHdStatus("1");	//进行中
				}else{
					hdgl.setHdStatus("0");	//未开始
				}
				hdglService.update(hdgl, id, fName, zzIds);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存组织活动信息出错：",e);
		}
		return "error";
	}
	
	/**
	 * 删除组织活动
	 * @param ids --组织活动ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			hdglService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除组织活动出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除附件
	 * @param fjId --附件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delFj")
	public String delFj(String fjId, String fName){
		try {
			log.info("要删除的附件ID：" + fjId);
			hdglService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除组织活动信息附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 设置组织活动是否热点
	 * @param id
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setHot")
	public String setHot(String id,String flag){
		try {
			hdglService.updateisHot(id, flag);
			return "success";
		} catch (Exception e) {
			log.error("设置组织活动热点出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转审核页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goViewAudit")
	public String goViewAudit(String id,Model model){
		HdglEntity hdglEntity = hdglService.findById(id);
		model.addAttribute("hdglEntity",hdglEntity);
		return "shzz/hdgl/view_examine";
	}
	
	/**
	 * 审核组织活动信息
	 * @return
	 */
	@RequestMapping("/doAuditing")
	@ResponseBody
	public String doAuditing(String id, String status,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		
		try {
			hdglService.doAuditing(id,status,sysAccCount);
			if (status.equals("1")) {
				return "success";
			}else if (status.equals("2")) {
				return "succes";
			}
		} catch ( Exception e) {
			log.error("审核出错：", e);
		}
		return "fail";
	}
		
	//设置热点
	@RequestMapping("/hot")
	@ResponseBody
	public String hot(String id,String hot){
	
		try {
			hdglService.hot(id,hot);
			if (hot.equals("1")) {
				return "success";
			}else if (hot.equals("2")) {
				return "succes";
			}
		} catch ( Exception e) {
			log.error("热点设置出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转组织活动反馈审核页面
	 * @param session
	 * @param id
	 * @param model
	 * @param ajaxDshCmd
	 * @param ajaxBtgCmd
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/goShFkView")
	public String goShFkView(HttpSession session, String id, Model model, String ajaxDshCmd, String ajaxBtgCmd,
			String ajaxTgCmd, @RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber) {
		log.info("组织活动==ID：" + id);
		try {
			//审核通过反馈
			if (ajaxTgCmd==null) {
				model.addAttribute("pagenateTg", null);
			}else{
				Map<String, String> tgMap = new HashMap<String, String>();
				tgMap.put("hdglId", id);
				Pagenate<HdglFkEntity> pagenateTg=hdglService.findFkByPage(pageNumber, pageSize, tgMap);
				List<HdglFkEntity> fkList = pagenateTg.getList();
				for (HdglFkEntity hdglFkEntity : fkList) {
					ZzxxEntity zzxxEntity = hdglService.findZzById(hdglFkEntity.getZzId());
					hdglFkEntity.setZzxxEntity(zzxxEntity);
				}
				model.addAttribute("pagenateTg", pagenateTg);
				return "shzz/hdgl/shfkview#"+ajaxTgCmd;
			}
			
			//待审核反馈
			if (ajaxDshCmd==null) {
				model.addAttribute("pagenateDsh", null);
			}else{
				Map<String, String> dshMap = new HashMap<String, String>();
				dshMap.put("hdglId", id);
				Pagenate<HdglFkEntity> pagenateDsh = hdglService.findDshFkByPage(pageNumber, pageSize, dshMap);
				model.addAttribute("pagenateDsh", pagenateDsh);
				return "shzz/hdgl/shfkview#"+ajaxDshCmd;
			}
			
			//审核不通过反馈
			if (ajaxBtgCmd==null) {
				model.addAttribute("pagenateBtg", null);
			}else{
				Map<String, String> btgMap = new HashMap<String, String>();
				btgMap.put("hdglId", id);
				Pagenate<HdglFkEntity> pagenateBtg = hdglService.findBtgFkByPage(pageNumber, pageSize, btgMap);
				model.addAttribute("pagenateBtg", pagenateBtg);
				return "shzz/hdgl/shfkview#"+ajaxBtgCmd;
			}
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("查看组织活动反馈出错：",e);
		}
		return "shzz/hdgl/shfkview";
	}
	
	/**
	 * 跳转组织活动留言审核页面
	 * @param session
	 * @param id
	 * @param model
	 * @param ajaxDshCmd
	 * @param ajaxBtgCmd
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/goShLyView")
	public String goShLyView(HttpSession session, String id, Model model, String ajaxDshCmd,String ajaxBtgCmd,
				@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber) {
		log.info("组织活动==ID：" + id);
		try {
			//待审核留言
			if (ajaxDshCmd==null) {
				model.addAttribute("pagenateDsh", null);
			}else{
				Map<String, String> dshMap = new HashMap<String, String>();
				dshMap.put("hdglId", id);
				Pagenate<HdglYjEntity> pagenateDsh = hdglService.findLyDshByPage(pageNumber, pageSize, dshMap);
				model.addAttribute("pagenateDsh", pagenateDsh);
				return "shzz/hdgl/shlyview#"+ajaxDshCmd;
			}
			
			//审核不通过留言
			if (ajaxBtgCmd==null) {
				model.addAttribute("pagenateBtg", null);
			}else{
				Map<String, String> btgMap = new HashMap<String, String>();
				btgMap.put("hdglId", id);
				Pagenate<HdglYjEntity> pagenateBtg = hdglService.findLyBtgByPage(pageNumber, pageSize, btgMap);
				model.addAttribute("pagenateBtg", pagenateBtg);
				return "shzz/hdgl/shlyview#"+ajaxBtgCmd;
			}
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("查看组织活动留言出错：",e);
		}
		return "shzz/hdgl/shlyview";
	}
	
	/**
	 * 审核反馈  通过
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shFktg")
	public String shFktg(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				hdglService.shFktg(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("审核反馈出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核反馈  不通过
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shFkbtg")
	public String shFkbtg(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				hdglService.shFkbtg(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("审核反馈出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核留言  通过
	 * @author 李伟
	 * @time 2017年11月24日下午1:51:41
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/successLy")
	public String successLy(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				hdglService.successLy(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("HdglAction类  successLy方法  审核留言出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核留言  不通过
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shLybtg")
	public String shLybtg(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				hdglService.shLybtg(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("HdglAction类  shLybtg方法  审核留言出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除留言
	 * @author 李伟
	 * @time 2017年11月24日下午1:42:59
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDelLy")
	public String doDelLy(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				hdglService.delLy(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("HdglAction类  doDelLy方法  删除留言出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转社会组织树页面
	 */
	@RequestMapping("/showTree")
	public String showTree() {
		return "shzz/hdgl/zztree";
	}
	
	/**
	 * 获取社会组织树
	 * @param request
	 * @param id	组织活动ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTreeData", produces = "text/html;charset=UTF-8")
	public String loadTreeData(HttpServletRequest request, String id) {
		// 组织结构节点列表
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
		String flag = "0";//用来判断父节点是否选中
		if(null != id && !"".equals(id)){
			//获取组织活动信息
			HdglEntity zzhd = hdglService.findById(id);
			
			//获取组织列表
			List<ZzxxEntity> list = zzxxService.getList();
			for(ZzxxEntity zz : list){
				String code = zz.getId();
				String name = zz.getName();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("open", "true");
				if(flag.equals("1")){
					nodeMap.put("checked", "true");
					flag ="0";
				}
				nodeList.add(nodeMap);
			}
		}else{
			//获取组织列表
			List<ZzxxEntity> list = zzxxService.getList();
			for(ZzxxEntity zz : list){
				String code = zz.getId();
				String name = zz.getName();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("open", "true");
				nodeList.add(nodeMap);
			}
			log.info("社会组织树======" + JSONArray.fromObject(nodeList).toString());
		}
		return JSONArray.fromObject(nodeList).toString();
	}
		
}
