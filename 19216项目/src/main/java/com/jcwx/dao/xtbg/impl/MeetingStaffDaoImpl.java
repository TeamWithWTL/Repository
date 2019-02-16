package com.jcwx.dao.xtbg.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtbg.MeetingStaffDao;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
/**
 *参会人员Dao接口实现
 * @author 李伟
 * @time 2017年10月29日上午2:27:30
 */
@SuppressWarnings("unchecked")
@Repository
public class MeetingStaffDaoImpl extends BaseDaoImpl implements MeetingStaffDao{
	/**
	 * id查询集合
	 */
	@Override
	public List<MeetingStaffEntity> findByHyglId(String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(MeetingStaffEntity.class);
		criteria.add(Restrictions.eq("hygl_id", id));
		return criteria.list();
	}

	@Override
	public Object findStaff(String staffId,String id) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(MeetingStaffEntity.class);
		criteria.add(Restrictions.eq("acc_code", staffId));
		criteria.add(Restrictions.eq("hygl_id", id));
		return  criteria.uniqueResult();
	}
	
}
