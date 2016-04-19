package com.baiyi.order.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Material;
import com.baiyi.order.program.ProgramGloable;

public class Utility {
	/**
	 * 过滤html标签
	 * 
	 * @param content
	 * @return
	 */
	public static String parseHtml(String content) {
		String htmlStr = content; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 获取指定xml文档的Document对象,xml文件必须在classpath中可以找到
	 * 
	 * @param xmlFilePath
	 *            xml文件路径
	 * @return Document对象
	 */
	public static Document parse2Document(String xmlFilePath) throws DocumentException, FileNotFoundException, IOException {
		SAXReader reader = new SAXReader();
		Document document = null;
		File file = new File(xmlFilePath);
		InputStream in = new FileInputStream(file);
		document = reader.read(in);
		in.close();
		return document;
	}

	/**
	 * 解析RSS新闻
	 * 
	 * @param rssContent
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parseRss(String rssContent) throws Exception {
		Document doc = DocumentHelper.parseText(rssContent);
		Element root = doc.getRootElement();// 取得根节点
		Element channelElement = root.element("channel");
		List<Element> itemList = channelElement.elements();

		JSONObject resultObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Element item : itemList) {
			if (item.getName().equals("item")) {
				String title = item.element("title").getData().toString().replaceAll("\\s", "").trim();
				String description = item.element("description").getData().toString().replaceAll("[\\s\\n\\r]*", "").replaceAll(" ", "").trim();
				if (title == null || title.equals("") || description == null || description.equals("")) {
					continue;
				}
				// 根據ID存放RSS新聞
				JSONObject obj = new JSONObject();
				obj.put("title", title);
				obj.put("description", description);
				jsonArray.add(obj);
				if (jsonArray.size() >= 20) {
					break;
				}
			}
		}
		if (jsonArray.size() > 0) {
			resultObject.put("rss", jsonArray);
			resultObject.put("success", true);
		} else {
			resultObject.put("success", false);
		}
		return resultObject;
	}

	public static void main(String[] args) {
		// test3();
		try {
			// parseYahooWeather();
			String string = "18:45:00;20:38:00;20:50:00";
			System.out.println(timeSortFirst(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检测上传的素材类型
	 * 
	 * @param fileName
	 * @return 1图片 2视频
	 */
	public static int checkType(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if (BeanUtil.pictureList != null && BeanUtil.vedioList != null && BeanUtil.musicList != null) {
			if (BeanUtil.pictureList.contains(fileName)) {
				return Material.TYPE_IMAGE;
			} else if (BeanUtil.vedioList.contains(fileName)) {
				return Material.TYPE_MOVIE;
			} else if (BeanUtil.musicList.contains(fileName)) {
				return 3;
			}
		} else {
			addPictureType();
			addMusicType();
			addVideoType();
			checkType(fileName);
		}
		return 0;
	}

	public static void addPictureType() {
		BeanUtil.pictureList.add("jpg");
		BeanUtil.pictureList.add("jpeg");
		BeanUtil.pictureList.add("bmp");
		BeanUtil.pictureList.add("png");
		BeanUtil.pictureList.add("gif");
	}

	public static void addVideoType() {
		BeanUtil.vedioList.add("mpg");
		BeanUtil.vedioList.add("wmv");
		BeanUtil.vedioList.add("3gp");
		BeanUtil.vedioList.add("mov");
		BeanUtil.vedioList.add("mp4");
		// BeanUtil.vedioList.add("asf");
		// BeanUtil.vedioList.add("asx");
		BeanUtil.vedioList.add("flv");
		// BeanUtil.vedioList.add("wmv9");
		// BeanUtil.vedioList.add("rm");
		// BeanUtil.vedioList.add("rmvb");
		BeanUtil.vedioList.add("avi");
		BeanUtil.vedioList.add("vob");
	}

	public static void addMusicType() {
		BeanUtil.musicList.add("mp3");
	}

	/**
	 * 檢查參數
	 * 
	 * @param param
	 * @return
	 */
	public static Object checkParmeter(Object param) {

		if (param instanceof String) {
			String result = (String) param;
			return result != null && result.trim().length() != 0 ? result.trim() : null;
		}
		return null;
	}

	/**
	 * 獲取數組參數的值
	 * 
	 * @param param
	 * @return
	 */
	public static String getArrayValue(String[] param) {
		if (param == null) {
			return null;
		} else {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < param.length; i++) {
				result.append(param[i] + ";");
			}
			return result.toString();
		}
	}

	/**
	 * 檢測URL地址是否正確
	 * 
	 * @param url
	 * @return
	 */
	public static boolean checkUrl(String url) {
		Matcher urlMat = urlPattern.matcher(url);
		return urlMat.find();
	}

