package com.jcwx.dao.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.utils.Pagenate;

public interface ZzxxDao extends BaseDao {
	/**
	 * 分页查询组织信息
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
