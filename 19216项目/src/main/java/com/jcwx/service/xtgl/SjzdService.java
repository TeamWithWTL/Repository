package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.Pagenate;

/**
 * 数据字典
 * @author Wjx
 *
 */
public interface SjzdService {
	/**
	 * 分页查询 -- 数据字典
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public	Pagenate<SysParam> findByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 根据code查询数据字典
	 * @param code
	 * @return
	 */
	public SysParam findByCode(String code);
	
	/**
	 * 添加保存
	 * @param s
	 */
	public void save(SysParam s);
	
	/**
	 * 修改保存
	 * @param s
	 */
	public void update(SysParam s);
	
	/**
	 * 删除数据字典
	 * @param ids
	 */
	public void del(String ids);
	
	/**
	 * 分页查询 -- 数据字典明细列表
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public	Pagenate<SysParamDesc> findDescByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 保存参数项
	 * @param sys
	 */
	public void saveDesc(SysParamDesc sys);
	
	/**
	 * 修改参数项
	 * @param sys
	 */
	public void updateDesc(SysParamDesc sys);
	
	/**
	 * 删除参数项
	 * @param ids
	 */
	public void delDesc(String ids);
	
	/**
	 * 根据参数id查参数项
	 * @param id
	 * @return
	 */
	public SysParamDesc findDescById(String id);
	
	/**
	 * 根据参数项编码查询
	 * @param code -------参数项编码
	 * @param sjzdCode --参数编号
	 * @return
	 */
	public SysParamDesc findDescByCode(String code, String sjzdCode);
	
	/**
	 * 根据code查询数据字典明细列表
	 * @param code
	 * @return
	 */
	public List<SysParamDesc> findByPCode(String code);
	
	/**
	 * 根据code查询数据字典明细列表
	 * @param code 系统参数编码
	 * @return [{code:code,name:name}]
	 */
	public List<Map<String, String>> findMapListByPCode(String code);
	
	/**
	 * 根据code查询数据字典明细列表
	 * @param code 系统参数编码
	 * @return {itemCode:itemName}
	 */
	public Map<String, String> findMapByPCode(String code);
	
	/**
	 * 根据code 查询参数明细表>参数项编码
	 * @param code
	 * @return
	 */
	public List<String> findAllItemCode(String code);
	
	/**
	 * 根据code获取数据字典中的一级社区服务列表
	 * @param code
	 * @return
	 */
	public List<SysParam> getParamList(String code);
	
	/**
	 * 获取二级社区服务列表
	 * @param code
	 * @return
	 */
	public List<SysParamDesc> getParamDescList(String code);
	
	/**
	 * 根据code获取一级社区服务下的对应二级社区服务列表
	 * @param code
	 * @return
	 */
	public List<SysParamDesc> getEjFwList(String code);
	
	/**
	 * @param code
	 * @return
	 */
	public List<SysParamDesc> getEjFwList1(String code);
	
	public List<SysParam> getParamDescList1(String code);
	
	/**
	 * 查询所有相同指定value1 的LIST
	 * @author 李伟
	 * @time 2017年11月9日上午10:43:15
	 * @param string
	 * @return
	 */
	public List<SysParam> findByValue1(String string);
	
	/**
	 * 根据item_name查询
	 * @param id
	 * @return
	 */
	public SysParamDesc finDescByItemName(String id);
	
	/**
	 * 根据code查询value1
	 * @param string
	 * @return
	 */
	public Map<String, String> findMapByPCode01(String string);
	
}
