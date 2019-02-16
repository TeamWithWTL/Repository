package com.jcwx.action.xtbg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.xtbg.TxlglService;
import com.jcwx.service.xtgl.DeptglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 协同办公--通讯录管理App
 * @author 
 * 11-10
 */
@Controller
@RequestMapping("/app/xtbg/txlgl")
public class AppTxlglAction {

	private Logger log = Logger.getLogger(AppTxlglAction.class);
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));

	@Autowired
	private TxlglService txlglService;
	
	@Autowired
	private DeptglService deptglService;
	
	@RequestMapping("/index")
	public String index(Model model,String ajaxCmd,
			@RequestParam(value ="pageNumber", defaultValue = "1")Integer pageNumber,String name,String deptNames,String duty,String phone,String deptId,HttpSession session){
		//获取登录用户信息
			SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
			model.addAttribute("roleCode", sysAccCount.getRole_code());
			model.addAttribute("accCode", sysAccCount.getAccCode());

			try {
				
				//将查询条件再次返回页面
				model.addAttribute("deptId", deptId);
				model.addAttribute("deptNames",deptNames);
				if(ajaxCmd == null){
					model.addAttribute("pagenate",null);
				}else{
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", name);
					map.put("duty",duty);
					map.put("phone", phone);
					deptId = deptId == null ? "" : deptId;
					deptId = deptId.equals("root") ? "" : deptId;
					map.put("deptId", deptId);
					map.put("deptNames", deptNames);
					Pagenate<SysAccCount> pagenate = txlglService.getTxlglContent(pageNumber,pageSize,map);
					model.addAttribute("pagenate", pagenate);
					model.addAttribute("deptId",deptId);
					model.addAttribute("deptNames",deptNames);
					model.addAttribute("pageCnts", pagenate.getPgCnts());
					model.addAttribute("pageSize", pagenate.getPageSize());
					return "xtbg/txlgl/index_txlgl_app#"+ajaxCmd;
				}
			} catch (Exception e) {
				log.error("查询通讯录管理出错。");
			}
			return "xtbg/txlgl/index_txlgl_app";
		}
	
	
	/**
	 * mui--获取更多
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param title
	 * @param createTimes
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appGetMorePage")
	public Map appGetMorePage(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,String name,String deptNames,String duty,String phone,String deptId,
			HttpServletRequest request, HttpServletResponse response,HttpSession session){
		
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		//获取登录用户信息
		SysAccCount sysAccCount = (SysAccCount)session.getAttribute("sysAccCount");
		String roleCode = sysAccCount.getRole_code();  //用户角色
		model.addAttribute("roleCode",roleCode);
		
		//分页查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("duty",duty);
		map.put("phone", phone);
		deptId = deptId == null ? "" : deptId;
		deptId = deptId.equals("root") ? "" : deptId;
		map.put("deptId", deptId);
		map.put("deptNames", deptNames);
		Pagenate<SysAccCount> pagenate = txlglService.getTxlglContent(pageNumber,pageSize,map);
		
		model.addAttribute("pagenate", pagenate);
		model.addAttribute("pageCnts", pagenate.getPgCnts());
		model.addAttribute("pageSize", pagenate.getPageSize());
		
		List<SysAccCount> txlLists = pagenate.getList();
		if(txlLists.isEmpty()){
			returnMap.put("result", "无数据");
			return returnMap;
		}
		SysAccCount sysEntity = new SysAccCount();
		List<Map> addList = new ArrayList<Map>();//存放Map
		for(int i = 0; i<txlLists.size(); i++){
			Map<String, String> addMap = new HashMap<String,String>();//存放对象
			sysEntity = txlLists.get(i);
		 addMap.put("name", sysEntity.getName());//姓名
		 addMap.put("deptNames", sysEntity.getDeptNames());	//单位
		 addMap.put("duty", sysEntity.getDuty());	//职务	
		 addMap.put("phone",sysEntity.getPhone());	//电话
		 addMap.put("deptId", sysEntity.getDeptId());
		 addList.add(addMap);
		}
		returnMap.put("result", addList);
		return returnMap;
	}
	
	
}
