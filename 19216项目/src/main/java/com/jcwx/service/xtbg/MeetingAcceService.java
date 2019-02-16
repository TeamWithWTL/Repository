package com.jcwx.service.xtbg;

import com.jcwx.entity.xtbg.MeetingAcceEntity;

/**
 * 会议附件service接口
 * @author 李伟
 * @time 2017年10月29日上午2:30:50
 */
public interface MeetingAcceService {
	/**
	 * 删除附件
	 * @author 李伟
	 * @time 2017年11月5日下午2:13:25
	 * @param fjId
	 */
	void delFj(String fjId);
	/**
	 * app会议附件添加修改
	 * @author 李伟
	 * @time 2017年11月18日下午3:46:10
	 * @param meetingAcceEntity
	 */
	void saveOrUpdateMeetingArrt(MeetingAcceEntity meetingAcceEntity);

}
