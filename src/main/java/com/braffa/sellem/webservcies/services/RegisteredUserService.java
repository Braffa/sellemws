package com.braffa.sellem.webservcies.services;

import org.apache.log4j.Logger;

import com.braffa.sellem.hbn.Dao;
import com.braffa.sellem.hbn.DaoFactory;
import com.braffa.sellem.hbn.DaoFactory.daoType;
import com.braffa.sellem.model.xml.RegisteredUserMsgXml;
import com.braffa.sellem.model.xml.RegisteredUserXml;

public class RegisteredUserService {

	private static final Logger logger = Logger.getLogger(RegisteredUserService.class);

	public static RegisteredUserService registeredUserService = new RegisteredUserService();

	public static RegisteredUserService getInstance() {
		return registeredUserService;
	}

	public String count() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCount");
		}
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, new RegisteredUserMsgXml());
		return "" + registeredUserDao.getCount();
	}

	public RegisteredUserMsgXml create(RegisteredUserMsgXml registeredUserMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("createRegisteredUser");
		}
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserDao.create();
		return registeredUserMsg;
	}

	public RegisteredUserMsgXml delete(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteLogin");
		}
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		registeredUser.setUserId(userId);
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserDao.delete();
		return registeredUserMsg;
	}

	public RegisteredUserMsgXml remove(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove");
		}
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		registeredUser.setUserId(userId);
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserDao.delete();
		return registeredUserMsg;
	}

	public RegisteredUserMsgXml findAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("findAll");
		}
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserMsg = (RegisteredUserMsgXml) registeredUserDao.readAll();
		return registeredUserMsg;
	}

	public RegisteredUserMsgXml find(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("find");
		}
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		registeredUser.setUserId(userId);
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserMsg = (RegisteredUserMsgXml) registeredUserDao.read();
		return registeredUserMsg;
	}

	public RegisteredUserMsgXml update(RegisteredUserMsgXml registeredUserMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("createLogin");
		}
		Dao registeredUserDao = DaoFactory.getDAO(daoType.REGISTERED_USER_DAO, registeredUserMsg);
		registeredUserDao.update();
		return registeredUserMsg;
	}
}
