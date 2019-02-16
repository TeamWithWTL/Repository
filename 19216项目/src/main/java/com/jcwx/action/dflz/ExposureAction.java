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

import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.ExpAcceService;
import com.jcwx.service.dflz.ExposureService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 曝光台
 * @author 李伟
 * @time 2017年10月21日下午5:09:31
 */
@Controller
@RequestMapping("/dflz/bgt")
public class ExposureAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(ExposureAction.class);
	
	@Autowired
	private ExposureService exposureService;
	@Autowired
	private ExpAcceService expAcceService;
	/**
	 * 主页面分页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber  默认值为1
	 * @param title
	 * @param applyTime
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String title, String applyTime,
			HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		
		//String accCode = acc.getAccCode(); //系统用户登录名
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
				map.put("roleCode", roleCode);
				Pagenate<ExposureEntity> pagenate = exposureService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "dflz/bggl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("ExposureAction类  index方法   查询曝光信息出错", e);
		}
		return "dflz/bggl/index";
	}
	
	/**
	 * 跳转 添加
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(String id,Model model){
		if (id!=null && !("".equals(id))) {
			ExposureEntity exposureEntity = exposureService.findById(id);//查询此id是不是已经存在
			model.addAttribute("exposureEntity",exposureEntity);
		}
		return "dflz/bggl/addEdit";
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
	public String doSave(HttpServletRequest req, String title,String content,String id, String fName) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			
		if (!("".equals(id)) && id!=null) {
				exposureService.update(title,acc,id,content,fName);
			}else {
				exposureService.saves(title, acc,id,content,fName);
			}
			return "success";//成功信息返回
		} catch (Exception e) {
			log.error("ExposureAction类  doSave方法   添加曝光处理信息出错", e);
		}
		return "error";//出错信息返回
	}
	/**
	 * 查阅要闻
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(String id,Model model){
		ExposureEntity exposureEntity = exposureService.findById(id);//查询此id是不是已经存在
		//exposureEntity.setContent(HtmlUtil.htmlRemoveTag(exposureEntity.getContent()));
		model.addAttribute("exposureEntity",exposureEntity);
		return  "dflz/bggl/view";
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String id){
		try {
			exposureService.del(id);
			return "success";//成功返回
		} catch (Exception e) {
			log.error("ExposureAction类  doDel方法  删除曝光信息出错：", e);
		}
		return "fail";//出错返回
	}
	
	/**
	 * 删除
	 * @param fjId  附件id
	 * @return
	 */
	@RequestMapping("/delFj")
	@ResponseBody
	public String delFj(String fjId){
		try {
			expAcceService.delFj(fjId);
			return "success";//成功返回
		} catch (Exception e) {
			log.error("ExposureAction类  delFj方法  删除附件信息出错：", e);
		}
		return "fail";//失败返回
	}
	/**
	 * 跳转审核
	 * @author 李伟
	 * @time 2017年11月6日上午9:08:45
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goViewAudit")
	public String goViewAudit(String id,Model model){
		ExposureEntity exposureEntity = exposureService.findById(id);//根据ID查询记录
		model.addAttribute("exposureEntity",exposureEntity);
		return  "dflz/bggl/viewAudit";
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
			exposureService.audit(id,status,acc);
			if ("1".equals(status)) {//1为审核成功
				return "success";//成功返回值
			}else if ("2".equals(status)) {//2为审核失败
				return "pass";//失败返回值
			}
		} catch ( Exception e) {
			log.error("ExposureAction类  audit方法  审核出错：", e);
		}
		return "fail";//出错返回值
	}
}
