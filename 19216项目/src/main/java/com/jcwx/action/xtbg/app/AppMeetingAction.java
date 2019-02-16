package com.jcwx.action.xtbg.app;

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
import com.jcwx.entity.xtbg.MeetingAcceEntity;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
import com.jcwx.service.xtbg.MeetingService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 会议管理
 * @author 李伟
 * @time 2017年11月13日上午8:02:23
 */
@Controller
@RequestMapping("/app/xtbg/appMeeting")
public class AppMeetingAction {
	private Logger log = Logger.getLogger(AppMeetingAction.class);//log4j
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	
	
		@Autowired
		private MeetingService meetingService;
	/**
	 * 跳转并查询未开始会议
	 * @author 李伟
	 * @time 2017年11月13日上午8:02:36
	 * @param ajaxCmd
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/toMeeting")
	public String toMeeting(String ajaxCmd,Model model,String title,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
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
						map.put("roleCode", roleCode);
						map.put("accCode", accCode);
						map.put("nowDate", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
						map.put("stamp", "1");//加入查询标记  1为未开始的会议
						Pagenate<MeetingEntity> pagenate = meetingService.findByPage(pageNumber, pageSize, map);
						model.addAttribute("pagenate", pagenate);
						model.addAttribute("pageCnts", pagenate.getPgCnts());
						model.addAttribute("pageSize", pagenate.getPageSize());
						return "/xtbg/hygl/app/appIndex#" + ajaxCmd;
					}
				} catch (Exception e) {
					log.error("[AppMeetingAction]_[toMeeting]_查询会议管理列表出错", e);
				}
		
		return "xtbg/hygl/app/appIndex";
	}
	/**
	 * 查询结束会议
	 * @author 李伟
	 * @time 2017年11月17日下午2:30:08
	 * @param ajaxCmd
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEndMeeting")
	public String toEndMeeting(String ajaxCmd,Model model,String title,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
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
						map.put("roleCode", roleCode);
						map.put("accCode", accCode);
						map.put("nowDate", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
						map.put("stamp", "2");//加入查询标记  2为已结束的会议
						Pagenate<MeetingEntity> pagenate = meetingService.findByPage(pageNumber, pageSize, map);
						model.addAttribute("pagenate", pagenate);
						model.addAttribute("pageCnts", pagenate.getPgCnts());
						model.addAttribute("pageSize", pagenate.getPageSize());
						return "/xtbg/hygl/app/appIndex#" + ajaxCmd;
					}
				} catch (Exception e) {
					log.error("[AppMeetingAction]_[toEndMeeting]_查询会议管理列表出错", e);
				}
		return "xtbg/hygl/app/appIndex";
	}
	/**
	 * 查询下一页未开始的会议
	 * @author 李伟
	 * @time 2017年11月13日上午8:03:11
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/meetingNextContent")
	public Map meetingNextContent(Model model,String title,Integer pageNumber,HttpServletRequest req){
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
				map.put("roleCode", roleCode);
				map.put("accCode", accCode);
				map.put("nowDate", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
				map.put("stamp", "1");//加入查询标记  1为未开始的会议
				Pagenate<MeetingEntity> pagenate = meetingService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				List<MeetingEntity> sqfwLists = pagenate.getList();
				MeetingEntity meetingEntity = new MeetingEntity();
				List<Map> addList = new ArrayList<Map>();//存放Map
				for(int i = 0; i<sqfwLists.size(); i++){
					Map<String, String> addMap = new HashMap<String,String>();//存放对象
					meetingEntity = sqfwLists.get(i);
				 String titleId = meetingEntity.getId();//标题id
				 String titleName = meetingEntity.getTitle();//标题名称
				// String createTime = meetingEntity.getFmtCreate_date();//创建时间
				 String fmtStart_date = meetingEntity.getFmtStart_date();
				 String fmtEnd_date = meetingEntity.getFmtEnd_date();
				 String venue = meetingEntity.getVenue();
				 addMap.put("titleId", titleId);
				 addMap.put("titleName", titleName);
				// addMap.put("createTime", createTime);
				 addMap.put("fmtStart_date", fmtStart_date);
				 addMap.put("fmtEnd_date", fmtEnd_date);
				 addMap.put("venue", venue);
				 addList.add(addMap);
				}
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				returnMap.put("result", addList);
				return returnMap;
	}
	/**
	 * 查询下一页已结束的会议
	 * @author 李伟
	 * @time 2017年11月17日下午2:53:52
	 * @param model
	 * @param title
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/endMeetingNextContent")
	public Map endMeetingNextContent(Model model,String title,Integer pageNumber,HttpServletRequest req){
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
				map.put("roleCode", roleCode);
				map.put("accCode", accCode);
				map.put("nowDate", DateUtils.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//当前时间
				map.put("stamp", "2");//加入查询标记  2为已结束的会议
				Pagenate<MeetingEntity> pagenate = meetingService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				List<MeetingEntity> sqfwLists = pagenate.getList();
				MeetingEntity meetingEntity = new MeetingEntity();
				List<Map> addList = new ArrayList<Map>();//存放Map
				for(int i = 0; i<sqfwLists.size(); i++){
					Map<String, String> addMap = new HashMap<String,String>();//存放对象
					meetingEntity = sqfwLists.get(i);
				 String titleId = meetingEntity.getId();//标题id
				 String titleName = meetingEntity.getTitle();//标题名称
				// String createTime = meetingEntity.getFmtCreate_date();//创建时间
				 String fmtStart_date = meetingEntity.getFmtStart_date();
				 String fmtEnd_date = meetingEntity.getFmtEnd_date();
				 String venue = meetingEntity.getVenue();
				 addMap.put("titleId", titleId);
				 addMap.put("titleName", titleName);
				// addMap.put("createTime", createTime);
				 addMap.put("fmtStart_date", fmtStart_date);
				 addMap.put("fmtEnd_date", fmtEnd_date);
				 addMap.put("venue", venue);
				 addList.add(addMap);
				}
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				returnMap.put("result", addList);
				return returnMap;
	}
	/**
	 * 查看详情
	 * @author 李伟
	 * @time 2017年11月13日上午10:27:49
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model,String id){
		try {
			if (id !=null &&!"".equals(id)) {
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
					if (departList.get(i)!=null) {
						List<SysAccCountLazy> deparList = new ArrayList<SysAccCountLazy>();//存放相同部门的人员
						departMap.put(departList.get(i).getDeptName(), deparList);
					}
				}
				for (int i = 0; i <departList.size(); i++) {
					if (departList.get(i)!=null) {
						if (departMap.get(departList.get(i).getDeptName())!=null) {
							departMap.get(departList.get(i).getDeptName()).add(departList.get(i));
						}
					}
				}
				model.addAttribute("departMap",departMap);
				
				//存放附件分组
				Map<String,List<MeetingAcceEntity>> classifyMap = new HashMap<String, List<MeetingAcceEntity>>();
				List<MeetingAcceEntity> meetAcceList = meetingEntity.getMeetAcceList();
				for (int i = 0; i < meetAcceList.size(); i++) {//以上传人为key值，存入map,,value值，先为空List
					if (meetAcceList.get(i)!=null) {
						List<MeetingAcceEntity> accList = new ArrayList<MeetingAcceEntity>();
						classifyMap.put(meetAcceList.get(i).getScr_id(), accList);
					}
				}
				for (int i = 0; i < meetAcceList.size(); i++) {//以对应的上传人，存入map内的list集合
					if (meetAcceList.get(i)!=null) {
						if (classifyMap.get(meetAcceList.get(i).getScr_id())!=null) {
							classifyMap.get(meetAcceList.get(i).getScr_id()).add(meetAcceList.get(i));
						}
					}
				}
				model.addAttribute("classifyMap",classifyMap);
				String path = ProjectUtils.getSysCfg("appDownloadPath");
				model.addAttribute("path",path);
				//model.addAttribute("departList",departList);
				model.addAttribute("detailEntity",meetingEntity);
				return "/xtbg/hygl/app/detail";
			}
		} catch (Exception e) {
			log.error("AppExposureAtion类 goView方法 查询详情信息出错",e);
		}
		return "/xtbg/hygl/app/detail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8;")
	public String save(String hygl_id){
		try {
			return "{\"result\":\"success\",\"pid\":\"" + hygl_id + "\"}";
		} catch (Exception e) {
			log.error("appMeetingAtion类 save方法 添加附件出错",e);
		}
		return "{\"result\":\"fail\"}";
	}
}
