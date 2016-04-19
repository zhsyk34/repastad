package com.baiyi.order.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Log4JFactory {

	private static Log4JFactory factory;

	private Log logger;

	private Log4JFactory() {
		this.logger = LogFactory.getLog(this.getClass());
	}

	public static Log instance() {
		if (factory == null)
			factory = new Log4JFactory();

		return factory.logger;
	}
}