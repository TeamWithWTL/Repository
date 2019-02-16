package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglGmanagerEntity;
import com.jcwx.entity.shgl.ShglGovernmentEntity;
import com.jcwx.utils.Pagenate;

/**
 * 政府机构
 * @author wangjinxiao
 *
 */
/**
 * @author Administrator
 *
 */
public interface ZfjgService {
	
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
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ShglGovernmentEntity findById(String id);
	
	/**
	 * 保存
	 * @param s
	 */
	public void save(ShglGovernmentEntity s);
	
	/**
	 * 编辑
	 * @param s
	 */
	public void update(ShglGovernmentEntity s);
	
	/**
	 * 删除
	 * @param ids
	 */
	public void del(String ids);
	
	/**
	 * 保存负责人
	 * @param s
	 */
	public void saveFzr(ShglGmanagerEntity s);
	
	/**
	 * 删除负责人
	 * @param id
	 */
	public void delFzr(String id);

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
