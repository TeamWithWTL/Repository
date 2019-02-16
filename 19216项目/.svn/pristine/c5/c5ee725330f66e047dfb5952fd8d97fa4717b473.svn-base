package com.jcwx.action.xtgl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.xtgl.JsglService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

/**
 * @author MaBo 2017年5月24日 用户管理
 */
@Controller
@RequestMapping("/xtgl/yhgl")
public class YhglAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private YhglService yhglService;
	@Autowired
	private JsglService jsglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;

	/**
	 * 首页信息查询展示
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String rolecode,
			String accCode, String cdCode, String typeCode,  HttpServletRequest req) {
		
			String defaultPwd = ProjectUtils.getSysCfg("defaultPwd");
			List<SysRole> sysrole = jsglService.findall(); // 查询所有的角色--除超级管理员外
			model.addAttribute("sysrole", sysrole);
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			model.addAttribute("roleCode", acc.getRole_code());
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
				model.addAttribute("defaultPwd", defaultPwd);
			} else {
				// 分页查询用户信息
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("rolecode", rolecode);
				map.put("accCode", accCode);
				map.put("typeCode", typeCode);
				Pagenate<SysAccCountLazy> pagenate = yhglService.findAccByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("defaultPwd", defaultPwd);
				return "xtgl/yhgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询用户信息出错", e);
		}
		return "xtgl/yhgl/index";
	}

	/**
	 * 跳转添加及修改界面
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String code) {
		log.info("用户编号==========" + code);
		SysAccCountLazy sysacc = null;
		List<SysRole> sysrole = jsglService.findall();// 查询所有的角色--除超级管理员外
		if (code != null && !code.equals("")) {
			sysacc = yhglService.findByCode(code);
			List<SysAccRole> roles = sysacc.getSysaccrole();
			for(SysRole role1: sysrole){                       //判断出已选中的角色
				for(SysAccRole role2 : roles){
					if(role1.getRoleCode().equals(role2.getRoleCode())){
						role1.setYhxzjs("1"); //1，选中角色
					}
				}
			}
		}
		Map<String, String> paramMap1 = new HashMap<String, String>();
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap1);// 社区列表
		model.addAttribute("comList", comList);
	
		List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
		model.addAttribute("ssList", ssList);

		List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap1);// 网格列表
		model.addAttribute("gridList", gridList);
		
		model.addAttribute("sysrole", sysrole);
		model.addAttribute("sysacc", sysacc);
		model.addAttribute("code", code);
		return "xtgl/yhgl/addEdit";
	}

	/**
	 * 跳转查看界面
	 */
	@RequestMapping("/goView")
	public String goUpdate(String code, Model model) {
		SysAccCountLazy acc = yhglService.findByCode(code);
		model.addAttribute("acc", acc);
		return "xtgl/yhgl/view";
	}

	/**
	 * 添加及修改
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(SysAccCount acc,String roles ,String code,String zh_type) {
		try {
			SysAccCountLazy sysa = yhglService.findByCode(code);// 根据code查用户信息
			String defaultPwd = ProjectUtils.getSysCfg("defaultPwd");
			if (sysa == null) {// 为null 则数据库中没有该数据
				acc.setPwd(StringUtil.toMD5(defaultPwd));
				acc.setRole_code(roles);
				acc.setSex(acc.getSex());//性别0 男 1女
				acc.setAddTime(new Date());// 创建日期 
				acc.setValidFlag("1");     //默认有效
				acc.setIntegral(0);        //积分
//				if(acc.getDeptId() != null && !"".equals(acc.getDeptId())){
//					acc.setDeptId(acc.getDeptId());//部门
//					acc.setZh_type("4"); //账号类型
//				}else{
//					acc.setZh_type(zh_type); //账号类型
//				}
				yhglService.saveAcc(acc);// 保存用户信息
				String roles1 [] = roles.split(",");
				for(String role1 :  roles1){
					SysAccRole sysrole = new SysAccRole();
					sysrole.setAccCode(code);
					sysrole.setRoleCode(role1);
					SysRole sys = jsglService.findById(role1);
					sysrole.setRoleName(sys.getRoleName());
					yhglService.saveAccRole(sysrole);    //保存用户角色信息
				}
			} else {
				BeanUtils.copyProperties(acc, sysa,
						new String[] { "accCode", "pwd", "pic_path","integral","zh_type","addTime","sysDate", "validFlag", "partyFee", "token_id", "partyFee" });
				sysa.setRole_code(roles);
//				if(acc.getDeptId() != null && !"".equals(acc.getDeptId())){
//					sysa.setDeptId(acc.getDeptId());//部门
//					sysa.setZh_type("4"); //账号类型
//				}else{
//					sysa.setZh_type(zh_type); //账号类型
//				}
				if(roles.indexOf("03")!=-1){
					sysa.setSsId(null);
					sysa.setGridId(null);
				}
				if(roles.indexOf("02")!=-1){
					sysa.setGridId(null);
				}
				if(roles.indexOf("04")!=-1 || roles.indexOf("05")!=-1){
					sysa.setCommId(null);
					sysa.setSsId(null);
					sysa.setGridId(null);
				}
				yhglService.updateAcc(sysa);// 修改用户信息
				String roles1 [] = roles.split(",");
				for(String role1 :  roles1){
					SysAccRole sysrole = new SysAccRole();
					sysrole.setAccCode(code);
					sysrole.setRoleCode(role1);
					SysRole sys = jsglService.findById(role1);
					sysrole.setRoleName(sys.getRoleName());
					yhglService.updateAccRole(sysrole);    //修改用户角色信息
					yhglService.deleterole(role1);
				}
			}

			return "success";

		} catch (Exception e) {
			log.error("添加用户信息出错", e);
		}
		return "error";
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids) {
		try {
			yhglService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除用户信息出错", e);
		}
		return "error";
	}

	/**
	 * 验证账号是否存在
	 */
	@ResponseBody
	@RequestMapping("/checkCode")
	public String checkCode(String accCode) {
		log.info("accCode=======" + accCode);
		SysAccCountLazy sysacc = yhglService.findByCode(accCode);
		return sysacc == null ? "{\"valid\":\"true\"}" : "{\"valid\":\"false\"}";
	}

	/**
	 * 密码重置
	 */
	@ResponseBody
	@RequestMapping("/resetKey")
	public String resetKey(String ids) {
		try {
			yhglService.resetKey(ids);
			return "success";
		} catch (Exception e) {
			log.error("重置用户密码出错", e);
		}
		return "error";
	}

	/**
	 * 设置用户有效无效
	 */
	@ResponseBody
	@RequestMapping("/ValidAcc")
	public String ValidAcc(String accCode,String validFlag){
		try {
			yhglService.yhglService(accCode,validFlag);
			return "success";
		} catch (Exception e) {
			log.error("设置用户有效性出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转到选择部门页面
	 */
	@RequestMapping("/gobm")
	public String gobm(Model model){
		return "xtgl/yhgl/partyProup";
	}
}
