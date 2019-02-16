package com.jcwx.dao.dflz.impl;

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
import com.jcwx.dao.dflz.ComplainDao;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
/**
 * 投诉Dao实现类
 * @author 李伟
 * @time 2017年10月26日上午10:34:40
 */
@SuppressWarnings("unchecked")
@Repository
public class ComplainDaoImpl extends BaseDaoImpl implements ComplainDao{

	@Override
	public Pagenate<ComplainEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		String accCode = map.get("accCode");//当前登录人员id
		String roleCode = map.get("roleCode");
		String status = map.get("status");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ComplainEntity.class).createAlias("compHandList", "cList");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ComplainEntity.class).createAlias("compHandList", "cList");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("create_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("create_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		
		if (roleCode!=null &&!"".equals(roleCode)) {
			ArrayList<String> arrayList=new ArrayList<String>();
			if (roleCode.indexOf(",")!=-1) {
				String[] roleCodes = roleCode.split(",");//当前登录人员角色
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(roleCode);
			}
			if (arrayList.contains("10")||arrayList.contains("99")) {
				//查询处理当前用户待处理的数据
				if (accCode!=null&&!"".equals(accCode)) {
					criteriaCnt.add(Restrictions.or(Restrictions.eq("cl_name_id", accCode),
											Restrictions.or(Restrictions.eq("cList.acc_code", accCode))));
					criteria.add(Restrictions.or(Restrictions.eq("cl_name_id", accCode),
							Restrictions.or(Restrictions.eq("cList.acc_code", accCode))));
				}
			}else {
				criteriaCnt.add(Restrictions.eq("cList.pf_status", "2"));
				criteria.add(Restrictions.eq("cList.pf_status", "2"));
				//查询处理当前用户待处理的数据
				if (accCode!=null&&!"".equals(accCode)) {
					criteriaCnt.add(Restrictions.eq("cList.acc_code", accCode));
					criteria.add(Restrictions.eq("cList.acc_code", accCode));
				}
			}
		}
		//查询处理状态
		if (status!=null &&!"".equals(status)) {
			criteriaCnt.add(Restrictions.eq("cList.cl_status", status));
			criteria.add(Restrictions.eq("cList.cl_status", status));
		}
		
		criteria.addOrder(Order.desc("create_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	/**
	 * 已投诉处理表多对一，查询投诉记录表
	 */
	@Override
	public Pagenate<ComplainHandleEntity> findComPHandByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		String accCode = map.get("accCode");//当前登录人员id
		String roleCode = map.get("roleCode");
		String status = map.get("status");
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt = session.createCriteria(ComplainHandleEntity.class).createAlias("complainEntity", "comp");
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(ComplainHandleEntity.class).createAlias("complainEntity", "comp");
		
		if (title!=null&&!"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("comp.title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("comp.user_name", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("comp.title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("comp.user_name", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("comp.create_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("comp.create_time", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		
		if (roleCode!=null &&!"".equals(roleCode)) {
			if (roleCode.indexOf(",")!=-1) {
				String[] roleCodes = roleCode.split(",");//当前登录人员角色
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(roleCode);
			}
			if (arrayList.contains("10")||arrayList.contains("99")) {
				
				//查询处理当前用户待处理的数据
				criteriaCnt.add(Restrictions.eq("pf_status", "1"));
				criteria.add(Restrictions.eq("pf_status", "1"));
				if (accCode!=null&&!"".equals(accCode)) {
					criteriaCnt.add(Restrictions.eq("acc_code", accCode));
					criteria.add(Restrictions.eq("acc_code", accCode));
				}
			}else {
				criteriaCnt.add(Restrictions.eq("pf_status", "2"));
				criteria.add(Restrictions.eq("pf_status", "2"));
				//查询处理当前用户待处理的数据
				if (accCode!=null&&!"".equals(accCode)) {
					criteriaCnt.add(Restrictions.eq("acc_code", accCode));
					criteria.add(Restrictions.eq("acc_code", accCode));
				}
			}
		}
		
		//查询处理状态
				if (status!=null &&!"".equals(status)) {
					criteriaCnt.add(Restrictions.eq("cl_status", status));
					criteria.add(Restrictions.eq("cl_status", status));
				}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	/**
	 * 查询所有党风廉政人员
	 */
	@Override
	public List<SysAccRole> findByDflzYhList(String string) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccRole.class);
		criteria.add(Restrictions.eq("roleCode",string));
		return criteria.list();
	}
	/**
	 * 查询最后处理情况
	 */
	@Override
	public ComplainHandleEntity findYjById(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ComplainHandleEntity.class).createAlias("complainEntity", "comp");
		if (id!=null&&!"".equals(id)) {
			criteria.add(Restrictions.eq("comp.id", id));
		}
		//查询已经转发，并已经处理后的，记录
		criteria.add(Restrictions.eq("cl_status", "1")).add(Restrictions.eq("pf_status", "2"));
		return (ComplainHandleEntity) criteria.uniqueResult();
	}
	/**
	 * itemCode查询二级数据字典
	 */
	@Override
	public SysParamDesc findParamDescById(String jb_typeId) {
		String[] split = jb_typeId.split(",");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		criteria.add(Restrictions.eqOrIsNull("code", split[0]));
		criteria.add(Restrictions.eq("itemCode", split[1]));
		return (SysParamDesc) criteria.uniqueResult();
	}


}
