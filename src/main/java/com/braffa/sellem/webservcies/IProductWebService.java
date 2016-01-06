package com.braffa.sellem.webservcies;

import com.braffa.sellem.model.xml.ProductMsgXml;

public interface IProductWebService {

	public String getCount();

	public ProductMsgXml create(ProductMsgXml productMsg);

	public ProductMsgXml delete(String productId);

	public ProductMsgXml find(String productId);

	public ProductMsgXml findAll();

	public ProductMsgXml update(ProductMsgXml productMsgXml);

}
