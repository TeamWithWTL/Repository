package com.jcwx.dao.sjzx.tsrk;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.utils.Pagenate;


/**
 * 特殊人口统计
 * @author LiuMengMeng
 *
 */
public interface TsrkDao extends BaseDao{

	//优抚对象
//	public  int findCnt(String commId, String ssId, String type);

	//特殊人口总数
	public int findRkCnt(String commId, String ssId,String category);

	public Pagenate<ShglInmateEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> params);

	//社区房屋出租数量
	public int findCountFwcz(Map<String, String> comMap);

	//服务站房屋出租数量
	public int findSSfwcz(String sqId);


}
