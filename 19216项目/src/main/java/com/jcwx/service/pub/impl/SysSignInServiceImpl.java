package com.jcwx.service.pub.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcwx.dao.pub.SysSignInDao;
import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.entity.pub.SysSignInSetEntity;
import com.jcwx.service.pub.SysSignInService;
import com.jcwx.utils.Pagenate;
@Service("sysSignInService")
public class SysSignInServiceImpl implements SysSignInService {
		@Autowired
		private SysSignInDao sysSignInDao;

		@Override
		public SysSignInEntity findContent(String addCode, Date startTime, Date lunchTime, String flag) {
			return sysSignInDao.findContent(addCode, startTime, lunchTime, flag);
		}

		@Override
		public void saveContent(SysSignInEntity signInEntity) {
			sysSignInDao.save(signInEntity);
		}

		@Override
		public Pagenate<SysSignInEntity> getSignInList(Integer pageNumber,
				int pagesize, Map<String, String> map) {
			return sysSignInDao.getSignInList(pageNumber, pagesize, map);
		}

		@Override
		public List<SysSignInEntity> getCouunt(String accCode, Date sTime,
				Date eTime) {
			return sysSignInDao.getCouunt(accCode, sTime, eTime);
		}

		@Override
		public void updataPm(SysSignInEntity entityAm) {
			sysSignInDao.saveOrUpdate(entityAm);
		}

		@Override
		public List<SysSignInSetEntity> findAllSignMonth() {
			return sysSignInDao.findAll(SysSignInSetEntity.class);
		}

		@Override
		public void saveSetMonth(SysSignInSetEntity entity, String id) {
			if(null != id && !"".equals(id)){
				SysSignInSetEntity setEntity = sysSignInDao.findById(SysSignInSetEntity.class, id);
				if(null != setEntity){
					setEntity.setOneMonth(entity.getOneMonth());
					setEntity.setTwoMonth(entity.getTwoMonth());
					setEntity.setThreeMonth(entity.getThreeMonth());
					setEntity.setFourMonth(entity.getFourMonth());
					setEntity.setFiveMonth(entity.getFiveMonth());
					setEntity.setSixMonth(entity.getSixMonth());
					setEntity.setSevenMonth(entity.getSevenMonth());
					setEntity.setEightMonth(entity.getEightMonth());
					setEntity.setNineMonth(entity.getNineMonth());
					setEntity.setTenMonth(entity.getTenMonth());
					setEntity.setElevenMonth(entity.getElevenMonth());
					setEntity.setTwelveMonth(entity.getTwelveMonth());
					sysSignInDao.saveOrUpdate(setEntity);
				}
			}else{
				SysSignInSetEntity setEntity = new SysSignInSetEntity();
				setEntity.setOneMonth(entity.getOneMonth());
				setEntity.setTwoMonth(entity.getTwoMonth());
				setEntity.setThreeMonth(entity.getThreeMonth());
				setEntity.setFourMonth(entity.getFourMonth());
				setEntity.setFiveMonth(entity.getFiveMonth());
				setEntity.setSixMonth(entity.getSixMonth());
				setEntity.setSevenMonth(entity.getSevenMonth());
				setEntity.setEightMonth(entity.getEightMonth());
				setEntity.setNineMonth(entity.getNineMonth());
				setEntity.setTenMonth(entity.getTenMonth());
				setEntity.setElevenMonth(entity.getElevenMonth());
				setEntity.setTwelveMonth(entity.getTwelveMonth());
				sysSignInDao.save(entity);
			}
		}
}
