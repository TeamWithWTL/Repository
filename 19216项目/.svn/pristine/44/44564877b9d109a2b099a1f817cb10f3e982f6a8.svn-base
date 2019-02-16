package com.jcwx.dao.xtbg.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtbg.TxlglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.utils.Pagenate;


/**
 * 通讯录管理
 * @author LiuMengMeng
 *
 */
@SuppressWarnings("unused")
@Repository

public class TxlglDaoImpl extends BaseDaoImpl implements TxlglDao{
/**
 * 通讯录管理
 */
	@Override
	public Pagenate<SysAccCount> getTxlglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		//查询条件
		String name = map.get("name");	//姓名
		String duty = map.get("duty");	//职务	
		String phone = map.get("phone");//手机号
		String deptId = map.get("deptId");	//部门id
		String deptNames = map.get("deptNames");//部门名称
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(SysAccCount.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(SysAccCount.class);// 数据集
	
		if(null != name && !name.equals("")){
			criteriaCnt.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		if(null !=duty && !duty.equals("")){
			criteriaCnt.add(Restrictions.like("duty", duty,MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("duty", duty,MatchMode.ANYWHERE));
		}
		
		if(null !=phone && !phone.equals("")){
			criteriaCnt.add(Restrictions.like("phone", phone,MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("phone", phone,MatchMode.ANYWHERE));
		}
		
	
		if(null != deptId && !"".equals(deptId)){
			criteriaCnt.add(Restrictions.eq("deptId", deptId));
			criteria.add(Restrictions.eq("deptId", deptId));
		}
		
		if(null !=deptNames && !deptNames.equals("")){
			criteriaCnt.add(Restrictions.like("deptNames", deptNames,MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("deptNames", deptNames,MatchMode.ANYWHERE));
		}
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

}
