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
import com.jcwx.dao.shzz.ZzxxDao;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class ZzxxDaoImpl  extends BaseDaoImpl implements ZzxxDao{

	@Override
	public Pagenate<ZzxxEntity> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		String name = map.get("name");   //组织名称
		String fr_name = map.get("fr_name");  //法人名称
		String zzxz = map.get("zzxz");      //组织类型
		String sqid = map.get("sqid");      //社区id
		String fwid = map.get("fwid");      //服务站id
		String gridid = map.get("gridid");  //网格id
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		Criteria criteriaCnt = session.createCriteria(ZzxxEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ZzxxEntity.class);// 数据集
		
		if(!"".equals(name) && name != null){
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if(!"".equals(fr_name) && fr_name != null){
			criteria.add(Restrictions.like("fr_name", fr_name, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("fr_name", fr_name, MatchMode.ANYWHERE));
		}
		if(!"".equals(zzxz) && zzxz != null){
			criteria.add(Restrictions.eq("zzxz", zzxz));
			criteriaCnt.add(Restrictions.eq("zzxz", zzxz));
		}
		if(!"".equals(sqid) && sqid != null){
			criteria.add(Restrictions.eq("sqid", sqid));
			criteriaCnt.add(Restrictions.eq("sqid", sqid));
		}
		if(!"".equals(fwid) && fwid != null){
			criteria.add(Restrictions.eq("fwid", fwid));
			criteriaCnt.add(Restrictions.eq("fwid", fwid));
		}
		if(!"".equals(gridid) && gridid != null){
			criteria.add(Restrictions.eq("gridid", gridid));
			criteriaCnt.add(Restrictions.eq("gridid", gridid));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("enter_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	@Override
	public List<ZzxxEntity> getList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);// 数据集
		return criteria.list();
	}
	
	@Override
	public List<ZzxxEntity> getShzzByZzjg(String frString) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);// 数据集
		if(!"".equals(frString) && null != frString){
			criteria.add(Restrictions.eq("zzjg", frString));
		}
		return criteria.list();
	}
	
	@Override
	public ZzxxEntity getZzByFrId1(String frId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);// 数据集
		if(!"".equals(frId) && null != frId){
			criteria.add(Restrictions.eq("fr_id", frId));
		}
		return (ZzxxEntity) criteria.uniqueResult();
	}

	@Override
	public List<ZzxxEntity> getZzByFrId(String frId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);// 数据集
		if(!"".equals(frId) && null != frId){
			criteria.add(Restrictions.eq("fr_id", frId));
		}
		return criteria.list();
	}

}
