package com.jcwx.action.dflz;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.AccessoryService;
import com.jcwx.service.dflz.DzywService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
/**
 * 党政要闻
 * @author 李伟
 * @time 2017年10月21日下午12:24:07
 */
@Controller
@RequestMapping("/dflz/dzyw")
public class DzywAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(DzywAction.class);
	
	@Autowired
	private DzywService dzywService; 
	
	@Autowired
	private AccessoryService accessoryService;
	/**
	 * 加载党政要闻主页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber 默认值1
	 * @param title 标题
	 * @param applyTime 时间
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,
			HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		
		String accCode = acc.getAccCode(); //系统用户登录名
		String roleCode = acc.getRole_code();//角色编码
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
				Pagenate<DzywEntity> pagenate = dzywService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "dflz/dzyw/index#" + ajaxCmd;
			}
		} catch (Exception e) {

			log.error("DzywAction类 index方法 查询廉政要闻出错", e);

		}
		return "dflz/dzyw/index";
	}
	
	/**
	 * 跳转 添加
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(String id,Model model){
		if (id!=null && !("".equals(id))) {
			DzywEntity dzywEntity = dzywService.findById(id);//查询此id是不是已经存在
			model.addAttribute("dzywEntity",dzywEntity);
		}
		
		return "dflz/dzyw/addEdit";
	}
	/**
	 * 添加，修改
	 * @param req
	 * @param title
	 * @param content
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, String title,String content, String id, String fName) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
		if (!("".equals(id)) && id!=null) {
				dzywService.update(title,acc,id,content,fName);
			}else {
				dzywService.saves(title, acc,id,content,fName);
			}
			return "success";
		} catch (Exception e) {

			log.error("DzywAction类 doSave方法添加廉政要闻信息出错", e);

		}
		return "error";
	}
	/**
	 * 查阅要闻
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		DzywEntity dzywEntity = null;
		if (id!=null&&!"".equals(id)) {
			 dzywEntity = dzywService.findById(id);//根据ID查询记录
		}
		//dzywEntity.setContent(HtmlUtil.htmlRemoveTag(dzywEntity.getContent()));
		model.addAttribute("dzywEntity",dzywEntity);
		return  "dflz/dzyw/view";
	}
	
	/**
	 * 删除
	 * @param id 
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String id){
		System.out.println(id.toString());
		try {
			dzywService.del(id);
			return "success";//成功返回
		} catch (Exception e) {
			log.error("DzywAction类 doDel方法  删除廉政要闻出错：", e);
		}
		return "fail";//出错返回
	}
	
	/**
	 * 删除
	 * @param fjId
	 * @return
	 */
	@RequestMapping("/delFj")
	@ResponseBody
	public String delFj(String fjId){
		try {
			accessoryService.delFj(fjId);
			return "success"; //成功返回
		} catch (Exception e) {
			log.error("DzywAction类 delFj方法  删除附件信息出错：", e);
		}
		return "fail";//出错返回
	}
	/***
	 * 跳转审核页面
	 * @author 李伟
	 * @time 2017年11月6日上午8:07:10
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goViewAudit")
	public String goViewAudit(String id,Model model){
		DzywEntity dzywEntity = dzywService.findById(id);//根据ID查询记录
		model.addAttribute("dzywEntity",dzywEntity);
		return  "dflz/dzyw/viewAudit";
	}
	
	/**
	 * 审核
	 * @param id
	 * @param status 审核状态 1为通过，2为不通过
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public String audit(String id,String status,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			dzywService.audit(id,status,acc);
			if ("1".equals(status)) { //1为审核通过
				return "success";//成功返回
			}else if ("2".equals(status)) {//2为审核不通过
				return "pass";//不通过返回信息
			}
		} catch ( Exception e) {
			log.error("DzywAction类 audit方法  审核出错：", e);
		}
		return "fail";//出错返回信息
	}
	/**
	 * 热点
	 * @author 李伟
	 * @time 2017年11月6日上午8:02:19
	 * @param id
	 * @param hot 设置热点   1为是，2为否
	 * @return
	 */
	@RequestMapping("/hot")
	@ResponseBody
	public String hot(String id,String hot){
	
		try {
			dzywService.hot(id,hot);
			if ("1".equals(hot)) {//1为设置热点
				return "success";//成功返回
			}else if ("2".equals(hot)) {//2为取消热点
				return "cancel";//取消返回
			}
		} catch ( Exception e) {
			log.error(" DzywAction类 hot方法  热点设置出错：", e);
		}
		return "fail";//出错返回 
	}
}
