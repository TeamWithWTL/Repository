package com.jcwx.service.xtbg.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtbg.MeetingStaffDao;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
import com.jcwx.service.xtbg.MeetingStaffService;
/**
 * 参会人员service接口实现类
 * @author 李伟
 * @time 2017年10月29日上午2:35:02
 */
@Service
public class MeetingStaffServiceImpl implements MeetingStaffService{
	@Autowired
	private MeetingStaffDao meetingStaffDao;
	/**
	 * id删除
	 */
	@Override
	public String delStaff(String staffId,String id) {
		MeetingStaffEntity meetingStaffEntity=(MeetingStaffEntity) meetingStaffDao.findStaff(staffId,id);
		MeetingStaffEntity meetingStaffEn;
		try {
			meetingStaffEn = meetingStaffDao.findById(MeetingStaffEntity.class, meetingStaffEntity.getId());
		} catch (Exception e) {
			return "succes";
		}
		
	
		meetingStaffDao.delete(meetingStaffEn);
		
		return "success";
	}
	
	

}
