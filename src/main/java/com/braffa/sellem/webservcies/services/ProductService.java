package com.braffa.sellem.webservcies.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.hbn.DaoFactory;
import com.braffa.sellem.hbn.DaoFactory.daoType;
import com.braffa.sellem.model.xml.ProductMsgXml;
import com.braffa.sellem.model.xml.ProductXml;
import com.braffa.sellem.webservcies.IProductWebService;

public class ProductService implements IProductWebService {

	private static final Logger logger = Logger.getLogger(ProductService.class);

	public static ProductService productService = new ProductService();

	public static ProductService getInstance() {
		return productService;
	}

	@Override
	public String getCount() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, new ProductMsgXml());
		return "" + productDao.getCount();
	}

	@Override
	public ProductMsgXml create(ProductMsgXml productMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productDao.create();
		return productMsg;
	}

	@Override
	public ProductMsgXml delete(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		ProductXml productXml = new ProductXml();
		productXml.setProductId(productId);
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productDao.delete();
		return productMsg;
	}
	
	public ProductMsgXml remove(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		ProductXml productXml = new ProductXml();
		productXml.setProductId(productId);
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productDao.delete();
		return productMsg;
	}

	@Override
	public ProductMsgXml findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		ProductXml productXml = new ProductXml();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productMsg = (ProductMsgXml)productDao.readAll();
		return productMsg;
	}
	
	public ProductMsgXml findSome(List<String> lOfProductIds) {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		ProductXml productXml = new ProductXml();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		for (String productId : lOfProductIds) {
			productXml = new ProductXml();
			productXml.setProductId(productId);
		}
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productMsg = (ProductMsgXml)productDao.readListOfKeys();
		return productMsg;
	}

	@Override
	public ProductMsgXml find(String productId) {
		if (logger.isDebugEnabled()) {
			logger.debug("find");
		}
		ProductXml productXml = new ProductXml();
		productXml.setProductId(productId);
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productMsg = (ProductMsgXml)productDao.read();
		return productMsg;
	}
	
	public ProductMsgXml searchProduct(String field, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("searchProduct by " + field + " for " + value);
		}
		ProductXml productXml = new ProductXml();
		switch (field) {
		case "author":
			productXml.setAuthor(value);
			break;
		case "title":
			productXml.setTitle(value);
			break;
		case "productid":
			productXml.setProductId(value);
			break;
		case "manufacturer":
			productXml.setManufacturer(value);
			break;
		default:
			productXml.setProductId("*");
			break;
		}
		ProductMsgXml ProductMsg = new ProductMsgXml(productXml);
		ProductMsg.setSearchField(field);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, ProductMsg);
		return (ProductMsgXml) productDao.search();
	}


	@Override
	public ProductMsgXml update(ProductMsgXml productMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("update");
		}
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsg);
		productMsg = (ProductMsgXml)productDao.update();
		return productMsg;
	}


}
