package com.jcwx.dao.shzz.impl;

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
import com.jcwx.dao.shzz.ZxzmDao;
import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class ZxzmDaoImpl extends BaseDaoImpl implements ZxzmDao{

	@Override
	public Pagenate<ZxzmEntity> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		String title = map.get("title"); //标题
		String code = map.get("code");//区分手机端，手机端只查看审核通过的数据
	    Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		Criteria criteriaCnt = session.createCriteria(ZxzmEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ZxzmEntity.class);// 数据集
		
		if(title != null && !"".equals(title)){
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if(null != code && !"".equals(code) && "app".equals(code)){
			criteria.add(Restrictions.eq("sh_status", "1"));
			criteriaCnt.add(Restrictions.eq("sh_status", "1"));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("create_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ZxzmEntity> getContent() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZxzmEntity.class);// 数据集
//		String isHot = "1";
//		criteria.add(Restrictions.eq("is_hot", isHot));//是热点
		criteria.addOrder(Order.desc("create_time"));//时间降序
		return criteria.list();
	}

}
