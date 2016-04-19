package com.baiyi.order.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.web.context.WebApplicationContext;

import com.baiyi.order.program.UserDownBean;
import com.baiyi.order.vo.ConvertInfo;

public class BeanUtil {

	// 登陸頁面LOGO
	public static String loginLogo = "sysimg/login";

	// 首頁LOGO
	public static String indexLogo = "sysimg/index";

	public static final String WEBSITE = "CakeOrder";

	// 存儲圖片類型
	public static final List<String> pictureList = new ArrayList<String>();

	// 存儲視頻格式
	public static final List<String> vedioList = new ArrayList<String>();

	// 存儲音頻格式
	public static final List<String> musicList = new ArrayList<String>();

	// 存儲星期
	public static final Map<String, Integer> dayMap = new HashMap<String, Integer>();

	// 終端校正時間記錄
	public static final Map<String, Boolean> checkTimeMap = new HashMap<String, Boolean>();

	// 終端重啟記錄
	public static final Map<String, Boolean> rebootMap = new HashMap<String, Boolean>();

	// 終端啟動遠端
	public static final Map<String, Boolean> bootTeamViewer = new HashMap<String, Boolean>();

	// 終端關閉遠端
	public static final Map<String, Boolean> closeTeamViewer = new HashMap<String, Boolean>();

	// 終端關機記錄
	public static final Map<String, Boolean> shutDownMap = new HashMap<String, Boolean>();

	// 开机时间
	public static final Map<String, String> bootMap = new HashMap<String, String>();

	// 終端檢測未聯網的時間間隔,超過該時間則重啟
	public static final Map<String, Integer> terminalDetectRebootTimePeriodMap = new HashMap<String, Integer>();

	public static final Map<String, UserDownBean> downMap = new HashMap<String, UserDownBean>();

	public static Map<String, Map<String, Map<String, String>>> multiLanguage = new HashMap<String, Map<String, Map<String, String>>>();

	public static WebApplicationContext ctx;// spring

	public static String path;// 服务端tomcat路径-->D:\apache-tomcat-6.0.32\webapps\TopSetWebNet\

	public static String serverIp = "localhost";// server端IP地址 可由配置取得

	// 是否在本机上运行
	public static boolean isAvailableMachine = false;

	// 允許的最大連接數
	public static int availableCount = 0;

	// ZH_CN或ZH_TW
	public static String country = "";

	// 城市位置
	public static Set<String> cityLocation = new HashSet<String>();

	public static boolean isBus = false;

	// 是否婚宴
	public static boolean isWed = false;

	// jsp title
	public static String title = "";

	// 驗證後臺網址
	public static String cusurl = "";

	// 服務端ID號
	public static String serverId = "";

	// 服務端版本號
	public static String serverVersion = "";

	// 服務端是否有插入加密狗
	public static boolean isDog = false;

	// 通信
	public static ObjectInputStream ois;

	public static ObjectOutputStream oos;

	public static String language = "";// 簡體zh_CN繁體zh_TW英文en_US

	// 当前操作系统
	public static String os = "";

	static {
		pictureList.add("jpg");
		pictureList.add("jpeg");
		pictureList.add("bmp");
		pictureList.add("png");
		pictureList.add("gif");

		vedioList.add("mpg");
		vedioList.add("3gp");
		vedioList.add("mov");
		vedioList.add("flv");
		vedioList.add("avi");
		vedioList.add("vob");
		vedioList.add("wmv");
		vedioList.add("mp4");

		musicList.add("mp3");
		//		
		// officeList.add("doc");
		// officeList.add("xls");
		// officeList.add("ppt");
		// officeList.add("docx");
		// officeList.add("xlsx");
		// officeList.add("pptx");
		// officeList.add("pdf");
		// officeList.add("swf");
		// officeList.add("pps");

		dayMap.put("星期日", 1);
		dayMap.put("星期一", 2);
		dayMap.put("星期二", 3);
		dayMap.put("星期三", 4);
		dayMap.put("星期四", 5);
		dayMap.put("星期五", 6);
		dayMap.put("星期六", 7);

		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("windows")) {
			BeanUtil.os = "windows";
		} else if (os.toLowerCase().startsWith("linux")) {
			BeanUtil.os = "linux";
		}
	}

	// 判断是否有视频在转换
	public static boolean isTransform = false;

	// 存储转换视频列表
	public static ConcurrentLinkedQueue<ConvertInfo> linkedQueue = new ConcurrentLinkedQueue<ConvertInfo>();

	// 備份文件列表
	public static List<String> backupList = new ArrayList<String>();
	static {
		backupList.add("config");// 模板配置
		backupList.add("images");// 圖片素材
		backupList.add("program");// 節目
		backupList.add("template");// 模板文件
		backupList.add("vedios");// 視訊素材
		backupList.add("music");// 音樂素材
		backupList.add("office");// 文檔素材
		backupList.add("tvset.sql");
	}
}
