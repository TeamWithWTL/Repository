package com.jcwx.service.xtbg.impl;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.EventDao;
import com.jcwx.dao.xtbg.DocumentDao;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.shfw.ShfwSqfwAttrsEntity;
import com.jcwx.entity.xtbg.Document;
import com.jcwx.entity.xtbg.DocumentAttrs;
import com.jcwx.entity.xtbg.DocumentDeal;
import com.jcwx.service.xtbg.DocumentService;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

/**
 * 公文处理
 * @author jiangkia
 *
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService{
	@Autowired
	private DocumentDao documentDao;
	
	@Override
	public Pagenate<Document> findDocumentByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return documentDao.findDocumentByPage(pageNumber, pagesize, map);
	}

	@Override
	public Pagenate<DocumentDeal> findDocumentDealByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return documentDao.findDocumentDealByPage(pageNumber, pagesize, map);
	}

	@Override
	public void saveDoc(Document doc, String fName, String jsrIds) {
		//保存公文
		documentDao.save(doc);
		//保存公文附件 begin
		if(fName !=null && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					DocumentAttrs docAttr = new DocumentAttrs();
//					if(!fileType.equals("img") && !fileType.equals("pdf")){
//						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
//						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
//						if(result == 0){
//							//更新PDF文件名到数据库
//							docAttr.setNewFilename(transName);
//						}else{
//							docAttr.setNewFilename(newFileName);
//						}
//					}else{
//						docAttr.setNewFilename(newFileName);
//					}
					docAttr.setNewFilename(newFileName);
					docAttr.setGwclId(doc.getId());
					docAttr.setOldFilename(oldFileName);
					docAttr.setFileType(fileType);
					documentDao.save(docAttr);
				}
			}
		}
		//保存公文附件 end
		
		//保存公文处理人 begin
		if(jsrIds !=null && !"".equals(jsrIds)){
			String[] clrArray = jsrIds.split(";");
			for(String clrId : clrArray){
				if("".equals(clrId)){
					continue;
				}
				DocumentDeal deal  = new DocumentDeal();
				deal.setClrId(clrId);
				deal.setDocument(doc);
				deal.setZfrId(doc.getCreateUserId());
				deal.setZfrName(doc.getCreateUserName());
				deal.setStatus("0");//0 未处理  1 已处理
				deal.setCreateDate(new Date());
				documentDao.save(deal);
				}
			}
		//保存公文处理人 end
	}

	@Override
	public Document findDocById(String id) {
		return documentDao.findById(Document.class, id);
	}

	@Override
	public DocumentDeal findDealById(String id) {
		return documentDao.findById(DocumentDeal.class, id);
	}

	@Override
	public void updateDoc(Document doc) {
		documentDao.saveOrUpdate(doc);
		
	}
	@Override
	public void updateDocDeal(DocumentDeal deal) {
		documentDao.saveOrUpdate(deal);
		
	}

	@Override
	public void deleteDoc(String ids) {
		String[] split = ids.split(";");
		for (String id : split) {
			documentDao.deleteById(Document.class, id);
		}
	
		
	}
}
