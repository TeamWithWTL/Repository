package com.jcwx.dao.xtbg;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.utils.Pagenate;

public interface RdxwDao extends BaseDao{

	/**
	 * 获取热点新闻内容信息
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	public Pagenate<RdxwEntity> getRdxwContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);

	/**
	 * 时间倒叙查询热点新闻集合
	 * @param addMap 
	 * @return
	 */
	public List<RdxwEntity> getRdxwContentList(Map<String, String> addMap);

	/**
	 * 查询热点数据
	 * @param hot
	 * @return
	 */
	public List<RdxwEntity> findByHot(String hot);

	/**
	 * 查询置顶数据
	 * @param goTop
	 * @return
	 */
	public List<RdxwEntity> findByTop(String goTop);

}
