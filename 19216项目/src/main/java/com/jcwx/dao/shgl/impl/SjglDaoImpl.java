package com.jcwx.dao.shgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.SjglDao;
import com.jcwx.entity.shgl.EventEntity;
import com.jcwx.entity.shgl.EventFlowRecordEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class SjglDaoImpl extends BaseDaoImpl implements SjglDao {

	@Override
	public Pagenate<EventEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String applyTime = map.get("applyTime");// 上报i时间
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String gridId = map.get("gridId");// 网格id
		String accCode = map.get("accCode");// 用户code
		String roleCode = map.get("roleCode");// 用户角色

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(EventEntity.class).createAlias("grid", "grid")
				.createAlias("eventStastus", "status");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(EventEntity.class).createAlias("grid", "grid")
				.createAlias("eventStastus", "status");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("apply_name", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("apply_name", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("apply_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("apply_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("comm_id", commId));
			criteria.add(Restrictions.eq("comm_id", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("grid.serviceStation.id", ssId));
			criteria.add(Restrictions.eq("grid.serviceStation.id", ssId));
		}
		if (gridId != null && !"".equals(gridId)) {
			criteriaCnt.add(Restrictions.eq("grid.id", gridId));
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		
		if (roleCode != null && !"".equals(roleCode)) {
			String roles [] = roleCode.split(",");
			for(String role : roles){
			    if(!"99".equals(role)){
			    	criteriaCnt.add(Restrictions.or(Restrictions.eq("status.status", "2"),
							Restrictions.or(Restrictions.eq("status.curent_node", accCode))));
					criteria.add(Restrictions.or(Restrictions.eq("status.status", "2"),
							Restrictions.or(Restrictions.eq("status.curent_node", accCode))));
			    }
			}
		}
		// 排序
		criteria.addOrder(Order.asc("status.status"));
		criteria.addOrder(Order.desc("apply_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<EventEntity> findAllEvents(Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String gridId = map.get("gridId");// 网格id
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventEntity.class).createAlias("grid", "grid", CriteriaSpecification.LEFT_JOIN)
				.createAlias("eventStastus", "status", CriteriaSpecification.LEFT_JOIN);// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("apply_name", title, MatchMode.ANYWHERE))));
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("comm_id", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("grid.serviceStation.id", ssId));
		}
		if (gridId != null && !"".equals(gridId)) {
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		// 排序
		criteria.addOrder(Order.asc("status.status"));
		criteria.addOrder(Order.desc("apply_time"));
		return criteria.list();
	}

	@Override
	public EventFlowRecordEntity findByPara(Map<String, String> map) {
		// TODO Auto-generated method stub
		String statusId = map.get("statusId");// 流转状态id
		String accCode = map.get("accCode");// 处理人
		String done_status = map.get("done_status");// 处理状态
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteria = session.createCriteria(EventFlowRecordEntity.class);// 数据集
		if (statusId != null && !"".equals(statusId)) {
			criteria.add(Restrictions.eq("status_id", statusId));
		}
		if (accCode != null && !"".equals(accCode)) {
			criteria.add(Restrictions.eq("done_code", accCode));
		}
		if (done_status != null && !"".equals(done_status)) {
			criteria.add(Restrictions.eq("done_status", done_status));
		}
		return (EventFlowRecordEntity) criteria.uniqueResult();
	}

}
