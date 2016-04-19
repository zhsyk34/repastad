package com.baiyi.order.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class languageReadUtil {
	
	
	/**
	 * 读取xml文件转成字符串
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String readConfig2String(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "utf-8");
		BufferedReader buffered = new BufferedReader(isr);
		String tmp = null;
		StringBuilder sb = new StringBuilder();
		while ((tmp = buffered.readLine()) != null) {
			sb.append(tmp);
			sb.append("\n");
		}
		buffered.close();
		isr.close();
		fis.close();
		return sb.toString();
	}
	
	
	/**
	 * 读取配置文件获得模板所需的元素
	 * 
	 * @param configPath
	 */
	public static boolean readLanguage() {
		try{
			String[] fileList = new String[]{
					BeanUtil.path + "manage" + File.separator + "language" + File.separator+ "zh-cn.xml",
					BeanUtil.path + "manage" + File.separator + "language" + File.separator+ "zh-tw.xml",
					BeanUtil.path + "manage" + File.separator + "language" + File.separator+ "en-us.xml"
			};
			for(String file:fileList){
				String local = new File(file).getName().replace(".xml", "");
				String configContent = languageReadUtil.readConfig2String(file);
				//存放单个语言map
				Map<String,Map<String,String>> oneLanMap = new HashMap<String,Map<String,String>> ();
				if (configContent != null && configContent.length() != 0) {
					Document doc = DocumentHelper.parseText(configContent);
					Element language = doc.getRootElement();// 取得根节点
					if(language!=null){
						List<Element> listPage = language.elements();
						for(Element page:listPage){
							Map<String,String> map = new HashMap<String, String>();
							if(page!=null){
								List<Element> listWord = page.elements();
								for(Element word:listWord){
									map.put(word.getName(), word.getText());
									//System.out.println(word.getName()+":"+word.getText());
								} 
							}
							oneLanMap.put(page.getName(), map);
						} 
					}
					BeanUtil.multiLanguage.put(local, oneLanMap);
				}
			}
			System.out.println("read language "+BeanUtil.language);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String args[]){
		BeanUtil.path = "D:\\tomcat5.5.27\\webapps\\TopSetWebNetBef\\";
		BeanUtil.language = "zh_TW";
		readLanguage();
	}
}
