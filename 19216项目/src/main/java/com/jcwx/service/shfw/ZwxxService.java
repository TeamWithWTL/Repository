package com.jcwx.service.shfw;

import java.util.Map;

import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会服务-政务信息 Service
 * @author zhangkai 
 *
 */
public interface ZwxxService {
	
	/**
	 * 分页查询政务信息
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
	Pagenate<ShfwZwxxEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap);
	
	/**
	 * 根据ID获取政务信息详情
	 * @param id
	 * @return
	 */
	ShfwZwxxEntity getById(String id);
	
	/**
	 * 添加政务信息
	 * @param zwxx
	 * @param fName
	 * @param tjStatus
	 */
	void save(ShfwZwxxEntity zwxx, String fName, String tjStatus);
	
	/**
	 * 修改政务信息
	 * @param zwxx
	 * @param id
	 * @param fName
	 * @param tjStatus
	 */
	void update(ShfwZwxxEntity zwxx, String id, String fName, String tjStatus);
	
	/**
	 * 根据ID删除政务信息
	 * @param ids
	 */
	void del(String ids);
	
	/**
	 * 根据附件ID删除对应政务信息附件
	 * @param id
	 */
	void delFj(String id);
	
	/**
	 * 审核政务信息
	 * @param ids
	 */
	void shStatus(String ids);
	
	/**
	 * 审核政务信息
	 * @param zwxx
	 */
	void shZwStatus(ShfwZwxxEntity zwxx);
	
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
	
}
