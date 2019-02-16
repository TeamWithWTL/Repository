package com.jcwx.dao.pub.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.pub.DeptPersonDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.utils.Pagenate;

/** 
* 
* 部门人员管理 DaoImpl
* @author zhangkai
* 
*/
@SuppressWarnings("unchecked")
@Repository
public class DeptPersonDaoImpl extends BaseDaoImpl implements DeptPersonDao {

	@Override
	public Pagenate<SysAccCountLazy> getByPage(int pageNum, int pageSize, Map<String, String> map) {
		//查询条件
		String deptId = map.get("deptId");	//所属部门ID
		String name = map.get("name");	//姓名
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysAccCountLazy.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);
		
		if(deptId != null && !deptId.equals("")){
			criteriaCnt.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("deptId", deptId));
		}
		if(name != null && !name.equals("")){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	/*@Override
	public Pagenate<SysDepartmentPersonelEntity> getByPage(int pageNum, int pageSize, Map<String, String> map) {
		// TODO Auto-generated method stub
		//查询条件
		String deptId = map.get("deptId");	//所属部门ID
		String name = map.get("name");	//姓名
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysDepartmentPersonelEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		Criteria criteria = session.createCriteria(SysDepartmentPersonelEntity.class);
		
		if(deptId != null && !deptId.equals("")){
			criteriaCnt.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("deptId", deptId));
		}
		if(name != null && !name.equals("")){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}*/

	@Override
	public Pagenate<SysAccCountLazy> findByPage(int pageNum, int pageSize, Map<String, String> map) {
		//查询条件
		String deptId = map.get("deptId");	//所属部门ID
		String name = map.get("name");	//姓名
		String roleCode = map.get("roleCode");	//角色ID
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysAccCountLazy.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);
		
		
		criteriaCnt.add(Restrictions.eq("zh_type", "4"));
		criteria.add(Restrictions.eq("zh_type", "4"));
		
		if(deptId != null && !deptId.equals("")){
			criteriaCnt.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("deptId", deptId));
		}
		if(name != null && !"".equals(name)){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if(roleCode != null && !"".equals(roleCode)){
			criteriaCnt.add(Restrictions.like("role_code", roleCode, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("role_code", roleCode, MatchMode.ANYWHERE));
		}
		criteriaCnt.addOrder(Order.asc("orderNo"));
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}
	
}
