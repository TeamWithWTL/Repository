package com.jcwx.action.shgl;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.xtgl.YhglAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.EventFlowRecordEntity;
import com.jcwx.entity.shgl.EventStatusEntity;
import com.jcwx.service.shgl.SjglService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 事件管理
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/sjgl")
public class SjglAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private SjglService sjglService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private SjzdService sjzdService;

	/**
	 * 首页数据
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,
			HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				map.put("applyTime", applyTime);
				if ("01".equals(roleCode)) {// 网格员
					map.put("gridId", acc.getGridId());
				} else if ("02".equals(roleCode)) {// 服务站管理员
					map.put("ssId", acc.getSsId());
					map.put("accCode", accCode);
				} else if ("03".equals(roleCode)) {// 社区管理员
					map.put("commId", acc.getCommId());
					map.put("accCode", accCode);
				} else {
					map.put("accCode", accCode);
				}
				map.put("roleCode", roleCode);
				Pagenate<EventEntity> pagenate = sjglService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sjgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("查询事件信息出错", e);
		}
		return "shgl/sjgl/index";
	}

	/**
	 * 跳转查看界面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model, String id) {
		EventEntity event = null;
		List<SysParamDesc> sysStations = sjzdService.findByCode("10008").getSysParamDesc();// 事件类型
		model.addAttribute("sysStations", sysStations);
		if (id != null && !"".equals(id)) {
			event = sjglService.findById(id);
		}
		model.addAttribute("event", event);
		if ("1".equals(event.getEventStastus().getStatus())) {
			return "shgl/sjgl/view";
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("statusId", event.getEventStastus().getId());
			map.put("done_status", "1");
			EventFlowRecordEntity eventflow = sjglService.findByPara(map);
			model.addAttribute("eventflow", eventflow);
			return "shgl/sjgl/viewDeal";
		}
	}

	/**
	 * 跳转处理界面
	 * 
	 * @return
	 */
	@RequestMapping("/goDeal")
	public String goDeal(Model model, String id) {
		EventEntity event = null;
		if (id != null && !"".equals(id)) {
			event = sjglService.findById(id);
		}
		model.addAttribute("event", event);
		return "shgl/sjgl/deal";
	}

	/**
	 * 跳转上报界面
	 * 
	 * @return
	 */
	@RequestMapping("/goReport")
	public String goReport(Model model, String id) {
		EventEntity event = null;
		if (id != null && !"".equals(id)) {
			event = sjglService.findById(id);
		}
		model.addAttribute("event", event);
		model.addAttribute("pagenate", null);
		return "shgl/sjgl/report";
	}

	/**
	 * 上报人员查询
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @return
	 */
	@RequestMapping("/getPerData")
	public String getPerData(HttpServletRequest req, Model model,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String ajaxCmd, String name) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				if ("01".equals(roleCode)) {// 网格员
					map.put("ssId", acc.getSsId());// 服务站id
					map.put("rolecode", "02");
				} else if ("02".equals(roleCode)) {// 服务站管理员
					map.put("commId", acc.getCommId());// 社区id
					map.put("rolecode", "03");
				} else if ("03".equals(roleCode)) {// 社区管理员
					map.put("rolecode", "04");
				} else if ("04".equals(roleCode)) {// 街道信息员
					map.put("rolecode", "05");
				}
				map.put("name", name);
				Pagenate<SysAccCountLazy> pagenate = yhglService.findAccByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "/shgl/sjgl/report#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("上报人员查询出错", e);
		}
		return "/shgl/sjgl/report";
	}

	/**
	 * 添加、编辑 保存数据
	 * 
	 * @param req
	 * @param doneTime
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, EventFlowRecordEntity eventflow, String id, String doneTime) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		EventEntity eventEntity = sjglService.findById(id);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("statusId", eventEntity.getEventStastus().getId());
			map.put("accCode", accCode);
			EventFlowRecordEntity eventflowOld = sjglService.findByPara(map);
			if (eventflowOld == null) {// 为null 则数据库中没有该数据
				eventflow.setDone_code(accCode);
				eventflow.setDone_name(accName);
				eventflow.setDone_time(DateUtils.parseDate(doneTime, "yyyy-MM-dd"));
				eventflow.setStatus_id(eventEntity.getEventStastus().getId());
				eventflow.setDone_status("1");// 已处理
				sjglService.saveFlow(eventflow);
			} else {
				eventflowOld.setDone_time(DateUtils.parseDate(doneTime, "yyyy-MM-dd"));
				eventflowOld.setDone_status("1");// 已处理
				eventflowOld.setIs_open(eventflow.getIs_open());
				eventflowOld.setResult(eventflow.getResult());
				sjglService.updateFlow(eventflowOld);// 修改
			}
			EventStatusEntity eventStatus = eventEntity.getEventStastus();
			eventStatus.setStatus("2");// 已结束
			eventStatus.setEnd_time(new Date());
			sjglService.saveSta(eventStatus);
			return "success";
		} catch (Exception e) {
			log.info("添加事件处理信息出错", e);
		}
		return "error";
	}

	/**
	 * 上报
	 * 
	 * @param req
	 * @param doneTime
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doReport")
	public String doReport(HttpServletRequest req, String id, String accCode) {
		EventEntity eventEntity = sjglService.findById(id);
		SysAccCount acc = yhglService.findSysAccCountByCode(accCode);
		String accName = acc.getName();
		try {
			EventStatusEntity eventStatus = eventEntity.getEventStastus();
			eventStatus.setCurent_node(accCode);
			sjglService.saveSta(eventStatus);
			EventFlowRecordEntity eventflow = new EventFlowRecordEntity();
			eventStatus.setStatus(eventStatus.getId());
			eventflow.setDone_code(accCode);
			eventflow.setDone_name(accName);
			eventflow.setStatus_id(eventEntity.getEventStastus().getId());
			eventflow.setDone_status("0");// 未处理
			sjglService.saveFlow(eventflow);
			return "success";
		} catch (Exception e) {
			log.info("添加上报信息出错", e);
		}
		return "error";
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "");
		model.addAttribute("url", "");
		model.addAttribute("type", "sj");
		return "shgl/choseArea";
	}
}
