     package com.encora.CustomerService;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService service;
	private final ProductClient client;
    private final OrderClient orderClient;


	 public CustomerController(CustomerService service, ProductClient client, OrderClient orderClient) {
		super();
		this.service = service;
		this.client = client;
		this.orderClient = orderClient;
	}

	 @PostMapping
	public Customer createCustomer(@RequestBody Customer customer)
	{
	    return service.save(customer);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable("id") Long id)
	{
	   Optional<Customer> customer = service.findById(id);
	   return customer.map(ResponseEntity::ok)
	          .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<?> showAllProducts() {
	    return client.getProducts();
	}
	
	
	
	@PostMapping("/placeorder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderClient.placeOrder(orderRequest);
    }

	
	@DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) 
    {
		return orderClient.cancelOrder(orderId);
    }
}
