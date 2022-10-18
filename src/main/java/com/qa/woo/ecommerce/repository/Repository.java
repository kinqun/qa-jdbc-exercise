package com.qa.woo.ecommerce.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qa.woo.ecommerce.model.Product;

public class Repository {
	
	public Connection getDbConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb","root","root");
//				if(connection != null) {
//					System.out.println("successfully connected to productdb database");
//				}
			}catch(SQLException e) {
				System.out.println("...Invalid database login");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("...MySql connector jar file not found in class path");
		}
		
		return connection;
	}
	
	public void createProductTable() {
		
		try (Connection con = getDbConnection()){
			Statement stmt = con.createStatement();
			
			String sqlQueryCreateTable = "CREATE TABLE IF NOT EXISTS product_details("
	    			+ "id INT NOT NULL PRIMARY KEY,"
	    			+ "name VARCHAR(45),"
	    			+ "price DOUBLE,"
	    			+ "category VARCHAR(45),"
	    			+ "in_stock BOOLEAN,"
	    			+ "discount_percentage INT,"
	    			+ "delivery_mode VARCHAR(45),"
	    			+ "is_return_accepted BOOLEAN,"
	    			+ "seller_name VARCHAR(45))";
	    	int rows = stmt.executeUpdate(sqlQueryCreateTable);
	    	System.out.println(rows);
	    	
		} catch (SQLException e) {
			System.out.println("...couldnt access database");
			e.printStackTrace();
		} 
		
	}
	
	public Product getProductById(int id) {
		Product product = null;
		
		try (Connection con = getDbConnection()){
			Statement stmt = con.createStatement();
					
			String selectQuery = "SELECT * FROM product_details WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			if(rs.next() == true) {
				System.out.println("Product(" + id + ") IS found! \n");
				
				product =  Product.builder()
						  .id(rs.getInt("id"))
						  .name(rs.getString("name"))
						  .price(rs.getFloat("price"))
						  .category(rs.getString("category"))
						  .inStock(rs.getBoolean("in_stock"))
						  .discountPercentage(rs.getByte("discount_percentage"))
						  .deliveryMode(rs.getString("delivery_mode"))
						  .isReturnAccepted(rs.getBoolean("is_return_accepted"))
						  .sellerName(rs.getString("seller_name"))
						  .build();
			}else {
				System.out.println("Product(" + id + ") NOT found!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	public List<Product> getAllProducts() {
		List<Product> allProducts = new ArrayList<>();
		
		try (Connection con = getDbConnection()){
			Statement stmt = con.createStatement();
			String selectQuery = "SELECT * FROM product_details";
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next() != false) {
				Product product = Product.builder()
										  .id(rs.getInt("id"))
										  .name(rs.getString("name"))
										  .price(rs.getFloat("price"))
										  .category(rs.getString("category"))
										  .inStock(rs.getBoolean("in_stock"))
										  .discountPercentage(rs.getByte("discount_percentage"))
										  .deliveryMode(rs.getString("delivery_mode"))
										  .isReturnAccepted(rs.getBoolean("is_return_accepted"))
										  .sellerName(rs.getString("seller_name"))
										  .build();
				
				allProducts.add(product);
			}

			System.out.println("All Products Found! \n");
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return allProducts;
	}
		
	public Product addProduct(Product product) {
		Product productToAdd = null;
		Product queryProduct = getProductById(product.getId());
		
		if(queryProduct == null) {
			try (Connection con = getDbConnection()){
	        	PreparedStatement preparedInsertQuery = con.prepareStatement("INSERT IGNORE INTO product_details VALUES(?,?,?,?,?,?,?,?,?)");
	        	preparedInsertQuery.setInt(1, product.getId());
	        	preparedInsertQuery.setString(2, product.getName());
	        	preparedInsertQuery.setFloat(3, product.getPrice());
	        	preparedInsertQuery.setString(4, product.getCategory());
	        	preparedInsertQuery.setBoolean(5, product.isInStock());
	        	preparedInsertQuery.setByte(6, product.getDiscountPercentage());
	        	preparedInsertQuery.setString(7, product.getDeliveryMode());
	        	preparedInsertQuery.setBoolean(8, product.isReturnAccepted());
	        	preparedInsertQuery.setString(9, product.getSellerName());
	        	int insertedRows = preparedInsertQuery.executeUpdate();

	        	if(insertedRows > 0) {
	        		System.out.println("Product(" + product.getId() + ") IS added! \n");
	        	}
	        	
	        	productToAdd = product;
	        	
	        }catch(SQLException e) {
	        	e.printStackTrace();
	        }
		}else if(queryProduct.getId() == product.getId()) {
			System.out.println("Product(" + product.getId() + ") NOT added");
		}
    	
    	return productToAdd;
		
	}

	public Product updateProduct(Product product) {
		Product updatedProduct = null;
		
		try (Connection con = getDbConnection()) {
			String updateQuery = "UPDATE product_details SET "
					+ "name=?, price=?, category=?, in_stock=?, discount_percentage=?, delivery_mode=?, is_return_accepted=?, seller_name=? "
					+ "WHERE id=?";
			PreparedStatement prepareUpdateQuery = con.prepareStatement(updateQuery);
			prepareUpdateQuery.setString(1, product.getName());
			prepareUpdateQuery.setFloat(2, product.getPrice());
			prepareUpdateQuery.setString(3, product.getCategory());
			prepareUpdateQuery.setBoolean(4, product.isInStock());
			prepareUpdateQuery.setByte(5, product.getDiscountPercentage());
			prepareUpdateQuery.setString(6, product.getDeliveryMode());
			prepareUpdateQuery.setBoolean(7, product.isReturnAccepted());
			prepareUpdateQuery.setString(8, product.getSellerName());
			prepareUpdateQuery.setInt(9, product.getId());
			
			int rows = prepareUpdateQuery.executeUpdate();
			
			if(rows > 0) {
				System.out.println("Product(" + product.getId() + ") IS updated! \n");
				updatedProduct = product;
			}else {
				System.out.println("nothing updated");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return updatedProduct;
		
	}
	
	public boolean deleteProduct(int id) {
		try (Connection con = getDbConnection()){
			Statement stmt = con.createStatement();
			String deleteQuery = ("DELETE FROM product_details WHERE id = " + id);
			int row = stmt.executeUpdate(deleteQuery);
			
			if(row > 0) {
				System.out.println("Product(" + id + ") IS deleted");
				return true;
			}else {
				System.out.println("nothing deleted");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
