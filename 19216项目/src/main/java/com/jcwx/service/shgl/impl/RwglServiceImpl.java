package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.RwclDao;
import com.jcwx.dao.shgl.RwglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwglAttrEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("rwglService")
public class RwglServiceImpl implements RwglService{
	
	@Autowired
	private RwglDao rwglDao; 
	@Autowired
	private RwclDao rwclDao; 
	
	@Override
	public Pagenate<RwglEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		return rwglDao.getRwglContent(pageNumber, pagesize, map);
	}

	@Override
	public void save(String jsrIds, String fName, RwglEntity rwgl, String curRoleCode) {
		//保存任务
		rwglDao.save(rwgl);
		//保存任务附件 begin
		if(fName !=null && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					RwglAttrEntity docAttr = new RwglAttrEntity();
					docAttr.setNew_filename(newFileName);
					docAttr.setTask_id(rwgl.getId());
					docAttr.setOld_filename(oldFileName);
					docAttr.setFile_type(fileType);
					rwglDao.save(docAttr);
				}
			}
		}//保存任务附件 end
		
		//下发或转发任务
		String[] clrArray = jsrIds.split(";");
		for(String curId : clrArray){
			RwClEntity deal  = new RwClEntity();
			if("".equals(curId)){
				continue;
			}
			RwglEntity rwglEntity = new RwglEntity();
			rwglEntity.setId(rwgl.getId());//任务ID
			rwglEntity.setTitle(rwgl.getTitle());//任务标题
			deal.setRwglEntity(rwglEntity);
			deal.setPre_emp(rwgl.getFbr_id());//发布人ID
			deal.setCur_emp(curId);//当前处理人ID
			deal.setCur_role_id(curRoleCode);//当前处理人角色ID
			deal.setStarte_date(rwgl.getCreate_date());
			deal.setIs_down("2");//1是已下发，2是未下发
			deal.setIs_back("2");//1是已反馈，2是未反馈
			deal.setEnd_date(rwgl.getFinish_date());
			rwglDao.save(deal);
		}
	}

	@Override
	public RwglEntity findId(String id) {
		return rwglDao.findById(RwglEntity.class, id);
	}

	@Override
	public Pagenate<RwClEntity> getRwglMyContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		return rwglDao.getRwglMyContent(pageNumber, pagesize, map);
	}


	@Override
	public void upsave(RwglEntity rwgl) {
		rwglDao.save(rwgl);
		
	}

	@Override
	public void delRwContent(String ids,String fbrId) {
		String id[] = ids.split(";");
		for(int i = 0; i<id.length; i++){
			RwglEntity rwEntity= rwglDao.findById(RwglEntity.class, id[i]);
			List<RwClEntity> rcLists = rwEntity.getClEntities();
			if(fbrId.equals(rwEntity.getFbr_id())){
				rwglDao.deleteById(RwglEntity.class, id[i]);
				for(int j = 0; j<rcLists.size(); j++){
					rwglDao.deleteById(RwClEntity.class, rcLists.get(j).getId());
				}
			}else{
				List<RwClEntity> clEntities = rwclDao.findRcForDel(id[i],fbrId);
				for(int n = 0; n<clEntities.size(); n++){
					rwglDao.deleteById(RwClEntity.class, clEntities.get(n).getId());
				}
			}
		}
	}

	@Override
	public List<SysAccCount> findByCode(String roleCode,String zsId,String ztType) {
		return rwglDao.findByCode(roleCode,zsId,ztType);
	}

	@Override
	public List<ShglCmanagerEntity> findSqryById(String id) {
		return rwglDao.findSqryById(id);
	}

	@Override
	public List<ShglServiceStationEntity> findFwz(String sqId) {
		return rwglDao.findFwz(sqId);
	}

	@Override
	public List<ShglGridEntity> findWg(String fwzId) {
		return rwglDao.findWg(fwzId);
	}

	@Override
	public Pagenate<RwClEntity> getRwclContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		return rwglDao.getRwclContent(pageNumber,pagesize,map);
	}

	@Override
	public ShglServiceStationEntity getFwz(String fwzId) {
		return rwglDao.findById(ShglServiceStationEntity.class, fwzId);
	}

	@Override
	public Pagenate<RwglEntity> getRwglMyXFList(Integer pageNumber, int pagesize, Map<String, String> map) {
		
//		String countSql = "SELECT COUNT(DISTINCT rw.id,rw.title, rw.fbr_name, rw.create_date) FROM shgl_task_table rw LEFT JOIN shgl_task_deal_table rc ON rw.id = rc.task_id WHERE 1=1 ";
//		String Sql = "SELECT DISTINCT rw.id, rw.title, rw.fbr_name, rw.create_date FROM shgl_task_table rw LEFT JOIN shgl_task_deal_table rc ON rw.id = rc.task_id WHERE 1=1 ";
		return rwglDao.getRwglMyXFList(pageNumber,pagesize,map);
	}

	@Override
	public void saveOrUpdateAttrs(RwglAttrEntity docAttr) {
		rwglDao.saveOrUpdate(docAttr);
	}
}
