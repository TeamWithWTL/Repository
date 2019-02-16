package com.jcwx.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.jcwx.entity.shgl.ShglInmateEntity;
import com.jcwx.service.shgl.JmxxService;
import com.jcwx.utils.DateUtils;

//@Component
public class JmxxSchedule {
	private Logger log = Logger.getLogger(JmxxSchedule.class);

	@Autowired
	private JmxxService jmxxService;

	@Scheduled(fixedDelay = 1000 * 60)
	public void sqhd_index() {
		int count = 0;
		log.info("序开启>>>>>>>>>>>>>>>>>>>>\n");
		try {
			Map<String, String> jmxx = new HashMap<String, String>();
			List<ShglInmateEntity> inmateList = jmxxService
					.findAllInmates(jmxx);
			for (int i = 0; i < inmateList.size(); i++) {
				try {
					ShglInmateEntity image = inmateList.get(i);
					String cardNo = image.getCard_no();
					if (cardNo.length() == 18 || cardNo.length() == 15) {
						String birthDay = cardNo.substring(6, 10) + "-"
								+ cardNo.substring(10, 12) + "-"
								+ cardNo.substring(10, 12);
						String sex = "";
						String sCardNum = cardNo.substring(16, 17);
						if (Integer.parseInt(sCardNum) % 2 != 0) {
							sex = "E";// 男
						} else {
							sex = "F";// 女
						}
						image.setBirthday(DateUtils.parseDate(birthDay,
								"yyyy-MM-dd"));
						image.setSex(sex);
						jmxxService.update1(image);
						log.info("更新成功=\n");
						count++;
					}
				} catch (Exception e) {
					log.info("数据异常===================\n");
				}
			}
			log.error("完成=====================修改数量：" + count);
		} catch (Exception e) {
			log.error("异常退出");
		}
	}
}
