package com.jcwx.dao.shzz.impl;

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
import com.jcwx.dao.shzz.HdtjDao;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class HdtjDaoImpl extends BaseDaoImpl implements HdtjDao {

	@Override
	public Pagenate<HdglEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String title = paramsMap.get("title");	//标题
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(HdglEntity.class);	// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(HdglEntity.class);	// 数据集
		// 组织查询条件
		if(title != null && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
	
		criteriaCnt.add(Restrictions.eq("hdStatus", "2"));
		criteria.add(Restrictions.eq("hdStatus", "2"));
		
		// 排序
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<HdglFkEntity> findFkByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String hdglId = paramsMap.get("hdglId");	//活动ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(HdglFkEntity.class);	// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(HdglFkEntity.class);	// 数据集
		// 组织查询条件
		if(hdglId != null && !hdglId.equals("")){
			criteriaCnt.add(Restrictions.eq("hdglId", hdglId));
			criteria.add(Restrictions.eq("hdglId", hdglId));
		}
		
		// 排序
		criteria.addOrder(Order.desc("backTime"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<ZzxxEntity> findZzByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String name = paramsMap.get("name");	//组织名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ZzxxEntity.class);	// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(ZzxxEntity.class);	// 数据集
		// 组织查询条件
		if(name != null && !name.equals("")){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
	
		// 排序
		criteria.addOrder(Order.desc("enter_date"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<HdglFkEntity> findFk2ByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String zzId = paramsMap.get("zzId");	//组织ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(HdglFkEntity.class);	// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(HdglFkEntity.class);	// 数据集
		// 组织查询条件
		if(zzId != null && !zzId.equals("")){
			criteriaCnt.add(Restrictions.eq("zzId", zzId));
			criteria.add(Restrictions.eq("zzId", zzId));
		}
		
		criteriaCnt.add(Restrictions.eq("isBack", "1"));
		criteria.add(Restrictions.eq("isBack", "1"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<HdglFkEntity> getFkCnt(Map<String, String> paramsMap) {
		String zzId = paramsMap.get("zzId");	//组织ID
		String startTime = paramsMap.get("startTime");  //开始时间
		String endTime = paramsMap.get("endTime");     //结束时间
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglFkEntity.class);	// 数据集
		// 组织查询条件
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			if(startTime.equals(endTime)){
				criteria.add(Restrictions.between("backTime", DateUtils.parseDate(startTime, "yyyy-MM-dd"),
						DateUtils.nextDate(startTime, "yyyy-MM-dd")));
			}else{
				if (startTime != null && !"".equals(startTime)) {
					criteria.add(Restrictions.ge("backTime", DateUtils.parseDate(startTime, "yyyy-MM-dd")));
				}
				if (endTime != null && !"".equals(endTime)) {
					criteria.add(Restrictions.le("backTime", DateUtils.parseDate(endTime, "yyyy-MM-dd")));
				}
			}
		}
		if(zzId != null && !zzId.equals("")){
			criteria.add(Restrictions.eq("zzId", zzId));
		}
		
		//criteria.add(Restrictions.eq("isBack", "1"));
		//criteria.add(Restrictions.eq("shStatus", "1"));
		criteria.add(Restrictions.ne("content", ""));
		
		return criteria.list();
	}

}
