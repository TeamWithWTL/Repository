package com.jcwx.dao.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.utils.Pagenate;

public interface ZxzmDao extends BaseDao{
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
	 * 时间倒叙查询在线招募集合
	 * @return
	 */
	List<ZxzmEntity> getContent();

}
