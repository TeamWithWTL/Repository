package com.jcwx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger log = Logger.getLogger(HttpUtils.class);

	/**
	 * GET方式请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendByGet(String url, Map<String, String> params) {
		String result = "";
		BufferedReader in = null;
		try {
			// 组织请求参数
			String urlParams = "";
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					if (urlParams.equals("")) {
						urlParams = "?" + key + "=" + new String(params.get(key).getBytes("UTF-8"), "ISO8859-1");
					} else {
						urlParams += "&" + key + "=" + new String(params.get(key).getBytes("UTF-8"), "ISO8859-1");
					}
				}
			}
			String urlString = url + urlParams;
			log.info("GET方式链接URL====>" + urlString);
			URL realUrl = new URL(urlString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			log.info("接收到的数据====>" + result);
		} catch (Exception e) {
			log.error("GET方式请求", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				log.error("GET方式请求", e2);
			}
		}
		return result;
	}

	/**
	 * POST方式请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendByPost(String url, Map<String, String> params, Map<String, String> headParams) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			// 组织请求参数
			String urlParams = "";
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					if (urlParams.equals("")) {
						urlParams = "?" + key + "=" + params.get(key);
					} else {
						urlParams += "&" + key + "=" + params.get(key);
					}
				}
			}
			log.info("POST方式链接URL====>" + url);
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 添加自定义请求属性
			if (headParams != null && !headParams.isEmpty()) {
				for (String key : headParams.keySet()) {
					conn.setRequestProperty(key, headParams.get(key));
				}
			}
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(urlParams);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			log.info("接收到的数据====>" + result);
		} catch (Exception e) {
			log.error("POST方式请求", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e2) {
				log.error("POST方式请求", e2);
			}
		}
		return result;
	}

	/**
	 * POST方式请求
	 * 参数为Json格式
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String sendByPost_json(String url, String params) throws IOException {
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		String response = "";
		try {
			URL httpUrl = null;
			// 创建URL
			httpUrl = new URL(url);
			// 建立连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			// conn.setRequestProperty("connection", "keep-alive");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// POST请求
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(params);
			out.flush();
			// 读取响应
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				response += lines;
			}
			reader.close();
			// 断开连接
			conn.disconnect();
		} catch (Exception e) {
			log.error("发送POST_json请求出现异常", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return response;
	}

}
