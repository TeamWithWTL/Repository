package com.jcwx.service.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会组织--社会组织信息 Service
 * @author xushu
 *
 */
public interface ZzxxService{
	/**
	 * 分页查询政务信息
	 * @param pageNumber
	 * @param pageSize
	 * @param paramsMap
	 * @return
	 */
   Pagenate<ZzxxEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);
   
   /**
    * 获取所有组织信息
    * @return
    */
   List<ZzxxEntity> getList();
   
  /**
   * 根据id查询组织信息
   * @param id
   * @return
   */
	ZzxxEntity getById(String id);
	
	/**
	 * 保存组织信息
	 * @param zzxx
	 * @param fName
	 */
   void save(ZzxxEntity zzxx, String fName);
   
   /**
    * 修改组织信息
    * @param zzxx
    * @param id
    * @param fName
    */
   void update(ZzxxEntity zzxx, String id, String fName);
   
   /**
    * 删除组织信息
    * @param ids
    */
	void del(String ids);
	
	/**
	 * 删除附件
	 * @param fjId
	 */
    void delFj(String fjId);
    
	/**
	 * 保存法人信息
	 * @param zzxxEntity
	 */
	void updateZzFr(ZzxxEntity zzxxEntity);
	
	/**
	 * 查询是否存在组织机构
	 * @param frString
	 * @return
	 */
	List<ZzxxEntity> getShzzByZzjg(String frString);
	
	/**
	 * 根据法人ID获取对应的组织
	 * @param frId
	 * @return
	 */
	ZzxxEntity getZzByFrId1(String frId);
	
	/**
	 * 根据法人ID获取对应的组织
	 * @param frId
	 * @return
	 */
	List<ZzxxEntity> getZzByFrId(String frId);
	
}
