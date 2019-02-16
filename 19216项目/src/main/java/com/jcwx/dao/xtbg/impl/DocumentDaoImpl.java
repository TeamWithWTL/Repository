package com.jcwx.dao.xtbg.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtbg.DocumentDao;
import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.utils.Pagenate;

/**
 * 公文处理
 * @author jiangkia
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class DocumentDaoImpl extends BaseDaoImpl implements DocumentDao{

	@Override
	public Pagenate<Document> findDocumentByPage(
			Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题
		String createUserId = map.get("createUserId");// 创建人ID
		String isOver = map.get("isOver");// 是否结束
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(Document.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(Document.class);
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if (createUserId != null && !"".equals(createUserId)) {
			criteriaCnt.add(Restrictions.eq("createUserId", createUserId));
			criteria.add(Restrictions.eq("createUserId", createUserId));
		}
		if (isOver != null && !"".equals(isOver)) {
			criteriaCnt.add(Restrictions.eq("isOver", isOver));
			criteria.add(Restrictions.eq("isOver", isOver));
		}
		// 排序
		criteria.addOrder(Order.desc("createDate"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<DocumentDeal> findDocumentDealByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {

		String title = map.get("title");// 标题/上报人
		String clrId = map.get("clrId");// 处理人Id
		String status = map.get("status");// 处理状态
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(DocumentDeal.class).createAlias("document", "document");
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(DocumentDeal.class).createAlias("document", "document");
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("document.title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("document.title", title, MatchMode.ANYWHERE));
		}
		
		if (clrId != null && !"".equals(clrId)) {
			criteriaCnt.add(Restrictions.eq("clrId", clrId));
			criteria.add(Restrictions.eq("clrId", clrId));
		}
		if (status != null && !"".equals(status)) {
			criteriaCnt.add(Restrictions.eq("status", status));
			criteria.add(Restrictions.eq("status", status));
		}
		
		// 排序
		criteria.addOrder(Order.desc("createDate"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	
	}
	
}
