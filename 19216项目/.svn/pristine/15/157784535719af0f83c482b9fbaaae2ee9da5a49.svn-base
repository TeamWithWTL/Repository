package com.jcwx.dao.dflz.impl;

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
import com.jcwx.dao.dflz.AppComplainDao;
import com.jcwx.entity.dflz.CompAcceEntity;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.utils.Pagenate;
/**
 * app投诉举报Dao接口实现类
 * @author 李伟
 * @time 2017年11月9日上午8:26:53
 */
@Repository
@SuppressWarnings("unchecked")
public class AppComplainDaoImpl extends BaseDaoImpl implements AppComplainDao{
	/**
	 * 查询投诉举报信息
	 */
	@Override
	public Pagenate<ComplainEntity> findByPage(Integer pageNum, int pageSize, Map<String, String> hashMap) {
		String accCode = hashMap.get("accCode");
		String title = hashMap.get("title");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ComplainEntity.class).setProjection(Projections.rowCount());//查询条数
		Criteria criteria = session.createCriteria(ComplainEntity.class);//查询信息列表
		if (title!=null&&!"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title,MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.like("user_name", title,MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title,MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.like("user_name",title,MatchMode.ANYWHERE))))		;		
		}
		if (accCode!=null &&!"".equals(accCode)) {//只查询当前用户投诉的信息
			criteriaCnt.add(Restrictions.eq("user_id", accCode));
			criteria.add(Restrictions.eq("user_id",accCode));
		}
		criteria.addOrder(Order.desc("create_time"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}
	/**
	 * 根据角色id查询用户list
	 */
	@Override
	public List<SysAccCount> findByRolecode(String roleCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		//criteria.add(Restrictions.eq("roleCode", roleCode));
		criteria.add(Restrictions.or(Restrictions.like("role_code", roleCode+",",MatchMode.ANYWHERE),
				Restrictions.or(Restrictions.eq("role_code", roleCode))));
		return criteria.list();
	}
	
	/**
	 * app添加修改投诉附件
	 */
	@Override
	public void saveOrUpdateCompArrt(CompAcceEntity compAcceEntity) {
		try {
			super.saveOrUpdate(compAcceEntity);
			//return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return false;
	}
	

}
