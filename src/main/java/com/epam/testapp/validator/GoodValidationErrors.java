package com.epam.testapp.validator;

import java.io.Serializable;

/**
 * GoodValidationErrors --- bean class which contains string fields describing errors
 * corresponding fields of class Good
 * 
 * @author Marharyta_Amelyanchuk
 */
public class GoodValidationErrors implements Serializable {

	private static final long serialVersionUID = 7847122968294960921L;
	private String producerError;
	private String modelError;
	private String dateError;
	private String colorError;
	private String priceError;
	private String notInStockError;

	public String getProducerError() {
		return producerError;
	}

	public void setProducerError(String producerError) {
		this.producerError = producerError;
	}

	public String getModelError() {
		return modelError;
	}

	public void setModelError(String modelError) {
		this.modelError = modelError;
	}

	public String getDateError() {
		return dateError;
	}

	public void setDateError(String dateError) {
		this.dateError = dateError;
	}

	public String getColorError() {
		return colorError;
	}

	public void setColorError(String colorError) {
		this.colorError = colorError;
	}

	public String getPriceError() {
		return priceError;
	}

	public void setPriceError(String priceError) {
		this.priceError = priceError;
	}

	public String getNotInStockError() {
		return notInStockError;
	}

	public void setNotInStockError(String notInStockError) {
		this.notInStockError = notInStockError;
	}

	
}
