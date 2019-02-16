package com.jcwx.service.shgl;

import java.util.List;
import java.util.Map;

import com.jcwx.entity.pub.SysParamDesc;
import com.jcwx.entity.shgl.ShglCmanagerEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.entity.shgl.ShglInmateTypeEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglSmanagerEntity;
import com.jcwx.entity.shgl.ShglVmanagerEntity;
import com.jcwx.utils.Pagenate;

public interface JmxxService {

	/**
	 * 分页查询数据
	 * @param pageNumber
	 * @param pagesize
	 * @param map
	 * @return
	 */
	Pagenate<ShglInmateEntity> findByPage1(Integer pageNumber, int pagesize, Map<String, String> map);
	
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	public ShglInmateEntity findById1(String id);

	/**
	 * 保存
	 * @param village
	 */
	public void save1(ShglInmateEntity inmateEntity);

	/**
	 * 编辑保存
	 * @param villageOld
	 */
	public void update1(ShglInmateEntity inmateEntity);

	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 根据条件查询数据
	 * @param map
	 * @return
	 */
	public List<ShglInmateEntity> findAllInmates1(Map<String, String> map);

	/**
	 * 统计网格人口数
	 * @param gridId
	 * @return
	 */
	public Long countPerNumb(String gridId);

	/**
	 * 根据条件统计人口
	 * @param map
	 * @return
	 */
	public Long countPerNumbByparam(Map<String, String> map);
	
	/**
	 * 保存类型
	 * @param s
	 */
	public void saveLx(ShglInmateTypeEntity s);
	
	/**
	 * 删除类型
	 * @param id
	 */
	public void delLx(String id);
	
	/**
	 * 查询每个社区的人数
	 * @param id
	 */
	public int findCountBySqId(String id);
	
	/**
	 * 查询每种特殊人群的人数
	 * @param id
	 */
	public int findTsrkCount(String id);
	
	/**
	 * 查询当前用户负责的社区
	 * @author 李伟
	 * @time 2017年11月30日上午11:08:19
	 * @param accCode
	 * @return
	 */
	List<ShglCmanagerEntity> findCmanager(String accCode);
	
	/**
	 * 查询负责的服务站
	 * @author 李伟
	 * @time 2017年11月30日上午11:08:38
	 * @param accCode
	 * @return
	 */
	List<ShglSmanagerEntity> findSmanager(String accCode);
	
	/**
	 * 查询负责的小区
	 * @author 李伟
	 * @time 2017年11月30日上午11:08:59
	 * @param accCode
	 * @return
	 */
	List<ShglVmanagerEntity> findVmanager(String accCode);
	
	/**
	 * 以id查询社区
	 * @author 李伟
	 * @time 2017年11月30日下午2:48:53
	 * @param commId
	 * @return
	 */
	ShglCommunityEntity findCommById(String commId);
	
	/**
	 * 以id查询服务站
	 * @author 李伟
	 * @time 2017年11月30日下午2:59:51
	 * @param ssId
	 * @return
	 */
	ShglServiceStationEntity findSSById(String ssId);
	
	/**
	 * 服务站人口数量
	 * @author 李伟
	 * @time 2017年11月30日下午3:04:13
	 * @param sqId
	 * @return
	 */
	int findSSBySqId(String sqId);
	
	/**
	 * 以社区id,查询所有服务站
	 * @author 李伟
	 * @time 2017年11月30日下午3:50:55
	 * @param logCommId
	 * @return
	 */
	List<ShglServiceStationEntity> findSsListByCommId(String logCommId);
	
	/**
	 * 以服务站id 查询所有的小区list
	 * @author 李伟
	 * @time 2017年11月30日下午3:59:06
	 * @param logSsId
	 * @return
	 */
	List<ShglGridEntity> findVillListByCommId(String logSsId);
	
	/**
	 * 网格人口数量
	 * @author 李伟
	 * @time 2017年11月30日下午4:03:42
	 * @param sqId
	 * @return
	 */
	int findVillBySqId(String sqId);
	
	/**
	 * 查单一分类的居民数量
	 * @author 李伟
	 * @time 2017年11月30日下午11:13:13
	 * @param map
	 * @return
	 */
	int findInmateById(Map<String, String> map);
	
	/**
	 * 分页查询特殊人口
	 * @author 李伟
	 * @time 2017年12月1日上午11:06:31
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	Pagenate<ShglInmateEntity> getTsrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);
	
	/**
	 * 查询分类id
	 * @author 李伟
	 * @time 2017年12月1日下午5:34:42
	 * @param tsName
	 * @return
	 */
	String findClassifyByName(String tsName);
	
	/**
	 * 查询社区，服务站，网格Id
	 * @author 李伟
	 * @time 2017年12月2日上午5:26:54
	 * @param sqName
	 * @param isAdmin
	 * @return
	 */
	String findsqIdByName(String sqName, String isAdmin);
	
	/**
	 * 社区人口查询
	 * @author 李伟
	 * @time 2017年12月2日上午8:54:30
	 * @param pageNumber
	 * @param pagesize
	 * @param cxMap
	 * @return
	 */
	Pagenate<ShglInmateEntity> getSqrkContent(Integer pageNumber, int pagesize, Map<String, String> cxMap);
	
	/**
	 * 查询所有是户主的人员
	 * @author 李伟
	 * @time 2017年12月2日下午4:19:51
	 * @return
	 */
	List<ShglInmateEntity> findHz();
	
	/**
	 * 地址查询家庭关系
	 * @author 李伟
	 * @time 2017年12月2日下午5:37:25
	 * @param tiesMap
	 * @return
	 */
	List<ShglInmateEntity> findFamilyByHzId(Map<String, String> tiesMap);
	
	/**
	 * 查询户主关系字段表的名字
	 * @author 李伟
	 * @time 2017年12月4日上午5:43:59
	 * @param relation
	 * @return 
	 */
	SysParamDesc findHzidName(String relation);

	/**
	 * 根据姓名查询是否已存在
	 * @param map
	 * @return
	 */
	List<ShglInmateEntity> findAllInmates(Map<String, String> map);

	/**
	 * 根据楼宇Id查询实住户数
	 * @param addMap
	 * @return
	 */
	int findLyHsCount(Map<String, String> addMap);
	
	/**
	 * 根据code获取所有特殊人口类型列表
	 * @return
	 */
	List<SysParamDesc> getTypeList();
	
	/**
	 * 获取居民信息
	 * @param buildId
	 * @param unit_no
	 * @param room_no
	 * @return
	 */
	List<ShglInmateEntity> findJm(String buildId, String unit_no, String room_no);
	
}
