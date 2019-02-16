package com.jcwx.service.pub.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.DepartmentDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.service.pub.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	private List<SysDepartment> list = null; // 某组织及其所有上级组织列表
	private List<SysDepartment> list2 = null; // 某组织及其所有下级组织列表

	@Override
	public List<SysDepartment> findByParams(Map<String, String> params) {
		return departmentDao.findDeptByParams(params);
	}

	@Override
	public List<SysDepartment> findAllParents(String orgCode) {
		list = new ArrayList<SysDepartment>();
		SysDepartment self = departmentDao.findById(SysDepartment.class, orgCode);
		String pid = self.getParentId();
		if (pid != null && !pid.equals("")) {
			findAllParent(list, self.getParentId());
		}
		return list;
	}

	/**
	 * 递归查询党组的上级组织
	 * 
	 * @param list
	 * @param orgCode
	 */
	private void findAllParent(List<SysDepartment> list, String orgCode) {
		SysDepartment dept = departmentDao.findById(SysDepartment.class, orgCode);
		if (dept == null) {
			return;
		} else {
			list.add(dept);
			String pid = dept.getParentId();
			if (pid == null || pid.equals("")) {
				return;
			} else {
				findAllParent(list, pid);
			}
		}
	}

	@Override
	public List<SysDepartment> findAllChildren(String orgCode) {
		list2 = new ArrayList<SysDepartment>();
		SysDepartment self = departmentDao.findById(SysDepartment.class, orgCode);
		String id = self.getDeptId();
		findAllChildren(list2, id);
		return list2;
	}

	/**
	 * 递归查询党组的下级组织
	 * 
	 * @param list
	 * @param orgCode
	 */
	private void findAllChildren(List<SysDepartment> list, String orgCode) {
		List<SysDepartment> lst = departmentDao.findByPid(orgCode);
		if (lst.isEmpty()) {
			return;
		} else {
			list2.addAll(lst);
			for (SysDepartment pp : lst) {
				findAllChildren(list2, pp.getDeptId());
			}
		}
	}

	@Override
	public SysDepartment findById(String id) {
		return departmentDao.findById(SysDepartment.class, id);
	}
	/**
	 * 根据部门id查询部门人员
	 */
	@Override
	public List<SysAccCountLazy> findAllPsons(String deptId) {
		// TODO Auto-generated method stub
		return departmentDao.findAllPsons(deptId);
	}
	
}
