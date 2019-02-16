package com.jcwx.dao.shfw.impl;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shfw.ZwxxDao;
import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-政务信息 DaoImpl 
 * @author zhangkai
 *
 */
@Repository
public class ZwxxDaoImpl extends BaseDaoImpl implements ZwxxDao {

	@Override
	public Pagenate<ShfwZwxxEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		//查询条件
		String title = paramsMap.get("title");	//标题
		String code = paramsMap.get("app");	//app标识
		
		//获取session
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(ShfwZwxxEntity.class); 
		criteriaCnt.setProjection(Projections.rowCount());	//数据集条数
		
		Criteria criteriaData = session.createCriteria(ShfwZwxxEntity.class);	//数据集
		
		if(title != null && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteriaData.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if(code != null && !code.equals("") && code == "app"){//app列表显示
			criteriaCnt.add(Restrictions.eq("shStatus","1"));
			criteriaData.add(Restrictions.eq("shStatus","1"));
		}
		
		criteriaData.addOrder(Order.desc("createTime"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteriaData);
	}
	
}
