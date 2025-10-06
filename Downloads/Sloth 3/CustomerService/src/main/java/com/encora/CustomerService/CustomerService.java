package com.encora.CustomerService;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;


	public CustomerService(CustomerRepository customerRepository)
	{
		super();
		this.customerRepository = customerRepository;
	}
	
	
	
	public Customer save(Customer customer)
	{
	    return customerRepository.save(customer);
	}

	
	
	public Optional<Customer> findById(Long id)
	{
	    return customerRepository.findById(id);
	}
	
	
	
}
