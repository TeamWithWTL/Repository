package com.jcwx.action.pub;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jcwx.utils.FileUtil;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * @author MaBo 2017年6月8日
 */
@Controller
@RequestMapping("/pub")
public class PublicAction {

	private static Logger logger = Logger.getLogger(PublicAction.class);

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/uploadFile", produces = "text/html;charset=UTF-8")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") CommonsMultipartFile[] file) {
		// 取配置文件中的上传路径
		String path = ProjectUtils.getSysCfg("optUploadPath");
		String code = "200"; // 上传状态码
		String newName = ""; // 新文件名
		String oldName = ""; // 原文件名
		String type = ""; // 文件类型
		try {
			List<Map<String, String>> l = FileUtil.fileUpload(path, file);
			newName = l.get(0).get("newName");
			oldName = l.get(0).get("oldName");
			type = StringUtil.getFileType(newName);
		} catch (Exception e) {
			logger.error("附件上传出错", e);
			e.printStackTrace();
			code = "500";
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("newName", newName);
		jsonObject.put("oldName", oldName);
		jsonObject.put("type", type);

		return jsonObject.toString();
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 *            新文件名
	 * @param oldFileName
	 *            原文件名
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downloadbystream")
	public void downloadbystream(String fileName, String oldFileName, HttpServletRequest request,
			HttpServletResponse response) {
		FileUtil.downLoadFileByStream(fileName, oldFileName, request, response);
	}
	
	/**
	 * 查看PDF文件
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("/goPdf")
	public String goPdf(String url, Model model){
		model.addAttribute("url", url);
		return "previewPdf";
	}
}
