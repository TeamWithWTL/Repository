/**
 * 
 */
package com.jcwx.service.xtbg;

import java.util.Map;

import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
public interface TzggService {

	
	Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);

	/**
	 * @param id
	 * @return
	 */
	TzggEntity findById(String id);

	/**
	 * @param tzgg
	 * @param fName
	 */
	void save(TzggEntity tzgg, String fName);

	/**
	 * @param tzgg
	 * @param id
	 * @param fName
	 */
	void update(TzggEntity tzgg, String id, String fName);

	/**
	 * @param id
	 * @return
	 */
	TzggEntity getById(String id);

	/**
	 * @param id
	 */
	void delFj(String id);

	/**
	 * @param ids
	 */
	void del(String ids);

	
	

}
