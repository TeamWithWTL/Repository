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
import com.jcwx.dao.xtgl.SjzdDao;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class SjzdDaoImpl extends BaseDaoImpl implements SjzdDao {

	@Override
	public Pagenate<SysParam> findByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name");
		String code = paramMap.get("code");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(SysParam.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysParam.class);// 数据集
		// 组织查询条件
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (code != null && !code.equals("")) {
			criteriaCnt.add(Restrictions.like("code", code, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("code", code, MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("code"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<SysParamDesc> findByPCode(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		criteria.add(Restrictions.eq("code", code));
		criteria.addOrder(Order.asc("itemCode"));
		return criteria.list();
	}

	@Override
	public Pagenate<SysParamDesc> findDescByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
		String itemName = paramMap.get("itemName");// 参数项名称
		String itemCode = paramMap.get("itemCode");// 参数项编号
		String code = paramMap.get("code");// 参数--外键查询
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(SysParamDesc.class).add(Restrictions.eq("code", code));// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysParamDesc.class).add(Restrictions.eq("code", code));// 数据集
		// 组织查询条件
		if (itemName != null && !itemName.equals("")) {
			criteriaCnt.add(Restrictions.like("itemName", itemName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("itemName", itemName, MatchMode.ANYWHERE));
		}
		if (itemCode != null && !itemCode.equals("")) {
			criteriaCnt.add(Restrictions.like("itemCode", itemCode, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("itemCode", itemCode, MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("id"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public SysParamDesc findDescByCode(String code, String sjzdCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria c = session.createCriteria(SysParamDesc.class);
		c.add(Restrictions.eq("itemCode", code));
		c.add(Restrictions.eq("code", sjzdCode));
		return (SysParamDesc) c.uniqueResult();
	}

	@Override
	public List<SysParam> getParamList(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParam.class);
		criteria.add(Restrictions.or(Restrictions.eq("code", "10009"), Restrictions.eq("code", "10010")));
		return criteria.list();
	}

	@Override
	public List<SysParamDesc> getParamDescList(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		criteria.add(Restrictions.or(Restrictions.eq("code", "10009"), Restrictions.eq("code", "10010")));
		return criteria.list();
	}

	@Override
	public List<SysParamDesc> getEjFwList(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		if (code != null && !code.equals("")) {
			criteria.add(Restrictions.eq("code", code));
		}else{
			criteria.add(Restrictions.or(Restrictions.eq("code", "10009"), Restrictions.eq("code", "10010")));
		}
		return criteria.list();
	}
	
	@Override
	public List<SysParamDesc> getEjFwList1(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		if (code != null && !code.equals("")) {
			criteria.add(Restrictions.eq("code", code));
		}else{
			criteria.add(Restrictions.or(Restrictions.eq("code", "10014"),Restrictions.eq("code", "10012"), Restrictions.eq("code", "10013")));
		}
		return criteria.list();
	}

	@Override
	public List<SysParam> getParamDescList1(String code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParam.class);
		criteria.add(Restrictions.or(Restrictions.eq("code", "10014"),Restrictions.eq("code", "10012"), Restrictions.eq("code", "10013")));
		return criteria.list();
		
		
	}
	/**
	 * 查询所有相同指定value1 的LIST
	 */
	@Override
	public List<SysParam> findByValue1(String string) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParam.class);
					criteria.add(Restrictions.eq("value1", string));
		return criteria.list();
	}

	@Override
	public SysParamDesc finDescByItemName(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		if(null != id && !"".equals(id)){
			criteria.add(Restrictions.eq("itemName", id));
		}
		return (SysParamDesc) criteria.list();
	}

}
