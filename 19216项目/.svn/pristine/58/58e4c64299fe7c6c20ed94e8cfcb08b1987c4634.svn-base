package com.jcwx.service.shfw.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcwx.dao.shfw.SqhdDao;
import com.jcwx.entity.shfw.ShfwSqhdAttrsEntity;
import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.entity.shfw.ShfwSqhdYjEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.service.shfw.SqhdService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("sqhdService") 
@Transactional
public class SqhdServiceImpl implements SqhdService {
	
	@Autowired
	private SqhdDao sqhdDao;

	@Override
	public Pagenate<ShfwSqhdEntity> findByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		return sqhdDao.findByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public ShfwSqhdEntity getById(String id) {
		return sqhdDao.findById(ShfwSqhdEntity.class, id);
	}
	
	/**
	 * 留言审核
	 */
	@Override
	public ShfwSqhdYjEntity findId(String id) {
		return sqhdDao.findById(ShfwSqhdYjEntity.class, id);
	}
	@Override
	public ShfwSqhdEntity getByIsHot(String isHot) {
		return sqhdDao.getByIsHot(isHot);
	}

	@Override
	public void save(ShfwSqhdEntity sqhd, String fName, String fbStatus) {
		//sqhd.setTitle(sqhd.getTitle());
		//sqhd.setContent(sqhd.getContent());
		//sqhd.setCreateDate(new Date());
		sqhd.setShStatus("0");		//审核状态 0 待审核
		sqhd.setIsHot("2");			//是否热点 2 否
		if(sqhd.getStartDateFmt()!=null && !"".equals(sqhd.getStartDateFmt())){
			sqhd.setStartDate(DateUtils.parseDate(sqhd.getEndDateFmt(), "yyyy-MM-dd"));
		}else{
			sqhd.setStartDate(null);
		}
		if(sqhd.getEndDateFmt()!=null && !"".equals(sqhd.getEndDateFmt())){
			Calendar c = Calendar.getInstance();  
	        c.setTime(DateUtils.parseDate(sqhd.getEndDateFmt(), "yyyy-MM-dd")); 
	        c.set(c.HOUR_OF_DAY, 23);
	        c.set(c.MINUTE, 59);
	        c.set(c.SECOND, 59);
		    Date jstrrow = c.getTime(); 
			sqhd.setEndDate(jstrrow);
		}else{
			sqhd.setEndDate(null);
		}
		sqhdDao.save(sqhd);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwSqhdAttrsEntity zwAttr = new ShfwSqhdAttrsEntity();
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
					zwAttr.setSqhdId(sqhd.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					sqhdDao.save(zwAttr);
				}
			}
		}
	}

	@Override
	public void update(ShfwSqhdEntity sqhd, String id, String fName, String fbStatus) {
		ShfwSqhdEntity sqhd1 = getById(id);
		//sqhd.setTitle(sqhd.getTitle());
		//sqhd.setContent(sqhd.getContent());
		sqhd.setFbStatus(fbStatus);
		sqhd.setShStatus("0");		//审核状态 0 待审核
		if(sqhd.getStartDateFmt()!=null && !"".equals(sqhd.getStartDateFmt())){
			sqhd.setStartDate(DateUtils.parseDate(sqhd.getStartDateFmt(), "yyyy-MM-dd"));
		}else{
			sqhd.setStartDate(null);
		}
		if(sqhd.getEndDateFmt()!=null && !"".equals(sqhd.getEndDateFmt())){
			Calendar c = Calendar.getInstance();  
	        c.setTime(DateUtils.parseDate(sqhd.getEndDateFmt(), "yyyy-MM-dd")); 
	        c.set(c.HOUR_OF_DAY, 23);
	        c.set(c.MINUTE, 59);
	        c.set(c.SECOND, 59);
		    Date jstrrow = c.getTime(); 
			sqhd.setEndDate(jstrrow);
		}else{
			sqhd.setEndDate(null);
		}
		BeanUtils.copyProperties(sqhd, sqhd1, new String[]{"userId", "userName", "isHot", "createDate", "attrList"});
		sqhdDao.saveOrUpdate(sqhd1);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					ShfwSqhdAttrsEntity zwAttr = new ShfwSqhdAttrsEntity();
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
					zwAttr.setSqhdId(sqhd.getId());
					zwAttr.setOldFilename(oldFileName);
					zwAttr.setNewFilename(newFileName);
					zwAttr.setFileType(fileType);
					sqhdDao.save(zwAttr);
				}
			}
		}	
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			sqhdDao.deleteById(ShfwSqhdEntity.class, id[i]);
		}
	}

	@Override
	public void delFj(String id) {
		sqhdDao.deleteById(ShfwSqhdAttrsEntity.class, id);
	}

	@Override
	public void shStatus(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			ShfwSqhdEntity sqhd = getById(id[i]);
			if(sqhd!=null){
				sqhd.setShStatus("1");
				sqhdDao.saveOrUpdate(sqhd);
			}
		}
	}

	@Override
	public void shSqhdStatus(ShfwSqhdEntity sqhd) {
		sqhdDao.saveOrUpdate(sqhd);
	}

	/**
	 * 更新留言审核状态
	 */
	@Override
	public void updateLysh(String id) {
		ShfwSqhdYjEntity sqhd = findId(id);
		if(sqhd!=null){
			sqhdDao.saveOrUpdate(sqhd);
		}
	}
	
	@Override
	public void updateshStatus(String id) {
		ShfwSqhdEntity sqhd = getById(id);
		if(sqhd!=null){
			sqhdDao.saveOrUpdate(sqhd);
		}
	}
	@Override
	public void shZt(String id, String flag) {
		ShfwSqhdEntity sqhd = getById(id);
		if(sqhd!=null){
			sqhd.setShStatus(flag);
			sqhdDao.saveOrUpdate(sqhd);
		}
	}

	@Override
	public void updateisHot(String id, String flag) {
		ShfwSqhdEntity sqhd = getById(id);
		if(sqhd!=null){
			sqhd.setIsHot(flag);
			sqhdDao.saveOrUpdate(sqhd);
		}
	}

	@Override
	public List<ShfwSqhdEntity> getHdList() {
		return sqhdDao.getHdList();
	}

	@Override
	public void updateHdStatus(String id, String status) {
		ShfwSqhdEntity hd = getById(id);
		hd.setHdStatus(status);
		sqhdDao.saveOrUpdate(hd);
	}

	@Override
	public List<ShglCommunityEntity> getCommList() {
		return sqhdDao.getCommList();
	}

}
