package com.epam.testapp.util;

import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * TemplatesCache --- final public class with private constructor for storing
 * Templates instances in ConcurrentHashMap and method for access to them from
 * property file
 * 
 * @author Marharyta_Amelyanchuk
 */
final public class TemplatesCache {

	private TemplatesCache() {
	}

	private static ConcurrentHashMap<String, Templates> cache = new ConcurrentHashMap<String, Templates>();

	private static TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	/**
	 * public static method to put new Templates instance to map or return
	 * existing if it's already exists for given xslPath
	 * 
	 * @param String
	 *            xslPath - a key for Templates instances
	 * @return Transformer - appropriate instance
	 * @exception TransformerConfigurationException
	 * 
	 */
	public static Transformer getTransformer(String xslPath)
			throws TransformerConfigurationException {
		Source source = new StreamSource(
				TemplatesCache.class.getResourceAsStream(xslPath));
		cache.putIfAbsent(xslPath, transformerFactory.newTemplates(source));
		return cache.get(xslPath).newTransformer();
	}

}
