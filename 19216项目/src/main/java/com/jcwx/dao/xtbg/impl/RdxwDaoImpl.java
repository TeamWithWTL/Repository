package com.jcwx.dao.xtbg.impl;

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
import com.jcwx.dao.xtbg.RdxwDao;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;


@SuppressWarnings("unused")
@Repository
public class RdxwDaoImpl extends BaseDaoImpl implements RdxwDao{
/**
 * 热点新闻内容信息
 */
	@Override
	public Pagenate<RdxwEntity> getRdxwContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		//查询条件
		String title = cxMap.get("title");
		String code = cxMap.get("code");//手机端只查看审核通过的数据， //或PC端首页热点新闻更多标识--高帅（2017年12月14日--代码修改）
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Criteria criteriaCnt = session.createCriteria(RdxwEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(RdxwEntity.class);// 数据集
	
		if(null != title && !title.equals("")){
			criteriaCnt.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if(null != code && !"".equals(code) && "app".equals(code)){
			criteriaCnt.add(Restrictions.eq("shStatus", "1"));
			criteria.add(Restrictions.eq("shStatus", "1"));
		}
		// 按照添加时间倒序排序
		criteria.addOrder(Order.desc("createDate"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

@Override
public List<RdxwEntity> getRdxwContentList(Map<String, String> addMap) {
	String noRd = addMap.get("noRd");
	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Criteria criteria = session.createCriteria(RdxwEntity.class);// 数据集
	String iSstatus = "1";
	criteria.add(Restrictions.eq("shStatus", iSstatus));//审核通过的
	if(null == noRd || "".equals(noRd)){
		criteria.add(Restrictions.eq("isHot", "1"));//已置为热点的
	}else{
		criteria.add(Restrictions.eq("isHot", "2"));//不为热点的
	}
	criteria.addOrder(Order.desc("createDate"));
	return criteria.list();
}

/**
 * 查询热点数据
 */
@Override
public List<RdxwEntity> findByHot(String hot) {
	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Criteria criteria = session.createCriteria(RdxwEntity.class);
	criteria.add(Restrictions.eq("isHot", hot));
	return criteria.list();
}

@Override
public List<RdxwEntity> findByTop(String goTop) {
	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Criteria criteria = session.createCriteria(RdxwEntity.class);
	criteria.add(Restrictions.eq("isTop", goTop));
	return criteria.list();
}

}
