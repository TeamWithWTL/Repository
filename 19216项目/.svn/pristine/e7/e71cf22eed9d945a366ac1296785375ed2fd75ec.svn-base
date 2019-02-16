package com.jcwx.action.xtgl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.service.xtgl.DeptglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 部门管理 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/xtgl/dept")
public class DeptglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(DeptglAction.class);
	
	@Autowired
	private DeptglService deptglService;
	
	/**
	 * 首页列表展示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param deptId 部门编号
	 * @param deptName 部门名称
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String deptId, String deptName){
		try {
			//将查询条件再次返回页面
			model.addAttribute("deptId", deptId);
			model.addAttribute("deptName", deptName);
			
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("deptId", deptId);
				paramsMap.put("deptName", deptName);
				Pagenate<SysDepartment> pagenate = deptglService.findDepartmentByPage(pageNumber, pageSize, paramsMap);
				
				model.addAttribute("pagenate", pagenate);
				return "xtgl/dept/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询部门列表出错", e);
		}
		
		return "xtgl/dept/index";
	}
	
	/**
	 * 显示部门重命名页面
	 * @param deptId 部门编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/showRename")
	public String showRename(String deptId, Model model){
		SysDepartment os = deptglService.findById(deptId);
		model.addAttribute("os", os);
		return "xtgl/dept/rename";
	}
	
	/**
	 * 重命名保存
	 * @param os
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysDepartment os){
		try {
			log.info("部门编码：" + os.getDeptId());
			log.info("部门名称：" + os.getDeptName());
			deptglService.updateOs(os);
			return "succ";
		} catch (Exception e) {
			log.error("重命名保存出错", e);
		}
		return "fail";
	}
	
	/**
	 * 删除部门
	 * @param deptIds 部门编码，多个编码之间用;分隔，存在下级的部门不予删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDel")
	public String doDel(String deptIds){
		try {
			//删除
			log.info("要删除的部门编码：" + deptIds);
			deptglService.delDeptByIds(deptIds);;
			return "succ";
		} catch (Exception e) {
			log.error("删除部门出错", e);
		}
		return "fail";
	}
	
	/**
	 * 显示部门结构调整页面
	 * @return
	 */
	@RequestMapping("/showEditDept")
	public String showEditDept(){
		return "xtgl/dept/editDept";
	}
	
	/**
	 * 保存部门
	 * @param deptStr 部门结构（id,pid,name;......）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDept")
	public String saveDept(String deptStr){
		try {
			log.info("部门结构：" + deptStr);
			deptglService.saveDept(deptStr);
			return "succ";
		} catch (Exception e) {
			log.error("保存部门出错", e);
		}
		return "fail";
	}
	
}
