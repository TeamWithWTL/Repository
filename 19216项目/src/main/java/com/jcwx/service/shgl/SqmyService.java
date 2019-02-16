package com.jcwx.service.shgl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 社会管理--社情民意 Service
 * @author xushu
 *
 */
public interface SqmyService {
   

	/**
	 * 根据id 查询主体
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public <T> T findById(Class<T> type, Serializable id);
	/**
	 * 保存
	 * 
	 * @param id
	 */
	public void save(Object entity);
	/**
	 * 保存
	 * 
	 * @param id
	 */
	public void saveorupdate(Object entity);
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
	 * 根据id查询社情民意
	 * @param id
	 * @return
	 */
	ShglSqmyEntity getById(String id);
   
	/**
	 * 保存
	 * @param status     下发状态
	 * @param sqid       社区id
	 * @param accCode    发布人id
	 * @param name       发布人姓名
	 * @param title      标题
	 * @param content    内容
	 * @param dc_num     调查频率(调查的户数)
	 * @param start_date 开始时间
	 * @param end_date   结束时间
	 * @param fName 
	 */
	void save(String status, String sqid, String accCode, String name,
			String title, String content, int dc_num, String start_date,
			String end_date, String fName);
    
	/**
	 * 修改
	 * @param id
	 * @param status     下发状态
	 * @param sqid       社区id
	 * @param accCode    发布人id
	 * @param name       发布人姓名
	 * @param title      标题
	 * @param content    内容
	 * @param dc_num     调查频率(调查的户数)
	 * @param start_date 开始时间
	 * @param end_date   结束时间
	 * @param fName 
	 */
	void update(String id, String status, String sqid, String accCode,
			String name, String title, String content, int dc_num,
			String start_date, String end_date, String fName);
    
	/**
	 * 删除
	 * @param ids
	 */
	void del(String ids);
    /**
     * 查询社区信息
     * @param map   
     * @return
     */
	Pagenate<SysAccCountLazy> findBysqid(Integer pageNumber, int pagesize,
			Map<String, String> map);
    
	/**
	 * 是否结束
	 * @param id
	 * @param is_over2 
	 */
	void Xg(String id, String is_over);
    /**
     * 查询当前社情民意，网格员调查的住户信息
     * @param id     社情民意id 
     * @param wgyid  网格员id
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

    //保存住户信息
	void saveZh(ShglSqmyZhEntity zh,  String accCode, String name);

	/**
	 * 查询调查住户
	 * @param id
	 * @return
	 */
	ShglSqmyZhEntity findById(String id);

	/**
	 * 通过社区id查询网格员帐号信息
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<SysAccCountLazy> findWgyBysqid(Integer pageNumber, int pagesize, Map<String, String> map);
 
    /**
     * 删除社情民意附件
     * @param fjId
     */
    void delFj(String fjId);

    
    /** add by jiangkai  查询此次社情民意参加的网格员列表
     * @param map
     * @return
     */
    List<SysAccCountLazy> findWgyList(Map<String, String> map);
    
    Pagenate<ShglSqmyWgy> findSqmyWgyListByParam(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 查询网格员所有调查列表
	 * @param map
	 * @return
	 */
	List<ShglSqmyDc> findAllDc(Map<String, String> map);
	
	/**
	 * 查询网格员所有调查列表
	 * @param map
	 * @return
	 */
	List<ShglSqmyWgy> findSqmyWgyList(Map<String, String> map);
}
