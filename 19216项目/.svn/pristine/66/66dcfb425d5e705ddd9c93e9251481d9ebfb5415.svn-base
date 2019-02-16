package com.jcwx.action.sjzx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.entity.shgl.ShglTaskDealEntity;
import com.jcwx.service.sjzx.RwtjService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * @author gaoshuai 2017年10月26日 任务统计
 */
@Controller
@RequestMapping("/sjzx/rwtj")
public class RwtjAction {

	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大页数
	private Logger log = Logger.getLogger(RwtjAction.class);// 日志

	@Autowired
	private RwtjService rwtjService;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String indexRwtj(Model model) {
		// 查询所有的社区
		List<ShglCommunityEntity> sqList = rwtjService.sqAll();
		// 查询所有的服务站
		List<ShglServiceStationEntity> fwzList = rwtjService.fwzAll();
		model.addAttribute("sqList", sqList);
		model.addAttribute("fwzList", fwzList);
		model.addAttribute("pagenate", null);
		return "sjzx/rwtj/index_rwtj";
	}

	/**
	 * 分页查询
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param startTime
	 * @param endTime
	 * @param sqName
	 * @param fwzName
	 * @param req
	 * @return
	 */
	@RequestMapping("/initDataForRWTJ")
	public String initData(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String startTime,
			String endTime, String sqId, String fwzId, HttpServletRequest req) {
		log.info("任务创建时间>>>" + startTime);
		log.info("任务完成时间>>>" + endTime);
		log.info("社区编号>>>" + sqId);
		log.info("服务站编号>>>" + fwzId);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);
				paramMap.put("sqId", sqId);
				paramMap.put("fwzId", fwzId);
				Pagenate<ShglTaskDealEntity> pagenate = null;
				pagenate = rwtjService.findRwtjContent(pageNumber, pageSize, paramMap);
				model.addAttribute("pagenate", pagenate);
				return "sjzx/rwtj/index_rwtj#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.error("[RwtjAction]_[initData]_查询任务信息出错", e);
		}
		return "sjzx/rwtj/index_rwtj";
	}

}
