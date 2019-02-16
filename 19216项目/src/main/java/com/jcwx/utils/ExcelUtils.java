package com.jcwx.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author MaBo Excel文件操作
 */
public class ExcelUtils {

	/**
	 * 设置Excel表头格式
	 * 
	 * @param wb
	 *            Excel应用文件
	 * @return
	 */
	private static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 260);
		cellStyle.setFont(font);
		// 设置单元格边框为细线框
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 设置Excel表体格式
	 * 
	 * @param wb
	 *            Excel应用文件
	 * @return
	 */
	private static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线框
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 生成Excel文件
	 * 
	 * @param titles
	 *            标题列表
	 * @param list
	 *            数据列表
	 * @param dict
	 *            字典数据，不需要则传null 如：{'性别':{'01':'男','02':'女'}} 如果不需要字典，传null即可
	 * @return
	 */
	public static String createExcel(String[] titles, List<List<String>> list, Map<String, Map<String, String>> dict) {
		// 创建一个workBook，对应一个Excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();

		// 在workBook中添加一个Sheet，对应Excel文件中的Sheet，Sheet名称可以自定义为中文名称
		XSSFSheet sheet = workBook.createSheet("Sheet1");

		// 设置单元格格式
		XSSFCellStyle headStyle = getHeadStyle(workBook);
		XSSFCellStyle bodyStyle = getBodyStyle(workBook);

		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		headRow.setHeight((short) 360);
		XSSFCell cell = null;

		// 输出标题
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}

		// 构建表体数据
		if (list != null && !list.isEmpty()) {
			for (int j = 0; j < list.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				List<String> dataList = list.get(j);
				for (int k = 0; k < dataList.size(); k++) {
					cell = bodyRow.createCell(k);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(dataList.get(k));
					sheet.setColumnWidth(k, 5000);
				}
			}
		}

		// 生成数据字典sheet页
		if (dict != null) {
			for (Map.Entry<String, Map<String, String>> entry : dict.entrySet()) {
				String sheetName = entry.getKey();
				XSSFSheet dicSheet = workBook.createSheet(sheetName);
				// 表头
				XSSFRow headRow_dict = dicSheet.createRow(0);
				headRow_dict.setHeight((short) 360);
				XSSFCell cell_dict = null;

				// 输出字典标题
				cell_dict = headRow_dict.createCell(0);
				cell_dict.setCellStyle(headStyle);
				cell_dict.setCellValue(sheetName + "编码");
				cell_dict = headRow_dict.createCell(1);
				cell_dict.setCellStyle(headStyle);
				cell_dict.setCellValue(sheetName + "值");

				// 输出字典值
				Map<String, String> dictValues = entry.getValue();
				Set<String> keySet = dictValues.keySet();
				Object[] keys = keySet.toArray();
				Arrays.sort(keys);
				for (int i = 0; i < keys.length; i++) {
					XSSFRow bodyRow_dic = dicSheet.createRow(i + 1);
					cell_dict = bodyRow_dic.createCell(0);
					cell_dict.setCellStyle(bodyStyle);
					cell_dict.setCellValue(keys[i].toString());
					dicSheet.setColumnWidth(0, 5000);

					cell_dict = bodyRow_dic.createCell(1);
					cell_dict.setCellStyle(bodyStyle);
					cell_dict.setCellValue(dictValues.get(keys[i].toString()));
					dicSheet.setColumnWidth(1, 5000);
				}

			}
		}

		// 输出Excel文件
		String suffix = "_Source.xlsx";
		String fileName = DateUtils.getNow("yyyyMMddHHmmSS") + RandomStringUtils.randomAlphanumeric(10) + suffix;

		File file = new File(ProjectUtils.getSysCfg("exportPath") + File.separator + fileName);
		// 文件目录不存在则创建
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream outputStream = null;
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			workBook.write(os);
			outputStream = new FileOutputStream(file);
			outputStream.write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ProjectUtils.getSysCfg("exportPath") + File.separator + fileName;
	}

	/**
	 * 导入文件的基本校验:文件是否为空；文件导入的行数；文件与模板是否相同；文件单元格格式是否为文本格式；
	 * 
	 * @param fileName
	 *            带路径的Excel文件名
	 * @param titles
	 *            Excel表头
	 * @return 校验结果和文件数据集 {'errMsg':校验结果,
	 *         'dataList':[{'姓名':'张三'},{'性别':'男'},...]}
	 * @throws IOException
	 */
	public static Map<String, Object> checkExcel(String fileName, String[] titles) throws IOException {
		String checkResult = ""; // 校验结果
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 文件数据列表
		Map<String, Object> resultMap = new HashMap<String, Object>(); // 函数返回对象

		// 默认只读取第一个Sheet页的内容
		Workbook wb = new XSSFWorkbook(fileName);
		Sheet sheet = wb.getSheetAt(0);

		int rowNum = sheet.getLastRowNum(); // 得到总行数

		// 校验导入的文件是否为空，是否超出限定的行数
		if (rowNum == 0) {
			checkResult += "导入的文件不可为空！\r" + System.getProperty("line.separator");
			wb.close();
			resultMap.put("errMsg", checkResult);
			resultMap.put("dataList", list);
			return resultMap;
		} else if (rowNum > 5001) {
			checkResult += "每次最多导入5000行数据！\r" + System.getProperty("line.separator");
			wb.close();
			resultMap.put("errMsg", checkResult);
			resultMap.put("dataList", list);
			return resultMap;
		}

		// 校验导入的文件的标题顺序和模板文件是否相同
		// 1.获取导入文件中的标题
		Row row = sheet.getRow(0); // 第一行数据为标题
		int colNum = row.getPhysicalNumberOfCells(); // 列数
		int j = 0;
		List<String> titleList = new ArrayList<String>(); // 标题数据的列表
		while (j < colNum) {
			String cellValue = row.getCell(j).getStringCellValue();
			titleList.add(cellValue);
			j++;
		}
		// 2.校验导入文件的标题数和模板文件的字段数是否一致
		// if (titles.length != titleList.size()) {
		// checkResult += "导入的文件表头与模板文件表头不符！\r" +
		// System.getProperty("line.separator");
		// wb.close();
		// resultMap.put("errMsg", checkResult);
		// resultMap.put("dataList", list);
		// return resultMap;
		// }
		// 3.校验字段是否一致
		// for (int i = 0; i < titles.length; i++) {
		// if (!titles[i].equals(titleList.get(i))) {
		// checkResult += "导入的文件表头与模板文件表头不符！【第" + (i + 1) + "列】\r" +
		// System.getProperty("line.separator");
		// }
		// }
		// if (!"".equals(checkResult)) {
		// wb.close();
		// resultMap.put("errMsg", checkResult);
		// resultMap.put("dataList", list);
		// return resultMap;
		// }
		// 4.校验每个单元格的格式是否为字符串格式
		// 正文内容从第二行开始,第一行为表头的标题
		/*
		 * for (int i = 1; i <= rowNum; i++) { row = sheet.getRow(i); int k = 0;
		 * while (k < colNum) { Cell cell = row.getCell(k); int cellType =
		 * cell.getCellType(); if(cellType != XSSFCell.CELL_TYPE_BLANK){
		 * if(cellType != XSSFCell.CELL_TYPE_STRING){ checkResult += "请将第" +
		 * (i+1) + "行第" + (k+1) + "列的单元格格式改为文本格式\n"; } } k++; } }
		 */
		// 5.基本校验通过，组织内容数据
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			if(row == null ){
				continue;
			}
			int k = 0;
			Map<String, String> tMap = new HashMap<String, String>();
			while (k < colNum) {
				Cell cell = row.getCell(k);
				if (row.getCell(k) != null) {
					row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
				}
				try {
					String cellValue ="";
					if(cell == null){
						cellValue ="";
					}else{
					 cellValue = cell.getStringCellValue();
					}
					tMap.put(titles[k], cellValue);
				} catch (Exception e) {
					tMap.put(titles[k], null);
					e.printStackTrace();
					// checkResult += "【第"+i+"行】 【第" + (k+1) +
					// "列】为空或数据格式不正确，请设置为文本格式\r" +
					// System.getProperty("line.separator");
				}
				k++;
			}
			list.add(tMap);
		}
		wb.close();
		resultMap.put("errMsg", checkResult);
		resultMap.put("dataList", list);
		return resultMap;
	}

	/**
	 * 生成Excel文件
	 * 
	 * @param titles
	 *            标题列表
	 * @param data
	 *            数据列表
	 * @param dict
	 *            字典数据，不需要则传null 如：{'性别':{'01':'男','02':'女'}} 如果不需要字典，传null即可
	 * @return
	 */
	public static String createExcel1(String[] titles, List<List<Object>> data, Map<String, Map<String, String>> dict) {
		// 创建一个workBook，对应一个Excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();

		// 在workBook中添加一个Sheet，对应Excel文件中的Sheet，Sheet名称可以自定义为中文名称
		XSSFSheet sheet = workBook.createSheet("Sheet1");

		// 设置单元格格式
		XSSFCellStyle headStyle = getHeadStyle(workBook);
		XSSFCellStyle bodyStyle = getBodyStyle(workBook);

		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		headRow.setHeight((short) 360);
		XSSFCell cell = null;

		// 输出标题
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}

		// 构建表体数据
		if (data != null && !data.isEmpty()) {
			for (int j = 0; j < data.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				List<Object> dataList = data.get(j);
				for (int k = 0; k < dataList.size(); k++) {
					cell = bodyRow.createCell(k);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(dataList.get(k).toString());
					sheet.setColumnWidth(k, 5000);
				}
			}
		}

		// 生成数据字典sheet页
		if (dict != null) {
			for (Map.Entry<String, Map<String, String>> entry : dict.entrySet()) {
				String sheetName = entry.getKey();
				XSSFSheet dicSheet = workBook.createSheet(sheetName);
				// 表头
				XSSFRow headRow_dict = dicSheet.createRow(0);
				headRow_dict.setHeight((short) 360);
				XSSFCell cell_dict = null;

				// 输出字典标题
				cell_dict = headRow_dict.createCell(0);
				cell_dict.setCellStyle(headStyle);
				cell_dict.setCellValue(sheetName + "编码");
				cell_dict = headRow_dict.createCell(1);
				cell_dict.setCellStyle(headStyle);
				cell_dict.setCellValue(sheetName + "值");

				// 输出字典值
				Map<String, String> dictValues = entry.getValue();
				Set<String> keySet = dictValues.keySet();
				Object[] keys = keySet.toArray();
				Arrays.sort(keys);
				for (int i = 0; i < keys.length; i++) {
					XSSFRow bodyRow_dic = dicSheet.createRow(i + 1);
					cell_dict = bodyRow_dic.createCell(0);
					cell_dict.setCellStyle(bodyStyle);
					cell_dict.setCellValue(keys[i].toString());
					dicSheet.setColumnWidth(0, 5000);

					cell_dict = bodyRow_dic.createCell(1);
					cell_dict.setCellStyle(bodyStyle);
					cell_dict.setCellValue(dictValues.get(keys[i].toString()));
					dicSheet.setColumnWidth(1, 5000);
				}

			}
		}

		// 输出Excel文件
		String suffix = "_Source.xlsx";
		String fileName = DateUtils.getNow("yyyyMMddHHmmSS") + RandomStringUtils.randomAlphanumeric(10) + suffix;

		File file = new File(ProjectUtils.getSysCfg("exportPath") + File.separator + fileName);
		// 文件目录不存在则创建
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream outputStream = null;
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			workBook.write(os);
			outputStream = new FileOutputStream(file);
			outputStream.write(os.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ProjectUtils.getSysCfg("exportPath") + File.separator + fileName;
	}
}
