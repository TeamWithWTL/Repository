package com.jcwx.dao.dflz.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.dflz.ComplainHandleDao;
import com.jcwx.entity.dflz.ComplainHandleEntity;
/**
 * 投诉举报处理 Dao实现类
 * @author 李伟
 * @time 2017年10月26日上午10:36:13
 */
@SuppressWarnings("unchecked")
@Repository
public class ComplainHandleDaoImpl extends BaseDaoImpl implements ComplainHandleDao{
	/**
	 * 查询待处理投诉举报记录
	 */
	@Override
	public ComplainHandleEntity finComHandle(Map<String, String> map) {
		String tsjbId = map.get("tsjb_id");
		String accCode = map.get("accCode");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ComplainHandleEntity.class).createAlias("complainEntity", "comp");
		if (tsjbId!=null&&!"".equals(tsjbId)) {
			criteria.add(Restrictions.eq("comp.id", tsjbId));
		}
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("acc_code", accCode));
		}
		return (ComplainHandleEntity) criteria.uniqueResult();
	}

	

}
