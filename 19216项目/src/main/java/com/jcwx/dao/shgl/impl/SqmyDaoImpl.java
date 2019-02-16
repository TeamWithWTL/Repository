package com.jcwx.dao.shgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.SqmyDao;
import com.jcwx.entity.Consts;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.utils.Pagenate;
@SuppressWarnings("unchecked")
@Repository
@Transactional
public class SqmyDaoImpl extends BaseDaoImpl implements SqmyDao{

	@Override
	public Pagenate<ShglSqmyEntity> findSqmyByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		 String title = map.get("title");
		 String sq_id = map.get("sq_id");
		 String is_over = map.get("is_over");
         Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		 Criteria criteriaCnt = session.createCriteria(ShglSqmyEntity.class);// 数据集条数
		 criteriaCnt.setProjection(Projections.rowCount());
		 Criteria criteria = session.createCriteria(ShglSqmyEntity.class);// 数据集
		 
		 if(!"".equals(title) && title != null){
			 criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			 criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		 }
		 
		 if (sq_id != null && !"".equals(sq_id)) {
				criteriaCnt.add(Restrictions.eq("sq_id", sq_id));
				criteria.add(Restrictions.eq("sq_id", sq_id));
		 }
		 if (is_over != null && !"".equals(is_over)) {
				criteriaCnt.add(Restrictions.eq("is_over", is_over));
				criteria.add(Restrictions.eq("is_over", is_over));
		 }
		// 按照发布时间倒序排序
		criteria.addOrder(Order.desc("sq_id")).addOrder(Order.desc("create_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	

	@Override
	public Pagenate<ShglSqmyWgy> findSqmyWgyByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		 String title = map.get("title");
		 String wgyId = map.get("wgyId");
		 String status = map.get("status");//1 保存,2下发
		 String is_over = map.get("is_over");// 0 进行中,1 结束
		 String isSee = map.get("isSee");
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
       
		 Criteria criteriaCnt = session.createCriteria(ShglSqmyWgy.class).createAlias("sqmyInfo", "sqmyInfo");// 数据集条数
		 criteriaCnt.setProjection(Projections.rowCount());
		 Criteria criteria = session.createCriteria(ShglSqmyWgy.class).createAlias("sqmyInfo", "sqmyInfo");// 数据集
		 
		 if(!"".equals(title) && title != null){
			 criteria.add(Restrictions.like("sqmyInfo.title", title, MatchMode.ANYWHERE));
			 criteriaCnt.add(Restrictions.like("sqmyInfo.title", title, MatchMode.ANYWHERE));
		 }
		 
		 if (status != null && !"".equals(status)) {
				criteriaCnt.add(Restrictions.eq("sqmyInfo.status", status));
				criteria.add(Restrictions.eq("sqmyInfo.status", status));
		 }
		 if (is_over != null && !"".equals(is_over)) {
				criteriaCnt.add(Restrictions.eq("sqmyInfo.is_over", is_over));
				criteria.add(Restrictions.eq("sqmyInfo.is_over", is_over));
		 }
		 if (wgyId != null && !"".equals(wgyId)) {
				criteriaCnt.add(Restrictions.eq("wgyId", wgyId));
				criteria.add(Restrictions.eq("wgyId", wgyId));
		 }
		 if (isSee != null && !"".equals(isSee)) {
				criteriaCnt.add(Restrictions.eq("isSee", isSee));
				criteria.add(Restrictions.eq("isSee", isSee));
		 }
		// 按照发布时间倒序排序
		criteria.addOrder(Order.desc("sqmyInfo.create_date"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	@Override
	public Pagenate<SysAccCountLazy> finbysqid(Integer pageNumber,int pagesize, Map<String, String> map) {
		
		String sqid = map.get("sqid");       //社区id
		String name = map.get("name");      //网格员name
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		Criteria criteriaCnt = session.createCriteria(SysAccCountLazy.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);// 数据集
        
		if(sqid != null && !sqid.equals("")){
			criteria.add(Restrictions.eq("commId", sqid));
			criteriaCnt.add(Restrictions.eq("commId", sqid));
		}
		if(name != null && !name.equals("")){
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	@Override
	public List<ShglSqmyZhEntity> getwgylist(String id, String wgyid) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyZhEntity.class);
		if(id != null && !id.equals("")){
			criteria.add(Restrictions.eq("sqmy_id", id));
		}
		if(wgyid != null && !wgyid.equals("")){
			criteria.add(Restrictions.eq("wgy_id", wgyid));
		}
		return criteria.list();
	}
	@Override
	public Pagenate<ShglSqmyZhEntity> findBywgyzh(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		String rqmy_id = map.get("rqmy_id");       //社情民意id
		String wgyID = map.get("wgyID");           //网格员id
		String xq_id = map.get("xq_id");           //小区号
		String ly_id = map.get("ly_id");           //楼宇号
		String unit_num = map.get("unit_num");     //单元号
		String sqmy_id = map.get("sqmy_id");	//社情民意ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		Criteria criteriaCnt = session.createCriteria(ShglSqmyZhEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ShglSqmyZhEntity.class);// 数据集
        
		if(rqmy_id != null && !rqmy_id.equals("")){
			criteria.add(Restrictions.eq("sqmy_id", rqmy_id));
			criteriaCnt.add(Restrictions.eq("sqmy_id", rqmy_id));
		}
		if(wgyID != null && !wgyID.equals("")){
			criteria.add(Restrictions.eq("wgy_id", wgyID));
			criteriaCnt.add(Restrictions.eq("wgy_id", wgyID));
		}
		if(xq_id != null && !xq_id.equals("")){
			criteria.add(Restrictions.eq("xq_id", xq_id));
			criteriaCnt.add(Restrictions.eq("xq_id", xq_id));
		}
		if(ly_id != null && !ly_id.equals("")){
			criteria.add(Restrictions.eq("ly_id", ly_id));
			criteriaCnt.add(Restrictions.eq("ly_id", ly_id));
		}
		if(unit_num != null && !unit_num.equals("")){
			criteria.add(Restrictions.eq("unit_num", unit_num));
			criteriaCnt.add(Restrictions.eq("unit_num", unit_num));
		}
		
		if(sqmy_id != null && !sqmy_id.equals("")){
			criteria.add(Restrictions.eq("sqmy_id", sqmy_id));
			criteriaCnt.add(Restrictions.eq("sqmy_id", sqmy_id));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	@Override
	public List<ShglSqmyEntity> getSqmyContent() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyEntity.class);// 数据集
		criteria.addOrder(Order.desc("create_date"));
		return criteria.list();
	}
	@Override
	public List<ShglSqmyZhEntity> getwgylist(String sqId, String oneId, String sqmyId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyZhEntity.class);
		if(null != sqmyId && !"".equals(sqmyId)){
			criteria.add(Restrictions.eq("sqmy_id", sqmyId));
		}
		if(null != oneId && !"".equals(oneId)){
			criteria.add(Restrictions.eq("wgy_id", oneId));
		}
		if(null != sqId && !"".equals(sqId)){
			criteria.add(Restrictions.eq("sq_id", sqId));
		}
		return criteria.list();
	}
	@Override
	public List<ShglInmateEntity> findAllinmate(Map<String, String> paramMap) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");
		String ly = paramMap.get("ly");      //楼宇id
		if(ly != null && !ly.equals("")){
			criteria.add(Restrictions.eq("build.id", ly));
		}
		return criteria.list();
	}
	
	
	
	
	@Override
	public Pagenate<SysAccCountLazy> findWgyBysqid(Integer pageNumber,int pagesize, Map<String, String> map) {
		
		String sqid = map.get("sqid");       //社区id
		String name = map.get("name");      //网格员name
		String ssId = map.get("ssId");      //服务站id
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        
		Criteria criteriaCnt = session.createCriteria(SysAccCountLazy.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);// 数据集
        
		if(sqid != null && !sqid.equals("")){
			criteria.add(Restrictions.eq("commId", sqid));
			criteriaCnt.add(Restrictions.eq("commId", sqid));
		}
		if(ssId != null && !ssId.equals("")){
			criteria.add(Restrictions.eq("ssId", ssId));
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
		}
		if(name != null && !name.equals("")){
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		// 角色为：网格员
		criteria.add(Restrictions.like("role_code", Consts.ROLE_WGY, MatchMode.ANYWHERE));
		criteriaCnt.add(Restrictions.like("role_code", Consts.ROLE_WGY, MatchMode.ANYWHERE));
		
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<SysAccCountLazy> findWgyList(Map<String, String> map) {
		String commId = map.get("commId");
		String ssId =  map.get("ssId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);
		if(null != commId && !"".equals(commId)){
			criteria.add(Restrictions.eq("commId", commId));
		}
		if(null != ssId && !"".equals(ssId)){
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		criteria.add(Restrictions.like("role_code", Consts.ROLE_WGY, MatchMode.ANYWHERE));
		return criteria.list();
	}

	@Override
	public Pagenate<ShglSqmyWgy> findSqmyWgyListByParam(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		String sqmyId = map.get("sqmyId");
		String commId = map.get("commId");
		String ssId = map.get("ssId");
		String name = map.get("name");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ShglSqmyWgy.class).createAlias("sqmyInfo", "sqmyInfo");
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ShglSqmyWgy.class).createAlias("sqmyInfo", "sqmyInfo");
		// 组织查询条件
		if (sqmyId != null && !"".equals(sqmyId)) {
			criteriaCnt.add(Restrictions.eq("sqmyInfo.id", sqmyId));
			criteria.add(Restrictions.eq("sqmyInfo.id", sqmyId));

		}
		if (name != null && !"".equals(name)) {
			criteria.add(Restrictions.like("wgyName", name, MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("wgyName", name, MatchMode.ANYWHERE));
		}
	
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("sqmyInfo.sq_id", commId));
			criteria.add(Restrictions.eq("sqmyInfo.sq_id", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("fwzId", ssId));
			criteria.add(Restrictions.eq("fwzId", ssId));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}



	@Override
	public List<ShglSqmyDc> findAllDc(Map<String, String> map) {
		String sqmyWgyId = map.get("sqmyWgyId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyDc.class);
		if(null != sqmyWgyId && !"".equals(sqmyWgyId)){
			criteria.add(Restrictions.eq("sqmyWgyId", sqmyWgyId));
		}
		return criteria.list();
	}


	@Override
	public List<ShglSqmyWgy> findSqmyWgyList(Map<String, String> map) {
		 String wgyId = map.get("wgyId");
		 String status = map.get("status");//1 保存,2下发
		 String is_over = map.get("is_over");// 0 进行中,1 结束
		 String isSee = map.get("isSee");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSqmyWgy.class).createAlias("sqmyInfo", "sqmyInfo");;
		if (status != null && !"".equals(status)) {
			criteria.add(Restrictions.eq("sqmyInfo.status", status));
		 }
		 if (is_over != null && !"".equals(is_over)) {
				criteria.add(Restrictions.eq("sqmyInfo.is_over", is_over));
		 }
		 if (wgyId != null && !"".equals(wgyId)) {
				criteria.add(Restrictions.eq("wgyId", wgyId));
		 }
		 if (isSee != null && !"".equals(isSee)) {
				criteria.add(Restrictions.eq("isSee", isSee));
		 }
		// 按照发布时间倒序排序
		criteria.addOrder(Order.desc("sqmyInfo.create_date"));
		return criteria.list();
	}


}
