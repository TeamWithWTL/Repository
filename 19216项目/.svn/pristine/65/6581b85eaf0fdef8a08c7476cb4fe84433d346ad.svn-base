package com.jcwx.dao.shzz.impl;

import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shzz.ZxjsDao;
import com.jcwx.entity.shzz.ZxjsEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
@SuppressWarnings("unused")
@Repository
public class ZxjsDaoImpl extends BaseDaoImpl implements ZxjsDao {

	
	@Override
	public Pagenate<ZxjsEntity> getZxjsContent(Integer pageNumber, int pageSize, Map<String, String> map) {
		//查询条件
		String title = map.get("title");
		String createTimes = map.get("createTimes");// 发布时间
		String appCode = map.get("appCode");//手机端标识
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ZxjsEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ZxjsEntity.class);// 数据集
	
		if(null != title && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if (createTimes != null && !"".equals(createTimes)) {
			criteriaCnt.add(Restrictions.between("createTime", DateUtils.parseDate(createTimes, "yyyy-MM-dd"),
					DateUtils.nextDate(createTimes, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("createTime", DateUtils.parseDate(createTimes, "yyyy-MM-dd"),
					DateUtils.nextDate(createTimes, "yyyy-MM-dd")));
		}
		if(null != appCode && !"".equals(appCode)){
			criteriaCnt.add(Restrictions.eq("shStatus", "1"));
			criteria.add(Restrictions.eq("shStatus", "1"));
		}
		
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}



}
