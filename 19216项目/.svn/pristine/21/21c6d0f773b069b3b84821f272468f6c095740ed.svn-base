package com.jcwx.action.xtbg;

import java.util.ArrayList;
import java.util.Date;
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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.xtbg.MeetingAcceEntity;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.pub.DeptPersonService;
import com.jcwx.service.xtbg.MeetingAcceService;
import com.jcwx.service.xtbg.MeetingService;
import com.jcwx.service.xtbg.MeetingStaffService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.HtmlUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 协同办公---会议管理
 * @author 李伟
 * @time 2017年10月29日上午2:37:13
 */
@Controller
@RequestMapping("/xtbg/hygl")
public class MeetingAction {
	private Logger log =Logger.getLogger(MeetingAction.class);
	private static int pageSize=Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private DeptPersonService departmentPersonelService;
	@Autowired
	private MeetingStaffService meetingStaffService;
	@Autowired
	private MeetingAcceService meetingAcceService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private YhglService yhglService;
	/***
	 * 主页查询分页加载
	 * @author 李伟
	 * @time 2017年11月5日下午2:16:09
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title 标题查询
	 * @param stamp 标记查询会议是开始，还是结束
	 * @param applyTime 时间查询
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title,
			String stamp, String applyTime,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		
		String accCode = acc.getAccCode(); //系统用户登录名
		String roleCode = acc.getRole_code();//角色编码
		model.addAttribute("roleCode",roleCode);//回显页面 角色编码
		model.addAttribute("stamp",stamp);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("applyTime", applyTime);
				map.put("accCode", accCode);
				map.put("roleCode", roleCode);
				map.put("stamp", stamp);
				map.put("nowDate", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
				Pagenate<MeetingEntity> pagenate = meetingService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "xtbg/hygl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("查询会议信息出错", e);
		}
		return "xtbg/hygl/index";
	}
	/**
	 * 跳转添加修改
	 * @author 李伟
	 * @time 2017年10月30日上午9:30:05
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(String id,Model model,String acceId,HttpServletRequest req){
		if (null!=id && !("".equals(id))) {
			MeetingEntity meetingEntity = meetingService.findById(id);//查询此id是不是已经存在
			List<MeetingStaffEntity> meetStaffList = meetingEntity.getMeetStaffList();//获取参会人员idlist
			List<SysAccCountLazy> departList=new ArrayList<SysAccCountLazy>();
			for (MeetingStaffEntity staffList : meetStaffList) {//用参会人员id查询参会人员name
				if (!"admin".equals(staffList.getAcc_code())) {
					SysAccCountLazy sysAccCountLazy= meetingService.selectName(staffList.getAcc_code()).get(0);
					departList.add(sysAccCountLazy);
				}
				
			}
			model.addAttribute("departList",departList);
			model.addAttribute("meetingEntity",meetingEntity);
			return "xtbg/hygl/edit";
		}else if (null !=acceId && !"".equals(acceId)) {
			MeetingEntity meetingEntity = meetingService.findById(acceId);
			List<MeetingStaffEntity> meetStaffList = meetingEntity.getMeetStaffList();//获取参会人员idlist
			List<SysAccCountLazy> departList=new ArrayList<SysAccCountLazy>();
			for (MeetingStaffEntity staffList : meetStaffList) {//用参会人员id查询参会人员name
				if (!"admin".equals(staffList.getAcc_code())) {
					SysAccCountLazy sysAccCountLazy= meetingService.selectName(staffList.getAcc_code()).get(0);
					departList.add(sysAccCountLazy);
				}
			}
			//存放参会人员，以部门分组
			Map<String,List<SysAccCountLazy>> departMap = new HashMap<String, List<SysAccCountLazy>>();
			for (int i = 0; i < departList.size(); i++) {
				List<SysAccCountLazy> deparList = new ArrayList<SysAccCountLazy>();//存放相同部门的人员
				departMap.put(departList.get(i).getDeptName(), deparList);
			}
			for (int i = 0; i <departList.size(); i++) {
				if (departMap.get(departList.get(i).getDeptName())!=null) {
					departMap.get(departList.get(i).getDeptName()).add(departList.get(i));
				}
			}
			model.addAttribute("departMap",departMap);
			meetingEntity.setContent(HtmlUtil.htmlRemoveTag(meetingEntity.getContent()));
			model.addAttribute("meetingEntity",meetingEntity);
			//存放附件分组
			Map<String,List<MeetingAcceEntity>> classifyMap = new HashMap<String, List<MeetingAcceEntity>>();
			List<MeetingAcceEntity> meetAcceList = meetingEntity.getMeetAcceList();
			for (int i = 0; i < meetAcceList.size(); i++) {//以上传人为key值，存入map,,value值，先为空List
				List<MeetingAcceEntity> accList = new ArrayList<MeetingAcceEntity>();
				classifyMap.put(meetAcceList.get(i).getScr_id(), accList);
			}
			for (int i = 0; i < meetAcceList.size(); i++) {//以对应的上传人，存入map内的list集合
				if (classifyMap.get(meetAcceList.get(i).getScr_id())!=null) {
					classifyMap.get(meetAcceList.get(i).getScr_id()).add(meetAcceList.get(i));
				}
			}
			model.addAttribute("classifyMap",classifyMap);
			return "xtbg/hygl/acceEdit";
		}
		
		return "xtbg/hygl/edit";
	}
	/**
	 * 添加，修改
	 * @author 李伟
	 * @time 2017年10月30日上午9:45:51
	 * @param req
	 * @param meetingEntity
	 * @param sub 提交状态
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, MeetingEntity meetingEntity,String sub,String ids,String fName) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
		if (!"".equals(meetingEntity.getId()) && null!=meetingEntity.getId()) {
				meetingService.update(sub,acc,meetingEntity,ids,fName);
			}else {
				meetingService.saves(sub,acc,meetingEntity,ids);
			}
			return "success";
		} catch (Exception e) {
			log.error("添加会议信息出错", e);
		}
		return "error";
	}
	/**
	 * 保存附件
	 * @author 李伟
	 * @time 2017年11月22日下午2:43:11
	 * @param id
	 * @param fName 附件返回字符串
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doAcc")
	public String doAcc(String id,String fName,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			meetingService.doAcc(id,fName,acc);//保存附件
			return "success";
		} catch (Exception e) {
			log.error("上传会议记录信息出错", e);
		}
		return "error";
	}
	
	
	
	 /**
	  * 加载参会人员列表
	  * @author 李伟
	  * @time 2017年10月30日下午1:39:24
	  * @param name
	  * @param ajaxCmd
	  * @param pageNumber
	  * @param model
	  * @return
	  */
	@RequestMapping("/treeIndex")
	public String toIndex(String name, String ajaxCmd,String deptId, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,
			Model model){
		
		model.addAttribute("name", name);
		model.addAttribute("deptId", deptId);
		
		if(ajaxCmd == null){
			model.addAttribute("pagenate", null);
		}else{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("name", name);
			paramsMap.put("deptId", deptId);
			Pagenate<SysAccCountLazy> pagenate = departmentPersonelService.findByPage(pageNumber, pageSize, paramsMap);
			model.addAttribute("pagenate", pagenate);
			
			return "xtbg/hygl/deptTree#" + ajaxCmd;
		}
		
		return "xtbg/hygl/deptTree";
	}
	/**
	 * id查询参会人员
	 * @author 李伟
	 * @time 2017年10月30日下午4:54:45
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectName",produces="text/html;charset=UTF-8;")
	public String selectName(String ids){
		List<SysAccCountLazy> names = meetingService.selectName(ids);
		 JsonConfig config = new JsonConfig();
		 config.setExcludes(new String[]{"sysDepartment","sysaccrole"});//除去dept属性
		JSONArray nam = JSONArray.fromObject(names,config);
		
		return nam.toString();
	}
	/**
	 * 查看详情
	 * @author 李伟
	 * @time 2017年10月31日上午1:25:21
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		MeetingEntity meetingEntity = meetingService.findById(id);
		List<MeetingStaffEntity> meetStaffList = meetingEntity.getMeetStaffList();//获取参会人员idlist
		List<SysAccCountLazy> departList=new ArrayList<SysAccCountLazy>();
		for (MeetingStaffEntity staffList : meetStaffList) {//用参会人员id查询参会人员name
			if (!"admin".equals(staffList.getAcc_code())) {
				List<SysAccCountLazy> selectName = meetingService.selectName(staffList.getAcc_code());
				SysAccCountLazy sysAccCountLazy = null;
				if (selectName!=null) {
					sysAccCountLazy= selectName.get(0);
				}
				departList.add(sysAccCountLazy);
			}
		}
		//存放参会人员，以部门分组
		Map<String,List<SysAccCountLazy>> departMap = new HashMap<String, List<SysAccCountLazy>>();
		for (int i = 0; i < departList.size(); i++) {
			List<SysAccCountLazy> deparList = new ArrayList<SysAccCountLazy>();//存放相同部门的人员
			departMap.put(departList.get(i).getDeptName(), deparList);
		}
		for (int i = 0; i <departList.size(); i++) {
			if (departMap.get(departList.get(i).getDeptName())!=null) {
				departMap.get(departList.get(i).getDeptName()).add(departList.get(i));
			}
		}
		model.addAttribute("departMap",departMap);
		
		//存放附件分组
		Map<String,List<MeetingAcceEntity>> classifyMap = new HashMap<String, List<MeetingAcceEntity>>();
		List<MeetingAcceEntity> meetAcceList = meetingEntity.getMeetAcceList();
		for (int i = 0; i < meetAcceList.size(); i++) {//以上传人为key值，存入map,,value值，先为空List
			List<MeetingAcceEntity> accList = new ArrayList<MeetingAcceEntity>();
			classifyMap.put(meetAcceList.get(i).getScr_id(), accList);
		}
		for (int i = 0; i < meetAcceList.size(); i++) {//以对应的上传人，存入map内的list集合
			if (classifyMap.get(meetAcceList.get(i).getScr_id())!=null) {
				classifyMap.get(meetAcceList.get(i).getScr_id()).add(meetAcceList.get(i));
			}
		}
		model.addAttribute("classifyMap",classifyMap);
		
		meetingEntity.setContent(HtmlUtil.htmlRemoveTag(meetingEntity.getContent()));
		model.addAttribute("meetingEntity",meetingEntity);
		return "xtbg/hygl/view";
	}
	/**
	 * 删除
	 * @author 李伟
	 * @time 2017年11月6日上午9:56:58
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		System.out.println(ids.toString());
		try {
			meetingService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除会议信息出错：", e);
		}
		return "fail";
	}
	/**
	 * 删除参会人员
	 * @author 李伟
	 * @time 2017年10月31日上午10:11:34
	 * @param staffId
	 * @return
	 */
	@RequestMapping("/delStaff")
	@ResponseBody
	public String delFj(String staffId,String id){
		
		try {
			String delStaff = meetingStaffService.delStaff(staffId,id);
			return delStaff;
		} catch (Exception e) {
			log.error("删除人员信息出错：", e);
		}
		return "fail";
	}
	/**
	 * 跳转发布页面
	 * @author 李伟
	 * @time 2017年11月6日上午9:58:45
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goViewIssue")
	public String goViewIssue(String id,Model model){
		
		MeetingEntity meetingEntity = meetingService.findById(id);//根据ID查询记录
		List<MeetingStaffEntity> meetStaffList = meetingEntity.getMeetStaffList();//获取参会人员idlist
		List<SysAccCountLazy> departList=new ArrayList<SysAccCountLazy>();
		for (MeetingStaffEntity staffList : meetStaffList) {//用参会人员id查询参会人员name
			if (!"admin".equals(staffList.getAcc_code())) {
				SysAccCountLazy sysAccCountLazy= meetingService.selectName(staffList.getAcc_code()).get(0);
				departList.add(sysAccCountLazy);
			}
		}
		//存放参会人员，以部门分组
				Map<String,List<SysAccCountLazy>> departMap = new HashMap<String, List<SysAccCountLazy>>();
				for (int i = 0; i < departList.size(); i++) {
					List<SysAccCountLazy> deparList = new ArrayList<SysAccCountLazy>();//存放相同部门的人员
					departMap.put(departList.get(i).getDeptName(), deparList);
				}
				for (int i = 0; i <departList.size(); i++) {
					if (departMap.get(departList.get(i).getDeptName())!=null) {
						departMap.get(departList.get(i).getDeptName()).add(departList.get(i));
					}
				}
		model.addAttribute("departMap",departMap);
		meetingEntity.setContent(HtmlUtil.htmlRemoveTag(meetingEntity.getContent()));
		model.addAttribute("meetingEntity",meetingEntity);
		return  "xtbg/hygl/viewIssue";
	}
	
	/**
	 * 发布
	 * @author 李伟
	 * @time 2017年11月5日下午2:06:55
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/issue")
	public String issue(String id){
		try {
			meetingService.issue(id);
			return "success";
		} catch (Exception e) {
			log.error("发布出错：", e);
		}
		return "fail";
	}
	@ResponseBody
	@RequestMapping("/delFj")
	public String delFj(String fjId){
		try {
			meetingAcceService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除附件出错：",e);
		}
		return "fail";
	}
	
	
	
	/**
	 * 查看人员树
	 * @param model
	 * @param deptId 部门id
	 * @param jsrIds 已被选 的id字符串
	 * @return
	 */
	@RequestMapping("/showTree")
	public String showTree(Model model,String deptId,String jsrIds,String id){
		if (id !=null &&!"".equals(id)) {
			MeetingEntity meetingEntity = meetingService.findById(id);
			List<MeetingStaffEntity> staffList = meetingEntity.getMeetStaffList();
			for (MeetingStaffEntity ms : staffList) {
				jsrIds+=ms.getAcc_code()+";";
			}
		}
		
		model.addAttribute("jsrIds", jsrIds);
		model.addAttribute("docId", id);
		return "/xtbg/hygl/membertree";
	}
	
