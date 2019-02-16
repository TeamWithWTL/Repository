package com.jcwx.dao.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtgl.JsglDao;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
* @author MaBo
* 2017年5月26日
* 系统管理-角色管理
*/
@SuppressWarnings("unchecked")
@Repository
public class JsglDaoImpl extends BaseDaoImpl implements JsglDao {

	@Override
	public Pagenate<SysRole> findRoleByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String roleCode = paramsMap.get("roleCode");	// 角色编码
		String roleName = paramsMap.get("roleName");	// 角色名称
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysRole.class);	// 数据条数
		criteriaCnt.setProjection(Projections.rowCount());
		
		Criteria criteria = session.createCriteria(SysRole.class);		// 数据集
		
		// 组织查询条件
		if(roleCode != null && !roleCode.equals("")){
			criteriaCnt.add(Restrictions.like("roleCode", roleCode,MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("roleCode", roleCode,MatchMode.ANYWHERE));
		}
		if(roleName != null && !roleName.equals("")){
			criteriaCnt.add(Restrictions.like("roleName", roleName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("roleName", roleName, MatchMode.ANYWHERE));
		}
		
		// 按照序号正序排序
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public void delRightsByRoleCode(String roleCode) {
		String hql = "delete from SysRoleMenu where roleCode=?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, roleCode);
		query.executeUpdate();
	}

	@Override
	public List<SysRoleMenu> findRoleMenuByParams(Map<String, String> params) {
		String roleCode = params.get("roleCode");	// 角色编码
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteria = session.createCriteria(SysRoleMenu.class);
		
		if(roleCode != null && !roleCode.equals("")){
			criteria.add(Restrictions.eq("roleCode", roleCode));
		}
		
		return criteria.list();
	}

	@Override
	public List<SysRole> findall() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteria = session.createCriteria(SysRole.class);

		criteria.add(Restrictions.ne("roleCode", ProjectUtils.getSysCfg("sysRoleCode")));
		return criteria.list();
	}

}
