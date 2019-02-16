package com.jcwx.dao.dflz.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.dflz.AppExposureDao;
import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.utils.Pagenate;
/**
 * app曝光台Dao接口实现
 * @author 李伟
 * @time 2017年11月8日下午5:46:50
 */
@Repository
@SuppressWarnings("unchecked")
public class AppExposureDaoImpl extends BaseDaoImpl implements AppExposureDao{
	/**
	 *查询已审核信息
	 */
	@Override
	public Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map) {
		String title = map.get("title");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ExposureEntity.class).setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ExposureEntity.class);
		if (title!=null &&!"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.like("user_name", title,MatchMode.ANYWHERE))));
							
			criteria.add(Restrictions.or(Restrictions.like("title", title,MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.like("user_name", title,MatchMode.ANYWHERE))));
							
		}
		criteriaCnt.add(Restrictions.eq("sh_status", "1"));//1为已审核
		criteria.add(Restrictions.eq("sh_status", "1"));//1为已审核
		criteria.addOrder(Order.desc("create_time"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}
	
}
