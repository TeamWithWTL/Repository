package com.jcwx.action.shgl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcwx.action.xtgl.YhglAction;
import com.jcwx.entity.pub.SysAccCount;
import com.jcwx.entity.shgl.CameraEntity;
import com.jcwx.entity.shgl.ShglCommunityEntity;
import com.jcwx.entity.shgl.ShglGridEntity;
import com.jcwx.entity.shgl.ShglServiceStationEntity;
import com.jcwx.service.shgl.FwzglService;
import com.jcwx.service.shgl.SqglService;
import com.jcwx.service.shgl.SxtglService;
import com.jcwx.service.shgl.WgglService;
import com.jcwx.utils.Pagenate;
import com.jcwx.utils.ProjectUtils;

/**
 * 摄像头管理
 * 
 * @author 唐寿强
 *
 */
@Controller
@RequestMapping("/shgl/sxtgl")
public class SxtglAction {
	private static final int pageSize = Integer.parseInt(ProjectUtils.getSysCfg("pageSize"));// 每页最大条数
	private Logger log = Logger.getLogger(YhglAction.class);

	@Autowired
	private FwzglService fwzglService;
	@Autowired
	private SqglService sqglService;
	@Autowired
	private WgglService wgglService;
	@Autowired
	private SxtglService sxtglService;

	/**
	 * 首页数据
	 * 
	 * @param model
	 * @param ajaxCmd
	 * @param pageNumber
	 * @param name
	 * @param commId
	 * @param req
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, String ajaxCmd,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, String name, String ssId,
			String commId, String gridId, HttpServletRequest req) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String roleCode = acc.getRole_code();
		model.addAttribute("roleCode", roleCode);
		Map<String, String> paramMap = new HashMap<String, String>();
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);
		model.addAttribute("comList", comList);
		try {
			if (ajaxCmd == null) {
				model.addAttribute("pagenate", null);
			} else {
				// 分页查询
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("ssId", ssId);
				map.put("commId", commId);
				map.put("gridId", gridId);
				Pagenate<CameraEntity> pagenate = sxtglService.findByPage(pageNumber, pageSize, map);
				model.addAttribute("pagenate", pagenate);
				return "shgl/sxtgl/index#" + ajaxCmd;
			}
		} catch (Exception e) {
			log.info("查询摄像头管理信息出错", e);
		}
		return "shgl/sxtgl/index";
	}

	/**
	 * 跳转添加及修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/goAddEdit")
	public String goAdd(Model model, String id) {
		CameraEntity camera = null;
		String commId = "";
		Map<String, String> paramMap = new HashMap<String, String>();
		List<ShglCommunityEntity> comList = sqglService.findAllCom1(paramMap);// 社区列表
		model.addAttribute("comList", comList);
		if (id != null && !id.equals("")) {
			camera = sxtglService.findById(id);
			commId = camera.getGrid().getServiceStation().getCommunity().getId();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("commId", commId);

			List<ShglServiceStationEntity> ssList = fwzglService.findAllCom1(paramMap1);// 服务站列表
			model.addAttribute("commId", commId);
			model.addAttribute("ssList", ssList);

			Map<String, String> paramMap3 = new HashMap<String, String>();
			paramMap3.put("ssId", camera.getGrid().getServiceStation().getId());
			List<ShglGridEntity> gridList = wgglService.findAllSer1(paramMap3);// 网格列表
			model.addAttribute("gridList", gridList);
		}
		model.addAttribute("camera", camera);
		return "shgl/sxtgl/addEdit";
	}

	/**
	 * 添加、编辑 保存数据
	 * 
	 * @param req
	 * @param build
	 * @param id
	 * @param ssId
	 * @param vId
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doSave")
	public String doSave(HttpServletRequest req, CameraEntity camera, String id, String commId, String gridId,
			String name) {
		SysAccCount acc = (SysAccCount) req.getSession().getAttribute("sysAccCount");
		String accCode = acc.getAccCode();
		String accName = acc.getName();
		ShglGridEntity grid = wgglService.findById1(gridId);// 网格
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("name", name);
			map.put("gridId", gridId);
			List<CameraEntity> cameraEntities = sxtglService.findAllCas(map);
			if (cameraEntities.size() > 0) {
				return "exists";
			}
			CameraEntity cameraOld = sxtglService.findById(id);
			if (cameraOld == null) {// 为null 则数据库中没有该数据
				camera.setAdd_code(accCode);
				camera.setAdd_name(accName);
				camera.setAdd_time(new Date());
				camera.setGrid(grid);
				camera.setCommId(commId);
				sxtglService.save(camera);// 保存
			} else {
				BeanUtils.copyProperties(camera, cameraOld, new String[] { "add_code", "add_name", "add_time" });
				cameraOld.setGrid(grid);
				cameraOld.setCommId(commId);
				sxtglService.update(cameraOld);// 修改
			}
			return "success";
		} catch (Exception e) {
			log.info("添加摄像头信息出错", e);
		}
		return "error";
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goDel")
	public String goDel(String ids) {
		try {
			sxtglService.delete(ids);
			return "success";
		} catch (Exception e) {
			log.info("删除摄像头信息出错", e);
		}
		return "error";
	}

	/**
	 * 跳转地图绘制
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/choseArea", produces = "text/html;charset=UTF-8")
	public String choseArea(Model model, String id) {
		model.addAttribute("id", id);
		model.addAttribute("dataurl", "/shgl/wggl/getSingleAreaData.do");
		model.addAttribute("url", "/shgl/sxtgl/saveArea.do");
		model.addAttribute("type", "ly");
		return "shgl/choseArea";
	}

}
