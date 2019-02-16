package com.jcwx.utils;

import java.io.File;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
* @author MaBo
* 2017年3月7日
*/
public class OpenOfficeUtils {
	/**
	 * 将Office文档转换为PDF
	 * @param sourceFile 源文件(绝对路径) 示例:F:\\pdf\\test.doc
	 *        可以是Office2003-2007全部格式的文档,Office2010的没测试.包括:.doc .docx .xls .xlsx .ppt .pptx等
	 * @param destFile 目标文件(绝对路径) 示例:F:\\pdf\\dest.pdf
	 * @return -1:找不到源文件	0:成功	1:失败
	 */
	public static int office2PDF(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
            	// 找不到源文件, 则返回-1
                return -1;
            }
            
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
         
            //String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";//这里是OpenOffice的安装目录, 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的  
            String OpenOffice_HOME = ProjectUtils.getSysCfg("openOffice");
            // 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'  
            if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {  
                OpenOffice_HOME += "\\";  
            }  
            // 启动OpenOffice的服务  
            String command = OpenOffice_HOME  
                    + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";  
            Process pro = Runtime.getRuntime().exec(command);  
            
            // connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
            converter.convert(inputFile, outputFile);
            
            // close the connection
            connection.disconnect();
            
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 1;  
    }
	
	/**
	 * 将Office文档转换为PDF
	 * @param sourceFile 源文件(绝对路径) 示例:F:\\pdf\\test.doc
	 *        可以是Office2003-2007全部格式的文档,Office2010的没测试.包括:.doc .docx .xls .xlsx .ppt .pptx等
	 * @param destFile 目标文件(绝对路径) 示例:F:\\pdf\\dest.pdf
	 * @return -1:找不到源文件	0:成功	1:失败
	 */
	public static int office2PDF1(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
            	// 找不到源文件, 则返回-1
                return -1;
            }
            
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            
            //String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";//这里是OpenOffice的安装目录, 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的  
            String OpenOffice_HOME = ProjectUtils.getSysCfg("openOffice");
            // 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'  
            if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {  
                OpenOffice_HOME += "\\";  
            }  
            // 启动OpenOffice的服务  
            String command = OpenOffice_HOME  
                    + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";  
            Process pro = Runtime.getRuntime().exec(command);  
            
            // connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            
            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
            converter.convert(inputFile, outputFile);
            
            // close the connection
            connection.disconnect();
            
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 1;  
    }
	
	
	
	// Test
	public static void main(String[] args) {
		office2PDF("D:/新建 Microsoft Word 97 - 2003 文档.doc", "D:/新建 Microsoft Word 97 - 2003 文档.pdf");
	}
}
