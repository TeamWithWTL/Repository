package com.jcwx.service.shgl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.shgl.JmxxDao;
import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.utils.Pagenate;

@Service("jmxxService")
public class JmxxServiceImpl implements JmxxService {

	@Autowired
	private JmxxDao jmxxDao;

	@Override
	public void delete(String ids) {
		String id[] = ids.split(";");
		for (int i = 0; i < id.length; i++) {
			jmxxDao.deleteById(ShglInmateEntity.class, id[i]);
		}
	}

	@Override
	public Long countPerNumb(String gridId) {
		return jmxxDao.countPerNumb(gridId);
	}

	@Override
	public Long countPerNumbByparam(Map<String, String> map) {
		return jmxxDao.countPerNumbByparam(map);
	}

	@Override
	public Pagenate<ShglInmateEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map) {
		return jmxxDao.findByPage1(pageNumber, pagesize, map);
	}

	@Override
	public void save1(ShglInmateEntity inmateEntity) {
		jmxxDao.save(inmateEntity);
	}

	@Override
	public ShglInmateEntity findById1(String id) {
		return jmxxDao.findById(ShglInmateEntity.class, id);
	}

	@Override
	public void update1(ShglInmateEntity inmateEntity) {
		jmxxDao.saveOrUpdate(inmateEntity);
	}

	@Override
	public List<ShglInmateEntity> findAllInmates1(Map<String, String> map) {
		return jmxxDao.findAllInmates1(map);
	}

	@Override
	public void saveLx(ShglInmateTypeEntity s) {
		jmxxDao.save(s);
	}

	@Override
	public void delLx(String id) {
		jmxxDao.deleteById(ShglInmateTypeEntity.class, id);
	}

	@Override
	public int findCountBySqId(String id) {
		return jmxxDao.findCountBySqId(id);
	}

	@Override
	public int findTsrkCount(String id) {
		return jmxxDao.findTsrkCount(id);
	}
	
	/**
	 * 查询当前用户负责的社区
	 */
	@Override
	public List<ShglCmanagerEntity> findCmanager(String accCode) {
		
		return jmxxDao.findCmanager(accCode);
	}
	
	/**
	 * 查询负责的服务站
	 */
	@Override
	public List<ShglSmanagerEntity> findSmanager(String accCode) {
		return jmxxDao.findSmanager(accCode);
	}
	
	/**
	 * 查询负责的小区
	 */
	@Override
	public List<ShglVmanagerEntity> findVmanager(String accCode) {
		return jmxxDao.findVmanager(accCode);
	}
	
	/**
	 * 以id查询社区
	 */
	@Override
	public ShglCommunityEntity findCommById(String commId) {
		return jmxxDao.findById(ShglCommunityEntity.class, commId);
	}
	
	/**
	 * 以Id查询服务站
	 */
	@Override
	public ShglServiceStationEntity findSSById(String ssId) {
		return jmxxDao.findById(ShglServiceStationEntity.class, ssId);
	}
	
	/**
	 * 服务站人口数量
	 */
	@Override
	public int findSSBySqId(String sqId) {
		return jmxxDao.findSSBySqId(sqId);
	}
	
	/**
	 * 以社区id,查询所有服务站
	 */
	@Override
	public List<ShglServiceStationEntity> findSsListByCommId(String logCommId) {
		return jmxxDao.findSsListByCommId(logCommId);
	}
	
	/**
	 * 以服务站id 查询所有的小区list
	 */
	@Override
	public List<ShglGridEntity> findVillListByCommId(String logSsId) {
		return jmxxDao.findVillListByCommId(logSsId);
	}
	
	/**
	 * 查网格人口数量
	 */
	@Override
	public int findVillBySqId(String sqId) {
		return jmxxDao.findVillBySqId(sqId);
	}
	
	/**
	 * 查单一分类的居民数量
	 */
	@Override
	public int findInmateById(Map<String, String> map) {
		return jmxxDao.findInmateById(map);
	}
	
	/**
	 * 分页查询特殊人口
	 */
	@Override
	public Pagenate<ShglInmateEntity> getTsrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		 Pagenate<ShglInmateEntity> tsrkContent = jmxxDao.getTsrkContent(pageNumber,pagesize,cxMap);
		return tsrkContent;
	}
	
	/**
	 * 查询特殊人口分类id
	 */
	@Override
	public String findClassifyByName(String tsName) {
		return jmxxDao.findClassifyByName(tsName);
	}
	
	/**
	 * 查询社区，服务站，网格Id
	 */
	@Override
	public String findsqIdByName(String sqName, String isAdmin) {
		return jmxxDao.findsqIdByName(sqName,isAdmin);
	}
	
	/**
	 *  社区人口查询
	 */
	@Override
	public Pagenate<ShglInmateEntity> getSqrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
	
		return jmxxDao.getSqrkContent(pageNumber,pagesize,cxMap);
	}
	
	/**
	 * 查询所有是户主的人员
	 */
	@Override
	public List<ShglInmateEntity> findHz() {
		return jmxxDao.findHz();
	}
	
	/**
	 * 地址查询家庭关系
	 */
	@Override
	public List<ShglInmateEntity> findFamilyByHzId(Map<String, String> tiesMap) {
		return jmxxDao.findFamilyByHzId(tiesMap);
	}
	
	/**
	 * 查询户主关系字段表的名字
	 * @return 
	 */
	@Override
	public SysParamDesc findHzidName(String relation) {
		return jmxxDao.findHzidName(relation);
	}

	@Override
	public List<ShglInmateEntity> findAllInmates(Map<String, String> map) {
		return jmxxDao.findAllInmates(map);
	}

	@Override
	public int findLyHsCount(Map<String, String> addMap) {
		return jmxxDao.findLyHsCount(addMap);
	}

	@Override
	public List<SysParamDesc> getTypeList() {
		return jmxxDao.getTypeList();
	}

	@Override
	public List<ShglInmateEntity> findJm(String buildId, String unit_no, String room_no) {
		return jmxxDao.findJm(buildId, unit_no, room_no);
	}

}
