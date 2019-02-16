package com.jcwx.dao.dflz.impl;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.dflz.AccessoryDao;
import com.jcwx.entity.dflz.AccessoryEntity;
/**
 * 党政要闻附件Dao实现类
 * @author 李伟
 * @time 2017年10月26日上午10:31:17
 */
@SuppressWarnings("unchecked")
@Repository
public class AccessoryDaoImpl extends BaseDaoImpl implements AccessoryDao{

	@Override
	public List<AccessoryEntity> findByYwId(String yw_id) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(AccessoryEntity.class);
		if (null != yw_id && !"".equals(yw_id)) {
			criteria.add(Restrictions.eq("yw_id", yw_id));
		}
		
		return criteria.list();
	/*	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "FROM AccessoryEntity A WHERE A.yw_id = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0,yw_id);
		return query.list();*/
	}
		
}
