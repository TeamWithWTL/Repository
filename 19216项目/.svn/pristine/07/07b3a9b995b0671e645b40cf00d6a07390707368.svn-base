package com.jcwx.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.jcwx.entity.dflz.CompAcceEntity;
import com.jcwx.entity.pub.SysAccCountLazy;
import com.jcwx.entity.shfw.ShfwSqhdYjAttrsEntity;
import com.jcwx.entity.shgl.EventAttrs;
import com.jcwx.entity.shgl.RwclAttrEntity;
import com.jcwx.entity.shgl.RwglAttrEntity;
import com.jcwx.entity.shgl.ShglSqmyDcAttrs;
import com.jcwx.entity.shzz.HdglFkAttrEntity;
import com.jcwx.entity.xtbg.MeetingAcceEntity;
import com.jcwx.entity.xtbg.RdxwArrtsEntity;
import com.jcwx.service.dflz.AppComplainService;
import com.jcwx.service.shfw.YjlyService;
import com.jcwx.service.shgl.EventService;
import com.jcwx.service.shgl.RwclService;
import com.jcwx.service.shgl.RwglService;
import com.jcwx.service.shgl.SqmyService;
import com.jcwx.service.shzz.HdglService;
import com.jcwx.service.ws.WSService;
import com.jcwx.service.xtbg.MeetingAcceService;
import com.jcwx.service.xtgl.YhglService;
import com.jcwx.utils.DateUtils;
import com.jcwx.utils.FileUtil;
import com.jcwx.utils.ProjectUtils;
import com.jcwx.utils.SpringFactory;
import com.jcwx.utils.StringUtil;


