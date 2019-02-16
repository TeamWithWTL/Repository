package com.jcwx.dao.shgl.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.jcwx.dao.BaseDaoImpl;
import com.jcwx.dao.shgl.JmxxDao;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglBuildingEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.utils.Pagenate;

@SuppressWarnings("unchecked")
@Repository
public class JmxxDaoImpl extends BaseDaoImpl implements JmxxDao {

	@Override
	public Long countPerNumb(String gridId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");// 数据集条数
		criteria.add(Restrictions.eq("build.grid.id", gridId));
		criteria.setProjection(Projections.countDistinct("card_no"));
		return (Long) criteria.setProjection(Projections.countDistinct("card_no")).uniqueResult();
	}

	@Override
	public Long countPerNumbByparam(Map<String, String> map) {
		String buildId = map.get("buildId");
		String commId = map.get("commId");
		String ssId = map.get("ssId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");// 数据集条数
		
		if (buildId != null && !"".equals(buildId)) {
			criteria.add(Restrictions.eq("build.id", buildId));
		}
		if (commId != null && !"".equals(commId)) {
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId != null && !"".equals(ssId)) {
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		criteria.setProjection(Projections.countDistinct("card_no"));
		return (Long) criteria.setProjection(Projections.countDistinct("card_no")).uniqueResult();
	}

	@Override
	public Pagenate<ShglInmateEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		String name = map.get("name");	// 名称
		String commId = map.get("commId");	// 社区编码
		String ssId = map.get("ssId");	// 服务站编码
		String gridId = map.get("gridId");	// 网格编码
		String xqId = map.get("xqId");	// 小区编号
		String buildId = map.get("buildId");	// 楼宇编号
		String roomNo = map.get("roomNo");	// 房间号
		String unitNo = map.get("unitNo");	// 单元号
		String dqCommId = map.get("dqCommId");	//当前用户所负责的社区Id
		String dqSsId = map.get("dqSsId");	//当前用户所负责的服务站Id
		String dpgridId = map.get("dqgridId");	//当前用户所负责的网格Id
		String category = map.get("category");	//特殊人口类型Id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteriaCnt;
		Criteria criteria;
		if (category != null && !category.equals("")) {
			criteriaCnt = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build").createAlias("inmateTList", "intype");// 数据集条数
			criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build").createAlias("inmateTList", "intype");// 数据集
		}else{
			criteriaCnt = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");// 数据集条数
			criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");// 数据集
			
		}
//		criteriaCnt.setProjection(Projections.rowCount());
		criteriaCnt.setProjection(Projections.countDistinct("id"));
//		criteriaCnt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (name != null && !name.equals("")) {
			criteriaCnt.add(Restrictions.or(Restrictions.like("name", name, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("card_no", name, MatchMode.ANYWHERE))));//高帅--（2017年12月26日修改）
			criteria.add(Restrictions.or(Restrictions.like("name", name, MatchMode.ANYWHERE),
					Restrictions.or(Restrictions.like("card_no", name, MatchMode.ANYWHERE))));//高帅--（2017年12月26日修改）
		}
		if (commId != null && !"".equals(commId)) {
			criteriaCnt.add(Restrictions.eq("commId", commId));
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (dqCommId!=null&&!"".equals(dqCommId)) {
			criteriaCnt.add(Restrictions.eq("commId", dqCommId));
			criteria.add(Restrictions.eq("commId", dqCommId));
		}
		if (ssId != null && !ssId.equals("")) {
			criteriaCnt.add(Restrictions.eq("ssId", ssId));
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (xqId!=null&&!"".equals(xqId)) {
			criteriaCnt.add(Restrictions.eq("build.village.id", xqId));
			criteria.add(Restrictions.eq("build.village.id", xqId));
		}
		if (dqSsId!=null&&!"".equals(dqSsId)) {
			criteriaCnt.add(Restrictions.eq("ssId", dqSsId));
			criteria.add(Restrictions.eq("ssId", dqSsId));
		}
		if (gridId != null && !gridId.equals("")) {
			criteriaCnt.add(Restrictions.eq("build.grid.id", gridId));
			criteria.add(Restrictions.eq("build.grid.id", gridId));
		}
		if (dpgridId != null && !dpgridId.equals("")) {
			criteriaCnt.add(Restrictions.eq("build.grid.id", dpgridId));
			criteria.add(Restrictions.eq("build.grid.id", dpgridId));
		}
		if (buildId != null && !buildId.equals("")) {
			criteriaCnt.add(Restrictions.eq("build.id", buildId));
			criteria.add(Restrictions.eq("build.id", buildId));
		}
		if (unitNo != null && !unitNo.equals("")) {
			criteriaCnt.add(Restrictions.eq("unit_no", unitNo));
			criteria.add(Restrictions.eq("unit_no", unitNo));
		}
		if (roomNo != null && !roomNo.equals("")) {
			criteriaCnt.add(Restrictions.eq("room_no", roomNo));
			criteria.add(Restrictions.eq("room_no", roomNo));
		}
		if (category != null && !category.equals("")) {
			criteriaCnt.add(Restrictions.eq("intype.category", category));
			criteria.add(Restrictions.eq("intype.category", category));
		}
		// 排序
		criteria.addOrder(Order.asc("build.grid.id"));// 网格
		criteria.addOrder(Order.asc("name"));// 楼宇名称
		return super.findByPage(pageNumber, pagesize, criteriaCnt, criteria);
	}

	@Override
	public List<ShglInmateEntity> findAllInmates1(Map<String, String> map) {
		String id = map.get("id");// id
		String name = map.get("name");
		String card_no = map.get("card_no");// 身份证号
		String ssId = map.get("ssId");// 服务站编码
		String buildId = map.get("buildId");// 楼宇编码
		String unit_no = map.get("unit_no");// 单元号
		String room_no = map.get("room_no");// 房间号
		String type = map.get("type");// 人员类型
		
		String dqCommId = map.get("dqCommId");// 所属社区ID
		String dqSsId = map.get("dqSsId");// 所属服务站ID
		
		String commId = map.get("commId"); // 社区id
		String gridId = map.get("gridId"); // 网格id
		String xqId = map.get("xqId");
		String inmateType = map.get("inmateType"); // 居民类型
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build").createAlias("inmateTList", "inm", JoinType.LEFT_OUTER_JOIN);// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		if (card_no != null && !card_no.equals("")) {
			criteria.add(Restrictions.eq("card_no", card_no));
		}
		if (unit_no != null && !unit_no.equals("")) {
			criteria.add(Restrictions.eq("unit_no", unit_no));
		}
		if (room_no != null && !room_no.equals("")) {
			criteria.add(Restrictions.eq("room_no", room_no));
		}
		if (buildId != null && !buildId.equals("")) {
			criteria.add(Restrictions.eq("build.id", buildId));
		}
		if (ssId != null && !ssId.equals("")) {
			criteria.add(Restrictions.eq("ssId", ssId));
		}
		if (dqSsId != null && !dqSsId.equals("")) {
			criteria.add(Restrictions.eq("ssId", dqSsId));
		}
		if (type != null && !type.equals("")) {
			criteria.add(Restrictions.like("inm.category", type, MatchMode.ANYWHERE));
		}
		
		if (commId != null && !"".equals(commId.trim())) {
			criteria.add(Restrictions.eq("commId", commId));
		}
		if (dqCommId != null && !"".equals(dqCommId.trim())) {
			criteria.add(Restrictions.eq("commId", dqCommId));
		}
		if (gridId != null && !"".equals(gridId.trim())) {
			criteria.add(Restrictions.eq("build.grid.id", gridId));
		}
		if (xqId!=null&&!"".equals(xqId)) {
			criteria.add(Restrictions.eq("build.village.id", xqId));
		}
		if (inmateType != null && !"".equals(inmateType.trim())) {
			criteria.add(Restrictions.eq("inm.category", inmateType));
		}
		
		// 排序
		criteria.addOrder(Order.asc("build.grid.id"));
		criteria.addOrder(Order.asc("name"));// 名称
		return criteria.list();
	}

