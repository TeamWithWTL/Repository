package com.jcwx.dao.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.utils.Pagenate;
/**
 * 党政要闻Dao
 * @author 李伟
 * @time 2017年10月26日上午10:22:56
 */
public interface DzywDao extends BaseDao{
	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<DzywEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 时间倒叙查询廉政要闻集合
	 * @return
	 */
	List<DzywEntity> getDzywContentList();
	/**
	 * 查询为热点的数据
	 * @author 李伟
	 * @time 2017年11月4日下午3:11:40
	 * @param hot 热点参数
	 * @return
	 */
	List<DzywEntity> findByHot(String hot);

	
}
