package com.jcwx.service.ws;

import com.jcwx.entity.xtbg.RdxwArrtsEntity;

/**
 * 
 * @author wangjinxiao
 *
 */
public interface WSService {
	
	/**
	 * 保存、修改热点新闻附件
	 * @param rdxwArrtsEntity
	 * @return
	 */
	public boolean saveOrUpdateRdxwArrt(RdxwArrtsEntity rdxwArrtsEntity);
}
