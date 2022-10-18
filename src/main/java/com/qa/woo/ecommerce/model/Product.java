package com.qa.woo.ecommerce.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private float price;
	private String category;
	private boolean inStock;
	private byte discountPercentage;
	private String deliveryMode;
	private boolean isReturnAccepted;
	private String sellerName;
}
