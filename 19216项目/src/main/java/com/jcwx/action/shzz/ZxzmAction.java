package com.jcwx.action.shzz;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.service.shzz.ZxzmService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author xushu
 * 	2017年10月24日
 * 	在线招募
 */
@Controller
@RequestMapping("/shzz/zxzm")
public class ZxzmAction {
	
	private Logger log = Logger.getLogger(ZxzmAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	
	@Autowired
	private ZxzmService zxzmService;
	
	/**
	 * 跳转到在线招募信息首页
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String ShzzxxContent(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String title,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCode = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCode);
		
		try{
		  if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				Pagenate<ZxzmEntity> pagenate = zxzmService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("title", title);
				model.addAttribute("pagenate", pagenate);
				return "shzz/zxzm/index#" + ajaxCmd;
			}
		}catch(Exception e){
			log.error("查询在线招募出错", e);
		}
		return "shzz/zxzm/index";
	}
	
	/**
	 * 跳转到新增/修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model , String id){
		log.info("在线招募ID：" + id);
		try {
			ZxzmEntity zxzm = null;
			if(id != null && !id.equals("")){
				zxzm = zxzmService.getById(id);
			}
			model.addAttribute("zxzm", zxzm);
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shzz/zxzm/addEdit";
	}
	
	/**
	 * 保存在线招募
	 * @param id
	 * @param zxzm
	 * @param fName
	 * @param session
	 * @return
	 */
	@RequestMapping("/addzxzm")
	@ResponseBody
	public String addzxzm(String id, ZxzmEntity zxzm, String fName, HttpSession session){
		//获取发布人信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();            //发布人id
		String name  = sysAccCount.getName(); //发布人姓名
		try {
			ZxzmEntity ze = zxzmService.getById(id);
			if(ze == null){
				zxzmService.save(zxzm, fName, accCode, name);
			}else{
				zxzmService.update(zxzm, id,fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("添加在线招募信息出错",e);
		}
		return "error";
	}
	
	/**
	 * 删除附件信息
	 * @param fjId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delFj")
	public String delFj(String fjId){
		try {
			log.info("要删除的附件ID：" + fjId);
			zxzmService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除在线招募附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除在线招募
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			zxzmService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除在线招募出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 查看在线招募详情
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("在线招募ID：" + id);
		try {
			ZxzmEntity zxzm = null;
			if(id != null && !id.equals("")){
				zxzm = zxzmService.getById(id);
			}
			model.addAttribute("zxzm", zxzm);
		} catch (Exception e) {
			log.error("查看在线招募详情出错：",e);
		}
		return "shzz/zxzm/view";
	}
	
	/**
	 * 审核
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goSh")
	public String goSh(Model model , String id,HttpSession session){
		log.info("在线招募ID：" + id);
		//获取审核人信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String name  = sysAccCount.getName(); //审核人姓名
		try {
			ZxzmEntity zxzm = null;
			if(id != null && !id.equals("")){
				zxzm = zxzmService.getById(id);
			}
			model.addAttribute("zxzm", zxzm);
			model.addAttribute("name", name);
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("跳转审核页面出错：",e);
		}
		return "shzz/zxzm/Sh";
	}
	
	@RequestMapping("/saveSh")
	@ResponseBody
	public String saveSh(String id ,String sh_status, HttpSession session){
		//获取审核人信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();            //审核人id
		String name  = sysAccCount.getName(); //审核人姓名
		try {
			zxzmService.saveSh(id,sh_status,accCode,name);
			if ("1".equals(sh_status)) {//审核通过
				return "success";
			}
			if ("2".equals(sh_status)) {//审核不通过
				return "pass";
			}
		} catch (Exception e) {
			log.error("保存审核结果出错：", e);
		}
		return "fail";
	}
}
