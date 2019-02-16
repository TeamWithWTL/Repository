package com.jcwx.service.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.utils.Pagenate;

public interface DzywService {

	Pagenate<DzywEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);

	DzywEntity findById(String id);
	
	void saveOrUpdate(DzywEntity dzywEntity);

	void save(DzywEntity dzywEntity);


	void saves( String title, SysAccCount acc, String id, String content, String fName);

	void update( String title, SysAccCount acc, String id, String content, String fName);

	void del(String ids);

	void audit(String id, String status, SysAccCount acc);
	/**
	 * 热点设置
	 * @author 李伟
	 * @time 2017年10月29日上午12:43:46
	 * @param id
	 * @param hot
	 */
	void hot(String id, String hot);

	/**
	 * 时间倒叙查询廉政要闻集合
	 * @return
	 */
	List<DzywEntity> getDzywContentList();
}
