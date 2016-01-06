package com.braffa.sellem.webservcies.client;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.datafortesting.UserToProductTestData;
import com.braffa.sellem.model.sql.UserToProduct;
import com.braffa.sellem.model.xml.UserToProductMsgXml;
import com.braffa.sellem.model.xml.UserToProductXml;
import com.braffa.sellem.tables.IDBActions;
import com.braffa.sellem.tables.TableEnum;
import com.braffa.sellem.tables.TableFactory;
import com.sun.jersey.api.client.ClientResponse;

public class UserToProductClientTest {

	private static IDBActions mySqlUserToProduct;

	private UserToProductTestData utptd = new UserToProductTestData();

	private UserToProductClient userToProductClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mySqlUserToProduct = TableFactory.getTable(TableEnum.USER_TO_PRODUCT);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// mySqlUserToProduct.dropTable();
	}

	@Before
	public void setUp() throws Exception {
		try {
			mySqlUserToProduct.dropTable();
		} catch (Exception e) {
			// ignore if no table found
		}
		mySqlUserToProduct.createTable();
		userToProductClient = new UserToProductClient();
	}

	private void setUpUserToProducts() {
		try {
			UserToProduct UserToProduct = utptd.insertUserToProductTable1();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable2();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable3();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable4();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable5();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable6();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable7();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable8();
			mySqlUserToProduct.create(UserToProduct);
			UserToProduct = utptd.insertUserToProductTable9();
			mySqlUserToProduct.create(UserToProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UserToProductMsgXml convertStringToObject(String xmlStr) {
		try {
			StringReader reader = new StringReader(xmlStr);
			JAXBContext jaxbContext = JAXBContext.newInstance(UserToProductMsgXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (UserToProductMsgXml) jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<UserToProductXml> getLOfUserToProductMsgXml(String xmlStr) {
		UserToProductMsgXml userToProductMsgXml = convertStringToObject(xmlStr);
		return userToProductMsgXml.getLOfUserToProduct();
	}

	@Test
	public void countTest() {
		setUpUserToProducts();
		String count = userToProductClient.getCount();
		System.out.println(count);
		assertEquals("count failed Incorrect number of rows ", "9", count);
	}

	@Test
	public void createTest() {
		UserToProductXml userToProduct = new UserToProductXml.UserToProductBuilder().id(5).userId("Braffa")
				.productId("978098056856").productIndex("0").build();
		UserToProductMsgXml UserToProductMsgXml  = new UserToProductMsgXml(userToProduct);
		ClientResponse response = userToProductClient.create(UserToProductMsgXml);
		System.out.println(response);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/usertoproduct/create returned a response status of 200 OK",
				response.toString());
	}
	
	@Test
	public void deleteTest() {
		setUpUserToProducts();
		String response = userToProductClient.delete("Braffa", "978098056856", "0");
		System.out.println(response);
		assertEquals(
				"DELETE http://localhost:8080/sellemws/rest/usertoproduct/delete/Braffa/978098056856/0 returned a response status of 200 OK",
				response.toString());
	}

	@Test
	public void findallTest() {
		setUpUserToProducts();
		String xmlStr = userToProductClient.findAll();
		List<UserToProductXml> lOfUserToProductXml = getLOfUserToProductMsgXml(xmlStr);
		assertEquals("userId should be  ", 9, lOfUserToProductXml.size());
	}
	
	@Test
	public void findTest() {
		setUpUserToProducts();
		String xmlStr = userToProductClient.find("Braffa", "978098056856", "0");
		List<UserToProductXml> lOfUserToProductXml = getLOfUserToProductMsgXml(xmlStr);
		assertEquals("One row should be returned ", 1, lOfUserToProductXml.size());
		UserToProductXml UserToProductXml = lOfUserToProductXml.get(0);
		System.out.println(UserToProductXml.toString());
		assertEquals("index should be 5 ", 5, UserToProductXml.getId());
		assertEquals("userId should be  ", "Braffa", UserToProductXml.getUserId());
		assertEquals("productId should be  ", "978098056856", UserToProductXml.getProductId());
		assertEquals("productIndex should be  ", "0", UserToProductXml.getProductIndex());
	}
	
	@Test
	public void findTestByProductId() {
		setUpUserToProducts();
		String xmlStr = userToProductClient.find(" ", "9780789724410", " ");
		List<UserToProductXml> lOfUserToProductXml = getLOfUserToProductMsgXml(xmlStr);
		assertEquals("One row should be returned ", 2, lOfUserToProductXml.size());
		UserToProductXml UserToProductXml = lOfUserToProductXml.get(0);
		System.out.println(UserToProductXml.toString());
		assertEquals("index should be 2 ", 2, UserToProductXml.getId());
		assertEquals("userId should be  ", "georgie", UserToProductXml.getUserId());
		assertEquals("productId should be  ", "9780789724410", UserToProductXml.getProductId());
		assertEquals("productIndex should be  ", "0", UserToProductXml.getProductIndex());
		
		UserToProductXml = lOfUserToProductXml.get(1);
		System.out.println(UserToProductXml.toString());
		assertEquals("index should be 3 ", 3, UserToProductXml.getId());
		assertEquals("userId should be  ", "Braffa", UserToProductXml.getUserId());
		assertEquals("productId should be  ", "9780789724410", UserToProductXml.getProductId());
		assertEquals("productIndex should be  ", "0", UserToProductXml.getProductIndex());
	}
	@Test
	public void findTestByUserId() {
		setUpUserToProducts();
		String xmlStr = userToProductClient.find("Braffa", " ", " ");
		List<UserToProductXml> lOfUserToProductXml = getLOfUserToProductMsgXml(xmlStr);
		assertEquals("userId should be  ", 5, lOfUserToProductXml.size());
		UserToProductXml UserToProductXml = lOfUserToProductXml.get(0);
		System.out.println(UserToProductXml.toString());
	}
	
}
