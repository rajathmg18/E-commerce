package com.encora.OrderService.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encora.OrderService.OrderRequestDTO;
import com.encora.OrderService.Model.Order;
import com.encora.OrderService.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}
	
	
	@PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            Order order = orderService.placeOrder(
                orderRequest.getProductId(),
                orderRequest.getCustomerId(),
                orderRequest.getQuantity(),
                orderRequest.getOrderdate(),
                orderRequest.getOrderStatus()
            );
            return ResponseEntity.ok(order);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
	
	
	
	@DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
		try {
	        orderService.cancelOrder(orderId);
	        return ResponseEntity.ok(Collections.singletonMap("message", "Order cancelled successfully"));
	    } catch (RuntimeException ex) {
	        return ResponseEntity.badRequest().body(Collections.singletonMap("error", ex.getMessage()));
	    }
    }
	
	@GetMapping
	public List<Order> findall()
	{
		return orderService.findall();
	}

}
