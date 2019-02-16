package com.jcwx.service.xtbg.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.DeptPersonDao;
import com.jcwx.dao.xtbg.MeetingAcceDao;
import com.jcwx.dao.xtbg.MeetingDao;
import com.jcwx.dao.xtbg.MeetingStaffDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.xtbg.MeetingAcceEntity;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
import com.jcwx.service.xtbg.MeetingService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;
/**
 * 会议管理service接口实现类
 * @author 李伟
 * @time 2017年10月29日上午2:32:22
 */
@Service
public class MeetingServiceImpl implements MeetingService{
	
	@Autowired
	private MeetingDao meetingDao;

	@Autowired
	private MeetingStaffDao meetingStaffDao;
	@Autowired
	private MeetingAcceDao meetingAcceDao;
	@Autowired
	private DeptPersonDao deptPersonDao;
	/**
	 * 主页加载，分业查询
	 */
	@Override
	public Pagenate<MeetingEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map) {
		return meetingDao.findByPage(pageNumber, pageSize, map);
	}
	/**
	 * 会议id查询
	 */
	@Override
	public MeetingEntity findById(String id) {
		return meetingDao.findById(MeetingEntity.class, id);
	}
	/**
	 * 会议修改
	 */
	@Override
	public void update(String sub,SysAccCount acc, MeetingEntity meetingEntity,String ids, String fName) {
		String accCode = acc.getAccCode();
		HashSet<String> hashSet = new HashSet<String>();
		if (ids==null||"".equals(ids)) {
			ids=accCode;//会议发起人，默认参加会议
		}else{
			ids=ids+accCode;//会议发起人，默认参加会议
		}
		if (null !=ids && !"".equals(ids)) {
			String[] split = ids.split(",");
			//去重复
			for (String string : split) {
				if (string!=null&&!"".equals(string)) {
					hashSet.add(string);
				}
			}
		}
		
		MeetingEntity meetEntity = meetingDao.findById(MeetingEntity.class,meetingEntity.getId());
		if (null !=meetingEntity.getTitle()&& !"".equals(meetingEntity.getTitle())) {
			meetEntity.setTitle(StringUtil.filterchart(meetingEntity.getTitle()));
		}
		if (null !=meetingEntity.getVenue()&& !"".equals(meetingEntity.getVenue())) {
			meetEntity.setVenue(StringUtil.filterchart(meetingEntity.getVenue()));
		}
		
		if (null !=meetingEntity.getContent()&& !"".equals(meetingEntity.getContent())) {
			meetEntity.setContent(meetingEntity.getContent());
		}
		if (null !=meetingEntity.getStart_date()&& !"".equals(meetingEntity.getStart_date())) {
			meetEntity.setStart_date(meetingEntity.getStart_date());
		}
		if (null !=meetingEntity.getEnd_date()&& !"".equals(meetingEntity.getEnd_date())) {
			meetEntity.setEnd_date(meetingEntity.getEnd_date());
		}
		if (null !=sub&&"".equals(sub)) {
			meetEntity.setTj_status(sub);
		}
		
		meetingDao.saveOrUpdate(meetEntity);
		//移除历史参会人员
		List<MeetingStaffEntity> findByHyglId = meetingStaffDao.findByHyglId(meetEntity.getId());
		for (MeetingStaffEntity meetingStaffEntity : findByHyglId) {
			meetingStaffDao.delete(meetingStaffEntity);
		}
		//存参会人员
		for (String string : hashSet) {
			MeetingStaffEntity meetingStaffEntity = new MeetingStaffEntity();
			//判断比人员在不在存在，存在，就不再存入数据库
			
			if (meetingStaffDao.findStaff(string, meetingEntity.getId())==null) {
				meetingStaffEntity.setAcc_code(string);
				meetingStaffEntity.setHygl_id(meetingEntity.getId());
				meetingStaffDao.saveOrUpdate(meetingStaffEntity);
			}
		}
		//存附件
		if (null!=fName && !("".equals(fName))) {
			String[] fNames = fName.split(">");
			for (String f : fNames) {
				if (!"".equals(f)) {
					JSONObject fromObject = JSONObject.fromObject(f);
					String newName = fromObject.getString("newName");
					String oldName = fromObject.getString("oldName");
					String type = fromObject.getString("type");
					MeetingAcceEntity meetingAcceEntity = new MeetingAcceEntity();
					meetingAcceEntity.setHygl_id(meetingEntity.getId());
					meetingAcceEntity.setOld_fileName(oldName);
					meetingAcceEntity.setScr_id(accCode);
					meetingAcceEntity.setNew_fileName(newName);
					meetingAcceEntity.setFile_type(type);
					meetingAcceDao.saveOrUpdate(meetingAcceEntity);
				}
			}
		}
		
	}
	/**
	 * 会议添加
	 */
	@Override
	public void saves(String sub,SysAccCount acc, MeetingEntity meetingEntity,String ids) {
		String accCode = acc.getAccCode();
		String name = acc.getName();
		HashSet<String> hashSet = new HashSet<String>();
		ids=ids+accCode;//发起人，默认加入参会列表 
		String[] split = ids.split(",");
		for (String string : split) {
			hashSet.add(string);
		}
		meetingEntity.setTitle(StringUtil.filterchart(meetingEntity.getTitle()));
		meetingEntity.setVenue(StringUtil.filterchart(meetingEntity.getVenue()));
		//meetingEntity.setContent(HtmlUtil.htmlRemoveTag(meetingEntity.getContent()));
		meetingEntity.setCreate_date(new Date());
		meetingEntity.setUser_id(accCode);
		meetingEntity.setUser_name(name);
		meetingEntity.setTj_status(sub);
		meetingDao.save(meetingEntity);
	
		for (String id : hashSet) {
			MeetingStaffEntity meetingStaffEntity = new MeetingStaffEntity();
			meetingStaffEntity.setAcc_code(id);
			meetingStaffEntity.setHygl_id(meetingEntity.getId());
			meetingStaffDao.save(meetingStaffEntity);
		}
	}
	/**
	 * id查询
	 */
	
	@Override
	public List<SysAccCountLazy> selectName(String ids) {
		String[] id = ids.split(",");
		HashSet<String> hashSet = new HashSet<String>();
		List<SysAccCountLazy> names=new ArrayList<SysAccCountLazy>();
		for (String string : id) {
			hashSet.add(string);
		}
		for (String string : hashSet) {
			SysAccCountLazy sysAccCountLazy = deptPersonDao.findById(SysAccCountLazy.class, string);
			
			names.add(sysAccCountLazy);
		}
		return names;
	}
	/**
	 * 删除
	 */
	@Override
	public void del(String ids) {
		String[] split = ids.split(";");
		for (String id : split) {
			meetingDao.deleteById(MeetingEntity.class, id);
		}
	}
	
	/**
	 * 发布
	 */
	@Override
	public void issue(String id) {
		MeetingEntity meetingEntity = meetingDao.findById(MeetingEntity.class, id);
		meetingEntity.setTj_status("1");//1表示为发布状态
		meetingDao.save(meetingEntity);
	}
	
	/**
	 * 保存附件
	 */
	@Override
	public void doAcc(String id, String fName, SysAccCount acc) {
		String accCode = acc.getAccCode();
		//存附件
				if (null!=fName && !("".equals(fName))) {
					String[] fNames = fName.split(">");
					for (String f : fNames) {
						if (!"".equals(f)) {
							JSONObject fromObject = JSONObject.fromObject(f);
							String newName = fromObject.getString("newName");
							String oldName = fromObject.getString("oldName");
							String type = fromObject.getString("type");
							MeetingAcceEntity meetingAcceEntity = new MeetingAcceEntity();
							meetingAcceEntity.setHygl_id(id);
							meetingAcceEntity.setOld_fileName(oldName);
							meetingAcceEntity.setScr_id(accCode);
							meetingAcceEntity.setNew_fileName(newName);
							meetingAcceEntity.setFile_type(type);
							meetingAcceDao.saveOrUpdate(meetingAcceEntity);
						}
					}
				}
	}
	
}
