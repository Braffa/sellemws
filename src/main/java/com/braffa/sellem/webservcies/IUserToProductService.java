package com.braffa.sellem.webservcies;

import com.braffa.sellem.model.xml.UserToProductMsgXml;

public interface IUserToProductService {

	public String count();

	public UserToProductMsgXml create(UserToProductMsgXml xmlUserToProductMsg);

	public UserToProductMsgXml delete(String userId, String productId, String productIndex);

	public UserToProductMsgXml find(String userId, String productId, String productIndex);

	public UserToProductMsgXml findAll();

	public UserToProductMsgXml search(String searchField, String value);

}
