package com.jcwx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jcwx.utils.VerifyCodeUtils;

/**
* @author MaBo
* 2016年12月2日
* 登录验证码
*/
@SuppressWarnings("serial")
public class CodeImage extends HttpServlet{
	
	Logger logger = Logger.getLogger(CodeImage.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
        // 将验证码放入Session
        HttpSession session = request.getSession(true);
        session.setAttribute("authCode", verifyCode);
        // 验证码图片宽度和高度
        int w = 90, h = 35;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
  
    }
}
