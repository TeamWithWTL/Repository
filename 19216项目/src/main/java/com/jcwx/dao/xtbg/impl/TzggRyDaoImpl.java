/**
 * 
 */
package com.jcwx.dao.xtbg.impl;

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
import com.jcwx.dao.xtbg.TzggRyDao;
import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class TzggRyDaoImpl extends BaseDaoImpl implements TzggRyDao{

	@Override
	public List<TzggRyEntity> findByAccCode(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(TzggRyEntity.class);// 数据集
		criteria.add(Restrictions.eq("receivce_id", id));
		return criteria.list();
	}

	@Override
	public Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String title = map.get("title").toString();// 标题/发布人
		String[] tzggIDs = (String[])map.get("tzggIDs");// 通知公告Id
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
		if(tzggIDs.length>0){
			criteriaCnt.add(Restrictions.in("id", tzggIDs));
			criteria.add(Restrictions.in("id", tzggIDs));
		}
		// 排序
		/*criteria.addOrder(Order.asc("status.status"));
		criteria.addOrder(Order.desc("apply_time"));*/
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

}
