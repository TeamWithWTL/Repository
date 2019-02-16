package com.jcwx.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcwx.utils.FileUtil;


public class DownLoadFileServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 String filep = request.getParameter("filename");// 输出图片的类型的标志
		 String oldfilep = request.getParameter("filename");// 输出图片的类型的标志
		// filename.replace("", "//")"
		if (null==oldfilep) oldfilep = "";
		 
		 FileUtil.downLoadFileByStream(filep,oldfilep,request,response);
		
	       
	 }

}











