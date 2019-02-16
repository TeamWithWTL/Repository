package com.jcwx.service.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会组织--在线招募 Service
 * @author xushu
 *
 */
public interface ZxzmService {
    
	/**
	 * 分页查询在线招募信息
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ZxzmEntity> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> map);
   
	/**
	 * 根据ID查询在线招募信息
	 * @param id
	 * @return
	 */
	ZxzmEntity getById(String id);
    /**
     * 保存在线招募信息
     * @param zxzm
     * @param fName
     * @param accCode 
     * @param name 
     */
	void save(ZxzmEntity zxzm, String fName, String accCode, String name);
    /**
     * 修改在线招募信息
     * @param zxzm
     * @param id
     * @param fName
     */
	void update(ZxzmEntity zxzm, String id, String fName);
    /**
     * 删除附件
     * @param fjId
     */
	void delFj(String fjId);
    /**
     * 删除在线招募
     * @param ids
     */
	void del(String ids);
   /**
    * 保存审核结果
    * @param id
    * @param sh_status
    * @param accCode
    * @param name
    */
	void saveSh(String id, String sh_status, String accCode, String name);
	
	/**
	 * 时间倒叙查询在线招募集合
	 * @return
	 */
	List<ZxzmEntity> getContent();

}
