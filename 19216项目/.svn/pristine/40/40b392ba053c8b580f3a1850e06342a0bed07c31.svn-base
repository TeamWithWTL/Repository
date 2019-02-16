package com.jcwx.service.xtbg.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtbg.MeetingAcceDao;
import com.jcwx.entity.xtbg.MeetingAcceEntity;
import com.jcwx.service.xtbg.MeetingAcceService;
/**
 * 会议附件service接口实现类
 * @author 李伟
 * @time 2017年10月29日上午2:33:46
 */
@Service("meetingAcceServiceImpl")
public class MeetingAcceServiceImpl implements MeetingAcceService{
	@Autowired
	private MeetingAcceDao meetingAcceDao;
	/**
	 * 附件删除
	 */
	@Override
	public void delFj(String fjId) {
		meetingAcceDao.deleteById(MeetingAcceEntity.class, fjId);
	}
	/**
	 * app会议附件添加修改
	 */
	@Override
	public void saveOrUpdateMeetingArrt(MeetingAcceEntity meetingAcceEntity) {
		meetingAcceDao.saveOrUpdateMeetingArrt(meetingAcceEntity);
	}

}
