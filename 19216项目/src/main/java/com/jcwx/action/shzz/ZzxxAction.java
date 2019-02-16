package com.jcwx.action.shzz;

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
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.shzz.ZzxxService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author xushu
 * 	2017年10月23日
 * 	社会组织信息
 */
@Controller
@RequestMapping("/shzz/zzxx")
public class ZzxxAction {
	
	private Logger log = Logger.getLogger(ZzxxAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	
	@Autowired
	private ZzxxService shzzxxService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private WgglService wgglService;
	
	/**
	 * 跳转到社会组织信息首页
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String ShzzxxContent(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,String name, String fr_name, String zzxz,HttpSession session){
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCode = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCode);
		List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织分类
		model.addAttribute("zzlx", zzlx);
		try{
		  if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				//分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("fr_name", fr_name);
				map.put("zzxz", zzxz);
				Pagenate<ZzxxEntity> pagenate = shzzxxService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("zzmc", name);
				model.addAttribute("frxm", fr_name);
				model.addAttribute("pagenate", pagenate);
				return "shzz/zzxx/index#" + ajaxCmd;
			}
		}catch(Exception e){
			log.error("查询组织信息出错", e);
		}
		return "shzz/zzxx/index";
	}
	
	/**
	 * 跳转到新增/修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAddEdit(Model model , String id){
		log.info("组织信息ID：" + id);
		try {
			List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织性质
			Map<String, String> paramMap = new HashMap<String, String>();
			List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
			ZzxxEntity zzxx = null;
			if(id != null && !id.equals("")){
				zzxx = shzzxxService.getById(id);
				String commId = zzxx.getSqid();
				Map<String, String> paramMap1 = new HashMap<String, String>();
				paramMap1.put("commId",commId);    //当前数据库的社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
				Map<String, String> paramMap2 = new HashMap<String, String>();
				String fwzid = zzxx.getFwid();    //当前数据库的服务站id
				paramMap1.put("ssId",fwzid);
				List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap2);// 网格列表
				model.addAttribute("gridList", gridList);
			}
			model.addAttribute("comList", comList);
			model.addAttribute("zzxx", zzxx);
			model.addAttribute("zzlx", zzlx);
			model.addAttribute("id", id);
		} catch (Exception e) {
			log.error("跳转添加修改页面出错：",e);
		}
		return "shzz/zzxx/addEdit";
	}
	
	/**
	 * 保存
	 * @param code
	 * @param ZzxxEntity   zzxx
	 * @param fName---附件
	 * @param session
	 * @return
	 */
	@RequestMapping("/addzzxx")
	@ResponseBody
	public String addzzxx(String id, ZzxxEntity zzxx, String fName, HttpSession session){
		try {
			ZzxxEntity ze = shzzxxService.getById(id);
			if(ze == null){
				shzzxxService.save(zzxx, fName);
			}else{
				shzzxxService.update(zzxx, id,fName);
			}
			return "success";
		} catch (Exception e) {
			log.error("添加社会组织信息出错",e);
		}
		return "error";
	}
	
	/**
	 * 删除组织信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/doDel")
	@ResponseBody
	public String doDel(String ids){
		try {
			shzzxxService.del(ids);
			return "success";
		} catch (Exception e) {
			log.error("删除组织信息出错：", e);
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
	public String delFj(String fjId){
		try {
			log.info("要删除的附件ID：" + fjId);
			shzzxxService.delFj(fjId);
			return "success";
		} catch (Exception e) {
			log.error("删除组织信息附件出错：", e);
		}
		return "fail";
	}
	
	/**
	 * 查看组织信息
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(HttpSession session, String id, Model model) {
		log.info("组织信息ID：" + id);
		try {
			ZzxxEntity zzxx = null;
			if(id != null && !id.equals("")){
				zzxx = shzzxxService.getById(id);
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap);// 服务站列表
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap);// 网格列表
			List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织性质
			model.addAttribute("comList", comList);
			model.addAttribute("ssList", ssList);
			model.addAttribute("gridList", gridList);
			model.addAttribute("zzlx", zzlx);
			model.addAttribute("zzxx", zzxx);
		} catch (Exception e) {
			log.error("查看组织信息详情出错：",e);
		}
		return "shzz/zzxx/view";
	}
}
