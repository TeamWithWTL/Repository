package com.jcwx.service.sjzx.tsrl;

import java.util.Map;

import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.utils.Pagenate;

/**
 * 特殊人口统计
 * @author Administrator
 *
 */
public interface TsrkService {
	//优抚对象
	public int findCnt_yfdx(String commId, String ssId);

	//低保人员
	public int findCnt_dbry(String commId, String ssId);

	//离退干部
	public int findCnt_ltgb(String commId, String ssId);

	public Pagenate<ShglInmateEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> params);
	//查询社区房屋出租数量
	public int findCountFwcz(Map<String, String> addMap);

	//服务站房屋出租数量
	public int findSSfwcz(String sqId);
	
}
