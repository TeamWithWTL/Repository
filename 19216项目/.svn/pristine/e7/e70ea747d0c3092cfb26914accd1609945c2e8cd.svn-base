package com.jcwx.tags;

import java.util.List;

import org.apache.log4j.Logger;
import org.beetl.core.GeneralVarTagBinding;

import com.jcwx.entity.pub.SysRoleMenu;
import com.jcwx.service.xtgl.JsglService;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.SpringFactory;

/**
* @author MaBo
* 2017年5月25日
* 自定义标签-操作按钮
* <#roleBtn btnId="" roleCode="" />
*/
public class ButtonTag extends GeneralVarTagBinding{
	
	private Logger logger = Logger.getLogger(ButtonTag.class);
	
	private String buttonId;	// 操作按钮ID
	private String roleCode;	// 角色编码
	
	@Override
	public void render() {
		
		try {
			buttonId = getAttributeValue("btnId").toString();
			roleCode = getAttributeValue("roleCode").toString();
			if(buttonId != null && !buttonId.equals("") && roleCode != null && !roleCode.equals("")){
				if(roleCode.equals(ProjectUtils.getSysCfg("sysRoleCode"))){//系统的超级用户，拥有所有权限，无需分配权限
					// 显示标签下的html内容
					doBodyRender();
				}else{
					// 校验角色是否存在此按钮权限
					JsglService jsglService = SpringFactory.getBean("jsglService"); 
					String roles [] = roleCode.split(",");
					StringBuffer buf = new StringBuffer();      //方法名
					for(String role : roles){
						List<SysRoleMenu> list = jsglService.findRightsByRoleCode(role);
						for(SysRoleMenu sysRoleMenu : list){
							String methods = sysRoleMenu.getMethodCodes();
							if(methods != null && !methods.equals("")){
								String[] metCodeArr = methods.split(",");
								for(String metCode : metCodeArr){
									if(!metCode.equals("") && metCode.equals(buttonId)){
										if(buf != null){
											String f = buf.toString();
											String ff [] = f.split(",");
											for(String bt : ff){
												if(!bt.equals(buttonId)){
												  buf. append(buttonId+",");
													// 显示标签下的html内容
													doBodyRender();
												}
											}
										}else{
											buf. append(buttonId+",");
											// 显示标签下的html内容
											doBodyRender();
										}
									}
								}
							}
						}
						
					}
				}
			}else{
				logger.error("相关参数未定义");
			}
		} catch (Exception e) {
			logger.error("自定义标签-操作按钮出错", e);
		}
	}
}
