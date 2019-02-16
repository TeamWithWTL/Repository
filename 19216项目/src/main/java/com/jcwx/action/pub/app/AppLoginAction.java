package com.jcwx.action.pub.app;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.pub.LoginService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shzz.ZzxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;
/**
 * @author gaoshuai 
 * 2017年11月8日 
 * App--登陆
 */
@Controller
@RequestMapping("/appLogin")
public class AppLoginAction {

	private Logger log = Logger.getLogger(AppLoginAction.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private ZzxxService shzzxxService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private JmxxService jmxxService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SqglService sqglService;
	
	
	/**
	 * 跳转到App登陆页面
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String jumpToLogin(Model model) {
		SysParam sp = sjzdService.findByCode("sys_version");	// 查看数据字典手机版本号
		String version = sp.getValue1();
		String appPath = ProjectUtils.getSysCfg("appUrl");
		model.addAttribute("version", version);
		model.addAttribute("appPath", appPath);
		return "apppub/login";
	}

	/**
	 * 跳转到App注册页面
	 * @return
	 */
	@RequestMapping("/jumpToRegist")
	public String jumpToRegist() {
		return "apppub/register";
	}

	/**
	 * 跳转到App协议
	 * @return
	 */
	@RequestMapping("/jumpToXy")
	public String jumpToXy() {
		return "apppub/terms_of_use2";
	}

	/**
	 * 登陆校验
	 * @param model
	 * @param req
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public String doLogin(Model model, HttpServletRequest req, HttpSession session) {
		String userName = req.getParameter("userName");
		String pwd = req.getParameter("passWord");
		log.info("username>>>>>>>" + userName);
		log.info("pwd>>>>>>>" + pwd);
		SysAccCount acc = loginService.checkLogin(userName, pwd);
		if (acc == null) {
			return "paserro";
		}
		// 验证账户是否有效
		String validFlag = acc.getValidFlag();
		if (!"1".equals(validFlag)) {
			return "expired";
		}
		// 将用户账户信息和用户详情存入Session
		session.setAttribute("sysAccCount", acc);
		return "succ";
	}

	/**
	 * 注册检验
	 * @param model
	 * @param req
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public String doRegist(Model model, HttpServletRequest req, HttpSession session) {
		String frString = req.getParameter("frString");			// 组织机构代码
		String userName = req.getParameter("userName");			// 账号
		String pwd = req.getParameter("passWord");				// 密码
		String firstName = req.getParameter("firstName");		// 姓名
		String idCard = req.getParameter("idCard");				// 身份证号
		String phoneNum = req.getParameter("phoneNum");			// 手机号
		String phoneNum2 = req.getParameter("phoneNum2");		// 手机号（居民用户名）
		String accType = "2";									// 账号类型
		// 法人--校验
		if (frString != null && !"".equals(frString)) {
			SysAccCount acc = loginService.checkLogin(userName, null);			// 用户名作为登陆账号
			// 用户已存在
			if (acc != null) {
				return "paserro";
			}
			List<ZzxxEntity> entities = shzzxxService.getShzzByZzjg(frString);	// 是否存在组织机构代码
			if(entities.isEmpty()){
				return "frerror";
			}
			ZzxxEntity zzxxEntity = entities.get(0);
			// 是否已绑定法人
			String frStringId = zzxxEntity.getFr_id();
			if (null != frStringId) {
				return "frerroron";
			}
			accType = "3";
			zzxxEntity.setFr_id(userName);
			zzxxEntity.setFr_name(firstName);
			zzxxEntity.setFr_phone(phoneNum);
			shzzxxService.updateZzFr(zzxxEntity);

			SysAccCount saveAcc1 = new SysAccCount();
			saveAcc1.setAccCode(userName);						// 用户名
			saveAcc1.setName(firstName);						// 姓名
			saveAcc1.setAddTime(new Date());					// 注册日期
			saveAcc1.setPwd(StringUtil.toMD5(pwd));				// 密码
//			saveAcc1.setCardNo(idCard);							// 身份证号
			saveAcc1.setPhone(phoneNum);						// 手机号
			saveAcc1.setRole_code("14");						// 默认角色
			saveAcc1.setValidFlag("1");							// 默认有效
			saveAcc1.setIntegral(0);							// 默认积分
			saveAcc1.setZh_type(accType);						// 1,内部 2,居民 3,法人
			loginService.save(saveAcc1);
		} else {
			// 居民校验
			SysAccCount acc = loginService.checkLogin(phoneNum2, null);// 手机号作为登陆账号
			// 用户已存在
			if (null != acc) {
				return "paserro";
			}
			// 居民身份证校验是否存在
			ShglInmateEntity inmateEntity = loginService.checkCardId(idCard);
			// 居民身份证数据库不存在
			if(null == inmateEntity){
				return "noCard";
			}
			
			SysAccCount saveAcc = new SysAccCount();
			saveAcc.setName(firstName);							// 姓名
			saveAcc.setAccCode(phoneNum2);						// 用户名
			saveAcc.setAddTime(new Date());						// 注册日期
			saveAcc.setPwd(StringUtil.toMD5(pwd));				// 密码
			saveAcc.setCardNo(idCard);							// 身份证号
			saveAcc.setRole_code("14");							// 默认角色
			saveAcc.setValidFlag("1");							// 默认有效
			saveAcc.setIntegral(0);								// 默认积分
			saveAcc.setZh_type(accType);						// 1,内部 2,居民 3,法人
			loginService.save(saveAcc);
		}
		return "succ";
	}

	/**
	 * 跳转到App-个人中心
	 * @return
	 */
//	@RequestMapping("/toUserInfo")
//	public String toUserInfo(Model model, HttpSession session) {
//		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
//		int jfCount = accCount.getIntegral();
//		model.addAttribute("jfCount", jfCount);
//		return "apppub/app_user";
//	}

	/**
	 * 跳转到App-个人中心-修改密码
	 * @return
	 */
//	@RequestMapping("/toUpdataPwd")
//	public String toUpdataPwd() {
//		return "apppub/app_updata_pwd";
//	}
	
	/**
	 * 跳转到App-个人中心-服务热线展示
	 * @return
	 */
//	@RequestMapping("/toUpdataXx")
//	public String toUpdataXx(Model model, HttpSession session) {
//		
//		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
//		String accCode = accCount.getAccCode();
//		
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("dqCommId", null);//社区下拉列表显示所有小区
//		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
//		
//		Map<String, String> addMap = new HashMap<String,String>();
//		addMap.put("accCode", accCode);
//		SysAccMore accMore = loginService.findSysMore(addMap);//归属信息
//		if(accMore == null){
//			model.addAttribute("accMore", null);
//			model.addAttribute("sqLists", null);
//			model.addAttribute("fwzLists", null);
//			model.addAttribute("wgLists", null);
//		}else{
//			List<ShglCmanagerEntity> sqLists = null;
//			List<ShglSmanagerEntity> fwzLists = null;
//			List<ShglGridManagerEntity> wgLists = null;
//			//社区负责人
//			String commId = accMore.getCommId();
//			if(null != commId || !"".equals(commId)){
//				sqLists = rwglService.findSqryById(commId);
//			}
//			//服务站负责人
//			String ssId = accMore.getSsId();
//			if(null != commId || !"".equals(commId)){
//				fwzLists = jmxxService.findSmanager(ssId);
//			}
//			//网格负责人
//			String gridId = accMore.getGridId();
//			if(null != commId || !"".equals(commId)){
//				wgLists = wgglService.findGridManager(gridId);
//			}
//			
//			model.addAttribute("accMore", accMore);
//			model.addAttribute("sqLists", sqLists);
//			model.addAttribute("fwzLists", fwzLists);
//			model.addAttribute("wgLists", wgLists);
//		}
//		model.addAttribute("comList", comList);
//		
//		return "apppub/app_updata_Xx";
//	}
	
	/**
	 * 保存居民归属信息
	 */
//	@ResponseBody
//	@RequestMapping("/doSaveXx")
//	public String doSaveXx(String commId, String ssId, String gridId, HttpSession session){
//		
//		SysAccCount accCount = (SysAccCount) session.getAttribute("sysAccCount");
//		String accCode = accCount.getAccCode();
//		
//		Map<String, String> addMap = new HashMap<String,String>();
//		addMap.put("accCode", accCode);
//		SysAccMore more1 = loginService.findSysMore(addMap);//归属信息
//		
//		SysAccMore accMore = new SysAccMore();
//		if(more1 != null){
//			more1.setCommId(commId);
//			if(ssId != null && !"".equals(ssId)){
//				more1.setSsId(ssId);
//			}else{
//				ssId = "null";
//				more1.setSsId(ssId);
//			}
//			if(gridId != null && !"".equals(gridId)){
//				more1.setGridId(gridId);
//			}else{
//				gridId = "null";
//				more1.setGridId(gridId);
//			}
//			loginService.updataSysM(more1);
//			return "upSuc";
//		}
//		
//		accMore.setAccCode(accCode);
//		accMore.setCommId(commId);
//		accMore.setSsId(ssId);
//		accMore.setGridId(gridId);
//		loginService.saveSysM(accMore);
//		return "success";
//	}
	
	/**
	 * 执行App-个人中心-修改密码
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("/savePwd")
//	public String savePwd(HttpSession session, HttpServletRequest req) {
//
//		try {
//			String oldpwd = req.getParameter("oldpwd");// 原密码
//			String newpwd = req.getParameter("newpwd");// 新密码
//			String truepwd = req.getParameter("truepwd");// 确认密码
//
//			log.info("oldpwd>>>>>>>" + oldpwd);
//			log.info("newpwd>>>>>>>" + newpwd);
//			log.info("newpwd>>>>>>>" + newpwd);
//
//			SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
//			String pass = sysAccCount.getPwd();// 登录人的密码
//			String oldpwdNews = StringUtil.toMD5(oldpwd).toUpperCase();
//			String newpwdNews = StringUtil.toMD5(newpwd).toUpperCase();
//			
//			if (!pass.equals(oldpwdNews)) {// 原密码不相同
//				return "oldpwdno";
//			}
//			if (pass.equals(newpwdNews)) {// 新密码与原密码相同
//				return "newpwdno";
//			}
//			if (!newpwd.equals(truepwd)) {// 新密码与原密码输入不一致
//				return "truepwdno";
//			}
//			
//			String acccode = sysAccCount.getAccCode();// 登录人的账户
//			SysAccCountLazy countLazy = yhglService.findByCode(acccode);
//			String p2 = StringUtil.toMD5(truepwd).toUpperCase();// 修改密码页面---新密码
//			countLazy.setPwd(p2);
//			yhglService.updateAcc(countLazy);
//		} catch (Exception e) {
//			log.error("[AppLoginAction]_[savePwd]_保存密码错误", e);
//		}
//		return "succ";
//	}
}
