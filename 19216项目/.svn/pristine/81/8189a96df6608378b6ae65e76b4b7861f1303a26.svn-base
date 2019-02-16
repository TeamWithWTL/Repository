package com.jcwx.service.shgl.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcwx.dao.shgl.SqmyDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglSqmyAttrsEntity;
import com.jcwx.entity.shgl.ShglSqmyDc;
import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.entity.shgl.ShglSqmyWgy;
import com.jcwx.entity.shgl.ShglSqmyZhEntity;
import com.jcwx.service.shgl.SqmyService;
import com.jcwx.utils.Pagenate;
@Service("SqmyService")
@Transactional
public class SqmyServiceImpl implements SqmyService{
	
	@Autowired
	private SqmyDao sqmyDao;
	@Override
	public <T> T findById(Class<T> type, Serializable id) {
		return sqmyDao.findById(type, id);
	}

	@Override
	public void save(Object entity) {
		sqmyDao.save(entity);
	}
	
	@Override
	public void saveorupdate(Object entity) {
		sqmyDao.saveOrUpdate(entity);
	}
	@Override
	public Pagenate<ShglSqmyEntity> findSqmyByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return sqmyDao.findSqmyByPage(pageNumber,pagesize,map);
	}

	@Override
	public Pagenate<ShglSqmyWgy> findSqmyWgyByPage(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return sqmyDao.findSqmyWgyByPage(pageNumber,pagesize,map);
	}
	@Override
	public ShglSqmyEntity getById(String id) {
		return sqmyDao.findById(ShglSqmyEntity.class, id);
	}

	public void save(String status, String sqid, String accCode, String name,
			String title, String content, int dc_num, String start_date,
			String end_date,String fName) {
				ShglSqmyEntity sqmy = new ShglSqmyEntity();
				sqmy.setSq_id(sqid);
				sqmy.setTitle(title);
				sqmy.setContent(content);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				Date start;
				Date end;
				try {
					start = sdf.parse(start_date);
					end = sdf.parse(end_date);
					Calendar c = Calendar.getInstance();  
			        c.setTime(end); 
			        c.set(c.HOUR_OF_DAY, 23);
			        c.set(c.MINUTE, 59);
			        c.set(c.SECOND, 59);
				    Date jstrrow = c.getTime(); 
					sqmy.setStart_date(start);
					sqmy.setEnd_date(jstrrow);//设置结束时间为23：59：59
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				sqmy.setDc_num(dc_num);
				sqmy.setStatus(status);
				sqmy.setFbr_id(accCode);
				sqmy.setFbr_name(name);
				Date createDate = new Date();
				sqmy.setCreate_date(createDate);
				//获取系统当前时间
				Date date=new Date();
				//获取页面设置的结束日期
				Date jstime;
				try {
					jstime = sdf.parse(end_date);
					Calendar c = Calendar.getInstance();  
			        c.setTime(jstime); 
			        c.set(c.HOUR_OF_DAY, 23);
			        c.set(c.MINUTE, 59);
			        c.set(c.SECOND, 59);
				    Date jstrrow = c.getTime(); 
				    if(jstrrow.before(date)){
						//已结束
						sqmy.setIs_over("1"); //社情民意状态 1已结束
					}else if(date.before(jstrrow)){
						//未结束
						sqmy.setIs_over("0"); //社情民意状态 0未结束
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				sqmyDao.save(sqmy);
				
				//添加社情民意网格员表
				Map<String, String> map = new HashMap<String, String>();
				map.put("commId", sqid);
				List<SysAccCountLazy> wgyList = findWgyList(map);
				
				for (int i = 0; i < wgyList.size(); i++) {
					SysAccCountLazy tempAcc = wgyList.get(i);
					ShglSqmyWgy wgyInfo =  new ShglSqmyWgy();
					wgyInfo.setSqmyInfo(sqmy);
					wgyInfo.setWgyId(tempAcc.getAccCode());
					wgyInfo.setWgyName(tempAcc.getName());
					wgyInfo.setFwzId(tempAcc.getSsId());
					wgyInfo.setDcCount(0);
					wgyInfo.setWcl("0");
					wgyInfo.setIsSee("2");
					sqmyDao.save(wgyInfo);
				}
				//保存附件
				 if(null != fName && !"".equals(fName)){
						String[] path = fName.split(">");
						for(String p : path){
							if(!p.equals("")){
								JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
								String newFileName = jsStr.getString("newName");
								String oldFileName = jsStr.getString("oldName");
								String fileType = jsStr.getString("type").toLowerCase();
								ShglSqmyAttrsEntity ent = new ShglSqmyAttrsEntity();
								ent.setNewFilename(newFileName);
								ent.setSqmy_id(sqmy.getId());
								ent.setOldFilename(oldFileName);
								ent.setFileType(fileType);
								sqmyDao.save(ent);
							}
						}
					}
	}
	
	@Override
	public void update(String id, String status, String sqid, String accCode,
			String name, String title, String content, int dc_num,
			String start_date, String end_date,String fName) {
		    ShglSqmyEntity sqmy = getById(id);
				sqmy.setTitle(title);
				sqmy.setContent(content);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				Date start;
				Date end;
				try {
					start = sdf.parse(start_date);
					end = sdf.parse(end_date);
					Calendar c = Calendar.getInstance();  
			        c.setTime(end); 
			        c.set(c.HOUR_OF_DAY, 23);
			        c.set(c.MINUTE, 59);
			        c.set(c.SECOND, 59);
				    Date jstrrow = c.getTime(); 
					sqmy.setStart_date(start);
					sqmy.setEnd_date(jstrrow);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				sqmy.setDc_num(dc_num);
				sqmy.setStatus(status);
				//获取系统当前时间
				Date date=new Date();
				//获取页面设置的结束日期
				Date jstime;
				try {
					jstime = sdf.parse(end_date);
					Calendar c = Calendar.getInstance();  
					c.setTime(jstime); 
					c.set(c.HOUR_OF_DAY, 23);
					c.set(c.MINUTE, 59);
					c.set(c.SECOND, 59);
					Date jstrrow = c.getTime(); 
					if(jstrrow.before(date)){
						//已结束
						sqmy.setIs_over("1"); //社情民意状态 1已结束
					}else if(date.before(jstrrow)){
						//未结束
						sqmy.setIs_over("0"); //社情民意状态 0未结束
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				sqmy.setFbr_id(accCode);
				sqmy.setFbr_name(name);
				sqmyDao.saveOrUpdate(sqmy);
				if(null != fName && !"".equals(fName)){
					String[] path = fName.split(">");
					for(String p : path){
						if(!p.equals("")){
							JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
							String newFileName = jsStr.getString("newName");
							String oldFileName = jsStr.getString("oldName");
							String fileType = jsStr.getString("type").toLowerCase();
							ShglSqmyAttrsEntity ent = new ShglSqmyAttrsEntity();
							ent.setNewFilename(newFileName);
							ent.setSqmy_id(sqmy.getId());
							ent.setOldFilename(oldFileName);
							ent.setFileType(fileType);
							sqmyDao.save(ent);
						}
					}
				}
	}
	
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			sqmyDao.deleteById(ShglSqmyEntity.class, id[i]);
		}		
	}

	@Override
	public Pagenate<SysAccCountLazy> findBysqid(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return sqmyDao.finbysqid(pageNumber,pagesize,map);
	}

	@Override
	public void Xg(String id, String is_over) {
		 ShglSqmyEntity sqmy = getById(id);
		 sqmy.setIs_over(is_over);
		 BeanUtils.copyProperties(sqmy,new String[]{"sqmy","title","content","start_date","end_date",
				 "dc_num","status","fbr_id","fbr_name","create_date","sqmyzh"});
		 sqmyDao.saveOrUpdate(sqmy);
	}

	@Override
	public List<ShglSqmyZhEntity> getwgylist(String id, String wgyid) {
		return sqmyDao.getwgylist(id,wgyid);
	}

	/**
	 * 住户列表
	 */
	@Override
	public Pagenate<ShglSqmyZhEntity> findBywgyzh(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return sqmyDao.findBywgyzh(pageNumber,pagesize,map);
	}

	@Override
	public List<ShglSqmyEntity> getSqmyContent() {
		return sqmyDao.getSqmyContent();
	}

	@Override
	public List<ShglSqmyZhEntity> getwgylist(String sqId, String oneId, String sqmyId) {
		return sqmyDao.getwgylist(sqId,oneId,sqmyId);
	}

	@Override
	public List<ShglInmateEntity> findAllinmate(Map<String, String> paramMap) {
		return sqmyDao.findAllinmate(paramMap);
	}

	
	/**
	 * app_保存住户信息
	 */
	@Override 
	public void saveZh(ShglSqmyZhEntity zh, String accCode, String name) {//
		
		zh.setXq_id(zh.getXq_id());
		zh.setLy_id(zh.getLy_id());
		zh.setUnit_num(zh.getUnit_num());
		zh.setRoom_num(zh.getRoom_num());
		sqmyDao.save(zh);
	}

	@Override
	public ShglSqmyZhEntity findById(String id) {
		return sqmyDao.findById(ShglSqmyZhEntity.class, id);
	}

	
	@Override
	public Pagenate<SysAccCountLazy> findWgyBysqid(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return sqmyDao.findWgyBysqid(pageNumber,pagesize,map);
	}

	@Override
	public void delFj(String id) {
		sqmyDao.deleteById(ShglSqmyAttrsEntity.class, id);			
	}

	@Override
	public List<SysAccCountLazy> findWgyList(Map<String, String> map) {
		return sqmyDao.findWgyList(map);
	}

	@Override
	public Pagenate<ShglSqmyWgy> findSqmyWgyListByParam(Integer pageNumber,
			int pagesize, Map<String, String> map) {

		return sqmyDao.findSqmyWgyListByParam(pageNumber, pagesize, map);
	}

	@Override
	public List<ShglSqmyDc> findAllDc(Map<String, String> map) {
		return sqmyDao.findAllDc(map);
	}

	@Override
	public List<ShglSqmyWgy> findSqmyWgyList(Map<String, String> map) {
		return sqmyDao.findSqmyWgyList(map);
	}

	
	
}
