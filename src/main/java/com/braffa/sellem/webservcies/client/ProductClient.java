package com.braffa.sellem.webservcies.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.braffa.sellem.model.xml.ProductMsgXml;

public class ProductClient extends BaseClient {

	private static final Logger logger = Logger.getLogger(ProductClient.class);

	static final String COUNT_URI = "/product/count";
	static final String CREATE_URI = "/product/create";
	static final String DELETE_URI = "/product/delete/";
	static final String REMOVE_URI = "/product/remove/";
	static final String FIND_ALL_URI = "/product/findall";
	static final String FIND_BY_PRODUCTID_URI = "/product/findbyproductid/";
	static final String FIND_BY_KEYS_URI = "/product/findbykeys/";
	static final String SEARCH_PRODUCT_URI = "/product/searchProduct/";
	static final String UPDATE_URI = "/product/update";

	public ClientResponse create(ProductMsgXml ProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource createService = service.path("rest").path(CREATE_URI);
			ClientResponse response = createService.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
					ProductMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String remove(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove " + productId);
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(REST_URI);
			ClientResponse response = webResource.path("rest").path(REMOVE_URI + productId)
					.delete(ClientResponse.class);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delete(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete " + productId);
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(REST_URI);
			ClientResponse response = webResource.path("rest").path(DELETE_URI + productId)
					.delete(ClientResponse.class);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// http://localhost:8080/sellemws/rest/product/count
	public String getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(REST_URI);
		WebResource countService = service.path("rest").path(COUNT_URI);
		return (getResponse(countService));
	}

	// http://localhost:8080/sellemws/rest/product/findall
	public String findall() {
		if (logger.isDebugEnabled()) {
			logger.debug("findall");
		}
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(REST_URI);
		WebResource getAllService = service.path("rest").path(FIND_ALL_URI);
		return getResponseAsXML(getAllService);
	}

	// http://localhost:8080/sellemws/rest/product/findByProductId/9781861005618
	public String findByProductId(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("findByProductId " + productId);
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource getService = service.path("rest").path(FIND_BY_PRODUCTID_URI + productId);
			return getResponseAsXML(getService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// http://localhost:8080/sellemws/rest/product/findByProductId/9781861005618
	public String findByKeys(List<String> lOfProductId) {
		if (logger.isDebugEnabled()) {
			logger.debug("findByProductId ");
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource getService = service.path("rest").path(FIND_BY_KEYS_URI + lOfProductId);
			return getResponseAsXML(getService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String searchProduct(String field, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("searchProduct by " + field + " value " + value);
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource getService = service.path("rest").path(SEARCH_PRODUCT_URI + field + "/" + value);
			return getResponseAsXML(getService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ClientResponse update(ProductMsgXml ProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("update");
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource createService = service.path("rest").path(UPDATE_URI);
			ClientResponse response = createService.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
					ProductMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ClientResponse remove(ProductMsgXml xmlProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove");
		}
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(REST_URI);
			WebResource createService = service.path("rest").path(REMOVE_URI);
			ClientResponse response = createService.accept(MediaType.APPLICATION_XML).post(ClientResponse.class,
					xmlProductMsg);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
