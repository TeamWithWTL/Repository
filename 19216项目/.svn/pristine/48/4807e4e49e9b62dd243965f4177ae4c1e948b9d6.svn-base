package com.jcwx.service.shgl.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.RwclDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysRole;
import com.jcwx.entity.shgl.RwClEntity;
import com.jcwx.entity.shgl.RwclAttrEntity;
import com.jcwx.entity.shgl.RwglEntity;
import com.jcwx.entity.shgl.TaskDealEntity;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.utils.Pagenate;

import net.sf.json.JSONObject;

@Service("rwclService")
public class RwclServiceImpl implements RwclService{
	
	@Autowired
	private RwclDao rwclDao;
	
	@Override
	public void save(RwClEntity rwcl) {
		
		rwclDao.save(rwcl);
	}

	@Override
	public RwClEntity findId(String id) {
		
		return rwclDao.findById(RwClEntity.class, id);
	}

	@Override
	public Pagenate<RwClEntity> getRwglContent(Integer pageNumber, int pagesize, Map<String, String> map) {
		return rwclDao.getRwglContent(pageNumber, pagesize, map);
	}

	@Override
	public List<RwClEntity> findMyRwNo(Map<String, String> rwMap) {
		return rwclDao.findMyRwNo(rwMap);
	}
	/**
	 * 多对一，查询我的未处理任务
	 */
	@Override
	public Pagenate<RwClEntity> findClByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return rwclDao.findClByPage(pageNumber,pagesize,map);
	}
	/**
	 * Id查询任务管理
	 */
	@Override
	public TaskDealEntity findById(String rwglId) {
		TaskDealEntity rwglEntity = null;
		if (rwglId!=null&&!"".equals(rwglId)) {
			rwglEntity = rwclDao.findById(TaskDealEntity.class, rwglId);
		}
		
		
		return rwglEntity;
	}
	/**
	 * id查询系统角色
	 */
	@Override
	public SysRole findSysRoleById(String role_id) {
		SysRole sysRole = null;
		if (role_id!=null &&!"".equals(role_id)) {
			sysRole = rwclDao.findById(SysRole.class, role_id);
		}
		return sysRole;
	}
	/**
	 * 查询当前排序层级的所有角色集合
	 */
	@Override
	public List<SysRole> findSysRoleList(int i) {
		
		return rwclDao.findSysRoleList(i);
	}
	/**
	 * 以角色id查询用户集合
	 */
	@Override
	public List<SysAccCount> findSysAccByRoleCode(String roleCode) {
		return rwclDao.findSysAccByRoleCode(roleCode);
	}
	/**
	 * 下发
	 */
	@Override
	public void issue( String rwclId,String content, String personIds, String curRoleId, String accCode) {
		//查询要下发的任务处理信息
		TaskDealEntity taskDealEntity=rwclDao.findById(TaskDealEntity.class, rwclId);
		if (taskDealEntity!=null) {
			taskDealEntity.setIs_down("1");//改为已下发状态
			taskDealEntity.setEnd_date(new Date());//下发时间
		}
		String rwglId=taskDealEntity.getRwglEntity().getId();
		rwclDao.saveOrUpdate(taskDealEntity);//修改
		
		//新建下发记录，并指定处理人员
		String curIds[] = personIds.split(";");
		for(String curId : curIds){
			TaskDealEntity dealEntity = new TaskDealEntity();
			if (content!=null &&!"".equals(content)) {
				dealEntity.setContent(content);//下 发意见
			}
			dealEntity.setIs_back("2");//初始化为未反馈状态
			dealEntity.setIs_down("2");//初始化为未下发状态
			dealEntity.setStarte_date(new Date());//下发时间
			dealEntity.setPre_emp(accCode);//上一位处理人id,为当前登录用户
			dealEntity.setCur_emp(curId);//要下发人的id ，指定为当前处理人
			dealEntity.setCur_role_id(curRoleId);//指定下发人的角色id
			RwglEntity rwglEntity = new RwglEntity();
			rwglEntity.setId(rwglId);
			dealEntity.setRwglEntity(rwglEntity);//要处理的任务id
			rwclDao.save(dealEntity);//保存
		}
	}

	/**
	 * id查询用户信息
	 */
	@Override
	public SysAccCount findSysAccById(String pre_emp) {
		SysAccCount sysAccCount = rwclDao.findById(SysAccCount.class, pre_emp);
		return sysAccCount;
	}
	/**
	 * 保存反馈
	 */
	@Override
	public void goFk(String id, String content, String fName) {
		//查询出要反馈的记录
		RwClEntity taskDealEntity=rwclDao.findById(RwClEntity.class, id);
		taskDealEntity.setIs_back("1");//改为已反馈
		taskDealEntity.setEnd_date(new Date());//反馈时间
		taskDealEntity.setContent(content);//反馈意见
		rwclDao.saveOrUpdate(taskDealEntity);//修改
		//保存任务附件 begin
		if(fName !=null && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					RwclAttrEntity docAttr = new RwclAttrEntity();
					docAttr.setNew_filename(newFileName);
					docAttr.setTask_deal_id(taskDealEntity.getId());
					docAttr.setOld_filename(oldFileName);
					docAttr.setFile_type(fileType);
					rwclDao.save(docAttr);
				}
			}
		}//保存任务附件 end
		//新建记录，保存反馈信息
		/*String pre_emp = taskDealEntity.getPre_emp();
		String cur_emp = taskDealEntity.getCur_emp();
		String cur_role_id = taskDealEntity.getCur_role_id();
		Date starte_date = taskDealEntity.getStarte_date();
		TaskDealEntity dealEntity = new TaskDealEntity();
		dealEntity.setContent(content);//反馈意见
		dealEntity.setIs_back("1");//初始化为已反馈状态
		dealEntity.setIs_down("2");//初始化为未下发状态
		dealEntity.setStarte_date(starte_date);//下发时间
		dealEntity.setEnd_date(new Date());
		dealEntity.setPre_emp(pre_emp);//上一位处理人id,为当前登录用户
		dealEntity.setCur_emp(cur_emp);//反馈人的id ，指定为当前处理人
		dealEntity.setCur_role_id(cur_role_id);//反馈人的角色id
		RwglEntity rwglEntity = new RwglEntity();
		rwglEntity.setId(id);
		dealEntity.setRwglEntity(rwglEntity);;//要处理的任务id
		rwclDao.save(dealEntity);//保存
*/	}


	@Override
	public RwClEntity findRwclById(String id) {
		return rwclDao.findRwclById(id);
	}
	/**
	 * 查询单个任务的所有处理记录
	 */
	@Override
	public List<RwClEntity> findDealList(String rwglId,String accCode,RwClEntity taskDealEntity,String isWgy) {
		return rwclDao.findDealList(rwglId,accCode,taskDealEntity,isWgy);
	}

	@Override
	public List<RwClEntity> findRwcl(String taskId) {
		return rwclDao.findRwcl(taskId);
	}

	@Override
	public int findNoDealRwCount(Map<String, String> addMap) {
		return rwclDao.findNoDealRwCount(addMap);
	}

	@Override
	public void saveDealAttrs(RwClEntity clEntity, String fName) {
		//保存任务附件 begin
		if(fName !=null && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					RwclAttrEntity docAttr = new RwclAttrEntity();
					docAttr.setNew_filename(newFileName);
					docAttr.setTask_deal_id(clEntity.getId());
					docAttr.setOld_filename(oldFileName);
					docAttr.setFile_type(fileType);
					rwclDao.save(docAttr);
				}
			}
		}//保存任务附件 end
	}

	@Override
	public void upadateRc(RwClEntity taskDealEntity) {
		rwclDao.saveOrUpdate(taskDealEntity);
	}

	@Override
	public void saveOrUpdateAttrs(RwclAttrEntity docAttr) {
		rwclDao.saveOrUpdate(docAttr);
	}
}
