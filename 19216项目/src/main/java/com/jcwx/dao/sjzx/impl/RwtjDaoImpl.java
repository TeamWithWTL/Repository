package com.jcwx.dao.sjzx.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.sjzx.RwtjDao;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglTaskDealEntity;
import com.jcwx.utils.Pagenate;
@Repository
public class  RwtjDaoImpl extends BaseDaoImpl implements RwtjDao {
	

	@Override
	public List<ShglCommunityEntity> sqAll() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria=session.createCriteria(ShglCommunityEntity.class);
		return criteria.list();
	}

	@Override
	public List<ShglServiceStationEntity> fwzAll() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria=session.createCriteria(ShglServiceStationEntity.class);
		return criteria.list();
	}

	@Override
	public Pagenate<ShglTaskDealEntity> findRwtjContent(Integer pageNumber, int pagesize, Map<String, String> paramMap) {
		String startTime = paramMap.get("startTime");
		String endTime = paramMap.get("endTime");
		String sqId = paramMap.get("sqId");
		String fwzId = paramMap.get("fwzId");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglTaskDealEntity.class).createAlias("shglTaskEntity", "st");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ShglTaskDealEntity.class).createAlias("shglTaskEntity", "st");// 数据集----不显示黑名单数据
		
		// 组织查询条件
		if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime) &&  startTime.equals(endTime)) {
			criteriaCnt.add(Restrictions.ge("st.createDate", Date.valueOf(startTime)));
			criteria.add(Restrictions.ge("st.createDate", Date.valueOf(startTime)));
		}else{
			if (startTime != null && !"".equals(startTime)) {
					criteriaCnt.add(Restrictions.ge("st.createDate", Date.valueOf(startTime)));
					criteria.add(Restrictions.ge("st.createDate", Date.valueOf(startTime)));
			}
			if(endTime != null && !"".equals(endTime)){
				criteriaCnt.add(Restrictions.lt("st.finishDate", Date.valueOf(endTime)));
				criteria.add(Restrictions.lt("st.finishDate", Date.valueOf(endTime)));
			}
		}
		if (sqId != null && !"".equals(sqId)) {
			criteriaCnt.add(Restrictions.eq("st.sqId", sqId));
			criteria.add(Restrictions.eq("st.sqId", sqId));
		}
		if (fwzId != null && !"".equals(fwzId)) {
			criteriaCnt.add(Restrictions.eq("st.fwzId", fwzId));
			criteria.add(Restrictions.eq("st.fwzId", fwzId));
		}
		criteria.addOrder(Order.desc("st.createDate"));//已创建时间降序
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglGridEntity> wgAll() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria=session.createCriteria(ShglGridEntity.class);
		return criteria.list();
	}

}
