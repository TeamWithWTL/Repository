package com.jcwx.dao.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.utils.Pagenate;

/**
 * 数据字典
 * @author Wjx
 *
 */
public interface SjzdDao extends BaseDao{
	
	/**
	 * 分页查询 -- 数据字典
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public	Pagenate<SysParam> findByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 根据code查询明细列表
	 * @param code
	 * @return
	 */
	public List<SysParamDesc> findByPCode(String code);
	
	/**
	 * 分页查询 -- 数据字典明细列表
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public	Pagenate<SysParamDesc> findDescByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 根据参数项编码查询
	 * @param code --参数项编号
	 * @param sjzdCode--参数编号
	 * @return
	 */
	public SysParamDesc findDescByCode(String code, String sjzdCode);
	
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

	/**
	 * @param code
	 * @return
	 */
	public List<SysParam> getParamDescList1(String code);
	/**
	 * 查询所有相同指定value1 的LIST
	 * @author 李伟
	 * @time 2017年11月9日上午10:45:16
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
	
}
