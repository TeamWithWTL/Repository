package com.jcwx.dao.sjzx.tsrk.impl;


import org.springframework.stereotype.Repository;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.jcwx.dao.BaseDao;
import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.sjzx.tsrk.TsrkDao;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.utils.Pagenate;



@SuppressWarnings("unused")
@Repository
public class TsrkDaoImpl extends BaseDaoImpl implements TsrkDao{

	/**
	 * 特殊人口数量统计
	 */
	@Override
	public int findRkCnt(String commId, String ssId,String category) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria =session.createCriteria(ShglInmateEntity.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("ssId", ssId));
		criteria.add(Restrictions.eq("commId", commId));
		criteria.add(Restrictions.eq("category", category));
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public Pagenate<ShglInmateEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> params) {
		String commId = params.get("commId");	//社区Id
		String ssId = params.get("ssId");	//服务站Id
//		String inmateTList = params.get("inmateTList");//人员分类
	
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ShglInmateEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ShglInmateEntity.class);
		
		if(commId !=null && !commId.equals("")){
			criteria.add(Restrictions.eq("commId", commId));
			criteriaCnt.add(Restrictions.eq("commId", commId));
		}
		
		if(ssId !=null && !ssId.equals("")){
			criteria.add(Restrictions.eq("ssId", ssId));
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
		}
		
//		if(inmateTList !=null && !inmateTList.equals("")){
//			criteria.add(Restrictions.eq("inmateTList", inmateTList));
//			criteriaCnt.add(Restrictions.eq("inmateTList", inmateTList));
//		}
		
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	/**
	 * 房屋出租数量
	 */
	@Override
	public int findCountFwcz(Map<String, String> addMap) {
		String sqId = addMap.get("sqId");//社区id
		String fwzId = addMap.get("fwzId");//服务站ID 
		String xq_id  = addMap.get("xq_id");//小区id 
		String zfsy_type = addMap.get("zfsy_type");//房屋使用类别
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyZhEntity.class).createAlias("sqmyzhdc", "sqmyzhdc");
		if(!"".equals(sqId) && null != sqId){
			criteria.add(Restrictions.eq("sq_id", sqId));
		}
		if(!"".equals(fwzId) && null != fwzId){
			criteria.add(Restrictions.eq("fwz_id", fwzId));
		}
		if(!"".equals(xq_id) && null != xq_id){
			criteria.add(Restrictions.eq("xq_id", xq_id));
		}
		
		if(!"".equals(zfsy_type) && null != zfsy_type){
			criteria.add(Restrictions.eq("sqmyzhdc.zfsy_type", '1'));
		}
		return criteria.list().size();
	}

	/**
	 * 服务站房屋出租数量
	 */
	@Override
	public int findSSfwcz(String sqId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyZhEntity.class);
		if(!"".equals(sqId) && null != sqId){
			criteria.add(Restrictions.eq("ssId", sqId));
		}
		return criteria.list().size();
	}

}
