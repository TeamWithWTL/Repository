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
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.LyglDao;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class LyglDaoImpl extends BaseDaoImpl implements LyglDao {

	@Override
	public Pagenate<ShglBuildingEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		String name = map.get("name");// 名称
		String commId = map.get("commId");// 社区编码
		String ssId = map.get("ssId");// 服务站编码
		String gridId = map.get("gridId");// 网格编码
		String dqCommId = map.get("dqCommId");//当前用户所负责的社区
		String dqSsId = map.get("dqSsId");//当前用户所负责的服务站
		String dqgridId = map.get("dqgridId");//当前用户所负责的网格
		String xqId = map.get("xqId");//小区编码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglBuildingEntity.class).createAlias("village", "village")
				.createAlias("grid", "grid", JoinType.LEFT_OUTER_JOIN);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("village", "village")
				.createAlias("grid", "grid", JoinType.LEFT_OUTER_JOIN);// 数据集
		// 组织查询条件
		if (name != null && !"".equals(name)) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("commId", dqCommId));
			criteria.add(Restrictions.eq("commId", dqCommId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("village.serviceStation.id", ssId));
			criteria.add(Restrictions.eq("village.serviceStation.id", ssId));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {
			criteriaCnt.add(Restrictions.eq("village.serviceStation.id", dqSsId));
			criteria.add(Restrictions.eq("village.serviceStation.id", dqSsId));
		}
		if (gridId != null && !"".equals(gridId)) {
			criteriaCnt.add(Restrictions.eq("grid.id", gridId));
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		if (dqgridId != null && !"".equals(dqgridId)) {
			criteriaCnt.add(Restrictions.eq("grid.id", dqgridId));
			criteria.add(Restrictions.eq("grid.id", dqgridId));
		}
		if (xqId != null && !"".equals(xqId)) {
			criteriaCnt.add(Restrictions.eq("village.id", xqId));
			criteria.add(Restrictions.eq("village.id", xqId));
		}
		// 排序
//		criteria.addOrder(Order.asc("grid.id"));// 网格
//		criteria.addOrder(Order.asc("name"));// 楼宇名称
		criteria.addOrder(Order.desc("add_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglBuildingEntity> findAllBuilds1(Map<String, String> map) {
		String id = map.get("id");// id
		String name = map.get("name");// 名称
		String commId = map.get("commId");// 社区编码
		String ssId = map.get("ssId");// 服务站编码
		String vId = map.get("vId");// 小区编码
		String xq_id = map.get("xq_id"); //小区ID
		String gridId = map.get("gridId");// 网格编码
		String dqSsId = map.get("dqSsId");//当前登录用户所负责的服务站Id
		String dqCommId = map.get("dqCommId");//当前登录用户所负责的社区Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("village", "village")
				.createAlias("grid", "grid");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		if (commId != null && !commId.equals("")) {
			criteria.add(Restrictions.eq("commId", commId));
		}else if (dqCommId!=null&&!"".equals(dqCommId)) {
			criteria.add(Restrictions.eq("commId", dqCommId));
		}
		if (ssId != null && !ssId.equals("")) {
			criteria.add(Restrictions.eq("village.serviceStation.id", ssId));
		}else if (dqSsId!=null&&!"".equals(dqSsId)) {
			criteria.add(Restrictions.eq("village.serviceStation.id", dqSsId));
		}
		if (vId != null && !vId.equals("")) {
			criteria.add(Restrictions.eq("village.id", vId));
		}
		if (gridId != null && !gridId.equals("")) {
			criteria.add(Restrictions.eq("grid.id", gridId));
		}
		if (xq_id != null && !xq_id.equals("")) {
			criteria.add(Restrictions.eq("village.id", xq_id));
		}
		// 排序
		criteria.addOrder(Order.asc("village.id"));// 小区
		criteria.addOrder(Order.asc("name"));// 楼宇名称
		return criteria.list();
	}

	@Override
	public void del(String ids) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String[] id = ids.split(";");
		for(String i : id){
			/****居民 *****/
			String sql4="select s.id from shgl_inmate_table s where s.building = '"+ i +"'";
			SQLQuery query4 = session.createSQLQuery(sql4);
			List<String> iList = query4.list();
			if(iList != null){
				for(String iid : iList){
					super.deleteById(ShglInmateEntity.class, iid);
				}
			}
			/****居民 *****/
			super.deleteById(ShglBuildingEntity.class, i);
		}
	}

	@Override
	public ShglBuildingEntity findByName(Map<String, String> addMapLy) {
		String lyName = addMapLy.get("lyName");// 楼宇名称
		String vId = addMapLy.get("vId");// 所属社区Id
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("village", "v");// 数据集
		// 组织查询条件
		if (null != lyName && !"".equals(lyName)) {
			criteria.add(Restrictions.eq("name", lyName));
		}
		if (null != vId && !"".equals(vId)) {
			criteria.add(Restrictions.eq("v.id", vId));
		}
		ShglBuildingEntity shglBuildingEntity = null;
		if(criteria.list().size()>0){
			shglBuildingEntity = (ShglBuildingEntity) criteria.list().get(0);
		}
		return shglBuildingEntity;
	}

	@Override
	public List<ShglBuildingEntity> findAllBuilds(Map<String, String> map) {
		
		String id = map.get("id");// id
		String name = map.get("name");// 名称
		String vId = map.get("vId");// 小区编码
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("village", "village")
				.createAlias("grid", "grid");// 数据集
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		if (vId != null && !vId.equals("")) {
			criteria.add(Restrictions.eq("village.id", vId));
		}
		// 排序
		criteria.addOrder(Order.asc("village.id"));// 小区
		criteria.addOrder(Order.asc("name"));// 楼宇名称
		return criteria.list();
	}

	@Override
	public List<ShglInmateEntity> getYzhRoom(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		/*String sql = "SELECT DISTINCT i.unit_no,i.room_no "
				+ "FROM shgl_building_table b LEFT JOIN shgl_inmate_table i "
				+ "ON b.id = i.building WHERE b.id = '"+ id +"'";*/
		String sql = "SELECT DISTINCT unit_no,room_no FROM shgl_inmate_table i WHERE i.building = '"+ id +"'";
		SQLQuery query = session.createSQLQuery(sql);
		List<ShglInmateEntity> roomList = query.list();
		return roomList;
	}

	@Override
	public List<ShglBuildingEntity> getBuildList(Map<String, String> addMap) {
		String sqId = addMap.get("sqId");
		String wgId = addMap.get("wgId");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("grid", "gd");
		
		if(null != sqId && !"".equals(sqId)){
			criteria.add(Restrictions.eq("commId", sqId));
		}
		if(null != wgId && !"".equals(wgId)){
			criteria.add(Restrictions.eq("gd.id", wgId));
		}
		return criteria.list();
	}

}
