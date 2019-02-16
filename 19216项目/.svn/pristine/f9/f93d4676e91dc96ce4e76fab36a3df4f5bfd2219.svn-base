package com.jcwx.service.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.utils.Pagenate;

public interface ExposureService {

	Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	ExposureEntity findById(String id);

	void saveOrUpdate(ExposureEntity exposureEntity);

	void update(String title, SysAccCount acc, String id, String content, String fName);

	void saves(String title, SysAccCount acc, String id, String content, String fName);

	void del(String ids);

	void audit(String id, String status, SysAccCount acc);
	
	/**
	 * 时间倒叙查询曝光台集合
	 * @return
	 */
	List<ExposureEntity> getBgtContentList();

}
