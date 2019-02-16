package com.jcwx.dao.xtbg.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.xtbg.MeetingDao;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;
/**
 * 会议Dao接口实现
 * @author 李伟
 * @time 2017年10月29日上午2:24:16
 */
@SuppressWarnings("unchecked")
@Repository
public class MeetingDaoImpl extends BaseDaoImpl implements MeetingDao{
	/**
	 * 主页加载，分页
	 */
	@Override
	public Pagenate<MeetingEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map) {
		String title = map.get("title");// 标题/发布人
		String applyTime = map.get("applyTime");// 发布时间
		String accCode = map.get("accCode");//当前登录用户
		String roleCode = map.get("roleCode");//当前登录用户角色Id 字符串
		ArrayList<String> arrayList=new ArrayList<String>();//用户角色，保存list
		String app = map.get("app");
		String stamp = map.get("stamp");////加入查询标记  1为未开始的会议 2为已结束
		Date nowTime = DateUtils.parseDate(map.get("nowTime"), "yyyy-MM-dd HH:mm:ss");//当前时间
		String nowDate =map.get("nowDate");//当前时间  app会议管理查询 liwei
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(MeetingEntity.class).createAlias("meetStaffList","staff");// 数据集条数
		criteriaCnt.setProjection(Projections.rowCount());

		Criteria criteria = session.createCriteria(MeetingEntity.class).createAlias("meetStaffList","staff");// 数据集
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("user_name", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("create_date", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("create_date", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if(null != nowTime && !"".equals(nowTime)){
			criteriaCnt.add(Restrictions.and(Restrictions.lt("start_date", nowTime),Restrictions.gt("end_date", nowTime)));
			criteria.add(Restrictions.and(Restrictions.lt("start_date", nowTime),Restrictions.gt("end_date", nowTime)));
		}
		//查询会议状态，是结束，还是未开始
		if (nowDate!=null&&!"".equals(nowDate)) {
			if (stamp!=null&&!"".equals(stamp)) {
				if ("1".equals(stamp)) {//查询当前时间小于开始时间或结束时间，也就是未开始或未结束的会议
					criteriaCnt.add(Restrictions.or(Restrictions.gt("start_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")),
							 				Restrictions.or(Restrictions.gt("end_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")))));
					criteria.add(Restrictions.or(Restrictions.gt("start_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")),
			 				Restrictions.or(Restrictions.gt("end_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")))));
				}else if ("2".equals(stamp)) {//查询当前时间大于结束时间，也就是已结束的会议
					criteriaCnt.add(Restrictions.lt("end_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")));
					criteria.add(Restrictions.lt("end_date", DateUtils.parseDate(nowDate, "yyyy-MM-dd HH:mm:ss")));
				}
			}
		}
		//查询当前登录用户相关的
		if (null !=accCode && !"".equals(accCode)) {
			criteriaCnt.add(Restrictions.eq("staff.acc_code", accCode));
			criteria.add(Restrictions.eq("staff.acc_code", accCode));
			
		}
		//只查询角色权限相关的
		if (roleCode!=null&&!"".equals(roleCode)) {
			if (roleCode.indexOf(",")!=-1) {
				String[] roleCodes = roleCode.split(",");//当前登录人员角色,多角色，转存list
				arrayList = new ArrayList<String>(Arrays.asList(roleCodes));
			}else {
				arrayList.add(roleCode);//单角色直接添加
			}
			if (arrayList.contains("06")||arrayList.contains("99")) {
				
			}else{
				criteriaCnt.add(Restrictions.eq("tj_status", "1"));//只查询已发布的会议，，1为发布
				criteria.add(Restrictions.eq("tj_status", "1"));
			}
		}
		if (app!=null&&!"".equals(app)) {
			criteriaCnt.add(Restrictions.eq("tj_status", "1"));//只查询已发布的会议，，1为发布
			criteria.add(Restrictions.eq("tj_status", "1"));
		}
		// 排序
		//criteria.addOrder(Order.asc("is_hot"));
		criteria.addOrder(Order.asc("start_date"));
		criteria.addOrder(Order.desc("create_date"));
		
		return super.findByPage(pageNumber, pageSize, criteriaCnt, criteria);
	}

}
