package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.utils.Pagenate;

public interface SqmyDao extends BaseDao{
  
	/**
	 * 分页查询社情民意
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglSqmyEntity> findSqmyByPage(Integer pageNumber, int pagesize,
			Map<String, String> map);

	Pagenate<ShglSqmyWgy> findSqmyWgyByPage(Integer pageNumber, int pagesize,
			Map<String, String> map);
	/**
	 * 查询社区信息
	 * @param pagesize 
	 * @param pageNumber 
	 * @param sqid
	 * @return
	 */
	Pagenate<SysAccCountLazy> finbysqid(Integer pageNumber, int pagesize,
			Map<String, String> map);
    
	/**
	 * 查询当前社情民意，网格员调查的住户信息
	 * @param id
	 * @param wgyid
	 * @return
	 */
	List<ShglSqmyZhEntity> getwgylist(String id, String wgyid);
    
	/**
	 * 查看网格员调查住户列表
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglSqmyZhEntity> findBywgyzh(Integer pageNumber, int pagesize,
			Map<String, String> map);

	/**
	 * 社情民意--开始时间降序查询
	 * @return
	 */
	List<ShglSqmyEntity> getSqmyContent();

	/**
	 * 社情民意--查询所有的网格员
	 * @param sqId
	 * @param oneId
	 * @param sqmyId
	 * @return
	 */
	List<ShglSqmyZhEntity> getwgylist(String sqId, String oneId, String sqmyId);
   /**
    * 获取单元列表
    * @param paramMap
    * @return
    */
	List<ShglInmateEntity> findAllinmate(Map<String, String> paramMap);

	

	
	/**
	 * 通过社区id查询网格员帐号信息
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<SysAccCountLazy> findWgyBysqid(Integer pageNumber, int pagesize,
			Map<String, String> map);

	List<SysAccCountLazy> findWgyList(Map<String, String> map);

	/**
	 * 分页查询社情民意网格员
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglSqmyWgy> findSqmyWgyListByParam(Integer pageNumber,
			int pagesize, Map<String, String> map);

	List<ShglSqmyDc> findAllDc(Map<String, String> map);

	List<ShglSqmyWgy> findSqmyWgyList(Map<String, String> map);


 
}
