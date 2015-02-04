package com.epam.testapp.presentation.form;

import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

import com.epam.testapp.bean.Good;
import com.epam.testapp.validator.GoodValidationErrors;

/**
 * ProductForm --- a JavaBean class with properties and their setters and
 * getters methods helping association between ProductAction class methods and
 * jsp pages, extends ActionForm
 * 
 * 
 * @author Marharyta_Amelyanchuk
 */
public class ProductForm extends ActionForm {

	private static final long serialVersionUID = 8165324849362759536L;
	private Document document;
	private Good good;
	private GoodValidationErrors errors;
	private int catIndex = -1;
	private int subcatIndex = -1;
	private String category;
	private String subcategory;
	private Integer[] idsNotInStock;
	private Map<Integer, String> priceMap;

	public ProductForm() {
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public GoodValidationErrors getErrors() {
		return errors;
	}

	public void setErrors(GoodValidationErrors errors) {
		this.errors = errors;
	}

	public int getCatIndex() {
		return catIndex;
	}

	public void setCatIndex(int catIndex) {
		this.catIndex = catIndex;
	}

	public int getSubcatIndex() {
		return subcatIndex;
	}

	public void setSubcatIndex(int subcatIndex) {
		this.subcatIndex = subcatIndex;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public Integer[] getIdsNotInStock() {
		return idsNotInStock;
	}

	public void setIdsNotInStock(Integer[] idsNotInStock) {
		this.idsNotInStock = idsNotInStock;
	}

	public Map<Integer, String> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<Integer, String> priceMap) {
		this.priceMap = priceMap;
	}

	public String getValue(String key) {
		return priceMap.get(Integer.parseInt(key));
	}

	public void setValue(String key, String value) {
		priceMap.put(Integer.parseInt(key), value);
	}

}