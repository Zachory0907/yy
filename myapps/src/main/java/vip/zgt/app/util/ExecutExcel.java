package vip.zgt.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 通过POI方式操作Excel文件 往已存在的excel里面读、写内容
 * 
 * @author 赵郭桃
 *
 */
public class ExecutExcel {

	public static void main(String[] args) {

	}

	/**
	 * 往单一页签里写
	 * <table border="1" cellspacing="0">
	 * <tr>
	 * <td>srcFile</td>
	 * <td>往哪儿写</td>
	 * </tr>
	 * <tr>
	 * <td>sheetNum</td>
	 * <td>往第几个页签里写（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>rowStart</td>
	 * <td>从第几行开始往里写（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>colStart</td>
	 * <td>从第几列开始往里写（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>content</td>
	 * <td>想要写的内容，注意是排好顺序的List<List<String>></td>
	 * </tr>
	 * </table>
	 * 
	 * @author 赵郭桃
	 */
	public static boolean writeToSingleExcel(String srcFile, int sheetNum, int rowStart, int colStart,
			List<List<String>> content) {
		File file = new File(srcFile);
		try {
			InputStream inputStream = new FileInputStream(file);
			HSSFWorkbook xssfWorkbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum); // 拿到第sheetNum个sheet
			if (content.isEmpty()) {
				return false;
			}
			int rowCount = content.size();
			List<String> list = content.get(0);
			if (list.isEmpty()) {
				return false;
			}
			int colCount = list.size();
			// i, j 是表格循环; x, y 是List<>循环
			for (int i = rowStart, m = rowCount + rowStart, x = 0; i < m; i++, x++) {
				for (int j = colStart, n = colCount + colStart, y = 0; j < n; j++, y++) {
					try {
						String con = content.get(x).get(y);
						sheet.getRow(i).getCell(j).setCellValue(con);
						String v = sheet.getRow(i).getCell(j).getStringCellValue();
						System.out.println("(" + i + ", " + j + "):" + v);
					} catch (Exception e) {
						continue;
					}
				}
			}
			FileOutputStream fos = new FileOutputStream(srcFile);
			xssfWorkbook.write(fos);
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到！");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("写入Excel异常！");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从单一页签里读
	 * <table border="1" cellspacing="0">
	 * <tr>
	 * <td>srcFile</td>
	 * <td>要读的文件</td>
	 * </tr>
	 * <tr>
	 * <td>sheetNum</td>
	 * <td>读第几个页签（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>rowStart</td>
	 * <td>从第几行开始读（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>colStart</td>
	 * <td>从第几列开始读（0开始）</td>
	 * </tr>
	 * <tr>
	 * <td>headList</td>
	 * <td>表头信息</td>
	 * </tr>
	 * </table>
	 * 
	 * @author 赵郭桃
	 */
	public static List<List<Map<String, Object>>> readFromSingleExcel(String srcFile, int sheetNum, int rowStart,
			int colStart, String[] headList) {
		File file = new File(srcFile);
		try {
			InputStream inputStream = new FileInputStream(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum); // 拿到第sheetNum个sheet
			List<List<Map<String, Object>>> results = new ArrayList<List<Map<String, Object>>>();
			for (Row row : sheet) {
				List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
				if (row.getRowNum() < rowStart) {
					continue;
				}
				for (Cell hssfCell : row) {
					if (hssfCell.getColumnIndex() < colStart) {
						continue;
					}
					Object cont = null;
					Map<String, Object> map = new HashMap<String, Object>();
					if (headList == null || headList.length == 0) {
						// 说明没有HeadList
						if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
							cont = hssfCell.getBooleanCellValue();
						} else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							cont = hssfCell.getNumericCellValue();
						} else if (hssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
							cont = hssfCell.getStringCellValue();
						}
						map.put(String.valueOf(hssfCell.getColumnIndex()), cont);
					} else {
						if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
							cont = hssfCell.getBooleanCellValue();
						} else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							cont = hssfCell.getNumericCellValue();
						} else if (hssfCell.getCellType() == Cell.CELL_TYPE_STRING) {
							cont = hssfCell.getStringCellValue();
						}
						String key = null;
						try {
							key = headList[(hssfCell.getColumnIndex() - colStart)];
						} catch (Exception e) {
							key = String.valueOf(hssfCell.getColumnIndex());
						}
						if (key == null) {
							key = String.valueOf(hssfCell.getColumnIndex());
						}
						map.put(key, cont);
					}
					content.add(map);
				}
				results.add(content);
			}
			return results;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}