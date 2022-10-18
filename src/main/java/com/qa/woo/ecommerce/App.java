package com.qa.woo.ecommerce;

import java.util.List;
import java.util.Scanner;

import com.qa.woo.ecommerce.controller.ProductController;
import com.qa.woo.ecommerce.model.Product;

public class App 
{
    public static void main( String[] args )
    {     
       // Scanner scanner = new Scanner(System.in);
        ProductController prodCtrller = new ProductController();
        
        
        //TEST getAllProducts
        List<Product> prods = prodCtrller.getAllProducts();
        for(Product p : prods)System.out.println(p);
        System.out.println("");
        
        
		//TEST getProductById
        Product prod = prodCtrller.getProductById(99999);
        prodCtrller.showProductInfo(prod);
        Product prod2 = prodCtrller.getProductById(101);
        prodCtrller.showProductInfo(prod2);

        
        //TEST addProduct
        Product productToAdd = Product.builder()
        							  .id(9999)
        							  .name("Pen")
        							  .price(100.50f)
        							  .category("Stationary")
        							  .inStock(false)
        							  .discountPercentage((byte)25)
        							  .deliveryMode("free")
        							  .isReturnAccepted(false)
        							  .sellerName("Bob")
        							  .build();
        productToAdd = prodCtrller.addProduct(productToAdd);
        prodCtrller.addProduct(productToAdd);


//        	TEST updateProduct
        Product productToUpdate = Product.builder()
										  .id(9999)
										  .name("Pen2")
										  .price(1000.50f)
										  .category("Stationary2")
										  .inStock(true)
										  .discountPercentage((byte)35)
										  .deliveryMode("free2")
										  .isReturnAccepted(true)
										  .sellerName("Bob2")
										  .build();
        Product updatedProduct = prodCtrller.updateProduct(productToUpdate);
        
//        TEST deleteProduct
        prodCtrller.deleteProduct(9999);
        
        
        
        
        
        
        
    }
}
