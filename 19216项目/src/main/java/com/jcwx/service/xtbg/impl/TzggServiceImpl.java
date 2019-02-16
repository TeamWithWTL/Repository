/**
 * 
 */
package com.jcwx.service.xtbg.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtbg.TzggDao;
import com.jcwx.entity.shzz.DtbbAttrEntity;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.xtbg.TzggArrtEntity;
import com.jcwx.entity.xtbg.TzggEntity;
import com.jcwx.service.xtbg.TzggService;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author m
 *
 */

@Service("tzggService")
public class TzggServiceImpl implements TzggService {
	
	
	@Autowired
	private TzggDao tzggDao;


	@Override
	public Pagenate<TzggEntity> getTzggContent(Integer pageNumber, int pageSize, Map<String, String> map) {
		return tzggDao.getTzggContent(pageNumber, pageSize, map);
	}


	@Override
	public TzggEntity findById(String id) {
		return tzggDao.findById(TzggEntity.class, id);
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtbg.TzggService#save(com.jcwx.entity.xtbg.TzggEntity, java.lang.String)
	 */
	@Override
	public void save(TzggEntity tzgg, String fName) {
		tzgg.setTitle(tzgg.getTitle());
		tzgg.setContent(tzgg.getContent());
		tzgg.setCreateDate(new Date());
		tzgg.setStatus("0");
		tzggDao.save(tzgg);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFilename = jsStr.getString("newName");
					String oldFilename = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					TzggArrtEntity tzAttr = new TzggArrtEntity();
//					if(!fileType.equals("img") && !fileType.equals("pdf")){
						//String transName = newFilename.replace(StringUtil.getFileExt(newFilename), "pdf");
						//int result = OpenOfficeUtils.office2PDF(newFilename, transName);
						//if(result == 0){
							//更新PDF文件名到数据库
//							tzAttr.setNew_filename(transName);
//						}
//					}else{
						tzAttr.setNew_filename(newFilename);
//					}
					tzAttr.setRy_id(tzgg.getId());
					tzAttr.setOld_filename(oldFilename);
					tzAttr.setFileType(fileType);
					tzggDao.save(tzAttr);
				}
			}
		}
		
	}

	@Override
	public void update(TzggEntity tzgg, String id, String fName) {
		TzggEntity tzgg1 = findById(id);
		tzgg.setTitle(tzgg.getTitle());
		tzgg.setContent(tzgg.getContent());
		BeanUtils.copyProperties(tzgg, tzgg1, new String[]{"createUserId","createUserName","createDate","attrList"});
		tzggDao.saveOrUpdate(tzgg1);
		System.out.println("AAAAA"+ new String[]{"createUserId","createUserName","createDate","attrList"});
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFilename = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					TzggArrtEntity tzAttr = new TzggArrtEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFilename.replace(StringUtil.getFileExt(newFilename), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFilename, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							tzAttr.setNew_filename(transName);
						}
					}else{
						tzAttr.setNew_filename(newFilename);
					}
					tzAttr.setRy_id(tzgg.getId());
					tzAttr.setOld_filename(oldFileName);
					tzAttr.setFileType(fileType);
					tzggDao.save(tzAttr);
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtbg.TzggService#getById(java.lang.String)
	 */
	@Override
	public TzggEntity getById(String id) {
		return tzggDao.findById(TzggEntity.class, id);
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtbg.TzggService#delFj(java.lang.String)
	 */
	@Override
	public void delFj(String id) {
		
		tzggDao.deleteById(TzggEntity.class, id);
		
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtbg.TzggService#del(java.lang.String)
	 */
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			tzggDao.deleteById(TzggEntity.class, id[i]);
		}
	}
	
	

}
