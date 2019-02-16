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
import com.jcwx.dao.xtgl.XtcdglDao;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月27日
* 系统管理-系统菜单管理
*/
@SuppressWarnings("unchecked")
@Repository
public class XtcdglDaoImpl extends BaseDaoImpl implements XtcdglDao {

	@Override
	public Pagenate<SysMenu> findMenuByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String menuName = paramsMap.get("menuName");	// 菜单名称
		String menuCode = paramsMap.get("menuCode");	// 菜单编号
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(SysMenu.class);	// 数据条数
		criteriaCnt.setProjection(Projections.rowCount());
		
		Criteria criteria = session.createCriteria(SysMenu.class);		// 数据集
		
		// 组织查询条件
		if(menuName != null && !menuName.equals("")){
			criteriaCnt.add(Restrictions.like("menuName", menuName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("menuName", menuName, MatchMode.ANYWHERE));
		}
		if(menuCode != null && !menuCode.equals("")){
			criteriaCnt.add(Restrictions.or(Restrictions.eq("menuCode", menuCode), Restrictions.eq("parentId", menuCode)));
			criteria.add(Restrictions.or(Restrictions.eq("menuCode", menuCode), Restrictions.eq("parentId", menuCode)));
		}
		
		// 按照序号正序排
		criteria.addOrder(Order.asc("parentId"));
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<String> findRoleMenuByMenuCode(String menuCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysRoleMenu.class).createAlias("menu", "menu");
		criteria.setProjection(Projections.distinct(Projections.property("roleCode")));
		criteria.add(Restrictions.eq("menu.menuCode", menuCode));
		return criteria.list();
	}

}
