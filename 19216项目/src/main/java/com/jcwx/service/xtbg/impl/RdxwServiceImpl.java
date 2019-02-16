package com.jcwx.service.xtbg.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.xtbg.RdxwDao;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.entity.xtbg.RdxwEntity;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.utils.OpenOfficeUtils;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;

@Service("rdxwService")
public class RdxwServiceImpl implements RdxwService{

	@Autowired
	private RdxwDao rdxwDao;
	/**
	 * 热点新闻内容
	 */
	@Override
	public Pagenate<RdxwEntity> getRdxwContent(Integer pageNumber, int pagesize, Map<String, String> cxMap) {
		return rdxwDao.getRdxwContent(pageNumber,pagesize,cxMap);
	}
	
	/**
	 * 增加
	 */
	@Override
	public RdxwEntity getById(String id) {
		return rdxwDao.findById(RdxwEntity.class, id);
	}
	
	/**
	 * 保存
	 */
	@Override
	public void save(RdxwEntity rdxw, String fName) {
		rdxw.setTitle(rdxw.getTitle());
		rdxw.setContent(rdxw.getContent());
		rdxw.setSummary(rdxw.getSummary());
		rdxw.setTypes(rdxw.getTypes());
		rdxw.setCreateDate(new Date());
		rdxw.setShStatus("0");	//审核状态 ，0 待审核 ，1 通过 ，2 未通过
		rdxw.setIsHot("2"); 	//是否热点 ，2 否 ，1 是
		rdxw.setIsTop("2"); 	//是否置顶 ，2 否 ，1 是
		rdxwDao.save(rdxw);		
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					RdxwArrtsEntity zwAttr = new RdxwArrtsEntity();
					/*if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFileName(transName);
						}
					}else{
						zwAttr.setNewFileName(newFileName);
					}*/
					zwAttr.setRdxwId(rdxw.getId());
					zwAttr.setOldFileName(oldFileName);
					zwAttr.setNewFileName(newFileName);
					zwAttr.setFileType(fileType);
					rdxwDao.save(zwAttr);
				}
			}
		}
		
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(RdxwEntity rdxw, String id, String fName) {
		RdxwEntity rdxw1 = getById(id);
		rdxw1.setTitle(rdxw.getTitle());
		rdxw1.setSummary(rdxw.getSummary());
		rdxw1.setTypes(rdxw.getTypes());
		rdxw1.setContent(rdxw.getContent());
		rdxw1.setShStatus("0");
		rdxw1.setIsHot(rdxw.getIsHot()); 	//是否热点 ，2 否 ，1 是
		rdxw1.setIsTop(rdxw.getIsTop()); 	//是否置顶 ，2 否 ，1 是
		rdxw1.setPicPath(rdxw.getPicPath());//热点新闻封面图片路径
		BeanUtils.copyProperties(rdxw1, rdxw, new String[]{"userId","userName","createDate","attrList"});
		rdxwDao.saveOrUpdate(rdxw1);
		if(null != fName && !"".equals(fName)){
			String[] path = fName.split(">");
			for(String p : path){
				if(!p.equals("")){
					JSONObject jsStr = JSONObject.fromObject(p); //将字符串{“id”：1}
					String newFileName = jsStr.getString("newName");
					String oldFileName = jsStr.getString("oldName");
					String fileType = jsStr.getString("type").toLowerCase();
					RdxwArrtsEntity zwAttr = new RdxwArrtsEntity();
					if(!fileType.equals("img") && !fileType.equals("pdf")){
						String transName = newFileName.replace(StringUtil.getFileExt(newFileName), "pdf");
						int result = OpenOfficeUtils.office2PDF(newFileName, transName);
						if(result == 0){
							//更新PDF文件名到数据库
							zwAttr.setNewFileName(transName);
						}
					}else{
						zwAttr.setNewFileName(newFileName);
					}
					zwAttr.setRdxwId(rdxw.getId());
					zwAttr.setOldFileName(oldFileName);
					zwAttr.setFileType(fileType);
					rdxwDao.save(zwAttr);
				}
			}
		}
		
	}

	/**
	 * 删除热点新闻信息
	 */
	@Override
	public void del(String ids) {
		String id[] = ids.split(";");
		for(int i=0; i<id.length; i++){
			rdxwDao.deleteById(RdxwEntity.class, id[i]);
		}
		
	}
	
	
	/**
	 * 审核状态
	 */
	@Override
	public void shZxStatus(RdxwEntity rdxw) {
		rdxwDao.saveOrUpdate(rdxw);
	}

	/**
	 * 更新审核状态
	 */
	@Override
	public void updateshStatus(String id) {
		RdxwEntity rdxw = getById(id);
		if(rdxw!=null){
			rdxwDao.saveOrUpdate(rdxw);
		}
		
	}

	/**
	 * 删除附件
	 */
	@Override
	public void delFj(String id) {
		rdxwDao.deleteById(RdxwArrtsEntity.class, id);
	}

	@Override
	public List<RdxwEntity> getRdxwContentList(Map<String, String> addMap) {
		return rdxwDao.getRdxwContentList(addMap);
	}

	/**
	 * 设置热点
	 */
	@Override
	public void hot(String id, String hot) {
//		 if ("1".equals(hot)) {//1为热点状态
//			List<RdxwEntity> rdxwHotList = rdxwDao.findByHot(hot);
//			for (RdxwEntity rdxwEntity : rdxwHotList) {
//				rdxwEntity.setIsHot("2");;//2为取消热点状态
//				rdxwDao.saveOrUpdate(rdxwEntity);
//			}
//		}//修改--高帅（2017年12月14日）
		if (!"".equals(id) && null!=id) {
			RdxwEntity rdxwEntity = rdxwDao.findById(RdxwEntity.class, id);
			if (rdxwEntity!=null) {
				rdxwEntity.setIsHot(hot);
				rdxwDao.saveOrUpdate(rdxwEntity);
			}
		}
	}

	/**
	 * 审核页面 审核状态
	 */
	@Override
	public void shZt(String id, String flag, SysAccCount acc) {

		 String accCode = acc.getAccCode();
		 String name = acc.getName();
//		RdxwEntity rdxw = getById(id);
		 RdxwEntity rdxw = rdxwDao.findById(RdxwEntity.class, id);
		if(rdxw!=null){
			rdxw.setShStatus(flag);
			rdxw.setShUserId(accCode);
			rdxw.setShUserName(name);
			if("2".equals(flag)){//不通过
				rdxw.setIsHot("2");//不是热点
			}
			rdxwDao.saveOrUpdate(rdxw);
		}

	}

	@Override
	public void goTop(String id, String goTop) {
		 if ("1".equals(goTop)) {//1为置顶状态
				List<RdxwEntity> rdxwHotList = rdxwDao.findByTop(goTop);
				for (RdxwEntity rdxwEntity : rdxwHotList) {
					rdxwEntity.setIsTop("2");;//2为取消置顶状态
					rdxwDao.saveOrUpdate(rdxwEntity);
				}
			}
		//修改--高帅（2017年12月14日--需求变更）
		if (!"".equals(id) && null!=id) {
			RdxwEntity rdxwEntity = rdxwDao.findById(RdxwEntity.class, id);
			if (rdxwEntity!=null) {
				rdxwEntity.setIsTop(goTop);
				rdxwDao.saveOrUpdate(rdxwEntity);
			}
		}
	}


}
