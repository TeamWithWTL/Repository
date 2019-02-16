package com.jcwx.service.pub.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.OrganizationDao;
import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.service.pub.OrganizationService;

/**
 * @author MaBo 2017年7月24日 组织结构
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao orgDao;
	private List<OrganizationalStructure> list = null; // 某组织及其所有上级组织列表
	private List<OrganizationalStructure> list2 = null; // 某组织及其所有下级组织列表

	@Override
	public List<OrganizationalStructure> findByParams(Map<String, String> params) {
		return orgDao.findPartyByParams(params);
	}

	@Override
	public List<OrganizationalStructure> findAllParents(String orgCode) {
		list = new ArrayList<OrganizationalStructure>();
		OrganizationalStructure self = orgDao.findById(OrganizationalStructure.class, orgCode);
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
	private void findAllParent(List<OrganizationalStructure> list, String orgCode) {
		OrganizationalStructure org = orgDao.findById(OrganizationalStructure.class, orgCode);
		if (org == null) {
			return;
		} else {
			list.add(org);
			String pid = org.getParentId();
			if (pid == null || pid.equals("")) {
				return;
			} else {
				findAllParent(list, pid);
			}
		}
	}

	@Override
	public List<OrganizationalStructure> findAllChildren(String orgCode) {
		list2 = new ArrayList<OrganizationalStructure>();
		OrganizationalStructure self = orgDao.findById(OrganizationalStructure.class, orgCode);
		String id = self.getOrgId();
		findAllChildren(list2, id);
		return list2;
	}

	/**
	 * 递归查询党组的下级组织
	 * 
	 * @param list
	 * @param orgCode
	 */
	private void findAllChildren(List<OrganizationalStructure> list, String orgCode) {
		List<OrganizationalStructure> lst = orgDao.findByPid(orgCode);
		if (lst.isEmpty()) {
			return;
		} else {
			list2.addAll(lst);
			for (OrganizationalStructure pp : lst) {
				findAllChildren(list2, pp.getOrgId());
			}
		}
	}

	@Override
	public OrganizationalStructure findById(String id) {
		return orgDao.findById(OrganizationalStructure.class, id);
	}
}
