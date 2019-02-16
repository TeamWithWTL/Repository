package com.jcwx.service.xtbg.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtbg.TxlglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.xtbg.TxlglService;
import com.jcwx.utils.Pagenate;

@Service("txlglService")
public class TxlglServiceImpl implements TxlglService{

	@Autowired
	private TxlglDao txlglDao;	
/**
 * 通讯录管理首页
 */
	@Override
	public Pagenate<SysAccCount> getTxlglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		return txlglDao.getTxlglContent(pageNumber,pagesize,map);
	}

}
