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
import com.jcwx.dao.xtgl.CzanglDao;
import com.jcwx.entity.pub.SysMethod;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年5月31日
* 系统管理-操作按钮管理
*/
@SuppressWarnings("unchecked")
@Repository
public class CzanglDaoImpl extends BaseDaoImpl implements CzanglDao {

	@Override
	public Pagenate<SysMethod> findMetByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		String metName = paramsMap.get("metName");		// 功能名称
		String menuCode = paramsMap.get("menuCode");	// 所属菜单编号
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		// 查询数据条数
		Criteria criteriaCnt = session.createCriteria(SysMethod.class);
		criteriaCnt.setProjection(Projections.rowCount());
		
		// 查询数据集
		Criteria criteria = session.createCriteria(SysMethod.class);
		
		// 组织查询条件
		if(metName != null && !metName.equals("")){
			criteriaCnt.add(Restrictions.like("methodName", metName, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("methodName", metName, MatchMode.ANYWHERE));
		}
		if(menuCode != null && !menuCode.equals("")){
			criteriaCnt.add(Restrictions.eq("menuCode", menuCode));
			criteria.add(Restrictions.eq("menuCode", menuCode));
		}

		// 根据菜单编码排序
		criteria.addOrder(Order.asc("menuCode"));
		// 根据序号正序排
		criteria.addOrder(Order.asc("orderNo"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<SysRoleMenu> findRoleMenuByMenu(String menuCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysRoleMenu.class).createCriteria("menu", "menu");
		criteria.add(Restrictions.eq("menu.menuCode", menuCode));
		return criteria.list();
	}

}
