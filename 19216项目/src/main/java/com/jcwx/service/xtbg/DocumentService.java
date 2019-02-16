package com.jcwx.service.xtbg;

import java.util.Map;

import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.utils.Pagenate;

/**
 * 公文处理service
 * @author jiangkia
 *
 */
public interface DocumentService {

	
	/**
	 * 根据ID查公文
	 * @param id
	 * @return
	 */
	Document findDocById(String id);
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

	/**
	 * 根据ID查询公文处理ID
	 * @param id
	 * @return
	 */
	DocumentDeal findDealById(String id);
	/**
	 * 保存公文
	 * @param doc
	 * @param fName 附件
	 * @param jsrIds 接收人IDs
	 */
	void saveDoc(Document doc, String fName, String jsrIds);
	
	/**
	 * 更新
	 * @param doc
	 * @param fName 附件
	 * @param jsrIds 接收人IDs
	 */
	void updateDoc(Document doc);
	/**
	 * 更新
	 * @param doc
	 * @param fName 附件
	 * @param jsrIds 接收人IDs
	 */
	void updateDocDeal(DocumentDeal deal);
	
	void deleteDoc(String ids);
	
}
