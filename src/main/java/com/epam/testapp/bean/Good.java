package com.epam.testapp.bean;

import java.io.Serializable;
import java.util.Map;

import com.epam.testapp.constants.XslParameter;

/**
 * Good --- model class which describes product with string fields producer,
 * model, date, color, price and float field parsedPrice, boolean field inStock
 * 
 * @author Marharyta_Amelyanchuk
 */
public class Good implements Serializable {

	private static final long serialVersionUID = 1968210181155288366L;
	private String producer;
	private String model;
	private String date;
	private String color;
	private String price;
	private float parsedPrice;
	private boolean notInStock;

	public Good() {
	}

	/**
	 * Good --- model class which describes product with string fields producer,
	 * model, date, color, price and float field parsedPrice, boolean field
	 * inStock
	 * 
	 * @param params
	 *            a map of pairs with name of good field as key and appropriate
	 *            value; sets field 'inStock' as false if map contain parameter
	 *            'not_on_stock'
	 */
	public Good(Map<String, String> params) {
		this.producer = params.get(XslParameter.PRODUCER.toString());
		this.model = params.get(XslParameter.MODEL.toString());
		this.date = params.get(XslParameter.DATE.toString());
		this.color = params.get(XslParameter.COLOR.toString());
		this.price = params.get(XslParameter.PRICE.toString()).trim();
		if (params.get(XslParameter.NOT_IN_STOCK.toString()) != null) {
			notInStock = true;
		}
	}
	
	public Good(String producer, String model, String date, String color,
			String price) {
		this.producer = producer;
		this.model = model;
		this.date = date;
		this.color = color;
		this.price = price;
	}

	public Good(String producer, String model, String date, String color,
			int parsedPrice) {
		this.producer = producer;
		this.model = model;
		this.date = date;
		this.color = color;
		this.parsedPrice = parsedPrice;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isNotInStock() {
		return notInStock;
	}

	public void setNotInStock(boolean notInStock) {
		this.notInStock = notInStock;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getParsedPrice() {
		return parsedPrice;
	}

	public void setParsedPrice(float parsedPrice) {
		this.parsedPrice = parsedPrice;
	}

}
