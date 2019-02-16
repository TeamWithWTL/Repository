package com.jcwx.service.dflz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.ComplainDao;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.service.dflz.ComplainService;
import com.jcwx.utils.Pagenate;
/**
 * 投诉service 接口实现
 * @author 李伟
 * @time 2017年10月26日上午10:44:47
 */
@Service
public class ComplainServiceImpl implements ComplainService{
	@Autowired
	ComplainDao complainDao;
	/**
	 * 主页加载
	 */
	@Override
	public Pagenate<ComplainEntity> findByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return complainDao.findByPage(pageNumber,pagesize,map);
	}
	/**
	 * id查询
	 */
	@Override
	public ComplainEntity findById(String id) {
		return complainDao.findById(ComplainEntity.class, id);
	}
	/**
	 * 删除
	 */
	@Override
	public void del(String ids) {
		String[] split = ids.split(";");
		for (String id : split) {
			complainDao.deleteById(ComplainEntity.class, id);
		}
	}
	/**
	 * 审核
	 */
	@Override
	public void audit(String id, String status, SysAccCount acc) {
		String accCode = acc.getAccCode();
		String name = acc.getName();
		ComplainEntity complainEntity = complainDao.findById(ComplainEntity.class, id);
		complainEntity.setSh_status(status);//修改审核状态
		complainEntity.setSh_user_id(accCode);
		complainEntity.setSh_user_name(name);
		complainDao.saveOrUpdate(complainEntity);
	}
	@Override
	public SysAccRole findByRole(String accCode) {
		return complainDao.findById(SysAccRole.class, accCode);
	}
	@Override
	public Pagenate<ComplainHandleEntity> findComPHandByPage(Integer pageNumber, int pagesize,
			Map<String, String> map) {
		return complainDao.findComPHandByPage(pageNumber, pagesize, map);
	}
	
	/**
	 * 查询二级数据字典
	 * @author 李伟
	 * @time 2017年11月15日上午8:39:19
	 * @param jb_typeId
	 * @return
	 */
	@Override
	public SysParamDesc findParamDescById(String jb_typeId) {
		return complainDao.findParamDescById( jb_typeId);
	}
	@Override
	public SysParam findParamById(String code) {
		return complainDao.findById(SysParam.class, code);
	}
	/**
	 * 查询所有党风廉政人员
	 */
	@Override
	public List<SysAccRole> findByDflzYhList(String string) {
		return complainDao.findByDflzYhList(string);
	}
	
	/**
	 * 查询最后处理情况
	 */
	@Override
	public ComplainHandleEntity findYjById(String id) {
		return complainDao.findYjById(id);
	}
	
	/**
	 * accCode查询处理人
	 */
	@Override
	public SysAccCount findByAccCode(String acc_code) {
		return complainDao.findById(SysAccCount.class, acc_code);
	}
	
}
