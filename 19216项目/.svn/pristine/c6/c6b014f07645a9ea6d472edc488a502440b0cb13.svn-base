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
import com.jcwx.dao.dflz.ExposureDao;
import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
/**
 * 曝光台Dao实现类
 * @author 李伟
 * @time 2017年10月26日上午10:33:04
 */
@SuppressWarnings("unchecked")
@Repository
public class ExposureDaoImpl extends BaseDaoImpl implements ExposureDao {

	@Override
	public Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		String roleCode = map.get("roleCode");//角色id
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(ExposureEntity.class);// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(ExposureEntity.class);// 数据集
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
		
		if (roleCode!=null&&!"".equals(roleCode)) {
			if (roleCode.indexOf(",")!=-1) {
				String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(roleCode);//单角色直接添加
			}
			if (arrayList.contains("10")||arrayList.contains("99")) {
				
			}else {
				criteriaCnt.add(Restrictions.eq("sh_status", "1"));//普通人员无权查看，设置差不到的值
				criteria.add(Restrictions.eq("sh_status", "1"));
			}
		}
		// 排序
		criteria.addOrder(Order.desc("create_time"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ExposureEntity> getBgtContentList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ExposureEntity.class);// 数据集
		criteria.addOrder(Order.desc("create_time"));//时间降序
		return criteria.list();
	}
}
