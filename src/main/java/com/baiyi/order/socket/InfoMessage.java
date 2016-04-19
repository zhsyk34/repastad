package com.baiyi.order.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 該類主要用於終端連接后存儲終端狀態信息
 * 
 * 獲取驗證終端有兩種辦法： 1 使用客戶驗證主機驗證通過的終端。 2 使用加密狗驗證的終端。
 * 
 * @author Administrator
 * 
 */
public class InfoMessage {

	public static final String host = "localhost";

	// 內存中的終端連線記錄，连线实施信息存储在这里
	public static Map<String, Object[]> map = new HashMap<String, Object[]>();// <終端編號，連線信息>

	// 保存终端实时上传的图片地址
	public static Map imgmap = new TreeMap();

	public static void addInfo(String no, Object[] objects) {
		map.put(no, objects);
	}

	public static void main(String[] args) {
		Object[] tempObjects = new Object[7];
		tempObjects[0] = "hc0002";
		tempObjects[1] = "";
		tempObjects[2] = "date";
		tempObjects[3] = "正常運行";
		tempObjects[4] = "已連線";
		tempObjects[5] = "";// 圖片路徑
		tempObjects[6] = "";
		InfoMessage.map.put("hc0000020011", tempObjects);
		Object[] tempObjects2 = new Object[7];
		tempObjects2[0] = "hc0000020013";
		tempObjects2[1] = "";
		tempObjects2[2] = "date";
		tempObjects2[3] = "正常運行";
		tempObjects2[4] = "已連線";
		tempObjects2[5] = "";
		tempObjects2[6] = "";
		InfoMessage.map.put("hc0000020013", tempObjects2);
		// getConfirmedTerminal(36,false);
	}
}
