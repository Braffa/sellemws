package com.braffa.sellem.webservcies.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class BaseClient {
	
	private static final Logger logger = Logger.getLogger(BaseClient.class);
	
	static final String REST_URI = "http://localhost:8080/sellemws/";
	
	protected static URI getBaseURI() {
		return UriBuilder.fromUri(REST_URI).build();
	}
	
	protected WebResource getResource () {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		return client.resource(REST_URI);
	}
	
	protected static String getResponse(WebResource service) {
		if (logger.isDebugEnabled()) {
			logger.debug("getResponse");
		}
		return service.accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	protected static String getResponseAsXML(WebResource service) {
		if (logger.isDebugEnabled()) {
			logger.debug("getResponseAsXML");
		}
		return service.accept(MediaType.TEXT_XML).get(String.class);
	}
	
	protected WebResource getWebService (String theUrl) {
		WebResource resource = getResource();
		WebResource service = resource.path("rest").path(theUrl);
		return service;
	}
}
