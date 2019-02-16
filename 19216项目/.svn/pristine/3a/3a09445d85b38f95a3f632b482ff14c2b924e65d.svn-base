package com.jcwx.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtil {

	private static Logger log = Logger.getLogger(StringUtil.class);
	
	public static char[] ranStr = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'i', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	/**
	 * 对字符串进行MD5加密
	 * @param plainText
	 * @return
	 */
	public static String toMD5(String plainText) {
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch (Exception e) {
			log.error("对字符串进行MD5加密", e);
		}
		return null;
	}

	/**
	 * 生成随机字符串
	 * @param len 生成随机字符串的个数
	 * @return 一个随机字符串
	 */
	public static String getRandomStr(int len) {
		Random random = new Random();
		int length = ranStr.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			int ii = random.nextInt(length);
			char c = ranStr[ii];
			str += c;
		}
		return str;
	}

	/**
	 * 生成UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 过滤特殊字符
	 * @param dateString
	 * @param formate
	 * @return
	 */
	public static String filterchart(String value) {
		if (value == null || value.equals("")) {
			return "";
		}
		String val[] = { "<", ">", "script", "'", "function", "(", ")", "{", "}", "/", "\\", "alert", "confirm", "$",
				"query", "\"" };
		String value1 = value.toLowerCase();
		for (int i = 0; i < val.length; i++) {
			String ch = val[i];
			value1 = value1.replace(ch, "*");
		}
		return value1.trim();
	}

	/**
	 * 获取文件扩展名
	 * @param fileName 文件名称
	 * @return 小写的文件扩展名
	 */
	public static String getFileExt(String fileName) {
		String fileExt = "";
		try {
			int a = fileName.lastIndexOf(".");
			fileExt = fileName.substring(a + 1, fileName.length());
		} catch (Exception e) {
			log.error("获取文件扩展名", e);
		}
		return fileExt.toLowerCase();
	}

	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String ext = getFileExt(fileName).toLowerCase();//大写转小写
		String fileType = "";
		if (ext.equals("doc") || ext.equals("docx")) {
			fileType = "word";
		} else if (ext.equals("xls") || ext.equals("xlsx")) {
			fileType = "exl";
		} else if (ext.equals("ppt") || ext.equals("pptx")) {
			fileType = "ppt";
		} else if (ext.equals("zip") || ext.equals("rar")) {
			fileType = "zip";
		} else if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")) {
			fileType = "img";
		} else if (ext.equals("pdf")) {
			fileType = "pdf";
		} else if (ext.equals("txt")) {
			fileType = "txt";
		} else if (ext.equals("mp4")) {
			fileType = "video";
		} else if (ext.equals("mp3")) {
			fileType = "audio";
		} else {
			fileType = "default";
		}
		return fileType;
	}

	/**
	 * 截取字符串
	 * @param content 截取内容
	 * @param length 截取长度
	 * @return
	 */
	public static String getCutString(String content, int length) {
		if (!"".equals(content) && content != null) {
			if (content.length() > length) {
				content = content.substring(0, length - 1) + "......";
			}
		}
		return content;
	}

	/**
	 * 记录错误信息
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	/**
	 * 判断日期格式:yyyy-MM-dd
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{1,2}-\\d{1,2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 判断日期格式:yyyy/MM/dd
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate2(String sDate) {
		String datePattern1 = "\\d{4}/\\d{1,2}/\\d{1,2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 判断日期格式:yyyyMMdd
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate3(String sDate) {
		String datePattern1 = "\\d{4}\\d{1,2}\\d{1,2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 判断日期格式:yyyyMM
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate4(String sDate) {
		String regEx2="^[1-2]{1}\\d{3}((1[0-2]{1})|(0\\d{1}))$";
		Pattern p=Pattern.compile(regEx2);
		Matcher m=p.matcher(sDate);
		return m.matches();
	}
	
	/**
	 * 判断日期格式:yyyy-MM
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate5(String sDate) {
		String regEx2="^\\d{4}-\\d{1,2}";
		Pattern p=Pattern.compile(regEx2);
		Matcher m=p.matcher(sDate);
		return m.matches();
	}
	
	/**
	 * 电话校验
	 * @param phoneNum
	 * 格式  13112341234,010-12456789,01012456789,(010)12456789,00861012456789,+861012456789
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNum){  
	    String regex="1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";  
	    Pattern p = Pattern.compile(regex);  
	    return p.matcher(phoneNum).matches();  
	}  
	
	/**
	 * 身份证校验
	 * @param cardno
	 * 格式 15、18位，最后一位是数字或取字母
	 * @return
	 */
	public static boolean isCardno(String cardno){
		String regEx="(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
		Pattern pattern=Pattern.compile(regEx);
		Matcher matcher=pattern.matcher(cardno);
		return matcher.matches();
	}
	
	/**
	 * 年级校验
	 * @param grade
	 * 格式 1000-2999 如：2016
	 * @return
	 */
	public static boolean isGrade(String grade){
		String regEx="^[1-2]{1}[0-9]{3}$";
		Pattern pattern=Pattern.compile(regEx);
		Matcher matcher=pattern.matcher(grade);
		return matcher.matches();
	}
}
