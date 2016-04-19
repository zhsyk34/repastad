package com.baiyi.order.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Admins;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class OauthInterceptor extends AbstractInterceptor {

	// 存放訪問權限管理文件信息 key:actionMethod,value:authorityId
	private static Map<String, Integer> map = new HashMap();

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map session = actionInvocation.getInvocationContext().getSession();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		PrintWriter responseOut = null;

		// 可能用到的判斷數據--->>>

		Admins user = (Admins) session.get("user");
		// 當前用戶所擁有的訪問權限
		int[] authorityIds = null;
		// 當前請求需要的授權
		int requiredAuthorityId = 0;
		// 請求類型 普通請求：null,ajax:XMLHttpRequest
		String type = null;
		// 是否放行
		Boolean accredit = false;
		// ueer == null 已在UserFilter攔截
		if (user == null) {
			return null;
		}
		return actionInvocation.invoke();// TODO

		// // 驗證處理流程
		// if (user.getId() == 1) {
		// // 1系統内置管理員賬號放行
		// accredit = true;
		// } else {
		// // 2獲取當前訪問
		// String actionMethod = actionInvocation.getProxy().getMethod();
		// // 讀取配置信息
		// map = (Map)
		// ServletActionContext.getServletContext().getAttribute("oauth");
		// if (!map.containsKey(actionMethod)) {
		// // 2.1無需授權即可訪問的acitonName
		// accredit = true;
		// } else {
		// // 2.2受保護的action所需要的授權
		// // 需要的權限id
		// int requried = map.get(actionMethod);
		// // 2.2.1讀取權限
		// authorityIds = (int[]) session.get("authorityIds");
		// if (authorityIds != null && authorityIds.length > 0) {
		// for (int authorityId : authorityIds) {
		// // 2.2.2通過驗證
		// if (authorityId == requried) {
		// accredit = true;
		// break;
		// }
		// }
		// }
		//
		// }
		// }
		// // 根據驗證結果進行處理
		// if (accredit) {
		// // 允許訪問
		// return actionInvocation.invoke();
		// } else {
		// // 無權訪問
		// request = ServletActionContext.getRequest();
		// type = request.getHeader("X-Requested-With");
		// // 通過ajax訪問 則返回打印信息
		// if ("XMLHttpRequest".equals(type)) {
		// response = ServletActionContext.getResponse();
		// responseOut = response.getWriter();
		// response.setContentType("text/json; charset=UTF-8");
		// response.setHeader("Cache", "no-cache");
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("verOauth", "unauthorized");
		// responseOut.write(jsonObject.toString());
		// return null;
		// }
		// // 否則直接跳轉到提示頁面
		// return "unauthorized";
		// }

	}

}
