package com.jcwx.service.dflz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.dflz.ComplainDao;
import com.jcwx.dao.dflz.ComplainHandleDao;
import com.jcwx.entity.dflz.ComplainEntity;
import com.jcwx.entity.dflz.ComplainHandleEntity;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.service.dflz.ComplainHandleService;
/**
 * 投诉处理service 接口实现
 * @author 李伟
 * @time 2017年10月26日上午10:46:11
 */
@Service
public class ComplainHandleServiceImpl implements ComplainHandleService{
	@Autowired
	private ComplainDao complainDao;

	@Autowired
	private ComplainHandleDao complainHandleDao;
	/**
	 * 转发处理
	 */
	@Override
	public void saveOrUpdate(String pid, String cid,SysAccCount acc) {
		//添加派发人
		ComplainEntity complainEntity = complainDao.findById(ComplainEntity.class, cid);
		//complainEntity.setCl_name(acc.getName());
		//complainEntity.setCl_name_id(acc.getAccCode());
		//complainEntity.setCl_time(new Date());
		//complainDao.saveOrUpdate(complainEntity);
		//查询投诉举报处理记录 并修改派发状态
		String accCode = acc.getAccCode();
		Map<String, String> map = new HashMap<String, String>();
		map.put("accCode", accCode);
		map.put("tsjb_id", cid);
		ComplainHandleEntity comHandleEntity = complainHandleDao.finComHandle(map);
		comHandleEntity.setCl_status("1");//改为已处理状态
		//comHandleEntity.setPf_status("2");//改为派发相关人员状态
		complainHandleDao.save(comHandleEntity);
		//创建新的处理记录，并指给相关人员处理
		//SysAccCountLazy sysAccCountLazy = complainHandleDao.findById(SysAccCountLazy.class, pid);
		ComplainHandleEntity complainHandleEntity = new ComplainHandleEntity();
		complainHandleEntity.setComplainEntity(complainEntity);;//父id
		complainHandleEntity.setAcc_code(pid);
		//complainHandleEntity.setContent("已派发给"+sysAccCountLazy.getName()+"处理");
		//complainHandleEntity.setHandle_date(new Date());
		complainHandleEntity.setCl_status("2");//2为未处理状态
		complainHandleEntity.setPf_status("2");//2为已转发给当事人处理
		complainHandleDao.save(complainHandleEntity);
	}
	/**
	 * 确认处理结果
	 */
	@Override
	public void handle(Map<String, String> map) {
		ComplainHandleEntity finComHandle = complainHandleDao.finComHandle(map);
		String content = map.get("content");
		finComHandle.setCl_status("1");//改为已处理状态
		finComHandle.setContent(content);//处理意见
		finComHandle.setHandle_date(new Date());//处理时间
		complainHandleDao.saveOrUpdate(finComHandle);
		
	}
	

}
