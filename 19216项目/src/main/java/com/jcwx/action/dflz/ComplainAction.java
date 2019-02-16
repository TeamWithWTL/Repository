package com.jcwx.action.dflz;

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

import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.service.dflz.ComplainHandleService;
import com.jcwx.service.dflz.ComplainService;
import com.jcwx.service.pub.DepartmentService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.HtmlUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
/**
 * 投诉举报
 * @author 李伟
 * @time 2017年10月26日下午1:15:17
 */
@Controller
@RequestMapping("/dflz/tsjb")
public class ComplainAction {
		
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(ComplainAction.class);
	
	@Autowired
	private ComplainService complainService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ComplainHandleService complainHandleService;
	@Autowired
	private YhglService yhglService;
	/***
	 * 主页加载
	 * @author 李伟
	 * @time 2017年10月26日下午1:31:11
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber 分页
	 * @param title 查询条件
	 * @param applyTime 查询条件
	 * @param status 1为处理 2 未处理的
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,String status, String title, String applyTime,
			HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();//角色编码
		String accCode = acc.getAccCode();
		model.addAttribute("status",status);
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//回显页面 角色编码
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
				map.put("status", status);
				Pagenate<ComplainHandleEntity> pagenate = complainService.findComPHandByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "dflz/tsjb/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询投诉举报信息出错", e);
		}
		return "dflz/tsjb/index";
	}
	
	/**
	 * 查看投诉
	 * @author 李伟
	 * @time 2017年10月26日下午2:02:21
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		
		ComplainEntity complainEntity = complainService.findById(id);//查询
		ComplainHandleEntity complainHandleEntity=complainService.findYjById(id);
		if (complainHandleEntity!=null) {
			String acc_code = complainHandleEntity.getAcc_code();//处理人
			SysAccCount sysAccCount=complainService.findByAccCode(acc_code);//查询处理人
			model.addAttribute("handleName",sysAccCount.getName());
			model.addAttribute("complainHandleEntity",complainHandleEntity);
		}
		complainEntity.setContent(HtmlUtil.htmlRemoveTag(complainEntity.getContent()));
		
		String jb_typeId = complainEntity.getJb_type();//投诉类型id
		String[] jb_type = jb_typeId.split(",");
		SysParamDesc sysParamDesc=complainService.findParamDescById(jb_typeId);//查询投诉类型实体
		SysParam sysParam=complainService.findParamById(jb_type[0]);//查询投诉类型父类
		complainEntity.setContent(HtmlUtil.htmlRemoveTag(complainEntity.getContent()));
		model.addAttribute("sysParam",sysParam);//回显一级投诉类型实体
		model.addAttribute("sysParamDesc",sysParamDesc);//回显二级投诉类型实体
		
		
		model.addAttribute("complainEntity",complainEntity);
		return  "dflz/tsjb/view";
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			complainService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除投诉举报信息出错：", e);
		}
		return "fail";
	}
	/**
	 * 跳转审核页面
	 * @author 李伟
	 * @time 2017年11月6日上午9:07:15
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goViewAudit")
	public String goViewAudit(String id,Model model){
		ComplainEntity complainEntity = complainService.findById(id);//根据ID查询记录
		ComplainHandleEntity complainHandleEntity=complainService.findYjById(id);
		if (complainHandleEntity!=null) {
			complainHandleEntity.setContent(HtmlUtil.htmlRemoveTag(complainHandleEntity.getContent()));
			model.addAttribute("complainHandleEntity",complainHandleEntity);
		}
		complainEntity.setContent(HtmlUtil.htmlRemoveTag(complainEntity.getContent()));
		
		String jb_typeId = complainEntity.getJb_type();//投诉类型id
		String[] jb_type = jb_typeId.split(",");
		SysParamDesc sysParamDesc=complainService.findParamDescById(jb_typeId);//查询投诉类型实体
		SysParam sysParam=complainService.findParamById(jb_type[0]);//查询投诉类型父类
		complainEntity.setContent(HtmlUtil.htmlRemoveTag(complainEntity.getContent()));
		model.addAttribute("sysParam",sysParam);//回显一级投诉类型实体
		model.addAttribute("sysParamDesc",sysParamDesc);//回显二级投诉类型实体
		
		model.addAttribute("complainEntity",complainEntity);
		return  "dflz/tsjb/viewAudit";
	}
	
	/**
	 * 审核
	 * @param id
	 * @param status  审核状态 1为通过，2为不通过
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public String audit(String id,String status,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			complainService.audit(id,status,acc);
			if ("1".equals(status)) {//1为通过
				return "success";
			}else if ("2".equals(status)) {//2为不通过
				return "succes";
			}
		} catch ( Exception e) {
			log.error("审核出错：", e);
		}
		return "fail";
	}
	/**
	 * 跳转部门人员选择
	 * @author 李伟
	 * @time 2017年11月21日上午9:31:55
	 * @param model
	 * @param req
	 * @param id
	 * @return
	 */
	@RequestMapping("/goTransView")
	public String goTransView(Model model,HttpServletRequest req,String id){
		ComplainEntity doc  = complainService.findById(id);
		model.addAttribute("doc", doc);
		return "/dflz/tsjb/transView";
	}

	/**
	 * 加载部门人员列表
	 * @author 李伟
	 * @time 2017年11月21日上午9:31:42
	 * @param model
	 * @param jsrIds  已被选的人员id字符串
	 * @param docId  待要转发的id
	 * @return
	 */
	@RequestMapping("/showTree")
	public String showTree(Model model,String jsrIds,String docId){
		model.addAttribute("jsrIds", jsrIds);
		model.addAttribute("docId", docId);
		return "/dflz/tsjb/membertree";
	}

	
	/**
	 * 转发
	 * @author 李伟
	 * @time 2017年10月30日下午1:36:04
	 * @param pid 转发人员id
	 * @param cid 投诉实体类id
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/payOut")
	public String payOut(String pid,String cid,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			try {
				complainHandleService.saveOrUpdate(pid,cid,acc);
				return "success";//成功返回
			} catch (Exception e) {
				log.error("转发出错：", e);
			}
			return "fail"; //出错返回
	}
	/**
	 * 确认投诉结果
	 * @author 李伟
	 * @time 2017年11月10日上午10:43:58
	 * @param id
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/handle")
	public String handle(String id,HttpServletRequest req,String content){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("tsjb_id", id);
			map.put("accCode", accCode);
			map.put("content", content);
			complainHandleService.handle(map);
			return "success";//成功返回
		} catch (Exception e) {
			log.error("complainAction类 handle方法，确认结果出错",e);
		}
		return "fail";
	}
	/**
	 * 跳转意见处理
	 * @author 李伟
	 * @time 2017年11月13日上午12:13:24
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goDealPage")
	public String goDealPage(Model model,String id){
		model.addAttribute("dealId", id);
		return "/dflz/tsjb/deal";
	}
	
	/**
	 * 加载人员树
	 *
	 * @param jsrIds  已被选的人员id字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadTreeData", produces="text/html;charset=UTF-8")
	public String loadTreeData(String jsrIds){
		log.info("jsrIds:"+jsrIds);
		// 组织结构节点列表
	/*	Document doc =null;
		List<DocumentDeal> dealList = null;*/
	
		List<String> jsrList = new ArrayList<String>();
		
		List<SysAccRole> dfList = complainService.findByDflzYhList("10");//查询所有党风廉政人员
		if(dfList != null ){
			for (int i = 0; i < dfList.size(); i++) {
				jsrList.add(dfList.get(i).getAccCode());
			}
		}
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
			//查询部门结构
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
