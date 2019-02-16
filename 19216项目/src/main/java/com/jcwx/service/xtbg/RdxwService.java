package com.jcwx.service.xtbg;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.utils.Pagenate;

/**
 * 协同办公--热点新闻
 * @author liuMengMeng
 *
 */

public interface RdxwService {

	/**
	 * 获取热点新闻内容
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	public	Pagenate<RdxwEntity> getRdxwContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);

	/**
	 * 跳转至新增页面
	 * @param id
	 * @return
	 */
	
	public RdxwEntity getById(String id);

	/**
	 * 保存
	 * @param rdxw
	 * @param fName
	 */
	public void save(RdxwEntity rdxw, String fName);

	/**
	 * 更新
	 * @param rdxw
	 * @param id
	 * @param fName
	 */
	public void update(RdxwEntity rdxw, String id, String fName);

	//审核状态
	public void shZxStatus(RdxwEntity rdxw);

	
	/**
	 * 删除热点新闻信息
	 * @param ids
	 */
	public void del(String ids);

	/**
	 * 更新审核热点新闻状态
	 * @param id
	 */
	public void updateshStatus(String id);

	/**
	 * 删除附件
	 * @param fjId
	 */
	public void delFj(String fjId);

	/**
	 * 时间倒叙查询热点新闻集合
	 * @param addMap 
	 * @return
	 */
	List<RdxwEntity> getRdxwContentList(Map<String, String> addMap);

	/**
	 * 设置热点 
	 * @param id
	 * @param hot
	 */
	public void hot(String id, String hot);

	/**
	 * 审核页面审核状态
	 * @param id
	 * @param flag
	 */
	public void shZt(String id, String flag,SysAccCount acc);

	/**
	 * 设置置顶
	 * @param id
	 * @param goTop
	 */
	public void goTop(String id, String goTop);


}
