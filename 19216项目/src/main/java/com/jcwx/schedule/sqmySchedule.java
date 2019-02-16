package com.jcwx.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcwx.entity.shgl.ShglSqmyEntity;
import com.jcwx.service.shgl.SqmyService;

/**
 * 2017年11月7日
 * @author xushu
 * 社情民意有效期维护
 */
@Component
public class sqmySchedule {
	
	private Logger log = Logger.getLogger(sqmySchedule.class);
	
	@Autowired
	private SqmyService sqmyService;
	
	@Scheduled(cron="0 0 0 * * ?")
	public void index() {
		Date today = new Date();
		log.info("社情民意有效期维护轮询程序开启>>>>>>>>>>>>>>>>>>>>\n");
		try {
			// 1.查询
			Map<String, String> map = new HashMap<String, String>();
//			Pagenate<ShglSqmyEntity> pagenate = sqmyService.findSqmyByPage(1, pageSize, map);
			List<ShglSqmyEntity> sqmyLists = sqmyService.getSqmyContent();//高帅--（2017年12月21日）
			if(sqmyLists.isEmpty()){
				log.info("没有正在进行的社情民意<<<<<<<<<<<<<<<<<<\n");
			}else{
				for(ShglSqmyEntity p : sqmyLists){
					String id = p.getId();           //当前社情民意的id
					Date endtime = p.getEnd_date();  // 结束时间
					if(endtime.before(today)){       //结束
						String is_over = "1";
						sqmyService.Xg(id,is_over);	
						log.info("社情民意【" + id + "】已结束，并且已经更新其状态为[结束]");
					}else if(today.before(endtime)){ //未结束
						String is_over = "0";
//						sqmyService.Xg(id,is_over);	
						log.info("社情民意【" + id + "】未结束！");
					}
	
				}
				log.info("更新社情民意状态完成，轮询程序退出<<<<<<<<<<<<<<<<<<\n");
			}
		} catch (Exception e) {
			log.error("社情民意轮询程序出错A，程序退出=====================\n");
		}
	}
}
