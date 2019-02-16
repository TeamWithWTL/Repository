package com.jcwx.action.shfw;

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

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shfw.ShfwSqfwEntity;
import com.jcwx.service.shfw.SqfwService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 社会服务-社区服务 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/shfw/sqfw")
public class SqfwAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(SqfwAction.class);
	
	@Autowired
	private SqfwService sqfwService;
	@Autowired
	private SjzdService sjzdService;
	
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
				Pagenate<ShfwSqfwEntity> pagenate = sqfwService.findByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/sqfw/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询社区服务列表出错", e);
		}
		return "shfw/sqfw/index";
	}
	
	/**
	 * 跳转查看页面
	 * @param session
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, Model model, String id) {
		log.info("社区服务ID：" + id);
		try {
			ShfwSqfwEntity sqfw = null;
			if(id != null && !id.equals("")){
				sqfw = sqfwService.getById(id);
			}
			model.addAttribute("sqfw", sqfw);
		} catch (Exception e) {
			log.error("查看社区服务详情出错：",e);
		}
		return "shfw/sqfw/view";
	}
	
	/**
	 * 跳转审核页面
	 * @param session
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goSh")
	public String goSh(HttpSession session, Model model, String id) {
		log.info("社区服务ID：" + id);
		try {
			ShfwSqfwEntity sqfw = null;
			if(id != null && !id.equals("")){
				sqfw = sqfwService.getById(id);
			}
			model.addAttribute("sqfw", sqfw);
		} catch (Exception e) {
			log.error("跳转社区服务审核页面出错：",e);
		}
		return "shfw/sqfw/sh_index";
	}
	
	/**
	 * 跳转添加及修改页面
	 * @param model
	 * @param id
	 * @param code
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model, String id, String code){
		log.info("社区服务ID：" + id);
		try {
			ShfwSqfwEntity sqfw = null;
			if(id != null && !id.equals("")){
				sqfw = sqfwService.getById(id);
			}
			model.addAttribute("sqfw", sqfw);
			
			//获取一级社区服务列表
			List<SysParam> oneFwList = sjzdService.getParamList(code);
			
			//获取二级社区服务列表
			List<SysParamDesc> twoFwList = sjzdService.getEjFwList(code);
			
			model.addAttribute("oneFwList", oneFwList);
			model.addAttribute("twoFwList", twoFwList);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shfw/sqfw/addEdit";
	}
	
	/**
	 * 保存社区服务
	 * @param sqfw
	 * @param id
	 * @param fName
	 * @param tjStatus
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(ShfwSqfwEntity sqfw, String id, String fName, String tjStatus, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			ShfwSqfwEntity s =  sqfwService.getById(id);
			if(s == null){
				//新增
				sqfw.setUserId(sysAccCount.getAccCode());
				sqfw.setUserName(sysAccCount.getName());
				sqfwService.save(sqfw, fName, tjStatus);
			}else{
				//修改
				sqfwService.update(sqfw, id, fName, tjStatus);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存社区服务出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 审核社区服务
	 * @param ids --社区服务ID
	 * @param session
	 * @return
	 */
	@RequestMapping("/doAuditing")
	@ResponseBody
	public String doAuditing(String ids, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			//sqfwService.shStatus(ids);
			String id[] = ids.split(";");
			for(int i=0; i<id.length; i++){
				ShfwSqfwEntity sqfw = sqfwService.getById(id[i]);
				if(sqfw!=null){
					sqfw.setShStatus("1");
					sqfw.setShUserId(sysAccCount.getAccCode());
					sqfw.setShUserName(sysAccCount.getName());
					sqfwService.shSqfwStatus(sqfw);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("审核社区服务出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除社区服务
	 * @param ids --社区服务ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			sqfwService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除社区服务出错：", e);
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
			sqfwService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除社区服务附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核社区服务
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/auditing")
	public String auditing(String id, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			ShfwSqfwEntity sqfw = sqfwService.getById(id);
			sqfw.setShStatus("1");
			sqfw.setShUserId(sysAccCount.getAccCode());
			sqfw.setShUserName(sysAccCount.getName());
			sqfwService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("审核社区服务出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 反审核社区服务
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/antiaudit")
	public String antiaudit(String id){
		try {
			ShfwSqfwEntity sqfw = sqfwService.getById(id);
			sqfw.setShStatus("0");
			sqfwService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("反审核社区服务出错：", e);
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
			sqfwService.shZt(id, flag);
			if("2".equals(flag)){
				sqfwService.updateisHot(id, "2");
			}else{
				sqfwService.updateisHot(id, "1");
			}
			return "success";
		} catch (Exception e) {
			log.error("审核社区服务出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 设置社区服务是否热点
	 * @param id
	 * @param flag	热点标志
	 * @param isHot 是否热点
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setHot")
	public String setHot(String id,String flag, String isHot){
		try {
			ShfwSqfwEntity sqfw = sqfwService.getByIsHot(isHot);
			if(sqfw==null){
				sqfwService.updateisHot(id, flag);
				return "success";
			}else{
				sqfwService.updateisHot(id, flag);
				sqfwService.updateisHot(sqfw.getId(), "2");
				return "success";
			}
		} catch (Exception e) {
			log.error("设置社区服务热点出错：", e);
		}
		return "fail";
	}
	
}
