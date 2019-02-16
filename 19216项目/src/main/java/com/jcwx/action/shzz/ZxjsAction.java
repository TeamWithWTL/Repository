package com.jcwx.action.shzz;

import java.util.HashMap;
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
import com.jcwx.entity.shzz.ZxjsEntity;
import com.jcwx.service.shzz.ZxjsService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 2017年10月21日
 * 社会组织-中心介绍
 */
@Controller
@RequestMapping("/shzz/zxjs")
public class ZxjsAction {
	
	private Logger log = Logger.getLogger(ZxjsAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	
	@Autowired
	private ZxjsService zxjsService;
	
				
	/**
	 * 跳转到中心介绍首页
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTimes,HttpSession session){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
//				model.addAttribute("roleCode", sysAccCount.getRole_code());
//				model.addAttribute("accCode", sysAccCount.getAccCode());
				String accCode = sysAccCount.getAccCode();
				String roleCode = sysAccCount.getRole_code();
				model.addAttribute("roleCode", roleCode);
			try {
				model.addAttribute("title", title);
				model.addAttribute("createTimes",createTimes);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("title",title);
				cxMap.put("createTimes", createTimes);
				cxMap.put("roleCode", roleCode);
				Pagenate<ZxjsEntity> pagenate = zxjsService.getZxjsContent(pageNumber, pageSize, cxMap);
				model.addAttribute("pagenate", pagenate);
				return "shzz/zxjs/index_zxjs#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询中心介绍出错。");
		}
		return "shzz/zxjs/index_zxjs";
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
		log.info("中心介绍信息ID：" + id);
		try {
			ZxjsEntity zxjs = null;
			if(id != null && !id.equals("")){
				zxjs = zxjsService.getById(id);
			}
			model.addAttribute("zxjs", zxjs);
		} catch (Exception e) {
			log.info("查看政务信息详情出错：",e);
		}
		return "shzz/zxjs/view";
	}
	/**
	 * 跳转到新增修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model,String id){
		try {
			ZxjsEntity zxjs = null;
			if(id != null && !id.equals("")){
				zxjs = zxjsService.getById(id);
			}
			model.addAttribute("zxjs", zxjs);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shzz/zxjs/index_addEdit";
	}
	/**
	 * 保存
	 * @param zxjs
	 * @param id
	 * @param fName
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(Model model,ZxjsEntity zxjs, String id, String fName, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleCode = sysAccCount.getRole_code();
		model.addAttribute("roleCode", roleCode);
		
		try {
			ZxjsEntity z = zxjsService.getById(id);
			if(z == null){
				//新增
				zxjs.setUserId(sysAccCount.getAccCode());
				zxjs.setUserName(sysAccCount.getName());
				zxjsService.save(zxjs, fName);
			}else{
				//修改
				zxjsService.update(zxjs, id, fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存中心介绍信息出错：",e);
		}
		return "error";
	}
	
	/**
	 * 审核中心介绍信息
	 * @param ids --中心介绍信息ID
	 * @return
	 */
	@RequestMapping("/doAuditing")
	@ResponseBody
	public String doAuditing(String ids, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		
		try {
//			zxjsService.shStatus(ids);
			String id[] = ids.split(";");
			for(int i=0; i<id.length; i++){
				ZxjsEntity zxjs = zxjsService.getById(id[i]);
				if(zxjs!=null){
					zxjs.setShStatus("1");
					zxjs.setShUserId(sysAccCount.getAccCode());
					zxjs.setShUserName(sysAccCount.getName());
					zxjsService.shZxStatus(zxjs);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("审核中心介绍信息出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 反审核中心介绍信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/antiaudit")
	public String antiaudit(String id){
		try {
			ZxjsEntity zx = zxjsService.getById(id);
			zx.setShStatus("2");//不同过
			zxjsService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("反审核中心介绍出错：", e);
		}
		return "fail";
	}
	
	
	/**
	 * 删除中心介绍信息
	 * @param ids --政务信息ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			zxjsService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除中心介绍信息出错：", e);
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
			zxjsService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除中心信息附件出错：", e);
		}
		return "fail";
	}
	

	
	/**
	 * 跳转审核界面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/goSh")
	public String goSh(String id, Model model) {
		log.info("中心介绍信息ID：" + id);
		try {
			ZxjsEntity zxjs = null;
			if(id != null && !id.equals("")){
				zxjs = zxjsService.getById(id);
			}
			model.addAttribute("zxjs", zxjs);
		} catch (Exception e) {
			log.error("跳转中心介绍审核页面出错：",e);
		}
		return "shzz/zxjs/viewAudit";
	}
	
	/**
	 * 
	 * @param id
	 * @param flag 状态标志
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/setSh")
	public String setSh(String id,String flag,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			zxjsService.shZt(id, flag,acc);
			return "success";
		} catch (Exception e) {
			log.error("审核中心介绍信息出错：", e);
		}
		return "fail";
	}
}
