package com.jcwx.service.xtgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.utils.Pagenate;

/**
 * 用户管理
 * @author Wjx
 *
 */
public interface YhglService {
	
	/**
	 * 分页查询--用户
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public Pagenate<SysAccCountLazy> findAccByPage(int pageNum, int pageSize, Map<String, String> paramMap);
	
	/**
	 * 添加保存
	 * @param acc
	 */
	public void	saveAcc(SysAccCount acc);
	
	/**
	 * 修改保存
	 * @param acc
	 */
	public void updateAcc(SysAccCountLazy acc);
	
	/**
	 * 根据code查询
	 * @param code
	 * @return
	 */
	public SysAccCountLazy findByCode(String code);
	
	/**
	 * 修改保存
	 * @param ids
	 */
	public void del(String ids);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SysAccCount> findAll();
	
//	/**
//	 * 修改用户明细
//	 * @param acc
//	 */
//	public void updateAccDesc(SysAccDesc acc);
//	
//	/**
//	 * 添加用户明细
//	 * @param acc
//	 */
//	public void saveAccDesc(SysAccDesc acc);
//	
//	/**
//	 * 根据code查询明细
//	 * @param code
//	 * @return
//	 */
//	public SysAccDesc findDescByCode(String code);
	
	/**
	 * 重置密码
	 * @param ids 多个账号之间用;分隔
	 */
	public void resetKey(String ids);
	
	//通过token查询用户
	public SysAccCountLazy findByToken(String tokenId);
	/**
	 * 根据电话查询用户名称
	 * @param rec
	 * @return
	 */
	public String findNameByPhone(String rec);
	/**
	 * 获取登录用户 zhangliming 2017/7/16添加
	 * @param accCode
	 * @return
	 */
	public SysAccCount findSysAccCountByCode(String accCode);
	/**
	 * 添加用户角色
	 * @param sysrole
	 */
	public void saveAccRole(SysAccRole sysrole);
	/**
	 * 修改用户角色
	 * @param sysrole
	 */
	public void updateAccRole(SysAccRole sysrole);
	/**
	 * 根据用户id，角色id删除用户对应的角色
	 * @param role
	 */
	public void deleterole(String role);
    
	/**
	 * 设置用户有效性
	 * @param accCode
	 * @param validFlag
	 */
	public void yhglService(String accCode, String validFlag);
	
	/**
	 * 根据参数查询部门成员
	 * @param map
	 * @return
	 */
	List<SysAccCount> findByParam( Map<String, String> map);
	
	/**
	 * 更新用户积分
	 * @param accCode
	 */
	void updateAccJf(String accCode);
	
	//获取社区ID
	public SysAccCount getSq(String accCode);

	public SysAccCount findByName(String pre_emp);

	 List<SysAccCount> getName(String role_code);

	 List<SysAccCount> getFwzName(String role_code, String sqId);

	 List<SysAccCount> getWgyName(String role_code, String fwzId);

	/**
	 *  修改保存
	 * @param sysa
	 */
	public void updateAccLazy(SysAccCountLazy sysa);

	/**
	 * 查询所有的角色记录
	 * @param accCode
	 * @return
	 */
	public List<SysAccRole> findRole(String accCode);

	/**
	 * 删除所有的角色记录
	 * @param lists
	 */
	public void delRoles(SysAccRole lists);

	/**
	 * 分页查询网格员
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	public Pagenate<SysAccCount> getGridPeople(Integer pageNumber,
			int pagesize, Map<String, String> map);
	
}
