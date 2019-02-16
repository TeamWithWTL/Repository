package com.jcwx.service.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.ZzjgglDao;
import com.jcwx.entity.pub.OrganizationalStructure;
import com.jcwx.service.xtgl.ZzjgglService;
import com.jcwx.utils.Pagenate;

/**
* @author MaBo
* 2017年7月25日
* 组织架构管理
*/
@Service("zzjgglService")
public class ZzjgglServiceImpl implements ZzjgglService {

	@Autowired
	private ZzjgglDao zzjgglDao;
	
	@Override
	public Pagenate<OrganizationalStructure> findZzjgByPage(int pageNumber, int pageSize,
			Map<String, String> paramsMap) {
		return zzjgglDao.findZzjgByPage(pageNumber, pageSize, paramsMap);
	}

	@Override
	public OrganizationalStructure findById(String id) {
		return zzjgglDao.findById(OrganizationalStructure.class, id);
	}

	@Override
	public void updateOs(OrganizationalStructure os) {
		OrganizationalStructure _os = findById(os.getOrgId());
		_os.setOrgName(os.getOrgName());
		zzjgglDao.saveOrUpdate(_os);
	}
	
	@Override
	public void delOrgs(String orgCodes) {
		String[] orgCodeArr = orgCodes.split(";");
		for(String orgCode : orgCodeArr){
			if(!orgCode.equals("")){
				List<OrganizationalStructure> osList = findByParentId(orgCode);
				if(osList.isEmpty()){
					// 不存在下级，直接删除
					zzjgglDao.deleteById(OrganizationalStructure.class, orgCode);
				}
			}
		}
	}

	@Override
	public List<OrganizationalStructure> findByParentId(String pid) {
		return zzjgglDao.findByParentId(pid);
	}

	@Override
	public void saveOrg(String orgStr) {
		int orderNo = 1;	// 排序
		String[] orgArr = orgStr.split(";");
		for(String org : orgArr){
			if(!org.equals("")){
				String id = org.split(",")[0];
				String pid = org.split(",")[1];
				String name = org.split(",")[2];
				if(id.equals("root")){
					continue;
				}else{
					if(pid.equals("root")){
						pid = null;
					}
				}
				OrganizationalStructure _os = findById(id);
				if(_os == null){
					OrganizationalStructure os = new OrganizationalStructure();
					os.setOrgId(id);
					os.setOrgName(name);
					os.setParentId(pid);
					os.setOrderNo(orderNo);
					zzjgglDao.save(os);
				}else{
					_os.setOrgName(name);
					_os.setOrderNo(orderNo);
					zzjgglDao.saveOrUpdate(_os);
				}
				orderNo ++;
			}
		}
	}

}
