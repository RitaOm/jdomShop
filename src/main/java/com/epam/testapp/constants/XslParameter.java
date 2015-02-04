package com.epam.testapp.constants;

/**
 * XslParameter --- an enum which contains names of xsl parameters needed to
 * pass information in request process
 * 
 * @author Marharyta_Amelyanchuk
 */

public enum XslParameter {
	CATEGORY, SUBCATEGORY, GOOD, VALIDATOR, ERRORS, XML, PRODUCER, MODEL, DATE, COLOR, PRICE, NOT_IN_STOCK;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
