package com.jcwx.service.shfw;

import java.util.Map;

import com.jcwx.entity.shfw.ShfwSqfwEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-社区服务 Service 
 * @author zhangkai
 *
 */
public interface SqfwService {
	
	/**
	 * 分页查询社区服务
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwSqfwEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据ID获取社区服务详情
	 * @param id
	 * @return
	 */
	ShfwSqfwEntity getById(String id);
	
	/**
	 * 查询是否有热点社区服务
	 * @param isHot 是否热点
	 * @return
	 */
	ShfwSqfwEntity getByIsHot(String isHot);
	
	/**
	 * 添加社区服务
	 * @param sqfw
	 * @param fName
	 * @param tjStatus
	 */
	void save(ShfwSqfwEntity sqfw, String fName, String tjStatus);
	
	/**
	 * 修改社区服务
	 * @param sqfw
	 * @param id
	 * @param fName
	 * @param tjStatus
	 */
	void update(ShfwSqfwEntity sqfw, String id, String fName, String tjStatus);
	
	/**
	 * 根据ID删除社区服务
	 * @param ids
	 */
	void del(String ids);
	
	/**
	 * 根据附件ID删除对应社区服务附件
	 * @param id
	 */
	void delFj(String id);
	
	/**
	 * 审核社区服务
	 * @param ids
	 */
	void shStatus(String ids);
	
	/**
	 * 审核社区服务
	 * @param sqfw
	 */
	void shSqfwStatus(ShfwSqfwEntity sqfw);
	
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
	 * @param flag
	 */
	void updateisHot(String id, String flag);
	
}
