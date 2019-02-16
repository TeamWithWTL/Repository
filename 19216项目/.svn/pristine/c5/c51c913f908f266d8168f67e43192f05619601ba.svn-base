package com.jcwx.utils;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * 类名：HtmlUtil
 * 类描述：Html工具类
 * 	         用于去除html标签等
 * @author zhangkai
 *
 */
public class HtmlUtil {
	
	private static Logger logger = Logger.getLogger(HtmlUtil.class);
	
	 /**
     * 删除字符串中的Html标签
     * @param inputString
     * @return
     */
    public static String htmlRemoveTag(String inputString) {
        if (inputString == null) {
            return null;
        }    
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            //e.printStackTrace();
        	logger.error("删除字符串中的Html标签出错：", e);
        }
        return textStr;// 返回文本字符串
    }
   
    /**
     * 转义HTML标签
     * @param html
     * @return
     */
    public static String convertHtml(String html){
    	if(html==null) {
    		return "";
    	}
	    //html = html.replace( "'", "&apos;");
        html = html.replaceAll( "&", "&amp;");
        html = html.replace( "\"", "&quot;");  //"
        html = html.replace( "\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace( " ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replaceAll( ">", "&gt;");
        return html;
    }
	    
    public static void main(String[] args) {  
        String str = "<html><div style='text-align:center;'> 删除字符串中的<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>Html标签</span><br/></div></html>";  
        System.out.println(htmlRemoveTag(str));  
    }  
    
}
