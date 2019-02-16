package com.jcwx.dao.shfw.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shfw.YjlyDao;
import com.jcwx.entity.shfw.ShfwSqhdYjAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区活动意见留言 DaoImpl 
 * @author zhangkai 
 *
 */
@Repository
public class YjlyDaoImpl extends BaseDaoImpl implements YjlyDao {

	@Override
	public Pagenate<ShfwSqhdYjEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String sqhdId = paramsMap.get("sqhdId");	//社区活动ID
		String code = paramsMap.get("app");//app标识
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdYjEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdYjEntity.class);	//数据集
		
		if(sqhdId != null && !sqhdId.equals("")){
			criteriaCnt.add(Restrictions.eq("sqhdId", sqhdId));
			criteriaData.add(Restrictions.eq("sqhdId", sqhdId));
		}
		//if(code != null && !code.equals("") && code == "app"){
			criteriaCnt.add(Restrictions.eq("shStatus", "1"));
			criteriaData.add(Restrictions.eq("shStatus", "1"));
		//}
		
		criteriaData.addOrder(Order.desc("createTime"));
				
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}
	
	@Override
	public Pagenate<ShfwSqhdYjEntity> findDshLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String sqhdId = paramsMap.get("sqhdId");	//社区活动ID
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdYjEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdYjEntity.class);	//数据集
		
		if(sqhdId != null && !sqhdId.equals("")){
			criteriaCnt.add(Restrictions.eq("sqhdId", sqhdId));
			criteriaData.add(Restrictions.eq("sqhdId", sqhdId));
		}
		criteriaCnt.add(Restrictions.eq("shStatus", "0"));
		criteriaData.add(Restrictions.eq("shStatus", "0"));
		
		criteriaCnt.add(Restrictions.eq("isBack", "1"));
		criteriaData.add(Restrictions.eq("isBack", "1"));
		
		criteriaData.addOrder(Order.desc("createTime"));
				
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> findBtgLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String sqhdId = paramsMap.get("sqhdId");	//社区活动ID
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdYjEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdYjEntity.class);	//数据集
		
		if(sqhdId != null && !sqhdId.equals("")){
			criteriaCnt.add(Restrictions.eq("sqhdId", sqhdId));
			criteriaData.add(Restrictions.eq("sqhdId", sqhdId));
		}
		criteriaCnt.add(Restrictions.eq("shStatus", "2"));
		criteriaData.add(Restrictions.eq("shStatus", "2"));
		
		criteriaData.addOrder(Order.desc("createTime"));
				
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> findWpfLyByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String sqhdId = paramsMap.get("sqhdId");	//社区活动ID
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdYjEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdYjEntity.class);	//数据集
		
		if(sqhdId != null && !sqhdId.equals("")){
			criteriaCnt.add(Restrictions.eq("sqhdId", sqhdId));
			criteriaData.add(Restrictions.eq("sqhdId", sqhdId));
		}
		criteriaCnt.add(Restrictions.and(Restrictions.eq("shStatus", "1"),Restrictions.eq("integral", 0)));
		criteriaData.add(Restrictions.and(Restrictions.eq("shStatus", "1"),Restrictions.eq("integral", 0)));
		
		criteriaData.addOrder(Order.desc("createTime"));
				
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjBySqhdId(String sqhdId, String userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShfwSqhdYjEntity.class);
		criteria.add(Restrictions.eq("sqhdId", sqhdId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isSignup", "2"));
		criteria.add(Restrictions.eq("isBack", "1"));
		return (ShfwSqhdYjEntity) criteria.uniqueResult();
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjByWfk(String sqhdId, String userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShfwSqhdYjEntity.class);
		criteria.add(Restrictions.eq("sqhdId", sqhdId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isSignup", "1"));
		criteria.add(Restrictions.eq("isBack", "2"));
		return (ShfwSqhdYjEntity) criteria.uniqueResult();
	}

	@Override
	public ShfwSqhdYjEntity getSqhdYjByYfk(String sqhdId, String userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShfwSqhdYjEntity.class);
		criteria.add(Restrictions.eq("sqhdId", sqhdId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isSignup", "1"));
		criteria.add(Restrictions.eq("isBack", "1"));
		return (ShfwSqhdYjEntity) criteria.uniqueResult();
	}

	@Override
	public Pagenate<ShfwSqhdYjEntity> getSqhdBmjlByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String sqhdId = paramsMap.get("sqhdId");	//社区活动ID
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdYjEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdYjEntity.class);	//数据集
		
		if(sqhdId != null && !sqhdId.equals("")){
			criteriaCnt.add(Restrictions.eq("sqhdId", sqhdId));
			criteriaData.add(Restrictions.eq("sqhdId", sqhdId));
		}
		criteriaCnt.add(Restrictions.eq("isSignup", "1"));
		criteriaData.add(Restrictions.eq("isSignup", "1"));
		
		criteriaData.addOrder(Order.desc("createTime"));
				
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public void saveOrUpdateAttrs(ShfwSqhdYjAttrsEntity attrs) {
		try {
			super.saveOrUpdate(attrs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
