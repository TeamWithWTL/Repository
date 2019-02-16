package com.jcwx.action.shfw;

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
import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.service.shfw.ZwxxService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 社会服务-政务信息 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/shfw/zwxx")
public class ZwxxAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(SqfwAction.class);
	
	@Autowired
	private ZwxxService zwxxService;
	
	/**
	 * 首页列表展示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleCode = sysAccCount.getRole_code();
		model.addAttribute("roleCode", roleCode);
		model.addAttribute("accCode", sysAccCount.getAccCode());
		try {
			model.addAttribute("title", title);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				Pagenate<ShfwZwxxEntity> pagenate = zwxxService.findByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/zwxx/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询政务信息列表出错", e);
		}
		return "shfw/zwxx/index";
	}
	
	/**
	 * 跳转查看页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("政务信息ID：" + id);
		try {
			ShfwZwxxEntity zwxx = null;
			if(id != null && !id.equals("")){
				zwxx = zwxxService.getById(id);
			}
			model.addAttribute("zwxx", zwxx);
		} catch (Exception e) {
			log.error("查看政务信息详情出错：",e);
		}
		return "shfw/zwxx/view";
	}
	
	/**
	 * 跳转审核页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSh")
	public String goSh(HttpSession session, String id, Model model) {
		log.info("政务信息ID：" + id);
		try {
			ShfwZwxxEntity zwxx = null;
			if(id != null && !id.equals("")){
				zwxx = zwxxService.getById(id);
			}
			model.addAttribute("zwxx", zwxx);
		} catch (Exception e) {
			log.error("跳转政务信息审核页面出错：",e);
		}
		return "shfw/zwxx/sh_index";
	}
	
	/**
	 * 跳转添加及修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model, String id){
		log.info("政务信息ID：" + id);
		try {
			ShfwZwxxEntity zwxx = null;
			if(id != null && !id.equals("")){
				zwxx = zwxxService.getById(id);
			}
			model.addAttribute("zwxx", zwxx);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shfw/zwxx/addEdit";
	}
	
	/**
	 * 保存政务信息
	 * @param zwxx
	 * @param id
	 * @param fName
	 * @param tjStatus
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(ShfwZwxxEntity zwxx, String id, String fName, String tjStatus, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			ShfwZwxxEntity z = zwxxService.getById(id);
			if(z == null){
				//新增
				zwxx.setUserId(sysAccCount.getAccCode());
				zwxx.setUserName(sysAccCount.getName());
				zwxxService.save(zwxx, fName, tjStatus);
			}else{
				//修改
				zwxxService.update(zwxx, id, fName, tjStatus);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存政务信息出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 审核政务信息
	 * @param ids --政务信息ID
	 * @param session
	 * @return
	 */
	@RequestMapping("/doAuditing")
	@ResponseBody
	public String doAuditing(String ids, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			//zwxxService.shStatus(ids);
			String id[] = ids.split(";");
			for(int i=0; i<id.length; i++){
				ShfwZwxxEntity zwxx = zwxxService.getById(id[i]);
				if(zwxx!=null){
					zwxx.setShStatus("1");
					zwxx.setShUserId(sysAccCount.getAccCode());
					zwxx.setShUserName(sysAccCount.getName());
					zwxxService.shZwStatus(zwxx);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("审核政务信息出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除政务信息
	 * @param ids --政务信息ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			zwxxService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除政务信息出错：", e);
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
			zwxxService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除政务信息附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核政务信息
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/auditing")
	public String auditing(String id, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		try {
			ShfwZwxxEntity zw = zwxxService.getById(id);
			zw.setShStatus("1");
			zw.setShUserId(sysAccCount.getAccCode());
			zw.setShUserName(sysAccCount.getName());
			zwxxService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("审核政务信息出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 反审核政务信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/antiaudit")
	public String antiaudit(String id){
		try {
			ShfwZwxxEntity zw = zwxxService.getById(id);
			zw.setShStatus("0");
			zwxxService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("反审核政务信息出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 设置审核状态
	 * @param id
	 * @param flag	状态标志
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setSh")
	public String setSh(String id,String flag){
		try {
			zwxxService.shZt(id, flag);
			return "success";
		} catch (Exception e) {
			log.error("审核政务信息出错：", e);
		}
		return "fail";
	}
	
}
