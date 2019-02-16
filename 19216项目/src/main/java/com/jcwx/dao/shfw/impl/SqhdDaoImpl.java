package com.jcwx.dao.shfw.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shfw.SqhdDao;
import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区活动 DaoImpl 
 * @author zhangkai
 *
 */
@Repository
@Transactional
public class SqhdDaoImpl extends BaseDaoImpl implements SqhdDao {

	@Override
	public Pagenate<ShfwSqhdEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		
		/*Date date = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//转换格式
		String currentDate = sdf.format(date);*/
		
		//查询条件
		String title = paramsMap.get("title");		//标题
		String code = paramsMap.get("app");//app标识
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwSqhdEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwSqhdEntity.class);	//数据集
		
		if(title != null && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteriaData.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if(code != null && !code.equals("") && code == "app"){//app列表显示
			criteriaCnt.add(Restrictions.eq("shStatus","1"));
			criteriaData.add(Restrictions.eq("shStatus","1"));
			/*criteriaCnt.add(Restrictions.and(Restrictions.le("startDate",DateUtils.parseDate(currentDate, "yyyy-MM-dd")), Restrictions.ge("endDate",DateUtils.parseDate(currentDate, "yyyy-MM-dd"))));
			criteriaData.add(Restrictions.and(Restrictions.le("startDate",DateUtils.parseDate(currentDate, "yyyy-MM-dd")), Restrictions.ge("endDate",DateUtils.parseDate(currentDate, "yyyy-MM-dd"))));*/
		}
		
		criteriaData.addOrder(Order.desc("createDate"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public ShfwSqhdEntity getByIsHot(String isHot) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShfwSqhdEntity.class);
		criteria.add(Restrictions.eq("isHot", "1"));
		return (ShfwSqhdEntity) criteria.uniqueResult();
	}

	@Override
	public List<ShfwSqhdEntity> getHdList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShfwSqhdEntity.class);
		return criteria.list();
	}

	@Override
	public List<ShglCommunityEntity> getCommList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class);
		return criteria.list();
	}
	
}
