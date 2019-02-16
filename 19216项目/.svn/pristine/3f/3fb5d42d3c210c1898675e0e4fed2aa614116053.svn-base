package com.jcwx.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowServerPic extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 输出图片的类型的标志
		String imagePath = request.getParameter("fileName");
		response.reset();

		if (imagePath.toLowerCase().endsWith(".jpg") || imagePath.toLowerCase().endsWith(".png") || imagePath.toLowerCase().endsWith(".jpeg") || imagePath.toLowerCase().endsWith(".bmp")){
			response.setContentType("image/jpeg");// 设定输出的类型
			// 得到图片的文件流
			FileInputStream imageIn = new FileInputStream(new File(imagePath));
			// 图片输出流
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
