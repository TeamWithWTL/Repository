package com.jcwx.action.xtbg;

import java.util.HashMap;
import java.util.List;
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
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 协同办公 -- 热点新闻
 * @author LiuMengMeng
 * 2017/10/25
 */
@Controller
@RequestMapping("/xtbg/rdxw")
public class RdxwAction {
	
	private Logger log = Logger.getLogger(RdxwAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));
	
	@Autowired
	private RdxwService rdxwService;
	//首页
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String title, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		model.addAttribute("roleCode", sysAccCount.getRole_code());
		model.addAttribute("accCode", sysAccCount.getAccCode());
		
		try{
			if(ajaxCmd==null){
				model.addAttribute("pagenate",null);
				
			}else{
				Map<String, String> cxMap = new HashMap<String, String>();
				cxMap.put("title", title);
				Pagenate<RdxwEntity> pagenate = rdxwService.getRdxwContent(pageNumber, pageSize, cxMap);
				model.addAttribute("title",title);
				model.addAttribute("pagenate", pagenate);
				return "xtbg/rdxw/index#" + ajaxCmd;
			}
		}catch (Exception e) {
			log.error("查询热点新闻出错！");
		}
		
		return "xtbg/rdxw/index";
}
	/**
	 * 跳转到新增修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model,String id){
		try {
			RdxwEntity rdxw = null;
			if(id != null && !id.equals("")){
				rdxw = rdxwService.getById(id);
			}
			model.addAttribute("rdxw", rdxw);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "xtbg/rdxw/addEdit";
	}
	
	/**
	 * 保存热点新闻内容
	 * @param rdxw
	 * @param id
	 * @param fName
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(RdxwEntity rdxw, String id, String fName, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			RdxwEntity z = rdxwService.getById(id);
			if(z == null){
				//新增
				rdxw.setUserId(sysAccCount.getAccCode());
				rdxw.setUserName(sysAccCount.getName());
				rdxwService.save(rdxw, fName);
			}else{
				//修改
				rdxwService.update(rdxw, id, fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存热点新闻信息出错：",e);
		}
		return "error";
	}
	
	/**
	 * 跳转查看内容详情页
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("热点新闻信息ID：" + id);
		try {
			RdxwEntity rdxw = null;
			if(id != null && !id.equals("")){
				rdxw = rdxwService.getById(id);
			}
			model.addAttribute("rdxw", rdxw);
		} catch (Exception e) {
			log.info("查看热点新闻信息详情出错：",e);
		}
		return "xtbg/rdxw/view";
	}
	
	
	/**
	 * 删除热点新闻信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			rdxwService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除中心介绍信息出错：", e);
		}
		return "fail";
	}
	/**
	 * 审核热点新闻内容
	 * @param ids
	 * @param session
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
				RdxwEntity rdxw = rdxwService.getById(id[i]);
				if(rdxw!=null){
					rdxw.setShStatus("1");
					rdxw.setShUserId(sysAccCount.getAccCode());
					rdxw.setShUserName(sysAccCount.getName());
					rdxwService.shZxStatus(rdxw);
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("审核热点新闻信息出错：", e);
		}
		return "fail";
	}
	
	
	/**
	 * 删除附件
	 * @param fjId
	 * @param fName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delFj")
	public String delFj(String fjId, String fName){
		try {
			log.info("要删除的附件ID：" + fjId);
			rdxwService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除热点新闻信息附件出错：", e);
		}
		return "fail";
	}
	
	
	
	/**
	 * 设置热点
	 */
	
	@RequestMapping("/hot")
	@ResponseBody
	public String hot(String id,String hot){
		try {
			RdxwEntity dxw = rdxwService.getById(id);
			/*List<RdxwArrtsEntity> list1 = dxw.getAttrList();
			String flag = "0";	//判断有无图片的标准 0 无 1 有
			
			for(RdxwArrtsEntity r : list1){
				if("img".equals(r.getFileType())){
					flag = "1";
					break;
				}
			}
			if("0".equals(flag)){
				return "fzWtp";
			}*/
			if(null == dxw.getPicPath()){
				if(!"2".equals(hot)){
					return "fzWtp";
				}
			}
			// 代码修改--设置了封面图片无需判断附件是否有图片（Gs-2018年5月20日 ）
			rdxwService.hot(id,hot);
			if("1".equals(hot)){
				return "success1";
			}else if (hot.equals("2")) {
				return "success2";
			}
		} catch ( Exception e) {
			log.error("热点设置出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 设置置顶_高帅（2017年12月14日--需求变更）
	 */
	@RequestMapping("/goTop")
	@ResponseBody
	public String goTop(String id,String goTop){
		try {
				rdxwService.goTop(id,goTop);
				if("1".equals(goTop)){
					return "success1";
				}else if (goTop.equals("2")) {
					return "success2";
				}
			
		} catch ( Exception e) {
			log.error("置顶设置出错：", e);
		}
		return "fail";
	}
	
	
	/**
	 * 热点新闻审核
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSh")
	public String goSh(String id, Model model) {
		log.info("中心介绍信息ID：" + id);
		try {
			RdxwEntity rdxw = null;
			if(id != null && !id.equals("")){
				rdxw = rdxwService.getById(id);
			}
			model.addAttribute("rdxw", rdxw);
		} catch (Exception e) {
			log.error("跳转热点新闻审核页面出错：",e);
		}
		return "xtbg/rdxw/viewAudit";
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
			rdxwService.shZt(id, flag,acc);
			if("1".equals(flag)){
				return "success";//成功
			}else if ("2".equals(flag)) {
				return "pass";//不通过返回信息
			}
			
		} catch (Exception e) {
			log.error("审核中心介绍信息出错：", e);
		}
		return "fail";
	}
}
