package com.jcwx.service.shzz;

import java.util.Map;

import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shzz.DtbbEntity;
import com.jcwx.entity.shzz.ZxjsEntity;
import com.jcwx.utils.Pagenate;

/**
 * 	中心介绍
 */
public interface ZxjsService {
	
	/**
	 * 获取中心介绍内容
	 * @param pageNumber
	 * @param ajaxCmd
	 * @param map
	 * @return
	 */
	public Pagenate<ZxjsEntity> getZxjsContent(Integer pageNumber, int pageSize, Map<String, String> map);
	//根据id获取中心介绍详情
	public ZxjsEntity getById(String id);
	
	//添加保存
	public void save(ZxjsEntity zxjs, String fName);
	
	//修改
	public void update(ZxjsEntity zxjs, String id, String fName);
	
	//审核状态
	public void shStatus(String ids);
	
	//删除中心介绍信息
	public void del(String ids);
	
	//根据附件ID删除对应附件
	public void delFj(String id);
	
	//更新审核状态
	public void updateshStatus(String id);
	
	//审核政务信息
	public void shZxStatus(ZxjsEntity zxjs);
	
	//审核页面 审核状态
	public void shZt(String id, String flag,SysAccCount acc);
	
	
}
