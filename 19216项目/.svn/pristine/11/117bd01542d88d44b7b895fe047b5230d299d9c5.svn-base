package com.jcwx.dao.sjzx.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.sjzx.HouseholdSurveyDao;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
/**
 * 入户调查Dao接口实现
 * @author 李伟
 * @time 2017年11月4日上午8:38:35
 */
@SuppressWarnings("unchecked")
@Repository
public class HouseholdSurveyDaoImpl extends BaseDaoImpl implements HouseholdSurveyDao{
	/**
	 * 查询社情民意
	 */
	@Override
	public Pagenate<ShglSqmyEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String startTime = paramsMap.get("startTime");  //开始时间
		String endTime = paramsMap.get("endTime");     //结束时间
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ShglSqmyEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());//数据条数
		Criteria criteria = session.createCriteria(ShglSqmyEntity.class);
		
		//拼接查询条件
		if (null!=startTime && !"".equals(startTime)) {
			criteriaCnt.add(Restrictions.ge("start_date", DateUtils.parseDate(startTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.ge("start_date", DateUtils.parseDate(startTime, "yyyy-MM-dd")));
		}
		if (null!=endTime&&!"".equals(endTime)) {
			criteriaCnt.add(Restrictions.le("end_date", DateUtils.parseDate(endTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.le("end_date", DateUtils.parseDate(endTime, "yyyy-MM-dd")));
		}
			criteriaCnt.add(Restrictions.eq("is_over", "1"));//只查询已经结束的任务
			criteria.add(Restrictions.eq("is_over", "1"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}
	
}
