/**
 * 
 */
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

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shzz.DtbbDao;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class DtbbDaoImpl extends BaseDaoImpl implements DtbbDao{

	@Override
	public Pagenate<DtbbEntity> getDtbbContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		String code = map.get("code");//区分手机端还是pc端 手机端只查看审核通过的数据
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(DtbbEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(DtbbEntity.class);// 数据集
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
		if(null != code && !"".equals(code) && "app".equals(code)){
			criteriaCnt.add(Restrictions.eq("shStatus", "1"));
			criteria.add(Restrictions.eq("shStatus", "1"));
		}
		// 排序
		/*criteria.addOrder(Order.asc("status.status"));
		criteria.addOrder(Order.desc("apply_time"));*/
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<DtbbEntity> getDtbbContentList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(DtbbEntity.class);// 数据集
		String iSstatus = "1";
		criteria.add(Restrictions.eq("shStatus", iSstatus));
		return criteria.list();
	}

	@Override
	public List<DtbbYjEntity> findByDtId(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(DtbbYjEntity.class);// 数据集
		criteria.add(Restrictions.eq("dtbb_id", id));
		criteria.add(Restrictions.eq("status", "1"));//审核通过的留言
		return criteria.list();
	}

	@Override
	public Pagenate<DtbbYjEntity> getByDtId(Integer pageNumber, int pageSize, Map<String, String> map) {
		String dtbbId = map.get("dtbbId");// 动态播报id
		String code = map.get("code");//区分手机端还是pc端 手机端只查看审核通过的数据
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(DtbbYjEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(DtbbYjEntity.class);// 数据集
		// 组织查询条件
		if(null != dtbbId && !"".equals(dtbbId)){
			criteriaCnt.add(Restrictions.eq("dtbb_id", dtbbId));
			criteria.add(Restrictions.eq("dtbb_id", dtbbId));
		}
		if(null != code && !"".equals(code) && "app".equals(code)){
			criteriaCnt.add(Restrictions.eq("status", "1"));
			criteria.add(Restrictions.eq("status", "1"));
		}
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}
	/**
	 * pc 留言分页查询
	 */
	@Override
	public Pagenate<DtbbYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String dtbb_id = map.get("dtbb_id");//动态播报id
		String role_code = map.get("role_code");//当前角色
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(DtbbYjEntity.class);
		criteriaCnt.setProjection(Projections.rowCount());//查询数据总条数
		Criteria criteria = session.createCriteria(DtbbYjEntity.class);//查询数据
		
		if (dtbb_id!=null &&!"".equals(dtbb_id)) {
			criteriaCnt.add(Restrictions.eq("dtbb_id", dtbb_id));
			criteria.add(Restrictions.eq("dtbb_id", dtbb_id));
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
			}else{
				criteriaCnt.add(Restrictions.eq("status", "1"));//只查询审核通过的  //1为已审核通过，0为未审核
				criteria.add(Restrictions.eq("status", "1"));
				
			}
			criteria.addOrder(Order.desc("createTime"));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	

}
