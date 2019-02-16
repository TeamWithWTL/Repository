package com.jcwx.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcwx.entity.shfw.ShfwSqhdEntity;
import com.jcwx.service.shfw.SqhdService;
/**
 * 社区活动定时任务
 */
@Component
public class SqhdSchedule {
	private Logger log = Logger.getLogger(SqhdSchedule.class);
	
	@Autowired
	private SqhdService sqhdService;
	
	@Scheduled(cron="0 0 0 * * ?")
	public void sqhd_index() {
		Date today = new Date();
		log.info("社区活动有效期维护轮询程序开启>>>>>>>>>>>>>>>>>>>>\n");
		try {
			// 1.查询
			List<ShfwSqhdEntity> hdList = sqhdService.getHdList();
			if(hdList.isEmpty()){
				log.info("没有正在进行的社区活动<<<<<<<<<<<<<<<<<<\n");
			}else{
				for(ShfwSqhdEntity h : hdList){
					String id = h.getId();
					Date endDate = h.getEndDate();  				//结束日期
					Date startDate = h.getStartDate();				//开始日期
					
					/*Calendar c = Calendar.getInstance();
				    c.setTime(endDate);
				    c.add(Calendar.DAY_OF_MONTH, 1);*/
				    
					if(endDate.before(today)){       				//活动结束
						String status = "2";
						sqhdService.updateHdStatus(id,status);	
						log.info("社区活动【" + id + "】已结束，并且已经更新其状态为[结束]");
					}
					else if(startDate.before(today)){ 				//活动开始
						String status = "1";
						sqhdService.updateHdStatus(id,status);	
						log.info("社区活动【" + id + "】置为进行中");
					}
				}
				log.info("更新社区活动状态完成，轮询程序退出<<<<<<<<<<<<<<<<<<\n");
			}
		} catch (Exception e) {
			log.error("社区活动轮询程序出错，程序退出=====================\n");
		}
	}
}
