/**
 * 
 */
package com.jcwx.service.shzz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.DtbbYjEntity;
import com.jcwx.utils.Pagenate;

/**
 * @author m
 *
 */
public interface DtbbService {
	
	
	 Pagenate<DtbbEntity> getDtbbContent(Integer pageNumber, int pageSize, Map<String, String> map);

	 DtbbEntity findById(String id);
	
	 void update(DtbbEntity dtbb, String id, String fName);

	 void save(DtbbEntity dtbb, String fName);



	 void del(String ids);

	
	 void shZxStatus(DtbbEntity dtbb);
	 void delFj(String id);

	/**
	 * @param id
	 */
	 void updateshStatus(String id);

	/**
	 * @param id
	 * @return
	 */
	 DtbbEntity getById(String id);

	/**
	 * 时间倒叙查询动态播报信息集合
	 * @return
	 */
	 List<DtbbEntity> getDtbbContentList();

	/**
	 * @param id
	 * @param shStatus
	 * @param sysAccCount
	 */
	void doAuditing(String id, String status, SysAccCount sysAccCount);
	
	/**
	  * 根据动态播报id查询留言
	  * @param id
	  * @return
	  */
	 public List<DtbbYjEntity> findByDtId(String id);
	 
	 /**
	  * 保存留言
	  * @param d
	  */
	 public void saveLy(DtbbYjEntity d);
	 
	 /**
	  * 留言分页查询
	  * @param pageNumber
	  * @param pageSize
	  * @param map
	  * @return
	  */
	 public Pagenate<DtbbYjEntity> getByDtId(Integer pageNumber, int pageSize, Map<String, String> map);
	 /**
	  * 留言分页查询
	  * @author 李伟
	  * @time 2017年11月24日下午2:21:57
	  * @param pageNumber
	  * @param pagesize
	  * @param map
	  * @return
	  */
	Pagenate<DtbbYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	/**
	 * 留言删除
	 * @author 李伟
	 * @time 2017年11月24日下午2:31:59
	 * @param id
	 */
	void delLy(String id);
	/**
	 * 审核留言
	 * @author 李伟
	 * @time 2017年11月24日下午2:33:23
	 * @param id
	 */
	void successLy(String id);

}
