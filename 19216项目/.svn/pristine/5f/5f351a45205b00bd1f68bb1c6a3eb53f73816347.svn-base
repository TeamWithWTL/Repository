/**
 * 
 */
package com.jcwx.action.xtbg;

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

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.service.pub.DeptPersonService;
import com.jcwx.service.xtbg.TzggRyService;
import com.jcwx.service.xtbg.TzggService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author m
 *
 */

@Controller
@RequestMapping("xtbg/tzgg")
public class TzggAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(TzggAction.class);
	
	
	@Autowired
	private TzggService tzggService;
	
	@Autowired
	private DeptPersonService departmentPersonelService;
	
	@Autowired
	private TzggRyService tzggRyService;
	
	@Autowired
	private YhglService yhglService;
	
	
	
	/**
	 * 跳转到通知公告首页
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createDate,HttpSession session){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
				model.addAttribute("roleCode", sysAccCount.getRole_code());
				model.addAttribute("accCode", sysAccCount.getAccCode());
				
			try {
				model.addAttribute("title", title);
				model.addAttribute("createDate",createDate);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("title",title);
				cxMap.put("createDate", createDate);
				Pagenate<TzggEntity> pagenate = tzggService.getTzggContent(pageNumber, pageSize, cxMap);
				model.addAttribute("pagenate", pagenate);
				return "xtbg/tzgg/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询通知公告出错。",e);
		}
		return "xtbg/tzgg/index";
	
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
		log.info("通知公告信息ID：" + id);
		try {
			TzggEntity tzgg = null;
			if(id != null && !id.equals("")){
				tzgg = tzggService.findById(id);
			}
			model.addAttribute("tzgg", tzgg);
		} catch (Exception e) {
			log.error("查看通知公告详情出错：",e);
		}
		return "xtbg/tzgg/view";
	}
	
	
	/**
	 * 跳转到新增修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model,String id,String code){
		try {
			TzggEntity tzgg = null;
			if(id != null && !id.equals("")){
				tzgg = tzggService.findById(id);
			}
			model.addAttribute("tzgg", tzgg);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "xtbg/tzgg/index_addEdit";  
	}
	
	/**
	 * 保存
	
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(TzggEntity tzgg, String id, String fName, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			TzggEntity d = tzggService.getById(id);
			if(d == null){
				//新增
				tzgg.setCreateUserId(sysAccCount.getAccCode());
				tzgg.setCreateUserName(sysAccCount.getName());
				tzggService.save(tzgg, fName);
				//添加接收人
				Map<String,String> map = new HashMap<String,String>();
				map.put("zh_type", Consts.USER_TYPE_DEPT);
				List<SysAccCount>  receiveList = yhglService.findByParam(map); ///查询suo
				for(int i = 0;i< receiveList.size();i++){
					SysAccCount userInfo = receiveList.get(i);
					TzggRyEntity tzggRy = new TzggRyEntity();
					tzggRy.setReceivce_name(userInfo.getName());
					tzggRy.setReceivce_id(userInfo.getAccCode());
					tzggRy.setTzgg_id(tzgg.getId());
					tzggRyService.save(tzggRy);
					
				}
			}else{
				//修改
				tzggService.update(tzgg, id, fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存通知公告信息出错：",e);
		}
		return "error";
	}
	
	
	/**
	 * 删除附件
	 * @param fjId --附件ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delFj")
	public String delFj(String id, String fName){
		try {
			log.info("要删除的附件ID：" + id);
			tzggService.delFj(id);
			return "success";
		} catch (Exception e) {
			log.error("删除中心信息附件出错：", e);
		}
		return "fail";
	}
	
	
	/**
	 * 删除通知公告信息
	 * @param ids --政务信息ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			tzggService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除通知公告信息出错：", e);
		}
		return "fail";
	}
	
	
	//跳转人员选择
	@RequestMapping("/tree")
	public String toTree(String id,Model mod,HttpSession session){
 		String[] split = id.split(",");
		TzggEntity tzggEntity = tzggService.findById(split[0]);
		//mod.addAttribute("complainEntity",complainEntity);
		session.setAttribute("tzggEntity", tzggEntity);
		mod.addAttribute("pagenate", null);
		return "xtbg/tzgg/deptTree";
	}

	
	//加载人员
	@RequestMapping("/treeIndex")
	public String toIndex(String name, String ajaxCmd, String deptId, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,
			HttpSession session, Model model){
		
		model.addAttribute("name", name);
		
		if(ajaxCmd == null){
			model.addAttribute("pagenate", null);
		}else{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("name", name);
			paramsMap.put("deptId", deptId);
			Pagenate<SysAccCountLazy> pagenate = departmentPersonelService.findByPage(pageNumber, pageSize, paramsMap);			model.addAttribute("pagenate", pagenate);
			model.addAttribute("pagenate", pagenate);
			return "xtbg/tzgg/deptTree#" + ajaxCmd;
		}
		
		return "xtbg/tzgg/deptTree";
	}
	
	//保存
	@RequestMapping("/payOut")
	public String saveZhuan(String pname,String pid,String tid,String fName){
		System.out.println("A"+pid);
		System.out.println("B"+pname);
		System.out.println("C"+tid);
		TzggEntity tzgg = tzggService.findById(pname);
		System.out.println("测试"+tzgg);
		tzgg.setStatus("0");
		tzggService.update(tzgg, pname, fName);
		tzggRyService.saveOrUpdate(pname,pid,tid);
		
		return "success";
		
	}
	
}
