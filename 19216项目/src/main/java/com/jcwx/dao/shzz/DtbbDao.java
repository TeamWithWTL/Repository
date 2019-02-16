package com.jcwx.dao.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
public interface DtbbDao extends BaseDao{
	
	Pagenate<DtbbEntity> getDtbbContent(Integer pageNumber, int pageSize, Map<String, String> map);

	/**
	 * 时间倒叙查询动态播报信息集合
	 * @return
	 */
	 List<DtbbEntity> getDtbbContentList();
	 
	 /**
	  * 根据动态播报id查询留言
	  * @param id
	  * @return
	  */
	 public List<DtbbYjEntity> findByDtId(String id);
	 
	 /**
	  * 留言分页查询
	  * @param pageNumber
	  * @param pageSize
	  * @param map
	  * @return
	  */
	 public Pagenate<DtbbYjEntity> getByDtId(Integer pageNumber, int pageSize, Map<String, String> map);
	 /**
	  * pc 留言分页查询
	  * @author 李伟
	  * @time 2017年11月24日下午2:24:19
	  * @param pageNumber
	  * @param pagesize
	  * @param map
	  * @return
	  */
	Pagenate<DtbbYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map);
}
