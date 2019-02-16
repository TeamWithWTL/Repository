package com.jcwx.dao.xtgl.impl;

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
import com.jcwx.dao.xtgl.DeptglDao;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.utils.Pagenate;

/** 
* 
* 部门管理 DaoImpl
* @author zhangkai
* 
*/
@SuppressWarnings("unchecked")
@Repository
public class DeptglDaoImpl extends BaseDaoImpl implements DeptglDao {

	@Override
	public Pagenate<SysDepartment> findDepartmentByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		String deptId = paramsMap.get("deptId");	// 组织编号
		String deptName = paramsMap.get("deptName");	// 组织名称
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		// 查询数据条数
		Criteria criteriaCnt = session.createCriteria(SysDepartment.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		// 查询数据集
		Criteria criteria = session.createCriteria(SysDepartment.class);
		
		// 组织查询条件
		if(deptId != null && !deptId.equals("") && !deptId.equals("root")){
			criteriaCnt.add(Restrictions.or(Restrictions.eq("parentId", deptId), Restrictions.eq("deptId", deptId)));
			criteria.add(Restrictions.or(Restrictions.eq("parentId", deptId), Restrictions.eq("deptId", deptId)));
		}
		if(deptName != null && !deptName.equals("")){
			criteriaCnt.add(Restrictions.like("deptName", deptName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("deptName", deptName, MatchMode.ANYWHERE));
		}
		
		// 排序
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<SysDepartment> findByParentId(String pid) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("parentId", pid));
		return criteria.list();
	}

}
