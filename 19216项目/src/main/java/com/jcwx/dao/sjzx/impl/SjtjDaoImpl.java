package com.jcwx.dao.sjzx.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.sjzx.SjtjDao;
import com.jcwx.entity.shgl.Event;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class SjtjDaoImpl extends BaseDaoImpl implements SjtjDao{

	@Override
	public Pagenate<Event> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> paramsMap) {
		String startTime = paramsMap.get("startTime");  //开始时间
		String endTime = paramsMap.get("endTime");     //结束时间
		String commId = paramsMap.get("commId");// 社区id
		String ssId =   paramsMap.get("ssId");// 服务站id
		String isOver =   paramsMap.get("isOver");// 是否结束 1是2否
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(Event.class);
		criteriaCnt.setProjection(Projections.rowCount());// 数据集条数
		Criteria criteria = session.createCriteria(Event.class);// 数据集
		
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			if(startTime.equals(endTime)){
				criteriaCnt.add(Restrictions.between("applyTime", java.sql.Date.valueOf(startTime),
						java.sql.Date.valueOf(startTime)));
				criteria.add(Restrictions.between("applyTime", java.sql.Date.valueOf(startTime),
						java.sql.Date.valueOf(startTime)));
			}else{
				if (startTime != null && !"".equals(startTime)) {
					criteriaCnt.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startTime)));
					criteria.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startTime)));
				}
				if (endTime != null && !"".equals(endTime)) {
					criteriaCnt.add(Restrictions.le("applyTime", java.sql.Date.valueOf(endTime)));
					criteria.add(Restrictions.le("applyTime", java.sql.Date.valueOf(endTime)));
				}
			}
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (isOver != null && !"".equals(isOver)) {
			criteriaCnt.add(Restrictions.eq("isOver", isOver));
			criteria.add(Restrictions.eq("isOver", isOver));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<Event> findEvent(Map<String, String> paramsMap) {
		String startTime = paramsMap.get("startTime");  //开始时间
		String endTime = paramsMap.get("endTime");     //结束时间
		String commId = paramsMap.get("commId");// 社区id
		String ssId =   paramsMap.get("ssId");// 服务站id
		String isOver =   paramsMap.get("isOver");// 是否结束 1是2否
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);// 数据集
		
		if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
			if(startTime.equals(endTime)){
				criteria.add(Restrictions.between("applyTime", java.sql.Date.valueOf(startTime),
						java.sql.Date.valueOf(startTime)));
			}else{
				if (startTime != null && !"".equals(startTime)) {
					criteria.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startTime)));
				}
				if (endTime != null && !"".equals(endTime)) {
					criteria.add(Restrictions.le("applyTime", java.sql.Date.valueOf(endTime)));
				}
			}
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (isOver != null && !"".equals(isOver)) {
			criteria.add(Restrictions.eq("isOver", isOver));
		}
		return criteria.list();
	}
     
}
