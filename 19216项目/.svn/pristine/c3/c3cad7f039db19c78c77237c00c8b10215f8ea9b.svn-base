package com.jcwx.dao.shgl.impl;

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
import com.jcwx.dao.shgl.SxtglDao;
import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class SxtglDaoImpl extends BaseDaoImpl implements SxtglDao {

	@Override
	public Pagenate<CameraEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		// TODO Auto-generated method stub
		String name = map.get("name");// 名称
		String commId = map.get("commId");// 社区编码
		String ssId = map.get("ssId");// 服务站编码
		String gridId = map.get("gridId");// 网格编码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(CameraEntity.class).createAlias("grid", "grid");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(CameraEntity.class).createAlias("grid", "grid");// 数据集
		// 组织查询条件
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (commId != null && !commId.equals("")) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId != null && !ssId.equals("")) {
			criteriaCnt.add(Restrictions.eq("grid.serviceStation.id", ssId));
			criteria.add(Restrictions.eq("grid.serviceStation.id", ssId));
		}
		if (gridId != null && !gridId.equals("")) {
			criteriaCnt.add(Restrictions.eq("grid.id", gridId));
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		// 排序
		criteria.addOrder(Order.asc("grid.id"));// 网格
		criteria.addOrder(Order.asc("name"));// 名称
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<CameraEntity> findAllCas(Map<String, String> map) {
		// TODO Auto-generated method stub
		String id = map.get("id");// id
		String name = map.get("name");// 名称
		String commId = map.get("commId");// 社区编码
		String ssId = map.get("ssId");// 服务站编码
		String gridId = map.get("gridId");// 网格编码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(CameraEntity.class).createAlias("grid", "grid");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		if (commId != null && !commId.equals("")) {
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId != null && !ssId.equals("")) {
			criteria.add(Restrictions.eq("grid.serviceStation.id", ssId));
		}
		if (gridId != null && !gridId.equals("")) {
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		// 排序
		criteria.addOrder(Order.asc("grid.id"));// 网格
		criteria.addOrder(Order.asc("name"));// 名称
		return criteria.list();
	}

}
