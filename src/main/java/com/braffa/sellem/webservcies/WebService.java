package com.braffa.sellem.webservcies;

import org.apache.log4j.Logger;

public class WebService implements IBaseWebService {
	
	private static final Logger logger = Logger.getLogger(WebService.class);

	public String getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		return "No Implementation for getCount";
	}
}
