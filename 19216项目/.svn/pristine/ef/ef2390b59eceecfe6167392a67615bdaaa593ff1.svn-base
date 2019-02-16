/**
 * 
 */
package com.jcwx.service.shzz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shzz.DtbbDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.DtbbAttrEntity;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.service.shzz.DtbbService;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author m
 *
 */
@Service("dtbbService")
public class DtbbServiceImpl implements DtbbService{
	@Autowired
	private DtbbDao dtbbDao;

	/* (non-Javadoc)
	 * @see com.jcwx.service.shzz.DtbbService#findByPage(java.lang.Integer, int, java.util.Map)
	 */
	@Override
	
	public Pagenate<DtbbEntity> getDtbbContent(Integer pageNumber, int pageSize, Map<String, String> map) {
		return dtbbDao.getDtbbContent(pageNumber, pageSize, map);
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.shzz.DtbbService#findById(java.lang.String)
	 */
	@Override
	public DtbbEntity findById(String id) {
		return dtbbDao.findById(DtbbEntity.class, id);
	}
	

	/* (non-Javadoc)
	 * @see com.jcwx.service.shzz.DtbbService#saveOrUpdate(com.jcwx.entity.shzz.DtbbEntity)
	 */
	@Override
	public void save(DtbbEntity dtbb, String fName) {
		dtbb.setTitle(dtbb.getTitle());
		dtbb.setContent(dtbb.getContent());
		dtbb.setCreateTime(new Date());
		dtbb.setShStatus("0");
		dtbbDao.save(dtbb);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFilename = jsStr.getString("newName");
					String oldFilename = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					DtbbAttrEntity dtAttr = new DtbbAttrEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFilename.replace(StringUtil.getFileExt(newFilename), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFilename, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							dtAttr.setNew_filename(transName);
						}else{
							dtAttr.setNew_filename(newFilename);
						}
					}else{
						dtAttr.setNew_filename(newFilename);
					}
					dtAttr.setDtbb_id(dtbb.getId());
					dtAttr.setOld_filename(oldFilename);
					dtAttr.setFileType(fileType);
					dtbbDao.save(dtAttr);
				}
			}
		}
	}

	@Override
	public void update(DtbbEntity dtbb, String id, String fName) {
		DtbbEntity dtbb1 = findById(id);
		dtbb.setTitle(dtbb.getTitle());
		dtbb.setContent(dtbb.getContent());
		dtbb.setShStatus("0");
		BeanUtils.copyProperties(dtbb, dtbb1, new String[]{"userId","userName","createTime","attrList"});
		dtbbDao.saveOrUpdate(dtbb1);
		System.out.println("AAAAAAA0"+new String[]{"userId","userName","createTime","attrList"});
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFilename = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					DtbbAttrEntity dtAttr = new DtbbAttrEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFilename.replace(StringUtil.getFileExt(newFilename), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFilename, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							dtAttr.setNew_filename(transName);
						}
					}else{
						dtAttr.setNew_filename(newFilename);
					}
					dtAttr.setDtbb_id(dtbb.getId());
					dtAttr.setOld_filename(oldFileName);
					dtAttr.setFileType(fileType);
					dtbbDao.save(dtAttr);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.shzz.DtbbService#saves(java.lang.String, com.jcwx.entity.pub.SysAccCount, java.lang.String, java.lang.String, java.lang.String)
	 */

	
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			dtbbDao.deleteById(DtbbEntity.class, id[i]);
		}
		
	}
	@Override
	public void shZxStatus(DtbbEntity dtbb) {
		dtbbDao.saveOrUpdate(dtbb);
	}

	@Override
	public void delFj(String id) {
		dtbbDao.deleteById(DtbbEntity.class, id);
	}
	@Override
	public void updateshStatus(String id) {
		DtbbEntity dtbb = findById(id);
		if(dtbb!=null){
			dtbbDao.saveOrUpdate(dtbb);
		}
		
	}
	@Override
	public DtbbEntity getById(String id) {
		return dtbbDao.findById(DtbbEntity.class, id);
	}

	@Override
	public List<DtbbEntity> getDtbbContentList() {
		return dtbbDao.getDtbbContentList();
	}
	
	
	
	@Override
	public void doAuditing(String id, String status, SysAccCount sysAccCount) {
		
		String accCode = sysAccCount.getAccCode();
		String name = sysAccCount.getName();
		DtbbEntity dtbbEntity = dtbbDao.findById(DtbbEntity.class, id);
		dtbbEntity.setShStatus(status);
		dtbbEntity.setShUserName(name);//审核人
		dtbbEntity.setShUserId(accCode);
		dtbbDao.saveOrUpdate(dtbbEntity);
	}

	@Override
	public List<DtbbYjEntity> findByDtId(String id) {
		return dtbbDao.findByDtId(id);
	}

	@Override
	public void saveLy(DtbbYjEntity d) {
		dtbbDao.saveOrUpdate(d);
	}

	@Override
	public Pagenate<DtbbYjEntity> getByDtId(Integer pageNumber, int pageSize, Map<String, String> map) {
		return dtbbDao.getByDtId(pageNumber, pageSize, map);
	}
	/**
	 * 留言分页查询
	 */
	@Override
	public Pagenate<DtbbYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return dtbbDao.findLyByPage(pageNumber,pagesize,map);
	}
	/**
	 * 留言删除
	 */
	@Override
	public void delLy(String id) {
		dtbbDao.deleteById(DtbbYjEntity.class, id);
	}
	/**
	 * 审核留言
	 */
	@Override
	public void successLy(String id) {
		DtbbYjEntity dtbbYjEntity = dtbbDao.findById(DtbbYjEntity.class, id);
		dtbbYjEntity.setStatus("1");//1为审核通过，0为未审核
		dtbbDao.saveOrUpdate(dtbbYjEntity);//修改保存
	}
	 

}

