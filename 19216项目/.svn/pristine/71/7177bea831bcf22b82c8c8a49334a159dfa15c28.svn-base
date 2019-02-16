package com.jcwx.dao.shgl.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.EventDao;
import com.jcwx.entity.Consts;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.shgl.EventDeal;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class EventDaoImpl extends BaseDaoImpl implements EventDao  {


	public Pagenate<Event> findEventByPage(Integer pageNumber, int pagesize,	Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String applyTime = map.get("applyTime");// 上报i时间
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String roleCode = map.get("roleCode");// 用户角色
		String applyCode = map.get("applyCode");//上报人ID
		String clrId = map.get("clrId");// 处理人ID
		String dealStatus = map.get("dealStatus");//处理状态
		String eventfrm = map.get("eventfrm");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(Event.class).createAlias("eventDeals", "deal");
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Criteria criteria = session.createCriteria(Event.class).createAlias("eventDeals", "deal");
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (applyCode != null && !"".equals(applyCode)) {
			criteriaCnt.add(Restrictions.eq("applyCode", applyCode));
			criteria.add(Restrictions.eq("applyCode", applyCode));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (eventfrm != null && !"".equals(eventfrm.trim())) {
			criteriaCnt.add(Restrictions.eq("eventfrm", eventfrm.trim()));
			criteria.add(Restrictions.eq("eventfrm", eventfrm.trim()));
		}
		
		if (dealStatus != null && !"".equals(dealStatus)) {
			Disjunction disa = Restrictions.disjunction();
			String[] status = dealStatus.split(";");
			for (int i = 0; i < status.length; i++) {
				String stat  = status[i];
				if(stat!= null && !"".equals(stat)){
					 disa.add(Restrictions.eq("deal.dealStatus", stat));
				}
			}
			 criteria.add(disa);
			 criteriaCnt.add(disa);
		}
		if (clrId != null && !"".equals(clrId)) {
			criteriaCnt.add(Restrictions.eq("deal.clrId", clrId));
			criteria.add(Restrictions.eq("deal.clrId", clrId));
		}
		// 排序
		criteria.addOrder(Order.desc("applyTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	
	
	public Pagenate<Event> appfindEventByPage(Integer pageNumber, int pagesize,	Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String applyTime = map.get("applyTime");// 上报i时间
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String applyCode = map.get("applyCode");//上报人ID
		String isOver = map.get("isOver");//是否结束
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(Event.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(Event.class);
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (applyCode != null && !"".equals(applyCode)) {
			criteriaCnt.add(Restrictions.eq("applyCode", applyCode));
			criteria.add(Restrictions.eq("applyCode", applyCode));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (isOver != null && !"".equals(isOver)) {
			criteriaCnt.add(Restrictions.eq("isOver", isOver));
			criteria.add(Restrictions.eq("isOver", isOver));
		}
		// 排序
		criteria.addOrder(Order.desc("applyTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	public Pagenate<EventDeal> findEventDealByPage(Integer pageNumber, int pagesize,	Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String applyTime = map.get("applyTime");// 上报i时间
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String clrId = map.get("clrId");// 处理人ID
		String roleCode = map.get("roleCode");// 用户角色
		String dealStatus = map.get("dealStatus");//处理状态
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(EventDeal.class).createAlias("event", "event");
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(EventDeal.class).createAlias("event", "event");

		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("event.title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("event.applyName", title, MatchMode.ANYWHERE))));
			criteria.add(Restrictions.or(Restrictions.like("event.title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("event.applyName", title, MatchMode.ANYWHERE))));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("event.applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("event.applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.or(Restrictions.eq("event.commId", commId),Restrictions.isNull("event.commId"),Restrictions.eq("event.commId", "")));
			criteria.add(Restrictions.or(Restrictions.eq("event.commId", commId),Restrictions.isNull("event.commId"),Restrictions.eq("event.commId", "")));
//			criteriaCnt.add(Restrictions.eq("event.commId", commId));
//			criteria.add(Restrictions.eq("event.commId", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.or(Restrictions.eq( "event.ssId", ssId),Restrictions.isNull("event.ssId"),Restrictions.eq("event.ssId", "")));
			criteria.add(Restrictions.or(Restrictions.eq( "event.ssId", ssId),Restrictions.isNull("event.ssId"),Restrictions.eq("event.ssId", "")));
//			criteriaCnt.add(Restrictions.eq("event.ssId", ssId));
//			criteria.add(Restrictions.eq("event.ssId", ssId));
		}
	

		if (clrId != null && !"".equals(clrId)) {
			criteriaCnt.add(Restrictions.eq("clrId", clrId));
			criteria.add(Restrictions.eq("clrId", clrId));
			criteria.add(Restrictions.eq("dealStatus", "0"));
			criteriaCnt.add(Restrictions.eq("dealStatus", "0"));
		}
//			criteriaCnt.add(Restrictions.eq("dealStatus", dealStatus));
//			criteria.add(Restrictions.eq("dealStatus", dealStatus));
		 Disjunction dis = Restrictions.disjunction();
		if (roleCode != null && !"".equals(roleCode)) {
			String roles [] = roleCode.split(",");
			for(String role : roles){
//				if("02".equals(role)||"03".equals(role)||"04".equals(role)||"08".equals(role)||"12".equals(role)){
					 dis.add(Restrictions.eq("curRoleId", role));
//				}
			//如果是街道办领导 或者工作人员  选择处理人
			}
			 criteria.add(dis);
			 criteriaCnt.add(dis);
			//
			boolean hassxxyRole = false;
			for(String role : roles){
			//如果是街道办信息员 需要查询处理街道办领导,和业务部门人员处理完成的事件
				if(Consts.ROLE_JDXXY.equals(role)){
					criteria.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					criteriaCnt.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					hassxxyRole = true;
					break;
				}
			}
			if(!hassxxyRole){
					criteria.add(Restrictions.eq("dealStatus", "0"));
					criteriaCnt.add(Restrictions.eq("dealStatus", "0"));
			}
		}
	
		// 排序
		criteria.addOrder(Order.desc("event.applyTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	
	public Pagenate<EventDeal> findSingleEventDealByPage(Integer pageNumber, int pagesize,	Map<String, String> map) {
		String title = map.get("title");// 标题/上报人
		String applyTime = map.get("applyTime");// 上报i时间
		String commId = map.get("commId");// 社区id
		String ssId = map.get("ssId");// 服务站id
		String clrId = map.get("clrId");// 处理人ID
		String roleCode = map.get("roleCode");// 用户角色
		String dealStatus = map.get("dealStatus");//处理状态
		String sjly = map.get("sjly"); //事件来源
		String applyCode = map.get("applyCode"); // 事件发布人Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(EventDeal.class).createAlias("event", "event");
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(EventDeal.class).createAlias("event", "event");
		// 组织查询条件
		if (title != null && !"".equals(title)) {
			criteriaCnt.add(Restrictions.like("event.title", title, MatchMode.ANYWHERE));
			criteria.add(Restrictions.like("event.title", title, MatchMode.ANYWHERE));
		}
		if (applyTime != null && !"".equals(applyTime)) {
			criteriaCnt.add(Restrictions.between("event.applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
			criteria.add(Restrictions.between("event.applyTime", DateUtils.parseDate(applyTime, "yyyy-MM-dd"),
					DateUtils.nextDate(applyTime, "yyyy-MM-dd")));
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("event.commId", commId));
			criteria.add(Restrictions.eq("event.commId", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteriaCnt.add(Restrictions.eq("event.ssId", ssId));
			criteria.add(Restrictions.eq("event.ssId", ssId));
		}
		if (clrId != null && !"".equals(clrId)) {
			criteriaCnt.add(Restrictions.eq("clrId", clrId));
			criteria.add(Restrictions.eq("clrId", clrId));
		}
		
		if (dealStatus != null && !"".equals(dealStatus)) {
			criteriaCnt.add(Restrictions.eq("dealStatus", dealStatus));
			criteria.add(Restrictions.eq("dealStatus", dealStatus));
		}
		
		if (sjly != null && !"".equals(sjly.trim())) {
			criteriaCnt.add(Restrictions.eq("event.eventfrm", sjly.trim()));
			criteria.add(Restrictions.eq("event.eventfrm", sjly.trim()));
		}
		
		if(applyCode!=null && !"".equals(applyCode.trim())){
			criteriaCnt.add(Restrictions.eq("event.applyCode", applyCode.trim()));
			criteria.add(Restrictions.eq("event.applyCode", applyCode.trim()));
		}
		
		Disjunction dis = Restrictions.disjunction();
		if (roleCode != null && !"".equals(roleCode)) {
			String roles [] = roleCode.split(",");
			for(String role : roles){
				if("02".equals(role)||"03".equals(role)||"04".equals(role)||"08".equals(role)||"12".equals(role)){
//					criteriaCnt.add(Restrictions.or(Restrictions.eq("deal.curRoleId", role)));
//					criteria.add(Restrictions.or(Restrictions.eq("deal.curRoleId", role)));
					 dis.add(Restrictions.eq("curRoleId", role));
				}
			}
			 criteria.add(dis);
			 criteriaCnt.add(dis);
			//
			boolean hassxxyRole = false;
			for(String role : roles){
			//如果是街道办信息员 需要查询处理街道办领导,和业务部门人员处理完成的事件
				if(Consts.ROLE_JDXXY.equals(role)){
					criteria.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					criteriaCnt.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					hassxxyRole = true;
					break;
				}
			}
			if(!hassxxyRole){
				criteria.add(Restrictions.eq("dealStatus", "0"));
				criteriaCnt.add(Restrictions.eq("dealStatus", "0"));
			}
		}
		
		// 排序
		criteria.addOrder(Order.desc("event.applyTime"));
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	public EventDeal findEventDeal(Map<String, String> map) {
		String eventId = map.get("eventId")==null?"":map.get("eventId").toString();
		String dealStatus = map.get("dealStatus")==null?"":map.get("dealStatus").toString();
		String hql = "from EventDeal k where k.event.id= '"+eventId+"' "
				   + " and k.dealStatus= '"+dealStatus+"'" ;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return 	(EventDeal) query.uniqueResult();
	}



	@Override
	public int findCountById(Map<String, String> addMap) {
		
		String sqId = addMap.get("sqId");
		String fwzId = addMap.get("fwzId");
		String wgId = addMap.get("wgId");
		String comeId = addMap.get("comeId");//事件来源ID
		String typeId = addMap.get("typeId");//事件类别ID
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		if(null != sqId && !"".equals(sqId)){
			criteria.add(Restrictions.eq("commId", sqId));
		}
		if(null != fwzId && !"".equals(fwzId)){
			criteria.add(Restrictions.eq("ssId", fwzId));
		}
		if(null != typeId && !"".equals(typeId)){
			criteria.add(Restrictions.eq("type", typeId));
		}
		if(null != wgId && !"".equals(wgId)){
			criteria.add(Restrictions.eq("gridId", wgId));
		}
		if(null != comeId && !"".equals(comeId)){
			criteria.add(Restrictions.eq("eventfrm", comeId));
		}
		
		Date date = new Date();//获取当前时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date); 
		if(date.getMonth() == 1){
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//
			calendar.add(calendar.MONTH, -2);//当前月减两个月
			Date str = calendar.getTime(); 
			criteria.add(Restrictions.ge("applyTime",str));
		}
		if(date.getMonth() == 2){
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//当前年减一年
			calendar.add(calendar.MONTH, -1);//当前月减一个月
			Date str = calendar.getTime(); 
			criteria.add(Restrictions.ge("applyTime",str));
		}
		if(date.getMonth() == 3){
			calendar.set(date.getYear(), 12, date.getDay());
			calendar.add(calendar.YEAR, -1);//当前年减一年
			Date str = calendar.getTime(); 
			criteria.add(Restrictions.ge("applyTime",str));
		}else{
			calendar.add(calendar.MONTH, -3);//当前月减三个月
			Date str = calendar.getTime(); 
			criteria.add(Restrictions.ge("applyTime",str));
		}
		
		return criteria.list().size();
	}



	@Override
	public List<EventDeal> findMyEventNo(Map<String, String> map) {
		
		String roleCode = map.get("roleCode");// 用户角色
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventDeal.class).createAlias("event", "event");
		
		Disjunction dis = Restrictions.disjunction();
		if (roleCode != null && !"".equals(roleCode)) {
			String roles [] = roleCode.split(",");
			for(String role : roles){
				if("02".equals(role)||"03".equals(role)||"04".equals(role)||"08".equals(role)||"12".equals(role)){
					 dis.add(Restrictions.eq("curRoleId", role));
				}
			}
			 criteria.add(dis);
			boolean hassxxyRole = false;
			for(String role : roles){
			//如果是业务部门工作人员 需要查询处理街道办领导,和业务部门人员处理完成的事件
				if("08".equals(role)){
					criteria.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					hassxxyRole = true;
					break;
				}
			}
			if(!hassxxyRole){
				criteria.add(Restrictions.eq("dealStatus", "0"));
			}
		}
		// 排序
		criteria.addOrder(Order.desc("event.applyTime"));
		return criteria.list();
	}

	@Override
	public Pagenate<Event> getSjContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		
		String title = cxMap.get("title");// 标题
		String sqId = cxMap.get("sqId");//社区ID
		String fwzId = cxMap.get("fwzId");//服务站ID
		String wgId = cxMap.get("wgId");//社区ID
		String sjTypeId = cxMap.get("sjTypeId");//事件类别ID
		String sjlyId = cxMap.get("sjlyId");//事件来源ID
		String startDate = cxMap.get("startDate");//开始时间
		String endDate = cxMap.get("endDate");//结束时间
		
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		Criteria criteriaCnt = session.createCriteria(Event.class);
		criteriaCnt.setProjection(Projections.rowCount());
		Criteria criteria = session.createCriteria(Event.class);
		
		if(null != title && !"".equals(title)){
			criteria.add(Restrictions.like("title", title,MatchMode.ANYWHERE));
			criteriaCnt.add(Restrictions.like("title", title,MatchMode.ANYWHERE));
		}
		if(null != sqId && !"".equals(sqId)){
			criteria.add(Restrictions.eq("commId", sqId));
			criteriaCnt.add(Restrictions.eq("commId", sqId));
		}
		if(null != sjTypeId && !"".equals(sjTypeId)){
			criteria.add(Restrictions.eq("type", sjTypeId));
			criteriaCnt.add(Restrictions.eq("type", sjTypeId));
		}
		if(null != fwzId && !"".equals(fwzId)){
			criteria.add(Restrictions.eq("ssId", fwzId));
			criteriaCnt.add(Restrictions.eq("ssId", fwzId));
		}
		if(null != wgId && !"".equals(wgId)){
			criteria.add(Restrictions.eq("gridId", wgId));
			criteriaCnt.add(Restrictions.eq("gridId", wgId));
		}
		if(null != sjlyId && !"".equals(sjlyId)){
			criteria.add(Restrictions.eq("eventfrm", sjlyId));
			criteriaCnt.add(Restrictions.eq("eventfrm", sjlyId));
		}
		
		// 组织查询条件
		if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate) &&  startDate.equals(endDate)) {
			criteriaCnt.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startDate)));
			criteria.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startDate)));
		}else{
			if (startDate != null && !"".equals(startDate)) {
					criteriaCnt.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startDate)));
					criteria.add(Restrictions.ge("applyTime", java.sql.Date.valueOf(startDate)));
			}
			if(endDate != null && !"".equals(endDate)){
				criteriaCnt.add(Restrictions.le("applyTime", java.sql.Date.valueOf(endDate)));
				criteria.add(Restrictions.le("applyTime", java.sql.Date.valueOf(endDate)));
			}
		}
//		if(startDate == null || "".equals(startDate) || endDate == null || "".equals(endDate)){
//			Date date = new Date();//获取当前时间
//			Calendar calendar = new GregorianCalendar();
//			calendar.setTime(date); 
//			if(date.getMonth() == 1){
//				calendar.set(date.getYear(), 12, date.getDay());
//				calendar.add(calendar.YEAR, -1);//
//				calendar.add(calendar.MONTH, -2);//当前月减两个月
//				Date str = calendar.getTime(); 
//				criteriaCnt.add(Restrictions.ge("applyTime",str));
//				criteria.add(Restrictions.ge("applyTime",str));
//			}
//			if(date.getMonth() == 2){
//				calendar.set(date.getYear(), 12, date.getDay());
//				calendar.add(calendar.YEAR, -1);//当前年减一年
//				calendar.add(calendar.MONTH, -1);//当前月减一个月
//				Date str = calendar.getTime(); 
//				criteriaCnt.add(Restrictions.ge("applyTime",str));
//				criteria.add(Restrictions.ge("applyTime",str));
//			}
//			if(date.getMonth() == 3){
//				calendar.set(date.getYear(), 12, date.getDay());
//				calendar.add(calendar.YEAR, -1);//当前年减一年
//				Date str = calendar.getTime(); 
//				criteriaCnt.add(Restrictions.ge("applyTime",str));
//				criteria.add(Restrictions.ge("applyTime",str));
//			}else{
//				calendar.add(calendar.MONTH, -3);//当前月减三个月
//				Date str = calendar.getTime(); 
//				criteriaCnt.add(Restrictions.ge("applyTime",str));
//				criteria.add(Restrictions.ge("applyTime",str));
//			}
//		}
		
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}
	
	public List<EventDeal> findEventDealList(Map<String, String> map) {
		
		String eventId = map.get("eventId");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventDeal.class).createAlias("event", "event");;
		if(null != eventId && !"".equals(eventId.trim())){
			criteria.add(Restrictions.eq("event.id", eventId));
		}
		criteria.addOrder(Order.desc("dealDate"));
		
		return (List<EventDeal>)criteria.list();
	}



	



	@Override
	public int myEventCount(Map<String, String> map) {
		String commId = map.get("commId");
		String ssId = map.get("ssId");
		String roleCode = map.get("roleCode");
		String clrId = map.get("clrId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventDeal.class).createAlias("event", "event");;
		criteria.setProjection(Projections.rowCount());
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.or(Restrictions.eq("event.commId", commId),Restrictions.isNull("event.commId"),Restrictions.eq("event.commId", "")));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.or(Restrictions.eq( "event.ssId", ssId),Restrictions.isNull("event.ssId"),Restrictions.eq("event.ssId", "")));
		}
	
		Disjunction dis = Restrictions.disjunction();
		if (roleCode != null && !"".equals(roleCode)) {
			String roles [] = roleCode.split(",");
			for(String role : roles){
					 dis.add(Restrictions.eq("curRoleId", role));
			}
			
			boolean hassxxyRole = false;
			for(String role : roles){
			//如果是街道办信息员 需要查询处理街道办领导,和业务部门人员处理完成的事件
				if(Consts.ROLE_JDXXY.equals(role)){
					criteria.add(Restrictions.or(Restrictions.eq("dealStatus", "0"),Restrictions.eq("dealStatus", "3")));
					hassxxyRole = true;
					break;
				}
			}
			
			if(!hassxxyRole){
					criteria.add(Restrictions.eq("dealStatus", "0"));
			}
			
		
			 criteria.add(dis);
		}
		if (clrId != null && !"".equals(clrId)) {
			criteria.add(Restrictions.eq("clrId", clrId));
			criteria.add(Restrictions.eq("dealStatus", "0"));
		}

		

		int rsCnts = ((Long) criteria.uniqueResult()).intValue();
		return rsCnts;
	}	
	
	public List<Event> findAllEvents(Map<String, String> map){
		
		String id = map.get("id");
		String commId = map.get("commId");
		String ssId = map.get("ssId");
		String title = map.get("title");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		if(null!=id && !"".equals(id.trim())){
			criteria.add(Restrictions.eq("id", id));
		}
		if(null!=commId && !"".equals(commId.trim())){
			criteria.add(Restrictions.eq("commId", commId));
		}
		if(null!=ssId && !"".equals(ssId.trim())){
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		// 组织查询条件
		if (title!=null && !"".equals(title)) {
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
		}
		criteria.addOrder(Order.desc("applyTime"));
		
		return (List<Event>)criteria.list();
	}
	
public List<Event> findRecentEvents(Map<String, String> map){
		
		String id = map.get("id");
		String commId = map.get("commId");
		String ssId = map.get("ssId");
		String title = map.get("title");
		String isOver = map.get("isOver");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		if(null!=id && !"".equals(id.trim())){
			criteria.add(Restrictions.eq("id", id));
		}
		if(null!=commId && !"".equals(commId.trim())){
			criteria.add(Restrictions.eq("commId", commId));
		}
		if(null!=ssId && !"".equals(ssId.trim())){
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		// 组织查询条件
		if (title!=null && !"".equals(title)) {
			criteria.add(Restrictions.or(Restrictions.like("title", title, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("applyName", title, MatchMode.ANYWHERE))));
		}
		
		if(null!=isOver && !"".equals(isOver.trim())){
			criteria.add(Restrictions.eq("isOver", isOver));
		}
		
		Calendar calendar=Calendar.getInstance();   
	    calendar.setTime(new Date()); 
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-3);
		criteria.add(Restrictions.gt("applyTime", calendar.getTime()));
		
		criteria.addOrder(Order.desc("applyTime"));
		
		return (List<Event>)criteria.list();
	}
	
}
