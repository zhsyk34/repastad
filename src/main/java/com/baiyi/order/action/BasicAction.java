package com.baiyi.order.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.util.Log4JFactory;
import com.opensymphony.xwork2.ActionSupport;

public class BasicAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware, ServletContextAware {

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected Map<String, Object> session;

	protected ServletContext servletContext;

	protected Admins admins;

	protected Log logger = Log4JFactory.instance();

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		admins = (Admins) this.session.get("user");
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Admins getAdmins() {
		return admins;
	}

	public void setAdmins(Admins admins) {
		this.admins = admins;
	}

}
