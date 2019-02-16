package com.jcwx.action.xtgl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.service.xtgl.ZzjgglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
* @author MaBo
* 2017年7月25日
* 组织架构管理
*/
@Controller
@RequestMapping("/xtgl/zzjggl")
public class ZzjgglAction {
	
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));	// 每页最大条数
	private Logger log = Logger.getLogger(ZzjgglAction.class);
	
	@Autowired
	private ZzjgglService zzjgglService;
	
	/**
	 * 首页数据加载
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param orgCode 组织编号
	 * @param orgName 组织名称
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd, @RequestParam(value="pageNumber", defaultValue="1")Integer pageNumber, String orgCode, String orgName){
		try {
			// 将查询条件再次返回页面
			model.addAttribute("orgCode", orgCode);
			model.addAttribute("orgName", orgName);
			
			if(ajaxCmd == null){
				model.addAttribute("pagenate", null);
			}else{
				// 分页查询角色信息
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("orgCode", orgCode);
				paramsMap.put("orgName", orgName);
				Pagenate<OrganizationalStructure> pagenate = zzjgglService.findZzjgByPage(pageNumber, pageSize, paramsMap);
				
				model.addAttribute("pagenate", pagenate);
				return "xtgl/zzjggl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("查询组织架构信息出错", e);
		}
		
		return "xtgl/zzjggl/index";
	}
	
	/**
	 * 显示组织重命名页面
	 * @param orgCode 组织编码
	 * @return
	 */
	@RequestMapping("/showRename")
	public String showRename(String orgCode, Model model){
		OrganizationalStructure os = zzjgglService.findById(orgCode);
		model.addAttribute("os", os);
		return "xtgl/zzjggl/rename";
	}
	
	/**
	 * 重命名保存
	 * @param os
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(OrganizationalStructure os){
		try {
			log.info("组织结构编码：" + os.getOrgId());
			log.info("组织结构名称：" + os.getOrgName());
			zzjgglService.updateOs(os);
			return "succ";
		} catch (Exception e) {
			log.error("重命名保存出错", e);
		}
		return "fail";
	}
	
	/**
	 * 删除组织结构
	 * @param orgCodes 组织结构编码，多个编码之间用;分隔，存在下级的机构不予删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDel")
	public String doDel(String orgCodes){
		try {
			// 查询是否有正在进行的评议
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("checkStatus", "2");
			
			// 删除组织机构
			log.info("要删除的组织机构编码：" + orgCodes);
			zzjgglService.delOrgs(orgCodes);
			return "succ";
		} catch (Exception e) {
			log.error("删除组织结构出错", e);
		}
		return "fail";
	}
	
	/**
	 * 显示组织结构调整页面
	 * @return
	 */
	@RequestMapping("/showEditZzjg")
	public String showEditZzjg(){
		return "xtgl/zzjggl/editZzjg";
	}
	
	/**
	 * 保存组织架构
	 * @param orgStr 组织结构（id,pid,name;......）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveOrg")
	public String saveOrg(String orgStr){
		try {
			log.info("组织结构：" + orgStr);
			zzjgglService.saveOrg(orgStr);
			return "succ";
		} catch (Exception e) {
			log.error("保存组织结构出错", e);
		}
		return "fail";
	}
	
}
