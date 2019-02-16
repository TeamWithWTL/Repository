package com.jcwx.service.sjzx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.sjzx.RwtjDao;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglTaskDealEntity;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.utils.Pagenate;

@Service("RwtjService")
public class RwtjServiceImpl implements RwtjService {
	
	@Autowired
	private RwtjDao rwtjDao;

	@Override
	public List<ShglCommunityEntity> sqAll() {
		return rwtjDao.sqAll();
	}

	@Override
	public List<ShglServiceStationEntity> fwzAll() {
		return rwtjDao.fwzAll();
	}

	@Override
	public Pagenate<ShglTaskDealEntity> findRwtjContent(Integer pageNumber, int pagesize, Map<String, String> paramMap) {
		return rwtjDao.findRwtjContent(pageNumber,pagesize,paramMap);
	}

	@Override
	public List<ShglGridEntity> wgAll() {
		return rwtjDao.wgAll();
	}

}
