package com.jcwx.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowServerPDF extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 输出图片的类型的标志
		String videoPath = request.getParameter("fileName");
		response.reset();
		if (videoPath.toLowerCase().endsWith(".pdf")){
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			// 得到视频的文件流
			FileInputStream imageIn = new FileInputStream(new File(videoPath));
			// 视频输出流
			ServletOutputStream out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = imageIn.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			imageIn.close(); // 关闭文件流
			out.flush();
		}
	}
}
