/**
 * 
 */
package com.jcwx.service.xtbg;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.entity.xtbg.TzggRyEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
public interface TzggRyService {

	

	/**
	 * @param pid
	 * @param cid
	 * @param pname
	 */
	void saveOrUpdate(String pid,String tid,String pname);

	void save(TzggRyEntity tzggRy);
	
	/**
	 * 根据接收人账号查询通知id
	 * @param id
	 * @return
	 */
	public List<TzggRyEntity> findByAccCode(String id);
	
	/**
	 * 根据条件查询通知公告
	 * @param pageNumber
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pageSize, Map<String, Object> map);
}
