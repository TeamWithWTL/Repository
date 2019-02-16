package com.jcwx.dao.pub;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 部门人员管理 Dao
 * @author zhangkai 
 * 
*/
public interface DeptPersonDao extends BaseDao {
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<SysAccCountLazy> getByPage(int pageNum, int pageSize, Map<String, String> map);
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param map
	 * @return
	 */
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<SysAccCountLazy> findByPage(int pageNum, int pageSize, Map<String, String> map);

	
}
