package com.jcwx.dao.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtgl.ZzjgglDao;
import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年7月25日
* 组织架构管理
*/
@SuppressWarnings("unchecked")
@Repository
public class ZzjgglDaoImpl extends BaseDaoImpl implements ZzjgglDao {

	@Override
	public Pagenate<OrganizationalStructure> findZzjgByPage(int pageNumber, int pageSize,
			Map<String, String> paramsMap) {
		String orgCode = paramsMap.get("orgCode");	// 组织编号
		String orgName = paramsMap.get("orgName");	// 组织名称
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		// 查询数据条数
		Criteria criteriaCnt = session.createCriteria(OrganizationalStructure.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		// 查询数据集
		Criteria criteria = session.createCriteria(OrganizationalStructure.class);
		
		// 组织查询条件
		if(orgCode != null && !orgCode.equals("") && !orgCode.equals("root")){
			criteriaCnt.add(Restrictions.or(Restrictions.eq("parentId", orgCode), Restrictions.eq("orgId", orgCode)));
			criteria.add(Restrictions.or(Restrictions.eq("parentId", orgCode), Restrictions.eq("orgId", orgCode)));
		}
		if(orgName != null && !orgName.equals("")){
			criteriaCnt.add(Restrictions.like("orgName", orgName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("orgName", orgName, MatchMode.ANYWHERE));
		}
		
		// 排序
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<OrganizationalStructure> findByParentId(String pid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(OrganizationalStructure.class);
		criteria.add(Restrictions.eq("parentId", pid));
		return criteria.list();
	}
}
