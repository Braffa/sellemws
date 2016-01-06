package com.braffa.sellem.webservcies.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.braffa.sellem.model.xml.ProductMsgXml;
import com.braffa.sellem.model.xml.UserToProductMsgXml;
import com.braffa.sellem.webservcies.IUserToProductService;
import com.braffa.sellem.webservcies.services.ProductService;
import com.braffa.sellem.webservcies.services.UserToProductService;

@Path("usertoproduct")
public class UserToProductResource implements IUserToProductService {

	@Context
	UriInfo uriInfo;

	private static final Logger logger = Logger.getLogger(UserToProductResource.class);

	@Override
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public String count() {
		if (logger.isDebugEnabled()) {
			logger.debug("");
		}
		try {
			return UserToProductService.getInstance().count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@POST
	@Path("/create")
	@Consumes("application/xml")
	@Produces("application/xml")
	public UserToProductMsgXml create(UserToProductMsgXml UserToProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("createUserToProductMsg");
		}
		URI uri = uriInfo.getAbsolutePathBuilder().path(UserToProductMsg.getUserToProduct().getUserId()).build();
		Response res = Response.created(uri).build();
		try {
			UserToProductService.getInstance().create(UserToProductMsg);
		} catch (Exception e) {

		}
		return UserToProductMsg;
	}

	@Override
	@DELETE
	@Path("/delete/{userId}/{productId}/{productIndex}")
	@Consumes("application/xml")
	public UserToProductMsgXml delete(@PathParam("userId") String userId, @PathParam("productId") String productId,
			@PathParam("productIndex") String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteUserToProduct");
		}
		try {
			UserToProductMsgXml UserToProductMsg = UserToProductService.getInstance().delete(userId, productId,
					productIndex);
			return UserToProductMsg;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	@GET
	@Path("/find/{userId}/{productId}/{productIindex}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_XML)
	public UserToProductMsgXml find(@PathParam("userId") String userId, @PathParam("productId") String productId,
			@PathParam("productIindex") String productIindex) {
		if (logger.isDebugEnabled()) {
			logger.debug("find");
		}
		try {
			return UserToProductService.getInstance().find(userId, productId, productIindex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@GET
	@Path("/findall")
	@Produces(MediaType.TEXT_XML)
	public UserToProductMsgXml findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAllUserToProductMsg");
		}
		try {
			return UserToProductService.getInstance().findAll();
		} catch (Exception e) {

		}
		return null;
	}
	
	//@DELETE
	//@Path("/delete/{userId}/{productId}/{productIndex}")
	//@Consumes("application/xml")
	//public UserToProductMsgXml remove(@PathParam("userId") String userId, @PathParam("productId") String productId,
	//		@PathParam("productIndex") String productIndex) {
	//	if (logger.isDebugEnabled()) {
	//		logger.debug("deleteUserToProduct");
	//	}
	//	try {
	//		UserToProductMsgXml UserToProductMsg = UserToProductService.getInstance().remove(userId, productId,
	//				productIndex);
	//		return UserToProductMsg;
	//	} catch (Exception e) {
	//
	//	}
	//	return null;
	//}

	
	@Override
	@GET
	@Path("/search/{searchField}/{value}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_XML)
	public UserToProductMsgXml search(@PathParam("searchField") String searchField, @PathParam("value") String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("search");
		}
		try {
			return UserToProductService.getInstance().search(searchField, value);
		} catch (Exception e) {

		}
		return null;
	}

}
