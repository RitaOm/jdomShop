package com.epam.testapp.validator;

import com.epam.testapp.bean.Good;

/**
 * GoodValidator --- class with validating method of fields of a Good instance
 * 
 * @author Marharyta_Amelyanchuk
 */
public class GoodValidator {
	/**a field for saving detected errors */
	private GoodValidationErrors errors;
	/**a field for setting it false if a good is invalid */
	private boolean isGoodValid = true;

	public GoodValidator() {
		errors = new GoodValidationErrors();
	}

	/**
	 * validate all the fields of a Good and saves the errors in errors field 
	 * 
	 * @param good
	 *            a Good instance to validate
	 * 
	 * @return boolean true if good is valid and false otherwise 
	 */
	public boolean validate(Good good) {
		
		if (good.getProducer() == null || good.getProducer().isEmpty()) {
			errors.setProducerError(ValidatorManager
					.getProperty("error.empty.field"));
			isGoodValid = false;
		}
		if (good.getModel() == null || good.getModel().isEmpty()) {
			errors.setModelError(ValidatorManager
					.getProperty("error.empty.field"));
			isGoodValid = false;
		} else if (!good.getModel().matches(
				ValidatorManager.getProperty("model.pattern"))) {
			errors.setModelError(ValidatorManager
					.getProperty("error.model.format"));
			isGoodValid = false;
		}
		if (good.getDate() == null || good.getDate().isEmpty()) {
			errors.setDateError(ValidatorManager
					.getProperty("error.empty.field"));
			isGoodValid = false;
		} else if (!good.getDate().matches(
				ValidatorManager.getProperty("date.pattern"))) {
			errors.setDateError(ValidatorManager
					.getProperty("error.date.format"));
			isGoodValid = false;
		}
		if (good.getColor() == null || good.getColor().isEmpty()) {
			errors.setColorError(ValidatorManager
					.getProperty("error.empty.field"));
			isGoodValid = false;
		}
		if ((good.getPrice() == null || good.getPrice().isEmpty())) {
			if (!good.isNotInStock()) {
				errors.setNotInStockError(ValidatorManager
						.getProperty("price.or.not.in.stock"));
				isGoodValid = false;
			} 
		} else if (!good.getPrice().matches(
				ValidatorManager.getProperty("price.pattern"))) {
			errors.setPriceError(ValidatorManager.getProperty("price.number"));
			isGoodValid = false;
		} else {
			if (good.getPrice().contains(",")) {
				good.setPrice(good.getPrice().replace(",", "."));
			}
			float price = Float.parseFloat(good.getPrice());
			if ((good.isNotInStock() && price > 0)
					|| (!good.isNotInStock() && price == 0)) {
				errors.setNotInStockError(ValidatorManager
						.getProperty("price.or.not.in.stock"));
				isGoodValid = false;
			}
			good.setParsedPrice(price);
		}
		return isGoodValid;
	}

	public GoodValidationErrors getErrors() {
		return errors;
	}

	public boolean isGoodValid() {
		return isGoodValid;
	}
	
	

}
