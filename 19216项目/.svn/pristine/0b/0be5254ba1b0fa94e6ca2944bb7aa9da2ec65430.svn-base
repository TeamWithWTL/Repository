package com.jcwx.service.shfw.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shfw.SqfwDao;
import com.jcwx.entity.shfw.ShfwSqfwAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqfwEntity;
import com.jcwx.service.shfw.SqfwService;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("sqfwService") 
public class SqfwServiceImpl implements SqfwService {
	
	@Autowired
	private SqfwDao sqfwDao;

	@Override
	public Pagenate<ShfwSqfwEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return sqfwDao.findByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public ShfwSqfwEntity getById(String id) {
		return sqfwDao.findById(ShfwSqfwEntity.class, id);
	}
	
	@Override
	public ShfwSqfwEntity getByIsHot(String isHot) {
		return sqfwDao.getByIsHot(isHot);
	}

	@Override
	public void save(ShfwSqfwEntity sqfw, String fName, String tjStatus) {
		//sqfw.setTitle(sqfw.getTitle());
		//sqfw.setContent(sqfw.getContent());
		sqfw.setCreateTime(new Date());
		sqfw.setShStatus("0");		//审核状态 0 待审核
		sqfw.setIsHot("2");			//是否热点 2 否
		sqfwDao.save(sqfw);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwSqfwAttrsEntity zwAttr = new ShfwSqfwAttrsEntity();
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
					zwAttr.setSqfwId(sqfw.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					sqfwDao.save(zwAttr);
				}
			}
		}
	}

	@Override
	public void update(ShfwSqfwEntity sqfw, String id, String fName, String tjStatus) {
		ShfwSqfwEntity sqfw1 = getById(id);
		//sqfw.setTitle(sqfw.getTitle());
		//sqfw.setContent(sqfw.getContent());
		sqfw.setTjStatus(tjStatus);
		sqfw.setShStatus("0");		//审核状态 0 待审核
		BeanUtils.copyProperties(sqfw, sqfw1, new String[]{"userId", "userName", "isHot", "createTime", "attrList"});
		sqfwDao.saveOrUpdate(sqfw1);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwSqfwAttrsEntity zwAttr = new ShfwSqfwAttrsEntity();
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
					zwAttr.setSqfwId(sqfw.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					sqfwDao.save(zwAttr);
				}
			}
		}	
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			sqfwDao.deleteById(ShfwSqfwEntity.class, id[i]);
		}
	}

	@Override
	public void delFj(String id) {
		sqfwDao.deleteById(ShfwSqfwAttrsEntity.class, id);
	}

	@Override
	public void shStatus(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			ShfwSqfwEntity sqfw = getById(id[i]);
			if(sqfw!=null){
				sqfw.setShStatus("1");
				sqfwDao.saveOrUpdate(sqfw);
			}
		}
	}
	
	@Override
	public void shSqfwStatus(ShfwSqfwEntity sqfw) {
		sqfwDao.saveOrUpdate(sqfw);
	}

	@Override
	public void updateshStatus(String id) {
		ShfwSqfwEntity sqfw = getById(id);
		if(sqfw!=null){
			sqfwDao.saveOrUpdate(sqfw);
		}
	}
	
	@Override
	public void shZt(String id, String flag) {
		ShfwSqfwEntity sqfw = getById(id);
		if(sqfw!=null){
			sqfw.setShStatus(flag);
			sqfwDao.saveOrUpdate(sqfw);
		}
	}

	@Override
	public void updateisHot(String id, String flag) {
		ShfwSqfwEntity sqfw = getById(id);
		if(sqfw!=null){
			sqfw.setIsHot(flag);
			sqfwDao.saveOrUpdate(sqfw);
		}
	}

}
