package com.jcwx.service.sjzx;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.Event;
import com.jcwx.utils.Pagenate;

/**
 * @author xushu
 *	数据中心-事件统计
 */
public interface SjtjService {
   
	/**
	 * 按条件查询事件统计的数据
	 * @param pageNumber
	 * @param pagesize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<Event> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> paramsMap);

	/**
	 * 查询事件
	 * @param paramsMap
	 * @return
	 */
	List<Event> findEvent(Map<String, String> paramsMap);

}
