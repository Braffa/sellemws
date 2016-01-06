package com.braffa.sellem.webservcies.resources;

import java.net.URI;
import java.util.List;

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
import com.braffa.sellem.webservcies.IProductWebService;
import com.braffa.sellem.webservcies.services.ProductService;

@Path("product")
public class ProductResource implements IProductWebService {

	@Context
	UriInfo uriInfo;

	private static final Logger logger = Logger.getLogger(ProductResource.class);

	@Override
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("count");
		}
		try {
			return ProductService.getInstance().getCount();
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
	public ProductMsgXml create(ProductMsgXml productMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		URI uri = uriInfo.getAbsolutePathBuilder().path(productMsg.getProduct().getProductId()).build();
		Response res = Response.created(uri).build();
		try {
			ProductService.getInstance().create(productMsg);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(res);
			}
			e.printStackTrace();
		}
		return productMsg;
	}

	@Override
	@DELETE
	@Path("/delete/{productId}")
	@Consumes("application/xml")
	public ProductMsgXml delete(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		try {
			ProductMsgXml ProductMsg = ProductService.getInstance().delete(productId);
			return ProductMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@DELETE
	@Path("/remove/{productId}")
	@Consumes("application/xml")
	public void remove(@PathParam("productId") String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		try {
			ProductMsgXml ProductMsg = ProductService.getInstance().delete(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@GET
	@Path("/findall")
	@Produces(MediaType.TEXT_XML)
	public ProductMsgXml findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		try {
			return ProductService.getInstance().findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@GET
	@Path("/findsome/{productId}")
	@Produces(MediaType.TEXT_XML)
	public ProductMsgXml findSome(@PathParam("lOfProductIds") List<String> lOfProductIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("findSome");
		}
		try {
			return ProductService.getInstance().findSome(lOfProductIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@GET
	@Path("/findbyproductid/{productId}")
	@Produces(MediaType.TEXT_XML)
	public ProductMsgXml find(@PathParam("productId") String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("find");
		}
		try {
			return ProductService.getInstance().find(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/searchProduct/{field}/{value}")
	@Produces(MediaType.TEXT_XML)
	public ProductMsgXml searchProduct(@PathParam("field")String field, @PathParam("value") String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("searchProduct by "+ field + " for " + value);
		}
		try {
			return ProductService.getInstance().searchProduct(field, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@POST
	@Path("/update")
	@Consumes("application/xml")
	@Produces("application/xml")
	public ProductMsgXml update(ProductMsgXml productMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("update");
		}
		URI uri = uriInfo.getAbsolutePathBuilder().path(productMsg.getProduct().getProductId()).build();
		Response res = Response.created(uri).build();
		try {
			productMsg = ProductService.getInstance().update(productMsg);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(res);
			}
			e.printStackTrace();
		}
		return productMsg;
	}

}
