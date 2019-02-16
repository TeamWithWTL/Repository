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
import com.jcwx.dao.shgl.ZfjgDao;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.utils.Pagenate;

@Repository
public class ZfjgDaoImpl extends BaseDaoImpl implements ZfjgDao {

	@Override
	public Pagenate<ShglGovernmentEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name");// 名称
		String manager = paramMap.get("manager");//负责人
		String phone = paramMap.get("phone");//联系电话
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglGovernmentEntity.class)/*.createAlias("gmList", "gm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		Criteria criteria = session.createCriteria(ShglGovernmentEntity.class)/*.createAlias("gmList", "gm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		/*if(null != manager && !"".equals(manager)){
			criteriaCnt.add(Restrictions.like("gm.manager", manager, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("gm.manager", manager, MatchMode.ANYWHERE));
		}
		if(null != phone && !"".equals(phone)){
			criteriaCnt.add(Restrictions.like("gm.phone", phone, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("gm.phone", phone, MatchMode.ANYWHERE));
		}*/
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("addTime"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglGovernmentEntity> findAllGover(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String strative_id = paramMap.get("strative_id");// 街道Id
		String name = paramMap.get("name");// 名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGovernmentEntity.class);// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (strative_id != null && !strative_id.equals("")) {
			criteria.add(Restrictions.eq("strativeId", strative_id));
		}
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("addTime"));
		return criteria.list();
	}

	@Override
	public ShglGovernmentEntity findByName(Map<String, String> addMap) {
		
		String jgName = addMap.get("jgName");// 政府机构名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGovernmentEntity.class);// 数据集
		
		if (jgName != null && !jgName.equals("")) {
			criteria.add(Restrictions.eq("name", jgName));
		}
		ShglGovernmentEntity governmentEntity = null;
		if(criteria.list().size()>0){
			governmentEntity = (ShglGovernmentEntity) criteria.list().get(0);
		}
		return governmentEntity;
	}

	@Override
	public List<ShglGovernmentEntity> findGoverByName(Map<String, String> map) {
		String name = map.get("name");// 名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGovernmentEntity.class);// 数据集
		// 组织查询条件
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("addTime"));
		return criteria.list();
	}

}
