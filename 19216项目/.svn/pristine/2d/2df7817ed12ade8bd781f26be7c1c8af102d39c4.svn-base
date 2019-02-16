package com.jcwx.service.shzz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shzz.ZxzmDao;
import com.jcwx.entity.shzz.ZxzmAttrsEntity;
import com.jcwx.entity.shzz.ZxzmEntity;
import com.jcwx.service.shzz.ZxzmService;
import com.jcwx.utils.Pagenate;
@Service("ZxzmService")
public class ZxzmServiceImpl implements ZxzmService{

	 @Autowired
	 private ZxzmDao zxzmDao;

	@Override
	public Pagenate<ZxzmEntity> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		return zxzmDao.findByPage(pageNumber, pagesize, map);
	}

	@Override
	public ZxzmEntity getById(String id) {
		return zxzmDao.findById(ZxzmEntity.class, id);
	}

	@Override
	public void save(ZxzmEntity zxzm, String fName, String accCode, String name) {
		zxzm.setUser_id(accCode);
		zxzm.setUser_name(name);
		zxzm.setCreate_time(new Date());
		zxzm.setSh_status("0");
		zxzmDao.save(zxzm);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZxzmAttrsEntity ent = new ZxzmAttrsEntity();
//					if(!fileType.equals("img") && !fileType.equals("pdf")){
//						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
//						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
//						if(result == 0){
//							//更新PDF文件名到数据库
//							ent.setNewFilename(transName);
//						 }
//					 }else{
//						ent.setNewFilename(newFileName);
//					 }
					ent.setNewFilename(newFileName);
					ent.setZxjs_id(zxzm.getId());
					ent.setOldFilename(oldFileName);
					ent.setFileType(fileType);
					zxzmDao.save(ent);
				}
			}
		}
	}

	@Override
	public void update(ZxzmEntity zxzm, String id, String fName) {
		ZxzmEntity  zm = zxzmDao.findById(ZxzmEntity.class, id);
		zxzm.setCreate_time(zm.getCreate_time());
		BeanUtils.copyProperties(zxzm, zm, new String[]{"user_id","user_name","sh_user_id","sh_user_name","sh_status","attrList"});
		zxzmDao.saveOrUpdate(zm);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZxzmAttrsEntity ent = new ZxzmAttrsEntity();
//					if(!fileType.equals("img") && !fileType.equals("pdf")){
//						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
//						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
//						if(result == 0){
//							//更新PDF文件名到数据库
//							ent.setNewFilename(transName);
//						}
//					}else{
//						ent.setNewFilename(newFileName);
//					}
					ent.setNewFilename(newFileName);
					ent.setZxjs_id(zxzm.getId());
					ent.setOldFilename(oldFileName);
					ent.setFileType(fileType);
					zxzmDao.save(ent);
				}
			}
		}
		
	}

	@Override
	public void delFj(String id) {
		zxzmDao.deleteById(ZxzmAttrsEntity.class, id);
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			zxzmDao.deleteById(ZxzmEntity.class, id[i]);
		}
	}

	@Override
	public void saveSh(String id, String sh_status, String accCode, String name) {
		ZxzmEntity  zm = zxzmDao.findById(ZxzmEntity.class, id);
		zm.setCreate_time(new Date());
		zm.setSh_status(sh_status);
		zm.setSh_user_id(accCode);
		zm.setSh_user_name(name);
		BeanUtils.copyProperties(zm, new String[]{"user_id","user_name","attrList"});
		zxzmDao.saveOrUpdate(zm);
	}

	@Override
	public List<ZxzmEntity> getContent() {
		return zxzmDao.getContent();
	}
}
