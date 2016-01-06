package com.braffa.sellem.webservcies.client;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.braffa.sellem.model.xml.RegisteredUserMsgXml;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RegisteredUserClient extends BaseClient {

	private static final Logger logger = Logger
			.getLogger(RegisteredUserClient.class);

	static final String COUNT_URI = "/registeredusers/count";
	static final String CREATE_URI = "/registeredusers/create";
	static final String DELETE_URI = "/registeredusers/delete/";
	static final String REMOVE_URI = "/registeredusers/remove/";
	static final String FIND_ALL_URI = "/registeredusers/findall";
	static final String FIND_BY_USERID_URI = "/registeredusers/findbyuserid/";
	static final String UPDATE_URI = "/registeredusers/update";
	
	public String count() {
		if (logger.isDebugEnabled()) {
			logger.debug("count");
		}
		return (getResponse(getWebService (COUNT_URI)));
	}

	public ClientResponse create(RegisteredUserMsgXml xmlRegisteredUserMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		try {
			WebResource WebService = getWebService (CREATE_URI);
			ClientResponse response = WebService.accept(
					MediaType.APPLICATION_XML).post(ClientResponse.class,
					xmlRegisteredUserMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delete(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete " + userId);
		}
		try {
			WebResource webResource = getResource();
			ClientResponse response = webResource.path("rest")
					.path(DELETE_URI + userId).delete(ClientResponse.class);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String remove(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove " + userId);
		}
		try {
			WebResource webResource = getResource();
			ClientResponse response = webResource.path("rest")
					.path(REMOVE_URI + userId).delete(ClientResponse.class);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		return (getResponseAsXML(getWebService (FIND_ALL_URI)));
	}
	
	public String find(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("find " + userId);
		}
		return (getResponseAsXML(getWebService (FIND_BY_USERID_URI + userId)));
	}
	
	public ClientResponse update (RegisteredUserMsgXml xmlRegisteredUserMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("update");
		}
		try {
			WebResource WebService = getWebService (UPDATE_URI);
		ClientResponse response = WebService.accept(MediaType.APPLICATION_XML).post(
				ClientResponse.class, xmlRegisteredUserMsg);
		return response;
		} catch (Exception e) {  
			e.printStackTrace();
		}
		return null;
	}
}
