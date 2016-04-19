package com.baiyi.order.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class IOUtil {

	/**
	 * 写文件
	 * @param filePath
	 * @param append
	 * @param content
	 */
	
	public static void writerFile(String filePath,boolean append,String content){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer =null;
		try {
			writer = new FileWriter(file,append);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void writerFile(String filePath,boolean append,String content,String code){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Writer writer=null;
		try {
			writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(filePath),code));
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 读取文件
	 * @param filePath
	 * @return
	 */
	public static String readerFile(String filePath){
		StringBuilder result = new StringBuilder();
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			String encoding = "utf-8"; // 字符编码(可解决中文乱码问题 )
			if (file.isFile() && file.exists()) {
				inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
				bufferedReader = new BufferedReader(inputStreamReader);
				String lineTXT = null;
				while ((lineTXT = bufferedReader.readLine()) != null) {
					result.append(lineTXT);
				}
			}
		} catch (IOException ioe) {
			Log4JFactory.instance().error(ioe);
			System.out.println("ERROR:讀取文件出錯，文件："+file.getAbsolutePath());
		} finally{
			try {
				if(bufferedReader!=null){
						bufferedReader.close();
				}
				if(inputStreamReader!=null ){
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result.toString();
	}
	
}
