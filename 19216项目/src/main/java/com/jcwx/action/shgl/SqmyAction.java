package com.jcwx.action.shgl;

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

import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.service.shgl.LyglService;
import com.jcwx.service.shgl.SqmyService;
import com.jcwx.service.shgl.XqxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author xushu
 * 	2017年11月03日
 * 	社情民意
 */
@Controller
@RequestMapping("/shgl/sqmy")
public class SqmyAction {
	
	private Logger log = Logger.getLogger(SqmyAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数

	@Autowired
	private SqmyService sqmyService;
	@Autowired
	private XqxxService xqxxService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private LyglService lyglService;
	@Autowired
	private SjzdService sjzdService;
	
	/**
	 * 跳转到社情民意首页
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title     标题
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String SqmyContent(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String title,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCodes = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCodes);
		try{
			  if(ajaxCmd == null){
					model.addAttribute("pagenate", null);
				}else{
					String roleCode = "";
					if(roleCodes.indexOf(Consts.ROLE_FWZGLY)>-1){
						roleCode = Consts.ROLE_FWZGLY;
					}
					if(roleCodes.indexOf(Consts.ROLE_SQGLY)>-1){
						roleCode = Consts.ROLE_SQGLY;
					}
					//如果查询的角色 不是社区管理员 也不是 服务站管理员  返回空
					if("".equals(roleCode)){
						model.addAttribute("pagenate", null);
						return "shgl/sqmy/index#" + ajaxCmd;
					}
					//分页查询
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", title);
					map.put("sq_id", sysAccCount.getCommId());
					Pagenate<ShglSqmyEntity> pagenate = sqmyService.findSqmyByPage(pageNumber, pageSize, map);
					model.addAttribute("pagenate", pagenate);
					return "shgl/sqmy/index#" + ajaxCmd;
				}
			}catch(Exception e){
				log.error("查询社情民意出错", e);
			}
			return "shgl/sqmy/index";
	}
	/**
	 * 查询所有社情民意
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title     标题
	 * @param session
	 * @return
	 */
	@RequestMapping("/allsqmyList")
	public String allsqmyList(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String title,String isOver,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCodes = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCodes);
		try{
			  if(ajaxCmd == null){
					model.addAttribute("pagenate", null);
				}else{
					//分页查询
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", title);
					map.put("is_over", isOver);
					Pagenate<ShglSqmyEntity> pagenate = sqmyService.findSqmyByPage(pageNumber, pageSize, map);
					model.addAttribute("pagenate", pagenate);
					return "shgl/sqmy/sqmy_all#" + ajaxCmd;
				}
			}catch(Exception e){
				log.error("查询社情民意出错_allsqmyList", e);
			}
			return "shgl/sqmy/sqmy_all";
	}
	/**
	 * 跳转到新增/修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model , String id,HttpSession session){
		log.info("社情民意ID：" + id);
		try {
			ShglSqmyEntity sqmy = null;
			if(id != null && !id.equals("")){
				sqmy = sqmyService.getById(id);
			}
			model.addAttribute("sqmy", sqmy);
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shgl/sqmy/addEdit";
	}
	
	/**
	 * 保存
	 * @param status     下发状态
	 * @param sqid       社区id
	 * @param accCode    发布人id
	 * @param name       发布人姓名
	 * @param title      标题
	 * @param content    内容
	 * @param dc_num     调查频率(调查的户数)
	 * @param start_date 开始时间
	 * @param end_date   结束时间
	 */
	@RequestMapping("/addsqmy")
	@ResponseBody
	public String addsqmy(String id, String status,String title,String content,int dc_num,String start_date,String end_date,String fName,HttpSession session){
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount"); //获取登录用户信息
		String accCode = sysAccCount.getAccCode();                                  //当前登陆者的账号
		String name  = sysAccCount.getName();
		String sqid = sysAccCount.getCommId();                                      //获取当前登陆者所在的社区id
		if(sqid == null || "".equals(sqid)){
			return "noCommId";
		}
		try {
			ShglSqmyEntity se = sqmyService.getById(id);
			if(se == null){
				sqmyService.save(status,sqid,accCode,name,title,content,dc_num,start_date,end_date,fName);
			}else{
				sqmyService.update(id,status,sqid,accCode,name,title,content,dc_num,start_date,end_date,fName);
			}
			if("1".equals(status)){//保存
				return "success1";
			}else if("2".equals(status)){//下发
				return "success2";
			}
		} catch (Exception e) {
			log.error("添加社情民意信息出错",e);
		}
		return "error";
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			sqmyService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除社情民意出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 查看网格员列表
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param id          社情民意id
	 * @param accCode     网格员id
	 * @param session
	 * @return
	 */
	@RequestMapping("/ckwgylist")
	public String ckwgylist(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String id,String name,HttpSession session){
		model.addAttribute("id", id);       //社情民意
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount"); //获取登录用户信息
		String roleCodes = sysAccCount.getRole_code();        //获取用户角色
		try{
		  if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				ShglSqmyEntity sqe = sqmyService.getById(id);
				String sqid = sqe.getSq_id();        //社区id
				String roleCode = "";

				if(roleCodes.indexOf(Consts.ROLE_FWZGLY)>-1){
					roleCode = Consts.ROLE_FWZGLY;
				}
				if(roleCodes.indexOf(Consts.ROLE_SQGLY)>-1){
					roleCode = Consts.ROLE_SQGLY;
				}
				//如果既不是 社区管理员 也不是服务站管理员,查询全部
//				if("".equals(roleCode)){
//					model.addAttribute("pagenate", null);
//					return "shgl/sqmy/index#" + ajaxCmd;
//				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("sqmyId", id);
				if(Consts.ROLE_FWZGLY.equals(roleCode)){           //服务站管理员
					String ssid = sysAccCount.getSsId(); 
					map.put("ssId", ssid);
					map.put("name", name);         //网格员name
				}else{
					map.put("commId", sqid);         //社区id
					map.put("name", name);         //网格员name
				}
				// 查询网格员分页信息
				Pagenate<ShglSqmyWgy> pagenate = sqmyService.findSqmyWgyListByParam(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sqmy/ckwgylist#" + ajaxCmd;
			}
		  }catch(Exception e){
		  	  log.error("查看网格员列表出错", e);
		  }
		  return "shgl/sqmy/ckwgylist";
	}
	
	/**
	 * 获取网格员所有调查列表
	 * @param model
	 * @param wgyID
	 * @return
	 */
	@RequestMapping("/dcList")
	public String dcList(Model model,String wgyID){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sqmyWgyId", wgyID);
		List<ShglSqmyDc> dcList = sqmyService.findAllDc(map);
		model.addAttribute("dcList",dcList);
		return "shgl/sqmy/sqmy_dcList";
	}

	/**
	 * 查看调查详情
	 * @param model
	 * @param dcId
	 * @return
	 */
	@RequestMapping("/dcInfo")
	public String dcInfo(Model model,String dcId){
		ShglSqmyDc dcInfo = null;
		if(dcId != null && !"".equals(dcId)){
		 dcInfo = sqmyService.findById(ShglSqmyDc.class, dcId);
		}else{
			dcInfo = new ShglSqmyDc();
		}
		model.addAttribute("dcInfo",dcInfo);
		return "shgl/sqmy/sqmy_dcInfo";
	}
	
	@RequestMapping("/viewSqmyInfo")
	public String viewSqmyInfo(Model model,String id){
		ShglSqmyEntity sqmyInfo = null;
		if(id != null && !"".equals(id)){
			sqmyInfo = sqmyService.findById(ShglSqmyEntity.class, id);
		}else{
			sqmyInfo = new ShglSqmyEntity();
		}
		model.addAttribute("sqmy",sqmyInfo);
		return "shgl/sqmy/sqmy_info";
	}
	
	/**
	 * 下发
	 */
	@RequestMapping("/issued")
	@ResponseBody
	public String  issued(String id,HttpSession session){
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount"); //获取登录用户信息
		String accCode1 = sysAccCount.getAccCode();
		String name1 = sysAccCount.getName();
		try {
			ShglSqmyEntity se = sqmyService.getById(id);
			//下发状态
			String status ="2";  //下发状态
			String sqid = se.getSq_id();
			String accCode = accCode1;
			String name = name1;
			String title = se.getTitle();
			String content = se.getContent();
			int dc_num = se.getDc_num();
			String start_date = se.getStart_date().toString();
			String end_date = se.getEnd_date().toString();
			sqmyService.update(id,status,sqid,accCode,name,title,content,dc_num,start_date,end_date,null);
		   //下发成功
			return "success2";
		} catch (Exception e) {
			log.error("下发社情民意信息出错",e);
		}
		return "error";
	}
	
	/**
	 * 删除附件
	 * @return 
	 */
	@RequestMapping("/delFj")
	@ResponseBody
	public String  delFj(String fjId){
		try {
			log.info("要删除的附件ID：" + fjId);
			sqmyService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除社情民意附件出错：", e);
		}
		return "fail";
	}
}
