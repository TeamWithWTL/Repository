package com.jcwx.service.xtgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.YhglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.pub.SysAccMore;
import com.jcwx.entity.pub.SysAccRole;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

@Service("yhglService")
public class YhglServiceImpl implements YhglService {

	@Autowired
	private YhglDao yhglDao;
	
	@Override
	public Pagenate<SysAccCountLazy> findAccByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
		return yhglDao.findAccByPage(pageNum, pageSize, paramMap);
	}

	@Override
	public void saveAcc(SysAccCount acc) {
		yhglDao.save(acc);
	}

	@Override
	public void updateAcc(SysAccCountLazy acc) {
		yhglDao.saveOrUpdate(acc);
	}

	@Override
	public SysAccCountLazy findByCode(String code) {
		return yhglDao.findById(SysAccCountLazy.class, code);
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			SysAccCountLazy accCountLazy = yhglDao.findById(SysAccCountLazy.class, id[i]);
			String accType = accCountLazy.getZh_type();
			if("3".equals(accType)){
				//根据法人Id查询所在组织
				ZzxxEntity ze = yhglDao.getZzxxByFrId(id[i]);
				//若法人账号删除，则清空所在组织的法人信息
				if(null != ze){
					ze.setFr_id("");
					ze.setFr_name("");
					yhglDao.saveOrUpdate(ze);
				}
				SysAccMore accMore = yhglDao.findSysAccMoreByFrId(id[i]);
				yhglDao.delete(accMore);
			}
			if("2".equals(accType)){
				SysAccMore accMore = yhglDao.findSysAccMoreByFrId(id[i]);
				yhglDao.delete(accMore);
			}
			yhglDao.deleteById(SysAccCount.class, id[i]);
		}
	}

	@Override
	public List<SysAccCount> findAll() {
		return yhglDao.findAll(SysAccCount.class);
	}

//	@Override
//	public void updateAccDesc(SysAccDesc acc) {
//		yhglDao.saveOrUpdate(acc);
//	}
//
//	@Override
//	public void saveAccDesc(SysAccDesc acc) {
//		yhglDao.save(acc);
//	}
//
//	@Override
//	public SysAccDesc findDescByCode(String code) {
//		return yhglDao.findById(SysAccDesc.class, code);
//	}

	@Override
	public void resetKey(String ids) {
		String defaultPwd = ProjectUtils.getSysCfg("defaultPwd");
		String idArr[] = ids.split(";");
		for(String id : idArr){
			if(!id.equals("")){
				SysAccCount sysAccCount = yhglDao.findById(SysAccCount.class, id);
				sysAccCount.setPwd(StringUtil.toMD5(defaultPwd));
				yhglDao.saveOrUpdate(sysAccCount);
			}
		}
	}

	@Override
	public SysAccCountLazy findByToken(String tokenId) {
		// TODO Auto-generated method stub
		return yhglDao.findByToken(tokenId);
	}

	@Override
	public String findNameByPhone(String rec) {
		String hql="select s.sysAccdesc.name from SysAccCountLazy s where s.phone=?0";
		@SuppressWarnings("rawtypes")
		List list=yhglDao.find(hql, rec);
		if(list.size()==1){
			return (String) list.get(0);
		}
		return "";
	}

	@Override
	public SysAccCount findSysAccCountByCode(String accCode) {
		return yhglDao.findById(SysAccCount.class, accCode);
	}

	@Override
	public void saveAccRole(SysAccRole sysrole) {
		yhglDao.save(sysrole);
	}

	@Override
	public void updateAccRole(SysAccRole sysrole) {
		yhglDao.saveOrUpdate(sysrole);
	}

	@Override
	public void deleterole(String role) {
		List<SysAccRole> accRole = yhglDao.findaccRole(role);
		if(!accRole.isEmpty()){
			for(SysAccRole ar : accRole){
				if(ar.getAccCode() == null){
					String id = ar.getId();
					yhglDao.deleteById(SysAccRole.class, id);
				}
			}
		}
	}

	@Override
	public void yhglService(String id, String validFlag) {
		SysAccCountLazy sys = yhglDao.findById(SysAccCountLazy.class, id);
		sys.setValidFlag(validFlag);
		BeanUtils.copyProperties(sys, new String[]{"sysDepartment","sysAccdesc","sysaccrole"});
		yhglDao.saveOrUpdate(sys);
	}

	@Override
	public List<SysAccCount> findByParam( Map<String, String> map) {
		return yhglDao.findByParam(map);
	}

	@Override
	public void updateAccJf(String accCode) {
		// TODO Auto-generated method stub
		SysAccCountLazy acc = findByCode(accCode);
		if(acc!=null){
			yhglDao.saveOrUpdate(acc);
		}
	}

	@Override
	public SysAccCount getSq(String accCode) {
		 return yhglDao.getSq(SysAccCount.class, accCode);
	}

	@Override
	public SysAccCount findByName(String pre_emp) {
		return yhglDao.findById(SysAccCount.class, pre_emp);
	}

	@Override
	public List<SysAccCount> getName(String role_code) {
		 return yhglDao.getName(SysAccCount.class, role_code);
	}

	@Override
	public List<SysAccCount> getFwzName(String role_code, String sqId) {
		 return yhglDao.getFwzName(SysAccCount.class, role_code,sqId);
	}

	@Override
	public List<SysAccCount> getWgyName(String role_code, String fwzId) {
		return yhglDao.getWgyName(SysAccCount.class, role_code,fwzId);
	}

	@Override
	public void updateAccLazy(SysAccCountLazy sysa) {
		yhglDao.saveOrUpdate(sysa);
	}

	@Override
	public List<SysAccRole> findRole(String accCode) {
		return yhglDao.findRole(accCode);
	}

	@Override
	public void delRoles(SysAccRole lists) {
		yhglDao.delete(lists);
	}

	@Override
	public Pagenate<SysAccCount> getGridPeople(Integer pageNumber,
			int pagesize, Map<String, String> map) {
		return yhglDao.getGridPeople(pageNumber, pagesize, map);
	}

	
}
