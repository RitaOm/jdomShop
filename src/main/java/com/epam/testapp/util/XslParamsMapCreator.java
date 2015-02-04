package com.epam.testapp.util;

import java.util.HashMap;
import java.util.Map;

import com.epam.testapp.bean.Good;
import com.epam.testapp.constants.XslParameter;
import com.epam.testapp.validator.GoodValidationErrors;
import com.epam.testapp.validator.GoodValidator;

/**
 * XSLParametersMapCreator --- an util class which has several static methods
 * with the same name to create new Map<Object,Object> from different parameters
 * which take place in xsl transformation
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class XslParamsMapCreator {

	/**
	 * Creates new Map and fills it with string parameters category name,
	 * subcategory name, good model name checking before if the parameter is
	 * null or not
	 * 
	 * @param categoryName
	 *            string which is the name of category
	 * @param subcategoryName
	 *            string which is the name of subcategory
	 * @param model
	 *            string which is the name of model
	 * 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> create(String categoryName,
			String subcategoryName, String model) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		return create(categoryName, subcategoryName, model, parametersMap);
	}

	/**
	 * Creates new Map and fills it with string parameters category name,
	 * subcategory name and Good, GoodValidator and GoodValidationErrors
	 * instances checking before if the parameter is null or not
	 * 
	 * @param good
	 *            Good instance
	 * @param validator
	 *            GoodValidator instance
	 * @param errors
	 *            GoodValidationErrors instance
	 * 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> create(Good good,
			GoodValidator validator, GoodValidationErrors errors) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		return create(good, validator, errors, parametersMap);
	}

	/**
	 * Creates new Map and fills it with Good, GoodValidator and
	 * GoodValidationErrors instances checking before if the parameter is null
	 * or not
	 * 
	 * @param good
	 *            Good instance
	 * @param validator
	 *            GoodValidator instance
	 * @param errors
	 *            GoodValidationErrors instance
	 * 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> create(String categoryName,
			String subcategoryName, Good good, GoodValidator validator,
			GoodValidationErrors errors) {
		Map<String, Object> parametersMap = create(categoryName,
				subcategoryName, null);
		return create(good, validator, errors, parametersMap);
	}
	
	/**
	 * Fills parameter Map with string parameters category name, subcategory
	 * name, good model name checking before if the parameter is null or not
	 * 
	 * @param categoryName
	 *            string which is the name of category
	 * @param subcategoryName
	 *            string which is the name of subcategory
	 * @param model
	 *            string which is the name of model
	 * 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> create(Good good,
			GoodValidator validator, GoodValidationErrors errors,
			Map<String, Object> parametersMap) {
		if (good != null) {
			parametersMap.put(XslParameter.GOOD.toString(), good);
		}
		if (validator != null) {
			parametersMap.put(XslParameter.VALIDATOR.toString(), validator);
		}
		if (errors != null) {
			parametersMap.put(XslParameter.ERRORS.toString(), errors);
		}
		return parametersMap;
	}

	/**
	 * Fills parameter Map with Good, GoodValidator and GoodValidationErrors
	 * instances checking before if the parameter is null or not
	 * 
	 * @param good
	 *            Good instance
	 * @param validator
	 *            GoodValidator instance
	 * @param errors
	 *            GoodValidationErrors instance
	 * 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> create(String categoryName,
			String subcategoryName, String model,
			Map<String, Object> parametersMap) {
		if (categoryName != null) {
			parametersMap.put(XslParameter.CATEGORY.toString(), categoryName);
		}
		if (subcategoryName != null) {
			parametersMap.put(XslParameter.SUBCATEGORY.toString(),
					subcategoryName);
		}
		if (model != null) {
			parametersMap.put(XslParameter.MODEL.toString(), model);
		}
		return parametersMap;
	}

}
