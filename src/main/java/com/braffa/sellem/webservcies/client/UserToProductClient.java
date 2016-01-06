package com.braffa.sellem.webservcies.client;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.braffa.sellem.model.xml.UserToProductMsgXml;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserToProductClient extends BaseClient {

	private static final Logger logger = Logger.getLogger(UserToProductClient.class);

	static final String COUNT_URI = "/usertoproduct/count";
	static final String CREATE_URI = "/usertoproduct/create";
	static final String DELETE_URI = "/usertoproduct/delete/";
	static final String REMOVE_URI = "/usertoproduct/remove/";
	static final String FIND_ALL_URI = "/usertoproduct/findall";
	static final String FIND_URI = "/usertoproduct/find/";
	static final String UPDATE_URI = "/usertoproduct/update";
	static final String SEARCH_URI = "/usertoproduct/search/";

	public String getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("count");
		}
		return (getResponse(getWebService(COUNT_URI)));
	}

	public ClientResponse create(UserToProductMsgXml userToProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		try {
			WebResource WebService = getWebService(CREATE_URI);
			ClientResponse response = WebService.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
					userToProductMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delete(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		try {
			WebResource webResource = getResource();
			ClientResponse response = webResource.path("rest")
					.path(DELETE_URI + userId + "/" + productId + "/" + productIndex).delete(ClientResponse.class);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String find(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("find " + userId + " " + productId + " " + productIndex);
		}
		try {
			return (getResponseAsXML(getWebService(FIND_URI + userId + "/" + productId + "/" + productIndex)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		return (getResponseAsXML(getWebService(FIND_ALL_URI)));
	}

	public ClientResponse remove(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove " + userId);
		}
		try {
			WebResource webResource = getResource();
			ClientResponse response = webResource.path("rest")
					.path(REMOVE_URI + userId + "/" + productId + "/" + productIndex).delete(ClientResponse.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String search(String searchField, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("find " + searchField + " " + value);
		}
		try {
			return (getResponseAsXML(getWebService(SEARCH_URI + searchField + "/" + value)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ClientResponse update(UserToProductMsgXml userToProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("update");
		}
		try {
			WebResource WebService = getWebService(UPDATE_URI);
			ClientResponse response = WebService.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
					userToProductMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
