package com.jcwx.action.shfw;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.service.shfw.SqhdService;
import com.jcwx.service.shfw.YjlyService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 
 * 社会服务-社区活动 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/shfw/sqhd")
public class SqhdAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(SqhdAction.class);
	
	@Autowired
	private SqhdService sqhdService;
	@Autowired
	private YjlyService yjlyService;
	@Autowired
	private YhglService yhglService;
	
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
		model.addAttribute("accCode", accCode);
		try {
			model.addAttribute("title", title);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				Pagenate<ShfwSqhdEntity> pagenate = sqhdService.findByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/sqhd/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询社区活动列表出错", e);
		}
		return "shfw/sqhd/index";
	}
	
	/**
	 * 跳转查看页面
	 * @param session
	 * @param id
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param sqhdId	--社区活动ID
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model, 
						String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String sqhdId) {
		log.info("社区活动goView====ID：" + id);
		//获取登录用户信息
		SysAccCount acc = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		try {
			ShfwSqhdEntity sqhd = null;
			if(id != null && !id.equals("")){
				sqhd = sqhdService.getById(id);
			}
			model.addAttribute("sqhd", sqhd);
			model.addAttribute("id", id);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("sqhdId", id);
				Pagenate<ShfwSqhdYjEntity> pagenate = yjlyService.findByPage(pageNumber, pageSize, map);
				/*for (ShfwSqhdYjEntity sqhdYjEntity : pagenate.getList()) {
					String userId = sqhdYjEntity.getUserId();	//留言用户Id
					SysAccCount sysAccCount=yjlyService.findLyUserById(userId);	//id查询留言用户信息
					String role_code = sysAccCount.getRole_code();//留言用户角色
					if ("14".equals(role_code)||"14,".equals(role_code)) {
						sqhdYjEntity.setRole("isNormalUser");	//标识普通用户
					}else {
						sqhdYjEntity.setRole("noIsNormalUser");	//标识不是普通用户
					}
				}*/
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("sqhdId",sqhd.getId());
				return "shfw/sqhd/view#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查看社区活动详情出错：",e);
		}
		return "shfw/sqhd/view";
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
		log.info("社区活动goSh===ID：" + id);
		try {
			ShfwSqhdEntity sqhd = null;
			if(id != null && !id.equals("")){
				sqhd = sqhdService.getById(id);
			}
			model.addAttribute("sqhd", sqhd);
		} catch (Exception e) {
			log.error("跳转社区活动审核页面出错：",e);
		}
		return "shfw/sqhd/sh_index";
	}
	
	/**
	 * 跳转添加及修改页面
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model, String id, HttpSession session){
		log.info("社区活动goAddEdit===ID：" + id);
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			ShfwSqhdEntity sqhd = null;
			/* List<ShglCommunityEntity> commList = null;
			 if("99".equals(sysAccCount.getRole_code())){
				 commList = sqhdService.getCommList();
			 }*/
			if(id != null && !id.equals("")){
				sqhd = sqhdService.getById(id);
			}
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("sqhd", sqhd);
			//model.addAttribute("commList", commList);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shfw/sqhd/addEdit";
	}
	
	/**
	 * 保存社区活动
	 * @param sqhd
	 * @param id
	 * @param fName
	 * @param fbStatus
	 * @param isSignup
	 * @param startDateFmt
	 * @param session
	 * @return
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public String doSave(ShfwSqhdEntity sqhd, String id, String fName, String fbStatus, String isSignup, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			 ShfwSqhdEntity hd = sqhdService.getById(id);
			 Date date = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date start = sdf.parse(sqhd.getStartDateFmt());
		     /*Calendar c = Calendar.getInstance();              
		     c.setTime(date);                                 
		     int year1 = c.get(Calendar.YEAR);
		     int month1 = c.get(Calendar.MONTH)+1;
		     int day1 = c.get(Calendar.DAY_OF_MONTH);
		     String year =  Integer.toString(year1);
		     String month =  "";
		     String day = "";
		     if(month1 < 10){
		    	 month = "0" + Integer.toString(month1);
		     }else{
		    	 month = Integer.toString(month1);
		     }
		     if(day1 < 10){
		    	 day = "0" + Integer.toString(day1);
		     }else{
		    	 day = Integer.toString(day1);
		     }
		     String currentDate = year+"-"+month+"-"+day;*/
			 
			if(hd == null){
				if(start.before(date)){
					sqhd.setHdStatus("1");	//进行中
				}else{
					sqhd.setHdStatus("0");	//未开始
				}
				//新增
				sqhd.setIsSignup(isSignup);
				sqhd.setUserId(sysAccCount.getAccCode());
				sqhd.setUserName(sysAccCount.getName());
				String roleCode[] = sysAccCount.getRole_code().split(",");
				for(String role_code : roleCode){
					if("03".equals(role_code)){
						sqhd.setCommId(sysAccCount.getCommId());
					}
				}
				/*if("99".equals(sysAccCount.getRole_code())){
					sqhd.setCommId(commId);
				}*/
				sqhdService.save(sqhd, fName, fbStatus);
			}else{
				//修改
				if(start.before(date)){
					sqhd.setHdStatus("1");	//进行中
				}else{
					sqhd.setHdStatus("0");	//未开始
				}
				String roleCode[] = sysAccCount.getRole_code().split(",");
				for(String role_code : roleCode){
					if("03".equals(role_code)){
						sqhd.setCommId(sysAccCount.getCommId());
					}
				}
				/*if("99".equals(sysAccCount.getRole_code())){
					sqhd.setCommId(commId);
				}*/
				sqhd.setIsSignup(isSignup);
				sqhdService.update(sqhd, id, fName, fbStatus);
			}
			return "success";
		} catch (Exception e) {
			log.error("保存社区活动出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 审核社区活动
	 * @param ids --社区活动ID
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
				ShfwSqhdEntity sqhd = sqhdService.getById(id[i]);
				if(sqhd!=null){
					sqhd.setShStatus("1");
					sqhd.setShUserId(sysAccCount.getAccCode());
					sqhd.setShUserName(sysAccCount.getName());
					sqhdService.shSqhdStatus(sqhd);;
				}
			}
			return "success";
		} catch (Exception e) {
			log.error("审核社区活动出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 删除社区活动
	 * @param ids --社区活动ID
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			sqhdService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除社区活动出错：", e);
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
			sqhdService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除社区活动附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 审核社区活动
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
			ShfwSqhdEntity sqhd = sqhdService.getById(id);
			sqhd.setShStatus("1");
			sqhd.setShUserId(sysAccCount.getAccCode());
			sqhd.setShUserName(sysAccCount.getName());
			sqhdService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("审核社区活动出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转社区活动留言审核页面
	 * @param session
	 * @param model
	 * @param id
	 * @param ajaxDshCmd
	 * @param ajaxBtgCmd
	 * @param ajaxWpfCmd
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/goShView")
	public String goShView(HttpSession session, Model model, String id, String ajaxDshCmd,String ajaxBtgCmd,
			String ajaxWpfCmd, @RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber) {
		log.info("社区活动==ID：" + id);
		try {
			//待审核留言
			if (ajaxDshCmd==null) {
				model.addAttribute("pagenateDsh", null);
			}else{
				Map<String, String> dshMap = new HashMap<String, String>();
				dshMap.put("sqhdId", id);
				Pagenate<ShfwSqhdYjEntity> pagenateDsh = yjlyService.findDshLyByPage(pageNumber, pageSize, dshMap);
				model.addAttribute("pagenateDsh", pagenateDsh);
				return "shfw/sqhd/shview#"+ajaxDshCmd;
			}
			
			//审核不通过留言
			if (ajaxBtgCmd==null) {
				model.addAttribute("pagenateBtg", null);
			}else{
				Map<String, String> btgMap = new HashMap<String, String>();
				btgMap.put("sqhdId", id);
				Pagenate<ShfwSqhdYjEntity> pagenateBtg = yjlyService.findBtgLyByPage(pageNumber, pageSize, btgMap);
				model.addAttribute("pagenateBtg", pagenateBtg);
				return "shfw/sqhd/shview#"+ajaxBtgCmd;
			}
			
			//未评分留言
			if (ajaxWpfCmd==null) {
				model.addAttribute("pagenateWpf", null);
			}else{
				Map<String, String> wpfMap = new HashMap<String, String>();
				wpfMap.put("sqhdId", id);
				Pagenate<ShfwSqhdYjEntity> pagenateWpf = yjlyService.findWpfLyByPage(pageNumber, pageSize, wpfMap);
				model.addAttribute("pagenateWpf", pagenateWpf);
				return "shfw/sqhd/shview#"+ajaxWpfCmd;
			}
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("查看社区活动留言出错：",e);
		}
		return "shfw/sqhd/shview";
	}
	
	/**
	 * 留言审核通过
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shtg")
	public String shtg(String id, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		try {
			ShfwSqhdYjEntity sqhd = sqhdService.findId(id);
			sqhd.setShStatus("1");	//审核通过
			sqhd.setScore_type("0");	//未设置积分
			sqhdService.updateLysh(id);
			return "success";
		} catch (Exception e) {
			log.error("留言审核出错：", e);
		}
		return "fail";
	
	}
	
	/**
	 * 留言审核不通过
	 * @param id
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shbtg")
	public String shbtg(String id, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		try {
			ShfwSqhdYjEntity sqhd = sqhdService.findId(id);
			sqhd.setShStatus("2");	//审核通过
			sqhd.setScore_type("0");	//未设置积分
			sqhdService.updateLysh(id);
			return "success";
		} catch (Exception e) {
			log.error("留言审核出错：", e);
		}
		return "fail";
	
	}
	
	/**
	 * 反审核社区活动
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/antiaudit")
	public String antiaudit(String id){
		try {
			ShfwSqhdEntity sqhd = sqhdService.getById(id);
			sqhd.setShStatus("0");
			sqhdService.updateshStatus(id);
			return "success";
		} catch (Exception e) {
			log.error("反审核社区活动出错：", e);
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
			sqhdService.shZt(id, flag);
			if("2".equals(flag)){
				sqhdService.updateisHot(id, "2");
			}else{
				sqhdService.updateisHot(id, "1");
			}
			return "success";
		} catch (Exception e) {
			log.error("审核社区活动出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 设置社区活动是否热点
	 * @param id
	 * @param flag 	热点标志
	 * @param isHot 是否热点
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setHot")
	public String setHot(String id,String flag, String isHot){
		try {
			ShfwSqhdEntity sqhd = sqhdService.getByIsHot(isHot);
			if(sqhd==null){
				sqhdService.updateisHot(id, flag);
				return "success";
			}else{
				sqhdService.updateisHot(id, flag);
				sqhdService.updateisHot(sqhd.getId(), "2");
				return "success";
			}
		} catch (Exception e) {
			log.error("设置社区活动热点出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 跳转社区活动意见留言评分页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goScore")
	public String goScore(HttpSession session, String id, Model model) {
		log.info("社区活动意见留言ID：" + id);
		ShfwSqhdYjEntity yjly = yjlyService.findById(id);
		model.addAttribute("id", id);
		model.addAttribute("yjly", yjly);
		return "shfw/sqhd/score";
	}
	
	/**
	 * 保存积分兑换分数
	 * @param jf
	 * @param integral	--积分
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveScore")
	@ResponseBody
	public String saveScore(ShfwSqhdYjEntity yjly, String id, String integral, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount) session.getAttribute("sysAccCount");
		try {
			ShfwJflsEntity jfls = new ShfwJflsEntity();
			ShfwSqhdYjEntity yjly1 = yjlyService.findById(id);
			if(yjly1!=null){
				yjly1.setScore_type("1");//已设置积分
				yjlyService.saveLy(yjly1);
				SysAccCountLazy acc = yhglService.findByCode(yjly1.getUserId());
				Integer zjf = acc.getIntegral();
				ShfwSqhdEntity sqhd =  sqhdService.getById(yjly1.getSqhdId());
				if(sqhd!=null){
					int jf = Integer.parseInt(integral);
					Integer hdjf = sqhd.getIntegral();
					if(jf <= hdjf){
						//保存意见留言表中积分
						yjlyService.updateScore(id, jf);
						//保存积分流水
						jfls.setAccCode(yjly1.getUserId());
						jfls.setCreateUserId(sysAccCount.getAccCode());
						jfls.setCreateUserName(sysAccCount.getName());
						jfls.setContent("参加活动--"+sqhd.getTitle()+"获得"+jf+"积分");
						jfls.setIntegral(jf);
						
						yjlyService.save(jfls);
						//更新用户积分
						Integer jf2 = zjf + jf;
						acc.setIntegral(jf2);
						yhglService.updateAccJf(yjly1.getUserId());
						
						return "success";
					}else{
						yjly1.setScore_type("0");//设置积分超限，设置积分状态，还是0，未设置积分状态
						yjlyService.saveLy(yjly1);
						return "ccszjf";
					}
				}
			}
		} catch (Exception e) {
			log.error("保存评分出错：",e);
		}
		return "fail";
	}
	
	/**
	 * 获取报名记录
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param sqhdId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getBmjl")
	public String getBmjl(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String sqhdId, HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String accCode = sysAccCount.getAccCode();
		String roleCode = sysAccCount.getRole_code();
		model.addAttribute("roleCode", roleCode);
		model.addAttribute("accCode", sysAccCount.getAccCode());
		try {
			model.addAttribute("sqhdId", sqhdId);
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("sqhdId", sqhdId);
				Pagenate<ShfwSqhdYjEntity> pagenate = yjlyService.getSqhdBmjlByPage(pageNumber, pageSize, map);
				
				model.addAttribute("pagenate", pagenate);
				
				return "shfw/sqhd/bmjl#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询社区活动报名记录出错", e);
		}
		return "shfw/sqhd/bmjl";
	}
	
}