	/**
	 * 加载人员树
	 * 
	 * @param jsrIds 已被选 的id字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadTreeData", produces="text/html;charset=UTF-8")
	public String loadTreeData(String jsrIds,HttpServletRequest req){
		log.info("jsrIds:"+jsrIds);
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
		
			Map<String, String> params = new HashMap<String, String>();
			List<SysDepartment> list = departmentService.findByParams(params);
			//组织节点
			for(SysDepartment department : list){
				String code = department.getDeptId();
				String name = department.getDeptName();
				String pid = department.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeMap.put("nocheck", "true");
				nodeList.add(nodeMap);
			}
			//添加成员
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("isDept", "1");
			List<SysAccCount> userlist = yhglService.findByParam(paramMap);
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				SysAccCount te = userlist.get(i);
				nodeMap.put("id", te.getAccCode());
				nodeMap.put("name", te.getName());
				if(accCode==te.getAccCode()){
					nodeMap.put("chkDisabled", "true");
				}
				if(jsrIds != null && jsrIds.length()>0){
					if(jsrIds.indexOf(te.getAccCode())>=0){
						nodeMap.put("checked", "true");
					}
				}
				nodeMap.put("pId", te.getDeptId());
				nodeList.add(nodeMap);
				
			}
		
		
		
		//添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "root");
		rootNode.put("name", "博昌街道");
		rootNode.put("pId", "0");
		rootNode.put("open", "true");
		rootNode.put("nocheck", "true");
		nodeList.add(rootNode);
		
		return JSONArray.fromObject(nodeList).toString();
	}
}


