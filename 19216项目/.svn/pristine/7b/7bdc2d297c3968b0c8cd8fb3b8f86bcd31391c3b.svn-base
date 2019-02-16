package com.jcwx.service.shzz.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcwx.dao.shzz.HdglDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.HdglAttrEntity;
import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.entity.shzz.HdglFkAttrEntity;
import com.jcwx.entity.shzz.HdglFkEntity;
import com.jcwx.entity.shzz.HdglYjEntity;
import com.jcwx.entity.shzz.ZzxxEntity;
import com.jcwx.service.shzz.HdglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author m
 */
@Service("hdglService")
@Transactional
public class HdglServiceImpl implements HdglService{
	
	@Autowired
	private HdglDao hdglDao;

	@Override
	public Pagenate<HdglEntity> getHdglContent(Integer pageNumber, int pagesize, Map<String, String> Map) {

		return hdglDao.getHdglContent(pageNumber, pagesize, Map);
	
	}

	@Override
	public HdglEntity findById(String id) {
		
		return hdglDao.findById(HdglEntity.class, id);
	}

	@Override
	public HdglEntity getById(String id) {
		
		return hdglDao.findById(HdglEntity.class, id);
	}
	
	/*@Override
	public void save(HdglEntity hdgl, String fName) {
		hdgl.setCreateTime(new Date());
		hdgl.setShStatus("0");
		hdgl.setIs_hot("2");
		hdglDao.save(hdgl);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					HdglAttrEntity hdAttr = new HdglAttrEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							hdAttr.setNewFilename(transName);
						}
					}else{
						hdAttr.setNewFilename(newFileName);
					}
					hdAttr.setHdglId(hdgl.getId());
					hdAttr.setOldFilename(oldFileName);
					hdAttr.setFileType(fileType);
					hdglDao.save(hdAttr);
				}
			}
		}
		
	}*/
	
