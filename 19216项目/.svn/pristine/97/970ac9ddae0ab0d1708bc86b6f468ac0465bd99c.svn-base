package com.jcwx.service.dflz;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.Pagenate;

/**
 * 投诉service 接口
 * @author 李伟
 * @time 2017年10月26日上午10:38:06
 */
public interface ComplainService{
	/**
	 * 主页加载
	 * @author 李伟
	 * @time 2017年10月26日下午1:32:35
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ComplainEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 投诉处理表为主，多对一查询
	 * @author 李伟
	 * @time 2017年11月14日下午2:19:24
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ComplainHandleEntity>findComPHandByPage(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * id查询
	 * @author 李伟
	 * @time 2017年10月26日下午2:02:45
	 * @param id
	 * @return
	 */
	ComplainEntity findById(String id);
	/**
	 * 删除
	 * @author 李伟
	 * @time 2017年10月26日下午2:06:50
	 * @param ids
	 */
	void del(String ids);
	/**
	 * 审核
	 * @author 李伟
	 * @time 2017年10月26日下午2:10:50
	 * @param id
	 * @param status
	 * @param acc
	 */
	void audit(String id, String status, SysAccCount acc);
	/**
	 * 查询角色身份
	 * @author 李伟
	 * @time 2017年11月10日上午9:12:53
	 * @param accCode
	 * @return 
	 */
	SysAccRole findByRole(String accCode);
	/**
	 * 查询二级数据字典
	 * @author 李伟
	 * @time 2017年11月15日上午8:39:19
	 * @param jb_typeId
	 * @return
	 */
	SysParamDesc findParamDescById(String jb_typeId);
	/**
	 * id查询一级数据字典
	 * @author 李伟
	 * @time 2017年11月15日上午8:47:30
	 * @param code
	 * @return
	 */
	SysParam findParamById(String code);
	/**
	 * 查询所有党风廉政人员
	 * @author 李伟
	 * @time 2017年11月21日上午11:13:49
	 * @param string
	 * @return
	 */
	List<SysAccRole> findByDflzYhList(String string);
	/**
	 * 查询最后处理情况
	 * @author 李伟
	 * @time 2017年11月21日下午1:53:31
	 * @param id
	 * @return
	 */
	ComplainHandleEntity findYjById(String id);
	/**
	 * accCode查询处理人
	 * @author 李伟
	 * @time 2017年12月8日上午8:25:47
	 * @param acc_code
	 * @return
	 */
	SysAccCount findByAccCode(String acc_code);
	
}
