package com.braffa.sellem.webservcies.client;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.datafortesting.RegisteredUserTestData;
import com.braffa.sellem.model.sql.RegisteredUser;
import com.braffa.sellem.model.xml.RegisteredUserMsgXml;
import com.braffa.sellem.model.xml.RegisteredUserXml;
import com.braffa.sellem.tables.IDBActions;
import com.braffa.sellem.tables.TableEnum;
import com.braffa.sellem.tables.TableFactory;
import com.sun.jersey.api.client.ClientResponse;

public class RegisteredUserClientTest {
	
	private static IDBActions mySqlRegisteredUser;

	private RegisteredUserTestData rutd = new RegisteredUserTestData();
	
	private RegisteredUserClient registeredUserClient;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mySqlRegisteredUser = TableFactory.getTable(TableEnum.REGISTERED_USER);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//mySqlRegisteredUser.dropTable();
	}

	@Before
	public void setUp() throws Exception {
		try {
			mySqlRegisteredUser.dropTable();
		} catch (Exception e) {
			// ignore if no table found
		}
		mySqlRegisteredUser.createTable();
	}
	
	@Before
	public void setProductClient() {
		registeredUserClient = new RegisteredUserClient();
	}
	
	private void setUpRegisteredUsers() {
		try {
			RegisteredUser registeredUser = rutd.setUpRegisteredUser1();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser2();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser3();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser4();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser5();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser6();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser7();
			mySqlRegisteredUser.create(registeredUser);
			registeredUser = rutd.setUpRegisteredUser8();
			mySqlRegisteredUser.create(registeredUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private RegisteredUserMsgXml convertStringToObject(String xmlStr) {
		try {
			StringReader reader = new StringReader(xmlStr);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RegisteredUserMsgXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (RegisteredUserMsgXml) jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<RegisteredUserXml> getLOfRegisteredUser(String xmlStr) {
		RegisteredUserMsgXml registeredUserMsg = convertStringToObject(xmlStr);
		return registeredUserMsg.getLOfRegisteredUsers();
	}
	
	@Test
	public void countTest() {
		setUpRegisteredUsers();
		
		String count = registeredUserClient.count();
		System.out.println(count);
		assertEquals("count failed Incorrect number of rows ", "8", count);
	}
	
	@Test
	public void createTest() {
		setUpRegisteredUsers();
		RegisteredUserXml registeredUser = new RegisteredUserXml();
		registeredUser.setCrDate(new Date());
		registeredUser.setAuthorityLevel("11");
		registeredUser.setEmail("dave_allen476@hotmail.com");
		registeredUser.setFirstname("Alan");
		registeredUser.setLastname("Mills");
		registeredUser.setPassword("myPassword");
		registeredUser.setTelephone("01388 445561");
		registeredUser.setUpdDate(new Date());
		registeredUser.setUserId("gordon");
		RegisteredUserMsgXml registeredUserMsg = new RegisteredUserMsgXml(registeredUser);
		
		ClientResponse response = registeredUserClient.create(registeredUserMsg);
		System.out.println(response);
		assertEquals("create failed ", "POST http://localhost:8080/sellemws/rest/registeredusers/create returned a response status of 200 OK", response.toString());
	}
	
	@Test
	public void deleteTest() {
		setUpRegisteredUsers();
		String response = registeredUserClient.delete("gordon456");
		System.out.println(response);
		assertEquals("remove failed ", "DELETE http://localhost:8080/sellemws/rest/registeredusers/delete/gordon456 returned a response status of 200 OK", response);
	}
	
	@Test
	public void removeTest() {
		setUpRegisteredUsers();
		String response = registeredUserClient.remove("rachel33");
		System.out.println(response);
		assertEquals("remove failed ", "DELETE http://localhost:8080/sellemws/rest/registeredusers/remove/rachel33 returned a response status of 204 No Content", response);
	}
	
	@Test
	public void findallTest() {
		setUpRegisteredUsers();
		String xmlStr = registeredUserClient.findAll();
		System.out.println(xmlStr);
		List lOfRegisteredUser = getLOfRegisteredUser(xmlStr);
		assertEquals("userId should be  ", 8, lOfRegisteredUser.size());
	}
	
	@Test
	public void findByUserIdTest() {
		setUpRegisteredUsers();
		String xmlStr = registeredUserClient.find("gordon456");
		List<RegisteredUserXml> lOfRegisteredUser = getLOfRegisteredUser(xmlStr);
		assertEquals("userId should be  ", 1, lOfRegisteredUser.size());
		RegisteredUserXml registeredUser = lOfRegisteredUser.get(0);
		System.out.println(registeredUser.toString());
		
		assertEquals("read failed - authorityLevel incorrect ", "99", registeredUser.getAuthorityLevel());
		assertEquals("read failed - email incorrect ", "gordon@gmail.com", registeredUser.getEmail());
		assertEquals("read failed - firstname incorrect ", "gordon", registeredUser.getFirstname());
		assertEquals("read failed - lastname incorrect ", "Mills", registeredUser.getLastname());
		assertEquals("read failed - password incorrect ", "kelly1233", registeredUser.getPassword());
		assertEquals("read failed - telephone incorrect ", "1111 4444444", registeredUser.getTelephone());
		assertEquals("read failed - userId incorrect ", "gordon456", registeredUser.getUserId());
	}
	
	@Test
	public void updateEmailTest () {
		setUpRegisteredUsers();
		String xmlStr = registeredUserClient.find("dave123");
		List lOfRegisteredUser = getLOfRegisteredUser(xmlStr);
		RegisteredUserXml registeredUser = (RegisteredUserXml)lOfRegisteredUser.get(0);
		registeredUser.setEmail("dave_allen476@yahoo.co.uk");
		RegisteredUserMsgXml RegisteredUserMsg = new RegisteredUserMsgXml(registeredUser);
		
		ClientResponse response = registeredUserClient.update(RegisteredUserMsg);
		System.out.println(response);
		assertEquals("create failed ", "POST http://localhost:8080/sellemws/rest/registeredusers/update returned a response status of 200 OK", response.toString());

		xmlStr = registeredUserClient.find("dave123");
		lOfRegisteredUser = getLOfRegisteredUser(xmlStr);
		registeredUser = (RegisteredUserXml)lOfRegisteredUser.get(0);
		
		System.out.println(registeredUser.toString());
		
		assertEquals("read failed - authorityLevel incorrect ", "99", registeredUser.getAuthorityLevel());
		assertEquals("read failed - email incorrect ", "dave_allen476@yahoo.co.uk", registeredUser.getEmail());
		assertEquals("read failed - firstname incorrect ", "Dave", registeredUser.getFirstname());
		assertEquals("read failed - lastname incorrect ", "Allen", registeredUser.getLastname());
		assertEquals("read failed - password incorrect ", "kelly1233", registeredUser.getPassword());
		assertEquals("read failed - telephone incorrect ", "0772 234654", registeredUser.getTelephone());
		assertEquals("read failed - userId incorrect ", "dave123", registeredUser.getUserId());
	}

}
