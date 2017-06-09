package vip.zgt.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;

/**
 * 
 * 对文件的创建、读取、写入
 * @author Zachory
 *
 */
public class FileHandler {

	/**
	 * 创建文件
	 * @param fileName
	 * @return ifCreate
	 * @throws Exception
	 */
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 * @return content
	 */
	public static String readFile(File fileName) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return result;
	}

	/**
	 * 写入文件
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean writeToFile(String content, File fileName) throws Exception {
		RandomAccessFile raf = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("utf-8"));
			o.close();
			// raf = new RandomAccessFile(fileName,"rw");
			// raf.writeBytes(content);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (raf != null) {
				raf.close();
			}
		}
		return flag;
	}

	/**
	 * 追加写入文件
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean contentToTxt(String filePath, String content) {
		boolean flag = false;
		String str = new String(); // 原有txt内容
		String s1 = new String();// 内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.print("文件存在");
			} else {
				System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);
			input.close();
			s1 += content;
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			flag = true;
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
