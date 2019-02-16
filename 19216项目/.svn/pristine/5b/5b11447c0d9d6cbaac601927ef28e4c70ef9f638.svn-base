package com.jcwx.dao.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.utils.Pagenate;
/**
 * 曝光台Dao
 * @author 李伟
 * @time 2017年10月26日上午10:24:01
 */
public interface ExposureDao extends BaseDao {
	/**
	 * 分页接口
	 * @author 李伟
	 * @time 2017年10月26日上午10:27:27
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 时间倒叙查询曝光台集合
	 * @return
	 */
	List<ExposureEntity> getBgtContentList();
}
