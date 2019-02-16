package com.jcwx.dao.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.utils.Pagenate;

/**
 * 政府机构管理
 * @author wangjinxiao
 *
 */
public interface ZfjgDao extends BaseDao{
	
	/**
	 * 分页查询数据
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<ShglGovernmentEntity> findByPage1(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 根据条件查询政府机构list
	 * @param paramMap
	 * @return
	 */
	public List<ShglGovernmentEntity> findAllGover(Map<String, String> paramMap);

	/**
	 * 根据政府机构名称查询是否存在
	 * @param addMap
	 * @return
	 */
	public ShglGovernmentEntity findByName(Map<String, String> addMap);

	/**
	 * 根据政府机构名称模糊查询
	 * @param map
	 * @return
	 */
	public List<ShglGovernmentEntity> findGoverByName(Map<String, String> map);
}
