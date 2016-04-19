package com.baiyi.order.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * 如果有插入加密狗服務端就不用驗證，加密狗的數據必須是youhome 若沒有加密狗則需通過驗證主機驗證serverID
 * 
 * @author Administrator
 * 
 */
public class ValidateRunnable implements Runnable {

	// 配置中的信息
	private String availableMachine;

	private ServletContext servletContext;

	// private YT88Tool tool;//加密狗工具

	public ValidateRunnable(String availableMachine, ServletContext servletContext) {
		this.availableMachine = availableMachine;
		this.servletContext = servletContext;
		// tool = new YT88ToolImpl();
	}

	/**
	 * 比较配置中的机器信息与实际获得的配置信息是否一致
	 */
	public void run() {
		try {
			while (true) {
				BeanUtil.isDog = false;// 加密狗是否驗證成功

				if (BeanUtil.serverId != null && BeanUtil.serverId.length() != 0) {
					// 先用網絡檢查
					boolean isConfirm = confirmServerId();
					if (!isConfirm) {
						BeanUtil.isAvailableMachine = false;
						System.out.println("====server confirm error3====");
					} else {
						BeanUtil.isAvailableMachine = true;
						System.out.println("====server confirm success====");
					}
				} else {
					BeanUtil.isAvailableMachine = false;
					System.out.println("====server confirm error2====");
				}

				// 半小時驗證一次
				Thread.sleep(30 * 60 * 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BeanUtil.cusurl = "http://192.168.1.221:8181/customerControl/";
		BeanUtil.path = "D:/apache-tomcat-6.0.32/webapps/TopSetWebNetBef/";
		BeanUtil.serverId = "testserver";
		// confirmTerminal();
	}

	/**
	 * 到客戶管理後臺驗證服務端 先检查序列化文件是否存在，如果不存在就到客户管理后台验证 (验证分第一次和第二次验证)
	 * 文件不存在可能是第一次安装需要验证，验证完就序列化。 还有一种可能是被恶意删除了需要验证，这种情况下须由验证信息确认是否要让该服务端通过认证
	 */
	@SuppressWarnings("unchecked")
	public boolean confirmServerId() {
		boolean flag = false;
		try {
			// 服务端序列化文件路径
			File serialdir = new File(BeanUtil.path + "manage" + File.separator + "tconfirm" + File.separator);
			if (!serialdir.exists()) {
				serialdir.mkdirs();
			}
			String serilaPath = BeanUtil.path + "manage" + File.separator + "tconfirm" + File.separator + BeanUtil.serverId + ".data";
			File file = new File(serilaPath);
			if (!file.exists()) {
				// 如果不存在就去客管後臺驗證該服務器
				// String filePath = BeanUtil.path+"serverid.txt";
				// String serverId = IOUtil.readerFile(filePath);
				String param = "serverId=" + URLEncoder.encode(BeanUtil.serverId, "utf-8") + "&type=1";
				String confirmResult = Utility.sendRequest2(BeanUtil.cusurl + "checkServer.do", param);
				if (confirmResult != null && confirmResult.length() > 0) {
					JSONObject object = (JSONObject) JSONValue.parse(confirmResult);
					if (object.get("success") != null && object.get("success").toString().equals("true")) {
						String isOpen = object.get("isOpen").toString();
						if (isOpen.equals("true")) {
							DESPlus desPlus = new DESPlus();
							// 如果客管通過認證
							String terminalNumObj = (String) object.get("terminalNum");
							String serverIdObj = (String) object.get("serverId");
							String beginObj = (String) object.get("begin");
							String endObj = (String) object.get("end");
							JSONObject objectSerila = new JSONObject();
							objectSerila.put("serverId", serverIdObj);
							objectSerila.put("begin", beginObj);
							objectSerila.put("end", endObj);
							objectSerila.put("terminalCount", terminalNumObj);
							servletContext.setAttribute("availableCount", desPlus.decrypt(terminalNumObj));
							BeanUtil.availableCount = Integer.parseInt(desPlus.decrypt(terminalNumObj));
							Utility.writeObject(serilaPath, objectSerila);
							flag = true;
							System.out.println("====server confirm success====");
						} else {
							// 已經認證過了但是序列化文件被刪除了，認證不被通過
							flag = false;
						}
					} else if (object.get("success") != null && object.get("success").toString().equals("false")) {
						// 認證不通過
						flag = false;
					}
				}
			} else {
				// 如果存在了就根據序列化文件驗證驗證
				DESPlus desplus = new DESPlus();
				JSONObject object = Utility.readObject(serilaPath);
				String terminalCount = (String) object.get("terminalCount");
				String serverId = (String) object.get("serverId");
				// 驗證serverId是否一致
				if (!BeanUtil.serverId.equals(desplus.decrypt(serverId))) {
					flag = false;
				} else {
					terminalCount = desplus.decrypt(terminalCount);
					// 設置服務端的終端數量
					servletContext.setAttribute("availableCount", terminalCount);
					BeanUtil.availableCount = Integer.parseInt(terminalCount);
					flag = true;
				}
			}
			// {"terminalNum":"069726005eaf18c1","serverId":"AL0001","success":true,"end":"4ca2ab63835298d6caa441dc77dd93ab","begin":"4ca2ab63835298d66b5d676ed0fb4c30"}
			// 解析返回的信息

		} catch (IOException ioe) {
			// 如果是網絡問題就不驗證了，允許通過
			ioe.printStackTrace();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} catch (Error e) {
			flag = false;
			e.printStackTrace();
		}
		flag = true;// TODO:测试
		return flag;
	}

}
