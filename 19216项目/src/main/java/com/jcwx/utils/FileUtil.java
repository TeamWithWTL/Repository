package com.jcwx.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	static Logger log = Logger.getLogger(FileUtil.class);

	/**
	 * 保存二进制文件
	 * 
	 * @param in
	 *            输入流
	 * @param location
	 *            保存的位置
	 */
	public static void saveFile(InputStream in, String location) throws IOException {
		BufferedInputStream bufferIn = null;
		FileOutputStream outputStream = null;
		BufferedOutputStream bufferOut = null;
		try {
			bufferIn = new BufferedInputStream(in);
			outputStream = new FileOutputStream(location);
			bufferOut = new BufferedOutputStream(outputStream);
			byte[] b = new byte[1024];
			int c = -1;
			while ((c = bufferIn.read(b)) != -1) {
				bufferOut.write(b, 0, c);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		} finally {
			if (null != bufferOut) {
				bufferOut.close();
			}
			if (null != outputStream) {
				outputStream.close();
			}
			if (null != bufferIn) {
				bufferIn.close();
			}
			if (null != in) {
				in.close();
			}
		}
	}

	/**
	 * 文件上传 支持多文件上传
	 * 
	 * @param saveDir
	 *            文件保存路径
	 * @param fileName
	 *            原始文件名
	 * @param inputStream
	 *            文件流
	 * @return 重命名后的文件名列表[ {oldName:原文件名, newName:带路径的新文件名}, ... ]
	 * @throws Exception
	 */
	public static List<Map<String, String>> fileUpload(String saveDir, MultipartFile[] files) throws Exception {
		List<Map<String, String>> fileNames = new ArrayList<Map<String, String>>();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				Map<String, String> fileName = saveFile(saveDir, file);
				fileNames.add(fileName);
			}
		} else {
			throw new Exception("上传文件不可为空");
		}
		return fileNames;
	}

	/**
	 * 保存文件到指定目录
	 * 
	 * @param saveDir
	 * @param file
	 * @return 返回文件名的 {oldName:原文件名, newName:带路径的新文件名}
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	private static Map<String, String> saveFile(String saveDir, MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new Exception("上传文件不可为空！");
		}
		String oldFileName = file.getOriginalFilename(); // 原始文件名
		int dot = oldFileName.lastIndexOf(".");
		dot = dot >= 0 ? dot : 0;
		String suffix = oldFileName.substring(dot);
		// 对文件进行重命名：当前时间+10位随机数
		String newFileName = DateUtils.getNow("yyyyMMddHHmmSS") + RandomStringUtils.randomAlphanumeric(10) + suffix;

		// 判断文件保存目录是否存在，如果不存在则新建
		String fileName = saveDir + "/" + newFileName;
		File fileObj = new File(fileName);
		if (!fileObj.getParentFile().exists()) {
			fileObj.mkdirs();
		}
		// 保存文件到指定目录
		file.transferTo(fileObj);
		Map<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("oldName", oldFileName);
		nameMap.put("newName", fileName);
		// 压缩
		 reduceImg(fileName, 780, 1040, null);

		return nameMap;
	}

	/**
	 * Excel文件导入，支持2007以上版本
	 * 
	 * @param fileName
	 *            带路径的文件名
	 * @param tableName
	 *            数据表名
	 * @return 导入时的错误信息，如果导入成功，则返回空
	 * @throws IOException
	 */
	public static String importExcel(String fileName, String tableName) throws IOException {
		// 校验导入的文件
		String checkResult = checkImpData(fileName, tableName);

		if (checkResult.equals("")) {
			// 校验通过，开始导入数据到数据库
			// 0.读取Excel文件
			List<List<String>> content = readExcel(fileName);
			// 1.获取要插入的字段
			List<List<String>> fieldList = getExcelTitles(tableName);
			String selField = "";
			for (int i = 0; i < fieldList.size(); i++) {
				List<String> fList = fieldList.get(i);
				selField += fList.get(0) + ",";
			}
			// 2.组织SQL导入数据
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			for (int i = 1; i < content.size(); i++) {
				List<String> dataValue = content.get(i);
				String insValue = "";
				for (int j = 0; j < dataValue.size(); j++) {
					String tmp = dataValue.get(j);
					insValue += "'" + tmp + "',";
				}
				String insSql = "insert into " + tableName + "(" + selField.substring(0, selField.length() - 1)
						+ ") values(" + insValue.substring(0, insValue.length() - 1) + ")";
				insValue = "";
				try {
					jdbcTemplate.update(insSql);
				} catch (DataAccessException e) {
					checkResult += "导入第" + i + "行数据出现错误：" + e.getMessage() + "\n";
				}
			}
		}
		return checkResult;
	}

	/**
	 * 校验导入数据
	 * 
	 * @param content
	 *            数据内容，包含标题
	 * @param tableName
	 *            表名
	 * @return 校验的错误信息，如果校验通过则返回空
	 * @throws IOException
	 */
	private static String checkImpData(String fileName, String tableName) throws IOException {
		String checkResult = "";

		// 默认只读取第一个Sheet页的内容
		Workbook wb = new XSSFWorkbook(fileName);
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum(); // 得到总行数

		// 校验导入的文件是否为空
		if (rowNum == 0) {
			checkResult += "导入的文件不可为空！\n";
			wb.close();
			return checkResult;
		}

		// 校验导入的文件的标题顺序和模板文件是否相同
		// 1.获取模板文件中字段的中文名称
		List<List<String>> fieldList = getExcelTitles(tableName);
		List<String> cfield = new ArrayList<String>();
		for (int i = 0; i < fieldList.size(); i++) {
			List<String> fList = fieldList.get(i);
			cfield.add(fList.get(1));
		}
		// 2.获取导入文件中的标题
		Row row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();// 得到列数
		int j = 0;
		List<String> titleList = new ArrayList<String>(); // 标题数据的值
		while (j < colNum) {
			String cellValue = row.getCell(j).getStringCellValue();
			titleList.add(cellValue);
			j++;
		}
		// 3.校验导入文件的标题数和模板文件的字段数是否一致
		if (cfield.size() != titleList.size()) {
			checkResult += "导入的文件与模板文件不符！\n";
			wb.close();
			return checkResult;
		}
		// 4.校验字段是否一致
		for (int i = 0; i < cfield.size(); i++) {
			if (cfield.get(i).equals(titleList.get(i))) {
				continue;
			} else {
				checkResult += "导入的文件与模板文件不符！\n";
				wb.close();
				return checkResult;
			}
		}
		// 5.校验每个单元格的格式是否为字符串格式
		// 正文内容从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int k = 0;
			while (k < colNum) {
				Cell cell = row.getCell(k);
				int cellType = cell.getCellType();
				if (cellType != XSSFCell.CELL_TYPE_STRING) {
					checkResult += "请将第" + (i + 1) + "行第" + (k + 1) + "列的单元格格式改为字符串格式\n";
				}
				k++;
			}
		}
		wb.close();
		return checkResult;
	}

	/**
	 * 读取Excel文件内容
	 * 
	 * @param fileName
	 *            带路径的文件名
	 * @return 带有标题的Excel内容
	 * @throws Exception
	 * @throws IOException
	 */
	private static List<List<String>> readExcel(String fileName) throws IOException {
		List<List<String>> content = new ArrayList<List<String>>();
		Workbook wb = new XSSFWorkbook(fileName);

		// 默认只读取第一个Sheet页的内容
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum(); // 得到总行数
		Row row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();// 得到列数

		// 正文内容从第二行开始,第一行为表头的标题
		for (int i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			List<String> valueList = new ArrayList<String>(); // 每一行数据的值
			while (j < colNum) {
				Cell cell = row.getCell(j);
				String cellValue = cell.getStringCellValue();
				valueList.add(cellValue);
				j++;
			}
			content.add(valueList);
		}

		// 关闭流
		wb.close();
		return content;
	}

	/**
	 * Excel文件导出，支持2007以上版本
	 * 
	 * @param tableName
	 *            数据表名
	 * @param sqlWhere
	 *            查询条件
	 * @param fileType
	 *            要生成的文件类型：1-模板文件 2-数据文件 此参数的目的在于方便区分文件的类型，是否有数据体
	 * @param fileName_d
	 *            用户下载后显示的文件名(需要加上文件后缀)
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> exportExcel(String tableName, String sqlWhere, String fileType,
			String fileName_d) throws SQLException, IOException {
		// 生成Excel文件
		String fileName = createExcel(tableName, sqlWhere, fileType);
		// 下载Excel文件
		return downLoadFile(fileName, fileName_d);
	}

	/**
	 * 导入导出-生成Excel文件
	 * 
	 * @param tableName
	 *            数据表名
	 * @param sqlWhere
	 *            数据查询条件
	 * @param fileType
	 *            要生成的文件类型：1-模板文件 2-数据文件 此参数的目的在于方便区分文件的类型，是否有数据体
	 * @throws SQLException
	 * @return 带路径的Excel文件名
	 */
	private static String createExcel(String tableName, String sqlWhere, String fileType) throws SQLException {
		// 生成Excel表头
		List<List<String>> fieldList = getExcelTitles(tableName);
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < fieldList.size(); i++) {
			List<String> fList = fieldList.get(i);
			titles.add(fList.get(1));
		}
		List<List<String>> dataList = null; // 要导出的数据列表
		if (!"1".equals(fileType)) {
			dataList = new ArrayList<List<String>>();
			// 组织SQL查询数据，并重新组织数据结构
			// 1.获取要查询的字段
			String selField = ""; // 要查询的字段String
			List<String> fields = new ArrayList<String>(); // 要查询的字段List
			for (int i = 0; i < fieldList.size(); i++) {
				List<String> fList = fieldList.get(i);
				fields.add(fList.get(0));
				selField += fList.get(0) + ",";
			}
			// 2.组织SQL
			String sql = "select " + selField.subSequence(0, selField.length() - 1) + " from " + tableName + " "
					+ sqlWhere;
			// 3.查询结果集
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
			// 4.获取数据并重组数据结构
			Iterator<Map<String, Object>> iterator = rs.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> tMap = (Map<String, Object>) iterator.next();
				List<String> tmp = new ArrayList<String>();
				// 根据要查询的字段获取字段的值
				for (int i = 0; i < fields.size(); i++) {
					String fieldValue = tMap.get(fields.get(i)).toString();
					tmp.add(fieldValue);
				}
				dataList.add(tmp);
			}
		}
		return ExcelUtils.createExcel((String[]) titles.toArray(), dataList, null);

	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 *            带路径的文件名
	 * @param fileName_d
	 *            用户下载后显示的文件名(需要加上文件后缀)
	 * @throws IOException
	 * @return
	 */
	public static ResponseEntity<byte[]> downLoadFile(String fileName, String fileName_d) throws IOException {
		File file = new File(fileName);
		HttpHeaders headers = new HttpHeaders();
		fileName_d = new String(fileName_d.getBytes("UTF-8"), "iso-8859-1"); // 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName_d);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	/**
	 * 获取要导出的字段
	 * 
	 * @param tableName
	 *            表名
	 * @return {字段英文名:字段中文名}
	 */
	private static List<List<String>> getExcelTitles(String tableName) {
		// 获取数据源模板
		JdbcTemplate jdbcTemplate = getJdbcTemplate();

		// 从数据库中查询需要导出的表的字段
		String sql = "select b.efname, b.cfname from inoutdata_table a, inoutfield_table b where a.id=b.tab_id and a.ename=? order by b.id";
		List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, tableName);
		List<List<String>> fieldList = new ArrayList<List<String>>(); // 此处不能用Map，因为Map是无序的
		Iterator<Map<String, Object>> iterator = rs.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> tMap = (Map<String, Object>) iterator.next();
			String eFName = tMap.get("efname").toString(); // 字段英文名
			String cFName = tMap.get("cfname").toString(); // 字段中文名
			List<String> tmp = new ArrayList<String>();
			tmp.add(eFName);
			tmp.add(cFName);
			fieldList.add(tmp);
		}
		return fieldList;
	}

	/**
	 * 获取JDBC数据源
	 * 
	 * @return
	 */
	private static JdbcTemplate getJdbcTemplate() {
		return SpringFactory.getBean("jdbcTemplate");
	}

	/**
	 * 从磁盘中删除文件
	 * 
	 * @param
	 * @param fileName_d
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static int delFilefrmDisct(String fileName_d) {
		// 获取数据源模板
		return 1;
	}

	/**
	 * 流的方式下载文件
	 * 
	 * @param fileName
	 *            新文件名（带路径）
	 * @param oldFileName
	 *            原文件名（用于下载显示）
	 * @param request
	 * @param response
	 * @return
	 */
	public static String downLoadFileByStream(String fileName, String oldFileName, HttpServletRequest request,
			HttpServletResponse response) {
		if (oldFileName == null || oldFileName.equals("")) {
			oldFileName = "附件" + StringUtil.getFileExt(fileName);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String userAgent = request.getHeader("User-Agent");
		try {
			if (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge")) {
				oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			} else {
				oldFileName = new String(oldFileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;fileName=\"" + oldFileName + "\"");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			InputStream inputStream = new FileInputStream(new File(fileName));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 图片压缩 1、指定宽度、高度或压缩比例 的方式对图片进行压缩 2、添加图片水印
	 * 
	 * @param imgsrc
	 *            源图片地址
	 * 
	 * @param widthdist
	 *            压缩后图片宽度（当rate==null时，必传）
	 * @param heightdist
	 *            压缩后图片高度（当rate==null时，必传）
	 * @param rate
	 *            压缩比例
	 */
	public static void reduceImg(String imgsrc, int widthdist, int heightdist, Float rate) {
		log.info("imgsrc  file ======" + imgsrc);
		int a = imgsrc.lastIndexOf(".");
		String extfile = imgsrc.substring(a + 1).toLowerCase();
		String picStr = "png,jpg,jpeg,gif,bmp";
		log.info("imextfile ==extfile====" + extfile);
		log.info("imextfile ==extfile====" + picStr.indexOf(extfile));
		if (picStr.indexOf(extfile) <= -1) {
			return;
		}
		String newfile = imgsrc.substring(0, a) + "_1." + imgsrc.substring(a + 1);
		log.info("new file ======" + newfile);
		try {
			File srcFile = new File(imgsrc);
			if (!srcFile.exists()) {
				log.info("将要压缩的图片不存在");
				return;
			}
			if (null == rate || rate <= 0) {
				rate = 0.2f;
			}
			int[] fileResult = getImgWidth(srcFile);
			if (fileResult == null || fileResult[0] == 0 || fileResult[1] == 0) {
				return;
			} else {
				widthdist = (int) (fileResult[0] * rate);
				heightdist = (int) (fileResult[1] * rate);
			}
			Image src = javax.imageio.ImageIO.read(srcFile);
			BufferedImage bimg = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
			bimg.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
			bimg.getGraphics().dispose();
			ImageIO.write(bimg, "jpeg", new File(newfile));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int[] getImgWidth(File file) {

		InputStream is = null;
		BufferedImage src = null;
		int result[] = { 0, 0 };
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			result[0] = src.getWidth(null); // 得到源图宽
			result[1] = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取文件名
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		if("".equals(path)||path==null){
			return "";
		}
		String fileName=path.substring(path.lastIndexOf('/')+1);	
		fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
		return fileName;
	}
}
