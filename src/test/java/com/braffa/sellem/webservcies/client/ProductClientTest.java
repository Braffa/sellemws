package com.braffa.sellem.webservcies.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braffa.sellem.datafortesting.ProductTestData;
import com.braffa.sellem.model.sql.Product;
import com.braffa.sellem.model.sql.UserToProduct;
import com.braffa.sellem.model.xml.ProductMsgXml;
import com.braffa.sellem.model.xml.ProductXml;
import com.braffa.sellem.tables.IDBActions;
import com.braffa.sellem.tables.TableEnum;
import com.braffa.sellem.tables.TableFactory;
import com.sun.jersey.api.client.ClientResponse;

public class ProductClientTest {

	private static IDBActions mySqlProduct;

	private ProductTestData ptd = new ProductTestData();

	private ProductClient productClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mySqlProduct = TableFactory.getTable(TableEnum.PRODUCT);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// mySqlProduct.dropTable();
	}

	@Before
	public void setUp() throws Exception {
		try {
			mySqlProduct.dropTable();
		} catch (Exception e) {
			// ignore if no table found
		}
		mySqlProduct.createTable();
		productClient = new ProductClient();
	}

	private void setUpProducts() {
		try {
			Product product = ptd.setUpProduct1();
			mySqlProduct.create(product);
			product = ptd.setUpProduct2();
			mySqlProduct.create(product);
			product = ptd.setUpProduct3();
			mySqlProduct.create(product);
			product = ptd.setUpProduct4();
			mySqlProduct.create(product);
			product = ptd.setUpProduct5();
			mySqlProduct.create(product);
			product = ptd.setUpProduct6();
			mySqlProduct.create(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ProductMsgXml convertStringToObject(String xmlStr) {
		try {
			StringReader reader = new StringReader(xmlStr);
			JAXBContext jaxbContext = JAXBContext.newInstance(ProductMsgXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (ProductMsgXml) jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ProductXml> getLOfProductMsgXml(String xmlStr) {
		ProductMsgXml productMsgXml = convertStringToObject(xmlStr);
		return productMsgXml.getLOfProducts();
	}

	@Test
	public void countTest() {
		setUpProducts();
		String count = productClient.getCount();
		System.out.println(count);
		assertEquals("count failed Incorrect number of rows ", "6", count);
	}

	@Test
	public void createTest() {
		ProductXml product = new ProductXml.ProductBuilder().author("Sandercoe Justin")
				.imageURL("http://ecx.images-amazon.com/images/I/41hdqEVaWML._SL75_.jpg")
				.imageLargeURL("http://ecx.images-amazon.com/images/I/41hdqEVaWML._SL75_.jpg")
				.manufacturer("Music Sales").productIndex("1").productgroup("Book").productId("9781849386685")
				.productidtype("EAN").source("Amazon").sourceid("1849386684")
				.title("Justinguitar.Com Beginners Songbook J. Sandercoe (Easy Guitar With Notes and Tab)").build();
		ProductMsgXml productMsgXml = new ProductMsgXml(product);
		ClientResponse response = productClient.create(productMsgXml);
		System.out.println(response);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/create returned a response status of 200 OK",
				response.toString());

	}

	@Test
	public void deleteTest() {
		setUpProducts();
		String response = productClient.delete("9781849386685");
		System.out.println(response);
		assertEquals(
				"DELETE http://localhost:8080/sellemws/rest/product/delete/9781849386685 returned a response status of 200 OK",
				response.toString());
	}

	@Test
	public void removeTest() {
		setUpProducts();
		String response = productClient.remove("9780789724410");
		System.out.println(response);
		assertEquals("remove failed ",
				"DELETE http://localhost:8080/sellemws/rest/product/remove/9780789724410 returned a response status of 204 No Content",
				response);
	}

	@Test
	public void findallTest() {
		setUpProducts();
		String xmlStr = productClient.findall();
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		assertEquals("userId should be  ", 6, lOfProducts.size());
	}

	@Test
	public void findByProductIdTest() {
		setUpProducts();
		String xmlStr = productClient.findByProductId("9780789724410");
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		assertEquals("userId should be  ", 1, lOfProducts.size());
		ProductXml productXml = lOfProducts.get(0);
		assertEquals("getProduct failed Incorrect author returned ", "Mark Wutka", productXml.getAuthor());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product group returned ", "Book", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789724410", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724413", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ",
				"Using Java Server Pages and Servlets Special Edition (Special Edition Using)", productXml.getTitle());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());

	}
	
	@Test
	public void findByLOfProductIds() {
		setUpProducts();
		ProductXml productXml = new ProductXml();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		ArrayList<ProductXml> lOfProducts = new ArrayList<ProductXml>();
		productXml = new ProductXml("9780789724410");
		lOfProducts.add(productXml);
		productXml = new ProductXml("9781861005618");
		lOfProducts.add(productXml);
		productXml = new ProductXml("978098056856");
		lOfProducts.add(productXml);
		productXml = new ProductXml("5050582388237");
		lOfProducts.add(productXml);
		productXml = new ProductXml("9781849386685");
		lOfProducts.add(productXml);
		productMsg.setLOfProducts(lOfProducts);
		
		//String xmlStr = productClient.findByKeys(productMsg);
		
		


	}

	@Test
	public void updateAuthorTest() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("change of author").imageURL("")
				.imageLargeURL("").manufacturer("").productIndex("").productgroup("").productId(product.getProductId())
				.productidtype("").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);
		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		assertEquals("userId should be  ", 1, lOfProducts.size());
		productXml = lOfProducts.get(0);
		assertEquals("getProduct failed Incorrect author returned ", "change of author", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateImageUrl() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("change of image url")
				.imageLargeURL("").manufacturer("").productIndex("").productgroup("").productId(product.getProductId())
				.productidtype("").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		System.out.println(response);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ", "change of image url",
				productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateLargeImageUrl() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("")
				.imageLargeURL("change of large image url").manufacturer("").productIndex("").productgroup("")
				.productId(product.getProductId()).productidtype("").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ", "change of large image url",
				productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());

	}

	@Test
	public void testUpdateManufacturer() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("change of Manufacturer").productIndex("").productgroup("")
				.productId(product.getProductId()).productidtype("").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "change of Manufacturer",
				productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateProductIndex() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("1").productgroup("").productId(product.getProductId()).productidtype("")
				.source("").sourceid("").title("").build();
		System.out.println(productXml.toString());
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "1", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateProductGroup() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("").productgroup("test").productId(product.getProductId())
				.productidtype("").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "test", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateProductIdType() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("").productgroup("").productId(product.getProductId())
				.productidtype("DAB").source("").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect product type returned ", "DAB", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateSource() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("").productgroup("").productId(product.getProductId()).productidtype("")
				.source("TESCO").sourceid("").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect id product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "TESCO", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateSourceID() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("").productgroup("").productId(product.getProductId()).productidtype("")
				.source("").sourceid("999999").title("").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect id product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "999999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "The Choocolate Factory", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}

	@Test
	public void testUpdateTitle() {
		setUpProducts();
		Product product = ptd.setUpProduct6();
		ProductXml productXml = new ProductXml.ProductBuilder().author("").imageURL("").imageLargeURL("")
				.manufacturer("").productIndex("").productgroup("").productId(product.getProductId()).productidtype("")
				.source("").sourceid("").title("Change of title").build();
		ProductMsgXml productMsg = new ProductMsgXml(productXml);

		ClientResponse response = productClient.update(productMsg);
		assertEquals("create failed ",
				"POST http://localhost:8080/sellemws/rest/product/update returned a response status of 200 OK",
				response.toString());
		String xmlStr = productClient.findByProductId(product.getProductId());
		List<ProductXml> lOfProducts = getLOfProductMsgXml(xmlStr);
		productXml = lOfProducts.get(0);

		assertEquals("getProduct failed Incorrect author returned ", "Willy wonka", productXml.getAuthor());
		assertEquals("getProduct failed Incorrect image URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageURL());
		assertEquals("getProduct failed Incorrect image large URL returned ",
				"http://ecx.images-amazon.com/images/I/51TW6S55A4L._SL75_.jpg", productXml.getImageLargeURL());
		assertEquals("getProduct failed Incorrect manufacturer returned ", "QUE", productXml.getManufacturer());
		assertEquals("getProduct failed Incorrect product index returned ", "0", productXml.getProductIndex());
		assertEquals("getProduct failed Incorrect product group returned ", "DVD", productXml.getProductgroup());
		assertEquals("getProduct failed Incorrect product id returned ", "9780789799999", productXml.getProductId());
		assertEquals("getProduct failed Incorrect id product type returned ", "EAN", productXml.getProductidtype());
		assertEquals("getProduct failed Incorrect source returned ", "Amazon", productXml.getSource());
		assertEquals("actualProduct failed Incorrect source id returned ", "789724999", productXml.getSourceid());
		assertEquals("getProduct failed Incorrect title returned ", "Change of title", productXml.getTitle());
		assertTrue("getProduct failed crDate is null", null != productXml.getCrDate());
		assertTrue("getProduct failed updDate is null", null != productXml.getUpdDate());
	}
}
