package com.jcwx.service.shfw;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区活动 Service
 * @author zhangkai 
 *
 */
public interface SqhdService {
	
	/**
	 * 分页查询社区活动
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqhdEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据ID获取社区活动详情
	 * @param id
	 * @return
	 */
	ShfwSqhdEntity getById(String id);
	
	/**
	 * 查询是否有热点社区活动
	 * @param isHot 是否热点
	 * @return
	 */
	ShfwSqhdEntity getByIsHot(String isHot);
	
	/**
	 * 添加社区活动
	 * @param sqhd
	 * @param fName
	 * @param fbStatus
	 */
	void save(ShfwSqhdEntity sqhd, String fName, String fbStatus);
	
	/**
	 * 修改社区活动
	 * @param sqhd
	 * @param id
	 * @param fName
	 * @param fbStatus
	 */
	void update(ShfwSqhdEntity sqhd, String id, String fName, String fbStatus);
	
	/**
	 * 根据ID删除社区活动
	 * @param ids
	 */
	void del(String ids);
	
	/**
	 * 根据附件ID删除对应社区活动附件
	 * @param id
	 */
	void delFj(String id);
	
	/**
	 * 审核社区活动
	 * @param ids
	 */
	void shStatus(String ids);
	
	/**
	 * 审核社区活动
	 * @param sqhd
	 */
	void shSqhdStatus(ShfwSqhdEntity sqhd);
	
	/**
	 * 更改审核状态
	 * @param id
	 */
	void updateshStatus(String id);
	
	/**
	 * 设置审核状态
	 * @param id
	 * @param flag
	 */
	void shZt(String id, String flag);
	
	/**
	 * 更改热点状态
	 * @param id
	 */
	void updateisHot(String id, String flag);

	/**
	 * 根据id查找留言审核数据
	 * @param id
	 * @return
	 */
	ShfwSqhdYjEntity findId(String id);

	//更新留言审核
	void updateLysh(String id);
	
	/**
	 * 获取所有的社区活动
	 * @return
	 */
	List<ShfwSqhdEntity> getHdList();
	
	/**
	 * 根据ID修改活动状态
	 * @param id
	 * @param status
	 */
	void updateHdStatus(String id, String status);
	
	/**
	 * 获取所有的社区
	 * @return
	 */
	List<ShglCommunityEntity> getCommList();
	
}
