/**
 * 
 */
package com.jcwx.dao.xtbg.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtbg.TzggDao;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */

@SuppressWarnings("unchecked")
@Repository
public class TzggDaoImpl extends BaseDaoImpl implements TzggDao{

	/* (non-Javadoc)
	 * @see com.jcwx.dao.xtbg.TzggDao#getTzggContent(java.lang.Integer, int, java.util.Map)
	 */
	@Override
	public Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(TzggEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(TzggEntity.class);// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("createUserName", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("createUserName", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("createDate", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("createDate", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		// 排序
		/*criteria.addOrder(Order.asc("status.status"));
		criteria.addOrder(Order.desc("apply_time"));*/
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

}
