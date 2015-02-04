package com.epam.testapp.validator;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * ValidatorManager --- final public class with private constructor for getting property values by key
 * from property file
 * 
 * @author Marharyta_Amelyanchuk
 */
final public class ValidatorManager {
	private static final Logger LOG = Logger.getLogger(ValidatorManager.class);
	private final static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("validation");

	private ValidatorManager() {
	}

	public static String getProperty(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
