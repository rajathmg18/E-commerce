package com.encora.OrderService.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.encora.OrderService.ProductClient;
import com.encora.OrderService.Exceptionhandling.ProductException;
import com.encora.OrderService.Model.Order;
import com.encora.OrderService.Repository.OrderRepository;

import jakarta.ws.rs.BadRequestException;


@Service
public class OrderService {
	
	private final ProductClient productClient;
    private final OrderRepository orderRepository;
    
    
	public OrderService(ProductClient productClient, OrderRepository orderRepository) {
		super();
		this.productClient = productClient;
		this.orderRepository = orderRepository;
	}
	
	public List<Order> findall()
	{
		return orderRepository.findAll();
	}
	
	//cancel order
	public void cancelOrder(Long orderId) {
	    Order order = orderRepository.findById(orderId)
	        .orElseThrow(() -> new ProductException("Order not found"));

	    if ("Delivered".equalsIgnoreCase(order.getOrderStatus())) {
	        throw new BadRequestException("Cannot cancel order already delivered");
	    }

	    // Restore product quantity in product service
	    ResponseEntity<?> response = productClient.increaseQuantity(order.getProductId(), order.getQuantity());

	    if (!response.getStatusCode().is2xxSuccessful()) {
	        throw new RuntimeException("Failed to restore product quantity during order cancellation");
	    }

	    order.setOrderStatus("Cancelled");
	    orderRepository.save(order);
	}

	
	
	//place order
	public Order placeOrder(Long productId, Long customerId, int quantity,String orderdate,String orderStatus) {
        ResponseEntity<?> response = productClient.decreaseQuantity(productId, quantity);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ProductException("Product quantity insufficient or product not found");
        }

        Order order = new Order();
        order.setProductId(productId);
        order.setCustomerId(customerId);
        order.setQuantity(quantity);
        order.setOrderDate(orderdate);
        order.setOrderStatus(orderStatus);

        return orderRepository.save(order);
    }

    
}
