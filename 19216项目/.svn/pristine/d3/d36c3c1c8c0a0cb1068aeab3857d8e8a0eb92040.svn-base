package com.jcwx.action.xtgl;

import java.util.Date;
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
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.service.pub.DeptPersonService;
import com.jcwx.service.xtgl.JsglService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

/**
 * 
 * 部门人员管理 Action
 * @author zhangkai
 *
 */
@Controller
@RequestMapping("/xtgl/bmrygl")
public class DeptPersonAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private static Logger log = Logger.getLogger(DeptPersonAction.class);
	
	@Autowired
	private DeptPersonService deptPersonService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private JsglService jsglService;
	
	
	/**
	 * 部门人员管理首页展示
	 */
	@RequestMapping("/index")
	public String index(String name, String deptId, String roleCode,
			String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, HttpSession session, Model model){
		
		log.info("模糊查询字段>>>>>>>"+name);
		log.info("部门Id>>>>>>>"+deptId);
		log.info("部门成员角色>>>>>>>"+roleCode);
		
		String defaultPwd = ProjectUtils.getSysCfg("defaultPwd");
		List<SysRole> sysroles = jsglService.findall();// 查询所有的角色--除超级管理员外
		
		if(ajaxCmd == null){
			model.addAttribute("pagenate", null);
			model.addAttribute("name", name);
			model.addAttribute("defaultPwd", defaultPwd);
			model.addAttribute("sysroles", sysroles);
		}else{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("name", name);
			paramsMap.put("deptId", deptId);
			paramsMap.put("roleCode", roleCode);
			Pagenate<SysAccCountLazy> pagenate = deptPersonService.findByPage(pageNumber, pageSize, paramsMap);
			model.addAttribute("pagenate", pagenate);
			model.addAttribute("defaultPwd", defaultPwd);
			return "xtgl/bmrygl/index#" + ajaxCmd;
		}
		return "xtgl/bmrygl/index";
	}
	
	/**
	 * 跳转添加及修改页面
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model, String id,HttpSession session, String deptId){
		
		log.info("部门人员Id>>>>>>>" + id);
		
		SysAccCountLazy sysacc = null;
		List<SysRole> sysrole = jsglService.findall();// 查询所有的角色--除超级管理员外
		
		if (id != null && !id.equals("")) {
			sysacc = yhglService.findByCode(id);// 根据id查询用户信息
			List<SysAccRole> roles = sysacc.getSysaccrole();
			for(SysRole role1: sysrole){                       //判断出已选中的角色
				for(SysAccRole role2 : roles){
					if(role1.getRoleCode().equals(role2.getRoleCode())){
						role1.setYhxzjs("1"); //1，选中角色
					}
				}
			}
		}
		
		model.addAttribute("sysacc", sysacc);
		model.addAttribute("sysrole", sysrole);
//		model.addAttribute("deptId", deptId);
		return "xtgl/bmrygl/addEdit";
	}
	
	/**
	 * 保存部门人员
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(String id, String name, String office_tel, String sex, String fzr_type,Integer orderNo,
			String accCode, String phoneNum, String roles, String duty, HttpSession session, String deptId){
		try {
			SysAccCountLazy sysa = yhglService.findByCode(id);// 根据code查用户信息
			String defaultPwd = ProjectUtils.getSysCfg("defaultPwd");
//			if(roles.indexOf("13")!=-1){
//				String rolesNo[] = roles.split(",");
//				for(String rolesNo1 :  rolesNo){
//					if(rolesNo1.equals("13")){
//						rolesNo.
//					}
//				}
//			}
			if(sysa == null){
				String rolesRes = roles+"13,";
				//新增
				SysAccCount countLazy = new SysAccCount();
				countLazy.setAccCode(accCode);//用户名
				countLazy.setName(name);//姓名
				countLazy.setSex(sex);//性别
				countLazy.setOrderNo(orderNo);//排序
				countLazy.setFzr_type(fzr_type);//是否是主要负责人
				countLazy.setOffice_tel(office_tel);//办公室电话
				countLazy.setPwd(StringUtil.toMD5(defaultPwd));//默认初始密码
				countLazy.setAddTime(new Date());// 创建日期 
				countLazy.setPhone(phoneNum);//手机号
				countLazy.setRole_code(rolesRes);//角色串
				countLazy.setDuty(duty);//用户职务
				countLazy.setValidFlag("1");//默认有效
				countLazy.setIntegral(0);//积分
				countLazy.setDeptId(deptId);//部门ID
				countLazy.setZh_type("4"); //账号类型
				yhglService.saveAcc(countLazy);// 保存用户信息
				
				String roles1 [] = rolesRes.split(",");
				for(String role1 :  roles1){
					SysAccRole sysrole = new SysAccRole();
					sysrole.setAccCode(accCode);
					sysrole.setRoleCode(role1);
					SysRole sys = jsglService.findById(role1);
					sysrole.setRoleName(sys.getRoleName());
					yhglService.saveAccRole(sysrole);    //保存用户角色信息
				}
				
			}else{
				if(roles.indexOf("13")!=-1){
					roles.replace("13,", "");
				}else{
					roles = roles+"13,";
				}
				//修改
				sysa.setName(name);
				sysa.setSex(sex);//性别
				sysa.setOrderNo(orderNo);//排序
				sysa.setDeptId(deptId);//部门ID
				sysa.setFzr_type(fzr_type);//是否是主要负责人
				sysa.setOffice_tel(office_tel);//办公室电话
				sysa.setPhone(phoneNum);
				sysa.setRole_code(roles);
				sysa.setDuty(duty);
				yhglService.updateAccLazy(sysa);
				
				List<SysAccRole> rolesList =  yhglService.findRole(accCode);
				for(SysAccRole lists : rolesList){
					yhglService.delRoles(lists);
				}
				
				String roles1 [] = roles.split(",");
				for(String role1 :  roles1){
					SysAccRole sysrole = new SysAccRole();
					sysrole.setAccCode(accCode);
					sysrole.setRoleCode(role1);
					SysRole sys = jsglService.findById(role1);
					sysrole.setRoleName(sys.getRoleName());
					yhglService.updateAccRole(sysrole);//修改用户角色信息
				}
				
			}
			return "success";
		} catch (Exception e) {
			log.error("[DeptPersonAction]_[doSave]_保存部门人员信息出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 跳转查看界面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goUpdate(String code, Model model) {
		SysAccCountLazy acc = yhglService.findByCode(code);
		model.addAttribute("acc", acc);
		return "xtgl/bmrygl/bmrygl_view";
	}
	
	/**
	 * 删除部门人员
	 * @param ids --部门人员ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			yhglService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("[DeptPersonAction]_[doDel]删除部门人员出错：", e);
		}
		return "fail";
	}
	
	
}
