package com.jcwx.service.sjzx.tsrl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.sjzx.tsrk.TsrkDao;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.service.sjzx.tsrl.TsrkService;
import com.jcwx.utils.Pagenate;


/**
 * 特殊人口统计
 * @author Administrator
 *
 */
@Service("tsrkService")
public class TsrkServiceImpl implements TsrkService{

	@Autowired 
	private TsrkDao tsrkDao;
	/**
	 * 优抚对象
	 */
	@Override
	public int findCnt_yfdx(String commId, String ssId) {
		return tsrkDao.findRkCnt(commId,ssId,"yfdx");
	}

	/**
	 * 低保人员
	 */
	@Override
	public int findCnt_dbry(String commId, String ssId) {
		return tsrkDao.findRkCnt(commId,ssId,"dbry");
	}

	/**
	 * 离退干部
	 */
	@Override
	public int findCnt_ltgb(String commId, String ssId) {
		return tsrkDao.findRkCnt(commId,ssId,"ltgb");
	}

	@Override
	public Pagenate<ShglInmateEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> params) {
		return tsrkDao.findByPage(pageNumber, pagesize, params);
	}

	/**
	 * 某社区房屋出租数量
	 */
	@Override
	public int findCountFwcz(Map<String, String> addMap) {
		return tsrkDao.findCountFwcz(addMap);
	}

	/**
	 * 服务站房屋出租数量
	 */
	@Override
	public int findSSfwcz(String sqId) {
		return tsrkDao.findSSfwcz(sqId);
	}

}
