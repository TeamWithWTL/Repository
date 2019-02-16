package com.jcwx.dao.shgl.impl;

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
import com.jcwx.dao.shgl.RwclDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.TaskDealEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class RwclDaoImpl extends BaseDaoImpl implements RwclDao{

	@Override
	public Pagenate<RwClEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题
		String curId = map.get("cur_emp");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rw");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("rw.title", title, MatchMode.ANYWHERE));
		}
		if(null != curId && !"".equals(curId)){
			criteriaCnt.add(Restrictions.eq("cur_emp", curId));
			criteria.add(Restrictions.eq("cur_emp", curId));
			criteriaCnt.add(Restrictions.eq("is_back", "2"));
			criteria.add(Restrictions.eq("is_back", "2"));
		}
		// 时间倒叙排序
		criteria.addOrder(Order.desc("starte_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<RwClEntity> findMyRwNo(Map<String, String> rwMap) {
		String nowDealId = rwMap.get("nowDealId");// 当前处理人ID
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(RwClEntity.class);// 数据集
		
		if (nowDealId != null && !"".equals(nowDealId)) {
			criteria.add(Restrictions.eq("cur_emp",nowDealId));
		}
		criteria.add(Restrictions.eq("is_back", "2"));//未反馈的
		criteria.add(Restrictions.eq("is_down", "2"));//未下发
		criteria.addOrder(Order.desc("starte_date"));
		return criteria.list();
	}
	/**
	 * 多对一，查询我的未处理任务
	 */
	@Override
	public Pagenate<RwClEntity> findClByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		
		String accCode = map.get("accCode");
		String stamp = map.get("stamp");//加入查询标记 1为未处理  2为已处理的任务 
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(RwClEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());//查询条数
		Criteria criteria = session.createCriteria(RwClEntity.class);//查记录
		
		if (accCode!=null&&!"".equals(accCode)) {
			criteriaCnt.add(Restrictions.eq("cur_emp", accCode));//查询当前处理人是当前登录用户的
			criteria.add(Restrictions.eq("cur_emp", accCode));
		}
		if (stamp=="1") {
			criteriaCnt.add(Restrictions.eq("is_back", "2"));//未反馈的
			criteria.add(Restrictions.eq("is_back", "2"));//未反馈的
		}else if (stamp=="2") {
			criteriaCnt.add(Restrictions.eq("is_back", "1"));//已反馈的
			criteria.add(Restrictions.eq("is_back", "1"));//已反馈的
		}
	
		criteria.addOrder(Order.desc("starte_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	/**
	 * 查询当前排序层级的所有角色集合
	 */
	@Override
	public List<SysRole> findSysRoleList(int i) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysRole.class);
		criteria.add(Restrictions.eq("orderNo", i));
		return criteria.list();
	}
	/**
	 * 以角色id查询用户集合
	 */
	@Override
	public List<SysAccCount> findSysAccByRoleCode(String roleCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		if (roleCode!=null &&!"".equals(roleCode)) {
			criteria.add(Restrictions.or(Restrictions.eq("role_code", roleCode),//查询等于此角色的
					Restrictions.or(Restrictions.eq("role_code", roleCode+",")),//查询等于此角色带逗号的
					Restrictions.or(Restrictions.like("role_code", roleCode+",",MatchMode.ANYWHERE))));//查询包含此角色带逗号的
		}
		
		return criteria.list();
	}
	/**
	 * 查询要下发的任务处理信息
	 */
	@Override
	public TaskDealEntity findClByRwglId(String rwglId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(TaskDealEntity.class).createAlias("rwglEntity", "rwgl");
		if (rwglId!=null &&!"".equals(rwglId)) {
			criteria.add(Restrictions.eq("rwgl.id", rwglId));
		}
		criteria.add(Restrictions.eq("is_back", "2"))//未反馈
					.add(Restrictions.eq("is_down", "2"));//未下发
		return (TaskDealEntity) criteria.uniqueResult();
	}

	@Override
	public RwClEntity findRwclById(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(RwClEntity.class);// 数据集
		if(null != id && !"".equals(id)){
			criteria.add(Restrictions.eq("id",id));
		}
		RwClEntity clEntity = null;
		if(criteria.list().size() > 0){
			clEntity = (RwClEntity) criteria.list().get(0);
		}
		return clEntity;
	}
	/**
	 * 查询单个任务的所有处理记录
	 */
	@Override
	public List<RwClEntity> findDealList(String rwglId,String accCode,RwClEntity taskDealEntity,String isWgy) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rwgl");
		
		if (rwglId!=null&&!"".equals(rwglId)) {
			criteria.add(Restrictions.eq("rwgl.id", rwglId));
		}
		
		if ("2".equals(taskDealEntity.getIs_back())) {//任务未反馈时，详情查看，自己下发的任务，否则详情查看，自己处理反馈的
			if ("yes".equals(isWgy)) {
				if (accCode!=null &&!"".equals(accCode)) {
					criteria.add(Restrictions.eq("cur_emp",accCode));
				}
			}else {
				if (accCode!=null &&!"".equals(accCode)) {
					criteria.add(Restrictions.eq("pre_emp",accCode));
				}
			}
			
		}else{
			if (accCode!=null &&!"".equals(accCode)) {
				criteria.add(Restrictions.eq("cur_emp",accCode));
			}
		}
		criteria.addOrder(Order.asc("starte_date"));
		return criteria.list();
	}

	@Override
	public List<RwClEntity> findRwcl(String taskId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rg");
		if (taskId!=null&&!"".equals(taskId)) {
			criteria.add(Restrictions.eq("rg.id", taskId));
		}
		criteria.addOrder(Order.asc("starte_date")).addOrder(Order.desc("cur_role_id"));
		return criteria.list();
	}

	@Override
	public int findNoDealRwCount(Map<String, String> addMap) {
		
		String accCode = addMap.get("accCode");
		String stamp = addMap.get("stamp");//加入查询标记 1为未处理  2为已处理的任务 
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(TaskDealEntity.class);//查记录
		
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("cur_emp", accCode));
		}
		if (stamp=="1") {
			criteria.add(Restrictions.eq("is_back", "2"));//未反馈的
		}else if (stamp=="2") {
			criteria.add(Restrictions.eq("is_back", "1"));//已反馈的
		}
		
		if(criteria.list().size()>0){
			return criteria.list().size();
		}else{
			return 0;
		}
	}

	@Override
	public List<RwClEntity> findRcForDel(String string, String fbrId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(RwClEntity.class).createAlias("rwglEntity", "rg");
		
		if (null != string &&!"".equals(string)) {
			criteria.add(Restrictions.eq("rg.id", string));
		}
		if (null != fbrId &&!"".equals(fbrId)) {
			criteria.add(Restrictions.eq("pre_emp", fbrId));
		}
		return criteria.list();
	}
}