	@Override
	public void save(HdglEntity hdgl, String fName, String zzIds) {
		hdgl.setCreateTime(new Date());
		hdgl.setShStatus("0");
		hdgl.setIs_hot("2");
		if(hdgl.getStartDateFmt()!=null && !"".equals(hdgl.getStartDateFmt())){
			hdgl.setStartDate(DateUtils.parseDate(hdgl.getStartDateFmt(), "yyyy-MM-dd"));
		}else{
			hdgl.setStartDate(null);
		}
		if(hdgl.getEndDateFmt()!=null && !"".equals(hdgl.getEndDateFmt())){
			Calendar c = Calendar.getInstance();  
	        c.setTime(DateUtils.parseDate(hdgl.getEndDateFmt(), "yyyy-MM-dd")); 
	        c.set(c.HOUR_OF_DAY, 23);
	        c.set(c.MINUTE, 59);
	        c.set(c.SECOND, 59);
		    Date jstrrow = c.getTime();
			hdgl.setEndDate(jstrrow);
		}else{
			hdgl.setEndDate(null);
		}
		hdglDao.save(hdgl);
		//保存可反馈组织
		if(null != zzIds && !"".equals(zzIds)){
			String[] zzId = zzIds.split(";");
			for(String zId : zzId){
				HdglFkEntity hdglFkEntity = new HdglFkEntity();
				hdglFkEntity.setZzId(zId);
				hdglFkEntity.setHdglId(hdgl.getId());
				hdglFkEntity.setIsBack("2"); 	//未反馈
				hdglFkEntity.setShStatus("3");	
				hdglDao.save(hdglFkEntity);
			}
		}
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFilename = jsStr.getString("newName");
					String oldFilename = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					HdglAttrEntity hdAttr = new HdglAttrEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFilename.replace(StringUtil.getFileExt(newFilename), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFilename, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							hdAttr.setNew_filename(transName);
						}
					}else{
						hdAttr.setNew_filename(newFilename);
					}
					hdAttr.setHdglId(hdgl.getId());
					hdAttr.setOld_filename(oldFilename);
					hdAttr.setFileType(fileType);
					hdglDao.save(hdAttr);
				}
			}
		}
	}

	@Override
	public void update(HdglEntity hdgl, String id, String fName, String zzIds) {
		HdglEntity hdgl1 = getById(id);
		//sqfw.setTitle(sqfw.getTitle());
		//sqfw.setContent(sqfw.getContent());
		hdgl.setShStatus("0");
		if(hdgl.getStartDateFmt()!=null && !"".equals(hdgl.getStartDateFmt())){
			hdgl.setStartDate(DateUtils.parseDate(hdgl.getStartDateFmt(), "yyyy-MM-dd"));
		}else{
			hdgl.setStartDate(null);
		}
		if(hdgl.getEndDateFmt()!=null && !"".equals(hdgl.getEndDateFmt())){
			Calendar c = Calendar.getInstance();  
	        c.setTime(DateUtils.parseDate(hdgl.getEndDateFmt(), "yyyy-MM-dd")); 
	        c.set(c.HOUR_OF_DAY, 23);
	        c.set(c.MINUTE, 59);
	        c.set(c.SECOND, 59);
		    Date jstrrow = c.getTime();
			hdgl.setEndDate(jstrrow);
		}else{
			hdgl.setEndDate(null);
		}
		BeanUtils.copyProperties(hdgl, hdgl1, new String[]{"userId", "userName", "isHot", "createTime", "attrList", "fkList"});
		hdglDao.saveOrUpdate(hdgl1);
		//List<HdglFkEntity> fkList = hdglDao.findFkByHdId(id);
		for (HdglFkEntity fk : hdgl1.getFkList()) {
			hdglDao.delete(fk);
		}
		//保存可反馈组织
		if(null != zzIds && !"".equals(zzIds)){
			String[] zzId = zzIds.split(";");
			for(String zId : zzId){
				HdglFkEntity hdglFkEntity = new HdglFkEntity();
				hdglFkEntity.setZzId(zId);
				hdglFkEntity.setHdglId(id);
				hdglFkEntity.setIsBack("2"); 	//未反馈
				hdglFkEntity.setShStatus("3");
				hdglDao.save(hdglFkEntity);
			}
		}
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					HdglAttrEntity hdAttr = new HdglAttrEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							hdAttr.setNew_filename(transName);
						}
					}else{
						hdAttr.setNew_filename(newFileName);
					}
					hdAttr.setHdglId(hdgl.getId());
					hdAttr.setOld_filename(oldFileName);
					hdAttr.setFileType(fileType);
					hdglDao.save(hdAttr);
				}
			}
		}	
		
	}

	@Override
	public void shHdStatus(HdglEntity hdgl) {
		hdglDao.saveOrUpdate(hdgl);
	}

	@Override
	public void updateshStatus(String id) {
		HdglEntity hdgl = findById(id);
		if(hdgl!=null){
			hdglDao.saveOrUpdate(hdgl);
		}
		
	}

	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			hdglDao.deleteById(HdglEntity.class, id[i]);
		}
	}

	/**
	 * 附件删除
	 */
	@Override
	public void delFj(String id) {
		hdglDao.deleteById(HdglAttrEntity.class, id);
	}

	@Override
	public void updateisHot(String id, String flag) {
		
		HdglEntity hdgl = getById(id);
		if(hdgl!=null){
			hdgl.setIs_hot(flag);
			hdglDao.saveOrUpdate(hdgl);
		}
	}

	@Override
	public void doAuditing(String id, String status, SysAccCount sysAccCount) {
		String accCode = sysAccCount.getAccCode();
		String name = sysAccCount.getName();
		HdglEntity hdglEntity = hdglDao.findById(HdglEntity.class, id);
		hdglEntity.setShStatus(status);
		hdglEntity.setShUserName(name);//审核人
		hdglEntity.setShUserId(accCode);
		hdglDao.saveOrUpdate(hdglEntity);
		
	}

	@Override
	public void hot(String id, String hot) {
		if ("1".equals(hot)) {//1为热点状态
			List<HdglEntity> hdglHotList = hdglDao.findByHot(hot);
			for (HdglEntity hdglEntity : hdglHotList) {
				hdglEntity.setIs_hot("2");//2为取消热点状态
				hdglDao.saveOrUpdate(hdglEntity);
			}
		}
		if (!"".equals(id)&&null!=id) {
			HdglEntity hdglEntity = hdglDao.findById(HdglEntity.class, id);
			if (hdglEntity!=null) {
				hdglEntity.setIs_hot(hot);
				hdglDao.saveOrUpdate(hdglEntity);
			}
		}
	}
	
	/**
	 * 留言保存
	 */
	@Override
	public void saveLy(String id, String content, SysAccCount acc) {
		String accCode = acc.getAccCode();
		String name = acc.getName();
			HdglYjEntity hdglYjEntity = new HdglYjEntity();
			hdglYjEntity.setHdglId(id);//活动id
			hdglYjEntity.setContent(content);//留言内容
			hdglYjEntity.setCreateTime(new Date());//留言时间
			hdglYjEntity.setStatus("0");	//状态初始化 0未审核  1审核通过  2审核不通过
			hdglYjEntity.setUserId(accCode);//留言人id
			hdglYjEntity.setUserName(name);//留言人姓名
			hdglDao.save(hdglYjEntity);
	}
	
	/**
	 * 留言分页查询初始页
	 */
	@Override
	public Pagenate<HdglYjEntity> findHdyjFistPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return hdglDao.findHdyjFistPage(pageNumber,pagesize,map);
	}
	
	/**
	 * 查询留言
	 */
	@Override
	public List<HdglYjEntity> findByHdId(String id) {
		return hdglDao.findbyHdId(id);
	}
	
	/**
	 * 以法人id，查询组织信息列表
	 */
	@Override
	public List<ZzxxEntity> findZzxmByAccCode(String accCode) {
		return hdglDao.findZzxmByAccCode(accCode);
	}
	
	/**
	 * 活动管理反馈保存
	 */
	@Override
	public void saveFk(HdglFkEntity hdglFkEntity, String id) {
		HdglFkEntity fk = hdglDao.findById(HdglFkEntity.class, id);
		if(fk!=null){
			hdglDao.saveOrUpdate(fk);
		}
	}
	
	/**
	 *活动管理反馈附件保存
	 */
	@Override
	public void saveOrUpdateHdglArrt(HdglFkAttrEntity hdglFkAttrEntity) {
		hdglDao.seveOrUpdateHdglArrt(hdglFkAttrEntity);
		
	}
	
	/**
	 * 以活动id,查询反馈集合
	 */
	@Override
	public List<HdglFkEntity> findFkByHdId(String id) {
		return hdglDao.findFkByHdId(id);
	}
	
	@Override
	public HdglFkEntity getFkByHdIdZzId(String hdId, String zzId) {
		return hdglDao.getFkByHdIdZzId(hdId, zzId);
	}
	
	/**
	 *  id查询组织
	 */
	@Override
	public ZzxxEntity findZzById(String zzId) {
		return hdglDao.findById(ZzxxEntity.class,zzId);
	}
	
	/**
	 * 查询审核通过留言分页
	 */
	@Override
	public Pagenate<HdglYjEntity> findLyByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return hdglDao.findLyByPage(pageNumber,pagesize,map);
	}
	
	@Override
	public Pagenate<HdglYjEntity> findLyDshByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return hdglDao.findLyDshByPage(pageNumber, pagesize, map);
	}

	@Override
	public Pagenate<HdglYjEntity> findLyBtgByPage(Integer pageNumber, int pagesize, Map<String, String> map) {
		return hdglDao.findLyBtgByPage(pageNumber, pagesize, map);
	}

	/**
	 * 删除留言
	 */
	@Override
	public void delLy(String id) {
		hdglDao.deleteById(HdglYjEntity.class, id);
	}
	
	@Override
	public void shFktg(String id) {
		HdglFkEntity fk = hdglDao.findById(HdglFkEntity.class, id);
		fk.setShStatus("1");;	//审核通过
		hdglDao.saveOrUpdate(fk);
	}

	@Override
	public void shFkbtg(String id) {
		HdglFkEntity fk = hdglDao.findById(HdglFkEntity.class, id);
		fk.setShStatus("2");	//审核不通过
		hdglDao.saveOrUpdate(fk);
	}
	
	/**
	 * 审核留言 通过
	 */
	@Override
	public void successLy(String id) {
		HdglYjEntity hdglYjEntity = hdglDao.findById(HdglYjEntity.class, id);
		hdglYjEntity.setStatus("1");	//审核通过
		hdglDao.saveOrUpdate(hdglYjEntity);
	}
	
	@Override
	public void shLybtg(String id) {
		HdglYjEntity hdglYjEntity = hdglDao.findById(HdglYjEntity.class, id);
		hdglYjEntity.setStatus("2");	//审核不通过
		hdglDao.saveOrUpdate(hdglYjEntity);
	}
	
	/**
	 * 查询审核通过反馈分页
	 */
	@Override
	public Pagenate<HdglFkEntity> findFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		return hdglDao.findFkByPage(pageNumber,pagesize,fkMap);
	}
	
	@Override
	public Pagenate<HdglFkEntity> findDshFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		return hdglDao.findDshFkByPage(pageNumber, pagesize, fkMap);
	}

	@Override
	public Pagenate<HdglFkEntity> findBtgFkByPage(Integer pageNumber, int pagesize, Map<String, String> fkMap) {
		return hdglDao.findBtgFkByPage(pageNumber, pagesize, fkMap);
	}

	@Override
	public HdglYjEntity getHdYjByhdId(String hdglId, String userId) {
		return hdglDao.getHdYjByhdId(hdglId, userId);
	}

	@Override
	public List<HdglEntity> getZzhdList() {
		return hdglDao.getZzhdList();
	}

	@Override
	public void updateHdStatus(String id, String status) {
		HdglEntity hd = getById(id);
		hd.setHdStatus(status);
		hdglDao.saveOrUpdate(hd);
	}

	@Override
	public HdglFkEntity findFkByFkId(String id) {
		return hdglDao.findById(HdglFkEntity.class, id);
	}

	@Override
	public List<HdglFkEntity> findYfkByHdId(String id) {
		return hdglDao.findYfkByHdId(id);
	}

}
