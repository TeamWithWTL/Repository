package com.jcwx.service.xtbg;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.xtbg.MeetingEntity;
import com.jcwx.utils.Pagenate;

/**
 * 会议管理 service接口
 * @author 李伟
 * @time 2017年10月29日上午2:31:10
 */
public interface MeetingService {
	/**
	 * 主页加载，分页查询
	 * @author 李伟
	 * @time 2017年10月29日上午10:43:31
	 * @param pageNumber
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Pagenate<MeetingEntity> findByPage(Integer pageNumber, int pageSize, Map<String, String> map);
	/**
	 * 会议id查询
	 * @author 李伟
	 * @time 2017年10月30日上午9:15:48
	 * @param id
	 * @return
	 */
	MeetingEntity findById(String id);
	/**
	 * 会议修改
	 * @author 李伟
	 * @param sub 
	 * @time 2017年10月30日上午9:33:28
	 * @param acc
	 * @param meetingEntity
	 * @param ids 
	 * @param fName 
	 */
	void update(String sub, SysAccCount acc, MeetingEntity meetingEntity, String ids, String fName);
	/**
	 * 会议添加
	 * @author 李伟
	 * @param sub 
	 * @time 2017年10月30日上午9:34:12
	 * @param acc
	 * @param meetingEntity
	 */
	void saves(String sub, SysAccCount acc, MeetingEntity meetingEntity,String ids);
	/**
	 * id查询
	 * @author 李伟
	 * @time 2017年10月30日下午4:56:57
	 * @param ids
	 */
	List<SysAccCountLazy> selectName(String ids);
	/**
	 * 删除
	 * @author 李伟
	 * @time 2017年10月31日上午9:29:28
	 * @param ids
	 */
	void del(String ids);
	/**
	 * 发布
	 * @author 李伟
	 * @time 2017年11月3日上午12:33:38
	 * @param id
	 */
	void issue(String id);
	/**
	 * 保存附件
	 * @author 李伟
	 * @time 2017年11月22日下午2:34:00
	 * @param id
	 * @param fName
	 * @param acc
	 */
	void doAcc(String id, String fName, SysAccCount acc);


}
