package com.jcwx.dao.xtbg;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.utils.Pagenate;

public interface TxlglDao extends BaseDao{
/**
 * 通讯录管理首页
 * @param pageNumber
 * @param pagesize
 * @param map
 * @return
 */
	public Pagenate<SysAccCount> getTxlglContent(Integer pageNumber, int pagesize, Map<String, String> map);

}
