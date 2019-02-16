package com.jcwx.service.dflz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.ExpAcceDao;
import com.jcwx.dao.dflz.ExposureDao;
import com.jcwx.entity.dflz.ExpAcceEntity;
import com.jcwx.entity.dflz.ExposureEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.ExposureService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;
@Service
public class ExposureServiceImpl implements ExposureService{
	@Autowired 
	private ExposureDao exposureDao;
	@Autowired
	private ExpAcceDao expAcceDao;

	@Override
	public Pagenate<ExposureEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return exposureDao.findByPage(pageNumber,pagesize,map);
	}

	@Override
	public ExposureEntity findById(String id) {
		return exposureDao.findById(ExposureEntity.class, id);
	}

	@Override
	public void saveOrUpdate(ExposureEntity exposureEntity) {
		exposureDao.saveOrUpdate(exposureEntity);
	}

	@Override
	public void update(String title, SysAccCount acc, String id, String content, String fName) {
		ExposureEntity exposureEntity = exposureDao.findById(ExposureEntity.class, id); //查询此id是不是已经存在
		exposureEntity.setTitle(StringUtil.filterchart(title));
		exposureEntity.setContent(content);
		exposureDao.saveOrUpdate(exposureEntity);
		
		if (null!=fName && !("".equals(fName))) {
			String[] fNames = fName.split(">");
			for (String f : fNames) {
				if (!"".equals(f)) {
					JSONObject fromObject = JSONObject.fromObject(f);
					String newName = fromObject.getString("newName");
					String oldName = fromObject.getString("oldName");
					String type = fromObject.getString("type");
					ExpAcceEntity expAcceEntity = new ExpAcceEntity();
					expAcceEntity.setNew_id(exposureEntity.getId());
					expAcceEntity.setOld_fileName(oldName);
					expAcceEntity.setNew_fileName(newName);
					expAcceEntity.setFile_type(type);
					expAcceDao.saveOrUpdate(expAcceEntity);
				}
			}
		}
	}

	@Override
	public void saves(String title, SysAccCount acc, String id, String content, String fName) {
		String userCode = acc.getAccCode();//用户编码
		String accName = acc.getName();//用户名称
		ExposureEntity exposureEntity=new ExposureEntity();
		exposureEntity.setTitle(StringUtil.filterchart(title));
		exposureEntity.setContent(content);
		exposureEntity.setCreate_time(new Date());
		exposureEntity.setUser_id(userCode);
		exposureEntity.setUser_name(accName);
		exposureEntity.setSh_status("0");//初始化审核状态
		exposureDao.saveOrUpdate(exposureEntity);
		
		if (null!=fName && !("".equals(fName))) {
			String[] fNames = fName.split(">");
			for (String f : fNames) {
				if (!"".equals(f)) {
					JSONObject fromObject = JSONObject.fromObject(f);
					String newName = fromObject.getString("newName");
					String oldName = fromObject.getString("oldName");
					String type = fromObject.getString("type");
					ExpAcceEntity expAcceEntity = new ExpAcceEntity();
					expAcceEntity.setNew_id(exposureEntity.getId());
					expAcceEntity.setOld_fileName(oldName);
					expAcceEntity.setNew_fileName(newName);
					expAcceEntity.setFile_type(type);
					expAcceDao.saveOrUpdate(expAcceEntity);
				}
			}
		}
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			exposureDao.deleteById(ExposureEntity.class, id[i]);
		}
	}

	@Override
	public void audit(String id, String status, SysAccCount acc) {
		String userCode = acc.getAccCode();//用户编码
		String accName = acc.getName();//用户名称
		ExposureEntity exposureEntity = exposureDao.findById(ExposureEntity.class, id);
		exposureEntity.setSh_status(status);
		exposureEntity.setSh_user_id(userCode);
		exposureEntity.setSh_user_name(accName);
		exposureDao.saveOrUpdate(exposureEntity);
	}

	@Override
	public List<ExposureEntity> getBgtContentList() {
		return exposureDao.getBgtContentList();
	}

}
