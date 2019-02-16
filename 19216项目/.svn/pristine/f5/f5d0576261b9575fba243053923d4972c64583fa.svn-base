package com.jcwx.dao.shgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.FwzglDao;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class FwzglDaoImpl extends BaseDaoImpl implements FwzglDao {

	@Override
	public Pagenate<ShglServiceStationEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name");// 名称
		String commId = paramMap.get("commId");// 所在社区
		String manager = paramMap.get("manager");//负责人
		String phone = paramMap.get("phone");//联系电话
		String dqCommId = paramMap.get("dqCommId");//当前用户负责的小区Id
		String dqSsId = paramMap.get("dqSsId");//当前用户负责的服务站Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm")/*.createAlias("smanageList", "sm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm")/*.createAlias("smanageList", "sm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("comm.id", commId));
			criteria.add(Restrictions.eq("comm.id", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("comm.id", dqCommId));
			criteria.add(Restrictions.eq("comm.id", dqCommId));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {//服务站管理员只能查看自己负责 的服务站
			criteriaCnt.add(Restrictions.eq("id", dqSsId));
			criteria.add(Restrictions.eq("id", dqSsId));
		}
		/*if(null != manager && !"".equals(manager)){
			criteriaCnt.add(Restrictions.like("sm.manager", manager, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("sm.manager", manager, MatchMode.ANYWHERE));
		}
		if(null != phone && !"".equals(phone)){
			criteriaCnt.add(Restrictions.like("sm.phone", phone, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("sm.phone", phone, MatchMode.ANYWHERE));
		}*/
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglServiceStationEntity> findAllSs1(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String dqSsId = paramMap.get("dqSsId");//当前用户负责的服务站id
		String dqCommId = paramMap.get("dqCommId");//当前用户负责的社区id
		String name = paramMap.get("name");
		String commId = paramMap.get("commId");// 社区id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm");// 数据集
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {
			criteria.add(Restrictions.eq("id", dqSsId));
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("comm.id", commId));
		}else if (dqCommId != null && !"".equals(dqCommId)) {
			criteria.add(Restrictions.eq("comm.id", dqCommId));
		}
		
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public void del(String ids) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String[] id = ids.split(";");
		for(String i: id){
			/****居民 *****/
			String sql4="select s.id from shgl_inmate_table s where s.ss_id = '"+ i +"'";
			SQLQuery query4 = session.createSQLQuery(sql4);
			List<String> iList = query4.list();
			if(iList != null){
				for(String iid : iList){
					super.deleteById(ShglInmateEntity.class, iid);
				}
			}
			/****居民 *****/
			/****小区  *****/
			String sql2="select s.id from shgl_village_table s where s.ss_id = '"+ i +"'";
			SQLQuery query2 = session.createSQLQuery(sql2);
			List<String> vList = query2.list();
			if(vList != null){
				for(String vid : vList){
					/****小区负责人*****/
					String sql6="select m.id from shgl_vmanager_table m where m.vill_id = '"+ vid +"'";
					SQLQuery query6 = session.createSQLQuery(sql6);
					List<String> mList = query6.list();
					if(mList != null){
						for(String mid : mList){
							super.deleteById(ShglVmanagerEntity.class, mid);
						}
					}
					/****小区负责人*****/
					/****楼宇  *****/
					String sql3 = "select s.id from shgl_building_table s where s.village_id = '" + vid + "'";
					SQLQuery query3 = session.createSQLQuery(sql3);
					List<String> bList = query3.list();
					for(String bid : bList){
						super.deleteById(ShglBuildingEntity.class, bid);
					}
					/****楼宇  *****/
					super.deleteById(ShglVillageEntity.class, vid);
				}
			}
			/****小区  *****/
			/****网格 *****/
			String sql1="select s.id from shgl_grid_table s where s.ss_id = '"+ i +"'";
			SQLQuery query1 = session.createSQLQuery(sql1);
			List<String> gList = query1.list();
			if(gList != null){
				for(String gid : gList){
					/****网格负责人*****/
					String sql7="select m.id from shgl_grid_manager_table m where m.grid_id = '"+ gid +"'";
					SQLQuery query7 = session.createSQLQuery(sql7);
					List<String> mList = query7.list();
					if(mList != null){
						for(String mid : mList){
							super.deleteById(ShglGridManagerEntity.class, mid);
						}
					}
					/****网格负责人*****/
					
					super.deleteById(ShglGridEntity.class, gid);
				}
			}
			/****网格  *****/
			
			/****服务站负责人*****/
			String sql5="select m.id from shgl_smanager_table m where m.ss_id = '"+ i +"'";
			SQLQuery query5 = session.createSQLQuery(sql5);
			List<String> mList = query5.list();
			if(mList != null){
				for(String mid : mList){
					super.deleteById(ShglSmanagerEntity.class, mid);
				}
			}
			/****服务站负责人*****/
			
			/*****服务站*****/
			super.deleteById(ShglServiceStationEntity.class, i);
			/*****服务站****/
		}
	}
	
	@Override
	public List<ShglServiceStationEntity> findAllSs2(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String commId = paramMap.get("commId");// 社区id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.eq("id", id));
		}
		if (commId != null && !commId.equals("")) {
			criteria.add(Restrictions.eq("comm.id", commId));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public ShglServiceStationEntity findByName(Map<String, String> addMapSs) {
		
		String ssName = addMapSs.get("ssName");// 服务站名称
		String commId = addMapSs.get("commId");// 所属社区Id
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm");// 数据集
		// 组织查询条件
		if (null != ssName && !"".equals(ssName)) {
			criteria.add(Restrictions.eq("name", ssName));
		}
		if (null != commId && !"".equals(commId)) {
			criteria.add(Restrictions.eq("comm.id", commId));
		}
		ShglServiceStationEntity serviceStationEntity = null;
		if(criteria.list().size()>0){
			serviceStationEntity = (ShglServiceStationEntity) criteria.list().get(0);
		}
		return serviceStationEntity;
	}

	@Override
	public List<ShglServiceStationEntity> findAllCom(Map<String, String> map) {
		
		String id = map.get("id");// id
		String name = map.get("name");
		String commId = map.get("commId");// 社区id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm");// 数据集
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("comm.id", commId));
		}
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public List<ShglSmanagerEntity> findFwzManager(String ssId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSmanagerEntity.class);// 数据集
		if(null != ssId && !"".equals(ssId)){
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		return criteria.list();
	}
}
