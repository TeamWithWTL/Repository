package com.jcwx.action.dflz.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.service.dflz.AppComplainService;
import com.jcwx.service.dflz.ComplainService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.HtmlUtil;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;
/**
 * app投诉举报
 * @author 李伟
 * @time 2017年11月9日上午8:16:46
 */
@Controller
@RequestMapping("/app/dflz/appComp")
public class AppComplainAction {
	private Logger log = Logger.getLogger(AppDzywAction.class);//log4j
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	
	@Autowired
	private AppComplainService appComplainService;
	@Autowired
	private ComplainService complainService;
	@Autowired
	private SjzdService sjzdService;
	
	/**
	 * 跳转并查询
	 * @author 李伟
	 * @time 2017年11月10日下午4:02:59
	 * @return
	 */
	@RequestMapping("/toComp")
	public String toComp(String ajaxCmd,Model model,String title,@RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber,HttpServletRequest req){
		//获取登录用户信息
				SysAccCount sysAccCount = (SysAccCount)req.getSession().getAttribute("sysAccCount");
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
						//String code = "app";
						Map<String, String> map = new HashMap<String, String>();
						map.put("title", title);
						map.put("accCode", accCode);
						//map.put("app", code);
						Pagenate<ComplainEntity> pagenate = appComplainService.findByPage(pageNumber, pageSize, map);
						model.addAttribute("pagenate", pagenate);
						model.addAttribute("pageCnts", pagenate.getPgCnts());
						model.addAttribute("pageSize", pagenate.getPageSize());
						return "/dflz/tsjb/app/appIndex#" + ajaxCmd;
					}
				} catch (Exception e) {
					log.error("[AppComplainAction]_[toComp]_查询投诉举报列表出错", e);
				}
		
		return "dflz/tsjb/app/appIndex";
	}
	
	/**
	 * 跳转添加
	 * @author 李伟
	 * @time 2017年11月13日下午2:53:26
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(Model model){
		List<SysParam> byValue1 = sjzdService.findByValue1("10016");
		model.addAttribute("paramList",byValue1);
		return "dflz/tsjb/app/add";
	}
	/**
	 * 加载二级菜单
	 * @author 李伟
	 * @time 2017年11月13日下午3:02:00
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getData", produces = "text/html;charset=UTF-8")
	public String getData(String code){
		List<SysParamDesc> findByPCode = sjzdService.findByPCode(code);
		String returnJson = JSONArray.fromObject(findByPCode).toString();
		return returnJson;
	}
	/**
	 * 查询下一页投诉举报
	 * @author 李伟
	 * @time 2017年11月9日上午9:22:58
	 * @param model
	 * @param req
	 * @param pageNum
	 * @param title
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/compNextContent")
	public Map compNextContent(Model model,String title,Integer pageNumber,HttpServletRequest req){
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String roleCode = acc.getRole_code();
		model.addAttribute("accCode",accCode);
		model.addAttribute("roleCode",roleCode);//获取当前用户编码并回显页面
		//分页查询
			//	String code = "app";
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", title);
				//map.put("app", code);
				Pagenate<ComplainEntity> pagenate = appComplainService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("pageCnts", pagenate.getPgCnts());
				model.addAttribute("pageSize", pagenate.getPageSize());
				
				List<ComplainEntity> sqfwLists = pagenate.getList();
				ComplainEntity complainEntity = new ComplainEntity();
				
				List<Map> addList = new ArrayList<Map>();//存放Map
				for(int i = 0; i<sqfwLists.size(); i++){
					Map<String, String> addMap = new HashMap<String,String>();//存放对象
					complainEntity = sqfwLists.get(i);
				 String titleId = complainEntity.getId();//标题id
				 String titleName = complainEntity.getTitle();//标题名称
				 String createTime = complainEntity.getFmtCreate_time();//创建时间
				 addMap.put("titleId", titleId);
				 addMap.put("titleName", titleName);
				 addMap.put("createTime", createTime);
				 addList.add(addMap);
				}
				Map<Object, Object> returnMap = new HashMap<Object, Object>();
				returnMap.put("result", addList);
				return returnMap;
	}
	/**
	 * 查看详情
	 * @author 李伟
	 * @time 2017年11月9日上午9:37:23
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public String goView(Model model,String id){
		try {
			ComplainEntity complainEntity = null;
			if (id!=null&&!"".equals(id)) {
				complainEntity = complainService.findById(id);
			}
			String jb_typeId = complainEntity.getJb_type();//投诉类型id
			String[] jb_type = jb_typeId.split(",");
			SysParamDesc sysParamDesc=complainService.findParamDescById(jb_typeId);//查询投诉类型实体
			SysParam sysParam=complainService.findParamById(jb_type[0]);//查询投诉类型父类
			complainEntity.setContent(HtmlUtil.htmlRemoveTag(complainEntity.getContent()));
			model.addAttribute("detailEntity",complainEntity);//回显投诉举报实体类
			model.addAttribute("sysParam",sysParam);//回显一级投诉类型实体
			model.addAttribute("sysParamDesc",sysParamDesc);//回显二级投诉类型实体
			String path = ProjectUtils.getSysCfg("appDownloadPath");
			model.addAttribute("path",path);
		} catch (Exception e) {
			log.error("appComplainAtion类 goview方法 查看详情信息出错",e);
		}
		return "/dflz/tsjb/app/detail";
	}
	/**
	 * 添加保存
	 * @author 李伟
	 * @time 2017年11月15日上午8:32:54
	 * @param complainEntity
	 * @param fName
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8;")
	public String save(ComplainEntity complainEntity,HttpServletRequest req,String sjlx){
		SysAccCount sysacc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		try {
			appComplainService.save(sysacc,complainEntity,sjlx);
			//return "success";
			return "{\"result\":\"success\",\"pid\":\"" + complainEntity.getId() + "\"}";
		} catch (Exception e) {
			log.error("appComplainAtion类 save方法 添加信息出错",e);
		}
		//return "fail";
		return "{\"result\":\"fail\"}";
	}
}
