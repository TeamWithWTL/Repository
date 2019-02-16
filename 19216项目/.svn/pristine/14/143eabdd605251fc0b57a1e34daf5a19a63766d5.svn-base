package com.jcwx.service.shfw.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shfw.ZwxxDao;
import com.jcwx.entity.shfw.ShfwZwxxAttrsEntity;
import com.jcwx.entity.shfw.ShfwZwxxEntity;
import com.jcwx.service.shfw.ZwxxService;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("zwxxService") 
public class ZwxxServiceImpl implements ZwxxService {
	
	@Autowired
	private ZwxxDao zwxxDao;

	@Override
	public Pagenate<ShfwZwxxEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return zwxxDao.findByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public ShfwZwxxEntity getById(String id) {
		return zwxxDao.findById(ShfwZwxxEntity.class, id);
	}
	
	@Override
	public void save(ShfwZwxxEntity zwxx, String fName, String tjStatus) {
		zwxx.setTitle(zwxx.getTitle());
		zwxx.setContent(zwxx.getContent());
		zwxx.setCreateTime(new Date());
		zwxx.setShStatus("0");			//审核状态 0 待审核
		zwxx.setIsTop("2");				//是否置顶 2 否
		zwxxDao.save(zwxx);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwZwxxAttrsEntity zwAttr = new ShfwZwxxAttrsEntity();
					/*if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFilename(transName);
						}
					}else{
						zwAttr.setNewFilename(newFileName);
					}*/
					zwAttr.setZwxxId(zwxx.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					zwxxDao.save(zwAttr);
				}
			}
		}
	}

	@Override
	public void update(ShfwZwxxEntity zwxx, String id, String fName, String tjStatus) {
		ShfwZwxxEntity zwxx1 = getById(id);
		zwxx.setTitle(zwxx.getTitle());
		zwxx.setContent(zwxx.getContent());
		zwxx.setTjStatus(tjStatus);
		zwxx.setShStatus("0");		//审核状态 0 待审核
		BeanUtils.copyProperties(zwxx, zwxx1, new String[]{"userId", "userName", "isTop", "createTime", "attrList"});
		zwxxDao.saveOrUpdate(zwxx1);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwZwxxAttrsEntity zwAttr = new ShfwZwxxAttrsEntity();
					/*if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFilename(transName);
						}
					}else{
						zwAttr.setNewFilename(newFileName);
					}*/
					zwAttr.setZwxxId(zwxx.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					zwxxDao.save(zwAttr);
				}
			}
		}	
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			zwxxDao.deleteById(ShfwZwxxEntity.class, id[i]);
		}
	}

	@Override
	public void delFj(String id) {
		zwxxDao.deleteById(ShfwZwxxAttrsEntity.class, id);
	}

	@Override
	public void shStatus(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			ShfwZwxxEntity zwxx = getById(id[i]);
			if(zwxx!=null){
				zwxx.setShStatus("1");
				zwxxDao.saveOrUpdate(zwxx);
			}
		}
	}
	

	@Override
	public void shZwStatus(ShfwZwxxEntity zwxx) {
		zwxxDao.saveOrUpdate(zwxx);
	}

	@Override
	public void updateshStatus(String id) {
		ShfwZwxxEntity zwxx = getById(id);
		if(zwxx!=null){
			zwxxDao.saveOrUpdate(zwxx);
		}
	}

	@Override
	public void shZt(String id, String flag) {
		ShfwZwxxEntity zwxx = getById(id);
		if(zwxx!=null){
			zwxx.setShStatus(flag);
			zwxxDao.saveOrUpdate(zwxx);
		}
	}

}
