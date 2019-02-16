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
import com.jcwx.dao.dflz.AppDzywDao;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.utils.Pagenate;

/**
 * app党政要闻Dao实现
 * 
 * @author 李伟
 * @time 2017年11月8日下午4:48:37
 */
@Repository
@SuppressWarnings("unchecked")
public class AppDzywDaoImpl extends BaseDaoImpl implements AppDzywDao {
	/**
	 * 查询已经审核的要闻
	 */
	@Override
	public Pagenate<DzywEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(DzywEntity.class);
		criteriaCnt.setProjection(Projections.rowCount()); // 查询记录数
		Criteria criteria = session.createCriteria(DzywEntity.class);// 查询记录
		if (title != null && !"".equals(title)) { // 拼接条件查询
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
		}
		criteriaCnt.add(Restrictions.eq("sh_status", "1"));// 只查询已通过审核的记录
		criteria.add(Restrictions.eq("sh_status", "1"));
		criteria.addOrder(Order.desc("create_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

}
