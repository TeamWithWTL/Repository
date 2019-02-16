package com.jcwx.dao.xtgl.impl;

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
import com.jcwx.dao.xtgl.YhglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class YhglDaoImpl extends BaseDaoImpl implements YhglDao {

	@Override
	public Pagenate<SysAccCountLazy> findAccByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
		String name = paramMap.get("name"); // 姓名
		String accCode = paramMap.get("accCode"); // 账号
		String ssId = paramMap.get("ssId");// 服务站id
		String commId = paramMap.get("commId");// 社区id
		String typeCode = paramMap.get("typeCode");//账号类型
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(SysAccCount.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(SysAccCount.class);// 数据集
		
		criteriaCnt.add(Restrictions.ne("accCode", "admin"));
		criteria.add(Restrictions.ne("accCode", "admin"));
		
		// 组织查询条件
		if (accCode != null && !accCode.equals("")) {
			criteriaCnt.add(Restrictions.like("accCode", accCode, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("accCode", accCode, MatchMode.ANYWHERE));
		}
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (ssId != null && !ssId.equals("")) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (commId != null && !commId.equals("")) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (typeCode != null && !typeCode.equals("")) {
			if(typeCode.equals("1")){//查询内部成员
				criteriaCnt.add(Restrictions.eq("zh_type", "1"));
				criteria.add(Restrictions.eq("zh_type", "1"));
			}
			if(typeCode.equals("3")){//查询注册用户
				criteriaCnt.add(Restrictions.or(Restrictions.eq("zh_type", "2"),Restrictions.eq("zh_type", "3")));
				criteria.add(Restrictions.or(Restrictions.eq("zh_type", "2"),Restrictions.eq("zh_type", "3")));
			}
			if(typeCode.equals("4")){//查询部门成员
				criteriaCnt.add(Restrictions.eq("zh_type", "4"));
				criteria.add(Restrictions.eq("zh_type", "4"));
			}
		}
		// 按照角色编码和创建时间倒序排序
		criteria.addOrder(Order.desc("addTime"));
		return super.findByPage(pageNum, pageSize, criteriaCnt, criteria);
	}

	@Override
	public SysAccCountLazy findByToken(String tokenId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCountLazy.class);
		criteria.add(Restrictions.eq("token_id", tokenId));
		List<SysAccCountLazy> l = criteria.list();
		if (!l.isEmpty()) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<SysAccRole> findaccRole(String role) {
		Session session =  getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaData = session.createCriteria(SysAccRole.class);
		criteriaData.add(Restrictions.eq("roleCode", role));
		return criteriaData.list();
	}



	@Override
	public List<SysAccCount> findByParam( Map<String, String> map) {
		String name = map.get("name"); // 姓名
		String accCode = map.get("accCode"); // 账号
		String ssId = map.get("ssId");// 服务站id
		String commId = map.get("commId");// 社区id
		String deptId = map.get("deptId");// 社区id
		String isDept = map.get("isDept");// 查询部门成员 1 是  2否
		
		String zh_type = map.get("zh_type");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(SysAccCount.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(SysAccCount.class);// 数据集
		
		criteriaCnt.add(Restrictions.ne("accCode", "admin"));
		criteria.add(Restrictions.ne("accCode", "admin"));
		
		// 组织查询条件
		if (accCode != null && !accCode.equals("")) {
			criteriaCnt.add(Restrictions.like("accCode", accCode, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("accCode", accCode, MatchMode.ANYWHERE));
		}
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (ssId != null && !ssId.equals("")) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (commId != null && !commId.equals("")) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (deptId != null && !deptId.equals("")) {
			criteriaCnt.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("deptId", deptId));
		}
		if (zh_type != null && !"".equals(zh_type)) {
			criteriaCnt.add(Restrictions.eq("zh_type", zh_type));
			criteria.add(Restrictions.eq("zh_type", zh_type));
		}
		if (isDept != null && !isDept.equals("")) {
			if("1".equals(isDept)){
				criteria.add(Restrictions.or(Restrictions.ne("deptId",null), Restrictions.ne("deptId","")));
			}
		}
		// 按照角色编码和创建时间倒序排序
		criteria.addOrder(Order.desc("addTime"));
		return criteria.list();
	}

	@Override
	public SysAccCount getSq(Class<SysAccCount> class1, String accCode) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		criteria.add(Restrictions.eq("accCode", accCode));
		return (SysAccCount) criteria.uniqueResult();
		
	}

	@Override
	public List<SysAccCount> getName(Class<SysAccCount> class1, String role_code) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		criteria.add(Restrictions.like("role_code", role_code));
		return  criteria.list();
	}

	@Override
	public List<SysAccCount> getFwzName(Class<SysAccCount> class1, String role_code, String sqId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		criteria.add(Restrictions.like("role_code", role_code));
		criteria.add(Restrictions.eq("commId", sqId));
		return  criteria.list();
	}

	@Override
	public List<SysAccCount> getWgyName(Class<SysAccCount> class1, String role_code, String fwzId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccCount.class);
		criteria.add(Restrictions.like("role_code", role_code,MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("ssId", fwzId));
		return  criteria.list();
	}

	@Override
	public List<SysAccRole> findRole(String accCode) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccRole.class);
		criteria.add(Restrictions.eq("accCode", accCode));
		
		return  criteria.list();
	}

	@Override
	public ZzxxEntity getZzxxByFrId(String frId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ZzxxEntity.class);
		
		if(null != frId && !"".equals(frId)){
			criteria.add(Restrictions.eq("fr_id", frId));
		}
		if(criteria.list().size()>0){
			return (ZzxxEntity) criteria.list().get(0);
		}
		return null;
	}

	@Override
	public SysAccMore findSysAccMoreByFrId(String frId) {
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysAccMore.class);
		
		if(null != frId && !"".equals(frId)){
			criteria.add(Restrictions.eq("accCode", frId));
		}
		if(criteria.list().size()>0){
			return (SysAccMore) criteria.list().get(0);
		}
		return null;
	}

	@Override
	public Pagenate<SysAccCount> getGridPeople(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		
		String commId = map.get("commId");				// 社区编码
		String ssId = map.get("ssId");					// 服务站编码
		String gridId = map.get("gridId");				// 网格编码
		String dqCommId = map.get("dqCommId");			// 当前用户所负责的社区
		String dqSsId = map.get("dqSsId");				// 当前用户所负责的服务站
		String roleCode = map.get("roleCode");			// 查询角色为 “01” 的网格员
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(SysAccCount.class);	// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysAccCount.class);		// 数据集
		
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("commId", dqCommId));
			criteria.add(Restrictions.eq("commId", dqCommId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (dqSsId != null && !"".equals(dqSsId)) {
			criteriaCnt.add(Restrictions.eq("ssId", dqSsId));
			criteria.add(Restrictions.eq("ssId", dqSsId));
		}
		if (gridId != null && !"".equals(gridId)) {
			criteriaCnt.add(Restrictions.eq("gridId", gridId));
			criteria.add(Restrictions.eq("gridId", gridId));
		}
		if (roleCode != null && !"".equals(roleCode)) {
			criteriaCnt.add(Restrictions.like("role_code", roleCode, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("role_code", roleCode, MatchMode.ANYWHERE));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
}
