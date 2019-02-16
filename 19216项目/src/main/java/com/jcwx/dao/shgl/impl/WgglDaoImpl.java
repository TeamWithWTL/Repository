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
import com.jcwx.dao.shgl.WgglDao;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglGridManagerEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class WgglDaoImpl extends BaseDaoImpl implements WgglDao {

	@Override
	public Pagenate<ShglGridEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name");// 名称
		String commId = paramMap.get("commId");// 所在社区
		String serId = paramMap.get("serId");// 所在服务站
		String manager = paramMap.get("manager");//负责人
		String phone = paramMap.get("phone");//联系电话
		String dqCommId = paramMap.get("dqCommId");//当前用户所负责的社区
		String dqSsId = paramMap.get("dqSsId");//当前用户所负责的服务站
		String dpgridId = paramMap.get("dqgridId");//当前用户所负责的网格
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss")/*.createAlias("gridList", "gr", JoinType.LEFT_OUTER_JOIN)*/;// 数据集条数
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss")/*.createAlias("gridList", "gr", JoinType.LEFT_OUTER_JOIN)*/;// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (dpgridId != null && !"".equals(dpgridId)) {
			criteriaCnt.add(Restrictions.eq("id", dpgridId));
			criteria.add(Restrictions.eq("id", dpgridId));
		}
		if (serId != null && !"".equals(serId)) {
			criteriaCnt.add(Restrictions.eq("ss.id", serId));
			criteria.add(Restrictions.eq("ss.id", serId));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {
			criteriaCnt.add(Restrictions.eq("ss.id", dqSsId));
			criteria.add(Restrictions.eq("ss.id", dqSsId));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("ss.community.id", commId));
			criteria.add(Restrictions.eq("ss.community.id", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("ss.community.id", dqCommId));
			criteria.add(Restrictions.eq("ss.community.id", dqCommId));
		}
		/*if(null != manager && !"".equals(manager)){
			criteriaCnt.add(Restrictions.like("gr.manager", manager, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("gr.manager", manager, MatchMode.ANYWHERE));
		}
		if(null != phone && !"".equals(phone)){
			criteriaCnt.add(Restrictions.like("gr.phone", phone, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("gr.phone", phone, MatchMode.ANYWHERE));
		}*/
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglGridEntity> findAllCom1(Map<String, String> paramMap) {
		String id = paramMap.get("id");// id
		String name = paramMap.get("name");
		String commId = paramMap.get("commId");
		String ssId = paramMap.get("ssId");// 服务站id
		String dqSsId = paramMap.get("dqSsId");//当前登录用户所负责的服务站Id
		String dqCommId = paramMap.get("dqCommId");//当前登录用户所负责的社区Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}else if (dqSsId!=null&&!"".equals(dqSsId)) {
			criteria.add(Restrictions.eq("ss.id", dqSsId));
		}else {
			if (commId != null && !"".equals(commId)) {
				criteria.add(Restrictions.eq("ss.community.id", commId));
			}else if (dqCommId!=null&&!"".equals(dqCommId)) {
				criteria.add(Restrictions.eq("ss.community.id", dqCommId));
			}
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
		for(String i : id){
			String sql3 = "select s.id from shgl_building_table s where s.grid_id = '" + i + "'";
			SQLQuery query3 = session.createSQLQuery(sql3);
			List<String> bList = query3.list();
			for(String bid : bList){
				String sql4 = "UPDATE shgl_building_table SET grid_id = null WHERE id = '" + bid + "'";
				SQLQuery query4 = session.createSQLQuery(sql4);
				query4.executeUpdate();
			}
			
			/****网格负责人*****/
			String sql5="select m.id from shgl_grid_manager_table m where m.grid_id = '"+ i +"'";
			SQLQuery query5 = session.createSQLQuery(sql5);
			List<String> mList = query5.list();
			if(mList != null){
				for(String mid : mList){
					super.deleteById(ShglGridManagerEntity.class, mid);
				}
			}
			/****网格负责人*****/
			
			super.deleteById(ShglGridEntity.class, i);
		}
	}

	@Override
	public List<ShglGridManagerEntity> findGridManager(String gridId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridManagerEntity.class);// 数据集
		
		if(null != gridId && !"".equals(gridId)){
			criteria.add(Restrictions.eq("gridId", gridId));
		}
		
		return criteria.list();
	}

	@Override
	public ShglGridEntity findByName(Map<String, String> addMapG) {
		
		String grName = addMapG.get("grName");//网格名称
		String ssId = addMapG.get("ssId");//所属服务站ID
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 组织查询条件
		if (null != grName && !"".equals(grName)) {
			criteria.add(Restrictions.eq("name", grName));
		}
		if (null != ssId && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}
		ShglGridEntity gridEntity = null;
		if(criteria.list().size()>0){
			gridEntity = (ShglGridEntity) criteria.list().get(0);
		}
		return gridEntity;
	}

	@Override
	public List<ShglGridEntity> findAllSer(Map<String, String> map) {
		
		String id = map.get("id");// id
		String name = map.get("name");
		String ssId = map.get("ssId");// 服务站id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ss.id", ssId));
		}
		
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("add_time"));
		return criteria.list();
	}

	@Override
	public List<ShglGridEntity> getWgById(Map<String, String> addMap) {
		
		String sqId = addMap.get("sqId");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss");// 数据集
		
		if(null != sqId && !"".equals(sqId)){
			criteria.add(Restrictions.eq("ss.community.id", sqId));
		}
		return criteria.list();
	}

}
