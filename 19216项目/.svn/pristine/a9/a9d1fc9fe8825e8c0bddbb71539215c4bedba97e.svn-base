package com.jcwx.action.xtbg.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.xtbg.MeetingAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.xtbg.DocumentService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;


/**
 * app公文处理
 * @author jiangkia
 *
 */
@Controller
@RequestMapping("app/xtbg/gwcl")
public class AppDocumentAction {

	private Logger log =Logger.getLogger(MeetingAction.class);
	private static int pageSize=Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private DocumentService docService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private YhglService yhglService;
	
	
	/**
	 * 查看公文
	 * @param model
	 * @param req
	 * @param id 公文ID
	 * @return
	 */
	@RequestMapping("/goViewDoc")
	public String goViewDoc(Model model,HttpServletRequest req,String id){
		Document doc  = docService.findDocById(id);
		model.addAttribute("doc", doc);
		String path = ProjectUtils.getSysCfg("appDownloadPath");
		model.addAttribute("path", path);
		return "/xtbg/gwcl/app/app_gwcl_view";
	}
	
	
	
	/**
	 *  我接收的公文
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/myreceive")
	public String myReceive(Model model,String ajaxCmd,HttpServletRequest req,@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,String title){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("pageCnts", 0);
				model.addAttribute("pageSize", 0);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("clrId", accCode);
				map.put("title", title);
				Pagenate<DocumentDeal> pagenate = docService.findDocumentDealByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				return "/xtbg/gwcl/app/app_gwcl_myreceive#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "/xtbg/gwcl/app/app_gwcl_myreceive";
	}
	/**
	 * app分页查询 上报
	 */
	@ResponseBody
	@RequestMapping("/appGetMoreReceive")
	public Map appGetMorePage(Model model, HttpServletRequest request, HttpServletResponse response,
			String ajaxCmd, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
		SysAccCount acc = (SysAccCount) request.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		Map<String, String> map = new HashMap<String, String>();
		map.put("clrId", accCode);
		Pagenate<DocumentDeal> pagenate = docService.findDocumentDealByPage(pageNumber, pageSize, map);
		List<DocumentDeal>  dealList  = pagenate.getList();
		List<Map> dateList = new ArrayList<Map>();

		for (int i = 0; i < dealList.size(); i++) {
			Map data = new HashMap();
			DocumentDeal  deal =  dealList.get(i);
			data.put("id",  deal.getId());
			data.put("status",  deal.getStatus());
			data.put("docId",  deal.getDocument().getId());
			data.put("title",  deal.getDocument().getId());
			data.put("showDate", deal.getDocument().getCreateDatesh());
			dateList.add(data);
		}
		
		Map<Object, Object> valMap = new HashMap<Object, Object>();
		valMap.put("data", dateList);
		return valMap;
	}
}
