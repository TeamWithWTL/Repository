package com.jcwx.dao.xtbg;

import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.shgl.Event;
import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.utils.Pagenate;

/**
 * 公文处理接口
 * @author jiangkia
 *
 */
public interface DocumentDao extends BaseDao{

	
	/**
	 * 分页查询document
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<Document> findDocumentByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	
	/**
	 * 分页查询documentDeal
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<DocumentDeal> findDocumentDealByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
}
