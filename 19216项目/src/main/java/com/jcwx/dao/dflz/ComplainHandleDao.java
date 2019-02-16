package com.jcwx.dao.dflz;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.ComplainHandleEntity;

/**
 * 投诉处理人Dao
 * @author 李伟
 * @time 2017年10月26日上午10:28:27
 */
public interface ComplainHandleDao extends BaseDao{
	/**
	 *  查询待处理投诉举报记录
	 * @author 李伟
	 * @time 2017年11月10日上午10:22:35
	 * @param map
	 * @return
	 */
	ComplainHandleEntity finComHandle(Map<String, String> map);
	

}
