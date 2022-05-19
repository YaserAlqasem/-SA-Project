package restapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import restapplication.domain.*;

@SpringBootApplication
public class RestApplication implements CommandLineRunner {

	@Autowired
	private RestOperations restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String customerUrl = "http://localhost:8086/customer-service/customer";
		String productUrl = "http://localhost:8086/product-service/products";
		String orderUrl = "http://localhost:8086/order-service/orders";
		String shoppingCartUrl = "http://localhost:8086/cart-command-service/carts";
		String shoppingCartServiceUrl = "http://localhost:8086/cart-query-service/carts";

		//add product1 - Apple-Watch
		restTemplate.postForLocation(productUrl, new Product(
				"1",
				"Apple Watch",
				120.80,
				"Watch",
				250
		));

		//add product2 - Apple-Iphone
		restTemplate.postForLocation(productUrl, new Product(
				"2",
				"Iphone 13",
				1000.50,
				"Iphone",
				300
		));

		// get product1
		System.out.println("----------- product1 -> Apple Watch -----------------------");
		Product product1 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
		System.out.println(product1);

		// get product2
		System.out.println("----------- product2 -> Apple Iphone -----------------------");
		Product product2 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "2");
		System.out.println(product2);

		// modify product1's stock
		product1.setNumberInStock(200);
		restTemplate.put(productUrl + "/{productNumber}", product1, product1.getProductNumber());

		// get and confirm modified product
		Product modifiedProduct = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "1");
		System.out.println("----------- Product1 after modification-> apple watch with number In Stock changed to 200 -----------------------");
		System.out.println(modifiedProduct);

		// Create a customer
		Customer customer1 = new Customer(
				"612132",
				"John",
				"Michel",
				"1000 street",
				"Fairfield",
				"52557",
				"12345678",
				"johnmichel@gmail.com"
		);
		restTemplate.postForLocation(customerUrl + "/add", customer1);

		// get the added Customer
		Customer customer = restTemplate.getForObject(customerUrl + "/{cId}", Customer.class, "customer1");
		System.out.println("----------- John Michel -----------------------");
		System.out.println(customer);

		// - First create a shopping cart for customer1 using the shopping cart command service
		Long shoppingCartNumber = 1L;
		ShoppingCartCustomer shoppingCartCustomer = new ShoppingCartCustomer(customer1.getcId(), shoppingCartNumber);
		restTemplate.postForLocation(shoppingCartUrl, shoppingCartCustomer);

		//- Put product1 to the shopping cart
		ShoppingCartProduct shoppingCartProduct1 = new ShoppingCartProduct(
				Long.parseLong(product1.getProductNumber()),
				8,
				product1.getPrice());
		restTemplate.postForLocation(shoppingCartUrl + "/{cartId}/products", shoppingCartProduct1, shoppingCartNumber);

		//- Also put product2 to the shopping cart
		ShoppingCartProduct shoppingCartProduct2 = new ShoppingCartProduct(
				Long.parseLong(product2.getProductNumber()),
				23,
				product2.getPrice());
		restTemplate.postForLocation(shoppingCartUrl + "/{cartId}/products", shoppingCartProduct2, shoppingCartNumber);

		// get the shopping cart from the shopping cart query service
		ShoppingCart shoppingCart = restTemplate.getForObject(shoppingCartServiceUrl + "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
		System.out.println("----------- the shopping cart with two products -----------------------");
		System.out.println(shoppingCart);

		// change the quantity of product1 in the shopping cart from 8 to 5
		restTemplate.put(shoppingCartUrl + "/{cartId}/products/{productNumber}", new Quantity(5), shoppingCartNumber, product1.getProductNumber());

		// delete product2 from the shopping cart
		restTemplate.delete(shoppingCartUrl + "/{cartId}/products/{productNumber}", shoppingCartNumber, product2.getProductNumber());


		// get the updated shopping cart for display
		// add a sleep to allow for eventual consistency updating of the query service
		Thread.sleep(2000);
		ShoppingCart shoppingCartUpdated = restTemplate.getForObject(shoppingCartServiceUrl + "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
		System.out.println("----------- the updated shopping cart -----------------------");
		System.out.println(shoppingCartUpdated);


		String checkoutMessage = restTemplate.getForObject(shoppingCartUrl + "/{cartId}/checkout",  String.class, shoppingCartNumber);
		System.out.println("----------- checking out and sending the order to order service -----------");

		System.out.println(checkoutMessage);

		// retrieve order for customer1
		Orders order = restTemplate.getForObject(orderUrl + "/customers/{customerID}", Orders.class, "customer1");
		System.out.println("----------- order1 for customer1 -----------------------");
		System.out.println(order);

	}

	@Bean
	RestOperations restTemplate() {
		return new RestTemplate();
	}
}
