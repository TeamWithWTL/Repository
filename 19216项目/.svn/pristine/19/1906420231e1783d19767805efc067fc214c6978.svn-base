package com.jcwx.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jcwx.entity.pub.SysAccCount;

/**
* @author MaBo
* 2016年11月10日
* 登录拦截器
*/
public class LoginInterceptor implements HandlerInterceptor{
	private List<String> excludedUrls;

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		// TODO Auto-generated method stub
		String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.toLowerCase().contains(url.toLowerCase())) {
                return true;
            }
        }
        SysAccCount sysAccCount = (SysAccCount)request.getSession().getAttribute("sysAccCount");
		if(sysAccCount == null){
			 //如果判断是 AJAX 请求,直接设置为session超时
            if( request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")  ) {
            	response.setHeader("sessionstatus", "timeout"); 
               	if(requestUri.contains("app")){
				 	response.sendRedirect(request.getContextPath() + "/appLogin/toLogin.do");
				}else{
				 	response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
            }else{
               	if(requestUri.contains("app")){
				 	response.sendRedirect(request.getContextPath() + "/appLogin/toLogin.do");
				}else{
				 	response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
            }
			return false;
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
	
}
