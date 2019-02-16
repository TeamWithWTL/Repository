package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.TaskDealEntity;
import com.jcwx.utils.Pagenate;

public interface RwclDao extends BaseDao{

	Pagenate<RwClEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 查询我的未处理任务
	 * @param rwMap
	 * @return
	 */
	List<RwClEntity> findMyRwNo(Map<String, String> rwMap);
	/**
	 * 多对一，查询我的未处理任务
	 * @author 李伟
	 * @time 2017年11月27日上午11:06:09
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwClEntity> findClByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	/**
	 * 查询当前排序层级的所有角色集合
	 * @author 李伟
	 * @time 2017年11月28日上午8:43:57
	 * @param i
	 * @return
	 */
	List<SysRole> findSysRoleList(int i);
	/**
	 * 以角色id查询用户集合
	 * @author 李伟
	 * @time 2017年11月28日上午9:02:19
	 * @param roleCode
	 * @return
	 */
	List<SysAccCount> findSysAccByRoleCode(String roleCode);
	/**
	 * 查询当前要处理的记录
	 * @author 李伟
	 * @time 2017年11月28日下午5:19:01
	 * @param rwglId
	 * @return
	 */
	TaskDealEntity findClByRwglId(String rwglId);

	/**
	 * @param id
	 * @return
	 */
	RwClEntity findRwclById(String id);
	/**
	 * 查询单个任务的所有处理记录
	 * @author 李伟
	 * @time 2017年11月29日下午2:03:20
	 * @param rwglId
	 * @param taskDealEntity 
	 * @param isWgy 
	 * @return
	 */
	List<RwClEntity> findDealList(String rwglId,String accCode, RwClEntity taskDealEntity, String isWgy);

	/**
	 * 根据任务ID查询所有的处理任务
	 * @param taskId
	 * @return
	 */
	List<RwClEntity> findRwcl(String taskId);

	/**
	 * 查询当前登陆人的待处理任务数量
	 * @param addMap
	 * @return
	 */
	int findNoDealRwCount(Map<String, String> addMap);

	/**
	 * 删除当前人所转发的记录
	 * @param string
	 * @param fbrId
	 * @return
	 */
	List<RwClEntity> findRcForDel(String string, String fbrId);
}
