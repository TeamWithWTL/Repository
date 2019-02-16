package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwclAttrEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.TaskDealEntity;
import com.jcwx.utils.Pagenate;

public interface RwclService {

	void save(RwClEntity rwcl);

	RwClEntity findId(String id);

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
	 * @time 2017年11月27日上午11:02:49
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<RwClEntity> findClByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	/**
	 * Id查询任务管理
	 * @author 李伟
	 * @time 2017年11月28日上午8:20:56
	 * @param rwglId
	 * @return
	 */
	TaskDealEntity findById(String rwglId);
	/**
	 * id查询系统角色
	 * @author 李伟
	 * @time 2017年11月28日上午8:35:47
	 * @param role_id
	 * @return
	 */
	SysRole findSysRoleById(String role_id);
	/**
	 * 查询当前排序层级的所有角色集合
	 * @author 李伟
	 * @time 2017年11月28日上午8:42:17
	 * @param i
	 * @return
	 */
	List<SysRole> findSysRoleList(int i);
	/**
	 * 以角色id查询用户集合
	 * @author 李伟
	 * @time 2017年11月28日上午9:01:20
	 * @param roleCode
	 * @return
	 */
	List<SysAccCount> findSysAccByRoleCode(String roleCode);
	/**
	 * 下发
	 * @author 李伟
	 * @time 2017年11月28日下午5:08:27
	 * @param content
	 * @param personId
	 * @param curRoleId
	 * @param accCode
	 * @param rwglId 
	 */
	void issue(String content, String personId, String curRoleId, String accCode, String rwglId);
	/**
	 * id查询用户信息
	 * @author 李伟
	 * @time 2017年11月29日下午2:00:34
	 * @param pre_emp
	 * @return
	 */
	SysAccCount findSysAccById(String pre_emp);
	/**
	 * 保存反馈
	 * @author 李伟
	 * @time 2017年11月29日上午8:42:54
	 * @param id
	 * @param content
	 * @param fName 
	 */
	void goFk(String id, String content, String fName);
	
	/**
	 * 查询所反馈的任务
	 * @param id
	 * @return
	 */
	RwClEntity findRwclById(String id);
	/**
	 * 查询单个任务的所有处理记录
	 * @author 李伟
	 * @time 2017年11月29日下午1:59:38
	 * @param rwglId
	 * @param accCode 
	 * @param taskDealEntity 
	 * @param isWgy 
	 * @return
	 */
	List<RwClEntity> findDealList(String rwglId, String accCode, RwClEntity taskDealEntity, String isWgy);

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
	 * 保存任务处理附件
	 * @param clEntity
	 * @param fName
	 */
	void saveDealAttrs(RwClEntity clEntity, String fName);

	/**
	 * 保存任务处理表
	 * @param taskDealEntity
	 */
	void upadateRc(RwClEntity taskDealEntity);

	/**
	 * 跟新保存任务反馈事件--app
	 * @param docAttr
	 */
	void saveOrUpdateAttrs(RwclAttrEntity docAttr);

}
