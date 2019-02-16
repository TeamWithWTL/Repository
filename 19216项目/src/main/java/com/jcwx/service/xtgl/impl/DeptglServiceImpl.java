package com.jcwx.service.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.DeptglDao;
import com.jcwx.entity.pub.SysDepartment;
import com.jcwx.service.xtgl.DeptglService;
import com.jcwx.utils.Pagenate;

@Service("deptglService")
public class DeptglServiceImpl implements DeptglService {
	
	@Autowired
	private DeptglDao deptglDao;

	@Override
	public Pagenate<SysDepartment> findDepartmentByPage(int pageNumber, int pageSize, Map<String, String> paramsMap) {
		// TODO Auto-generated method stub
		return deptglDao.findDepartmentByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public SysDepartment findById(String id) {
		// TODO Auto-generated method stub
		return deptglDao.findById(SysDepartment.class, id);
	}

	@Override
	public void updateOs(SysDepartment os) {
		// TODO Auto-generated method stub
		SysDepartment _os = findById(os.getDeptId());
		_os.setDeptName(os.getDeptName());
		deptglDao.saveOrUpdate(_os);
	}

	@Override
	public List<SysDepartment> findByParentId(String pid) {
		// TODO Auto-generated method stub
		return deptglDao.findByParentId(pid);
	}

	@Override
	public void delDeptByIds(String deptIds) {
		// TODO Auto-generated method stub
		String[] deptIdArr = deptIds.split(";");
		for(String deptId : deptIdArr){
			if(!deptId.equals("")){
				List<SysDepartment> osList = findByParentId(deptId);
				if(osList.isEmpty()){
					//不存在下级，直接删除
					deptglDao.deleteById(SysDepartment.class, deptId);
				}
			}
		}
	}

	@Override
	public void saveDept(String deptStr) {
		// TODO Auto-generated method stub
		int orderNo = 1;	// 排序
		String[] deptArr = deptStr.split(";");
		for(String dept : deptArr){
			if(!dept.equals("")){
				String id = dept.split(",")[0];
				String pid = dept.split(",")[1];
				String name = dept.split(",")[2];
				if(id.equals("root")){
					continue;
				}else{
					if(pid.equals("root")){
						pid = null;
					}
				}
				SysDepartment _os = findById(id);
				if(_os == null){
					SysDepartment os = new SysDepartment();
					os.setDeptId(id);
					os.setDeptName(name);
					os.setParentId(pid);
					os.setOrderNo(orderNo);
					deptglDao.save(os);
				}else{
					_os.setDeptName(name);
					_os.setOrderNo(orderNo);
					deptglDao.saveOrUpdate(_os);
				}
				orderNo ++;
			}
		}
	}
	
}
