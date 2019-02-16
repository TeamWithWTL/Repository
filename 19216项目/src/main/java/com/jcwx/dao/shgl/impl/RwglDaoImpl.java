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
import com.jcwx.dao.shgl.RwglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;


@SuppressWarnings("unchecked")
@Repository
public class RwglDaoImpl extends BaseDaoImpl implements RwglDao{

	@Override
	public Pagenate<RwglEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		
		String title = map.get("title");// 标题
		String startD = map.get("startD");// 发布时间
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(RwglEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(RwglEntity.class);// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("fbr_id", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("fbr_id", title, MatchMode.ANYWHERE))));
		}
		if (startD != null && !"".equals(startD)) {
			criteriaCnt.add(Restrictions.ge("create_date", DateUtils.parseDate(startD, "yyyy-MM-dd")));
			criteria.add(Restrictions.ge("create_date", DateUtils.parseDate(startD, "yyyy-MM-dd")));
		}
		// 时间排序
		criteria.addOrder(Order.desc("create_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<RwClEntity> getRwglMyContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题
		String fbr_id = map.get("fbr_id");	//当前登陆人ID
		String rwId = map.get("rwId");	//任务ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
		}
		if (fbr_id != null && !"".equals(fbr_id)) {
			criteriaCnt.add(Restrictions.eq("pre_emp", fbr_id));
			criteria.add(Restrictions.eq("pre_emp", fbr_id));
		}
		if (rwId != null && !"".equals(rwId)) {
			criteriaCnt.add(Restrictions.eq("rw.id", rwId));
			criteria.add(Restrictions.eq("rw.id", rwId));
		}
		// 排序
		criteria.addOrder(Order.desc("starte_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	@Override
	public List<SysAccCount> findByCode(String roleCode,String zsId,String ztType) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);// 数据集
		
		// 组织查询条件
		if (roleCode != null && !"".equals(roleCode)) {
			criteria.add(Restrictions.like("role_code", roleCode, MatchMode.ANYWHERE));
		}
		if("1".equals(ztType)){
			if (zsId != null && !"".equals(zsId)) {
				criteria.add(Restrictions.eq("ssId", zsId));
			}
		}else{
			if (zsId != null && !"".equals(zsId)) {
				criteria.add(Restrictions.eq("commId", zsId));
			}
		}
		return criteria.list();
	}

	@Override
	public List<ShglCmanagerEntity> findSqryById(String id) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCmanagerEntity.class);// 数据集
		
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.eq("commId", id));
		}
		return criteria.list();
	}

	@Override
	public List<ShglServiceStationEntity> findFwz(String sqId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "cmm");// 数据集
		
		// 组织查询条件
		if (sqId != null && !"".equals(sqId)) {
			criteria.add(Restrictions.eq("cmm.id", sqId));
		}
		return criteria.list();
	}

	@Override
	public List<ShglGridEntity> findWg(String fwzId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "sst");// 数据集
		
		// 组织查询条件
		if (fwzId != null && !"".equals(fwzId)) {
			criteria.add(Restrictions.eq("sst.id", fwzId));
		}
		return criteria.list();
	}

	@Override
	public Pagenate<RwClEntity> getRwclContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题
		String curId = map.get("cur_emp");	//当前登陆人ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
		}
		if (curId != null && !"".equals(curId)) {
			criteriaCnt.add(Restrictions.eq("cur_emp", curId));
			criteria.add(Restrictions.eq("cur_emp", curId));
			criteriaCnt.add(Restrictions.eq("is_back", "1"));
			criteria.add(Restrictions.eq("is_back", "1"));
		}
		// 排序
		criteria.addOrder(Order.desc("starte_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<RwglEntity> getRwglMyXFList(Integer pageNumber, int pagesize, Map<String, String> map) {
		
		String title = map.get("title");// 标题
		String curId = map.get("fbr_id");	//当前登陆人ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(RwglEntity.class).createAlias("clEntities", "rw");// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Criteria criteria = session.createCriteria(RwglEntity.class).createAlias("clEntities", "rw");// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if (curId != null && !"".equals(curId)) {
			criteriaCnt.add(Restrictions.eq("rw.pre_emp", curId));
			criteria.add(Restrictions.eq("rw.pre_emp", curId));
		}
		// 排序
		criteria.addOrder(Order.desc("create_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
}
