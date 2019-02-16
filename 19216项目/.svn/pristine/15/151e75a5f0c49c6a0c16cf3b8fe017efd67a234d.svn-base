package com.jcwx.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.jcwx.utils.Pagenate;

/**
 * @author MaBo 2016年11月2日
 */

@SuppressWarnings({ "unchecked", "hiding" })
public class BaseDaoImpl implements BaseDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public <T> T findById(Class<T> type, Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(type, id);
	}

	@Override
	public <T> List<T> findAll(Class<T> type) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(type);
	}

	@Override
	public void save(Object... entities) {
		// TODO Auto-generated method stub
		for (Object entity : entities) {
			getHibernateTemplate().save(entity);
			getHibernateTemplate().flush();
		}
	}

	@Override
	public void saveOrUpdate(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(entity);
		getHibernateTemplate().flush();
	}

	@Override
	public void delete(Object... entities) {
		// TODO Auto-generated method stub
		for (Object entity : entities) {
			if (entity != null) {
				getHibernateTemplate().clear();
				getHibernateTemplate().delete(entity);
				getHibernateTemplate().flush();
			}
		}
	}

	@Override
	public void deleteById(Class<?> type, Serializable id) {
		// TODO Auto-generated method stub
		if (id == null) {
			return;
		}
		Object entity = findById(type, id);
		if (entity == null) {
			return;
		}
		delete(entity);
		getHibernateTemplate().flush();
	}

	@Override
	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, Criteria criteriaCnt, Criteria criteria) {
		// TODO Auto-generated method stub
		pageNum = pageNum <= 0 ? 1 : pageNum;
		// 查询总条数
		int rsCnts = ((Long) criteriaCnt.uniqueResult()).intValue();
		int pgCnts = (rsCnts % pageSize) == 0 ? rsCnts / pageSize : (rsCnts / pageSize) + 1;
		// 查询结果集
		criteria.setFirstResult((pageNum - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<T> list = criteria.list();

		Pagenate<T> pagenate = new Pagenate<T>();
		// 当前页页码
		pagenate.setPageNum(pageNum);
		// 页面大小（每页显示多少条）
		pagenate.setPageSize(pageSize);
		// 分页查询结果集
		pagenate.setList(list);
		// 总条数
		pagenate.setRsCnts(rsCnts);
		// 总页数
		pagenate.setPgCnts(pgCnts);
		// 首页
		pagenate.setFirstPage(1);
		// 上一页
		pagenate.setPrePage(pageNum <= 1 ? 1 : pageNum - 1);
		// 下一页
		pagenate.setNextPage(pageNum >= pgCnts ? pgCnts : pageNum + 1);
		// 尾页
		pagenate.setLastPage(pgCnts);
		// 页码，默认显示9页
		int showPgs = 5;
		int midPgNo = showPgs / 2 + 1;
		int startNo = 0;
		int endNo = 0;
		if (pgCnts <= midPgNo || pgCnts <= showPgs) {
			startNo = 1;
			endNo = pgCnts;
		} else if (pageNum <= midPgNo) {
			startNo = 1;
			endNo = showPgs;
		} else if (pageNum > midPgNo) {
			if (pageNum + (midPgNo - 1) <= pgCnts) {
				startNo = pageNum - (midPgNo - 1);
				endNo = pageNum + (midPgNo - 1);
			} else {
				startNo = pgCnts - showPgs + 1;
				endNo = pgCnts;
			}
		}
		List<Integer> pageNos = new ArrayList<Integer>();
		for (int i = startNo; i <= endNo; i++) {
			pageNos.add(i);
		}
		pagenate.setPageNos(pageNos);

		return pagenate;
	}

	@Override
	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, Query queryCnt, Query queryRs) {
		// TODO Auto-generated method stub
		pageNum = pageNum <= 0 ? 1 : pageNum;
		// 查询总条数
		int rsCnts = ((Long) queryCnt.uniqueResult()).intValue();
		int pgCnts = (rsCnts % pageSize) == 0 ? rsCnts / pageSize : (rsCnts / pageSize) + 1;
		// 查询结果集
		queryRs.setFirstResult((pageNum - 1) * pageSize);
		queryRs.setMaxResults(pageSize);
		List<T> list = queryRs.list();

		Pagenate<T> pagenate = new Pagenate<T>();
		// 当前页页码
		pagenate.setPageNum(pageNum);
		// 页面大小（每页显示多少条）
		pagenate.setPageSize(pageSize);
		// 分页查询结果集
		pagenate.setList(list);
		// 总条数
		pagenate.setRsCnts(rsCnts);
		// 总页数
		pagenate.setPgCnts(pgCnts);
		// 首页
		pagenate.setFirstPage(1);
		// 上一页
		pagenate.setPrePage(pageNum <= 1 ? 1 : pageNum - 1);
		// 下一页
		pagenate.setNextPage(pageNum >= pgCnts ? pgCnts : pageNum + 1);
		// 尾页
		pagenate.setLastPage(pgCnts);
		// 页码，默认显示9页
		int showPgs = 9;
		int midPgNo = showPgs / 2 + 1;
		int startNo = 0;
		int endNo = 0;
		if (pgCnts <= midPgNo || pgCnts <= showPgs) {
			startNo = 1;
			endNo = pgCnts;
		} else if (pageNum <= midPgNo) {
			startNo = 1;
			endNo = showPgs;
		} else if (pageNum > midPgNo) {
			if (pageNum + (midPgNo - 1) <= pgCnts) {
				startNo = pageNum - (midPgNo - 1);
				endNo = pageNum + (midPgNo - 1);
			} else {
				startNo = pgCnts - showPgs + 1;
				endNo = pgCnts;
			}
		}
		List<Integer> pageNos = new ArrayList<Integer>();
		for (int i = startNo; i <= endNo; i++) {
			pageNos.add(i);
		}
		pagenate.setPageNos(pageNos);

		return pagenate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, SQLQuery sqlQueryCnt, SQLQuery sqlQueryRs) {
		// TODO Auto-generated method stub

		pageNum = pageNum <= 0 ? 1 : pageNum;
		// 查询总条数
		int rsCnts = ((BigInteger) sqlQueryCnt.uniqueResult()).intValue();
		int pgCnts = (rsCnts % pageSize) == 0 ? rsCnts / pageSize : (rsCnts / pageSize) + 1;
		// 查询结果集
		sqlQueryRs.setFirstResult((pageNum - 1) * pageSize);
		sqlQueryRs.setMaxResults(pageSize);
		List<T> list = sqlQueryRs.list();

		Pagenate<T> pagenate = new Pagenate<T>();
		// 当前页页码
		pagenate.setPageNum(pageNum);
		// 页面大小（每页显示多少条）
		pagenate.setPageSize(pageSize);
		// 分页查询结果集
		pagenate.setList(list);
		// 总条数
		pagenate.setRsCnts(rsCnts);
		// 总页数
		pagenate.setPgCnts(pgCnts);
		// 首页
		pagenate.setFirstPage(1);
		// 上一页
		pagenate.setPrePage(pageNum <= 1 ? 1 : pageNum - 1);
		// 下一页
		pagenate.setNextPage(pageNum >= pgCnts ? pgCnts : pageNum + 1);
		// 尾页
		pagenate.setLastPage(pgCnts);
		// 页码，默认显示9页
		int showPgs = 9;
		int midPgNo = showPgs / 2 + 1;
		int startNo = 0;
		int endNo = 0;
		if (pgCnts <= midPgNo || pgCnts <= showPgs) {
			startNo = 1;
			endNo = pgCnts;
		} else if (pageNum <= midPgNo) {
			startNo = 1;
			endNo = showPgs;
		} else if (pageNum > midPgNo) {
			if (pageNum + (midPgNo - 1) <= pgCnts) {
				startNo = pageNum - (midPgNo - 1);
				endNo = pageNum + (midPgNo - 1);
			} else {
				startNo = pgCnts - showPgs + 1;
				endNo = pgCnts;
			}
		}
		List<Integer> pageNos = new ArrayList<Integer>();
		for (int i = startNo; i <= endNo; i++) {
			pageNos.add(i);
		}
		pagenate.setPageNos(pageNos);

		return pagenate;
	}

	// hql查询 无参
	@SuppressWarnings("rawtypes")
	@Override
	public List find(String hql) {
		return getHibernateTemplate().find(hql);
	}

	// hql 查询 多参
	@SuppressWarnings("rawtypes")
	@Override
	public List find(String hql, Object... objects) {
		// 创建查询
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if (objects != null) {
			// 为包含占位符的HQL语句设置参数
			for (int i = 0, len = objects.length; i < len; i++) {
				query.setParameter(i + "", objects[i]);
			}
		}
		return query.list();
	}

	// 根据HQL语句查询一般用于统计语句
	@Override
	public int findCount(String hql) {
		return ((Number) getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult())
				.intValue();
	}

	// 根据带占位符参数的HQL语句查询一般用于统计语句
	@Override
	public int findCount(String hql, Object... objects) {
		Query queryObject = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				queryObject.setParameter(i + "", objects[i]);
			}
		}
		return ((Number) queryObject.uniqueResult()).intValue();
	}

	// 根据hql与条件 更新或删除 成功为1 失败为0
	@Override
	public int updateOrDelete(String hql, Object... objects) {
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if (objects != null) {
			for (int i = 0, len = objects.length; i < len; i++) {
				query.setParameter(i + "", objects[i]);
			}
		}
		int result=query.executeUpdate();
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();
		return result;
	}

	@Override
	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, String countHql, String hql) {
		Query queryCnt = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countHql);
		Query queryRs = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		// TODO Auto-generated method stub
		pageNum = pageNum <= 0 ? 1 : pageNum;
		// 查询总条数
		int rsCnts = ((Long) queryCnt.uniqueResult()).intValue();
		int pgCnts = (rsCnts % pageSize) == 0 ? rsCnts / pageSize : (rsCnts / pageSize) + 1;
		// 查询结果集
		queryRs.setFirstResult((pageNum - 1) * pageSize);
		queryRs.setMaxResults(pageSize);
		List<T> list = queryRs.list();

		Pagenate<T> pagenate = new Pagenate<T>();
		// 当前页页码
		pagenate.setPageNum(pageNum);
		// 页面大小（每页显示多少条）
		pagenate.setPageSize(pageSize);
		// 分页查询结果集
		pagenate.setList(list);
		// 总条数
		pagenate.setRsCnts(rsCnts);
		// 总页数
		pagenate.setPgCnts(pgCnts);
		// 首页
		pagenate.setFirstPage(1);
		// 上一页
		pagenate.setPrePage(pageNum <= 1 ? 1 : pageNum - 1);
		// 下一页
		pagenate.setNextPage(pageNum >= pgCnts ? pgCnts : pageNum + 1);
		// 尾页
		pagenate.setLastPage(pgCnts);
		// 页码，默认显示9页
		int showPgs = 9;
		int midPgNo = showPgs / 2 + 1;
		int startNo = 0;
		int endNo = 0;
		if (pgCnts <= midPgNo || pgCnts <= showPgs) {
			startNo = 1;
			endNo = pgCnts;
		} else if (pageNum <= midPgNo) {
			startNo = 1;
			endNo = showPgs;
		} else if (pageNum > midPgNo) {
			if (pageNum + (midPgNo - 1) <= pgCnts) {
				startNo = pageNum - (midPgNo - 1);
				endNo = pageNum + (midPgNo - 1);
			} else {
				startNo = pgCnts - showPgs + 1;
				endNo = pgCnts;
			}
		}
		List<Integer> pageNos = new ArrayList<Integer>();
		for (int i = startNo; i <= endNo; i++) {
			pageNos.add(i);
		}
		pagenate.setPageNos(pageNos);

		return pagenate;
	}

}
