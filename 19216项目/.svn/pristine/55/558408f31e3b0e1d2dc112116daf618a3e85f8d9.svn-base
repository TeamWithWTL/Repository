package com.jcwx.dao.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.Pagenate;
/**
 * 投诉Dao
 * @author 李伟
 * @time 2017年10月26日上午10:22:36
 */
public interface ComplainDao extends BaseDao {
	/**
	 * 分页加载主页
	 * @author 李伟
	 * @time 2017年10月26日下午1:44:34
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ComplainEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	/**
	 * 投诉处理表为主，多对一查询
	 * @author 李伟
	 * @time 2017年11月14日下午2:19:24
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ComplainHandleEntity>findComPHandByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	/**
	 * 查询所有党风廉政人员
	 * @author 李伟
	 * @time 2017年11月21日上午11:16:19
	 * @param string
	 * @return
	 */
	List<SysAccRole> findByDflzYhList(String string);
	/**
	 * 查询最后处理情况
	 * @author 李伟
	 * @time 2017年11月21日下午1:55:09
	 * @param id
	 * @return
	 */
	ComplainHandleEntity findYjById(String id);
	/**
	 * 查询二级数据字典
	 * @author 李伟
	 * @time 2017年11月23日下午2:59:55
	 * @param jb_typeId
	 * @return
	 */
	SysParamDesc findParamDescById(String jb_typeId);
}
