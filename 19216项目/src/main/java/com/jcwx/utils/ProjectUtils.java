package com.jcwx.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author MaBo 2017年5月5日 项目工具包
 */
public class ProjectUtils {

	private static Logger logger = Logger.getLogger(ProjectUtils.class);

	/**
	 * 根据名称获取系统配置项
	 * 
	 * @param cfgName
	 * @return
	 */
	public static String getSysCfg(String cfgName) {
		try {
			InputStream in = ProjectUtils.class.getResourceAsStream("/sysConfig.properties");
			Properties properties = new Properties();
			properties.load(new InputStreamReader(in, "UTF-8"));
			Set<Entry<Object, Object>> set = properties.entrySet();
			for (Entry<Object, Object> entry : set) {
				if (cfgName.equals(entry.getKey().toString())) {
					return entry.getValue().toString();
				}
			}
		} catch (Exception e) {
			logger.error("根据名称获取系统配置项", e);
		}
		return "";
	}

	public static String checkData(String val, String type) {
		try {
			if (type != null && !"".equals(type)) {
				if (type.indexOf(val) != -1) {
					return "是";
				} else {
					return "否";
				}
			} else {
				return "否";
			}
		} catch (Exception e) {
			logger.error("判断出错", e);
		}
		return "否";
	}
}
