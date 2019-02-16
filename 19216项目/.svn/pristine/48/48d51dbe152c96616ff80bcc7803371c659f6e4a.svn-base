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

import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.service.pub.DepartmentService;

import net.sf.json.JSONArray;

/**
 * 
 * 部门结构树 Action
 * @author zhangkai
 *
 */ 
@Controller
@RequestMapping("/pub/deptTree")
public class DepartmentAction {
	
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 显示部门结构树页面
	 * deptId如果为null，则查询所有的部门结构；否则查询deptId及其下属的部门结构，如果没有下属，则查询其本身
	 * @param deptId 组织编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/showDeptTree")
	public String showOrgTree(String deptId, Model model, HttpSession session){
		model.addAttribute("deptId", deptId == null ? "" : deptId);
		return "deptTree";
	}
	
	/**
	 * 加载部门结构树
	 * deptId如果为null，则查询所有的部门结构；否则查询deptId及其下属的部门结构，如果没有下属，则查询其本身
	 * @param deptId 组织编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loadTreeData", produces="text/html;charset=UTF-8")
	public String loadTreeData(String deptId){
		
		// 组织结构节点列表
		List<Map<String, String>> nodeList = new ArrayList<Map<String,String>>();
		
		// 查询deptId所有上级和下级
		if(deptId != null && !deptId.equals("")){
			List<SysDepartment> parentList = departmentService.findAllParents(deptId);
			for(SysDepartment department : parentList){
				String code = department.getDeptId();
				String name = department.getDeptName();
				String pid = department.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
			
			List<SysDepartment> childrenList = departmentService.findAllChildren(deptId);
			for(SysDepartment department : childrenList){
				String code = department.getDeptId();
				String name = department.getDeptName();
				String pid = department.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
			
			SysDepartment department = departmentService.findById(deptId);
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put("id", department.getDeptId());
			nodeMap.put("name", department.getDeptName());
			nodeMap.put("pId", department.getParentId() == null || department.getParentId().equals("") ? "0" : department.getParentId());
			nodeList.add(nodeMap);
			
		}else{
			//查询部门结构
			Map<String, String> params = new HashMap<String, String>();
			params.put("deptId", deptId);
			List<SysDepartment> list = departmentService.findByParams(params);
			//组织节点
			for(SysDepartment department : list){
				String code = department.getDeptId();
				String name = department.getDeptName();
				String pid = department.getParentId();
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("id", code);
				nodeMap.put("name", name);
				nodeMap.put("pId", pid == null || pid.equals("") ? "root" : pid);
				nodeList.add(nodeMap);
			}
		}
		
		//添加根节点
		Map<String, String> rootNode = new HashMap<String, String>();
		rootNode.put("id", "root");
		rootNode.put("name", "博昌街道");
		rootNode.put("pId", "0");
		rootNode.put("open", "true");
		nodeList.add(rootNode);
		
		return JSONArray.fromObject(nodeList).toString();
	}
	
}
