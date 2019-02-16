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
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.service.shzz.DtbbService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author msy
 *
 */
@Controller
@RequestMapping("/shzz/dtbb")
public class DtbbAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(DtbbAction.class);
	
	

	@Autowired
	private DtbbService dtbbService;
				
	/**
	 * 跳转到动态播报首页
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String title,String createTime,HttpSession session){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
				model.addAttribute("roleCode", sysAccCount.getRole_code());
				model.addAttribute("accCode", sysAccCount.getAccCode());
				
			try {
				model.addAttribute("title", title);
				model.addAttribute("createTime",createTime);
				if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("title",title);
				Map.put("createTime", createTime);
				Pagenate<DtbbEntity> pagenate = dtbbService.getDtbbContent(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/dtbb/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询动态播报出错。",e);
		}
		return "shzz/dtbb/index";
	}
	
	/**
	 * 查看活动详情，并审核留言列表
	 * @author 李伟
	 * @time 2017年11月24日下午3:53:16
	 * @param session
	 * @param id
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber) {
		log.info("中心介绍信息ID：" + id);
		SysAccCount acc = (SysAccCount) session.getAttribute("sysAccCount");
		String role_code = acc.getRole_code();//当前用户角色
		model.addAttribute("roleCode", role_code);
		try {
			DtbbEntity dtbb = null;
			if(id != null && !"".equals(id)){
				dtbb = dtbbService.findById(id);
			}
			model.addAttribute("dtbb", dtbb);
			
			if (ajaxCmd==null) {
				model.addAttribute("pagenate", null);
			}else{
				Map<String, String> Map = new HashMap<String, String>();
				Map.put("dtbb_id", id);
				Map.put("role_code", role_code);
				Pagenate<DtbbYjEntity> pagenate=dtbbService.findLyByPage(pageNumber, pageSize, Map);
				model.addAttribute("pagenate", pagenate);
				return "shzz/dtbb/view#"+ajaxCmd;
			}
		} catch (Exception e) {
			log.info("查看动态播报详情出错：",e);
		}
		return "shzz/dtbb/view";
	}
	/**
	 * 跳转到新增修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model,String id){
		try {
			DtbbEntity dtbb = null;
			if(id != null && !id.equals("")){
				dtbb = dtbbService.findById(id);
			}
			model.addAttribute("dtbb", dtbb);
		} catch (Exception e) {
			log.info("跳转添加修改页面出错：",e);
		}
		return "shzz/dtbb/index_addEdit";
	}
	/**
	 * 保存
	
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(DtbbEntity dtbb, String id, String fName, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			DtbbEntity d = dtbbService.getById(id);
			if(d == null){
				//新增
				dtbb.setUserId(sysAccCount.getAccCode());
				dtbb.setUserName(sysAccCount.getName());
				dtbbService.save(dtbb, fName);
			}else{
				//修改
				dtbbService.update(dtbb, id, fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存动态播报信息出错：",e);
		}
		return "error";
	}
	//跳转审核页面
	@RequestMapping("/goViewAudit")
	public String goViewAudit(String id,Model model){
		
		DtbbEntity dtbbEntity = dtbbService.findById(id);//根据ID查询记录
		model.addAttribute("dtbbEntity",dtbbEntity);
		return "shzz/dtbb/view_examine";
	}
	
	/**
	 * 审核动态播报信息
	 * @return
	 */
	@RequestMapping("/doAuditing")
	@ResponseBody
	public String doAuditing(String id, String status,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		
		try {
			dtbbService.doAuditing(id,status,sysAccCount);
			if (status.equals("1")) {
				return "success";
			}else if (status.equals("2")) {
				return "succes";
			}
		} catch ( Exception e) {
			log.error("审核出错：", e);
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
			dtbbService.del(ids);
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
	public String delFj(String id, String fName){
		try {
			log.info("要删除的附件ID：" + id);
			dtbbService.delFj(id);
			return "success";
		} catch (Exception e) {
			log.error("删除中心信息附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除留言
	 * @author 李伟
	 * @time 2017年11月24日下午1:42:59
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDelLy")
	public String doDelLy(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				dtbbService.delLy(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("Hdglaction类  doDelLy方法  删除留言出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核留言
	 * @author 李伟
	 * @time 2017年11月24日下午1:51:41
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/successLy")
	public String successLy(String id){
		try {
			if (id!=null &&!"".equals(id)) {
				dtbbService.successLy(id);
				return "success";
			}
		} catch (Exception e) {
			log.error("Hdglaction类  successLy方法  审核留言出错：", e);
		}
		return "fail";
	}
	
}
