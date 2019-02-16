package com.jcwx.service.dflz;

import java.util.Map;

import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.utils.Pagenate;

/**
 * 曝光台service 接口
 * @author 李伟
 * @time 2017年11月8日下午5:41:23
 */
public interface AppExposureService {
	/**
	 * 查询已审核的曝光信息
	 * @author 李伟
	 * @time 2017年11月8日下午5:47:40
	 * @param pageNumber
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map);

}
