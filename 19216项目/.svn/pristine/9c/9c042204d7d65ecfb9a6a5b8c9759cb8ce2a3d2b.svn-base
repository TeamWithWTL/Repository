package com.jcwx.service.shzz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shzz.ZzxxDao;
import com.jcwx.entity.shzz.ZzxxAttrsEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.ZzxxService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("zzxxService")
public class ZzxxServiceImpl implements ZzxxService{

	@Autowired
	private ZzxxDao shzzxxDao;

	@Override
	public Pagenate<ZzxxEntity> findByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		return shzzxxDao.findByPage(pageNumber, pagesize, map);
	}
	
	@Override
	public List<ZzxxEntity> getList() {
		return shzzxxDao.getList();
	}

	@Override
	public ZzxxEntity getById(String id) {
		return shzzxxDao.findById(ZzxxEntity.class, id);
	}

	@Override
	public void save(ZzxxEntity zzxx, String fName) {
		zzxx.setSc_status("1");
		zzxx.setEnter_date(new Date());
		shzzxxDao.save(zzxx);
        if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZzxxAttrsEntity ent = new ZzxxAttrsEntity();
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
					ent.setShzzxx_id(zzxx.getId());
					ent.setOldFilename(oldFileName);
					ent.setFileType(fileType);
					shzzxxDao.save(ent);
				}
			}
		}
	}

	@Override
	public void update(ZzxxEntity zzxx, String id, String fName) {
		ZzxxEntity zx = shzzxxDao.findById(ZzxxEntity.class, id);
		zzxx.setEnter_date(zx.getEnter_date());
		if(null!=zzxx.getXscreate_timeFmt() || !"".equals(zzxx.getXscreate_timeFmt())){
			zzxx.setCreate_time(DateUtils.parseDate(zzxx.getXscreate_timeFmt(), "yyyy-MM-dd"));
		}else{
			zzxx.setCreate_time(null);
		}
		BeanUtils.copyProperties(zzxx, zx, new String[]{"fr_id","fr_name","fr_phone","sc_status","attrList"});
		shzzxxDao.saveOrUpdate(zx);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZzxxAttrsEntity ent = new ZzxxAttrsEntity();
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
					ent.setShzzxx_id(zzxx.getId());
					ent.setOldFilename(oldFileName);
					ent.setFileType(fileType);
					shzzxxDao.save(ent);
				}
			}
		}
	}
	
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			shzzxxDao.deleteById(ZzxxEntity.class, id[i]);
		}
	}

	@Override
	public void delFj(String id) {
		shzzxxDao.deleteById(ZzxxAttrsEntity.class, id);	
	}

	@Override
	public void updateZzFr(ZzxxEntity zzxxEntity) {
		shzzxxDao.saveOrUpdate(zzxxEntity);
	}

	@Override
	public List<ZzxxEntity> getShzzByZzjg(String frString) {
		return shzzxxDao.getShzzByZzjg(frString);
	}
	
	@Override
	public ZzxxEntity getZzByFrId1(String frId) {
		return shzzxxDao.getZzByFrId1(frId);
	}


	@Override
	public List<ZzxxEntity> getZzByFrId(String frId) {
		return shzzxxDao.getZzByFrId(frId);
	}

}
