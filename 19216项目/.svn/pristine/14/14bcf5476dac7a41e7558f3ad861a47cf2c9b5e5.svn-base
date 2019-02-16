package com.jcwx.service.xtgl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtgl.SjzdDao;
import com.jcwx.entity.pub.SysParam;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.utils.Pagenate;

@Service("sjzdService")
public class SjzdServiceImpl implements SjzdService {

	@Autowired
	private SjzdDao sjzdDao;
	
	@Override
	public Pagenate<SysParam> findByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
	
		return sjzdDao.findByPage(pageNum, pageSize, paramMap);
	}

	@Override
	public SysParam findByCode(String code) {
	
		return sjzdDao.findById(SysParam.class, code);
	}

	@Override
	public void save(SysParam s) {
	
		sjzdDao.save(s);
	}

	@Override
	public void update(SysParam s) {
	
		sjzdDao.saveOrUpdate(s);
	}

	@Override
	public List<SysParamDesc> findByPCode(String code) {
	
		return sjzdDao.findByPCode(code);
	}

	@Override
	public void del(String ids) {
	
		String	id[] = ids.split(";");
		for(int i=0;i<id.length;i++){
			List<SysParamDesc> sys = sjzdDao.findByPCode(id[i]);
			if(!sys.isEmpty()){
				for(SysParamDesc s:sys){
					sjzdDao.deleteById(SysParamDesc.class, s.getId());
				}
			}
			sjzdDao.deleteById(SysParam.class, id[i]);
		}
	}

	@Override
	public Pagenate<SysParamDesc> findDescByPage(int pageNum, int pageSize, Map<String, String> paramMap) {
	
		return sjzdDao.findDescByPage(pageNum, pageSize, paramMap);
	}

	@Override
	public void saveDesc(SysParamDesc sys) {
	
		sjzdDao.save(sys);
	}

	@Override
	public void updateDesc(SysParamDesc sys) {
		sjzdDao.saveOrUpdate(sys);
	}

	@Override
	public void delDesc(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			sjzdDao.deleteById(SysParamDesc.class, id[i]);
		}
	}

	@Override
	public SysParamDesc findDescById(String id) {
		return sjzdDao.findById(SysParamDesc.class, id);
	}

	@Override
	public SysParamDesc findDescByCode(String code, String sjzdCode) {
		return sjzdDao.findDescByCode(code, sjzdCode);
	}

	@Override
	public List<Map<String, String>> findMapListByPCode(String code) {
		List<SysParamDesc> paramdesc =  findByPCode(code);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(SysParamDesc p : paramdesc){
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", p.getItemCode());
			map.put("name", p.getItemName());
			map.put("value1", p.getValue1());
			map.put("value2", p.getValue2());
			list.add(map);
		}
		return list;
	}

	@Override
	public Map<String, String> findMapByPCode(String code) {
		Map<String, String> returnMap = new HashMap<String, String>();
		List<SysParamDesc> paramdesc =  findByPCode(code);
		for(SysParamDesc p : paramdesc){
			returnMap.put(p.getItemCode(), p.getItemName());
		}
		return returnMap;
	}

	@Override
	public List<String> findAllItemCode(String code) {
		String hql="select p.itemCode from SysParam s inner join  s.sysParamDesc p where s.code=?0";
		return sjzdDao.find(hql, code);
	}

	@Override
	public List<SysParam> getParamList(String code) {
	
		return sjzdDao.getParamList(code);
	}

	@Override
	public List<SysParamDesc> getParamDescList(String code) {
	
		return sjzdDao.getParamDescList(code);
	}

	@Override
	public List<SysParamDesc> getEjFwList(String code) {
	
		return sjzdDao.getEjFwList(code);
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtgl.SjzdService#getEjFwList1(java.lang.String)
	 */
	
	
	
	@Override
	public List<SysParamDesc> getEjFwList1(String code) {
	
		return sjzdDao.getEjFwList1(code);
	}

	/* (non-Javadoc)
	 * @see com.jcwx.service.xtgl.SjzdService#getParamDescList1(java.lang.String)
	 */
	@Override
	public List<SysParam> getParamDescList1(String code) {
		
		return sjzdDao.getParamDescList1(code);
	}
	/**
	 * 查询所有相同指定value1 的LIST
	 */
	@Override
	public List<SysParam> findByValue1(String string) {
		return sjzdDao.findByValue1(string);
	}

	@Override
	public SysParamDesc finDescByItemName(String id) {
		return sjzdDao.finDescByItemName(id);
	}

	@Override
	public Map<String, String> findMapByPCode01(String string) {
		Map<String, String> returnMap = new HashMap<String, String>();
		List<SysParamDesc> paramdesc =  findByPCode(string);
		for(SysParamDesc p : paramdesc){
			returnMap.put(p.getValue1(), p.getItemName());
		}
		return returnMap;
	}

}
