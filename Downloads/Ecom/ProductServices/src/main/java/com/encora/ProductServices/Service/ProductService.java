package com.encora.ProductServices.Service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.encora.ProductServices.Exceptionhandling.InvalidProductRequestException;
import com.encora.ProductServices.Exceptionhandling.ProductNotFoundException;
import com.encora.ProductServices.Model.Product;
import com.encora.ProductServices.Repository.ProductRepository;



@Service
public class ProductService {

	  @Autowired
	    private ProductRepository productRepository;
	 
	    // Get all products
	    public List<Product> getAllProducts() {
	        return productRepository.findAll();
	    }
	 
	    // Get product by ID
	    public Optional<Product> getProductById(Long id) {
	        return productRepository.findById(id);
	    }
	 
	    // Add product
	    public Product addProduct(Product prod) {
	        return productRepository.save(prod);
	    }
	 
	    // Delete product by ID
	    public void deleteProduct(Long id) {
	        productRepository.deleteById(id);
	    }
	    

		
		public Product DecreaseQuantity(Long ProductId,int quantity)
		{
			Product product =productRepository.findById(ProductId).orElseThrow(()->new ProductNotFoundException("Product not found"));
			
			if(product.getQuantity() < quantity)
			{
				throw new InvalidProductRequestException("Insufficient product quantity");
			}
			
			product.setQuantity(product.getQuantity()-quantity);
			
			return productRepository.save(product);
		}
		
		
		
		
		public ResponseEntity<String> increaseProductQuantity( Long id, int quantity) {
		    Product product = productRepository.findById(id)
		        .orElseThrow(() -> new ProductNotFoundException("Product not found"));

		    product.setQuantity(product.getQuantity() + quantity);
		    productRepository.save(product);
		    return ResponseEntity.ok("Product quantity increased");
		}

	
	
}

