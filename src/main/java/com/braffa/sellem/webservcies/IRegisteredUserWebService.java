package com.braffa.sellem.webservcies;

import com.braffa.sellem.model.xml.RegisteredUserMsgXml;

public interface IRegisteredUserWebService {

	public String count();

	public RegisteredUserMsgXml create(RegisteredUserMsgXml RegisteredUserMsg);

	public RegisteredUserMsgXml delete(String userId);

	public RegisteredUserMsgXml find(String userId);

	public RegisteredUserMsgXml findAll();

	public RegisteredUserMsgXml update(RegisteredUserMsgXml RegisteredUserMsg);

}
