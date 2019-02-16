package com.jcwx.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author MaBo 2016年11月2日
 */
@SuppressWarnings("hiding")
public interface BaseDao {

	public <T> T findById(Class<T> type, Serializable id);

	public <T> List<T> findAll(Class<T> type);

	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, Criteria criteriaCnt, Criteria criteria);

	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, Query queryCnt, Query queryRs);

	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, SQLQuery sqlQueryCnt, SQLQuery sqlQueryRs);

	public void save(Object... entities);

	public void saveOrUpdate(Object entity);

	public void delete(Object... entities);

	public void deleteById(Class<?> type, Serializable id);

	/**
	 * hql查询无参
	 * 
	 * @param hql
	 * @return list
	 */
	public <T> List<T> find(String hql);

	/**
	 * 根据hql查询(可有多个参数)
	 * 
	 * @param hql
	 * @param params
	 * @return list
	 */
	public <T> List<T> find(String hql, Object... objects);

	/**
	 * 根据HQL语句查询一般用于统计语句
	 * 
	 * @param hql
	 * @return int
	 */
	public int findCount(String hql);

	/**
	 * 根据带占位符参数的HQL语句查询一般用于统计语句
	 * 
	 * @param hql
	 * @param params
	 * @return int
	 */
	public int findCount(String hql, Object... bjects);

	/**
	 * 根据hql更新或删除 更新 成功为1 失败为0
	 * 
	 * @param hql
	 * @param objects
	 * @return int
	 */
	public int updateOrDelete(String hql, Object... objects);

	/**
	 * 根据 hql 语句查询分页数据
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param countHql
	 * @param hql
	 * @return Pagenate
	 */
	public <T> Pagenate<T> findByPage(int pageNum, int pageSize, String countHql, String hql);
}