public class AppUploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2455682236179429651L;
	Logger logger=Logger.getLogger(AppUploadServlet.class);
	
	/*@Autowired
	WSService wsService;*/

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			doPost(request,response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// 创建文件项目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();

		
		// 设置文件上传路径
		String upload = ProjectUtils.getSysCfg("optUploadPath");
		// 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
		String temp = System.getProperty("java.io.tmpdir");
		// 设置缓冲区大小为 5M
		factory.setSizeThreshold(1024 * 1024 * 5);
		// 设置临时文件夹为temp
		factory.setRepository(new File(temp));
		// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

		// 解析结果放在List中
		try {
			List<FileItem> list = servletFileUpload.parseRequest(request);
			String table = "";// 表名
			String pid = "";// 外键
			String title = "";// 标题
			String desc = "";// 描述
			String file = "";// 文件名
			String newFileName = "";// 新文件名

			for (FileItem item : list) {
				String name = item.getFieldName();
				InputStream is = item.getInputStream();

				if (name.contains("table")) {
					table = inputStream2String(is);
				} else if (name.contains("pid")) {
					pid = inputStream2String(is);
				} else if (name.contains("title")) {
					title = inputStream2String(is);
				} else if (name.contains("desc")) {
					desc = inputStream2String(is);
				} else if (name.contains("file")) {
					try {
						file = upload + "\\" + item.getName();
						String suffix = file.substring(file.lastIndexOf('.'));
						// 对文件进行重命名：当前时间+10位随机数
						newFileName = upload + "/" + DateUtils.getNow("yyyyMMddHHmmSS")
								+ RandomStringUtils.randomAlphanumeric(10) + suffix;

						inputStream2File(is, newFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if("".equals(pid)||"".equals(table)||"".equals(file)){
				out.write("{\"code\":102}");
			}else{
				saveAttInfo(table, pid, title, desc, file, newFileName);			
				out.write("{\"code\":200}");
			}
		} catch (FileUploadException e) {
			out.write("{\"code\":102}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	// 保存到数据库
	private void saveAttInfo(String table, String pid, String title, String desc, String file, String newFileName) {
		try {
			file=new String(file.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.debug("附件录入===================table===="+table+"===pid==="+pid+"=======newFileName===="+newFileName+"======file====="+file);
		
		if("RdxwArrtsEntity".equals(table)) {// 热点新闻附件
			WSService wsService=SpringFactory.getBean("wSService");
			RdxwArrtsEntity rdxwArrtsEntity = new RdxwArrtsEntity();
			rdxwArrtsEntity.setRdxwId(pid);
			rdxwArrtsEntity.setFileType(StringUtil.getFileType(file));
			rdxwArrtsEntity.setNewFileName(newFileName);
			rdxwArrtsEntity.setOldFileName(FileUtil.getFileName(file));//原文件名
			// 保存
			wsService.saveOrUpdateRdxwArrt(rdxwArrtsEntity);
			logger.debug("热点新闻附件添加成功");
		}
		if("CompAcceEntity".equals(table)){//投诉举报附件实体类
			AppComplainService appComplainService = SpringFactory.getBean("appComplainService");
			CompAcceEntity compAcceEntity = new CompAcceEntity();
			compAcceEntity.setTsjb_id(pid);
			compAcceEntity.setFile_type(StringUtil.getFileType(file));
			compAcceEntity.setNew_fileName(newFileName);
			compAcceEntity.setOld_fileName(FileUtil.getFileName(file));//原文件名
			//保存
			appComplainService.saveOrUpdateCompArrt(compAcceEntity);
			logger.debug("投诉举报附件添加成功");
		}
		if ("MeetingAcceEntity".equals(table)) {//会议附件
			MeetingAcceService  meetingAcceService= SpringFactory.getBean("meetingAcceServiceImpl");
			MeetingAcceEntity meetingAcceEntity = new MeetingAcceEntity();
			meetingAcceEntity.setHygl_id(pid);
			meetingAcceEntity.setFile_type(StringUtil.getFileType(file));
			meetingAcceEntity.setNew_fileName(newFileName);
			meetingAcceEntity.setOld_fileName(FileUtil.getFileName(file));
			meetingAcceService.saveOrUpdateMeetingArrt(meetingAcceEntity);
			logger.error("appUploadServlet 会议附件添加成功");
		}
		if ("HdglFkAttrEntity".equals(table)) {//活动管理反馈附件
			HdglService hdglService=SpringFactory.getBean("hdglService");
			HdglFkAttrEntity hdglFkAttrEntity = new HdglFkAttrEntity();
			hdglFkAttrEntity.setZxjsId(pid);
			hdglFkAttrEntity.setFileType(StringUtil.getFileType(file));
			hdglFkAttrEntity.setNewFilename(newFileName);
			hdglFkAttrEntity.setOldFilename(FileUtil.getFileName(file));
			hdglService.saveOrUpdateHdglArrt(hdglFkAttrEntity);
			logger.error("appUploadServlet 活动反馈附件添加成功");
		}
		if ("EventAttrs".equals(table)) {//事件上报附件
			EventService eventService=SpringFactory.getBean("eventService");
			EventAttrs eventEntity = new EventAttrs();
			eventEntity.setEventId(pid);
			eventEntity.setFileType(StringUtil.getFileType(file));
			eventEntity.setNewFilename(newFileName);
			eventEntity.setOldFilename(FileUtil.getFileName(file));
			eventService.saveOrUpdateAttrs(eventEntity);
		}
		if ("ShglSqmyDcAttrs".equals(table)) {//社情民意调查附件
			SqmyService sqmyService=SpringFactory.getBean("SqmyService");
			ShglSqmyDcAttrs ent = new ShglSqmyDcAttrs();
			ent.setDc_id(pid);
			ent.setFile_type(StringUtil.getFileType(file));
			ent.setNew_filename(newFileName);
			ent.setOld_filename(FileUtil.getFileName(file));
			sqmyService.saveorupdate(ent);
		}
		if ("RwclAttrEntity".equals(table)) {//任务管理处理反馈附件
			RwclService rwclService = SpringFactory.getBean("rwclService");
			RwclAttrEntity docAttr = new RwclAttrEntity();
			docAttr.setTask_deal_id(pid);
			docAttr.setFile_type(StringUtil.getFileType(file));
			docAttr.setNew_filename(newFileName);
			docAttr.setOld_filename(FileUtil.getFileName(file));
			rwclService.saveOrUpdateAttrs(docAttr);
		}
		if ("RwglAttrEntity".equals(table)) {//任务管理反馈附件
			RwglService rwglService = SpringFactory.getBean("rwglService");
			RwglAttrEntity docAttr = new RwglAttrEntity();
			docAttr.setTask_id(pid);
			docAttr.setFile_type(StringUtil.getFileType(file));
			docAttr.setNew_filename(newFileName);
			docAttr.setOld_filename(FileUtil.getFileName(file));
			rwglService.saveOrUpdateAttrs(docAttr);
		}
		if ("ShfwSqhdYjAttrsEntity".equals(table)) {//社区活动留言附件
			YjlyService yjlyService = SpringFactory.getBean("yjlyService");
			ShfwSqhdYjAttrsEntity attrs = new ShfwSqhdYjAttrsEntity();
			attrs.setYjId(pid);;
			attrs.setFileType(StringUtil.getFileType(file));
			attrs.setNewFilename(newFileName);
			attrs.setOldFilename(FileUtil.getFileName(file));
			yjlyService.saveOrUpdateAttrs(attrs);
		}
		if ("sysAccCount".equals(table)) {//上传头像
			YhglService yhglService = SpringFactory.getBean("yhglService");
			SysAccCountLazy a = yhglService.findByCode(pid);
			a.setPic_path(newFileName);
			yhglService.updateAcc(a);
		}
	}

	// 流转化成字符串
	public String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	// 流转化成文件
	public static void inputStream2File(InputStream is, String savePath) throws Exception {
		System.out.println("文件保存路径为:" + savePath);
		File file = new File(savePath);
		InputStream inputSteam = is;
		BufferedInputStream fis = new BufferedInputStream(inputSteam);
		FileOutputStream fos = new FileOutputStream(file);
		int f;
		while ((f = fis.read()) != -1) {
			fos.write(f);
		}
		fos.flush();
		fos.close();
		fis.close();
		inputSteam.close();
		
		  FileUtil.reduceImg(savePath, 780,   1040, null);

	}
}
