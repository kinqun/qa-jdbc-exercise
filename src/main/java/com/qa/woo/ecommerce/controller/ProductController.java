package com.qa.woo.ecommerce.controller;

import java.util.List;

import com.qa.woo.ecommerce.exception.ProductAlreadyExistsException;
import com.qa.woo.ecommerce.exception.ProductNotFoundException;
import com.qa.woo.ecommerce.model.Product;
import com.qa.woo.ecommerce.service.ProductServiceImp;

public class ProductController {
	ProductServiceImp productService ;
	
	public ProductController(){
		this.productService = new ProductServiceImp();
	}
	
	public Product getProductById(int id) {
		Product fetchedProduct = null;
		
		try {
			fetchedProduct = productService.getProductById(id);
		}catch(ProductNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return fetchedProduct;
		
	}
	
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	public Product addProduct(Product product){
		Product productToAdd = null;
		
		try {
			productToAdd = productService.addProduct(product);
		}catch(ProductAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		
		return productToAdd;
	}
	
	public Product updateProduct(Product product){
		Product updatedProduct = null;
		
		try {
			updatedProduct = productService.updateProduct(product);
		}catch(ProductNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return updatedProduct;
	}
	
	public boolean deleteProduct(int id){
		boolean isDeleted = false;
		
		try {
			isDeleted = productService.deleteProduct(id);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return isDeleted;
	}
	
	public void showProductInfo(Product product) {
		try {
			productService.showProductInfo(product);
		} catch (ProductNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
