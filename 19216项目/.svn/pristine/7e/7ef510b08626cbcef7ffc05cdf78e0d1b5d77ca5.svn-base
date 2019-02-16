package com.jcwx.dao.pub.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.pub.DepartmentDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysDepartment;
import com.sun.star.i18n.reservedWords;

/** 
* 
* 部门树 DaoImpl
* @author zhangkai
* 
*/
@SuppressWarnings("unchecked")
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl implements DepartmentDao {

	@Override
	public List<SysDepartment> findDeptByParams(Map<String, String> params) {
		// TODO Auto-generated method stub
		String deptId = params.get("deptId");	//部门编号
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysDepartment.class);
		if(deptId != null && !deptId.equals("")){
			criteria.add(Restrictions.or(Restrictions.eq("deptId", deptId), Restrictions.eq("parentId", deptId)));
		}
		criteria.addOrder(Order.asc("orderNo"));
		return criteria.list();
	}

	@Override
	public List<SysDepartment> findByPid(String pid) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("parentId", pid));
		criteria.addOrder(Order.asc("orderNo"));
		return criteria.list();
	}
	/**
	 * 根据部门id查询部门人员
	 */
	@Override
	public List<SysAccCountLazy> findAllPsons(String deptId) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCountLazy.class).createAlias("sysDepartment", "dept");
		criteria.add(Restrictions.eq("dept.deptId", deptId));
		return criteria.list();
	}

}