	@Override
	public int findCountBySqId(String id) {
		String sqId = id;//社区id
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class);
		if(!"".equals(sqId) && null != sqId){
			criteria.add(Restrictions.eq("commId", sqId));
		}
		return criteria.list().size();
	}
	
	/**
	 * 服务站人口数量
	 */
	@Override
	public int findSSBySqId(String sqId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class);
		if(!"".equals(sqId) && null != sqId){
			criteria.add(Restrictions.eq("ssId", sqId));
		}
		return criteria.list().size();
	}
	
	
	@Override
	public int findTsrkCount(String id) {
		String typeId = id;//特殊人群类别ID
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("inmateTList", "imt");
		if(!"".equals(typeId) && null != typeId){
			criteria.add(Restrictions.eq("imt.category", typeId));
		}
		return criteria.list().size();
	}
	
	
	/**
	 * 查询当前用户负责的社区
	 */
	@Override
	public List<ShglCmanagerEntity> findCmanager(String accCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglCmanagerEntity.class);
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("manager", accCode));
		}
		return criteria.list();
	}
	/**
	 * 查询负责的服务站
	 */
	@Override
	public List<ShglSmanagerEntity> findSmanager(String accCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglSmanagerEntity.class);
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("ssId", accCode));
		}
		return criteria.list();
	}
	/**
	 * 查询负责的小区
	 */
	@Override
	public List<ShglVmanagerEntity> findVmanager(String accCode) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglVmanagerEntity.class);
		if (accCode!=null&&!"".equals(accCode)) {
			criteria.add(Restrictions.eq("manager", accCode));
		}
		return criteria.list();
	}
	/**
	 * 以社区id,查询所有服务站
	 */
	@Override
	public List<ShglServiceStationEntity> findSsListByCommId(String logCommId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglServiceStationEntity.class).createAlias("community", "comm");
		criteria.add(Restrictions.eq("comm.id", logCommId));
		return criteria.list();
	}
	/**
	 * 以服务站id 查询所有的小区list
	 */
	@Override
	public List<ShglGridEntity> findVillListByCommId(String logSsId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglGridEntity.class).createAlias("serviceStation", "ss");
		criteria.add(Restrictions.eq("ss.id", logSsId));
		return criteria.list();
	}
	/**
	 * 查网格人员数量
	 */
	@Override
	public int findVillBySqId(String sqId) {
		int num=0;//所有楼宇总人口
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglBuildingEntity.class).createAlias("grid", "g");
		//查询网格下所有楼宇
		if(!"".equals(sqId) && null != sqId){
			criteria.add(Restrictions.eq("g.id", sqId));
		}
		List<ShglBuildingEntity> list = criteria.list();
		for (ShglBuildingEntity buildingEntity : list) {
			Criteria createCriteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "b");
			int size = createCriteria.add(Restrictions.eq("b.id", buildingEntity.getId())).list().size();
			num+=size;
		}
		return num;
	}

	@Override
	public int findInmateById(Map<String, String> map) {
		String itemCode = map.get("itemCode");//人员分类Id
		String commId = map.get("commId");//登录人负责的社区
		String ssId = map.get("ssId");//登录人负责的服务站
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria createCriteria = session.createCriteria(ShglInmateEntity.class).createAlias("inmateTList", "iList");
		if (itemCode!=null&&!"".equals(itemCode)) {
			createCriteria.add(Restrictions.eq("iList.category", itemCode));
		}
		if (commId!=null&&!"".equals(commId)) {
			createCriteria.add(Restrictions.eq("commId", commId));
		}
		if (ssId!=null&&!"".equals(ssId)) {
			createCriteria.add(Restrictions.eq("ssId", ssId));
		}
		return createCriteria.list().size();
	}
	/**
	 * 分页查询特殊人口
	 */
	@Override
	public Pagenate<ShglInmateEntity> getTsrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		String itemCode = cxMap.get("itemCode");
		String commId = cxMap.get("commId");
		String ssId = cxMap.get("ssId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria createCnt = session.createCriteria(ShglInmateEntity.class).createAlias("inmateTList", "iList",JoinType.RIGHT_OUTER_JOIN);//.createAlias("inmateTList", "iList",JoinType.RIGHT_OUTER_JOIN)
			 createCnt.setProjection(Projections.countDistinct("id"));
			 //createCnt.setProjection(Projections.rowCount());
			 Criteria	 criteria = session.createCriteria(ShglInmateEntity.class).createAlias("inmateTList", "iList",JoinType.RIGHT_OUTER_JOIN);
			 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
			if (itemCode!=null&&!"".equals(itemCode)) {
				createCnt.add(Restrictions.eq("iList.category", itemCode));
				criteria.add(Restrictions.eq("iList.category", itemCode));
			}
			if (commId!=null&&!"".equals(commId)) {
				createCnt.add(Restrictions.eq("commId", commId));
				criteria.add(Restrictions.eq("commId", commId));
			}
			if (ssId!=null&&!"".equals(ssId)) {
				createCnt.add(Restrictions.eq("ssId", ssId));
				criteria.add(Restrictions.eq("ssId", ssId));
			}
		return super.findByPage(pageNumber, pagesize, createCnt, criteria) ;
	}
	/**
	 * 查询特殊人口分类id
	 */
	@Override
	public String  findClassifyByName(String tsName) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		SysParamDesc result = (SysParamDesc) criteria.add(Restrictions.eq("itemName", tsName)).uniqueResult();
		return result.getValue1();
	}
	/**
	 * 查询社区，服务站，网格Id
	 */
	@Override
	public String findsqIdByName(String sqName, String isAdmin) {
		String id = null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (isAdmin=="admin") {
			Criteria criteria = session.createCriteria(ShglCommunityEntity.class);
			ShglCommunityEntity result=(ShglCommunityEntity)criteria.add(Restrictions.eq("name", sqName)).uniqueResult();
			id= result.getId();
		}
		if (isAdmin=="shequ") {
			Criteria criteria = session.createCriteria(ShglServiceStationEntity.class);
			ShglServiceStationEntity result=(ShglServiceStationEntity)criteria.add(Restrictions.eq("name", sqName)).uniqueResult();
			id= result.getId();
		}
		if (isAdmin=="fuwuzhan") {
			Criteria criteria = session.createCriteria(ShglGridEntity.class);
			ShglGridEntity result=(ShglGridEntity)criteria.add(Restrictions.eq("name", sqName)).uniqueResult();
			id= result.getId();
		}
		return id;
	}
	/**
	 * 社区人口分页查询
	 */
	@Override
	public Pagenate<ShglInmateEntity> getSqrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		String sqId = cxMap.get("sqId");
		String fwzId = cxMap.get("fwzId");
		String wgId = cxMap.get("wgId");
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria	createCnt ;
		Criteria	criteria ;
			//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
			if (wgId==null||"".equals(wgId)) {
				createCnt = session.createCriteria(ShglInmateEntity.class);
				createCnt.setProjection(Projections.rowCount());
				criteria = session.createCriteria(ShglInmateEntity.class);
				if (sqId!=null &&!"".equals(sqId)) {
					createCnt.add(Restrictions.eq("commId", sqId));
					criteria.add(Restrictions.eq("commId", sqId));
				}
				if (fwzId!=null&&!"".equals(fwzId)) {
					createCnt.add(Restrictions.eq("ssId", fwzId));
					criteria.add(Restrictions.eq("ssId", fwzId));
				}
				
			}else{
				//session.createCriteria(ShglInmateEntity.class).createAlias("build", "build")
				createCnt = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");
				createCnt.setProjection(Projections.rowCount());
				criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");
				if (wgId!=null&&!"".equals(wgId)) {
					createCnt.add(Restrictions.eq("build.grid.id", wgId));
					criteria.add(Restrictions.eq("build.grid.id", wgId));
				}
			}
	
		return super.findByPage(pageNumber, pagesize, createCnt, criteria) ;
	}
	/**
	 * 查询所有是户主的人员
	 */
	@Override
	public List<ShglInmateEntity> findHz() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class);
		criteria.add(Restrictions.eq("housemaster", "1"));//1表示是户主 0为不是户主
		
		return criteria.list();
	}
	/**
	 *  以户主身份查询家庭人员
	 */
	@Override
	public List<ShglInmateEntity> findFamilyByHzId(Map<String, String> tiesMap) {
		String commId = tiesMap.get("commId");//展示住户居住的社区id
		String ssId = tiesMap.get("ssId");///展示住户居住的服务站id
		String buildId = tiesMap.get("build");//展示住户居住的楼宇id
		String villageId = tiesMap.get("villageId");//展示住户居住的楼宇的小区id
		String unit_no = tiesMap.get("unit_no");//展示住户居住的单元号
		String room_no = tiesMap.get("room_no");//展示住户居住的房间号
		String gridId = tiesMap.get("gridId");//展示住户居住的网格Id
		String name = tiesMap.get("name");////展示住户居住的楼栋号
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");
//		if (commId!=null&&!"".equals(commId)) {
			criteria.add(Restrictions.eq("commId",commId));
//		}
//		if (ssId!=null&&!"".equals(ssId)) {
			criteria.add(Restrictions.eq("ssId", ssId));
//		}
//		if (buildId!=null&&!"".equals(buildId)) {
			criteria.add(Restrictions.eq("build.id", buildId));
//		}
//		if (name!=null&&!"".equals(name)) {
			criteria.add(Restrictions.eq("build.name", name));
//		}
//		if (villageId!=null&&!"".equals(villageId)) {
			criteria.add(Restrictions.eq("build.village.id", villageId));
//		}
//		if (gridId!=null&&!"".equals(gridId)) {
			criteria.add(Restrictions.eq("build.grid.id", gridId));
//		}
//		if (unit_no!=null&&!"".equals(unit_no)) {
			criteria.add(Restrictions.eq("unit_no", unit_no));
//		}
//		if (room_no!=null&&!"".equals(room_no)) {
			criteria.add(Restrictions.eq("room_no", room_no));
//		}
		return criteria.list();
	}
	
	/**
	 * 查询户主关系字段表的名字
	 */
	@Override
	public SysParamDesc findHzidName(String relation) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);
		criteria.add(Restrictions.eq("value1", relation)).add(Restrictions.eq("code", "10002"));
		return (SysParamDesc) criteria.uniqueResult();
	}

	@Override
	public List<ShglInmateEntity> findAllInmates(Map<String, String> map) {
		String id = map.get("id");// id
		String name = map.get("name");
		String card_no = map.get("card_no");// 身份证号
		String buildId = map.get("buildId");// 楼宇编码
		String unit_no = map.get("unit_no");// 单元号
		String room_no = map.get("room_no");// 房间号
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build").createAlias("inmateTList", "inm", JoinType.LEFT_OUTER_JOIN);// 数据集
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //去重数据
		// 组织查询条件
		if (id != null && !id.equals("")) {
			criteria.add(Restrictions.ne("id", id));
		}
		
		if (name != null && !name.equals("")) {
			criteria.add(Restrictions.eq("name", name));
		}
		if (card_no != null && !card_no.equals("")) {
			criteria.add(Restrictions.eq("card_no", card_no));
		}
		if (unit_no != null && !unit_no.equals("")) {
			criteria.add(Restrictions.eq("unit_no", unit_no));
		}
		if (room_no != null && !room_no.equals("")) {
			criteria.add(Restrictions.eq("room_no", room_no));
		}
		if (buildId != null && !buildId.equals("")) {
			criteria.add(Restrictions.eq("build.id", buildId));
		}
		
		// 排序
		criteria.addOrder(Order.asc("build.grid.id"));
		criteria.addOrder(Order.asc("name"));// 名称
		return criteria.list();
	}

	@Override
	public int findLyHsCount(Map<String, String> addMap) {
		
		String buildId = addMap.get("buildId");//楼宇ID
		String unitCnt = addMap.get("unitCnt");//所属单元
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "bd");// 数据集
		
		if(null != buildId && !"".equals(buildId)){
			criteria.add(Restrictions.eq("bd.id", buildId));
		}
		if(null != unitCnt && !"".equals(unitCnt)){
			criteria.add(Restrictions.eq("unit_no", unitCnt));
		}
		criteria.setProjection(Projections.distinct(Projections.property("room_no")));
		List<ShglInmateEntity> inmateEntities = criteria.list();
		if(inmateEntities.size()>0){
			return inmateEntities.size();
		}else{
			return 0;
		}
	}

	@Override
	public List<SysParamDesc> getTypeList() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(SysParamDesc.class);// 数据集条数
		criteria.add(Restrictions.eq("code", "10005"));
		return criteria.list();
	}

	@Override
	public List<ShglInmateEntity> findJm(String buildId, String unit_no, String room_no) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(ShglInmateEntity.class).createAlias("build", "build");// 数据集条数
		criteria.add(Restrictions.eq("build.id", buildId));
		criteria.add(Restrictions.eq("unit_no", unit_no));
		criteria.add(Restrictions.eq("room_no", room_no));
		return criteria.list();
	}

}
