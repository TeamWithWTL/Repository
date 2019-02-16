package com.jcwx.service.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkAttrEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 */
public interface HdglService {

	/**
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	Pagenate<HdglEntity> getHdglContent(Integer pageNumber, int pagesize, Map<String, String> Map);

	/**
	 * @param id
	 * @return
	 */
	HdglEntity findById(String id);

	/**
	 * @param id
	 * @return
	 */
	HdglEntity getById(String id);

	/**
	 * @param hdgl
	 * @param fName
	 * @param zzIds
	 */
	void save(HdglEntity hdgl, String fName, String zzIds);

	/**
	 * @param hdgl
	 * @param id
	 * @param fName
	 * @param zzIds
	 */
	void update(HdglEntity hdgl, String id, String fName, String zzIds);

	/**
	 * @param hdgl
	 */
	void shHdStatus(HdglEntity hdgl);

	/**
	 * @param id
	 */
	void updateshStatus(String id);

	/**
	 * @param ids
	 */
	void del(String ids);

	/**
	 * @param id
	 */
	void delFj(String id);

	/**
	 * @param id
	 * @param flag
	 */
	void updateisHot(String id, String flag);

	/**
	 * @param id
	 * @param status
	 * @param sysAccCount
	 */
	void doAuditing(String id, String status, SysAccCount sysAccCount);

	/**
	 * @param id
	 * @param hot
	 */
	void hot(String id, String hot);
	
	/**
	 * 保存留言
	 * @author 李伟
	 * @time 2017年11月15日上午10:09:17
	 * @param id
	 * @param content
	 * @param acc 
	 */
	void saveLy(String id, String content, SysAccCount acc);
	
	/**
	 * 审核通过留言分页查询初始页
	 * @author 李伟
	 * @time 2017年11月15日下午1:54:28
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<HdglYjEntity> findHdyjFistPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 查询待审核留言
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<HdglYjEntity> findLyDshByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 查询审核不通过留言
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<HdglYjEntity> findLyBtgByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 查询留言
	 * @author 李伟
	 * @time 2017年11月15日下午3:01:04
	 * @param id
	 * @return
	 */
	List<HdglYjEntity> findByHdId(String id);
	
	/**
	 * 以法人id，查询组织信息列表
	 * @author 李伟
	 * @time 2017年11月19日上午9:50:30
	 * @param accCode
	 * @return
	 */
	List<ZzxxEntity> findZzxmByAccCode(String accCode);
	
	/**
	 * 活动管理反馈保存
	 * @author 李伟
	 * @time 2017年11月19日下午1:38:35
	 * @param hdglFkEntity
	 * @param id
	 */
	void saveFk(HdglFkEntity hdglFkEntity, String id);
	
	/**
	 * 活动反馈附件保存
	 * @author 李伟
	 * @time 2017年11月19日下午1:47:33
	 * @param hdglFkAttrEntity
	 */
	void saveOrUpdateHdglArrt(HdglFkAttrEntity hdglFkAttrEntity);
	
	/**
	 * 以活动id,查询反馈集合
	 * @author 李伟
	 * @time 2017年11月19日下午2:19:51
	 * @param id
	 * @return
	 */
	List<HdglFkEntity> findFkByHdId(String id);
	
	/**
	 * 根据活动ID和组织ID获取对应的反馈
	 * @param hdId
	 * @param zzId
	 * @return
	 */
	HdglFkEntity getFkByHdIdZzId(String hdId, String zzId);
	
	/**
	 * id查询组织
	 * @author 李伟
	 * @time 2017年11月19日下午4:05:16
	 * @param zzId
	 * @return 
	 */
	ZzxxEntity findZzById(String zzId);
	
	/**
	 * 查询留言分页
	 * @author 李伟
	 * @time 2017年11月24日上午9:31:45
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<HdglYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 删除留言
	 * @author 李伟
	 * @time 2017年11月24日下午1:45:54
	 * @param id
	 */
	void delLy(String id);
	
	/**
	 * 审核反馈 通过
	 * @param id
	 */
	void shFktg(String id);
	
	/**
	 * 审核反馈 不通过
	 * @param id
	 */
	void shFkbtg(String id);
	
	/**
	 * 审核留言 通过
	 * @author 李伟
	 * @time 2017年11月24日下午1:54:11
	 * @param id
	 */
	void successLy(String id);
	
	/**
	 * 审核留言 不通过
	 * @param id
	 */
	void shLybtg(String id);
	
	/**
	 * 查询审核通过反馈分页
	 * @author 李伟
	 * @time 2017年11月27日上午8:16:29
	 * @param pageNumber
	 * @param pagesize
	 * @param fkMap
	 * @return
	 */
	Pagenate<HdglFkEntity> findFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap);
	
	/**
	 * 查询待审核反馈分页
	 * @param pageNumber
	 * @param pagesize
	 * @param fkMap
	 * @return
	 */
	Pagenate<HdglFkEntity> findDshFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap);
	
	/**
	 * 查询审核不通过反馈分页
	 * @param pageNumber
	 * @param pagesize
	 * @param fkMap
	 * @return
	 */
	Pagenate<HdglFkEntity> findBtgFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap);
	
	/**
	 * 根据组织活动ID和留言人ID获取留言记录
	 * @param sqhdId
	 * @param userId
	 * @return
	 */
	HdglYjEntity getHdYjByhdId(String hdglId, String userId);
	
	/**
	 * 获取所有的组织活动
	 * @return
	 */
	List<HdglEntity> getZzhdList();
	
	/**
	 * 根据ID修改活动状态
	 * @param id
	 * @param status
	 */
	void updateHdStatus(String id, String status);
	
	/**
	 * 根据反馈ID获取反馈详情
	 * @param id
	 * @return
	 */
	HdglFkEntity findFkByFkId(String id);
	
	/**
	 * 根据活动id,获取已反馈集合
	 * @param id
	 * @return
	*/
	List<HdglFkEntity> findYfkByHdId(String id);
	
}
