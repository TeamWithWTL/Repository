package com.jcwx.action.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.service.pub.OrganizationService;

import net.sf.json.JSONArray;

/**
* @author MaBo
* 2017年6月21日
* 组织结构树
*/
@Controller
@RequestMapping("/pub/orgTree")
public class OrganizationAction {
	
	@Autowired
	private OrganizationService orgService;
	
	/**
	 * 显示党组织结构树页面
	 * orgCode如果为null，则查询所有的组织结构；否则查询orgCode及其下属的组织结构，如果没有下属，则查询其本身
	 * @param orgCode 组织编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/showOrgTree")
	public String showOrgTree(String orgCode, Model model, HttpSession session){
		model.addAttribute("orgCode", orgCode == null ? "" : orgCode);
		return "organizationTree";
	}
	
	/**
	 * 加载组织结构树
	 * orgCode如果为null，则查询所有的组织结构；否则查询orgCode及其下属的组织结构，如果没有下属，则查询其本身
	 * @param orgCode 组织编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadTreeData", produces="text/html;charset=UTF-8")
	public String loadTreeData(String orgCode){
		
		// 组织结构节点列表
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
		
		// 查询orgCode所有上级和下级
		if(orgCode != null && !orgCode.equals("")){
			List<OrganizationalStructure> parentList = orgService.findAllParents(orgCode);
			for(OrganizationalStructure organizationalStructure : parentList){
				String code = organizationalStructure.getOrgId();
				String name = organizationalStructure.getOrgName();
				String pid = organizationalStructure.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
			
			List<OrganizationalStructure> childrenList = orgService.findAllChildren(orgCode);
			for(OrganizationalStructure organizationalStructure : childrenList){
				String code = organizationalStructure.getOrgId();
				String name = organizationalStructure.getOrgName();
				String pid = organizationalStructure.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
			
			OrganizationalStructure organizationalStructure = orgService.findById(orgCode);
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put("id", organizationalStructure.getOrgId());
			nodeMap.put("name", organizationalStructure.getOrgName());
			nodeMap.put("pId", organizationalStructure.getParentId() == null || organizationalStructure.getParentId().equals("") ? "0" : organizationalStructure.getParentId());
			nodeList.add(nodeMap);
			
		}else{
			// 查询组织结构
			Map<String, String> params = new HashMap<String, String>();
			params.put("orgCode", orgCode);
			List<OrganizationalStructure> list = orgService.findByParams(params);
			// 组织节点
			for(OrganizationalStructure organizationalStructure : list){
				String code = organizationalStructure.getOrgId();
				String name = organizationalStructure.getOrgName();
				String pid = organizationalStructure.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
		}
		
		// 添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "root");
		rootNode.put("name", "博昌街道");
		rootNode.put("pId", "0");
		rootNode.put("open", "true");
		nodeList.add(rootNode);
		
		return JSONArray.fromObject(nodeList).toString();
	}
}
