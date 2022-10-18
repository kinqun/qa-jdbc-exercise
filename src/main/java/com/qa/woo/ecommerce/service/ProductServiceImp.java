package com.qa.woo.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.qa.woo.ecommerce.exception.ProductAlreadyExistsException;
import com.qa.woo.ecommerce.exception.ProductNotFoundException;
import com.qa.woo.ecommerce.model.Product;
import com.qa.woo.ecommerce.repository.Repository;

public class ProductServiceImp implements ProductService {
	Repository repository ;

	public ProductServiceImp() {
		this.repository = new Repository();
	}

	@Override
	public Product getProductById(int id) throws ProductNotFoundException{
		return Optional.ofNullable(repository.getProductById(id)).orElseThrow(()-> new ProductNotFoundException("...product doesnt exist in the database \n"));
	};
	
	@Override
	public List<Product> getAllProducts(){
		return repository.getAllProducts();
		
	};
	
	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistsException{
		return Optional.ofNullable(repository.addProduct(product)).orElseThrow(()-> new ProductAlreadyExistsException("...unable to add product to database, product already in database \n"));
	};
	
	@Override
	public Product updateProduct(Product product) throws ProductNotFoundException{
		return Optional.ofNullable(repository.updateProduct(product)).orElseThrow(()-> new ProductNotFoundException("...product to be updated doesnt exist in database \n"));
	};
	
	@Override
	public boolean deleteProduct(int id) throws ProductNotFoundException{
		boolean isDeleted = repository.deleteProduct(id);
		
		if(!isDeleted) {
			throw new ProductNotFoundException("...product doesnt exist in database, nothing to delete \n");
		}
		
		return isDeleted;
	};
	
	public void showProductInfo(Product product) throws ProductNotFoundException{
		System.out.println("Show Product Details:");
		if(product == null) {
			throw new ProductNotFoundException("...invalid Product \n");
		}
		System.out.println("Product ID: " + product.getId());
		System.out.println("Product Name: " + product.getName());
		System.out.println("Product Price: $" + String.format("%.2f", product.getPrice()));
		System.out.println("Product Category: " + product.getCategory());
		System.out.println("Product Availabile: " + product.isInStock());
		System.out.println("Product Discount: " + product.getDiscountPercentage() + "%");
		System.out.println("Product Delievery Mode: " + product.getDeliveryMode());
		System.out.println("Product is Returnable: " + product.isReturnAccepted());
		System.out.println("Product's Seller: " + product.getSellerName());
		System.out.println("");
	}
}
