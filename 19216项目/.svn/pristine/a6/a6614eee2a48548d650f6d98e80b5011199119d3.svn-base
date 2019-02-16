package com.jcwx.dao.pub.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.pub.SysSignInDao;
import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
@SuppressWarnings("unchecked")
@Repository
public class SysSignInDaoImpl extends BaseDaoImpl implements SysSignInDao {

	@Override
	public SysSignInEntity findContent(String addCode, Date startTime,
			Date lunchTime, String flag) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysSignInEntity.class);
		criteria.add(Restrictions.eq("addCode", addCode));
		if("0".equals(flag)){// 判断上午是否签到
			criteria.add(Restrictions.ge("signTimeAm", startTime));
			criteria.add(Restrictions.le("signTimeAm", lunchTime));
		}
		if("1".equals(flag)){// 判断下午是否签到
			criteria.add(Restrictions.ge("signTimePm", startTime));
			criteria.add(Restrictions.le("signTimePm", lunchTime));
		}
		if(criteria.list().isEmpty()){
			return null;
		}
		return (SysSignInEntity) criteria.list().get(0);
	}

	@Override
	public Pagenate<SysSignInEntity> getSignInList(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		
		String accCode = map.get("accCode");
		String sTime = map.get("sTime");
		String eTime = map.get("eTime");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysSignInEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());						// 数据集条数
		Criteria criteria = session.createCriteria(SysSignInEntity.class);		// 数据集
		
		/*if(title != null && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteriaData.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}*/
		criteria.add(Restrictions.eq("addCode", accCode));
		
		if(null != sTime && !"".equals(sTime) && null != eTime && !"".equals(eTime)){
			criteria.add(Restrictions.or(Restrictions.ge("signTimeAm", DateUtils.parseDate(sTime, "yyyy-MM-dd mm:HH:ss")),Restrictions.ge("signTimePm", DateUtils.parseDate(sTime, "yyyy-MM-dd mm:HH:ss"))));
			criteriaCnt.add(Restrictions.or(Restrictions.ge("signTimeAm", DateUtils.parseDate(sTime, "yyyy-MM-dd mm:HH:ss")),Restrictions.ge("signTimePm", DateUtils.parseDate(sTime, "yyyy-MM-dd mm:HH:ss"))));
			criteria.add(Restrictions.or(Restrictions.le("signTimeAm", DateUtils.parseDate(eTime, "yyyy-MM-dd mm:HH:ss")),Restrictions.le("signTimePm", DateUtils.parseDate(eTime, "yyyy-MM-dd mm:HH:ss"))));
			criteriaCnt.add(Restrictions.or(Restrictions.le("signTimeAm", DateUtils.parseDate(eTime, "yyyy-MM-dd mm:HH:ss")),Restrictions.le("signTimePm", DateUtils.parseDate(eTime, "yyyy-MM-dd mm:HH:ss"))));
		}
		
		criteria.addOrder(Order.desc("signTimeAm"));
		criteria.addOrder(Order.desc("signTimePm"));
		
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<SysSignInEntity> getCouunt(String accCode, Date sTime,
			Date eTime) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysSignInEntity.class);
		criteria.add(Restrictions.eq("addCode", accCode));
		if(null != sTime && !"".equals(sTime) && null != eTime && !"".equals(eTime)){
			criteria.add(Restrictions.or(Restrictions.ge("signTimeAm", sTime),Restrictions.ge("signTimePm", sTime)));
			criteria.add(Restrictions.or(Restrictions.le("signTimeAm", eTime),Restrictions.le("signTimePm", eTime)));
		}
		return criteria.list();
	}

}
