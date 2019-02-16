package com.jcwx.service.dflz.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.AppComplainDao;
import com.jcwx.entity.dflz.CompAcceEntity;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.service.dflz.AppComplainService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;
/**
 * app投诉举报service接口实现
 * @author 李伟
 * @time 2017年11月9日上午8:21:32
 */
@Service("appComplainService")
public class AppComplainServiceImpl implements AppComplainService{
	@Autowired
	private AppComplainDao appComplainDao;

	
	/**
	 * 查询投诉举报信息
	 */
	@Override
	public Pagenate<ComplainEntity> findByPage(Integer pageNum, int pageSize, Map<String, String> hashMap) {
		return appComplainDao.findByPage(pageNum, pageSize, hashMap);
	}
	
	/**
	 * 添加投诉举报
	 */
	@Override
	public void save(SysAccCount sysacc, ComplainEntity complainEntity, String sjlx) {
		String name = sysacc.getName();
		String accCode = sysacc.getAccCode();
		complainEntity.setTitle(StringUtil.filterchart(complainEntity.getTitle()));
		complainEntity.setJb_type(sjlx+","+complainEntity.getJb_type());
		/*if ("1".equals(complainEntity.getIs_nm())) {//判断是否匿名
			complainEntity.setUser_id("匿名");
			complainEntity.setUser_name("匿名");
		}else {*/
			complainEntity.setUser_id(accCode);
			complainEntity.setUser_name(name);
		/*}*/
		List<SysAccCount>sysAccRoleList=appComplainDao.findByRolecode("10");//指定查询角色为党风廉政的所有用户
		List<SysAccCount> rootList = appComplainDao.findByRolecode("99");//查询管理员list
		
		//随机指派党风廉政工作人员
		String dflzCode = null;
		if (sysAccRoleList.size()>0) {
			Random random = new Random();
			int index = random.nextInt(sysAccRoleList.size());//随机出党风廉政解色中一个用户list的索引值
			dflzCode = sysAccRoleList.get(index).getAccCode();
		}else if (rootList.size()>0) {//如果没有党风廉政角色，指派给管理员
			Random random = new Random();
			int index = random.nextInt(rootList.size());//随机出党风廉政解色中一个用户list的索引值
			dflzCode = rootList.get(index).getAccCode();
		}
		 //根据用户id查询用户名
		// String dfzlName = appComplainDao.findById(SysAccCountLazy.class, dflzCode).getName();
		complainEntity.setCreate_time(new Date());
		complainEntity.setSh_status("0");//待审核状态
		appComplainDao.save(complainEntity);
		
		ComplainHandleEntity complainHandleEntity=new ComplainHandleEntity();
		complainHandleEntity.setComplainEntity(complainEntity);
		complainHandleEntity.setAcc_code(dflzCode);
		complainHandleEntity.setCreate_date(new Date());
		complainHandleEntity.setCl_status("2");//初始化为未处理状态
		complainHandleEntity.setPf_status("1");//初始化为未转派前，党风廉政人员处理状态
		appComplainDao.save(complainHandleEntity);//保存处理状态
	}
	/**
	 * app添加修改投诉附件
	 */
	@Override
	public void saveOrUpdateCompArrt(CompAcceEntity compAcceEntity) {
		appComplainDao.saveOrUpdateCompArrt(compAcceEntity);
	}

}
