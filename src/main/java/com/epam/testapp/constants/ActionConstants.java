package com.epam.testapp.constants;

/**
 * Mapping --- an enum which contains constants being used in ProductAction 
 * 
 * @author Marharyta_Amelyanchuk
 */
public enum ActionConstants {
	SUCCESS, FAILURE, NOT_VALID, INDEX;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
