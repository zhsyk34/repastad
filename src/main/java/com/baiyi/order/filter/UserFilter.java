package com.baiyi.order.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.baiyi.order.util.Utility;

public class UserFilter implements Filter {

	private FilterConfig filterConfig;

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String servletContext = request.getContextPath();
		String uri = request.getRequestURI();

		// language
		String language = request.getLocale().toString().toLowerCase().replace("_", "-");
		if (!language.equals("zh-cn") && !language.equals("zh-tw") && !language.equals("en-us")) {
			language = "zh-tw";
		}
		session.setAttribute("language", language);
		String suffix = "_" + language.split("-")[1];
		if ("_tw".equals(suffix)) {
			suffix = "";
		}
		session.setAttribute("lansuffix", suffix);

		// 登陸狀態檢查
		try {
			if (Utility.isPass(uri) && session.getAttribute("user") == null) {
				response.sendRedirect(servletContext + "/redirect.jsp");
			} else {
				chain.doFilter(request, response);
			}
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
