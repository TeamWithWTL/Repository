package com.jcwx.action.sjzx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 *	2017年12月1日
 *	组织统计
 */
@Controller
@RequestMapping("/sjzx/zztj")
public class zztjAction {
 
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(zztjAction.class);
	
	@Autowired
	private SqglService sqglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private ZzxxService shzzxxService;
	@Autowired
	private WgglService wgglService;
	
	/**
	 * 组织数量统计
	 * @param model
	 * @param ajaxCmd
	 * @param commId
	 * @param ssId
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/zzsltjindex")
	public String zzsltjindex(Model model, String ajaxCmd,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			//当前登陆者的角色id
			//多角色取登陆者最高权限的角色
			String roleCodes = acc.getRole_code();
			String roles [] = roleCodes.split(",");
			String roleCode = "";
			for(String role : roles){
				 //街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = roleCodes;
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("sqList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
			List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织分类
			model.addAttribute("zzlx", zzlx);
		}catch(Exception e) {
			log.error("组织数量统计信息出错", e);
		}
		return "sjzx/zztj/zzsltj";
	}
	/**
	 * 组织统计柱状图
	 * @param model
	 * @param sqid    社区id
	 * @param fwid    服务站id
	 * @param zzxx    组织类型id
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initCharts")
	public Map<Object, Object> initCharts(Model model,
			 String sqid, String fwid,String zzxx, HttpServletRequest req){
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			//当前登陆者的角色id
			//多角色取登陆者最高权限的角色
			String roleCodes = acc.getRole_code();
			String roles [] = roleCodes.split(",");
			String roleCode = "";
			for(String role : roles){
				 //街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = roleCodes;
				}
			}
		//柱状图
		Map<Object, Object> mapT = new HashMap<Object, Object>();
		// 查询条件
		Map<String, String> paramsMap1 = new HashMap<String, String>();
		if(roleCode.equals("05") || roleCode.equals("99")){        //街道管理员
			if(!"".equals(sqid) && sqid != null && fwid.equals("")){
				paramsMap1.put("commId", sqid);     //社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramsMap1);// 服务站列表
				String [] listAllLX = new String[ssList.size()];          //横向类型
				int [] listAllSL = new int[ssList.size()];    //数量
				for(int i=0;i<ssList.size();i++){
					listAllLX[i] = ssList.get(i).getName();     //服务站名称
					String  fwzid = ssList.get(i).getId();      //服务站id
					paramsMap1.put("fwid",fwzid);
					paramsMap1.put("zzxz", zzxx);               //组织类型
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i]=zzsl.getList().size();            //当前服务站的数量
				}
				mapT.put("listAllLX", listAllLX);
				mapT.put("listAllSL", listAllSL);
			}else if(!"".equals(sqid) && sqid != null && !"".equals(fwid) && fwid != null){
				paramsMap1.put("ssId",fwid);
				List<ShglGridEntity> gridList = wgglService.findAllSer1(paramsMap1);// 网格列表
				String [] listAllLX = new String[gridList.size()];          //横向类型
				int [] listAllSL = new int[gridList.size()];    //数量
				for(int i=0;i<gridList.size();i++){
					listAllLX[i] = gridList.get(i).getName();   //网格名称
					String grid = gridList.get(i).getId();      //网格id
					paramsMap1.put("gridid", grid);             //网格id
					paramsMap1.put("zzxz", zzxx);               //组织类型
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i]=zzsl.getList().size();            //当前网格的数量
				}
				mapT.put("listAllLX", listAllLX);
				mapT.put("listAllSL", listAllSL);
			}else{
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramsMap1);// 社区列表
				String [] listAllLX = new String[sqList.size()];          //横向类型
				int [] listAllSL = new int[sqList.size()];    //数量
				for(int i=0;i<sqList.size();i++){
					listAllLX[i] = sqList.get(i).getName();
					String sqid1 = sqList.get(i).getId();         //社区id
					paramsMap1.put("sqid", sqid1);               //社区id
					paramsMap1.put("zzxz", zzxx);               //组织类型
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i] = zzsl.getList().size(); //当前社区的组织数量
				}
				mapT.put("listAllLX", listAllLX);
				mapT.put("listAllSL", listAllSL);
			}
		}else if(roleCode.equals("03")){     //社区管理员
			if(!"".equals(fwid) && fwid != null){      //查询条件为其所在社区的：某个服务站时
				paramsMap1.put("ssId",fwid);
				List<ShglGridEntity> gridList = wgglService.findAllSer1(paramsMap1);// 网格列表
				String [] listAllLX = new String[gridList.size()];          //横向类型
				int [] listAllSL = new int[gridList.size()];    //数量
				for(int i=0;i<gridList.size();i++){
					listAllLX[i] = gridList.get(i).getName();   //网格名称
					String grid = gridList.get(i).getId();      //网格id
					paramsMap1.put("gridid", grid);             //网格id
					paramsMap1.put("zzxz", zzxx);               //组织类型
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i]=zzsl.getList().size();            //当前网格的数量
				}
					mapT.put("listAllLX", listAllLX);
					mapT.put("listAllSL", listAllSL);
			}else{
				paramsMap1.put("commId", acc.getCommId());     //社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramsMap1);// 服务站列表
				String [] listAllLX = new String[ssList.size()];          //横向类型
				int [] listAllSL = new int[ssList.size()];    //数量
				for(int i=0;i<ssList.size();i++){
					listAllLX[i] = ssList.get(i).getName();     //服务站名称
					String  fwzid = ssList.get(i).getId();     //服务站id
					paramsMap1.put("fwid",fwzid);
					paramsMap1.put("zzxz", zzxx);               //组织类型
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i]=zzsl.getList().size();            //当前服务站的数量
				}
				mapT.put("listAllLX", listAllLX);
				mapT.put("listAllSL", listAllSL);
			}	
		}else if(roleCode.equals("02")){    //服务站管理员
			paramsMap1.put("ssId",acc.getSsId());             //当前登陆者所在服务站id
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramsMap1);// 网格列表
			String [] listAllLX = new String[gridList.size()];          //横向类型
			int [] listAllSL = new int[gridList.size()];    //数量
			for(int i=0;i<gridList.size();i++){
				listAllLX[i] = gridList.get(i).getName();   //网格名称
				String grid = gridList.get(i).getId();      //网格id
				paramsMap1.put("gridid", grid);             //网格id
				paramsMap1.put("zzxz", zzxx);               //组织类型
				Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
				listAllSL[i]=zzsl.getList().size();            //当前网格的数量
			}
			mapT.put("listAllLX", listAllLX);
			mapT.put("listAllSL", listAllSL);
		}
		return mapT;
	}
	
	/**
	 * 组织类型统计
	 * @param model
	 * @param ajaxCmd
	 * @param commId
	 * @param ssId
	 * @param pageNumber
	 * @param req
	 * @return
	 */
	@RequestMapping("/zzlxtjindex")
	public String zzlxtjindex(Model model, String ajaxCmd,String commId, String ssId,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,HttpServletRequest req){
		try{
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			//当前登陆者的角色id
			//多角色取登陆者最高权限的角色
			String roleCodes = acc.getRole_code();
			String roles [] = roleCodes.split(",");
			String roleCode = "";
			for(String role : roles){
				 //街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = roleCodes;
				}
			}
			model.addAttribute("roleCode", roleCode);
			Map<String, String> paramMap1 = new HashMap<String, String>();
			if(roleCode.equals("05") || roleCode.equals("99")){
				List<ShglCommunityEntity> sqList = sqglService.findAllCom1(paramMap1);// 社区列表
				model.addAttribute("sqList", sqList);
			}else if(roleCode.equals("03")){
				paramMap1.put("commId", acc.getCommId());     //当前登陆者所在社区id
				List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
				model.addAttribute("ssList", ssList);
			}
			List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织分类
			model.addAttribute("zzlx", zzlx);
		}catch(Exception e) {
			log.error("组织类型统计信息出错", e);
		}
		return "sjzx/zztj/zzlxtj";
	}
	
	@ResponseBody
	@RequestMapping("/initChartslx")
	public Map<Object, Object> initChartslx(Model model,
			 String sqid, String fwid,String zzxx, HttpServletRequest req){
			//获取当前登陆的角色
			SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
			//当前登陆者的角色id
			//多角色取登陆者最高权限的角色
			String roleCodes = acc.getRole_code();
			String roles [] = roleCodes.split(",");
			String roleCode = "";
			for(String role : roles){
				 //街道管理员,这几个角色进来，和街道管理员拥有相同的权限
				if(role.equals("05") || role.equals("06") || role.equals("07") || role.equals("08") ||role.equals("09") || role.equals("10") || role.equals("12") || role.equals("13")){
					roleCode = "05";
					break;
				}else if(role.equals("03")){  //社区管理员
					roleCode = "03";
					break;
				}else if(role.equals("02")){  //服务站管理员
					roleCode = "02";
					break;
				}else{
				    roleCode = roleCodes;
				}
			}
		//柱状图
		Map<Object, Object> mapT = new HashMap<Object, Object>();
		// 查询条件
		Map<String, String> paramsMap1 = new HashMap<String, String>();
		List<SysParamDesc> zzlx = sjzdService.findByCode("10011").getSysParamDesc();// 组织分类
		String [] listAllLX = new String [zzlx.size()];    //类型
		int [] listAllSL = new int [zzlx.size()];          //占比
		for(int i=0;i<zzlx.size();i++){
			String lxid = zzlx.get(i).getValue1();         //类型编码
			listAllLX[i] = zzlx.get(i).getItemName();          //类型名称
			paramsMap1.put("zzxz", lxid);                  //组织类型
			 if(roleCode.equals("05") || roleCode.equals("99")){        //街道管理员
					if(!"".equals(sqid) && sqid != null && fwid.equals("")){       //查询条件为某个社区
						paramsMap1.put("sqid", sqid);     //社区id
						Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
						listAllSL[i] = zzsl.getList().size();    //当前社区下，某组织类型的数量
					}else if(!"".equals(sqid) && sqid != null && !"".equals(fwid) && fwid != null){
						paramsMap1.put("fwid",fwid);
						Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
						listAllSL[i] = zzsl.getList().size();    //当前社区，服务站下，某组织类型的数量
					}else{
						Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
						listAllSL[i] = zzsl.getList().size();    //当前全部社区下，某组织类型的数量
					}
			   }else if(roleCode.equals("03")){     //社区管理员
					if(!"".equals(fwid) && fwid != null){      //查询条件为其所在社区的：某个服务站时
						paramsMap1.put("fwid",fwid);
						Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
						listAllSL[i] = zzsl.getList().size();    //当前服务站下，某组织类型的数量
						}else{
							paramsMap1.put("sqid", acc.getCommId());     ////当前登陆者所在社区id
							Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
							listAllSL[i] = zzsl.getList().size();    //当前全部社区下，某组织类型的数量
						}	
			   }else if(roleCode.equals("02")){    //服务站管理员
					paramsMap1.put("fwid",acc.getSsId());             //当前登陆者所在服务站id
					Pagenate<ZzxxEntity> zzsl = shzzxxService.findByPage(1, pageSize, paramsMap1);
					listAllSL[i] = zzsl.getList().size();    //当前服务站下，某组织类型的数量
				}
	      }
		 mapT.put("listAllLX", listAllLX);
		 mapT.put("listAllSL", listAllSL);
		return mapT;
	}
}
