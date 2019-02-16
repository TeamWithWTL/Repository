package com.jcwx.action.xtbg;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.xtbg.DocumentService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 公文处理Action
 * @author jiangkia
 *
 */
@Controller
@RequestMapping("xtbg/gwcl")
public class DocumentAction {

	private Logger log =Logger.getLogger(MeetingAction.class);
	private static int pageSize=Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private DocumentService docService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private YhglService yhglService;
	
	/**
	 * 添加发文
	 * @param model
	 * @param title  标题
	 * @param num 文号
	 * @param level 密级
	 * @param dept 发文部门
	 * @param nwrSuggest 拟文人意见
	 * @param fName 附件路径
	 * @param jsrIds 接收人IDs
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addFw")
	public String addFw(HttpSession session,Model model, String title,String num,String level,String dept,String nwrSuggest,String fName,String jsrIds,HttpServletRequest req) {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		Document doc = new Document();
		doc.setType(Consts.DOC_TYPE_FW);//1发文,2收文
		doc.setFwStep("1");//发文步骤（1下发党政办负责人审批，2领导审批结束）
		doc.setTitle(title);
		doc.setNum(num);
		doc.setLevel(level);
		doc.setDept(dept);
		doc.setNwrSuggest(nwrSuggest);
		doc.setCreateDate(new Date());
		doc.setCreateUserId(sysAccCount.getAccCode());
		doc.setCreateUserName(sysAccCount.getName());
		docService.saveDoc(doc,fName,jsrIds);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/addSw")
	public String addSw(HttpSession session,Model model, String title,String num,String level,String dept,String nwrSuggest,String fName,String jsrIds,String endDate,String jkStatus,HttpServletRequest req) throws ParseException {
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		Document doc = new Document();
		doc.setType(Consts.DOC_TYPE_SW);//1发文,2收文
		doc.setSwStep("1");//发文步骤（1下发党政办负责人审批，2领导审批结束）
		doc.setTitle(title);
		doc.setNum(num);
		doc.setLevel(level);
		doc.setDept(dept);
		doc.setJkStatus(jkStatus);// 紧急程度 1 普通 2 加急 
		doc.setEndDate(format.parse(endDate));
		doc.setNwrSuggest(nwrSuggest);
		doc.setCreateDate(new Date());
		doc.setCreateUserId(sysAccCount.getAccCode());
		doc.setCreateUserName(sysAccCount.getName());
		docService.saveDoc(doc,fName,jsrIds);
		return "success";
	}
	/**
	 * 查询我的发起的公文
	 * @param model
	 * @param ajaxCmd 
	 * @param req
	 * @param pageNumber
	 * @param title 标题
	 * @return
	 */
	@RequestMapping("/mydoc")
	public String myDoc(Model model,String ajaxCmd,HttpServletRequest req,@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,String title){
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
				map.put("createUserId", accCode);
				map.put("title", title);
				Pagenate<Document> pagenate = docService.findDocumentByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "/xtbg/gwcl/mydoc#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "/xtbg/gwcl/mydoc";
	}
	
	
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
		return "/xtbg/gwcl/viewDoc";
	}
	
	/**
	 *删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			docService.deleteDoc(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除出错：", e);

		}
		return "fail";//出错返回
	}
	/**跳转 转发界面
	 * @param model
	 * @param req
	 * @param id 公文ID
	 * @return
	 */
	@RequestMapping("/goTransView")
	public String goTransView(Model model,HttpServletRequest req,String id){
		Document doc  = docService.findDocById(id);
		model.addAttribute("doc", doc);
		return "/xtbg/gwcl/transView";
	}
	/**
	 * 转发
	 * @param model
	 * @param req
	 * @param docId  公文ID
	 * @param jsrIds  接收人ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doTrans")
	public String doTrans(Model model,HttpServletRequest req,String docId,String jsrIds){
		Document doc  = docService.findDocById(docId);
		
		if(jsrIds !=null && !"".equals(jsrIds)){
			String[] clrArray = jsrIds.split(";");
			for(String clrId : clrArray){
				if("".equals(clrId)){
					continue;
				}
				DocumentDeal deal  = new DocumentDeal();
				deal.setDocument(doc);
				deal.setClrId(clrId);
				deal.setDocument(doc);
				deal.setZfrId(doc.getCreateUserId());
				deal.setZfrName(doc.getCreateUserName());
				deal.setStatus("0");//0 未处理  1 已处理
				deal.setCreateDate(new Date());
				docService.updateDocDeal(deal);
				}
			}
		//  如果党政办处理完 指给街道办处理
		if("2".equals(doc.getSwStep())){
			doc.setSwStep("3");
			docService.updateDoc(doc);
		}
		return "success";
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
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("clrId", accCode);
				map.put("title", title);
				Pagenate<DocumentDeal> pagenate = docService.findDocumentDealByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "/xtbg/gwcl/myreceive#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("[EventAction]_[myevent_]查询事件信息出错", e);
		}
		return "/xtbg/gwcl/myreceive";
	}
	
	/**
	 * 添加发文
	 * @param model
	 * @return
	 */
	@RequestMapping("/addFwDocPage")
	public String addFwDocPage(Model model){
		return "/xtbg/gwcl/add_fw";
	}
	/**
	 * 添加 收文
	 * @param model
	 * @return
	 */
	@RequestMapping("/addSwDocPage")
	public String addSwDocPage(Model model){
		return "/xtbg/gwcl/add_sw";
	}
	
	
	/**
	 * 跳转处理界面
	 * @param model
	 * @param dealId
	 * @return
	 */
	@RequestMapping("/goDealPage")
	public String goDealPage(Model model,String dealId){
//		DocumentDeal deal = docService.findDealById(dealId);
//		if(deal !=null){
//			model.addAttribute("docId", deal.getDocument().getId());
//		}else{
//			model.addAttribute("docId", "");
//		}
		model.addAttribute("dealId", dealId);
		return "/xtbg/gwcl/deal";
	}
	
	/**
	 * 填写意见
	 * @param model
	 * @param dealId
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDeal")
	public String saveDeal(Model model,HttpServletRequest req,String dealId,String content){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		DocumentDeal docDeal  = docService.findDealById(dealId);
		if(docDeal == null){
			return "error";
		}
		Document doc = docDeal.getDocument();
		String type =doc.getType();
		//发文
		if(Consts.DOC_TYPE_FW.equals(type)){
			String step  = doc.getFwStep();
			//党政办处理
			if("1".equals(step)){
				doc.setFwStep("2");
				doc.setLdSuggest(content);
				docService.updateDoc(doc);
				
				docDeal.setClrId(acc.getAccCode());
				docDeal.setClrName(acc.getName());
				docDeal.setStatus("1");// 0  待处理 ,1  已处理
				docService.updateDocDeal(docDeal);
			}
			// 党政办处理结束  转发 制鞋意见
			if("2".equals(step)){
				docDeal.setClrId(acc.getAccCode());
				docDeal.setClrName(acc.getName());
				docDeal.setStatus("1");// 0  待处理 ,1  已处理
				docDeal.setContent(content);
				docService.updateDocDeal(docDeal);
			}
		}
		
		// 收文
		if(Consts.DOC_TYPE_SW.equals(type)){
			String step  = doc.getSwStep();
			//党政办处理
			if("1".equals(step)){
				doc.setSwStep("2");
				doc.setDzbSuggest(content);
				docService.updateDoc(doc);
				
				docDeal.setClrId(acc.getAccCode());
				docDeal.setClrName(acc.getName());
				docDeal.setStatus("1");// 0  待处理 ,1  已处理
				docService.updateDocDeal(docDeal);
			}
			// 街道办处理
			if("3".equals(step)){
				doc.setSwStep("4");
				doc.setJdbSuggest(content);
				docService.updateDoc(doc);
				
				docDeal.setClrId(acc.getAccCode());
				docDeal.setClrName(acc.getName());
				docDeal.setStatus("1");// 0  待处理 ,1  已处理
				docService.updateDocDeal(docDeal);
			}
			// 党政办处理结束  转发 制鞋意见
			if("4".equals(step)){
				docDeal.setClrId(acc.getAccCode());
				docDeal.setClrName(acc.getName());
				docDeal.setStatus("1");// 0  待处理 ,1  已处理
				docDeal.setContent(content);
				docService.updateDocDeal(docDeal);
			}
		}
		
		return "success";
	}
	
	
	/**
	 * 查看人员树
	 * @param model
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/showTree")
	public String showTree(Model model,String deptId,String jsrIds,String docId){

		model.addAttribute("deptId", deptId);
		model.addAttribute("jsrIds", jsrIds);
		model.addAttribute("docId", docId);
		return "/xtbg/gwcl/membertree";
	}
	
	
	/**
	 * 加载人员树
	 * @param deptId
	 * @param jsrIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadTreeData", produces="text/html;charset=UTF-8")
	public String loadTreeData(String deptId,String jsrIds,String docId){
		log.info("jsrIds:"+jsrIds);
		// 组织结构节点列表
		Document doc =null;
		List<DocumentDeal> dealList = null;
		if(docId !=null && !"".equals(docId)){
			 doc = docService.findDocById(docId);
			 dealList = doc.getDeals();
		}
		List<String> jsrList = new ArrayList<String>();
		if(dealList != null ){
			for (int i = 0; i < dealList.size(); i++) {
				jsrList.add(dealList.get(i).getClrId());
			}
		}
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
		if(deptId != null && !"".equals(deptId)){
			SysDepartment  department= departmentService.findById(deptId);
			String code = department.getDeptId();
			String name = department.getDeptName();
			String pid = department.getParentId();
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put("id", code);
			nodeMap.put("name", name);
			nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
			nodeMap.put("nocheck", "true");
			nodeList.add(nodeMap);
			
			//添加成员
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("deptId", deptId);
			List<SysAccCount> userlist = yhglService.findByParam(paramMap);
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, String> userMap = new HashMap<String, String>();
				SysAccCount te = userlist.get(i);
				userMap.put("id", te.getAccCode());
				userMap.put("name", te.getName());
				
				if(jsrList.contains(te.getAccCode())){
					userMap.put("chkDisabled", "true");
				}
				if(jsrIds != null && jsrIds.length()>0){
					if(jsrIds.indexOf(te.getAccCode())>=0){
						userMap.put("checked", "true");
					}
				}
				userMap.put("pId", te.getDeptId());
				nodeList.add(userMap);
				
			}
			
		}else{
			//查询部门结构
			Map<String, String> params = new HashMap<String, String>();
			params.put("deptId", deptId);
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
				if(jsrList.contains(te.getAccCode())){
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
