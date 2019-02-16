package com.jcwx.dao.pub.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.pub.OrganizationDao;
import com.jcwx.entity.pub.OrganizationalStructure;

/**
* @author MaBo
* 2017年7月24日
* 组织结构
*/
@SuppressWarnings("unchecked")
@Repository
public class OrganizationDaoImpl extends BaseDaoImpl implements OrganizationDao {

	@Override
	public List<OrganizationalStructure> findPartyByParams(Map<String, String> params) {
		String orgCode = params.get("orgCode");	// 组织编号
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(OrganizationalStructure.class);
		if(orgCode != null && !orgCode.equals("")){
			criteria.add(Restrictions.or(Restrictions.eq("orgId", orgCode), Restrictions.eq("parentId", orgCode)));
		}
		criteria.addOrder(Order.asc("orderNo"));
		return criteria.list();
	}

	@Override
	public List<OrganizationalStructure> findByPid(String pid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(OrganizationalStructure.class);
		criteria.add(Restrictions.eq("parentId", pid));
		criteria.addOrder(Order.asc("orderNo"));
		return criteria.list();
	}

}
