package com.jcwx.dao.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.CompAcceEntity;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.utils.Pagenate;
/**
 * app投诉举报Dao接口
 * @author 李伟
 * @time 2017年11月9日上午8:25:15
 */
public interface AppComplainDao extends BaseDao{
	/**
	 * 查询app投诉举报信息
	 * @author 李伟
	 * @time 2017年11月9日上午8:42:42
	 * @param pageNum
	 * @param pageSize
	 * @param hashMap
	 * @return
	 */
	Pagenate<ComplainEntity> findByPage(Integer pageNum, int pageSize, Map<String, String> hashMap);
	/**
	 * 根据角色id查询用户list
	 * @author 李伟
	 * @time 2017年11月9日下午3:29:08
	 * @param string
	 * @return
	 */
	List<SysAccCount> findByRolecode(String roleCode);
	/**
	 * app添加修改投诉附件
	 * @author 李伟
	 * @time 2017年11月18日下午1:54:30
	 * @param compAcceEntity
	 */
	void saveOrUpdateCompArrt(CompAcceEntity compAcceEntity);

}
