package com.jcwx.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 服务器获取IP
 * 
 * @author Hao
 * 
 *
 */
public class IPUtils {
	//针对单一网卡情况
	public static String QueryIp()
	{
		String ipaddress=  "0.0.0.0";
		try{
		InetAddress inetAdd = InetAddress.getLocalHost();
		ipaddress = inetAdd.getHostAddress();
		
		}
		catch (Exception e)
		{
			ipaddress="0.0.0.0";
		}
		return ipaddress;
	}
	//针对多网卡情况
	//Enumeration<NetworkInterface>  netInterFace = (Enumeration<NetworkInterface>)NetworkInterface.getNetworkInterfaces();
	
	//获取ip mack地址
	
	public static void main (String[] args)
	{
		String ip = IPUtils.QueryIp();
		System.out.println("ip="+ip);
	}
	 
}
