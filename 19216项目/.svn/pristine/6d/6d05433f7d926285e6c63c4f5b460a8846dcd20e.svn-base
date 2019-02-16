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
import com.jcwx.dao.shgl.XqxxDao;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglVillageEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class XqxxDaoImpl extends BaseDaoImpl implements XqxxDao {

	@Override
	public Pagenate<ShglVillageEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		String name = map.get("name");// 名称
		String commId = map.get("commId");// 社区编码
		String ssId = map.get("ssId");// 服务站编码
		String manager = map.get("manager");//负责人
		String phone = map.get("phone");//联系电话
		String dqCommId = map.get("dqCommId");//当前用户所负责的社区
		String dqSsId = map.get("dqSsId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss")/*.createAlias("vmanList", "vm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		Criteria criteria = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss")/*.createAlias("vmanList", "vm", JoinType.LEFT_OUTER_JOIN)*/;// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("ss.community.id", commId));
			criteria.add(Restrictions.eq("ss.community.id", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("ss.community.id", dqCommId));
			criteria.add(Restrictions.eq("ss.community.id", dqCommId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("ss.id", ssId));
			criteria.add(Restrictions.eq("ss.id", ssId));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {
			criteriaCnt.add(Restrictions.eq("ss.id", dqSsId));
			criteria.add(Restrictions.eq("ss.id", dqSsId));
		}
		/*if(null != manager && !"".equals(manager)){
			criteriaCnt.add(Restrictions.like("vm.manager", manager, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("vm.manager", manager, MatchMode.ANYWHERE));
		}
		if(null != phone && !"".equals(phone)){
			criteriaCnt.add(Restrictions.like("vm.phone", phone, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("vm.phone", phone, MatchMode.ANYWHERE));
		}*/
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglVillageEntity> findAllVillages1(Map<String, String> map) {
		String name = map.get("name");// 名称
		String commId = map.get("commId");//社区编码
		String ssId = map.get("ssId");// 服务站编码
		String id = map.get("id");// 编码
		String dqSsId = map.get("dqSsId");//当前登录用户所负责的服务站Id
		String dqCommId = map.get("dqCommId");//当前登录用户所负责的社区Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteria.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}else if (dqSsId!=null&&!"".equals(dqSsId)) {
			criteria.add(Restrictions.eq("ss.id", dqSsId));
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("ss.community.id", commId));
		}else if (dqCommId!=null&&!"".equals(dqCommId)) {
			criteria.add(Restrictions.eq("ss.community.id", dqCommId));
		}
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public void del(String ids) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String[] id = ids.split(";");
		for(String i : id){
			/****楼宇  *****/
			String sql1 = "select s.id from shgl_building_table s where s.village_id = '" + i + "'";
			SQLQuery query1 = session.createSQLQuery(sql1);
			List<String> bList = query1.list();
			for(String bid : bList){
				/****居民 *****/
				String sql4="select s.id from shgl_inmate_table s where s.building = '"+ bid +"'";
				SQLQuery query4 = session.createSQLQuery(sql4);
				List<String> iList = query4.list();
				if(iList != null){
					for(String iid : iList){
						super.deleteById(ShglInmateEntity.class, iid);
					}
				}
				/****居民 *****/
				super.deleteById(ShglBuildingEntity.class, bid);
			}
			/****楼宇  *****/
			
			/****小区负责人*****/
			String sql5="select m.id from shgl_vmanager_table m where m.vill_id = '"+ i +"'";
			SQLQuery query5 = session.createSQLQuery(sql5);
			List<String> mList = query5.list();
			if(mList != null){
				for(String mid : mList){
					super.deleteById(ShglVmanagerEntity.class, mid);
				}
			}
			/****小区负责人*****/
			
			super.deleteById(ShglVillageEntity.class, i);
		}
	}

	@Override
	public ShglVillageEntity findByName(Map<String, String> addMapV) {
		
		String villName = addMapV.get("villName");//小区名称
		String ssId = addMapV.get("ssId");//服务站Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss");// 数据集
		
		if (null != villName && !"".equals(villName)) {
			criteria.add(Restrictions.eq("name", villName));
		}
		if (null != ssId && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}
		ShglVillageEntity shglVillageEntity = null;
		if(criteria.list().size()>0){
			shglVillageEntity = (ShglVillageEntity) criteria.list().get(0);
		}
		
		return shglVillageEntity;
	}

	@Override
	public List<ShglVillageEntity> findAllVillages(Map<String, String> map) {
		
		String name = map.get("name");// 名称
		String ssId = map.get("ssId");// 服务站编码
		String id = map.get("id");// 编码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteria.add(Restrictions.eq("name", name));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public List<ShglVillageEntity> findAllVillages() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglVillageEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

}
