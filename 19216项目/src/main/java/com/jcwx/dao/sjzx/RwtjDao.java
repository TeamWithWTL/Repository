package com.jcwx.dao.sjzx;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglTaskDealEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author gaoshuai
 *	2017年10月26日
 */
public interface RwtjDao {

	/**
	 * 查询所有的小区
	 * @return
	 */
	List<ShglCommunityEntity> sqAll();

	/**
	 * 查询所有的服务站
	 * @return
	 */
	List<ShglServiceStationEntity> fwzAll();

	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pagesize
	 * @param paramMap
	 * @return
	 */
	Pagenate<ShglTaskDealEntity> findRwtjContent(Integer pageNumber, int pagesize, Map<String, String> paramMap);

	/**
	 * 查询所有的网格
	 * @return
	 */
	List<ShglGridEntity> wgAll();
		
}
