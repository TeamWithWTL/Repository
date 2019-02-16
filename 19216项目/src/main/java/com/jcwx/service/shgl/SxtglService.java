package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.utils.Pagenate;

public interface SxtglService {

	/**
	 * 分页查询数据
	 * 
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<CameraEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public CameraEntity findById(String id);

	/**
	 * 根据本条件查询数据
	 * 
	 * @param map
	 * @return
	 */
	List<CameraEntity> findAllCas(Map<String, String> map);

	/**
	 * 保存
	 * 
	 * @param build
	 */
	public void save(CameraEntity camera);

	/**
	 * 编辑 保存
	 * 
	 * @param buildOld
	 */
	public void update(CameraEntity camera);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);
}
