package com.jcwx.service.dflz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.AppExposureDao;
import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.service.dflz.AppExposureService;
import com.jcwx.utils.Pagenate;
/**
 * app曝光台service 实现
 * @author 李伟
 * @time 2017年11月8日下午5:43:19
 */
@Service
public class AppExposureServiceImpl implements AppExposureService{
		@Autowired
		private AppExposureDao appExposureDao;
		/**
		 * 查询已审核曝光信息
		 */
		@Override
		public Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map) {
			return appExposureDao.findByPage(pageNumber, pageSize, map);
		}
}
