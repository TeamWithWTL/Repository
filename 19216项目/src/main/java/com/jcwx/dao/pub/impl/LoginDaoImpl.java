package com.jcwx.dao.pub.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.pub.LoginDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysMenu;
import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.entity.shgl.ShglInmateEntity;

/**
* @author MaBo
* 2017年5月24日
* 系统登录
*/
@SuppressWarnings("unchecked")
@Repository
public class LoginDaoImpl extends BaseDaoImpl implements LoginDao {

	@Override
	public List<SysAccCount> findAccountByParams(Map<String, String> paramsMap) {
		String accCode = paramsMap.get("accCode");	// 用户名
		String pwd = paramsMap.get("pwd");			// 密码
		String roleCode = paramsMap.get("roleCode");			// 密码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		if(accCode != null && !accCode.equals("")){
			criteria.add(Restrictions.eq("accCode", accCode));
		}
		if(pwd != null && !pwd.equals("")){
			criteria.add(Restrictions.eq("pwd", pwd));
		}
		if(roleCode != null && !roleCode.equals("")){
			criteria.add(Restrictions.like("role_code", roleCode));
		}
		return criteria.list();
	}

	@Override
	public List<SysRoleMenu> findRoleMenusByPid(String roleCode, String menuPid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysRoleMenu.class).createAlias("menu", "menu");
		if(roleCode != null && !roleCode.equals("")){
			criteria.add(Restrictions.eq("roleCode", roleCode));
		}
		if(menuPid == null || menuPid.equals("")){
			criteria.add(Restrictions.isNull("menu.parentId"));
		}else{
			criteria.add(Restrictions.eq("menu.parentId", menuPid));
		}
		criteria.addOrder(Order.asc("menu.orderNo"));
		
		return criteria.list();
	}

	@Override
	public List<SysMenu> findMenusByPid(String pid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysMenu.class);
		if(pid == null || pid.equals("")){
			criteria.add(Restrictions.isNull("parentId"));
		}else{
			criteria.add(Restrictions.eq("parentId", pid));
		}
		criteria.addOrder(Order.asc("orderNo"));
		
		return criteria.list();
	}

	@Override
	public SysAccMore findSysMore(Map<String, String> addMap) {
		
		String accCode = addMap.get("accCode");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccMore.class);
		
		if(null != accCode && !"".equals(accCode)){
			criteria.add(Restrictions.eq("accCode", accCode));
		}
		
		SysAccMore accMore = null;
		
		if(criteria.list().size()>0){
			accMore = (SysAccMore) criteria.list().get(0);
		}
		
		return accMore;
	}

	@Override
	public ShglInmateEntity checkCardId(String idCard) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class);
		if(null != idCard && !"".equals(idCard)){
			criteria.add(Restrictions.eq("card_no", idCard));
		}
		return (ShglInmateEntity) criteria.uniqueResult();
	}
}
