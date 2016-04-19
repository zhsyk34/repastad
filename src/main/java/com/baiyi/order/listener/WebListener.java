package com.baiyi.order.listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baiyi.order.service.SystemConfigService;
import com.baiyi.order.socket.ServerListener;
import com.baiyi.order.util.BackupAutoAndClearDetectThread;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.FileUtil;
import com.baiyi.order.util.JDBCUtil;
import com.baiyi.order.util.Utility;
import com.baiyi.order.util.ValidateRunnable;
import com.baiyi.order.util.languageReadUtil;

public class WebListener implements ServletContextListener {

	private ServletContext context;

	private String path;

	private SystemConfigService systemConfigService;

	public void contextDestroyed(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = (WebApplicationContext) event.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		path = context.getRealPath("/");
		BeanUtil.path = path;// D:\apache-tomcat-6.0.32\webapps\TopSetWebNet\//tomcat路径
		BeanUtil.ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		String[] result = Utility.getInitProperties(path);
		// 获得配置信息
		JDBCUtil.username = result[0];
		JDBCUtil.password = result[1];
		JDBCUtil.url = result[2];
		BeanUtil.country = result[3];// location
		context.setAttribute("projectTitle", result[7]);// title
		// 讀取權限配置
		Map<String, Integer> map = readOauth();
		context.setAttribute("oauth", map);
		// 讀取語言
		languageReadUtil.readLanguage();// manage/language/zh-cn.xml...
		// copyjsyunew3();

		// 服務端驗證
		ValidateRunnable vrun = new ValidateRunnable(result[4], context);// ip
		Thread vThread = new Thread(vrun);
		vThread.start();
		// 终端检测
		ServerListener listener = new ServerListener(event.getServletContext());
		listener.start();
		// 定時刪除檢測記錄
		BackupAutoAndClearDetectThread bdt = new BackupAutoAndClearDetectThread();
		bdt.start();
	}

	private void copyjsyunew3() {
		// ----------------加密狗初始化獲取終端機編號---------------------
		if (BeanUtil.os.equals("windows")) {
			File dogFile = new File(System.getProperty("java.home") + File.separator + "bin" + File.separator + "jsyunew3.dll");
			File libjsy = new File(BeanUtil.path + "jsyunew3.dll");
			if (libjsy.exists() && (!dogFile.exists())) {
				try {
					FileUtil.copyDirectory(libjsy, dogFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (BeanUtil.os.equals("linux")) {
			File dogFile = new File("/usr/lib/jvm/java-6-openjdk/jre/lib/i386/libJsyunew3.so");
			File libjsy = new File(BeanUtil.path + "libJsyunew3.so");
			if (libjsy.exists() && (!dogFile.exists())) {
				try {
					FileUtil.copyDirectory(libjsy, dogFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 讀取權限配置數據
	private static Map readOauth() {
		Map<String, Integer> map = new HashMap();
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/oauth.properties"));
			Iterator it = prop.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				Integer value = Integer.parseInt(String.valueOf(entry.getValue()));
				map.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return map;
		}
	}

}
