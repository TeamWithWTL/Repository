package com.jcwx.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
* @author MaBo
* 2017年3月21日
* 在线用户监听
*/
public class OnLineUserListener implements HttpSessionListener {

	Logger log = Logger.getLogger(OnLineUserListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		try {
			log.info("用户退出===========");
			// doSomething
		} catch (Exception e) {
			log.error("", e);
		}
		
	}

}
