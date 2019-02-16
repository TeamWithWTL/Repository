package com.jcwx.service.dflz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.AccessoryDao;
import com.jcwx.dao.dflz.DzywDao;
import com.jcwx.entity.dflz.AccessoryEntity;
import com.jcwx.entity.dflz.DzywEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.DzywService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;
@Service
public class DzywServiceImpl  implements DzywService{
	@Autowired
	private DzywDao dzywDao;
	@Autowired
	private AccessoryDao accessoryDao;
	/**
	 * 分页查询
	 */
	@Override
	public Pagenate<DzywEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return dzywDao.findByPage(pageNumber, pagesize, map);
	}
	/**
	 * id查询
	 */
	@Override
	public DzywEntity findById(String id) {
		return dzywDao.findById(DzywEntity.class, id);
	}
	/**
	 * 添加  修改
	 */
	@Override
	public void  saveOrUpdate(DzywEntity dzywEntity){
		dzywDao.saveOrUpdate(dzywEntity);
	}
	@Override
	public void save(DzywEntity dzywEntity) {
		dzywDao.save(dzywEntity);
	}
	
	/**
	 * 保存
	 */
	@Override
	public void saves(String title,SysAccCount acc, String id, String content, String fName) {
		String userCode = acc.getAccCode();//用户编码
		String accName = acc.getName();//用户名称
		DzywEntity dzywEntity=new DzywEntity();
		
		dzywEntity.setTitle(StringUtil.filterchart(title));
		
		dzywEntity.setContent(content);
		dzywEntity.setCreate_time(new Date());
		dzywEntity.setUser_id(userCode);
		dzywEntity.setUser_name(accName);
		dzywEntity.setSh_status("0");//初始化审核状态
		dzywEntity.setIs_hot("2");//初始化为非热点
		dzywDao.saveOrUpdate(dzywEntity);
		
		if (null!=fName && !("".equals(fName))) {
			String[] fNames = fName.split(">");
			for (String f : fNames) {
				if (!"".equals(f)) {
					JSONObject fromObject = JSONObject.fromObject(f);
					String newName = fromObject.getString("newName");
					String oldName = fromObject.getString("oldName");
					String type = fromObject.getString("type");
					AccessoryEntity accessoryEntity = new AccessoryEntity();
					accessoryEntity.setYw_id(dzywEntity.getId());
					accessoryEntity.setOld_fileName(oldName);
					accessoryEntity.setNew_fileName(newName);
					accessoryEntity.setFile_type(type);
					accessoryDao.saveOrUpdate(accessoryEntity);
				}
			}
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update( String title, SysAccCount acc, String id, String content, String fName) {
		DzywEntity dzywEntity = dzywDao.findById(DzywEntity.class, id); //查询此id是不是已经存在
		dzywEntity.setTitle(StringUtil.filterchart(title));
		dzywEntity.setContent(content);
		dzywDao.saveOrUpdate(dzywEntity);
		
		if (null!=fName && !("".equals(fName))) {
			String[] fNames = fName.split(">");
			for (String f : fNames) {
				if (!"".equals(f)) {
					JSONObject fromObject = JSONObject.fromObject(f);
					String newName = fromObject.getString("newName");
					String oldName = fromObject.getString("oldName");
					String type = fromObject.getString("type");
					AccessoryEntity accessoryEntity = new AccessoryEntity();
					accessoryEntity.setYw_id(dzywEntity.getId());
					accessoryEntity.setOld_fileName(oldName);
					accessoryEntity.setNew_fileName(newName);
					accessoryEntity.setFile_type(type);
					accessoryDao.saveOrUpdate(accessoryEntity);
				}
			}
		}
	}
	/**
	 * 删除
	 */
	@Override
	public void del(String ids) {
		String[] split = ids.split(";");
		for (String id : split) {
			dzywDao.deleteById(DzywEntity.class, id);
		}
	}
	
	/**
	 * 审核
	 */
	@Override
	public void audit(String id, String status,SysAccCount acc) {
		 String accCode = acc.getAccCode();
		 String name = acc.getName();
		DzywEntity dzywEntity = dzywDao.findById(DzywEntity.class, id);
		dzywEntity.setSh_status(status);//修改审核状态
		dzywEntity.setSh_user_id(accCode);
		dzywEntity.setSh_user_name(name);//审核人
		if (status.equals("2")) {//2为审核不通过，如果是审核不通过，就是取消热点
			dzywEntity.setIs_hot("2");//2为取消热点
		}
		
		dzywDao.saveOrUpdate(dzywEntity);
	}
	
	/**
	 * 设置热点
	 */
	@Override
	public void hot(String id, String hot) {
		System.out.println(hot=="1");
		if ("1".equals(hot)) {//1为热点状态
			List<DzywEntity> dzywHotList = dzywDao.findByHot(hot);
			for (DzywEntity dzywEntity : dzywHotList) {
				dzywEntity.setIs_hot("2");//2为取消热点状态
				dzywDao.saveOrUpdate(dzywEntity);
			}
		}
		if (!"".equals(id)&&null!=id) {
			DzywEntity dzywEntity = dzywDao.findById(DzywEntity.class, id);
			if (dzywEntity!=null) {
				dzywEntity.setIs_hot(hot);
				dzywDao.saveOrUpdate(dzywEntity);
			}
		}
		
		
	}
	@Override
	public List<DzywEntity> getDzywContentList() {
		return dzywDao.getDzywContentList();
	}
}
