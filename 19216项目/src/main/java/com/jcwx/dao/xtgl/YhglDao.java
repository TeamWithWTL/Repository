package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 用户管理
 * @author Wjx
 *
 */
public interface YhglDao extends BaseDao{
	
	/**
	 * 分页查询--用户
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<SysAccCountLazy> findAccByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	
	
	public SysAccCountLazy findByToken(String tokenId);



	public List<SysAccRole> findaccRole(String accCode);



	public List<SysAccCount> findByParam( Map<String, String> map);



	SysAccCount getSq(Class<SysAccCount> class1, String accCode);



	 List<SysAccCount> getName(Class<SysAccCount> class1, String role_code);



	List<SysAccCount> getFwzName(Class<SysAccCount> class1, String role_code, String sqId);



	 List<SysAccCount> getWgyName(Class<SysAccCount> class1, String role_code, String fwzId);



	public List<SysAccRole> findRole(String accCode);



	/**
	 * 根据法人ID查询社会组织
	 * @param string
	 * @return
	 */
	public ZzxxEntity getZzxxByFrId(String string);



	/**
	 * 根据法人Id查询SysAccMore
	 * @param string
	 * @return
	 */
	public SysAccMore findSysAccMoreByFrId(String string);



	/**
	 * 分页查询网格员
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	public Pagenate<SysAccCount> getGridPeople(Integer pageNumber,
			int pagesize, Map<String, String> map);
}
