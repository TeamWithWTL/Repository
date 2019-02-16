package com.jcwx.action.xtgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

import net.sf.json.JSONArray;

/**
 * 数据字典
 * @author Wjx
 *
 */

@Controller
@RequestMapping("/xtgl/sjzdgl")
public class SjzdAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));//每页最大条数
	private Logger log = Logger.getLogger(SjzdAction.class);
	
	@Autowired
	private SjzdService sjzdService;
	
	/**
	 * 首页信息查询展示
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param code
	 * @param cdCode
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber, String name, String code, String cdCode){
		try {
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
//				model.addAttribute("headTitle",null);
//				model.addAttribute("cdCode",cdCode);
			}else{
				//分页查询数据字典
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", code);
				map.put("name", name);
				Pagenate<SysParam> pagenate = sjzdService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
//				SysMenu s = xtcdglService.findById(cdCode);//根据code查询菜单信息
//				model.addAttribute("headTitle",s.getMenuName());
				model.addAttribute("code", code);
				model.addAttribute("name", name);
				return "xtgl/sjzd/index#"+ajaxCmd;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("数据字典信息出错",e);
		}
		return "xtgl/sjzd/index";
	}
	/**
	 * 添加修改界面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(String code, Model model){
		log.info("系统参数----------"+code);
		SysParam sysparam = null;
		if(code != null && !code.equals("")){
			sysparam = sjzdService.findByCode(code);
		}
		model.addAttribute("sysparam", sysparam);
		return "xtgl/sjzd/addEdit";
	}
	/**
	 * 添加及修改保存
	 * @param id
	 * @param sysparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(String id, SysParam sysparam){
		try {
			SysParam sysp = sjzdService.findByCode(id);
			if(sysp ==  null){
				sjzdService.save(sysparam);
			}else{
				sysp.setDescription(sysparam.getDescription());
				sysp.setName(sysparam.getName());
				sysp.setValue1(sysparam.getValue1());
				sysp.setValue2(sysparam.getValue2());
				sjzdService.update(sysp);
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			log.info("添加系统参数信息出错",e);
		}
		return "error";
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids){
		try {
			sjzdService.del(ids);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			log.info("删除数据字典信息出错",e);
		}
		return	"error";
	}
	/**
	 * 验证编号是否存在
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCode")
	public String checkCode(String code){
		log.info("code=======" + code);
		SysParam sys = sjzdService.findByCode(code);
		return sys == null ? "{\"valid\":\"true\"}" : "{\"valid\":\"false\"}";
	}
	
	@RequestMapping("/descIndex")
	public String descIndex(Model model, String ajaxCmd, @RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber, String itemName, String itemCode, String code){
		try {
			if(ajaxCmd == null){
				model.addAttribute("code", code);
				model.addAttribute("pagenate", null);
			}else{
				//分页查询数据字典
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", code);
				map.put("itemCode", itemCode);
				map.put("itemName", itemName);
				Pagenate<SysParamDesc> pagenate = sjzdService.findDescByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				model.addAttribute("itemCode", itemCode);
				model.addAttribute("itemName", itemName);
				return "xtgl/sjzd/descIndex#"+ajaxCmd;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("数据字典明细信息出错",e);
		}
		return "xtgl/sjzd/descIndex";
	}
	
	/**
	 * 添加修改界面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping("/goDescAddEdit")
	public String goDescAdd(String code, Model model, String sjzdId){
		log.info("系统参数----------"+code);
		SysParamDesc sysparam = null;
		if(code != null && !code.equals("")){
			sysparam = sjzdService.findDescById(code);
		}
		model.addAttribute("sysparam", sysparam);
		model.addAttribute("sjzdId", sjzdId);
		return "xtgl/sjzd/descAddEdit";
	}
	/**
	 * 添加及修改保存
	 * @param descId
	 * @param sysparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDescSave")
	public String doDescSave(String descId, SysParamDesc sysparam){
		try {
			SysParamDesc sys = sjzdService.findDescById(descId);
			if(sys ==  null){
				sjzdService.saveDesc(sysparam);
			}else{
				sys.setItemName(sysparam.getItemName());
				sys.setValue1(sysparam.getValue1());
				sys.setValue2(sysparam.getValue2());
				sjzdService.updateDesc(sys);
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			log.info("添加参数项信息出错",e);
		}
		return "error";
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goDescDel")
	public String goDescDel(String ids){
		try {
			sjzdService.delDesc(ids);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			log.info("删除数据字典信息出错",e);
		}
		return	"error";
	}
	/**
	 * 验证编号是否存在
	 * @param itemCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkDescCode")
	public String checkDescCode(String itemCode, String sjzdCode){
		log.info("itemCode=======" + itemCode);
		SysParamDesc sys = sjzdService.findDescByCode(itemCode, sjzdCode);
		return sys == null ? "{\"valid\":\"true\"}" : "{\"valid\":\"false\"}";
	}
	
	/**
	 * 获取二级社区服务列表
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getParamData", produces = "text/html;charset=UTF-8")
	public String getParamData(String code) {
		List<SysParamDesc> twoFwList = new ArrayList<SysParamDesc>();
		if (code != null && !"".equals(code)) {
			//Map<String, String> paramMap = new HashMap<String, String>();
			//paramMap.put("code", code);
			twoFwList = sjzdService.getEjFwList(code);
		}
		String returnJson = JSONArray.fromObject(twoFwList).toString();
		return returnJson;
	}
	
	
	
	/**
	 * 获取二级社区活动列表
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/getParamData1", produces = "text/html;charset=UTF-8")
	public String getParamData1(String code) {
		List<SysParamDesc> twoFwList = new ArrayList<SysParamDesc>();
		if (code != null && !"".equals(code)) {
			//Map<String, String> paramMap = new HashMap<String, String>();
			//paramMap.put("code", code);
			twoFwList = sjzdService.getEjFwList1(code);
		}
		String returnJson = JSONArray.fromObject(twoFwList).toString();
		return returnJson;
	}
	
}