	/**
	 * 检测IP地址是否正确
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean checkIPAddress(String ip) {
		ip = (String) Utility.checkParmeter(ip);
		if (ip == null) {
			return false;
		}
		Matcher ipMat = ipPattern.matcher(ip);
		return ipMat.find();
	}

	/**
	 * 格式化日期yyyyMMDD
	 * 
	 * @param date
	 * @return
	 */
	public static String getStrDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		if (date == null) {
			date = new Date();
		}
		return dateFormat.format(date);
	}

	/**
	 * 驗證日期yyyy-MM-dd
	 * 
	 * @param dateString
	 * @return
	 */
	public static boolean validate(String dateString) {

		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if (!m.matches()) {
			return false;
		} // 得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);
		if (month < 1 || month > 12) {
			return false;
		}
		int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(year)) {
			monthLengths[2] = 29;
		} else {
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if (day < 1 || day > monthLength) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是闰年
	 */
	private static boolean isLeapYear(int year) {
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * 过滤页面
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean isPass(String uri) {
		// 需要登陸才能訪問的頁面地址判斷
		if (uri.indexOf("manage") > 0 || (uri.indexOf(".do") >= 0 && uri.indexOf("login") < 0 && uri.indexOf("/order/") < 0 && uri.indexOf("/json/") < 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取配置信息
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String[] getInitProperties(String path) {
		Properties prop = new Properties();
		InputStream in = null;
		String[] config = new String[8];
		try {
			in = new BufferedInputStream(new FileInputStream(path + "WEB-INF" + File.separator + "classes" + File.separator + "init.properties"));
			prop.load(in);
			String username = prop.getProperty("datasource.username").trim();
			String password = prop.getProperty("datasource.password").trim();
			String url = prop.getProperty("datasource.url").trim();
			String location = prop.getProperty("location");
			String ip = prop.getProperty("ip");
			String position = prop.getProperty("position");// local(终端) 与
			// server(服务端)
			String title = prop.getProperty("title");
			String detectholdday = prop.getProperty("detectholdday");// 檢測記錄保存時間
			String cusurl = prop.getProperty("cusurl");
			if (detectholdday == null || detectholdday.trim().length() == 0) {
				detectholdday = "7";// 預設七天
				PropertiesReadAndWrite.writeProperties(path + "WEB-INF" + File.separator + "classes" + File.separator + "init.properties", "detectholdday", detectholdday);
			}
			config[0] = username;
			config[1] = password;
			config[2] = url;
			config[3] = location;
			config[4] = ip;
			config[5] = position;
			config[6] = null;// 服務端IP現在無用
			config[7] = title;
			String filePath = BeanUtil.path + "serverid.txt";
			BeanUtil.serverId = IOUtil.readerFile(filePath);
			BeanUtil.serverVersion = IOUtil.readerFile(BeanUtil.path + "version.txt");
			if (cusurl != null) {
				BeanUtil.cusurl = cusurl;
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("Utility getInitProperties ERROR", e);
		}
		return config;
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String sendRequest(String url, String param) throws IOException, Exception {
		StringBuilder builder = new StringBuilder();
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(ProgramGloable.POST_URL + url);
			// 打开连接
			connection = (HttpURLConnection) postUrl.openConnection();
			// Output to the connection. Default is
			// false, set to true because post
			// method must write something to the
			// connection
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post cannot use caches
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// This method takes effects to
			// every instances of this class.
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);

			// This methods only
			// takes effacts to this
			// instance.
			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// Set the content type to urlencoded,
			// because we will write
			// some URL-encoded content to the
			// connection. Settings above must be set before connect!
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			// String content = "username=" + URLEncoder.encode(param, "utf-8");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(param);
			out.flush();
			out.close(); // flush and close
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			String line = "";

			// System.out.println("=============================");
			// System.out.println("Contents of post request");
			// System.out.println("=============================");
			while ((line = reader.readLine()) != null) {
				// line = new String(line.getBytes(), "utf-8");
				// System.out.println(line);
				builder.append(line);
			}
			// System.out.println("=============================");
			// System.out.println("Contents of post request ends");
			// System.out.println("=============================");

		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return builder.toString();
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String sendRequest2(String url, String param) throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			connection = (HttpURLConnection) postUrl.openConnection();
			// Output to the connection. Default is
			// false, set to true because post
			// method must write something to the
			// connection
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post cannot use caches
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// This method takes effects to
			// every instances of this class.
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);

			// This methods only
			// takes effacts to this
			// instance.
			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// Set the content type to urlencoded,
			// because we will write
			// some URL-encoded content to the
			// connection. Settings above must be set before connect!
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			// String content = "username=" + URLEncoder.encode(param, "utf-8");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(param);
			out.flush();
			out.close(); // flush and close
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			String line = "";

			// System.out.println("=============================");
			// System.out.println("Contents of post request");
			// System.out.println("=============================");
			while ((line = reader.readLine()) != null) {
				// line = new String(line.getBytes(), "utf-8");
				// System.out.println(line);
				builder.append(line);
			}
			// System.out.println("=============================");
			// System.out.println("Contents of post request ends");
			// System.out.println("=============================");

		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return builder.toString();
	}

	public static void test3() {
		// 标点也匹配
		String str = "123abc这个中文cde123abc也要提始發地！！取123ab";
		Pattern p = null;
		Matcher m = null;
		String value = null;
		p = Pattern.compile("([\u4e00-\u9fa5]+)");
		m = p.matcher(str);
		while (m.find()) {
			value = m.group(0);
			System.out.println(value);
		}
	}

	public static String getChartAtStr(String str, int index) {
		if (str == null) {
			return "";
		} else if (index < str.length()) {
			return String.valueOf(str.charAt(index));
		}
		return "";
	}

	/**
	 * 填充位数
	 * 
	 * @param str
	 * @param number
	 * @return
	 */
	public static String fillString(String str, int number) {
		if (str == null) {
			str = "";
		}
		if (str.length() < number) {
			int index = number - str.length();
			for (int i = 0; i < index; i++) {
				str = "&nbsp;" + str;
			}
		}
		return str;
	}

	public static String fillNum(int num) {
		if (num < 10) {
			return "0" + num;
		} else {
			return String.valueOf(num);
		}
	}

	/**
	 * 返回節目uri地址中的目錄名
	 * 
	 * @param url
	 *            program//hotel_B_20120405170204171//index.jsp
	 * @return hotel_B_20120405170204171
	 */
	public static String getProgramUri(String url) {
		Pattern pattern = Pattern.compile("[\\w]+[\\d]+");
		Matcher mat = pattern.matcher(url);
		String tempUrl = "";
		if (mat.find()) {
			tempUrl = mat.group();
		}
		return tempUrl;
	}

	public static void writeObject(String totalPath, JSONObject object) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			File totalSBFile = new File(totalPath);
			if (!totalSBFile.exists()) {
				totalSBFile.createNewFile();
				fos = new FileOutputStream(totalPath);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(new ArrayList<Float>());
				oos.close();
				fos.close();
			}
			fos = new FileOutputStream(totalPath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONObject readObject(String filePath) {
		JSONObject object = new JSONObject();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filePath);
			ois = new ObjectInputStream(fis);
			object = (JSONObject) ois.readObject();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	/**
	 * 時間排序
	 * 
	 * @param param
	 * @return
	 */
	public static String timeSortFirst(String param) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar nowTime = Calendar.getInstance();
		nowTime.set(Calendar.MINUTE, nowTime.get(Calendar.MINUTE) - 1);// 现在时间往前延迟一分钟,
		String nowDay = df.format(new Date());
		DateFormat df2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		if (param != null) {
			if (param.indexOf(";") != -1) {
				String[] timeArr = param.split(";");
				String result = null;
				for (String time : timeArr) {
					try {
						if (df2.parse(nowDay + " " + time).after(nowTime.getTime())) {
							if (result == null) {
								result = time;
							} else {
								if (df2.parse(nowDay + " " + time).before(df2.parse(nowDay + " " + result))) {
									result = time;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (result == null) {
					Calendar cal2 = Calendar.getInstance();
					cal2.set(Calendar.DAY_OF_YEAR, cal2.get(Calendar.DAY_OF_YEAR) + 1);
					String tomorrowDay = df.format(cal2.getTime());
					for (String time : timeArr) {
						try {
							if (df2.parse(tomorrowDay + " " + time).after(nowTime.getTime())) {
								if (result == null) {
									result = time;
								} else {
									if (df2.parse(tomorrowDay + " " + time).before(df2.parse(tomorrowDay + " " + result))) {
										result = time;
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return result;
			} else {
				return param;
			}
		}
		return null;
	}

	public static Pattern ipPattern = Pattern.compile("^([\\d]{1,3}\\.){3}[\\d]{1,3}$");

	public static Pattern urlPattern = Pattern.compile("http://.*");

	public static Pattern datePattern = Pattern.compile("[\\d]{4}-[0|1]{1}[0|9]");

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMDD");

}
