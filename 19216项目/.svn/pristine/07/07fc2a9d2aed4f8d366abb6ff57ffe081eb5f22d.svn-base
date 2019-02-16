package com.jcwx.dao.shfw.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shfw.JftjDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwJflsEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-积分统计 DaoImpl 
 * @author zhangkai
 *
 */
@Repository
public class JftjDaoImpl extends BaseDaoImpl implements JftjDao {

	@Override
	public Pagenate<SysAccCountLazy> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String name = paramsMap.get("name");	//姓名
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysAccCountLazy.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(SysAccCountLazy.class);	//数据集
		
		//只查询普通用户
		/*criteriaCnt.add(Restrictions.eq("accCode", "14"));
		criteriaData.add(Restrictions.eq("accCode", "14"));*/
		/*criteriaCnt.add(Restrictions.or(Restrictions.eq("role_code", "14"),
				Restrictions.or(Restrictions.like("role_code", "14,",MatchMode.ANYWHERE))));
		criteriaData.add(Restrictions.or(Restrictions.eq("role_code", "14"),
				Restrictions.or(Restrictions.like("role_code", "14,",MatchMode.ANYWHERE))));*/
		
		if(name != null && !name.equals("")){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteriaData.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}

	@Override
	public Pagenate<ShfwJflsEntity> getByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String accCode = paramsMap.get("accCode");	//用户ID
		String startDate = paramsMap.get("startDate");  //开始日期
		String endDate = paramsMap.get("endDate");     //结束日期
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwJflsEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwJflsEntity.class);	//数据集、
		
		if(startDate.equals(endDate)){
			criteriaCnt.add(Restrictions.between("createDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"),
					DateUtils.nextDate(startDate, "yyyy-MM-dd")));
			criteriaData.add(Restrictions.between("createDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"),
					DateUtils.nextDate(startDate, "yyyy-MM-dd")));
		}else{
			if (startDate != null && !"".equals(startDate)) {
				criteriaCnt.add(Restrictions.ge("createDate", DateUtils.parseDate(startDate, "yyyy-MM-dd")));
				criteriaData.add(Restrictions.ge("createDate", DateUtils.parseDate(startDate, "yyyy-MM-dd")));
			}
			if (endDate != null && !"".equals(endDate)) {
				criteriaCnt.add(Restrictions.le("createDate", DateUtils.parseDate(endDate, "yyyy-MM-dd")));
				criteriaData.add(Restrictions.le("createDate", DateUtils.parseDate(endDate, "yyyy-MM-dd")));
			}
		}
		
		if(accCode != null && !accCode.equals("")){
			criteriaCnt.add(Restrictions.eq("accCode", accCode));
			criteriaData.add(Restrictions.eq("accCode", accCode));
		}
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}
	
}
