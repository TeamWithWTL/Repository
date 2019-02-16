package com.jcwx.dao.xtbg;

import java.util.List;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.xtbg.MeetingStaffEntity;
/**
 * 参会人员Dao接口
 * @author 李伟
 * @time 2017年10月29日上午2:22:50
 */
public interface MeetingStaffDao extends BaseDao{
	/**
	 * id查询集合
	 * @author 李伟
	 * @time 2017年10月31日上午12:46:08
	 * @param id
	 * @return
	 */
	List<MeetingStaffEntity> findByHyglId(String id);
	/**
	 * 查询参会 人员对像
	 * @author 李伟
	 * @time 2017年10月31日上午10:18:53
	 * @param staffId
	 * @param id 
	 * @return
	 */
	Object findStaff(String staffId, String id);

}
