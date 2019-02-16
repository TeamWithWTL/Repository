package com.jcwx.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcwx.entity.shzz.HdglEntity;
import com.jcwx.service.shzz.HdglService;

/**
 * 组织活动定时任务
 */
@Component
public class ZzhdSchedule {
	private Logger log = Logger.getLogger(ZzhdSchedule.class);
	
	@Autowired
	private HdglService hdglService;
	
	@Scheduled(cron="0 0 0 * * ?")
	public void zzhd_index() {
		Date today = new Date();
		log.info("组织活动有效期维护轮询程序开启>>>>>>>>>>>>>>>>>>>>\n");
		try {
			// 1.查询
			List<HdglEntity> hdList = hdglService.getZzhdList();
			if(hdList.isEmpty()){
				log.info("没有正在进行的组织活动<<<<<<<<<<<<<<<<<<\n");
			}else{
				for(HdglEntity h : hdList){
					String id = h.getId();
					Date endDate = h.getEndDate();  				//结束日期
					Date startDate = h.getStartDate();				//开始日期
					
					/*Calendar c = Calendar.getInstance();
				    c.setTime(endDate);
				    c.add(Calendar.DAY_OF_MONTH, 1);*/
				    
					if(endDate.before(today)){       			//活动结束
						String status = "2";
						hdglService.updateHdStatus(id,status);	
						log.info("组织活动【" + id + "】已结束，并且已经更新其状态为[结束]");
					}else if(startDate.before(today)){ 	  //活动开始
						String status = "1";
						hdglService.updateHdStatus(id,status);	
						log.info("社区活动【" + id + "】置为进行中");
					}
				}
				log.info("更新组织活动状态完成，轮询程序退出<<<<<<<<<<<<<<<<<<\n");
			}
		} catch (Exception e) {
			log.error("组织活动轮询程序出错，程序退出=====================\n");
		}
	}
}
