package com.braffa.sellem.webservcies.services;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.hbn.DaoFactory;
import com.braffa.sellem.hbn.DaoFactory.daoType;
import com.braffa.sellem.model.xml.ProductMsgXml;
import com.braffa.sellem.model.xml.ProductXml;
import com.braffa.sellem.model.xml.RegisteredUserMsgXml;
import com.braffa.sellem.model.xml.RegisteredUserXml;
import com.braffa.sellem.model.xml.UserToProductMsgXml;
import com.braffa.sellem.model.xml.UserToProductXml;
import com.braffa.sellem.webservcies.IUserToProductService;

public class UserToProductService implements IUserToProductService {

	private static final Logger logger = Logger.getLogger(UserToProductService.class);

	public static UserToProductService userToProductWebService = new UserToProductService();

	public static UserToProductService getInstance() {
		return userToProductWebService;
	}

	@Override
	public String count() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, new UserToProductMsgXml());
		return "" + userToProductDao.getCount();
	}

	@Override
	public UserToProductMsgXml create(UserToProductMsgXml xmlUserToProductMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("create");
		}
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, xmlUserToProductMsg);
		userToProductDao.create();
		return xmlUserToProductMsg;
	}

	@Override
	public UserToProductMsgXml delete(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		UserToProductXml UserToProductXml = new UserToProductXml();
		UserToProductXml.setUserId(userId);
		UserToProductXml.setProductId(productId);
		UserToProductXml.setProductIndex(productIndex);
		UserToProductMsgXml userToProductMsg = new UserToProductMsgXml(UserToProductXml);
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, userToProductMsg);
		userToProductDao.delete();
		return userToProductMsg;
	}

	public UserToProductMsgXml remove(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		UserToProductXml UserToProductXml = new UserToProductXml();
		UserToProductXml.setUserId(userId);
		UserToProductXml.setProductId(productId);
		UserToProductXml.setProductIndex(productIndex);
		UserToProductMsgXml userToProductMsg = new UserToProductMsgXml(UserToProductXml);
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, userToProductMsg);
		userToProductDao.delete();
		return userToProductMsg;
	}

	@Override
	public UserToProductMsgXml find(String userId, String productId, String productIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		UserToProductXml UserToProductXml = new UserToProductXml();
		if (userId != null && userId.length() > 0 && userId.trim().length() != 0) {
			UserToProductXml.setUserId(userId);
		}
		if (productId != null && productId.length() > 0  && productId.trim().length() != 0) {
			UserToProductXml.setProductId(productId);
		}
		if (productIndex != null && productIndex.length() > 0  && productIndex.trim().length() != 0) {
			UserToProductXml.setProductIndex(productIndex);
		}
		UserToProductMsgXml userToProductMsg = new UserToProductMsgXml(UserToProductXml);
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, userToProductMsg);
		return (UserToProductMsgXml) userToProductDao.read();
	}

	@Override
	public UserToProductMsgXml findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("delete");
		}
		UserToProductXml UserToProductXml = new UserToProductXml();
		UserToProductMsgXml userToProductMsg = new UserToProductMsgXml(UserToProductXml);
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, userToProductMsg);
		return (UserToProductMsgXml) userToProductDao.readAll();
	}

	@Override
	public UserToProductMsgXml search(String searchField, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("search");
		}
		UserToProductXml UserToProductXml = new UserToProductXml();
		if (searchField.equals("userId")) {
			UserToProductXml.setUserId(value);
		} else if (searchField.equals("productId")) {
			UserToProductXml.setProductId(value);
		}
		UserToProductMsgXml userToProductMsg = new UserToProductMsgXml(UserToProductXml);
		Dao userToProductDao = DaoFactory.getDAO(daoType.USER_TO_PRODUCT_DAO, userToProductMsg);
		UserToProductMsgXml userToProductMsgXml = (UserToProductMsgXml) userToProductDao.search();
		 ArrayList<UserToProductXml> lOfUserToProduct = userToProductMsgXml.getLOfUserToProduct();
		for (UserToProductXml userToProductXml : lOfUserToProduct) {
			userToProductXml.setEmail(getEmmail(userToProductXml.getUserId()));
		}
		if (searchField.equals("productId")) {
			userToProductMsgXml.setProduct(getProduct(value));
		}
		return userToProductMsgXml;
	}
	
	private ProductXml getProduct (String productId) {
		ProductXml  productXml = new ProductXml();
		productXml.setProductId(productId);
		productXml.setProductIndex("0");
		ProductMsgXml productMsgXml = new ProductMsgXml(productXml);
		Dao productDao = DaoFactory.getDAO(daoType.PRODUCT_DAO, productMsgXml);
		productDao.read();
		if (productMsgXml.getLOfProducts().size() > 0) {
			return productMsgXml.getLOfProducts().get(0);
		}
		return productMsgXml.getProduct();
	}
	
	private String getEmmail(String userId) {
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		registeredUser.setUserId(userId);
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserMsg = (RegisteredUserMsgXml) registeredUserDao.read();
		if (registeredUserMsg.getLOfRegisteredUsers().size() > 0) {
			return registeredUserMsg.getLOfRegisteredUsers().get(0).getEmail();
		}
		return "";
	}
}
