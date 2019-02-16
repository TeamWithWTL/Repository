package com.jcwx.dao.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkAttrEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 */
public interface HdglDao extends BaseDao{
	/**
	 * 分页查询组织活动
	 * @param pageNumber
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<HdglEntity> getHdglContent(Integer pageNumber, int pageSize, Map<String, String> map);

	/**
	 * @param hot
	 * @return
	*/
	List<HdglEntity> findByHot(String hot);
	 
	/**
	  * 留言分页查询初始页
	  * @author 李伟
	  * @time 2017年11月15日下午1:57:29
	  * @param pageNumber
	  * @param pagesize
	  * @param map
	  * @return
	*/
	Pagenate<HdglYjEntity> findHdyjFistPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 查询留言
	 * @author 李伟
	 * @time 2017年11月15日下午3:02:33
	 * @param id
	 * @return
	 */
	List<HdglYjEntity> findbyHdId(String id);
	
	/**
	 *  以法人id，查询组织信息列表
	 * @author 李伟
	 * @time 2017年11月19日上午9:52:07
	 * @param accCode
	 * @return
	*/
	List<ZzxxEntity> findZzxmByAccCode(String accCode);
	
	/**
	 * 活动管理反馈附件保存
	 * @author 李伟
	 * @time 2017年11月19日下午1:50:47
	 * @param hdglFkAttrEntity
	*/
	void seveOrUpdateHdglArrt(HdglFkAttrEntity hdglFkAttrEntity);
	
	/**
	 * 以活动id,查询反馈集合
	 * @author 李伟
	 * @time 2017年11月19日下午4:08:25
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
	 * 查询审核通过留言分页
	 * @author 李伟
	 * @time 2017年11月24日上午9:33:12
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<HdglYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
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
	 * findFkByPage
	 * @author 李伟
	 * @time 2017年11月27日上午8:18:44
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
	 * 根据活动id,获取已反馈集合
	 * @param id
	 * @return
	*/
	List<HdglFkEntity> findYfkByHdId(String id);
	
}
