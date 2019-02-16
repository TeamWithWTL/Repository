package com.jcwx.service.pub.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.DeptPersonDao;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.service.pub.DeptPersonService;
import com.jcwx.utils.Pagenate;

/**
 * 
 * 部门人员管理 ServiceImpl
 * @author zhangkai
 *
 */
@Service("deptPersonService")
public class DeptPersonServiceImpl implements DeptPersonService {
	
	@Autowired
	private DeptPersonDao deptPersonDao;

	@Override
	public Pagenate<SysAccCountLazy> getByPage(int pageNum, int pageSize, Map<String, String> map) {
		// TODO Auto-generated method stub
		return deptPersonDao.findByPage(pageNum, pageSize, map);
	}
	
	@Override
	public Pagenate<SysAccCountLazy> findByPage(int pageNum, int pageSize, Map<String, String> map) {
		return deptPersonDao.findByPage(pageNum, pageSize, map);
	}
	/**
	 * id查询用户
	 */
	@Override
	public SysAccCountLazy findById(String pid) {
		return deptPersonDao.findById(SysAccCountLazy.class, pid);
	}
	
	/*@Override
	public SysDepartmentPersonelEntity getById(String id) {
		// TODO Auto-generated method stub
		return deptPersonDao.findById(SysDepartmentPersonelEntity.class, id);
	}
	
	@Override
	public void save(SysDepartmentPersonelEntity p) {
		// TODO Auto-generated method stub
		p.setName(p.getName());
		p.setMobile(p.getMobile());
		p.setDuty(p.getDuty());
		deptPersonDao.save(p);
	}

	@Override
	public void update(SysDepartmentPersonelEntity p, String id) {
		// TODO Auto-generated method stub
		SysDepartmentPersonelEntity person = getById(id);
		if(person!=null){
			person.setName(p.getName());
			person.setMobile(p.getMobile());
			person.setDuty(p.getDuty());
			//person.setOrgId(p.getOrgId());
			BeanUtils.copyProperties(p, person, new String[]{"orgId"});
			deptPersonDao.saveOrUpdate(person);
		}
	}


	@Override
	public void del(String ids) {
		// TODO Auto-generated method stub
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			deptPersonDao.deleteById(SysDepartmentPersonelEntity.class, id[i]);
		}
	}*/
	
	
}
