package com.jcwx.dao.shgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.SqglDao;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
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
public class SqglDaoImpl extends BaseDaoImpl implements SqglDao {

//	@Override
//	public List<SysStrative> findAllStra() {
//		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//		Criteria criteria = session.createCriteria(SysStrative.class);// 数据集
//		return criteria.list();
//	}
//
//	@Override
//	public SysStrative findStrById(String id) {
//		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//		Criteria criteria = session.createCriteria(SysStrative.class);// 数据集
//		criteria.add(Restrictions.eq("id", id));
//		return (SysStrative) criteria.uniqueResult();
//	}

	@Override
	public Pagenate<ShglCommunityEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name");// 名称
		String manager = paramMap.get("manager");//负责人
		String phone = paramMap.get("phone");//联系电话
		String dqCommId = paramMap.get("dqCommId");//当前用户负责的小区Id
		String dqSsId = paramMap.get("dqSsId");//当前用户负责的服务站Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglCommunityEntity.class)/*.createAlias("cmanagerList", "cm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class)/*.createAlias("cmanagerList", "cm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (dqCommId!=null&&!"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("id", dqCommId));
			criteria.add(Restrictions.eq("id", dqCommId));
		}
		if (dqSsId!=null&&!"".equals(dqSsId)) {//服务站管理员无权查看社区管理
			criteriaCnt.add(Restrictions.eq("id", null));
			criteria.add(Restrictions.eq("id", null));
		}
		/*if(null != manager && !"".equals(manager)){
			criteriaCnt.add(Restrictions.like("cm.manager", manager, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("cm.manager", manager, MatchMode.ANYWHERE));
		}
		if(null != phone && !"".equals(phone)){
			criteriaCnt.add(Restrictions.like("cm.phone", phone, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("cm.phone", phone, MatchMode.ANYWHERE));
		}*/
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglCommunityEntity> findAllCom1(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String dqCommId = paramMap.get("dqCommId");//当前登录用户负责社区id
		String strative_id = paramMap.get("strative_id");// 街道Id
		String name = paramMap.get("name");// 名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class);// 数据集
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteria.add(Restrictions.eq("id", dqCommId));
		}
		if (strative_id != null && !"".equals(strative_id)) {
			criteria.add(Restrictions.eq("strative_id", strative_id));
		}
		if (name != null && !"".equals(name)) {
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
			/****社区负责人*****/
			String sql9="select m.id from shgl_cmanager_table m where m.comm_id = '"+ i +"'";
			SQLQuery query9 = session.createSQLQuery(sql9);
			List<String> mList = query9.list();
			if(mList != null){
				for(String mid : mList){
					super.deleteById(ShglCmanagerEntity.class, mid);
				}
			}
			/****社区负责人*****/
			/****居民 *****/
			String sql4="select s.id from shgl_inmate_table s where s.comm_id = '"+ i +"'";
			SQLQuery query4 = session.createSQLQuery(sql4);
			List<String> iList = query4.list();
			if(iList != null){
				for(String iid : iList){
					super.deleteById(ShglInmateEntity.class, iid);
				}
			}
			/****居民 *****/
			/****楼宇  *****/
			String hql3 = "DELETE FROM ShglBuildingEntity WHERE commId  = '" + i + "'";
			Query query3 = session.createQuery(hql3);
			query3.executeUpdate();
			/****楼宇  *****/
			/****服务站  *****/
			String sql="select s.id from shgl_service_station_table s where s.comm_id = '"+ i +"'";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> slist = query.list();
			if(slist != null){
				for(String sid : slist){
					/****服务站负责人*****/
					String sql10="select m.id from shgl_smanager_table m where m.ss_id = '"+ sid +"'";
					SQLQuery query10 = session.createSQLQuery(sql10);
					List<String> smList = query10.list();
					if(smList != null){
						for(String mid : smList){
							super.deleteById(ShglSmanagerEntity.class, mid);
						}
					}
					/****服务站负责人*****/
					/****网格 *****/
					String sql1="select s.id from shgl_grid_table s where s.ss_id = '"+ sid +"'";
					SQLQuery query1 = session.createSQLQuery(sql1);
					List<String> gList = query1.list();
					if(gList != null){
						for(String gid : gList){
							/****网格负责人*****/
							String sql11="select m.id from shgl_grid_manager_table m where m.grid_id = '"+ gid +"'";
							SQLQuery query11 = session.createSQLQuery(sql11);
							List<String> gmList = query11.list();
							if(gmList != null){
								for(String mid : gmList){
									super.deleteById(ShglGridManagerEntity.class, mid);
								}
							}
							/****网格负责人*****/
							
							super.deleteById(ShglGridEntity.class, gid);
						}
					}
					/****网格  *****/
					/****小区  *****/
					String sql2="select s.id from shgl_village_table s where s.ss_id = '"+ sid +"'";
					SQLQuery query2 = session.createSQLQuery(sql2);
					List<String> vList = query2.list();
					if(vList != null){
						for(String vid : vList){
							/****小区负责人*****/
							String sql12="select m.id from shgl_vmanager_table m where m.vill_id = '"+ vid +"'";
							SQLQuery query12 = session.createSQLQuery(sql12);
							List<String> vmList = query12.list();
							if(vmList != null){
								for(String mid : vmList){
									super.deleteById(ShglVmanagerEntity.class, mid);
								}
							}
							/****小区负责人*****/
							super.deleteById(ShglVillageEntity.class, vid);
						}
					}
					/****小区  *****/
					super.deleteById(ShglServiceStationEntity.class, sid);
					/****服务站  *****/
				}
			}
			
			/*******社区活动******//*
			String sql5 = "select h.id from shfw_sqhd_table h where h.comm_id = '"+ i +"'";
			SQLQuery query5 = session.createSQLQuery(sql5);
			List<String> hdList = query5.list();
			if(hdList != null){
				for(String hdid : hdList){
					*//*******社区活动附件******//*
					String sql6 = "select a.id from shfw_sqhd_attrs_table a where a.sqhd_id = '"+ hdid +"'";
					SQLQuery query6 = session.createSQLQuery(sql6);
					List<String> aList = query6.list();
					if(aList != null){
						for(String aid : aList){
							super.deleteById(ShfwSqhdAttrsEntity.class, aid);
						}
					}
					*//*******社区活动附件******//*
					*//*******社区活动意见留言******//*
					String sql7 = "select y.id from shfw_sqhd_yj_table y where y.sqhd_id = '"+ hdid +"'";
					SQLQuery query7 = session.createSQLQuery(sql7);
					List<String> yList = query7.list();
					if(yList != null){
						for(String yid : yList){
							*//*******社区活动意见留言附件******//*
							String sql8 = "select ya.id from shfw_sqhd_yj_attrs_table ya where ya.yj_id = '"+ yid +"'";
							SQLQuery query8 = session.createSQLQuery(sql8);
							List<String> yaList = query8.list();
							if(yaList != null){
								for(String yaid : yaList){
									super.deleteById(ShfwSqhdYjAttrsEntity.class, yaid);
								}
							}
							*//*******社区活动意见留言附件******//*
							
							super.deleteById(ShfwSqhdYjEntity.class, yid);
						}
					}
					*//*******社区活动意见留言******//*
					
					super.deleteById(ShfwSqhdEntity.class, hdid);
				}
			}
			*//*******社区活动******/
			
			/****社区  *****/
			super.deleteById(ShglCommunityEntity.class, i);
			/****社区  *****/
		}
	}
	
	@Override
	public List<ShglCommunityEntity> findAllCom2(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String strative_id = paramMap.get("strative_id");// 街道Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class);// 数据集
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		if (strative_id != null && !"".equals(strative_id)) {
			criteria.add(Restrictions.eq("strative_id", strative_id));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public ShglCommunityEntity findByName(Map<String, String> addMap) {
		
		String sqName = addMap.get("sqName");//社区名称
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class);// 数据集
		
		// 组织查询条件
		if (sqName != null && !"".equals(sqName)) {
			criteria.add(Restrictions.eq("name", sqName));
		}
		ShglCommunityEntity communityEntity = null;
		if(criteria.list().size()>0){
			communityEntity = (ShglCommunityEntity) criteria.list().get(0);
		}
		return communityEntity;
	}

	@Override
	public List<ShglCommunityEntity> findAllCom(Map<String, String> map) {
		String id = map.get("id");// id
		String name = map.get("name");// 名称
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCommunityEntity.class);// 数据集
		// 组织查询条件
		if (id != null && !"".equals(id)) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (name != null && !"".equals(name)) {
			criteria.add(Restrictions.eq("name", name));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}
}
