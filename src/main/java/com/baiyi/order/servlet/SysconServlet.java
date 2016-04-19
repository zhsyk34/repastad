package com.baiyi.order.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.SystemConfig;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.SystemConfigService;

//優先啓動servlet，讀取系統配置信息，并存放到servletContext
public class SysconServlet extends HttpServlet {

	private SystemConfigService systemConfigService;

	private MaterialService materialService;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		systemConfigService = (SystemConfigService) applicationContext.getBean("systemConfigService");
		materialService = (MaterialService) applicationContext.getBean("materialService");
		// 1 系統LOGO index login
		String loginUrl = null, indexUrl = null;
		String unifyNo = "0";
		String take = "0";
		String desk = "0";
		SystemConfig login = systemConfigService.findByName("login");
		if (login != null && login.getValue() != null) {
			int loginId = Integer.parseInt(login.getValue());
			if (loginId > 0) {
				Material loginMaterial = materialService.findById(loginId);
				if (loginMaterial != null) {
					loginUrl = loginMaterial.getPath();
				}
				if (loginUrl != null) {
					loginUrl = loginUrl.replace("\\", "/");
				}
			}
		}

		SystemConfig index = systemConfigService.findByName("index");
		if (index != null && index.getValue() != null) {
			int indexId = Integer.parseInt(index.getValue());
			if (indexId > 0) {
				Material indexMaterial = materialService.findById(indexId);
				if (indexMaterial != null) {
					indexUrl = indexMaterial.getPath();
				}
				if (indexUrl != null) {
					indexUrl = indexUrl.replace("\\", "/");
				}
			}
		}
		// 存放
		servletContext.setAttribute("loginLogo", loginUrl);
		servletContext.setAttribute("indexLogo", indexUrl);
		// 2 取餐方式 桌號顯示 take/desk
		SystemConfig takeSyscon = systemConfigService.findByName("take");
		SystemConfig deskSysocon = systemConfigService.findByName("desk");
		if (takeSyscon != null) {
			take = takeSyscon.getValue();
		}
		if (deskSysocon != null) {
			desk = deskSysocon.getValue();
		}
		servletContext.setAttribute("take", take);
		servletContext.setAttribute("desk", desk);

		// 3統一編號 unifyNo
		SystemConfig unifyNoSyscon = systemConfigService.findByName("unifyNo");
		if (unifyNoSyscon != null) {
			unifyNo = unifyNoSyscon.getValue();
		}
		servletContext.setAttribute("unifyNo", unifyNo);

	}
}
