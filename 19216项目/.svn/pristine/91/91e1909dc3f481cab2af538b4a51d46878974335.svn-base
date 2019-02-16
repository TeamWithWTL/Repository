package com.jcwx.service.shzz.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shzz.ZxjsDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.ZxjsAttrsEntity;
import com.jcwx.entity.shzz.ZxjsEntity;
import com.jcwx.service.shzz.ZxjsService;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;
@Service("zxjsService")
public class ZxjsServiceImpl implements ZxjsService {
	
	@Autowired
	private ZxjsDao zxjsDao;

	@Override
	public Pagenate<ZxjsEntity> getZxjsContent(Integer pageNumber, int pageSize, Map<String, String> map) {
//		String hql="from ZxjsEntity z";
//		String countHql="select count(*) from ZxjsEntity z";
		return zxjsDao.getZxjsContent(pageNumber, pageSize, map);
	}

	@Override
	public ZxjsEntity getById(String id) {
		return zxjsDao.findById(ZxjsEntity.class, id);
	}

	@Override
	public void save(ZxjsEntity zxjs, String fName) {
		zxjs.setTitle(zxjs.getTitle());
		zxjs.setContent(zxjs.getContent());
		zxjs.setCreateTime(new Date());
		zxjs.setShStatus("0");
		zxjsDao.save(zxjs);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZxjsAttrsEntity zwAttr = new ZxjsAttrsEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFileName(transName);
						}else{
							zwAttr.setNewFileName(newFileName);
						}
					}else{
						zwAttr.setNewFileName(newFileName);
					}
					zwAttr.setZxjsId(zxjs.getId());
					zwAttr.setOldFileName(oldFileName);
					zwAttr.setFileType(fileType);
					zxjsDao.save(zwAttr);
				}
			}
		}
	}

	@Override
	public void update(ZxjsEntity zxjs, String id, String fName) {
		ZxjsEntity zxjs1 = getById(id);
		zxjs.setTitle(zxjs.getTitle());
		zxjs.setContent(zxjs.getContent());
		zxjs.setShStatus("0");
		BeanUtils.copyProperties(zxjs, zxjs1, new String[]{"userId","userName","createTime","attrList"});
		zxjsDao.saveOrUpdate(zxjs1);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ZxjsAttrsEntity zwAttr = new ZxjsAttrsEntity();
					/*if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFileName(transName);
						}else{
							zwAttr.setNewFileName(newFileName);
						}
					}else{
						zwAttr.setNewFileName(newFileName);
					}*/
					zwAttr.setZxjsId(zxjs.getId());
					zwAttr.setOldFileName(oldFileName);
					zwAttr.setNewFileName(newFileName);
					zwAttr.setFileType(fileType);
					zxjsDao.save(zwAttr);
				}
			}
		}
	}
//审核
	@Override
	public void shStatus(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			ZxjsEntity zxjs = getById(id[i]);
			if(zxjs!=null){
				zxjs.setShStatus("1");
				zxjsDao.saveOrUpdate(zxjs);
			}
		}
	}
/**
 * 删除中心介绍信息
 */
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			zxjsDao.deleteById(ZxjsEntity.class, id[i]);
		}
		
	}
/**
 * 删除附件
 */
@Override
public void delFj(String id) {
	// TODO Auto-generated method stub
	zxjsDao.deleteById(ZxjsAttrsEntity.class, id);
}

//更新审核状态
@Override
public void updateshStatus(String id) {
	ZxjsEntity zxjs = getById(id);
	if(zxjs!=null){
		zxjsDao.saveOrUpdate(zxjs);
	}
	
}

//审核状态
@Override
public void shZxStatus(ZxjsEntity zxjs) {
	zxjsDao.saveOrUpdate(zxjs);
}

/**
 * shezhishenhezhuangtai
 */
@Override
public void shZt(String id, String flag,SysAccCount acc) {
	String accCode = acc.getAccCode();
	 String name = acc.getName();
	
	ZxjsEntity zxjs = getById(id);
	if(zxjs!=null){
		zxjs.setShStatus(flag);
		zxjs.setShUserId(accCode);
		zxjs.setShUserName(name);
		zxjsDao.saveOrUpdate(zxjs);
	}

}

}
