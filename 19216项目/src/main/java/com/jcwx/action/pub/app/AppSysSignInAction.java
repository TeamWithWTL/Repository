package com.jcwx.action.pub.app;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.entity.pub.Point;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.pub.SysSignInEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.util.Gps;
import com.jcwx.service.pub.LoginService;
import com.jcwx.service.pub.SysSignInService;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.service.xtbg.DocumentService;
import com.jcwx.service.xtbg.MeetingService;
import com.jcwx.service.xtbg.RdxwService;
import com.jcwx.service.xtgl.SjzdService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.PositionUtil;

/**
 * @author Gs
 * 2018年5月16日
 * App--网格员签到
 * 		1.要求每天签到两次，上午下午各一次（以12点为分割线）
 * 		2.必须在网格员所管理的网格内签到，超出范围不允许签到。
 */
@SuppressWarnings("static-access")
@Controller
@RequestMapping("/app/signIn")
public class AppSysSignInAction {
	
	private Logger log = Logger.getLogger(AppSysSignInAction.class);
	
	@Autowired
	private DocumentService docService;
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private RdxwService rdxwService;
	@Autowired
	private SjzdService sjzdService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private RwglService rwglService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private YhglService yhglService;
	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SysSignInService sysSignInService;
	
	/**
	 * 签到
	 * @param model
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSignIn")
	public String doSignIn(Model model, HttpServletRequest request, String lon, String lat){
		log.info("[AppSysSignInAction]_[doSignIn]_lon----->"+lon);
		log.info("[AppSysSignInAction]_[doSignIn]_lat----->"+lat);
		try {
			// 获取用户信息
			SysAccCount accCount = (SysAccCount) request.getSession().getAttribute("sysAccCount");
			// 获取用户角色
			String roleCode = accCount.getRole_code();
			// 获取用户所在社区
			String commId = accCount.getCommId();
			// 获取用户所在服务站
			String fwzId = accCount.getSsId();
			// 获取用户所在网格ID
			String gridId = accCount.getGridId();
			if("".equals(lon) || "".equals(lat)){
				lon = "0";
				lat = "0";
			}
			if(lon != null && !"".equals(lon) && lat != null && !"".equals(lat)){
			Gps gcj = PositionUtil.bd09_To_Gcj02(Double.parseDouble(lat), Double.parseDouble(lon));
			Gps gb =  PositionUtil.gcj_To_Gps84(gcj.getWgLat(), gcj.getWgLon());
			lat = String.valueOf(gb.getWgLat());
			lon = String.valueOf(gb.getWgLon());
			}
			// 判断是否网格员
			if(roleCode.indexOf("01") != -1){
				ShglGridEntity entity = wgglService.findById1(gridId);
				if(null != entity){
					String area = entity.getArea();
					String[] areas = area.split(";");
					Point[] ps = new Point[areas.length];
					for(int i = 0; i<areas.length; i++){
						Point point = new Point();
						point.setLon(Double.valueOf(areas[i].split(",")[0]));
						point.setLat(Double.valueOf(areas[i].split(",")[1]));
						ps[i] = point;
					}
					// 判断是否在网格范围内
					if(isPtInPoly(Double.valueOf(lon), Double.valueOf(lat), ps) == false){
//					if(isPtInPoly(37.147306457622, 118.09908237913, ps)){					// 测试用
						return "noScope";
					}
				}
			}else{
				return "noAuthority";
			}
			// 判断是否签到
			Calendar calendar1 = Calendar.getInstance();		// 获取00:00:00:00
			calendar1.set(calendar1.HOUR_OF_DAY, 0);
			calendar1.set(calendar1.MINUTE, 0);
			calendar1.set(calendar1.SECOND, 0);
			calendar1.set(calendar1.MILLISECOND, 0);
			Date startTime = calendar1.getTime();
			
			Calendar calendar2 = Calendar.getInstance();		// 获取11:59:59:59
			calendar2.set(calendar2.HOUR_OF_DAY, 11);
			calendar2.set(calendar2.MINUTE, 59);
			calendar2.set(calendar2.SECOND, 59);
			calendar2.set(calendar2.MILLISECOND, 59);
			Date lunchTime = calendar2.getTime();
			
			Calendar calendar3 = Calendar.getInstance();		// 获取11:59:59:59
			calendar3.set(calendar3.HOUR_OF_DAY, 23);
			calendar3.set(calendar3.MINUTE, 59);
			calendar3.set(calendar3.SECOND, 59);
			calendar3.set(calendar3.MILLISECOND, 59);
			Date dinnerTime = calendar3.getTime();
			
			SysSignInEntity signInEntity = new SysSignInEntity();
			// 判断是上午还是下午：“1”：下午，“0”：上午
			Calendar calendar = Calendar.getInstance();			// 获取当前时间
			Date nowTime = calendar.getTime();
			int flag = 	calendar.get(calendar.AM_PM);
			if(flag == 0){
				SysSignInEntity entity = sysSignInService.findContent(accCount.getAccCode(), startTime, lunchTime,"0");
				if(null != entity){
					return "amN"; // 上午已签到
				}
				signInEntity.setSignTimeAm(nowTime);
			}else{
				SysSignInEntity entity = sysSignInService.findContent(accCount.getAccCode(), startTime, dinnerTime,"1");
				if(null != entity){
					return "pmN"; // 下午已签到
				}
				SysSignInEntity entityAm = sysSignInService.findContent(accCount.getAccCode(), startTime, lunchTime,"0");
				if(null != entityAm){
					entityAm.setSignTimePm(nowTime);
					// 修改
					sysSignInService.updataPm(entityAm);
					return "success";
				}
				signInEntity.setSignTimePm(nowTime);
			}
			signInEntity.setAddCode(accCount.getAccCode());		// 签到人ID
			signInEntity.setAddName(accCount.getName());		// 签到人姓名
			signInEntity.setCommId(commId);						// 所在社区
			signInEntity.setFwzId(fwzId);						// 所在服务站
			signInEntity.setGridId(gridId);						// 所在网格
			// 保存
			sysSignInService.saveContent(signInEntity);
			return "success";
		} catch (NumberFormatException e) {
			log.error("[AppSysSignInAction]_[doSignIn]_签到错误", e);
		}
		return "error";
	}
	
	// 判断一个经纬度是否在某一区域
    public static boolean isPtInPoly (double ALon , double ALat , Point[] ps) {  
       int iSum, iCount, iIndex;  
       double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;  
       if (ps.length < 3) {  
           return false;  
       }  
       iSum = 0;  
       iCount = ps.length;  
       for (iIndex = 0; iIndex<iCount;iIndex++) {
           if (iIndex == iCount - 1) {  
               dLon1 = ps[iIndex].getLon();  
               dLat1 = ps[iIndex].getLat();  
               dLon2 = ps[0].getLon();  
               dLat2 = ps[0].getLat();  
           } else {  
               dLon1 = ps[iIndex].getLon();  
               dLat1 = ps[iIndex].getLat();  
               dLon2 = ps[iIndex + 1].getLon();  
               dLat2 = ps[iIndex + 1].getLat();  
           }  
           // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上  
           if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {  
               if (Math.abs(dLat1 - dLat2) > 0) {  
                   //得到 A点向左射线与边的交点的x坐标：  
                   dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat) ) / (dLat1 - dLat2);  
                   // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：  
                   if (dLon < ALon) {  
                       iSum++;  
                   }  
               }  
           }  
       }  
       if ((iSum % 2) != 0) {  
           return true;  
       }  
       return false;  
   }  
}  