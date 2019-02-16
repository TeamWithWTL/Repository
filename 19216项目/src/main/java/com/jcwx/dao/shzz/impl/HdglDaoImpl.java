package com.jcwx.dao.shzz.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.jcwx.dao.shzz.HdglDao;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkAttrEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 */
@SuppressWarnings("unchecked")
@Repository
@Transactional
public class HdglDaoImpl  extends BaseDaoImpl implements HdglDao{

	@Override
	public Pagenate<HdglEntity> getHdglContent(Integer pageNumber, int pageSize, Map<String, String> map) {
		String title = map.get("title");	//标题/发布人
		String applyTime = map.get("applyTime");	// 发布时间
		String app = map.get("app");	//app查询标识
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(HdglEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(HdglEntity.class);// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("userName", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("userName", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("createTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("createTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (app!=null&&!"".equals(app)) {
			criteriaCnt.add(Restrictions.eq("shStatus", "1"));//只查询审核通过的，1
			criteria.add(Restrictions.eq("shStatus", "1"));
		}
		// 排序
		/*criteria.addOrder(Order.asc("status.status"));*/
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

	@Override
	public List<HdglEntity> findByHot(String hot) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglEntity.class);
		criteria.add(Restrictions.eq("is_hot", hot));
		return criteria.list();
	}
	
	@Override
	public Pagenate<HdglYjEntity> findHdyjFistPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		//String status = map.get("status");
		String id = map.get("hdglId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglYjEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(HdglYjEntity.class);
		
		if (id!=null&&!"".equals(id)) {
			criteriaCnt.add(Restrictions.eq("hdglId", id));
			criteria.add(Restrictions.eq("hdglId", id));
		}
		
		criteriaCnt.add(Restrictions.eq("status", "1"));	//查询已审核留言
		criteria.add(Restrictions.eq("status", "1"));
		
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	@Override
	public Pagenate<HdglYjEntity> findLyDshByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String id = map.get("hdglId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglYjEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(HdglYjEntity.class);
		
		if (id!=null&&!"".equals(id)) {
			criteriaCnt.add(Restrictions.eq("hdglId", id));
			criteria.add(Restrictions.eq("hdglId", id));
		}
		
		criteriaCnt.add(Restrictions.eq("status", "0"));	//查询待审核留言
		criteria.add(Restrictions.eq("status", "0"));
		
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<HdglYjEntity> findLyBtgByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String id = map.get("hdglId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglYjEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(HdglYjEntity.class);
		
		if (id!=null&&!"".equals(id)) {
			criteriaCnt.add(Restrictions.eq("hdglId", id));
			criteria.add(Restrictions.eq("hdglId", id));
		}
		
		criteriaCnt.add(Restrictions.eq("status", "2"));	//查询审核不通过留言
		criteria.add(Restrictions.eq("status", "2"));
		
		criteria.addOrder(Order.desc("createTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<HdglYjEntity> findbyHdId(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglYjEntity.class);
		criteria.add(Restrictions.eq("hdglId", id));
		criteria.add(Restrictions.eq("status", "1"));	//审核通过
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
	
	@Override
	public List<ZzxxEntity> findZzxmByAccCode(String accCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("fr_id", accCode));
		}
		criteria.add(Restrictions.eq("sc_status", "1"));	//只查询审查通过的组织
		return criteria.list();
	}
	
	@Override
	public void seveOrUpdateHdglArrt(HdglFkAttrEntity hdglFkAttrEntity) {
		try {
			super.saveOrUpdate(hdglFkAttrEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<HdglFkEntity> findFkByHdId(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		criteria.add(Restrictions.eq("hdglId", id));
		criteria.addOrder(Order.desc("backTime"));
		return criteria.list();
	}
	
	@Override
	public HdglFkEntity getFkByHdIdZzId(String hdId, String zzId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		criteria.add(Restrictions.eq("hdglId", hdId));
		criteria.add(Restrictions.eq("zzId", zzId));
		criteria.add(Restrictions.eq("isBack", "2"));
		return (HdglFkEntity) criteria.uniqueResult();
	}
	
	@Override
	public Pagenate<HdglYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String hdglId = map.get("hdglId");	//活动id
		String role_code = map.get("role_code");	//当前角色
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglYjEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());//查询数据总条数
		Criteria criteria = session.createCriteria(HdglYjEntity.class);//查询数据
		
		if (hdglId!=null &&!"".equals(hdglId)) {
			criteriaCnt.add(Restrictions.eq("hdglId", hdglId));
			criteria.add(Restrictions.eq("hdglId", hdglId));
		}
		if (role_code!=null && !"".equals(role_code)) {
			ArrayList<String> arrayList=new ArrayList<String>();
			if (role_code.indexOf(",")!=-1) {
				String[] roleCodes = role_code.split(",");//当前登录人员角色
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(role_code);
			}
			if (arrayList.contains("09")||arrayList.contains("99")) {
				criteriaCnt.add(Restrictions.eq("status", "1"));	//只查询审核通过的  //1审核通过，0未审核
				criteria.add(Restrictions.eq("status", "1"));
			}else{
				criteriaCnt.add(Restrictions.eq("status", "1"));	//只查询审核通过的  //1审核通过，0未审核
				criteria.add(Restrictions.eq("status", "1"));
				
			}
			criteria.addOrder(Order.desc("createTime"));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	@Override
	public Pagenate<HdglFkEntity> findFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		String hdglId = fkMap.get("hdglId");	//活动ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglFkEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());	//查询记录数
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		if (hdglId!=null&&!"".equals(hdglId)) {	//查询此活动下的所有反馈信息
			criteriaCnt.add(Restrictions.eq("hdglId", hdglId));
			criteria.add(Restrictions.eq("hdglId", hdglId));
		}
		
		//查询审核通过反馈信息
		criteriaCnt.add(Restrictions.eq("shStatus", "1"));
		criteria.add(Restrictions.eq("shStatus", "1"));
		
		//criteriaCnt.add(Restrictions.eq("isBack", "1"));
		//criteria.add(Restrictions.eq("isBack", "1"));
		
		criteria.addOrder(Order.desc("backTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	@Override
	public Pagenate<HdglFkEntity> findDshFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		String hdglId = fkMap.get("hdglId");	//活动ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglFkEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());	//查询记录数
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		if (hdglId!=null&&!"".equals(hdglId)) {	//查询此活动下的所有反馈信息
			criteriaCnt.add(Restrictions.eq("hdglId", hdglId));
			criteria.add(Restrictions.eq("hdglId", hdglId));
		}
		
		criteriaCnt.add(Restrictions.eq("shStatus", "0"));
		criteria.add(Restrictions.eq("shStatus", "0"));
		
		//criteriaCnt.add(Restrictions.eq("isBack", "1"));
		//criteria.add(Restrictions.eq("isBack", "1"));
		
		criteria.addOrder(Order.desc("backTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public Pagenate<HdglFkEntity> findBtgFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		String hdglId = fkMap.get("hdglId");	//活动ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(HdglFkEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());//查询记录数
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		if (hdglId!=null&&!"".equals(hdglId)) {//查询此活动下的所有反馈信息
			criteriaCnt.add(Restrictions.eq("hdglId", hdglId));
			criteria.add(Restrictions.eq("hdglId", hdglId));
		}
		
		//查询审核通过反馈信息
		criteriaCnt.add(Restrictions.eq("shStatus", "2"));
		criteria.add(Restrictions.eq("shStatus", "2"));
		
		criteria.addOrder(Order.desc("backTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public HdglYjEntity getHdYjByhdId(String hdglId, String userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglYjEntity.class);
		criteria.add(Restrictions.eq("hdglId", hdglId));
		criteria.add(Restrictions.eq("userId", userId));
		return (HdglYjEntity) criteria.uniqueResult();
	}

	@Override
	public List<HdglEntity> getZzhdList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglEntity.class);
		return criteria.list();
	}

	@Override
	public List<HdglFkEntity> findYfkByHdId(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(HdglFkEntity.class);
		criteria.add(Restrictions.eq("hdglId", id));
		criteria.add(Restrictions.eq("shStatus", "1"));
		criteria.addOrder(Order.desc("backTime"));
		return criteria.list();
	}

}
